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

import com.avs.portal.bean.UserFamilyMapBean;
import com.avs.portal.enums.FamilyMemberTitleEnum;
import com.avs.portal.enums.LiveStatusEnum;

@Entity
@Table(schema = "public", name = "user_family_map_15")
public class UserFamilyMap extends BaseEntity {

	@Id
    @Column(name = "id")
	private UUID id;
	
	@MapsId
	@OneToOne(mappedBy = "userFamilyMap")
	@JoinColumn(name = "userid")   //same name as id @Column
    private User user;
	
	@Column(name = "parent_family_head_id")
	private UUID parentFamilyHeadId;

	@Column(name = "family_head_id")
	private UUID familyHeadId;
	
	@Column(name = "title")
	private FamilyMemberTitleEnum title;
	
	@Column(name = "live_status")
	private LiveStatusEnum liveStatus = LiveStatusEnum.ALIVE;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public UserFamilyMap setId(UUID id) {
		this.id = id;
		return this;
	}

	public UUID getParentFamilyHeadId() {
		return parentFamilyHeadId;
	}

	public UserFamilyMap setParentFamilyHeadId(UUID parentFamilyId) {
		this.parentFamilyHeadId = parentFamilyId;
		return this;
	}

	public UUID getFamilyHeadId() {
		return familyHeadId;
	}

	public UserFamilyMap setFamilyHeadId(UUID familyHeadId) {
		this.familyHeadId = familyHeadId;
		return this;
	}

	public FamilyMemberTitleEnum getTitle() {
		return title;
	}

	public UserFamilyMap setTitle(FamilyMemberTitleEnum title) {
		this.title = title;
		return this;
	}

	public LiveStatusEnum getLiveStatus() {
		return liveStatus;
	}

	public UserFamilyMap setLiveStatus(LiveStatusEnum liveStatus) {
		this.liveStatus = liveStatus;
		return this;
	}

	public User getUser() {
		return user;
	}

	public UserFamilyMap setUser(User user) {
		this.user = user;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserFamilyMap setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserFamilyMap setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	public UserFamilyMapBean toBean() {
		UserFamilyMapBean userFamilyMapBean = new UserFamilyMapBean()
				.setId(id)
				.setParentFamilyHeadId(parentFamilyHeadId)
				.setFamilyHeadId(familyHeadId)
				.setLiveStatus(liveStatus)
				.setTitle(title)
				.setUserId(user == null ? null : user.getId())
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());
		
		userFamilyMapBean				
				.setHasError(isHasError())
				.setCustomErrorMessages(getCustomErrorMessages())
				.setThrowable(getThrowable());
		
		return userFamilyMapBean;
			
	}

	@Override
	public String toString() {
		return "\nUserFamilyMap [" + 
					"  Id: " + id + 
					", Parent Family Head Id: " + parentFamilyHeadId + 
					", Family Head Id: " + familyHeadId + 
					", Family Member Title: " + title + 
					", Alive Status: " + liveStatus + 
					", User: " + (user == null ? "NULL" : user.getId()) + 
					", Created On: " + createdOn + 
					", Updated On: " + updatedOn + 
					"]";
	}
	
}
