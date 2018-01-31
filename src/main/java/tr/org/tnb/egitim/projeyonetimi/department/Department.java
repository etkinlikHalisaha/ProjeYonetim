package tr.org.tnb.egitim.projeyonetimi.department;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import tr.org.tnb.egitim.projeyonetimi.BaseEntity;
import tr.org.tnb.egitim.projeyonetimi.employee.Employee;

@Entity
@Table(name = "t_department")
public class Department extends BaseEntity {
	private String name;
	private List<Employee> employeeList = new ArrayList<>();
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy = "department")
	public List<Employee> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	
}
