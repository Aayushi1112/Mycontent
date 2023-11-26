package com.integration.auth.common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CommonUtil {

	private HttpServletRequest getRequest() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		return requestAttributes.getRequest();
	}

	public String getDevice() {
		jakarta.servlet.http.HttpServletRequest request = getRequest();
		return request.getHeader("User-Agent");
	}

	public String getRemoteAddress() {
		jakarta.servlet.http.HttpServletRequest request = getRequest();
		return request.getRemoteAddr();
	}

	public String getPaSystem() {
		return getRequest().getHeader(Constants.PASYSTEM);
	}

}
