package com.integration.auth.service.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

	@Id
	private String id;

	private String username;

	private String password;

	private String tenantId;

	private String paSystem;

	private String accountStatus;

	private Date createdDate;

	private Date updatedDate;

	private Date passwordResetDate;

	private Date lastLoginDate;

	private Date logoutDate;

	private String nativeSystem;

	@Field("is_Deleted")
	private String isDeleted;

	public User(String username, String password, String tenantId, String paSystem, String accountStatus,
			String nativeSystem, Date createdDate) {
		this.username = username;
		this.password = password;
		this.tenantId = tenantId;
		this.paSystem = paSystem;
		this.accountStatus = accountStatus;
		this.nativeSystem = nativeSystem;
		this.createdDate = createdDate;
	}

}
