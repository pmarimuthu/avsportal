package com.avs.portal.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.avs.portal.enums.CasteEnum;
import com.avs.portal.enums.KoththiramEnum;
import com.avs.portal.enums.MaritalStatusEnum;
import com.avs.portal.enums.NatchaththiramEnum;
import com.avs.portal.enums.RaasiEnum;
import com.avs.portal.enums.ReligionEnum;
import com.avs.portal.enums.SubcasteEnum;

public class UserProfileBean implements Serializable {

	private static final long serialVersionUID = 8389845465812273014L;

	private UUID id;
	
	private MaritalStatusEnum maritalStatus;
	
	private ReligionEnum religion;
	
	private CasteEnum caste;
	
	private SubcasteEnum subcaste;
	
	private KoththiramEnum koththiram;
	
	private String placeOfBirth;
	
	private LocalDateTime birthTimestamp;
	
	private RaasiEnum raasi;
	
	private NatchaththiramEnum natchaththiram;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;

	public UUID getId() {
		return id;
	}
	
	public UserProfileBean setId(UUID userId) {
		this.id = userId;
		return this;
	}

	public MaritalStatusEnum getMaritalStatus() {
		return maritalStatus;
	}

	public UserProfileBean setMaritalStatus(MaritalStatusEnum maritalStatus) {
		this.maritalStatus = maritalStatus;
		return this;
	}

	public ReligionEnum getReligion() {
		return religion;
	}

	public UserProfileBean setReligion(ReligionEnum religion) {
		this.religion = religion;
		return this;
	}

	public CasteEnum getCaste() {
		return caste;
	}

	public UserProfileBean setCaste(CasteEnum caste) {
		this.caste = caste;
		return this;
	}

	public SubcasteEnum getSubcaste() {
		return subcaste;
	}

	public UserProfileBean setSubcaste(SubcasteEnum subcaste) {
		this.subcaste = subcaste;
		return this;
	}

	public KoththiramEnum getKoththiram() {
		return koththiram;
	}

	public UserProfileBean setKoththiram(KoththiramEnum koththiram) {
		this.koththiram = koththiram;
		return this;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public UserProfileBean setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
		return this;
	}

	public LocalDateTime getBirthTimestamp() {
		return birthTimestamp;
	}

	public UserProfileBean setBirthTimestamp(LocalDateTime birthTimestamp) {
		this.birthTimestamp = birthTimestamp;
		return this;
	}

	public RaasiEnum getRaasi() {
		return raasi;
	}

	public UserProfileBean setRaasi(RaasiEnum raasi) {
		this.raasi = raasi;
		return this;
	}

	public NatchaththiramEnum getNatchaththiram() {
		return natchaththiram;
	}

	public UserProfileBean setNatchaththiram(NatchaththiramEnum natchaththiram) {
		this.natchaththiram = natchaththiram;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public UserProfileBean setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public UserProfileBean setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	@Override
	public String toString() {
		return "\nUserProfileBean [ Id: " + id + ", Marital Status: " + maritalStatus + ", Religion: "
				+ religion + ", Caste: " + caste + ", Subcaste: " + subcaste + ", Koththiram: " + koththiram
				+ ", Place Of Birth: " + placeOfBirth + ", Birth Timestamp: " + birthTimestamp + ", Raasi: " + raasi
				+ ", Natchaththiram: " + natchaththiram + ", Created On: " + createdOn + ", Updated On: " + updatedOn + " ]";
	}

}
