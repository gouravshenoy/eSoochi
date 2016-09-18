package edu.iu.mobiperv.esoochi.rest.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
import edu.iu.mobiperv.esoochi.rest.jaxb.ObjectFactory;
import edu.iu.mobiperv.esoochi.util.JPAUtil;
import edu.iu.mobiperv.esoochi.util.JaxbTransformer;

@Path("group")
public class GroupResource {

	ObjectFactory factory = new ObjectFactory();
	Log logger = LogFactory.getLog(UserResource.class);
	
	@Context 
	HttpServletRequest request;
	
	@Path("{groupId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response findGroup(@PathParam("groupId") String groupId) {
		ResponseBuilder builder;
		UserGroup userGroup = JPAUtil.findUserGroup(groupId);
		edu.iu.mobiperv.esoochi.rest.jaxb.UserGroup groupJaxb = JaxbTransformer.getUserGroupJaxb(userGroup);
		
		builder = Response.ok(groupJaxb);
		return builder.build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response createUserGroup(edu.iu.mobiperv.esoochi.rest.jaxb.UserGroup userGroup) {
		ResponseBuilder builder;
		try {
			logger.info("GroupResource.createUserGroup | userGroup: " + userGroup);
			
			String userId = request.getHeader("user-id");
			if(userId == null) {
				throw new Exception("No <user-id> header present, cannot proceed!");
			}
			
			UserGroup group = new UserGroup();
			group.setName(userGroup.getName());
			
			// fetch the user
			User user = JPAUtil.findUser(userId);
			if(user == null) {
				throw new Exception("User with ID: " + userId + ", not found!");
			}
			
			user.getGroups().add(group);
			group.getUsers().add(user);
			
			// saving user
			logger.info("GroupResource.createUserGroup | Updating user with group details");
			JPAUtil.saveEntity(user);
			
			// saving group
			logger.info("GroupResource.createUserGroup | Saving group to db");
			JPAUtil.saveEntity(group);
			
			logger.info("Successfully added userGroup record to db");
			builder = Response.ok(JaxbTransformer.getUserGroupJaxb(group));
		} catch(Exception ex) {
			logger.error("Exception creating user-group: " + ex, ex);
			builder = Response.serverError().entity("{\"error\": \"" + ex.getMessage() + "\"}");
		}
		return builder.build();
	}
	
	@Path("{groupId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response addUserToGroup(@PathParam("groupId") String groupId) {
		ResponseBuilder builder;
		try {
			logger.info("GroupResource.addUserToGroup | groupId: " + groupId);
			
			String userId = request.getHeader("user-id");
			if(userId == null) {
				throw new Exception("No <user-id> header present, cannot proceed!");
			}
			
			// fetch the group
			UserGroup group = JPAUtil.findUserGroup(groupId);
			if(group == null) {
				throw new Exception("Group with ID: " + groupId + ", not found!");
			}
			
			// fetch the user
			User user = JPAUtil.findUser(userId);
			if(user == null) {
				throw new Exception("User with ID: " + userId + ", not found!");
			}
			
			user.getGroups().add(group);
			group.getUsers().add(user);
			
			// saving user
			logger.info("GroupResource.addUserToGroup | Updating user with group details");
			JPAUtil.saveEntity(user);
			
			// saving group
			logger.info("GroupResource.addUserToGroup | Saving group to db");
			JPAUtil.saveEntity(group);
			
			logger.info("Successfully added user: " + user.getFullName() + ", to userGroup: " + group.getName() + " in db");
			builder = Response.ok(JaxbTransformer.getUserGroupJaxb(group));
		} catch(Exception ex) {
			logger.error("Exception creating user-group: " + ex, ex);
			builder = Response.serverError().entity("{\"error\": \"" + ex.getMessage() + "\"}");
		}
		return builder.build();
	}
}
