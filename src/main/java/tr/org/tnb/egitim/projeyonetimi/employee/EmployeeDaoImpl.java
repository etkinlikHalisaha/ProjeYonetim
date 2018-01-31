package tr.org.tnb.egitim.projeyonetimi.employee;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.org.tnb.egitim.projeyonetimi.QueryResult;

@Service("employeeDao")
@Transactional(readOnly = true)
public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Secured(value = "ROLE_ADMIN")
	public QueryResult getList(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
		for (String key : filters.keySet()) {
			criteria.add(Restrictions.ilike(key, (String) filters.get(key), MatchMode.START));
		}
		criteria.setFetchMode("phoneList", FetchMode.JOIN);

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

}
