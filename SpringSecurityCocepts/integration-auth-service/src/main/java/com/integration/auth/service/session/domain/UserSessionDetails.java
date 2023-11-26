package com.integration.auth.service.session.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.integration.auth.service.session.dataprovider.CustomDateDeserializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users_session")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSessionDetails {

	@Id
	private String sessionId;

	@JsonProperty(value = "jti", required = true)
	@NotNull(message = "jti is mandatory")
	private String jti;

	@JsonProperty(value = "username", required = true)
	@NotNull(message = "userName is mandatory")
	private String username;

	private String sessionToken;

	private String refreshToken;

	@JsonProperty(value = "tenantId", required = true)
	private String tenantId;

	@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonProperty(value = "creation_date", required = true)
	@NotNull(message = "creationDate is mandatory")
	private Date creationDate;

	private String createdBySystem;

	private boolean isTerminated;

	private String terminationReason;

	private Date terminationDate;

	private String terminatedBySystem;

	private Date lastAccessed;

	private String lastAccessedBySystem;

	private String generatedByExpirationOff;

	private String sourceIPAddress;

	private String loginType;

	private String device;

}
