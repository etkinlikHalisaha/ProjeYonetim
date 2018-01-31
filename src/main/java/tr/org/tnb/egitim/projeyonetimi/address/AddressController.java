package tr.org.tnb.egitim.projeyonetimi.address;

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
public class AddressController implements Serializable {
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private CommonDao commonDao;
	
	private Address address;
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void create() {
		this.address = new Address();
	}
	
	public void cancel() {
		this.address = null;
	}
	
	public void save() {
		try {
			if (this.address.isValid()) {
				if (this.address.getId() == null) {
					commonDao.save(this.address);
				}
				else {
					commonDao.update(this.address);
				}
				this.address = null;
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Save succeeded :)", null));
			}
			else {
				FacesContext.getCurrentInstance().validationFailed();
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Address is not valid!", 
								"Please enter some information!"));
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot save!", e.getMessage()));
		}
	}
	
	public void delete() {
		try {
			commonDao.delete(address);
			address = null;
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
		lazyDataModel = new AddressLazyDataModel();
	}
	
	private class AddressLazyDataModel extends LazyDataModel<BaseEntity> {
		@Override
		public List<BaseEntity> load(int first, int pageSize, String sortField,
				SortOrder sortOrder, Map<String, Object> filters) {
			QueryResult qr = addressDao.getList(first, pageSize, sortField, sortOrder, filters);
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
