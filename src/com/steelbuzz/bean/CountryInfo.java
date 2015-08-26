package com.steelbuzz.bean;

public class CountryInfo {

	String countryName, countryCode;

	public CountryInfo(String countryName, String countryCode) {
		super();
		this.countryName = countryName;
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
