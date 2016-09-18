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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class UserGroup {
	
	@Id
	String id;
	
	@Column
	String name;
	
	@ManyToMany(mappedBy = "groups", cascade = CascadeType.MERGE)
	Set<User> users = new HashSet<User>();
	
	@OneToMany(mappedBy = "addedInGroup", cascade = CascadeType.MERGE)
	Set<InventoryItem> items = new HashSet<InventoryItem>();

	@Column
	Date createdAt;

	@Column
	Date updatedAt;
	
	public UserGroup() {
		this.id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<InventoryItem> getItems() {
		return items;
	}

	public void setItems(Set<InventoryItem> items) {
		this.items = items;
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
		return "UserGroup [id=" + id + ", name=" + name + ", users=" + users + ", items=" + items + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
