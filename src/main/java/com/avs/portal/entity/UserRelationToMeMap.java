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

import com.avs.portal.bean.UserRelationToMeMapBean;
import com.avs.portal.enums.RelationToMeEnum;
import com.avs.portal.enums.VerificationStatusEnum;

@Entity
@Table(schema = "public", name = "user_relation_to_me_map_14")
public class UserRelationToMeMap {

	@Id
	@Column(name = "id")
	private UUID id;
	
	@MapsId
	@OneToOne(mappedBy = "tempPassword")
	@JoinColumn(name = "userid")   //same name as id @Column
    private User user;

	@Column(name = "relative_userid")
	private UUID relativeUserId;
	
	@Column(name = "relation_to_me_as")
	private RelationToMeEnum relationToMe;
	
	@Column(name = "verification_status")
	private VerificationStatusEnum verificaionStatus;
	
	@Column(name = "verified_by")
	private UUID verifiedBy;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UUID getRelativeUserId() {
		return relativeUserId;
	}

	public void setRelativeUserId(UUID relativeUserId) {
		this.relativeUserId = relativeUserId;
	}

	public RelationToMeEnum getRelationToMe() {
		return relationToMe;
	}

	public void setRelationToMe(RelationToMeEnum relationToMe) {
		this.relationToMe = relationToMe;
	}

	public VerificationStatusEnum getVerificaionStatus() {
		return verificaionStatus;
	}

	public void setVerificaionStatus(VerificationStatusEnum verificaionStatus) {
		this.verificaionStatus = verificaionStatus;
	}

	public UUID getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(UUID verifiedBy) {
		this.verifiedBy = verifiedBy;
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
	
	public UserRelationToMeMapBean toBean() {
		return new UserRelationToMeMapBean()
				.setId(relativeUserId)
				.setRelativeUserId(relativeUserId)
				.setRelationToMe(relationToMe)
				.setVerificaionStatus(verificaionStatus)
				.setVerifiedBy(verifiedBy)
				.setCreatedOn(createdOn.toLocalDateTime())
				.setUpdatedOn(updatedOn.toLocalDateTime());			
	}

	@Override
	public String toString() {
		return "\nUserRelationToMe [ " +
				"userId=" + id + 
				", user=" + user + 
				", relativeUserId=" + relativeUserId + 
				", relationToMe=" + relationToMe + 
				", verificaionStatus=" + verificaionStatus + 
				", verifiedBy=" + verifiedBy + 
				", createdOn=" + createdOn + 
				", updatedOn=" + updatedOn + 
				"]";
	}
	
}
