package com.steelbuzz.bean;

public class SelectedSubCatagory {

	private String name;
	private String address;
	private String contactParson;
	private String tata;
	private String mobile;
	private String fax;
	private String residential;
	private String email;
	private String web;
	private String hughes_no;

	public SelectedSubCatagory(String name, String address, String contactParson, String tata, String mobile, String fax, String residential, String email, String web, String hughes_no) {
		this.name = name;
		this.address = address;
		this.contactParson = contactParson;
		this.tata = tata;
		this.mobile = mobile;
		this.fax = fax;
		this.residential = residential;
		this.email = email;
		this.web = web;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactParson() {
		return contactParson;
	}

	public void setContactParson(String contactParson) {
		this.contactParson = contactParson;
	}

	public String getTata() {
		return tata;
	}

	public void setTata(String tata) {
		this.tata = tata;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getResidential() {
		return residential;
	}

	public void setResidential(String residential) {
		this.residential = residential;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}
	public String getHughes_no() {
		return hughes_no;
	}

	public void setHughes_no(String hughes_no) {
		this.hughes_no = hughes_no;
	}
}
