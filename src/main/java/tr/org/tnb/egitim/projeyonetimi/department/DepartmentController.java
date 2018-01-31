package tr.org.tnb.egitim.projeyonetimi.department;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tr.org.tnb.egitim.projeyonetimi.BaseEntity;
import tr.org.tnb.egitim.projeyonetimi.CommonDao;
import tr.org.tnb.egitim.projeyonetimi.QueryResult;

@Controller
@Scope("view")
public class DepartmentController implements Serializable {
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private CommonDao commonDao;
	
	private Department department;
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public void create() {
		this.department = new Department();
	}
	
	public void cancel() {
		this.department = null;
	}
	
	public void save() {
		try {
			if (this.department.getId() == null) {
				commonDao.save(this.department);
			}
			else {
				commonDao.update(this.department);
			}
			this.department = null;
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Save succeeded :)", null));
		}
		catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot save!", e.getMessage()));
		}
	}
	
	public void delete() {
		try {
			commonDao.delete(department);
			department = null;
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Deleted.", null));
		}
		catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot delete!", e.getMessage()));
		}
	}	
	
	
	
	// --------------- List --------------------
	private LazyDataModel<BaseEntity> lazyDataModel;

	public LazyDataModel<BaseEntity> getLazyDataModel() {
		return lazyDataModel;
	}

	@PostConstruct
	public void init() {
		lazyDataModel = new DepartmentLazyDataModel();
	}
	
	private class DepartmentLazyDataModel extends LazyDataModel<BaseEntity> {
		@Override
		public List<BaseEntity> load(int first, int pageSize, String sortField,
				SortOrder sortOrder, Map<String, Object> filters) {
			QueryResult qr = departmentDao.getList(first, pageSize, sortField, sortOrder, filters);
			setRowCount(qr.getRowCount());
			return qr.getList();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public BaseEntity getRowData(String rowKey) {
			List<BaseEntity> list = (List<BaseEntity>) getWrappedData();

			for (BaseEntity be : list) {
				if (be.getId().toString().equals(rowKey))
					return be;
			}

			return null;
		}

		@Override
		public Object getRowKey(BaseEntity base) {
			return base.getId();
		}		
	}
	
	

}
