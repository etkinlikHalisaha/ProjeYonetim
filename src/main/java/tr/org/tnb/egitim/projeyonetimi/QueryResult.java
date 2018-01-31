package tr.org.tnb.egitim.projeyonetimi;

import java.io.Serializable;
import java.util.List;

public class QueryResult implements Serializable {
	private List<BaseEntity> list;
	private int rowCount;
	
	public QueryResult() {
	}
	public QueryResult(List<BaseEntity> list, int rowCount) {
		this.list = list;
		this.rowCount = rowCount;
	}

	public List<BaseEntity> getList() {
		return list;
	}
	public void setList(List<BaseEntity> list) {
		this.list = list;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	
}
