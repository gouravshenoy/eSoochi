package edu.iu.mobiperv.esoochi.util;

import javax.ws.rs.core.MediaType;

import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.json.JSONObject;

import edu.iu.mobiperv.esoochi.rest.jaxb.User;

public class APIUtil {
	
	/** The client. */
	private static RestClient client;
	
	/** The client config. */
	private static ClientConfig clientConfig;
	
	/** The Constant URL. */
	public static String URL = "http://54.85.51.216:8085/esoochi/rest";

	static {
		// client config
		clientConfig = new org.apache.wink.client.ClientConfig();
		clientConfig.connectTimeout(30000);
		clientConfig.readTimeout(30000);

		// initialize client
		client = new org.apache.wink.client.RestClient(clientConfig);
	}

	/**
	 * Do post.
	 *
	 * @param json the json
	 * @return the client response
	 */
	public static ClientResponse doPost(String URL, JSONObject json) {
		Resource resource = client.resource(URL);
		ClientResponse response = resource.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(json.toString());
		return response;
	}
	
	public static ClientResponse getUser(String userId){
		Resource resource = client.resource(URL + "/user/" + userId);
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	public static ClientResponse getGroup(String groupId){
		Resource resource = client.resource(URL + groupId);
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	public static void main(String[] args) {
		ClientResponse resp = APIUtil.getUser("0263b7a3-1eac-4cf8-931d-735ecfdb9f3f");
		System.out.println(resp.getStatusCode());
		System.out.println(resp.getEntity(User.class).getGroups());
	}

}
