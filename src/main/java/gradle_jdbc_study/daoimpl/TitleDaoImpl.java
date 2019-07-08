package gradle_jdbc_study.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gradle_jdbc_study.dao.TitleDao;
import gradle_jdbc_study.dto.Title;
import gradle_jdbc_study.jdbc.ConnectionProvider;
import gradle_jdbc_study.jdbc.LogUtil;

public class TitleDaoImpl implements TitleDao {

	@Override
	public List<Title> selectTitleByAll() {
		List<Title> lists = new ArrayList<Title>();
		String sql = "select tno, tname from title";
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			LogUtil.prnLog(pstmt);
			while(rs.next()) {
				lists.add(getTitle(rs));
			}
		} catch (SQLException e) {
			LogUtil.prnLog(e);
		} 
		return lists;
	}

	private Title getTitle(ResultSet rs) throws SQLException {
		return new Title(rs.getInt("tno"), rs.getString("tname"));
	}

	@Override
	public Title selectTitleByNo(Title title) {
		Title searchDept = null;
		String sql = "select tno, tname from title where tno = ?";
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, title.getTitleNo());
			LogUtil.prnLog(pstmt);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					searchDept = getTitle(rs);
				}
			}
		} catch (SQLException e) {
			LogUtil.prnLog(e);
		} 
		return searchDept;
	}

	@Override
	public int insertTitle(Title title) {
		String sql = "insert into title(tno, tname) values(?, ?)";
		int res = -1;
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setInt(1, title.getTitleNo());
			pstmt.setString(2, title.getTitleName());
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			LogUtil.prnLog(e);
		}
		return res;
	}

	@Override
	public int deleteTitle(Title title) {
		String sql = "delete from title where tno=?";
		int res = -1;
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setInt(1, title.getTitleNo());
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			LogUtil.prnLog(e);
		}
		return res;
	}

	@Override
	public int updateTitle(Title title) {
		String sql = "update title set tname=? where tno=?;";
		int res = -1;
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setString(1, title.getTitleName());
			pstmt.setInt(2, title.getTitleNo());
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			LogUtil.prnLog(e);
		}
		return res;
	}

}
