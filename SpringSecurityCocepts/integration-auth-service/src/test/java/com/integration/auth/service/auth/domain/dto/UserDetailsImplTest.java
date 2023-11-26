package com.integration.auth.service.auth.domain.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.integration.auth.service.auth.domain.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserDetailsImplTest {

	@InjectMocks
	private UserDetailsImpl userDetails;

	@Mock
	private User user;

	@Test
	void testGetAuthorities() {
		user = new User("testuser", "password123", "tenant123", "FC", "ACtive", "fc", new Date());
		Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
		userDetails = new UserDetailsImpl("1", user.getUsername(), user.getPassword(), user.getTenantId(), authorities);
		Collection<? extends GrantedAuthority> expectedauthorities = userDetails.getAuthorities();
		assertEquals(1, expectedauthorities.size());
		assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
	}

	@Test
	void testGetters() {
		user = new User("testuser", "password123", "tenant123", "FC", "ACtive", "fc", new Date());
		Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
		userDetails = new UserDetailsImpl("1", user.getUsername(), user.getPassword(), user.getTenantId(), authorities);
		assertEquals("testuser", userDetails.getUsername());
		assertEquals("password123", userDetails.getPassword());
		assertEquals("tenant123", userDetails.getTenantId());
	}

	@Test
	void testIsMethods() {
		assertTrue(userDetails.isAccountNonExpired());
		assertTrue(userDetails.isAccountNonLocked());
		assertTrue(userDetails.isCredentialsNonExpired());
		assertTrue(userDetails.isEnabled());
	}

	@Test
	void testBuild() {
		UserDetailsImpl userDetails = UserDetailsImpl.build(user);
		assertEquals(user.getId(), userDetails.getId());
		assertEquals(user.getUsername(), userDetails.getUsername());
		assertEquals(user.getPassword(), userDetails.getPassword());
		assertEquals(user.getTenantId(), userDetails.getTenantId());
	}

	@Test
	void testEqualsAndHashCode() {
		UserDetailsImpl userDetails2 = new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(),
				user.getTenantId(), null);
		assertEquals(userDetails, userDetails2);
		assertEquals(userDetails.hashCode(), userDetails2.hashCode());
	}

}