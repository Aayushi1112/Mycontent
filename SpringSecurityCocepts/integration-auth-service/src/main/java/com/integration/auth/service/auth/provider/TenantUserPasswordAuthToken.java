package com.integration.auth.service.auth.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

public class TenantUserPasswordAuthToken extends UsernamePasswordAuthenticationToken {

	private final String tenantId;

	public TenantUserPasswordAuthToken(String tenantId, Object principal, Object credentials) {
		super(principal, credentials);
		this.tenantId = tenantId;
	}

	public TenantUserPasswordAuthToken(String tenantId, Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.tenantId = tenantId;
	}

	public String getTenantId() {
		return tenantId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		TenantUserPasswordAuthToken that = (TenantUserPasswordAuthToken) o;
		return Objects.equals(tenantId, that.tenantId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), tenantId);
	}

}
