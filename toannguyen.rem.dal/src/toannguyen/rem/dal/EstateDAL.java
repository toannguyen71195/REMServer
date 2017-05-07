package toannguyen.rem.dal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import toannguyen.rem.dal.database.EstateDatabaseHelper;
import toannguyen.rem.entity.EstateEntity;

public class EstateDAL extends EntityDAL {
	private static EstateDAL _instance;

	protected EstateDAL() {
		// hide constructor
	}

	public static EstateDAL getInstance() {
		if (_instance == null) {
			_instance = new EstateDAL();
		}
		return _instance;
	}

	public List<EstateEntity> getAll() throws ClassNotFoundException, SQLException, IOException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryAllEstate();
		} finally {
			dbh.closeConnection();
		}
	}
}
