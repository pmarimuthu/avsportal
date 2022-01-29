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
public class UserInformation extends BaseEntity {

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

	public UserInformation setId(UUID userId) {
		this.id = userId;
		return this;
	}

	public User getUser() {
		return user;
	}

	public UserInformation setUser(User user) {
		this.user = user;
		return this;
	}

	public String getFirstname() {
		return firstname;
	}

	public UserInformation setFirstname(String firstname) {
		this.firstname = firstname;
		return this;
	}

	public String getLastname() {
		return lastname;
	}

	public UserInformation setLastname(String lastname) {
		this.lastname = lastname;
		return this;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public UserInformation setGender(GenderEnum gender) {
		this.gender = gender;
		return this;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public UserInformation setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public String getProfession() {
		return profession;
	}

	public UserInformation setProfession(String profession) {
		this.profession = profession;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserInformation setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserInformation setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}
	
	@Override
	public UserInformationBean toBean() {
		UserInformationBean userInformationBean = 
				new UserInformationBean()
					.setId(id)
					.setUserId(user.getId())
					.setFirstname(firstname)
					.setLastname(lastname)
					.setGender(gender)
					.setDateOfBirth(dateOfBirth)
					.setProfession(profession)
					.setCreatedOn(createdOn.toLocalDateTime())
					.setUpdatedOn(updatedOn.toLocalDateTime());
		
		userInformationBean				
				.setHasError(isHasError())
				.setCustomErrorMessages(getCustomErrorMessages())
				.setThrowable(getThrowable());
		
		return userInformationBean;
	}

	@Override
	public String toString() {
		return "UserInformation [" +
				"  Id: " + id + 
				", User Id: " + user.getId() + 
				", Firstname: " + firstname + 
				", Lastname: " + lastname + 
				", Gender: " + gender + 
				", DateOfBirth: " + dateOfBirth + 
				", Profession: " + profession + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				" ]";
	}
	
}
