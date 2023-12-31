package com.integration.auth.common.generator;

import com.integration.auth.common.errors.ErrorFactory;
import com.integration.auth.common.util.TargetTypes;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simple Distributed unique ID generator class.
 */
public final class UniqueIdGenerator {

	/**
	 * Maximum elapsed time from base time. 2,199,023,255,551 milliseconds after base
	 * time.
	 */
	private static final long TIME_MAX = (1L << 41) - 1L; // milliseconds

	/**
	 * Maximum sequence ID per millisecond. 63 is the maximum.
	 */
	private static final long SEQUENCE_MAX = (1L << 6) - 1L;

	/**
	 * Lock object.
	 */
	private static final Object LOCK = new Object();

	/**
	 * Base time for generating unique ID. Unit is millisecond.
	 */
	private long baseTime;

	/**
	 * UniqueIdGenerator instance's identifier. Unsigned 16 bits integer value (0-262143)
	 * is required.
	 */
	private int machineId;

	/**
	 * This time indicates when the sequence ID was generated. This value will be updated
	 * at any time.
	 */
	private volatile long elapsedTime;

	/**
	 * Sequence ID. This value will be reset to 0 each time elapsedTime is updated.
	 */
	private AtomicInteger counter = new AtomicInteger(0);

	/**
	 * Initializes {@link UniqueIdGenerator} instance.
	 * @param machineId Machine ID
	 * @param baseTime Base time in UTC. UniqueIdGenerator uses the elapsed time from base
	 * time to generate an unique ID. Base time should be after UNIX epoch
	 * (1970-01-01T00:00:00Z), and before current time.
	 * @throws RuntimeException If failed to process machine ID, base time is invalid, or
	 * elapsed time from base time exceeded the maximum.
	 * @throws NullPointerException If machined ID or base time is null.
	 */
	public UniqueIdGenerator(MachineId machineId, Instant baseTime) {

		Instant now = Instant.now();
		if (baseTime.isBefore(Instant.EPOCH) || baseTime.isAfter(now)) {
			throw new UniqueIdGeneratorException(
					ErrorFactory.getInvalidBaseTime(TargetTypes.UNIQUE_ID_GENERATOR, baseTime));
		}
		long elapsed = now.toEpochMilli() - baseTime.toEpochMilli();
		if (elapsed > TIME_MAX) {
			throw new UniqueIdGeneratorException(ErrorFactory
					.getInvalidExceededTimeLimitQueryCriteriaError(TargetTypes.UNIQUE_ID_GENERATOR, elapsed));
		}

		this.machineId = machineId.getId();
		this.baseTime = baseTime.toEpochMilli();
		this.elapsedTime = elapsed;
	}

	/**
	 * Initializes {@link UniqueIdGenerator} instance. Base time is set to
	 * 2017-06-01T00:00:00Z by default.
	 * @param machineId machine ID.
	 * @throws UniqueIdGeneratorException If failed to process machine ID.
	 * @throws NullPointerException If machine ID is null.
	 */
	public UniqueIdGenerator(MachineId machineId) {
		this(machineId, ZonedDateTime.of(2017, 6, 1, 0, 0, 0, 0, ZoneId.of("UTC")).toInstant());
	}

	/**
	 * Initializes {@link UniqueIdGenerator} instance. Base time is set to
	 * 2017-06-01T00:00:00Z by default. Machine ID will be created by using the latter
	 * 16-bits of the host's IP address.
	 * @throws UniqueIdGeneratorException If failed to process machine ID.
	 */
	public UniqueIdGenerator() {
		this(new DefaultMachineId());
	}

	/**
	 * Generates unique ID. Every time this method is invoked, the instance generates a
	 * different unique ID. (Static method)
	 * @return unique ID (String)
	 */
	public static String nextIdKey() {
		return new UniqueIdGenerator().next();
	}

	/**
	 * Non static method to Generates unique ID. Every time this method is invoked, the
	 * instance generates a different unique ID. Use when IDs are being generated in loop
	 * to utilize @link{AtomicInteger} counter to get different ids in same instant.
	 * @return unique ID (String)
	 */
	public String nextId() {
		return next();
	}

	/**
	 * Generates unique ID. Every time this method is invoked, the instance generates a
	 * different unique ID.
	 * @return unique ID.
	 * @throws UniqueIdGeneratorException If elapsed time from base time exceeded the
	 * maximum, or failed to generate sequence ID.
	 */
	private String next() {

		// elapsedTime
		long elapsed = getElapsedTime();
		// sequence
		int sequence = getSequence(elapsed);
		// If sequence ID exceeded maximum value, it retries once to generate unique ID 2
		// milliseconds later.
		if (sequence > SEQUENCE_MAX) {
			sleep(TimeUnit.MILLISECONDS.toMillis(2L));

			elapsed = getElapsedTime();
			sequence = getSequence(elapsed);
			if (sequence > SEQUENCE_MAX) {
				throw new UniqueIdGeneratorException(
						ErrorFactory.getInvalidSequenceId(TargetTypes.UNIQUE_ID_GENERATOR, sequence));
			}
		}

		long id = (elapsed << 22) | (sequence << 16) | machineId;
		return Long.toString(id);
	}

	/**
	 * Calculates elapsed time from base time.
	 * @return Elapsed time from base time in milliseconds.
	 * @throws UniqueIdGeneratorException If elapsed time from base time exceeded the
	 * maximum.
	 */
	private long getElapsedTime() {

		long now = Instant.now().toEpochMilli();
		long elapsed = now - baseTime;
		if (elapsed > TIME_MAX) {
			throw new UniqueIdGeneratorException(ErrorFactory
					.getInvalidExceededTimeLimitQueryCriteriaError(TargetTypes.UNIQUE_ID_GENERATOR, elapsed));
		}
		return elapsed;
	}

	/**
	 * Generates sequence ID at a certain elapsed time. The maximum value of generated
	 * sequence ID is expected to 63, but this method not checks if the sequence ID is
	 * exceeded maximum. The caller of this method should check the validity of the
	 * generated sequence ID.
	 * @param elapsed Elapsed time from base time in milliseconds.
	 * @return sequence ID.
	 */
	private int getSequence(long elapsed) {

		synchronized (LOCK) {
			if (this.elapsedTime < elapsed) {
				this.elapsedTime = elapsed;
				counter.set(0); // reset
			}
			return counter.getAndIncrement();
		}
	}

	/**
	 * Sleeps currently executing thread for duration times.
	 * @param durationMillis Sleep time.
	 * @throws UniqueIdGeneratorException If the sleeping thread was interrupted.
	 */
	private void sleep(long durationMillis) {

		try {
			Thread.sleep(durationMillis);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new UniqueIdGeneratorException(
					ErrorFactory.interruptionOccurredDuringThread(TargetTypes.UNIQUE_ID_GENERATOR));
		}
	}

}