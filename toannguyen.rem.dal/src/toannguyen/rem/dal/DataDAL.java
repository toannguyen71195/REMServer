package toannguyen.rem.dal;

import java.sql.SQLException;
import java.util.List;

import toannguyen.rem.dal.database.DataDatabaseHelper;

public class DataDAL extends EntityDAL {
	private static DataDAL _instance;

	protected DataDAL() {
		// hide constructor
	}

	public static DataDAL getInstance() {
		if (_instance == null) {
			_instance = new DataDAL();
		}
		return _instance;
	}

	public List<String> getWard(int id) throws ClassNotFoundException, SQLException {
		DataDatabaseHelper dbh = new DataDatabaseHelper();
		try {
			return dbh.getWard(id);
		} finally {
			dbh.closeConnection();
		}
	}


}