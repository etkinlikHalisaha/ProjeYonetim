package tr.org.tnb.egitim.projeyonetimi.employee;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import tr.org.tnb.egitim.projeyonetimi.BaseEntity;
import tr.org.tnb.egitim.projeyonetimi.address.Address;
import tr.org.tnb.egitim.projeyonetimi.department.Department;
import tr.org.tnb.egitim.projeyonetimi.project.Project;

@Entity
@Table(name = "t_employee")
// @OptimisticLocking(type = OptimisticLockType.VERSION)
// @DynamicUpdate(value = true)
public class Employee extends BaseEntity {
	
	private String name;
	private Long salary;
	private Employee manager;
	private List<Employee> directedEmployeeList = new ArrayList<>();
	private List<Phone> phoneList = new ArrayList<>();
	private Department department;
	private Address address;
	private List<Project> projectList = new ArrayList<>();
	//private Date lastUpdate;
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSalary() {
		return salary;
	}
	public void setSalary(Long salary) {
		this.salary = salary;
	}
	
	@ManyToOne(optional = true)
	public Employee getManager() {
		return manager;
	}
	public void setManager(Employee manager) {
		this.manager = manager;
	}
	
	@OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
	public List<Employee> getDirectedEmployeeList() {
		return directedEmployeeList;
	}
	public void setDirectedEmployeeList(List<Employee> directedEmployeeList) {
		this.directedEmployeeList = directedEmployeeList;
	}
	public void addDirectedEmployee(Employee employee) {
		if (!this.directedEmployeeList.contains(employee)) {
			this.directedEmployeeList.add(employee);
		}
	}
	public void removeDirectedEmployee(Employee employee) {
		this.directedEmployeeList.remove(employee);
	}
	
	@ElementCollection
	@CollectionTable(name = "t_employee_phone")
	public List<Phone> getPhoneList() {
		return phoneList;
	}
	public void setPhoneList(List<Phone> phoneList) {
		this.phoneList = phoneList;
	}
	public void addPhone(Phone phone) {
		if (!this.phoneList.contains(phone)) {
			this.phoneList.add(phone);
		}
	}
	public void removePhone(Phone phone) {
		this.phoneList.remove(phone);
	}
	
	@ManyToOne(optional = true)
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	/*
	@Version
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	*/
	
	@ManyToOne(optional = true)
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	@ManyToMany(mappedBy = "employeeList")
	public List<Project> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}
	
	@Transient
	public String getEmployeeInfo() {
		return name + " - Salary: " + salary;  
	}
	
	public void increaseSalaryBy(int percent) {
		this.salary += this.salary * percent / 100;
	}
}
