package tr.org.tnb.egitim.projeyonetimi.employee;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tr.org.tnb.egitim.projeyonetimi.CommonDao;

@ContextConfiguration(locations = "classpath:testContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SqlGroup({
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, 
				scripts = "classpath:addEmployees.sql"),
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, 
				scripts = "classpath:deleteEmployees.sql")
})
public class TestEmployeeDao {

	@Autowired
	private CommonDao commonDao;
	
	@Test
	@Transactional
	public void saveEmployee() {
		int size = commonDao.getAll(Employee.class).size();
		
		Employee employee = new Employee();
		employee.setName("Test Employee");
		employee.setSalary(3500l);
		commonDao.save(employee);
		
		assertEquals(size + 1, commonDao.getAll(Employee.class).size());
	}
	
	@Test
	public void testGetAll() {
		assertEquals(2, commonDao.getAll(Employee.class).size());
	}

}
