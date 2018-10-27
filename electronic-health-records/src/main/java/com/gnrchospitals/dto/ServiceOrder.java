package com.gnrchospitals.dto;

public class ServiceOrder {

	private String serviceName;
	private String serviceId;
	private String minorCode;
	private String serviceCode;
	private String qty;
	private String rate;
	private String discount;
	private String discountAmount;
	private String netAmount;
	
	

	public String getMinorCode() {
		return minorCode;
	}

	public void setMinorCode(String minorCode) {
		this.minorCode = minorCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	@Override
	public String toString() {
		return "ServiceOrder [serviceName=" + serviceName + ", serviceId=" + serviceId + ", minorCode=" + minorCode
				+ ", serviceCode=" + serviceCode + ", qty=" + qty + ", rate=" + rate + ", discount=" + discount
				+ ", discountAmount=" + discountAmount + ", netAmount=" + netAmount + "]";
	}

	

}
