package com.force.example.fulfillment.order.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.force.api.ApiException;
import com.force.example.fulfillment.order.service.InvoiceService;
import com.force.example.fulfillment.order.service.InvoiceServiceImpl;

// Check that the invoice id exists in Force.com
public class CheckInvoiceIdValidator implements ConstraintValidator<CheckInvoiceId, String> {
	// @Autowired doesn't work here because of Hibernate/Spring incompatibilities
	private InvoiceService invoiceService;

	@Override
	public void initialize(CheckInvoiceId arg0) {
		this.invoiceService = new InvoiceServiceImpl();
	}

	@Override
	public boolean isValid(String id, ConstraintValidatorContext context) {
    	try {
    		invoiceService.findInvoice(id);
    	} catch (ApiException ae){
    		return false;
    	}
    	
		return true;
	}
}