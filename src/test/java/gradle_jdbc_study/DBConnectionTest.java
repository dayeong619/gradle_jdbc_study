package gradle_jdbc_study;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import gradle_jdbc_study.jdbc.ConnectionProvider;

public class DBConnectionTest {
	static final Logger log = LogManager.getLogger();
	
	@Test
	public void testConnection() {
		try {
			Connection con = ConnectionProvider.getConnection();
			log.trace("com = "+con);
			Assert.assertNotNull(con); //널이 아니면 성공했당
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 정보를 확인하세요!");
		}
	}

}
