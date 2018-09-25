package com.gnrchospitals.dto;

public class IndoorPatient {

	private String ipNumber;
	private String name;
	private String ward;
	private String bedNumber;
	private String admissionDate;
	private String admittingDoctor;
	private String speciality;
	private String subCategory;
	
	public IndoorPatient() {
		
	}

	@Override
	public String toString() {
		return "IndoorPatient [ipNumber=" + ipNumber + ", ipName=" + name + ", ward=" + ward + ", bedNumber="
				+ bedNumber + ", admissionDate=" + admissionDate + ", admittingDoctor=" + admittingDoctor
				+ ", speciality=" + speciality + ", subCategory=" + subCategory + "]";
	}

	public String getIpNumber() {
		return ipNumber;
	}

	public void setIpNumber(String ipNumber) {
		this.ipNumber = ipNumber;
	}

	public String getIpName() {
		return name;
	}

	public void setIpName(String ipName) {
		this.name = ipName;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getBedNumber() {
		return bedNumber;
	}

	public void setBedNumber(String bedNumber) {
		this.bedNumber = bedNumber;
	}

	public String getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}

	public String getAdmittingDoctor() {
		return admittingDoctor;
	}

	public void setAdmittingDoctor(String admittingDoctor) {
		this.admittingDoctor = admittingDoctor;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public IndoorPatient(String ipNumber, String ipName, String ward, String bedNumber, String admissionDate,
			String admittingDoctor, String speciality, String subCategory) {
		super();
		this.ipNumber = ipNumber;
		this.name = ipName;
		this.ward = ward;
		this.bedNumber = bedNumber;
		this.admissionDate = admissionDate;
		this.admittingDoctor = admittingDoctor;
		this.speciality = speciality;
		this.subCategory = subCategory;
	}

}
