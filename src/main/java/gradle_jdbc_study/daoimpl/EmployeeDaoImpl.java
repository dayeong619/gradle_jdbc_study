package gradle_jdbc_study.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import gradle_jdbc_study.dao.EmployeeDao;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.dto.Title;
import gradle_jdbc_study.jdbc.ConnectionProvider;
import gradle_jdbc_study.jdbc.LogUtil;

public class EmployeeDaoImpl implements EmployeeDao {

	@Override
	public List<Employee> selectEmployeeByAll() {
		List<Employee> lists = new ArrayList<Employee>();
		String sql = "select e.empno, e.empname, tno, tname, e.manager, m.empname as m_name, e.salary, e.gender, deptno, deptname, floor, e.hire_date "
				+ "from employee e left join title t on e.title = t.tno join department d on e.dno = d.deptno join employee m on e.manager = m.empno "
				+ "union "
				+ "select e.empno, e.empname, tno, tname, e.manager, null, e.salary, e.gender, deptno, deptname, floor, e.hire_date "
				+ "from employee e left join title t on e.title = t.tno join department d on e.dno = d.deptno where manager is null "
				+ "order by empno" ;
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			LogUtil.prnLog(pstmt);
			while(rs.next()) {
				lists.add(getEmployee(rs));
			}
		} catch (SQLException e) {
			LogUtil.prnLog(e);
		} 
		return lists;
	}

	private Employee getEmployee(ResultSet rs) throws SQLException {
		return new Employee(rs.getInt("empNo"), rs.getString("empName"), new Title(rs.getInt("tno"), rs.getString("tname")), 
				new Employee(rs.getInt("manager"), rs.getString("m_name")), rs.getInt("salary"), rs.getBoolean("gender"), 
				new Department(rs.getInt("deptno"), rs.getString("deptname"), rs.getInt("floor")), 
				rs.getDate("hire_date"));
	}

	@Override
	public Employee selectEmployeeByNo(Employee employee) {
		Employee searchEmployee = null;
		String sql = "select e.empno, e.empname, tno, tname, e.manager, m.empname as m_name, e.salary, e.gender, deptno, deptname, floor, e.hire_date "
				+ "from employee e left join title t on e.title = t.tno "
				+ "join department d on e.dno = d.deptno "
				+ "join employee m on e.manager = m.empno where e.empno = ?";
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, employee.getEmpNo());
			LogUtil.prnLog(pstmt);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					searchEmployee = getEmployee(rs);
				}
			}
		} catch (SQLException e) {
			LogUtil.prnLog(e);
		} 
		return searchEmployee;
	}

	@Override
	public int insertEmployee(Employee employee) {
		String sql = null;
		// 사장인 경우 직속상사 = null
		if (employee.getManager() != null) {
			sql = "insert into employee(empno, empname, title, salary, gender, dno, hire_date, manager) values(?, ?, ?, ?, ?, ?, ?, ?)";
		}else {
			sql = "insert into employee(empno, empname, title, salary, gender, dno, hire_date, manager) values(?, ?, ?, ?, ?, ?, ?, null)";
		}
		int res = -1;
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, employee.getEmpNo());
			pstmt.setString(2, employee.getEmpName());
			pstmt.setInt(3, employee.getTitle().getTitleNo());
			pstmt.setInt(4, employee.getSalary());
			pstmt.setBoolean(5, employee.isMale());
			pstmt.setInt(6, employee.getDno().getDeptNo());
			//java.util.Date ->java.sql.TimeStamp로 변환 후 적용 
			pstmt.setTimestamp(7, new Timestamp(employee.getHireDate().getTime()));
			
			// 사장인 경우 직속상사 = null
			if (employee.getManager() != null) {
				pstmt.setInt(8, employee.getManager().getEmpNo());
			}
			
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
		}catch (SQLException e) {
			LogUtil.prnLog(e);
		} 
		return res;
	}

	@Override
	public int deleteEmployee(Employee employee) {
		String sql = "delete from  employee where empno=?";
		int res = -1;
		try(Connection conn =  ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, employee.getEmpNo());
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
		}catch (SQLException e) {
			LogUtil.prnLog(e);
		} 
		return res;
	}

	@Override
	public int updateEmployee(Employee employee) {
		String sql = "update employee set empname=?, title=?, manager=?, salary=?, gender=?, dno=?, hire_date=? where empno=?";
		int res = -1;
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, employee.getEmpName());
			pstmt.setInt(2, employee.getTitle().getTitleNo());
			pstmt.setInt(3, employee.getManager().getEmpNo());
			pstmt.setInt(4, employee.getSalary());
			pstmt.setBoolean(5, employee.isMale());
			pstmt.setInt(6, employee.getDno().getDeptNo());
			pstmt.setTimestamp(7, new Timestamp(employee.getHireDate().getTime()));
			pstmt.setInt(8, employee.getEmpNo());
			
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
		}catch (SQLException e) {
			LogUtil.prnLog(e);
		} 
		return res;
	}

	@Override
	public List<Employee> selectEmployeeByDno(int dno) {
		String sql = "SELECT empno, empname FROM employee where dno=? or manager is null";
		List<Employee> lists = null;
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setInt(1, dno);
			LogUtil.prnLog(pstmt);
			try(ResultSet rs = pstmt.executeQuery()){
				if (rs.next()) {
					lists = new ArrayList<Employee>();
					do {
						lists.add(getManager(rs));
					}while(rs.next());
				}
			}
		}catch (SQLException e) {
			LogUtil.prnLog(e);
		} 
		return lists;
	}

	private Employee getManager(ResultSet rs) throws SQLException {
		return new Employee(rs.getInt("empno"), rs.getString("empname"));
	}

}
