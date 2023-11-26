package com.integration.auth.common.generator;

/**
 * Manages machine ID, which identifies {@link UniqueIdGenerator} instance.
 */
public interface MachineId {

	/**
	 * Returns machine ID.
	 *
	 * Machine ID is expected as a 16-bits unsigned integer value (0-262143), and
	 * identifies {@link UniqueIdGenerator} instance. This method throws
	 * {@link UniqueIdGenerator} if the implementation of this method failed to get or
	 * generate machine ID.
	 * @return Machine ID.
	 * @throws UniqueIdGeneratorException If failed to get or generate machine ID.
	 */
	int getId();

}
