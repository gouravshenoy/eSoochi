package edu.iu.mobiperv.esoochi.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.iu.mobiperv.esoochi.dao.entity.InventoryItem;
import edu.iu.mobiperv.esoochi.dao.entity.User;
import edu.iu.mobiperv.esoochi.dao.entity.UserGroup;

/**
 * The Class JPAUtil.
 */
public class JPAUtil {

	/**
	 * Save entity.
	 *
	 * @param entity the entity
	 */
	/* (non-Javadoc)
	 * @see edu.sga.apex.dao.EntityDAO#saveEntity(java.lang.Object)
	 */
	public static void saveEntity(Object entity) {

		// Connection details loaded from persistence.xml to create EntityManagerFactory.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-esoochi");

		EntityManager em = emf.createEntityManager();

		// Creating a new transaction.
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		// Persisting the entity object.
		//if( ! em.contains(entity) );
		em.merge(entity);

		// Committing transaction.
		tx.commit();

		// Closing connection.
		em.close();
		emf.close();
	}
	
	/**
	 * Find user.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	public static User findUser(String userId) {
		// Connection details loaded from persistence.xml to create EntityManagerFactory.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-esoochi");

		EntityManager em = emf.createEntityManager();
		
		User user = (User) em.find(User.class, userId);
		if(user != null) {
			user.getGroups(); // because of lazy load
		}
		
		// Closing connection.
		em.close();
		emf.close();
		
		return user;
	}
	
	/**
	 * Find user group.
	 *
	 * @param groupId the group id
	 * @return the user group
	 */
	public static UserGroup findUserGroup(String groupId) {
		// Connection details loaded from persistence.xml to create EntityManagerFactory.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-esoochi");

		EntityManager em = emf.createEntityManager();
		
		UserGroup group = (UserGroup) em.find(UserGroup.class, groupId);
		if(group != null) {
			group.getUsers(); // because of lazy load
			group.getItems(); // because of lazy load
		}
		
		// Closing connection.
		em.close();
		emf.close();
		
		return group;
	}
	
	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	@SuppressWarnings("unchecked")
	public static List<User> getUsers() {
		// Connection details loaded from persistence.xml to create EntityManagerFactory.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-esoochi");

		EntityManager em = emf.createEntityManager();
		
		Query query = em.createQuery("SELECT u FROM User u");
		List<User> users = query.getResultList();
		
		// Closing connection.
		em.close();
		emf.close();
		
		return users;
	}
	
	/**
	 * Find inventory item.
	 *
	 * @param itemId the item id
	 * @return the inventory item
	 */
	public static InventoryItem findInventoryItem(String itemId) {
		// Connection details loaded from persistence.xml to create EntityManagerFactory.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-esoochi");

		EntityManager em = emf.createEntityManager();
		
		InventoryItem item = (InventoryItem) em.find(InventoryItem.class, itemId);
		
		// Closing connection.
		em.close();
		emf.close();
		
		return item;
	}
	
}
