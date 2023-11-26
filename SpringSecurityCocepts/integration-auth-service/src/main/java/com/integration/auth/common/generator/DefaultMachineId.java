package com.integration.auth.common.generator;

import com.integration.auth.common.errors.ErrorFactory;
import com.integration.auth.common.util.TargetTypes;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Generates Machine ID from latter 16-bits of the host's IP address.
 */
class DefaultMachineId implements MachineId {

	@Override
	public int getId() {
		try {
			byte[] address = InetAddress.getLocalHost().getAddress();
			return (Byte.toUnsignedInt(address[2])) << 8 | Byte.toUnsignedInt(address[3]);

		}
		catch (UnknownHostException e) {
			throw new UniqueIdGeneratorException(ErrorFactory.getInvalidMachineId(TargetTypes.UNIQUE_ID_GENERATOR));
		}
	}

}
