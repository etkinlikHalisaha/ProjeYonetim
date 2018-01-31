package tr.org.tnb.egitim.projeyonetimi.address;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.org.tnb.egitim.projeyonetimi.QueryResult;

@Service("addressDao")
@Transactional(readOnly = true)
public class AddressDaoImpl implements AddressDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult getList(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Address.class);
		for (String key : filters.keySet()) {
			criteria.add(Restrictions.ilike(key, (String) filters.get(key), MatchMode.START));
		}

		QueryResult qr = new QueryResult();
		criteria.setProjection(Projections.rowCount());
		qr.setRowCount(((Long) criteria.uniqueResult()).intValue());
		
		criteria.setProjection(null);
		criteria.setFirstResult(first);
		criteria.setFetchSize(pageSize);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (sortField != null) {
			if (sortOrder.equals(SortOrder.ASCENDING)) {
				criteria.addOrder(Order.asc(sortField));
			}
			else if (sortOrder.equals(SortOrder.DESCENDING)) {
				criteria.addOrder(Order.desc(sortField));
			}
		}
		qr.setList(criteria.list());
		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Address> findByFirstLetters(String firstLetters) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Address.class);
		criteria.add(
			Restrictions.disjunction(
				Restrictions.ilike("street", firstLetters, MatchMode.START),
				Restrictions.ilike("city", firstLetters, MatchMode.START),
				Restrictions.ilike("state", firstLetters, MatchMode.START),
				Restrictions.ilike("zip", firstLetters, MatchMode.START)
				));
		criteria.addOrder(Order.asc("state"));
		criteria.addOrder(Order.asc("city"));
		criteria.addOrder(Order.asc("street"));
		criteria.addOrder(Order.asc("zip"));
		return criteria.list();
	}
	
	

}
