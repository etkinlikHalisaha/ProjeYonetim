package tr.org.tnb.egitim.projeyonetimi;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("commonDao")
@SuppressWarnings(value = {"rawtypes", "unchecked"})
@Transactional(readOnly = true)
public class CommonDaoImpl implements CommonDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void save(BaseEntity entity) {
		sessionFactory.getCurrentSession().save(entity);
	}

	@Transactional
	public void update(BaseEntity entity) {
		sessionFactory.getCurrentSession().update(entity);
	}

	@Transactional
	public void delete(BaseEntity entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}
	
	public BaseEntity getById(Long id, Class clazz) {
		return (BaseEntity) sessionFactory.getCurrentSession().get(clazz, id);
	}
	
	public List<BaseEntity> getAll(Class clazz) {
		String query = "select o from " + clazz.getSimpleName() + " o ";
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}
}	
