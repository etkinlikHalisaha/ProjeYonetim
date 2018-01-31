package tr.org.tnb.egitim.projeyonetimi.address;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import tr.org.tnb.egitim.projeyonetimi.QueryResult;

public interface AddressDao {
	QueryResult getList(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters);	
	List<Address> findByFirstLetters(String firstLetters);
}
