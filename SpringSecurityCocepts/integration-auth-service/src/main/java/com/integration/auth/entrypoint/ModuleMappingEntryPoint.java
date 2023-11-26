package com.integration.auth.entrypoint;

import com.integration.auth.common.responses.FailureResponseComposer;
import com.integration.auth.entrypoint.validators.AuthServiceValidator;
import com.integration.auth.service.auth.exceptions.AuthServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.integration.auth.common.util.Constants.*;

@Slf4j
@RestController
@RequestMapping(AUTH_API_BASE_URI)
public class ModuleMappingEntryPoint {

	@Autowired
	AuthServiceValidator dataInputValidator;

	@GetMapping(value = MODULE_MAPPING, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> moduleMapping(@RequestParam("tenantName") String tenantName,
			@RequestParam("userName") String userName, @RequestParam("targetSystem") String targetSystem) {
		try {
			dataInputValidator.validateInputToken(tenantName, userName, targetSystem);
			return ResponseEntity.ok(JSON_DATA);

		}
		catch (AuthServiceException ex) {
			log.error(MARKER + MODULE_MAPPING_API_ERROR, ex);
			return FailureResponseComposer.composeFailureResponse(ex, userName);
		}
	}

}
