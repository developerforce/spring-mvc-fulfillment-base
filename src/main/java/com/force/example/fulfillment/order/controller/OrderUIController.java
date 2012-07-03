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

import com.force.api.ApiException;
import com.force.example.fulfillment.order.model.Invoice;
import com.force.example.fulfillment.order.service.InvoiceService;

@Controller
@RequestMapping(value="/orderui")
public class OrderUIController {
    
	@Autowired
	private OrderService orderService;
    
    @Autowired
    private InvoiceService invoiceService;
	
	private Validator validator;
	
	@Autowired
	public OrderUIController(Validator validator) {
		this.validator = validator;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String getOrdersPage(Model model) {
		model.addAttribute("order", new Order());
		model.addAttribute("orders", orderService.listOrders());
		
		return "orders";
	}

	@RequestMapping(value="{orderId}", method=RequestMethod.GET)
	public String getOrderPage(@PathVariable Integer orderId, Model model) {
		Order order = orderService.findOrder(orderId);
		if (order == null) {
			throw new ResourceNotFoundException(orderId);
		}
		model.addAttribute("order", order);
        
        Invoice invoice;
        try {
            invoice = invoiceService.findInvoice(order.getId());
        } catch (ApiException ae) {
        	// No match
        	invoice = new Invoice();
        }
        model.addAttribute("invoice", invoice);
		
		return "order";
	}
}
