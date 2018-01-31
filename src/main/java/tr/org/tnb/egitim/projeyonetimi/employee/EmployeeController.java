package tr.org.tnb.egitim.projeyonetimi.employee;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tr.org.tnb.egitim.projeyonetimi.BaseEntity;
import tr.org.tnb.egitim.projeyonetimi.CommonDao;
import tr.org.tnb.egitim.projeyonetimi.QueryResult;
import tr.org.tnb.egitim.projeyonetimi.address.Address;
import tr.org.tnb.egitim.projeyonetimi.address.AddressDao;
import tr.org.tnb.egitim.projeyonetimi.department.Department;
import tr.org.tnb.egitim.projeyonetimi.security.SessionController;
import tr.org.tnb.egitim.util.JsfUtils;

@Controller
@Scope("view")
public class EmployeeController implements Serializable {
	
	static Logger logger = Logger.getLogger(EmployeeController.class);
	
	@Autowired
	private SessionController sessionController;
	
	@Autowired
	private transient EmployeeDao employeeDao;
	
	@Autowired
	private transient CommonDao commonDao;

	@Autowired
	private transient AddressDao addressDao;
	
	private Employee employee;
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = (Employee) commonDao.getById(employee.getId(), Employee.class);
		this.departmentList = commonDao.getAll(Department.class);
		JsfUtils.clearComponents();
	}

	public void create() {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating employee...");
		}
		this.employee = new Employee();
		if (logger.isInfoEnabled()) {
			logger.info("Getting departmentList");
		}
		this.departmentList = commonDao.getAll(Department.class);
		JsfUtils.clearComponents();
		logger.debug("...OK");
	}
	
	public void cancel() {
		this.employee = null;
	}
	
	public void save() {
		try {
			if (this.employee.getId() == null) {
				commonDao.save(this.employee);
			}
			else {
				commonDao.update(this.employee);
			}
			this.employee = null;
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Save succeeded :)", null));
		}
		catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().validationFailed();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot save!", e.getMessage()));
		}
	}
	
	public void delete() {
		try {
			commonDao.delete(employee);
			employee = null;
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
		this.lazyDataModel = new EmployeeLazyDataModel();
		this.managerLazyDataModel = new EmployeeLazyDataModel();
		this.phone = new Phone();
	}
	
	private class EmployeeLazyDataModel extends LazyDataModel<BaseEntity> {
		@Override
		public List<BaseEntity> load(int first, int pageSize, String sortField,
				SortOrder sortOrder, Map<String, Object> filters) {
			QueryResult qr = employeeDao.getList(first, pageSize, sortField, sortOrder, filters);
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
	
	
	private Phone phone;

	public Phone getPhone() {
		return phone;
	}

	public void addPhone() {
		this.employee.addPhone(this.phone);
		this.phone = new Phone();
	}
	
	public void removePhone(Phone phone) {
		this.employee.removePhone(phone);
	}
	
	
	private List<BaseEntity> departmentList;

	public List<BaseEntity> getDepartmentList() {
		return departmentList;
	}
	
	
	public List<Address> completeAddress(String firstLetters) {
		return addressDao.findByFirstLetters(firstLetters);
	}
	
	private Address address;

	public Address getAddress() {
		return address;
	}
	
	public void createAddress() {
		this.address = new Address();
	}
	public void cancelAddress() {
		this.address = null;
	}
	public void saveAddress() {
		try {
			if (this.address.isValid()) {
				commonDao.save(this.address);
				this.employee.setAddress(this.address);
				this.address = null;
			}
			else {
				FacesContext.getCurrentInstance().validationFailed();
				FacesContext.getCurrentInstance().addMessage("dialogForm:addressMessage", 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Address is not valid!", 
								"Please enter something!"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot save address!", e.getMessage()));
		}
	}
	
	
	private boolean selectingManager;

	public boolean isSelectingManager() {
		return selectingManager;
	}
	
	public void startSelectingManager() {
		this.selectingManager = true;
	}
	public void stopSelectingManager() {
		this.selectingManager = false;
	}
	
	private LazyDataModel<BaseEntity> managerLazyDataModel;

	public LazyDataModel<BaseEntity> getManagerLazyDataModel() {
		return managerLazyDataModel;
	}
	
}
