package com.avs.portal.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.avs.portal.bean.UserProfileBean;
import com.avs.portal.enums.CasteEnum;
import com.avs.portal.enums.KoththiramEnum;
import com.avs.portal.enums.MaritalStatusEnum;
import com.avs.portal.enums.NatchaththiramEnum;
import com.avs.portal.enums.RaasiEnum;
import com.avs.portal.enums.ReligionEnum;
import com.avs.portal.enums.SubcasteEnum;
import com.avs.portal.util.CommonUtil;

@Entity
@Table(schema = "public", name = "user_profile_08")
public class UserProfile extends BaseEntity {

	@Id
	@Column(name = "id")
	private UUID id;
	
	@MapsId
	@OneToOne(mappedBy = "userProfile")
	@JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "marital_status")
	private MaritalStatusEnum maritalStatus;
	
	@Column(name = "religion")
	private ReligionEnum religion;
	
	@Column(name = "caste")
	private CasteEnum caste;
	
	@Column(name = "subcaste")
	private SubcasteEnum subcaste;
	
	@Column(name = "koththiram")
	private KoththiramEnum koththiram;
	
	@Column(name = "place_of_birth")
	private String placeOfBirth;
	
	@Column(name = "birth_timestamp")
	private Timestamp birthTimestamp;
	
	@Column(name = "rasi")
	private RaasiEnum raasi;
	
	@Column(name = "natchaththiram")
	private NatchaththiramEnum natchaththiram;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public UserProfile setId(UUID userId) {
		this.id = userId;
		return this;
	}

	public User getUser() {
		return user;
	}

	public UserProfile setUser(User user) {
		this.user = user;
		return this;
	}

	public MaritalStatusEnum getMaritalStatus() {
		return maritalStatus;
	}

	public UserProfile setMaritalStatus(MaritalStatusEnum maritalStatus) {
		this.maritalStatus = maritalStatus;
		return this;
	}

	public ReligionEnum getReligion() {
		return religion;
	}

	public UserProfile setReligion(ReligionEnum religion) {
		this.religion = religion;
		return this;
	}

	public CasteEnum getCaste() {
		return caste;
	}

	public UserProfile setCaste(CasteEnum caste) {
		this.caste = caste;
		return this;
	}

	public SubcasteEnum getSubcaste() {
		return subcaste;
	}

	public UserProfile setSubcaste(SubcasteEnum subcaste) {
		this.subcaste = subcaste;
		return this;
	}

	public KoththiramEnum getKoththiram() {
		return koththiram;
	}

	public UserProfile setKoththiram(KoththiramEnum koththiram) {
		this.koththiram = koththiram;
		return this;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public UserProfile setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
		return this;
	}

	public Timestamp getBirthTimestamp() {
		return birthTimestamp;
	}

	public UserProfile setBirthTimestamp(Timestamp birthTimestamp) {
		this.birthTimestamp = birthTimestamp;
		return this;
	}

	public RaasiEnum getRaasi() {
		return raasi;
	}

	public UserProfile setRaasi(RaasiEnum raasi) {
		this.raasi = raasi;
		return this;
	}

	public NatchaththiramEnum getNatchaththiram() {
		return natchaththiram;
	}

	public UserProfile setNatchaththiram(NatchaththiramEnum natchaththiram) {
		this.natchaththiram = natchaththiram;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserProfile setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserProfile setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public UserProfileBean toBean() {
		UserProfileBean userProfileBean = new UserProfileBean()
				.setId(id)
				.setUserId(user.getId())
				.setBirthTimestamp(CommonUtil.toLocalDateTimeOrNull(birthTimestamp))
				.setCaste(caste)
				.setKoththiram(koththiram)
				.setMaritalStatus(maritalStatus)
				.setNatchaththiram(natchaththiram)
				.setPlaceOfBirth(placeOfBirth)
				.setRaasi(raasi)
				.setReligion(religion)
				.setSubcaste(subcaste)
				.setCreatedOn(CommonUtil.toLocalDateTimeOrNull(createdOn))
				.setUpdatedOn(CommonUtil.toLocalDateTimeOrNull(updatedOn));
		
		userProfileBean				
				.setHasError(isHasError())
				.setCustomErrorMessages(getCustomErrorMessages())
				.setThrowable(getThrowable());
		
		return userProfileBean;
	}

	@Override
	public String toString() {
		return "UserProfile [" + 
				"  Id: " + id + 
				", User Id: " + user.getId() + 
				", Marital Status: " + maritalStatus + 
				", Religion: " + religion + 
				", Caste: " + caste + 
				", Subcaste: " + subcaste + 
				", Koththiram: " + koththiram + 
				", PlaceOfBirth: " + placeOfBirth + 
				", BirthTimestamp: " + birthTimestamp + 
				", Raasi: " + raasi + 
				", Natchaththiram: " + natchaththiram + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				" ]";
	}

}
