package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import com.avs.portal.enums.GenderEnum;

public class UserInformationBean implements Serializable {

	private static final long serialVersionUID = -3509526445058485342L;
	
	private UUID id;
	
	private String firstname;
	
	private String lastname;
	
	private GenderEnum gender;
	
	private Date dateOfBirth;
	
	private String profession;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;

	public UUID getId() {
		return id;
	}

	public UserInformationBean setId(UUID userId) {
		this.id = userId;
		return this;
	}

	public String getFirstname() {
		return firstname;
	}

	public UserInformationBean setFirstname(String firstname) {
		this.firstname = firstname;
		return this;
	}

	public String getLastname() {
		return lastname;
	}

	public UserInformationBean setLastname(String lastname) {
		this.lastname = lastname;
		return this;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public UserInformationBean setGender(GenderEnum gender) {
		this.gender = gender;
		return this;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public UserInformationBean setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public String getProfession() {
		return profession;
	}

	public UserInformationBean setProfession(String profession) {
		this.profession = profession;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public UserInformationBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public UserInformationBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nUserInformation [ Id: " + id + ", Firstname: " + firstname + ", Lastname: "
				+ lastname + ", Gender: " + gender + ", Date Of Birth: " + dateOfBirth + ", Profession: " + profession
				+ ", Created On: " + createdOn + ", Updated On: " + updatedOn + " ]";
	}

}
