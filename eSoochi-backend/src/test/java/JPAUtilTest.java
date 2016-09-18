import edu.iu.mobiperv.esoochi.dao.entity.UserGroup;
import edu.iu.mobiperv.esoochi.dao.entity.InventoryItem;
import edu.iu.mobiperv.esoochi.dao.entity.User;
import edu.iu.mobiperv.esoochi.util.JPAUtil;

public class JPAUtilTest {
	
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
		
		group.getUsers().add(user);
		group.getUsers().add(user2);
		group.getItems().add(item);
		
		// save entities
		JPAUtil.saveEntity(user);
		JPAUtil.saveEntity(user2);
		JPAUtil.saveEntity(group);
		JPAUtil.saveEntity(item);
	}
	
	public void findUser() {
		User user = JPAUtil.findUser("04a6d1e7-883b-4fea-a3f5-11e586c38db7");
		System.out.println(user);
	}
	
	public void findUserGroup() {
		UserGroup ug = JPAUtil.findUserGroup("257f410c-699b-48f0-b1e2-c82781db118a");
		System.out.println(ug.getName());
		for(InventoryItem item : ug.getItems()) {
			System.out.println("Item name: " + item.getItemName());
		}
	}
	
	public static void main(String[] args) {
		JPAUtilTest test = new JPAUtilTest();
//		test.createEntities();
		test.findUser();
		test.findUserGroup();
	}

}
