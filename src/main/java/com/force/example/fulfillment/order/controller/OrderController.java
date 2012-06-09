package com.force.example.fulfillment.order.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.force.example.fulfillment.order.model.Order;
import com.force.example.fulfillment.order.service.OrderService;

@Controller
@RequestMapping(value="/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	private Validator validator;
	
	@Autowired
	public OrderController(Validator validator) {
		this.validator = validator;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody List<? extends Object> create(@Valid @RequestBody Order[] orders, HttpServletResponse response) {
		boolean failed = false;
		List<Map<String, String>> failureList = new LinkedList<Map<String, String>>();
		for (Order order: orders) {
			Set<ConstraintViolation<Order>> failures = validator.validate(order);
			if (failures.isEmpty()) {
				Map<String, String> failureMessageMap = new HashMap<String, String>();
				if (! orderService.findOrderById(order.getId()).isEmpty()) {					
					failureMessageMap.put("id", "id already exists in database");					
					failed = true;
				}
				failureList.add(failureMessageMap);
			} else {
				failureList.add(validationMessages(failures));
				failed = true;
			}
		}
		if (failed) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return failureList;
		} else {
			List<Map<String, Object>> responseList = new LinkedList<Map<String, Object>>();
			for (Order order: orders) {
				orderService.addOrder(order);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", order.getId());
				map.put("order_number", order.getOrderId());
				responseList.add(map);
			}
			return responseList;
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody List<Order> getOrders() {
		return orderService.listOrders();
	}

	@RequestMapping(value="{orderId}", method=RequestMethod.GET)
	public @ResponseBody Order getOrder(@PathVariable Integer orderId) {
		Order order = orderService.findOrder(orderId);
		if (order == null) {
			throw new ResourceNotFoundException(orderId);
		}
		return order;
	}
	
	@RequestMapping(value="{orderId}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteOrder(@PathVariable Integer orderId) {
		orderService.removeOrder(orderId);
	}
	
	// internal helper
	private Map<String, String> validationMessages(Set<ConstraintViolation<Order>> failureSet) {
		Map<String, String> failureMessageMap = new HashMap<String, String>();
		for (ConstraintViolation<Order> failure : failureSet) {
			failureMessageMap.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessageMap;
	}
}
