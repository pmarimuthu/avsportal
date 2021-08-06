package com.avs.portal.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.avs.portal.bean.UserInformationBean;
import com.avs.portal.enums.GenderEnum;

@Entity
@Table(schema = "public", name = "user_information_06")
public class UserInformation {

	@Id
	@Column(name = "id")
	private UUID id;
	
	@MapsId
	@OneToOne(mappedBy = "userInformation")
	@JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "gender")
	private GenderEnum gender;
	
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	
	@Column(name = "profession")
	private String profession;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public void setId(UUID userId) {
		this.id = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	public UserInformationBean toBean() {
		UserInformationBean bean = new UserInformationBean();
		bean.setId(id);
		bean.setFirstname(firstname);
		bean.setLastname(lastname);
		bean.setGender(gender);
		bean.setDateOfBirth(dateOfBirth);
		bean.setProfession(profession);
		bean.setCreatedOn(createdOn.toLocalDateTime());
		bean.setUpdatedOn(updatedOn.toLocalDateTime());
		
		return bean;
	}

	@Override
	public String toString() {
		return "UserInformation [userId=" + id + ", user=" + user + ", firstname=" + firstname + ", lastname="
				+ lastname + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", profession=" + profession
				+ ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}
	
}
