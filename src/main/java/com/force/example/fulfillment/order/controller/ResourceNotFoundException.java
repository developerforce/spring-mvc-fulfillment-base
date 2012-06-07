package com.force.example.fulfillment.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Integer resourceId;
	
	public ResourceNotFoundException(Integer resourceId) {
		this.resourceId = resourceId;
	}
	
	public Integer getResourceId() {
		return resourceId;
	}
	
}
