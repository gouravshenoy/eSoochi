package edu.iu.mobiperv.esoochi.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import edu.iu.mobiperv.esoochi.dao.entity.User;
import edu.iu.mobiperv.esoochi.dao.entity.UserGroup;

public class JPAUtil {

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
	
	public static User findUser(String userId) {
		// Connection details loaded from persistence.xml to create EntityManagerFactory.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-esoochi");

		EntityManager em = emf.createEntityManager();
		
		User user = (User) em.find(User.class, userId);
		user.getGroups(); // because of lazy load
		
		// Closing connection.
		em.close();
		emf.close();
		
		return user;
	}
	
	public static UserGroup findUserGroup(String groupId) {
		// Connection details loaded from persistence.xml to create EntityManagerFactory.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-esoochi");

		EntityManager em = emf.createEntityManager();
		
		UserGroup group = (UserGroup) em.find(UserGroup.class, groupId);
		group.getUsers(); // because of lazy load
		group.getItems(); // because of lazy load
		
		// Closing connection.
		em.close();
		emf.close();
		
		return group;
	}
}
