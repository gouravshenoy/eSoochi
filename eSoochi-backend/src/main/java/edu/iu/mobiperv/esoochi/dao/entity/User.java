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

/**
 * The Class User.
 */
@Entity
public class User {

	/** The id. */
	@Id
	String id;
	
	/** The google id. */
	@Column
	String googleId;
	
	/** The photo url. */
	@Column
	String photoUrl;
	
	/** The full name. */
	@Column
	String fullName;
	
	/** The email address. */
	@Column
	String emailAddress;
	
	/** The created at. */
	@Column
	Date createdAt;

	/** The updated at. */
	@Column
	Date updatedAt;
	
	/** The groups. */
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name="USERGROUP_USER", 
	joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="id"), 
	inverseJoinColumns=@JoinColumn(name="GROUP_ID", referencedColumnName="id"))
	Set<UserGroup> groups = new HashSet<UserGroup>();
	
	/**
	 * Instantiates a new user.
	 */
	public User() {
		this.id = UUID.randomUUID().toString();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the google id.
	 *
	 * @return the google id
	 */
	public String getGoogleId() {
		return googleId;
	}

	/**
	 * Sets the google id.
	 *
	 * @param googleId the new google id
	 */
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	/**
	 * Gets the photo url.
	 *
	 * @return the photo url
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * Sets the photo url.
	 *
	 * @param photoUrl the new photo url
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	/**
	 * Gets the full name.
	 *
	 * @return the full name
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the full name.
	 *
	 * @param fullName the new full name
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Gets the email address.
	 *
	 * @return the email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the email address.
	 *
	 * @param emailAddress the new email address
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Gets the groups.
	 *
	 * @return the groups
	 */
	public Set<UserGroup> getGroups() {
		return groups;
	}

	/**
	 * Sets the groups.
	 *
	 * @param groups the new groups
	 */
	public void setGroups(Set<UserGroup> groups) {
		this.groups = groups;
	}
	
	/**
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt the new created at
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the updated at.
	 *
	 * @return the updated at
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * Sets the updated at.
	 *
	 * @param updatedAt the new updated at
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * Created at.
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", googleId=" + googleId + ", photoUrl=" + photoUrl + ", fullName=" + fullName
				+ ", emailAddress=" + emailAddress + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", groups=" + groups + "]";
	}
	
}
