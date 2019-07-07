package gradle_jdbc_study.dao;

import java.util.List;

import gradle_jdbc_study.dto.Department;

public interface DepartmentDao {
	List<Department> selectDepartmentByAll();
	Department selectDepartmentByNo(Department dept);
	int insertDepartment(Department dept);
	int deleteDepartment(Department dept);
	int updateDepartment(Department dept);
}
