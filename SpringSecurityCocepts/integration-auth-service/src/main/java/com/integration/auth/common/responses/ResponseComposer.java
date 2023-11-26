package com.integration.auth.common.responses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.integration.auth.common.util.GsonUTCDateAdapter;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Common Response Composer class for JSON and REST response construction.
 */
public class ResponseComposer {

	protected ResponseComposer() {
		/*
		 * Implementation needed
		 */
	}

	public static String createJSONStringForResponse(@SuppressWarnings("rawtypes") RestResponse response) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCDateAdapter("GMT")).create();
		return gson.toJson(response);
	}

	public static <T extends BaseDTO> RestResponse<T> createRestResponse(List<T> data) {
		RestResponse<T> response = new RestResponse<>();
		if (Objects.isNull(data)) {
			response.setData(List.of());
		}
		else {
			response.setData(data);
		}

		return response;
	}

}