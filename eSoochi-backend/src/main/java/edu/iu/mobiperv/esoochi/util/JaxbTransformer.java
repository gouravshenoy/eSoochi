package edu.iu.mobiperv.esoochi.util;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import edu.iu.mobiperv.esoochi.rest.jaxb.User;
import edu.iu.mobiperv.esoochi.rest.jaxb.UserGroup;
import edu.iu.mobiperv.esoochi.rest.jaxb.InventoryItem;
import edu.iu.mobiperv.esoochi.rest.jaxb.ObjectFactory;

/**
 * The Class JaxbTransformer.
 */
public class JaxbTransformer {
	
	/** The factory. */
	static ObjectFactory factory = new ObjectFactory();
	
	/**
	 * Gets the user jaxb.
	 *
	 * @param userEntity the user entity
	 * @return the user jaxb
	 */
	public static User getUserJaxb(edu.iu.mobiperv.esoochi.dao.entity.User userEntity) {
		User user = factory.createUser();
		if(userEntity != null) {
			user.setId(userEntity.getId());
			user.setGoogleId(userEntity.getGoogleId());
			user.setPhotoUrl(userEntity.getPhotoUrl());
			user.setFullName(userEntity.getFullName());
			user.setEmailAddress(userEntity.getEmailAddress());
			user.setCreatedAt(getXMLGregorianCalendar(userEntity.getCreatedAt()));
			user.setUpdatedAt(getXMLGregorianCalendar(userEntity.getUpdatedAt()));
			
			if(userEntity.getGroups() != null) {
				for(edu.iu.mobiperv.esoochi.dao.entity.UserGroup group : userEntity.getGroups()) {
					user.getGroups().add(getUserGroupJaxb(group));
				}
			}
		}
		
		return user;
	}
	
	/**
	 * Gets the user group jaxb.
	 *
	 * @param groupEntity the group entity
	 * @return the user group jaxb
	 */
	public static UserGroup getUserGroupJaxb(edu.iu.mobiperv.esoochi.dao.entity.UserGroup groupEntity) {
		UserGroup group = factory.createUserGroup();
		if(groupEntity != null) {
			group.setId(groupEntity.getId());
			group.setName(groupEntity.getName());
			group.setCreatedAt(getXMLGregorianCalendar(groupEntity.getCreatedAt()));
			group.setUpdatedAt(getXMLGregorianCalendar(groupEntity.getUpdatedAt()));
			
			if(groupEntity.getItems() != null) {
				for(edu.iu.mobiperv.esoochi.dao.entity.InventoryItem item : groupEntity.getItems()) {
					group.getItems().add(getItemJaxb(item));
				}
			}
		}
		
		return group;
	}
	
	/**
	 * Gets the item jaxb.
	 *
	 * @param itemEntity the item entity
	 * @return the item jaxb
	 */
	public static InventoryItem getItemJaxb(edu.iu.mobiperv.esoochi.dao.entity.InventoryItem itemEntity) {
		InventoryItem item = factory.createInventoryItem();
		if(item != null) {
			item.setItemId(itemEntity.getItemId());
			item.setAddedByUserName(itemEntity.getAddedByUser().getFullName());
			item.setAddedByUserId(itemEntity.getAddedByUser().getId());
			if(itemEntity.getPurchasedByUser() != null) {
				item.setPurchasedByUserName(itemEntity.getPurchasedByUser().getFullName());
				item.setPurchasedByUserId(itemEntity.getPurchasedByUser().getId());
			}
			
			item.setAddedInGroupName(itemEntity.getAddedInGroup().getName());
			item.setAddedInGroupId(itemEntity.getAddedInGroup().getId());
			item.setAddress(itemEntity.getItemAddress());
			item.setItemName(itemEntity.getItemName());
			item.setLatitude(itemEntity.getAddressLatitude());
			item.setLongitude(itemEntity.getAddressLongitude());
			
			item.setCreatedAt(getXMLGregorianCalendar(itemEntity.getCreatedAt()));
			item.setUpdatedAt(getXMLGregorianCalendar(itemEntity.getUpdatedAt()));
		}
		
		return item;
	}
	
	
	/**
	 * Gets the XML gregorian calendar.
	 *
	 * @param date the date
	 * @return the XML gregorian calendar
	 */
	@SuppressWarnings("finally")
	public static XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
		XMLGregorianCalendar calendar = null ;
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(date);
			calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			return calendar;
		}
	}

}
