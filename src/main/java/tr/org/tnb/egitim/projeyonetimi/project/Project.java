package tr.org.tnb.egitim.projeyonetimi.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import tr.org.tnb.egitim.projeyonetimi.BaseEntity;
import tr.org.tnb.egitim.projeyonetimi.employee.Employee;

@Entity
@Table(name = "t_project")
public abstract class Project extends BaseEntity {
	private String name;
	private List<Employee> employeeList = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany
	@JoinTable(name = "t_project_employee",
			joinColumns = @JoinColumn(name = "project_id"),
			inverseJoinColumns = @JoinColumn(name = "employee_id"))
	public List<Employee> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	public void addEmployee(Employee employee) {
		if (!this.employeeList.contains(employee)) {
			this.employeeList.add(employee);
		}
	}
	public void removeEmployee(Employee employee) {
		this.employeeList.remove(employee);
	}

	@Transient
	public abstract int getType();

	@Transient
	public boolean isDesignProject() {
		return getType() == 1;
	}

	@Transient
	public boolean isQualityProject() {
		return getType() == 2;
	}
}
