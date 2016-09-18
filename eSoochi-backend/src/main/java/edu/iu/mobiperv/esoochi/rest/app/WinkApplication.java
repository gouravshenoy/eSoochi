package edu.iu.mobiperv.esoochi.rest.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import edu.iu.mobiperv.esoochi.rest.resource.GroupResource;
import edu.iu.mobiperv.esoochi.rest.resource.ItemResource;
import edu.iu.mobiperv.esoochi.rest.resource.UserResource;

public class WinkApplication extends Application{

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(UserResource.class);
		classes.add(GroupResource.class);
		classes.add(ItemResource.class);
		
		return classes;
	}
}
