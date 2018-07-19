package com.gnrchospitals.dto;

public class Patient {

	private String mrdNumber;
	private String ipNumber;
	private String patientName;
	private String sex;
	private String age;
	private String admissionDate;
	private String doctorIncharge;
	private String speciality;
	private String bedNo;
	private String wardNo;
	private String maritalStatus;

	public Patient(String mrdNumber, String ipNumber, String patientName, String sex, String age, String admissionDate,
			String doctorIncharge, String speciality, String bedNo, String wardNo, String maritalStatus) {
		super();
		this.mrdNumber = mrdNumber;
		this.ipNumber = ipNumber;
		this.patientName = patientName;
		this.sex = sex;
		this.age = age;
		this.admissionDate = admissionDate;
		this.doctorIncharge = doctorIncharge;
		this.speciality = speciality;
		this.bedNo = bedNo;
		this.wardNo = wardNo;
		this.maritalStatus = maritalStatus;
	}

	public String getMrdNumber() {
		return mrdNumber;
	}

	public String getIpNumber() {
		return ipNumber;
	}

	public String getPatientName() {
		return patientName;
	}

	public String getSex() {
		return sex;
	}

	public String getAge() {
		return age;
	}

	public String getAdmissionDate() {
		return admissionDate;
	}

	public String getDoctorIncharge() {
		return doctorIncharge;
	}

	public String getSpeciality() {
		return speciality;
	}

	public String getBedNo() {
		return bedNo;
	}

	public String getWardNo() {
		return wardNo;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

}
