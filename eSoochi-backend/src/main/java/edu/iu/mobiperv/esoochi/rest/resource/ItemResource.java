package edu.iu.mobiperv.esoochi.rest.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.iu.mobiperv.esoochi.dao.entity.User;
import edu.iu.mobiperv.esoochi.dao.entity.UserGroup;
import edu.iu.mobiperv.esoochi.rest.jaxb.InventoryItem;
import edu.iu.mobiperv.esoochi.rest.jaxb.ObjectFactory;
import edu.iu.mobiperv.esoochi.util.JPAUtil;
import edu.iu.mobiperv.esoochi.util.JaxbTransformer;

/**
 * The Class ItemResource.
 */
@Path("item")
public class ItemResource {

	/** The factory. */
	ObjectFactory factory = new ObjectFactory();
	
	/** The logger. */
	Log logger = LogFactory.getLog(ItemResource.class);
	
	/** The request. */
	@Context 
	HttpServletRequest request;
	
	/**
	 * Creates the item.
	 *
	 * @param item the item
	 * @return the response
	 */
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response createItem(InventoryItem item) {
		ResponseBuilder builder;
		try {
			logger.info("ItemResource.createItem | item: " + item);
			edu.iu.mobiperv.esoochi.dao.entity.InventoryItem invItem = new edu.iu.mobiperv.esoochi.dao.entity.InventoryItem();
			invItem.setItemName(item.getItemName());
			invItem.setItemAddress(item.getAddress());
			invItem.setAddressLatitude(item.getLatitude());
			invItem.setAddressLongitude(item.getLongitude());
			
			// fetch the group
			UserGroup group = JPAUtil.findUserGroup(item.getAddedInGroupId());
			if(group == null) {
				throw new Exception("Group with ID: " + item.getAddedInGroupId() + ", not found!");
			}
			
			// fetch the user
			User user = JPAUtil.findUser(item.getAddedByUserId());
			if(user == null) {
				throw new Exception("User with ID: " + item.getAddedByUserId() + ", not found!");
			}
			
			// set the user & group
			invItem.setAddedByUser(user);
			invItem.setAddedInGroup(group);
			
			// add item to group
			group.getItems().add(invItem);
			
			// update group & save item
			JPAUtil.saveEntity(group);
			JPAUtil.saveEntity(invItem);
			
			logger.info("Successfully added inventory item record to db");
			builder = Response.ok(JaxbTransformer.getItemJaxb(invItem));
		} catch(Exception ex) {
			logger.error("Exception creating inventory-item: " + ex, ex);
			builder = Response.serverError().entity("{\"error\": \"" + ex.getMessage() + "\"}");
		}
		return builder.build();	
	}
	
	/**
	 * Sets the item purchased.
	 *
	 * @param itemId the item id
	 * @return the response
	 */
	@Path("{itemId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response setItemPurchased(@PathParam("itemId") String itemId) {
		ResponseBuilder builder;
		try {
			logger.info("ItemResource.setItemPurchased | itemId: " + itemId);
			
			String userId = request.getHeader("user-id");
			if(userId == null) {
				throw new Exception("No <user-id> header present, cannot proceed!");
			}
			
			// fetch the user
			User user = JPAUtil.findUser(userId);
			if(user == null) {
				throw new Exception("User with ID: " + userId + ", not found!");
			}
			
			// fetch the item
			edu.iu.mobiperv.esoochi.dao.entity.InventoryItem item = JPAUtil.findInventoryItem(itemId);
			if(item == null) {
				throw new Exception("Item with ID: " + itemId + ", not found!");
			}
			item.setPurchasedByUser(user);
			
			// saving item
			logger.info("ItemResource.setItemPurchased | Updating item in db");
			JPAUtil.saveEntity(item);
			
			logger.info("Successfully set purchasedByUser for item: " + item.getItemName() + ", to user: " + user.getFullName());
			builder = Response.ok(JaxbTransformer.getItemJaxb(item));
		} catch(Exception ex) {
			logger.error("Exception creating user-group: " + ex, ex);
			builder = Response.serverError().entity("{\"error\": \"" + ex.getMessage() + "\"}");
		}
		return builder.build();
	}
}
