package edu.iu.mobiperv.esoochi.dao.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Group {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	
	@Column
	String groupName;
	
	@Column
	@ManyToMany
	Set<User> users = new HashSet<User>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column
	Date createdAt;

	@Column
	Date updatedAt;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
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
		return "Group [id=" + id + ", groupName=" + groupName + ", users=" + users + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

}
