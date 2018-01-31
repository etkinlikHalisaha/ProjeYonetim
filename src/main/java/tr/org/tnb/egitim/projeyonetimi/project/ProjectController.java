package tr.org.tnb.egitim.projeyonetimi.project;

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
public class ProjectController implements Serializable {
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private CommonDao commonDao;
	
	private Project project;
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void createDesignProject() {
		this.project = new DesignProject();
	}
	public void createQualityProject() {
		this.project = new QualityProject();
	}
	
	public void cancel() {
		this.project = null;
	}
	
	public void save() {
		try {
			if (this.project.getId() == null) {
				commonDao.save(this.project);
			}
			else {
				commonDao.update(this.project);
			}
			this.project = null;
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
			commonDao.delete(project);
			project = null;
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
		lazyDataModel = new ProjectLazyDataModel();
	}
	
	private class ProjectLazyDataModel extends LazyDataModel<BaseEntity> {
		@Override
		public List<BaseEntity> load(int first, int pageSize, String sortField,
				SortOrder sortOrder, Map<String, Object> filters) {
			QueryResult qr = projectDao.getList(first, pageSize, sortField, sortOrder, filters);
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
