package com.avs.portal.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.avs.portal.bean.UserRelationToMeMapBean;
import com.avs.portal.enums.RelationToMeEnum;
import com.avs.portal.enums.VerificationStatusEnum;

@Entity
@Table(schema = "public", name = "user_relation_to_me_map_14")
public class UserRelationToMeMap extends BaseEntity {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
	private UUID id;
	
	@ManyToOne()
	@JoinColumn(name = "userId")
	private User user; // ref

	@Column(name = "relative_user_id", columnDefinition = "uuid")
	private UUID relativeUserId;
	
	@Column(name = "relation_to_me_as")
	private RelationToMeEnum relationToMe;
	
	@Column(name = "verification_status")
	private VerificationStatusEnum verificaionStatus;
	
	@Column(name = "verified_by", columnDefinition = "uuid")
	private UUID verifiedBy;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public UserRelationToMeMap setId(UUID id) {
		this.id = id;
		return this;
	}

	public User getUser() {
		return user;
	}

	public UserRelationToMeMap setUser(User user) {
		this.user = user;
		return this;
	}

	public UUID getRelativeUserId() {
		return relativeUserId;
	}

	public UserRelationToMeMap setRelativeUserId(UUID relativeUserId) {
		this.relativeUserId = relativeUserId;
		return this;
	}

	public RelationToMeEnum getRelationToMe() {
		return relationToMe;
	}

	public UserRelationToMeMap setRelationToMe(RelationToMeEnum relationToMe) {
		this.relationToMe = relationToMe;
		return this;
	}

	public VerificationStatusEnum getVerificaionStatus() {
		return verificaionStatus;
	}

	public UserRelationToMeMap setVerificaionStatus(VerificationStatusEnum verificaionStatus) {
		this.verificaionStatus = verificaionStatus;
		return this;
	}

	public UUID getVerifiedBy() {
		return verifiedBy;
	}

	public UserRelationToMeMap setVerifiedBy(UUID verifiedBy) {
		this.verifiedBy = verifiedBy;
		return this;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public UserRelationToMeMap setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public UserRelationToMeMap setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}
	
	@Override
	public UserRelationToMeMapBean toBean() {
		UserRelationToMeMapBean userRelationToMeMapBean = new UserRelationToMeMapBean()
				.setId(id)
				.setUserId(user.getId())
				.setRelativeUserId(relativeUserId)
				.setRelationToMe(relationToMe)
				.setVerificaionStatus(verificaionStatus)
				.setVerifiedBy(verifiedBy)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());
		
		userRelationToMeMapBean				
				.setHasError(isHasError())
				.setCustomErrorMessages(getCustomErrorMessages())
				.setThrowable(getThrowable());
		
		return userRelationToMeMapBean;
	}

	@Override
	public String toString() {
		return "\nUserRelationToMe [ " +
				"  Id: " + id + 
				", User Id: " + user.getId() + 
				", Relative User Id: " + relativeUserId + 
				", Relation To Me: " + relationToMe + 
				", Verificaion Status: " + verificaionStatus + 
				", Verified By: " + verifiedBy + 
				", Created On: " + createdOn + 
				", Updated On: " + updatedOn + 
				" ]";
	}
	
}
