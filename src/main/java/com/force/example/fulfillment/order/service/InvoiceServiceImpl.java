package com.force.example.fulfillment.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.force.api.ApiSession;
import com.force.api.ForceApi;
import com.force.api.QueryResult;
import com.force.sdk.oauth.context.ForceSecurityContextHolder;
import com.force.sdk.oauth.context.SecurityContext;

import com.force.example.fulfillment.order.model.Invoice;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private ForceApi getForceApi() {
        SecurityContext sc = ForceSecurityContextHolder.get();

        ApiSession s = new ApiSession();
        s.setAccessToken(sc.getSessionId());
        s.setApiEndpoint(sc.getEndPointHost());

        return new ForceApi(s);
    }
    
	@Override
	public void addInvoice(Invoice invoice) {
        getForceApi().createSObject("Invoice_Statement__c", invoice);
	}

	@Override
	public List<Invoice> listInvoices() {
        QueryResult<Invoice> res = getForceApi().query("SELECT Id, Name, Description__c, Status__c FROM Invoice_Statement__c", Invoice.class);
        return res.getRecords();
	}

	@Override
	public Invoice findInvoice(String id) {
		return getForceApi().getSObject("Invoice_Statement__c", id).as(Invoice.class);
	}

	@Override
	public void removeInvoice(String id) {
        getForceApi().deleteSObject("Invoice_Statement__c", id);
    }

}
