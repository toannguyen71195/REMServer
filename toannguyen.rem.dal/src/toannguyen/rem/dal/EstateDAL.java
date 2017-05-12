package toannguyen.rem.dal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import toannguyen.rem.dal.database.EstateDatabaseHelper;
import toannguyen.rem.entity.EstateDetailEntity;
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

	public EstateEntity getEstateByOwnerID(int id) throws ClassNotFoundException, SQLException, IOException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryEstateByOwnerID(id);
		} finally {
			dbh.closeConnection();
		}
	}

	public List<EstateEntity> getNewEstate(int count) throws ClassNotFoundException, SQLException, IOException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryNewEstate(count);
		} finally {
			dbh.closeConnection();
		}
	}

	public EstateDetailEntity getEstateDetail(int estateId) throws ClassNotFoundException, SQLException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryEstateDetail(estateId);
		} finally {
			dbh.closeConnection();
		}
	}
}
