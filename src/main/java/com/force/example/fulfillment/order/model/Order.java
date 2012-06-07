package com.force.example.fulfillment.order.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name="ORDERS")
public class Order {
    @Id
    @GeneratedValue
	private Integer orderId;
	
    @Column(unique = true)
	@NotNull
	@Size(min = 15, max = 18)
	private String id;

	public Integer getOrderId() {
		return orderId;
	}

	void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String toString() {
		return new ToStringCreator(this).append("orderId", orderId).append("id", id)
				.toString();
	}
}
