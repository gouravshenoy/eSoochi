package edu.iu.mobiperv.esoochi.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.iu.mobiperv.esoochi.dao.entity.User;
import edu.iu.mobiperv.esoochi.rest.jaxb.ObjectFactory;
import edu.iu.mobiperv.esoochi.util.JPAUtil;
import edu.iu.mobiperv.esoochi.util.JaxbTransformer;

@Path("user")
public class UserResource {

	ObjectFactory factory = new ObjectFactory();
	Log logger = LogFactory.getLog(UserResource.class);
	
	@Path("{userId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response findUser(@PathParam("userId") String userId) {
		ResponseBuilder builder;
		try {
			logger.info("UserResource.findUser | userId: " + userId);
			User user = JPAUtil.findUser(userId);
			builder = Response.ok(JaxbTransformer.getUserJaxb(user));
		} catch(Exception ex) {
			logger.error("Exception creating user: " + ex, ex);
			builder = Response.serverError().entity("{\"error\": \"" + ex.getMessage() + "\"}");
		}
		return builder.build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response createUser(edu.iu.mobiperv.esoochi.rest.jaxb.User user) {
		ResponseBuilder builder;
		try {
			logger.info("UserResource.createUser | user: " + user);
			User userEntity = new User();
			userEntity.setGoogleId(user.getGoogleId());
			userEntity.setEmailAddress(user.getEmailAddress());
			userEntity.setPhotoUrl(user.getPhotoUrl());
			userEntity.setFullName(user.getFullName());
			
			JPAUtil.saveEntity(userEntity);
			logger.info("Successfully added user record to db");
			builder = Response.ok(JaxbTransformer.getUserJaxb(userEntity));
		} catch(Exception ex) {
			logger.error("Exception creating user: " + ex, ex);
			builder = Response.serverError().entity("{\"error\": \"" + ex.getMessage() + "\"}");
		}
		return builder.build();
	}
}
