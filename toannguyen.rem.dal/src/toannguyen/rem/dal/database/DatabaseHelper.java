package toannguyen.rem.dal.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import toannguyen.rem.entity.Entity;

public abstract class DatabaseHelper {
	
	private static final String USE_REM_DATABASE = "use estate_manager";
	private static final String CONNECTION_NAME = "jdbc:mysql://localhost:3306/";
	private static final String CONNECTION_ID = "root";
	private static final String CONNECTION_PASSWORD = "toannguyen";
	
//	private static final String USE_REM_DATABASE = "use estate_manager";
//	private static final String CONNECTION_NAME = "jdbc:mysql://mysql8.db4free.net:3307/estate_manager" + "?useUnicode=true&characterEncoding=UTF8";
//	private static final String CONNECTION_ID = "toannguyen";
//	private static final String CONNECTION_PASSWORD = "toannguyen";
	
	protected Connection con;

	public DatabaseHelper() throws ClassNotFoundException, SQLException {
		PreparedStatement stmt = null;
		Class.forName(com.mysql.jdbc.Driver.class.getName());
		con = DriverManager.getConnection(CONNECTION_NAME, CONNECTION_ID, CONNECTION_PASSWORD);
		try {
			stmt = con.prepareStatement(USE_REM_DATABASE);
			stmt.executeQuery();
		} finally {
			stmt.close();
		}

	}

	public void closeConnection() throws SQLException {
		if (con != null) {
	 		con.close();
		}
	}

	public List<Entity> queryAll(String tableName) throws Exception {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<Entity> result = new ArrayList<Entity>();
		try {
			stmt = con.prepareStatement("SELECT * FROM " + tableName);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Entity entity = getEntityFromResultSet(rs);
				result.add(entity);
			}
			return result;
		} finally {
			if (rs.isAfterLast()) {
				rs.close();
				stmt.close();
			}
		}
	}
	
	public Entity queryByID(String tableName, String idColumnName, int id) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(
					"SELECT * FROM " + tableName + " WHERE " + idColumnName + " = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			rs.next();
			Entity result = getEntityFromResultSet(rs);
			return result;
		} finally {
			closeQuery(stmt, rs);
		}
	}
	
	public void deleteByID(String tableName, String idColumnName, int id) throws SQLException, ClassNotFoundException, IOException {
		String query = new String("DELETE FROM " + tableName + " WHERE " + idColumnName + " = " + id + ";");
		executeUpdate(query);
	}

	protected abstract Entity getEntityFromResultSet(ResultSet resultSet) throws Exception;

	protected void executeUpdate(String query) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			stmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			if (con != null) {
				con.rollback();
			}
			throw e;
		} finally {
			con.setAutoCommit(true);
			closeQuery(stmt, rs);
		}
	}
	
	protected void closeQuery(PreparedStatement stmt, ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
	}
}
