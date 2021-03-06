import edu.iu.mobiperv.esoochi.dao.entity.InventoryItem;
import edu.iu.mobiperv.esoochi.dao.entity.User;
import edu.iu.mobiperv.esoochi.dao.entity.UserGroup;
import edu.iu.mobiperv.esoochi.util.JPAUtil;

/**
 * The Class JPAUtilTest.
 */
public class JPAUtilTest {
	
	/**
	 * Creates the entities.
	 */
	public void createEntities() {
		UserGroup group = new UserGroup();
		group.setName("TestGroup");
		
		User user = new User();
		user.setFullName("John Doe");
		user.setGoogleId("xs3dsd32qsajoi2ewjo");
		user.setEmailAddress("johndoe@gmail.com");
		user.setPhotoUrl("http://google.com");
		user.getGroups().add(group);
		
		User user2 = new User();
		user2.setFullName("Alex Harp");
		user2.setGoogleId("fe3dsd32qsajoisss");
		user2.setEmailAddress("alex@gmail.com");
		user2.setPhotoUrl("http://google.com");
		user2.getGroups().add(group);
		
		InventoryItem item = new InventoryItem();
		item.setItemName("Vegetables");
		item.setAddedInGroup(group);
		item.setAddedByUser(user);
		item.setPurchasedByUser(user2);
		item.setItemAddress("Marsh, North Gourley Pike");
		item.setAddressLatitude(28.85);
		item.setAddressLongitude(-25.766667);
		
		group.getUsers().add(user);
		group.getUsers().add(user2);
		group.getItems().add(item);
		
		// save entities
		JPAUtil.saveEntity(user);
		JPAUtil.saveEntity(user2);
		JPAUtil.saveEntity(group);
		JPAUtil.saveEntity(item);
	}
	
	/**
	 * Find user.
	 */
	public void findUser() {
		User user = JPAUtil.findUser("04a6d1e7-883b-4fea-a3f5-11e586c38db7");
		System.out.println(user);
	}
	
	/**
	 * Find user group.
	 */
	public void findUserGroup() {
		UserGroup ug = JPAUtil.findUserGroup("257f410c-699b-48f0-b1e2-c82781db118a");
		System.out.println(ug.getName());
		for(InventoryItem item : ug.getItems()) {
			System.out.println("Item name: " + item.getItemName());
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
//		JPAUtilTest test = new JPAUtilTest();
//		test.createEntities();
//		test.findUser();
//		test.findUserGroup();
//		System.out.println("Creating user...");
//		User user = new User();
//		user.setFullName("Mary Com");
//		user.setGoogleId("xs3dsd32qsajoi2ewjo");
//		user.setEmailAddress("jmary@gmail.com");
//		user.setPhotoUrl("http://google.com");
//		
//		JPAUtil.saveEntity(user);
//		
//		System.out.println("Fetching user...");
//		User u = JPAUtil.findUser(user.getId());
//		
//		System.out.println("Creating group...");
//		UserGroup ug = JPAUtil.findUserGroup("754ba4de-344c-43f3-8818-52193e5a23b7");
//		ug.getUsers().add(u);
//		u.getGroups().add(ug);
//		
//		System.out.println("Saving user...");
//		JPAUtil.saveEntity(u);
//		
//		System.out.println("Saving group...");
//		JPAUtil.saveEntity(ug);
		
//		User u = JPAUtil.findUserByGoogleId("fe3dsd32qsajoisss");
//		System.out.println(u);
	}

}
