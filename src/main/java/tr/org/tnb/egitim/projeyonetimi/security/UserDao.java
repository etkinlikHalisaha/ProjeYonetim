package tr.org.tnb.egitim.projeyonetimi.security;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional(readOnly = true)
	public User getByUsername(String username) {
		return (User) sessionFactory.getCurrentSession()
			.getNamedQuery(User.NQ.getByUsername)
			.setParameter("username", username)
			.uniqueResult();
	}
}
