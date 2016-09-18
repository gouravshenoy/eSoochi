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

@Entity
public class InventoryItem {
	
	@Id
	String itemId;
	
	@Column
	String itemName;
	
	@Column
	@ManyToOne
	@JoinColumn(name = "addedInGroup")
	UserGroup addedInGroup;
	
	@Column
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "addedByUser")
	User addedByUser;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "purchasedByUser")
	User purchasedByUser;
	
	@Column
	Date createdAt;

	@Column
	Date updatedAt;
	
	@Column
	String itemAddress;
	
	@Column
	double addressLatitude;
	
	@Column
	double addressLongitude;
	
	public InventoryItem() {
		this.itemId = UUID.randomUUID().toString();
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public UserGroup getAddedInGroup() {
		return addedInGroup;
	}

	public void setAddedInGroup(UserGroup addedInGroup) {
		this.addedInGroup = addedInGroup;
	}

	public User getAddedByUser() {
		return addedByUser;
	}

	public void setAddedByUser(User addedByUser) {
		this.addedByUser = addedByUser;
	}

	public User getPurchasedByUser() {
		return purchasedByUser;
	}

	public void setPurchasedByUser(User purchasedByUser) {
		this.purchasedByUser = purchasedByUser;
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

	public String getItemAddress() {
		return itemAddress;
	}

	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}
	
	public double getAddressLatitude() {
		return addressLatitude;
	}

	public void setAddressLatitude(double addressLatitude) {
		this.addressLatitude = addressLatitude;
	}

	public double getAddressLongitude() {
		return addressLongitude;
	}

	public void setAddressLongitude(double addressLongitude) {
		this.addressLongitude = addressLongitude;
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
		return "InventoryItem [itemId=" + itemId + ", itemName=" + itemName + ", addedInGroup=" + addedInGroup
				+ ", addedByUser=" + addedByUser + ", purchasedByUser=" + purchasedByUser + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
}
