package com.gnrchospitals.dto;

public class PatientIdentification {

	private String mrdNumber;
	private String ipNumber;
	private String patientName;
	private String sex;
	private String age;
	private String admissionDate;
	private String doctorName;

	public PatientIdentification(String mrdNumber, String ipNumber, String patientName, String sex, String age,
			String admissionDate, String doctorName) {
		super();
		this.mrdNumber = mrdNumber;
		this.ipNumber = ipNumber;
		this.patientName = patientName;
		this.sex = sex;
		this.age = age;
		this.admissionDate = admissionDate;
		this.doctorName = doctorName;
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

	public String getDoctorName() {
		return doctorName;
	}

}
