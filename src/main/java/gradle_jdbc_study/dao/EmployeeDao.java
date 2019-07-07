package gradle_jdbc_study.dao;

import java.util.List;

import gradle_jdbc_study.dto.Employee;

public interface EmployeeDao {
	List<Employee> selectEmployeeByAll();
	Employee selectEmployeeByNo(Employee employee);
	int insertEmployee(Employee employee);
	int deleteEmployee(Employee employee);
	int updateEmployee(Employee employee);
	
	//소속 부서별 사원
	List<Employee> selectEmployeeByDno(int dno);
}
