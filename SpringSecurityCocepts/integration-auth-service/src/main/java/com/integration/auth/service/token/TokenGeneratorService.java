package com.integration.auth.service.token;

import com.integration.auth.common.responses.Token;
import java.util.Map;

public interface TokenGeneratorService {

	String createSessionToken(String username, Map<String, String> claims);

	String createRefreshToken(String username, Map<String, String> claims);

	Token fetchTokenDetails(Object loginRequest, String paSystem, String loginType);

}
