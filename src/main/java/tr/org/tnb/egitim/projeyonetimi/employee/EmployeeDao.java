package tr.org.tnb.egitim.projeyonetimi.employee;

import java.util.Map;

import org.primefaces.model.SortOrder;

import tr.org.tnb.egitim.projeyonetimi.QueryResult;

public interface EmployeeDao {
	QueryResult getList(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters);	
}
