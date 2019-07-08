package gradle_jdbc_study.service;

import java.util.List;

import gradle_jdbc_study.dao.DepartmentDao;
import gradle_jdbc_study.dao.EmployeeDao;
import gradle_jdbc_study.dao.TitleDao;
import gradle_jdbc_study.daoimpl.DepartmentDaoImpl;
import gradle_jdbc_study.daoimpl.EmployeeDaoImpl;
import gradle_jdbc_study.daoimpl.TitleDaoImpl;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.dto.Title;

public class EmployeeDaoService {
	private EmployeeDao empDao;
	private TitleDao titleDao;
	private DepartmentDao deptDao;

	public EmployeeDaoService() {
		empDao = new EmployeeDaoImpl();
		titleDao = new TitleDaoImpl();
		deptDao = new DepartmentDaoImpl();
	}

	public List<Employee> selectEmployeeByAll() {
		return empDao.selectEmployeeByAll();
	}

	public List<Title> selectTitleByAll() {
		return titleDao.selectTitleByAll();
	}
	
	public List<Department> selectDepartmentByAll(){
		return deptDao.selectDepartmentByAll();
	}
	
	public List<Employee> selectEmployeeByDno(int dno){
		return empDao.selectEmployeeByDno(dno);
	}
	
	public int insertEmployee(Employee employee) {
		return empDao.insertEmployee(employee);
	}
	
	public int deleteEmployee(Employee employee) {
		return empDao.deleteEmployee(employee);
	}
	
	public int updateEmployee(Employee employee) {
		return empDao.updateEmployee(employee);
	}
}
