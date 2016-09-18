package edu.iu.mobiperv.esoochi.dao.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class User {

	@Id
	String id;
	
	@Column
	String googleId;
	
	@Column
	String photoUrl;
	
	@Column
	String fullName;
	
	@Column
	String emailAddress;
	
	@Column
	Date createdAt;

	@Column
	Date updatedAt;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name="USERGROUP_USER", 
	joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="id"), 
	inverseJoinColumns=@JoinColumn(name="GROUP_ID", referencedColumnName="id"))
	Set<UserGroup> groups = new HashSet<UserGroup>();
	
	public User() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Set<UserGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<UserGroup> groups) {
		this.groups = groups;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
	void createdAt() {
		this.createdAt = this.updatedAt = new Date();
	}

	/**
	 * Updated at.
	 */
	@PreUpdate
	void updatedAt() {
		this.updatedAt = new Date();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", googleId=" + googleId + ", photoUrl=" + photoUrl + ", fullName=" + fullName
				+ ", emailAddress=" + emailAddress + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", groups=" + groups + "]";
	}
	
}