package com.avs.portal.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.avs.portal.bean.UserFamilyMapBean;
import com.avs.portal.enums.FamilyMemberTitleEnum;
import com.avs.portal.enums.LiveStatusEnum;

@Entity
@Table(schema = "public", name = "user_family_map_15")
public class UserFamilyMap {

	@Id
    @Column(name = "id", updatable = false, nullable = false)
	private UUID id;	

	@Column(name = "parent_family_head_id")
	private UUID parentFamilyHeadId;

	@Column(name = "family_head_id", nullable = false)
	private UUID familyHeadId;
	
	@Column(name = "title", nullable = false)
	private FamilyMemberTitleEnum title;
	
	@Column(name = "live_status", nullable = false)
	private LiveStatusEnum liveStatus;
	
	@OneToMany(mappedBy = "userFamilyMap", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<User> users = new ArrayList<User>();

	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	@SuppressWarnings("unused")
	private UserFamilyMap() {
		
	}
	
	public UserFamilyMap(UUID id) {
		this.id = id;
	}
	
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

	public List<User> getUsers() {
		return users;
	}

	public UserFamilyMap setUsers(List<User> users) {
		this.users = users;
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
		return new UserFamilyMapBean()
				.setId(id)
				.setParentFamilyHeadId(parentFamilyHeadId)
				.setFamilyHeadId(familyHeadId)
				.setLiveStatus(liveStatus)
				.setTitle(title)
				.setUserIds((users == null ? null : users.stream().map(User :: getId).collect(Collectors.toList())))
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());
			
	}

	@Override
	public String toString() {
		return "\nUserFamilyMap [" + 
					"  Id: " + id + 
					", Parent Family Head Id: " + parentFamilyHeadId + 
					", Family Head Id: " + familyHeadId + 
					", Family Member Title: " + title + 
					", Alive Status: " + liveStatus + 
					", Users: " + (users == null ? "NULL" : users.stream().map(User :: getId).collect(Collectors.toList())) + 
					", Created On: " + createdOn + 
					", Updated On: " + updatedOn + 
					"]";
	}
	
}
