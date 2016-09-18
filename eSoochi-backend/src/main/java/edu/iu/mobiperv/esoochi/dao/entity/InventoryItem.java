package edu.iu.mobiperv.esoochi.dao.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * The Class InventoryItem.
 */
@Entity
public class InventoryItem {
	
	/** The item id. */
	@Id
	String itemId;
	
	/** The item name. */
	@Column
	String itemName;
	
	/** The added in group. */
	@Column
	@ManyToOne
	@JoinColumn(name = "addedInGroup")
	UserGroup addedInGroup;
	
	/** The added by user. */
	@Column
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "addedByUser")
	User addedByUser;
	
	/** The purchased by user. */
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "purchasedByUser")
	User purchasedByUser;
	
	/** The created at. */
	@Column
	Date createdAt;

	/** The updated at. */
	@Column
	Date updatedAt;
	
	/** The item address. */
	@Column
	String itemAddress;
	
	/** The address latitude. */
	@Column
	double addressLatitude;
	
	/** The address longitude. */
	@Column
	double addressLongitude;
	
	/**
	 * Instantiates a new inventory item.
	 */
	public InventoryItem() {
		this.itemId = UUID.randomUUID().toString();
	}

	/**
	 * Gets the item id.
	 *
	 * @return the item id
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * Sets the item id.
	 *
	 * @param itemId the new item id
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * Gets the item name.
	 *
	 * @return the item name
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Sets the item name.
	 *
	 * @param itemName the new item name
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * Gets the added in group.
	 *
	 * @return the added in group
	 */
	public UserGroup getAddedInGroup() {
		return addedInGroup;
	}

	/**
	 * Sets the added in group.
	 *
	 * @param addedInGroup the new added in group
	 */
	public void setAddedInGroup(UserGroup addedInGroup) {
		this.addedInGroup = addedInGroup;
	}

	/**
	 * Gets the added by user.
	 *
	 * @return the added by user
	 */
	public User getAddedByUser() {
		return addedByUser;
	}

	/**
	 * Sets the added by user.
	 *
	 * @param addedByUser the new added by user
	 */
	public void setAddedByUser(User addedByUser) {
		this.addedByUser = addedByUser;
	}

	/**
	 * Gets the purchased by user.
	 *
	 * @return the purchased by user
	 */
	public User getPurchasedByUser() {
		return purchasedByUser;
	}

	/**
	 * Sets the purchased by user.
	 *
	 * @param purchasedByUser the new purchased by user
	 */
	public void setPurchasedByUser(User purchasedByUser) {
		this.purchasedByUser = purchasedByUser;
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
	 * Gets the item address.
	 *
	 * @return the item address
	 */
	public String getItemAddress() {
		return itemAddress;
	}

	/**
	 * Sets the item address.
	 *
	 * @param itemAddress the new item address
	 */
	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}
	
	/**
	 * Gets the address latitude.
	 *
	 * @return the address latitude
	 */
	public double getAddressLatitude() {
		return addressLatitude;
	}

	/**
	 * Sets the address latitude.
	 *
	 * @param addressLatitude the new address latitude
	 */
	public void setAddressLatitude(double addressLatitude) {
		this.addressLatitude = addressLatitude;
	}

	/**
	 * Gets the address longitude.
	 *
	 * @return the address longitude
	 */
	public double getAddressLongitude() {
		return addressLongitude;
	}

	/**
	 * Sets the address longitude.
	 *
	 * @param addressLongitude the new address longitude
	 */
	public void setAddressLongitude(double addressLongitude) {
		this.addressLongitude = addressLongitude;
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
		return "InventoryItem [itemId=" + itemId + ", itemName=" + itemName + ", addedInGroup=" + addedInGroup
				+ ", addedByUser=" + addedByUser + ", purchasedByUser=" + purchasedByUser + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
}
