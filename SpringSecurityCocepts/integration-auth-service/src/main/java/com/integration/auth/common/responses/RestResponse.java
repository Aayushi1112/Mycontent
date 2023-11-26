package com.integration.auth.common.responses;

import com.integration.auth.common.errors.Errors;

import java.util.List;

public class RestResponse<T extends BaseDTO> {

	private Errors errors;

	private List<T> data;

	private Integer limit;

	private Integer offset;

	private Integer totalRecords;

	private Integer currentPageRecords;

	private Integer totalPages;

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getCurrentPageRecords() {
		return currentPageRecords;
	}

	public void setCurrentPageRecords(Integer currentPageRecords) {
		this.currentPageRecords = currentPageRecords;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

}
