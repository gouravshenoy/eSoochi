package edu.iu.mobiperv.esoochi.dao.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * The Class UserGroup.
 */
@Entity
public class UserGroup {
	
	/** The id. */
	@Id
	String id;
	
	/** The name. */
	@Column
	String name;
	
	/** The users. */
	@ManyToMany(mappedBy = "groups", cascade = CascadeType.MERGE)
	Set<User> users = new HashSet<User>();
	
	/** The items. */
	@OneToMany(mappedBy = "addedInGroup", cascade = CascadeType.MERGE)
	Set<InventoryItem> items = new HashSet<InventoryItem>();

	/** The created at. */
	@Column
	Date createdAt;

	/** The updated at. */
	@Column
	Date updatedAt;
	
	/**
	 * Instantiates a new user group.
	 */
	public UserGroup() {
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * Sets the users.
	 *
	 * @param users the new users
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
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
	 * Gets the items.
	 *
	 * @return the items
	 */
	public Set<InventoryItem> getItems() {
		return items;
	}

	/**
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	public void setItems(Set<InventoryItem> items) {
		this.items = items;
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
		return "UserGroup [id=" + id + ", name=" + name + ", users=" + users + ", items=" + items + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
