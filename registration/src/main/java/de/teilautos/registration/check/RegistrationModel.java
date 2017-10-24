/*
 Copyright (c) 2017 Videa Project Services GmbH

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
 and associated documentation files (the "Software"), to deal in the Software without restriction, 
 including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 and/or sell copies of the Software,and to permit persons to whom the Software is furnished to do so, 
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial 
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT 
 NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package de.teilautos.registration.check;

import java.io.Serializable;

public class RegistrationModel implements Serializable {
	private static final long serialVersionUID = -5537103333543653932L;

	private String firstname;
	private String surname;
	private String email;
	private String streetNo;
	private String postCode;
	private String city;
	private String phone;

	private PartnerProgramEnum partnerProgram;
	private String reference;

	private boolean termsAndConditionsAccepted;
	private boolean dataPrivacyAccepted;
	private boolean newsletterWanted;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public PartnerProgramEnum getPartnerProgram() {
		return partnerProgram;
	}

	public void setPartnerProgram(PartnerProgramEnum partnerProgram) {
		this.partnerProgram = partnerProgram;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public boolean isTermsAndConditionsAccepted() {
		return termsAndConditionsAccepted;
	}

	public void setTermsAndConditionsAccepted(boolean termsAndConditionsAccepted) {
		this.termsAndConditionsAccepted = termsAndConditionsAccepted;
	}

	public boolean isDataPrivacyAccepted() {
		return dataPrivacyAccepted;
	}

	public void setDataPrivacyAccepted(boolean dataPrivacyAccepted) {
		this.dataPrivacyAccepted = dataPrivacyAccepted;
	}

	public boolean isNewsletterWanted() {
		return newsletterWanted;
	}

	public void setNewsletterWanted(boolean newsletterWanted) {
		this.newsletterWanted = newsletterWanted;
	}

}
