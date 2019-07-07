package gradle_jdbc_study.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gradle_jdbc_study.dao.DepartmentDao;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.jdbc.ConnectionProvider;
import gradle_jdbc_study.jdbc.LogUtil;

public class DepartmentDaoImpl implements DepartmentDao {
	
	@Override
	public List<Department> selectDepartmentByAll() {
		List<Department> lists = new ArrayList<Department>();
		String sql = "select deptno, deptname, floor from department";
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			LogUtil.prnLog(pstmt);
			while(rs.next()) {
				lists.add(getDepartment(rs));
			}
		} catch (SQLException e) {
			LogUtil.prnLog(e);
		} 
		return lists;
	}
	
	private Department getDepartment(ResultSet rs) throws SQLException {
		return new Department(rs.getInt("deptno"), 
				              rs.getString("deptname"), 
				              rs.getInt("floor"));
	}
	
	@Override
	public Department selectDepartmentByNo(Department dept) {
		Department searchDept = null;
		String sql = "select deptno, deptname, floor from department where deptno = ?";
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, dept.getDeptNo());
			LogUtil.prnLog(pstmt);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					searchDept = getDepartment(rs);
				}
			}
		} catch (SQLException e) {
			LogUtil.prnLog(e);
		} 
		return searchDept;
	}

	@Override
	public int insertDepartment(Department dept) {
		String sql = "insert into department(deptno, deptname, floor) values(?, ?, ?)";
		int res = -1;
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setInt(1, dept.getDeptNo());
			pstmt.setString(2, dept.getDeptName());
			pstmt.setInt(3, dept.getFloor());
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			LogUtil.prnLog(e);
		}
		return res;
	}

	@Override
	public int deleteDepartment(Department dept) {
		String sql = "delete from department where deptno=?";
		int res = -1;
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setInt(1, dept.getDeptNo());
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			LogUtil.prnLog(e);
		}
		return res;
	}

	@Override
	public int updateDepartment(Department dept) {
		String sql = "update department set deptname=?, floor=? where deptno=?;";
		int res = -1;
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setString(1, dept.getDeptName());
			pstmt.setInt(2, dept.getFloor());
			pstmt.setInt(3, dept.getDeptNo());
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			LogUtil.prnLog(e);
		}
		return res;
	}

}
