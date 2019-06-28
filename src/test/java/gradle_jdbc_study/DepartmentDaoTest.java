package gradle_jdbc_study;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import gradle_jdbc_study.dao.DepartmentDao;
import gradle_jdbc_study.daoimpl.DepartmentDaoImpl;
import gradle_jdbc_study.dto.Department;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentDaoTest {
	static final Logger log = LogManager.getLogger();
	static DepartmentDao dao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		dao = new DepartmentDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		dao = null;
	}

	@Test
	public void test01SelectDepartmentByAll() {
		List<Department> lists = dao.selectDepartmentByAll();
		for(Department d : lists) {
			log.trace(d);
		}
		Assert.assertNotEquals(0, lists.size());
	}

	
	
}










