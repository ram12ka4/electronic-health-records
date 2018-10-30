package com.gnrchospitals.dto;

public class Patient {

	private String mrdNumber;
	private String ipNumber;
	private String patientName;
	private String sex;
	private String age;
	private String admissionDate;
	private String doctorId;
	private String doctorIncharge;
	private String speciality;
	private String bedNo;
	private String wardNo;
	private String maritalStatus;
	private String patientCategoryCode;
	private String patientSubCategoryCode;
	private String patientCategory;
	private String patientSubCategory;
	private String visit;
	private Emr emr;
	private static Patient patient;

	private Patient() {
	}

	static {
		try {
			patient = new Patient();
		} catch (Exception ex) {
			throw new RuntimeException("Exception occured in creating singleton instance");
		}
	}
	
	

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getPatientCategoryCode() {
		return patientCategoryCode;
	}

	public void setPatientCategoryCode(String patientCategoryCode) {
		this.patientCategoryCode = patientCategoryCode;
	}

	public String getPatientSubCategoryCode() {
		return patientSubCategoryCode;
	}

	public void setPatientSubCategoryCode(String patientSubCategoryCode) {
		this.patientSubCategoryCode = patientSubCategoryCode;
	}

	public String getVisit() {
		return visit;
	}

	public void setVisit(String visit) {
		this.visit = visit;
	}

	public String getPatientCategory() {
		return patientCategory;
	}

	public void setPatientCategory(String patientCategory) {
		this.patientCategory = patientCategory;
	}

	public String getPatientSubCategory() {
		return patientSubCategory;
	}

	public void setPatientSubCategory(String patientSubCategory) {
		this.patientSubCategory = patientSubCategory;
	}

	public static Patient getInstance() {
		return patient;
	}

	public void setMrdNumber(String mrdNumber) {
		this.mrdNumber = mrdNumber;
	}

	public void setIpNumber(String ipNumber) {
		this.ipNumber = ipNumber;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}

	public void setDoctorIncharge(String doctorIncharge) {
		this.doctorIncharge = doctorIncharge;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	public void setWardNo(String wardNo) {
		this.wardNo = wardNo;
	}

	public void setMaritalStatus(String maritalStatus) {
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

	public Emr getEmr() {
		return emr;
	}

	public void setEmr(Emr emr) {
		this.emr = emr;
	}

	@Override
	public String toString() {
		return "Patient [mrdNumber=" + mrdNumber + ", ipNumber=" + ipNumber + ", patientName=" + patientName + ", sex="
				+ sex + ", age=" + age + ", admissionDate=" + admissionDate + ", doctorId=" + doctorId
				+ ", doctorIncharge=" + doctorIncharge + ", speciality=" + speciality + ", bedNo=" + bedNo + ", wardNo="
				+ wardNo + ", maritalStatus=" + maritalStatus + ", patientCategoryCode=" + patientCategoryCode
				+ ", patientSubCategoryCode=" + patientSubCategoryCode + ", patientCategory=" + patientCategory
				+ ", patientSubCategory=" + patientSubCategory + ", visit=" + visit + ", emr=" + emr + "]";
	}

	

}
