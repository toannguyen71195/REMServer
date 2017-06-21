package toannguyen.rem.dal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import toannguyen.rem.dal.database.EstateDatabaseHelper;
import toannguyen.rem.dal.database.UserDatabaseHelper;
import toannguyen.rem.dal.mapping.UserColumn;
import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.EstateEntity;
import toannguyen.rem.entity.PhotoEntity;
import toannguyen.rem.entity.UserEntity;

public class UserDAL extends EntityDAL {
	private static UserDAL _instance;

	protected UserDAL() {
		// hide constructor
	}

	public static UserDAL getInstance() {
		if (_instance == null) {
			_instance = new UserDAL();
		}
		return _instance;
	}

	public List<Entity> getAll() throws Exception {
		UserDatabaseHelper dbh = null;
		try {
			dbh = new UserDatabaseHelper();
			return super.getAll(UserColumn.TABLE_NAME, dbh);
		} finally {
			if (dbh != null) {
				dbh.closeConnection();
			}
		}
	}

	public Entity getUserByID(int id) throws Exception {
		UserDatabaseHelper dbh = null;
		try {
			dbh = new UserDatabaseHelper();
			return super.getByID(UserColumn.TABLE_NAME, UserColumn.ID.getColumnName(), id, dbh);
		} finally {
			if (dbh != null) {
				dbh.closeConnection();
			}
		}
	}

	public UserEntity logIn(String username, String password) throws ClassNotFoundException, SQLException, IOException {
		UserDatabaseHelper dbh = null;
		try {
			dbh = new UserDatabaseHelper();
			return dbh.queryLogin(username, password);
		} finally {
			if (dbh != null) {
				dbh.closeConnection();
			}
		}
	}

	public String createLoginToken(int id) throws ClassNotFoundException, SQLException {
		UserDatabaseHelper dbh = null;
		try {
			String token = DataUtils.createLoginToken();
			dbh = new UserDatabaseHelper();
			dbh.clearLoginToken(id);
			dbh.insertLoginToken(id, token);
			return token;
		} finally {
			if (dbh != null) {
				dbh.closeConnection();
			}
		}
	}

	public UserEntity logIn(String token) throws ClassNotFoundException, SQLException, IOException {
		UserDatabaseHelper dbh = null;
		try {
			dbh = new UserDatabaseHelper();
			return dbh.queryLogin(token);
		} finally {
			if (dbh != null) {
				dbh.closeConnection();
			}
		}
	}

	public UserEntity register(UserEntity entity) throws Exception {
		UserDatabaseHelper dbh = null;
		try {
			dbh = new UserDatabaseHelper();
			return dbh.insertUser(entity);
		} finally {
			if (dbh != null) {
				dbh.closeConnection();
			}
		}
	}

	public void updateNote(int userId, int estateId, String note) throws ClassNotFoundException, SQLException {
		UserDatabaseHelper dbh = null;
		try {
			dbh = new UserDatabaseHelper();
			dbh.updateNote(userId, estateId, note);
		} finally {
			if (dbh != null) {
				dbh.closeConnection();
			}
		}
	}

	public String getNote(int userId, int estateId) throws ClassNotFoundException, SQLException {
		UserDatabaseHelper dbh = null;
		try {
			dbh = new UserDatabaseHelper();
			return dbh.getNote(userId, estateId);
		} finally {
			if (dbh != null) {
				dbh.closeConnection();
			}
		}
	}
	
	public List<EstateEntity> getInterestedEstate(int userId) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryInterestedEstate(userId);
		} finally {
			dbh.closeConnection();
		}
	}
	
	public void setInterestedEstate(int userId, int estateId, int rate) throws SQLException, ClassNotFoundException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			dbh.setInterested(userId, estateId, rate);
		} finally {
			dbh.closeConnection();
		}
	}

	public List<UserEntity> searchUser(String query, int offset, int range) throws ClassNotFoundException, SQLException, IOException {
		UserDatabaseHelper dbh = new UserDatabaseHelper();
		try {
			return dbh.searchUser(query, offset, range);
		} finally {
			dbh.closeConnection();
		}
	}

	public void postPhotos(int uid, int eid, List<PhotoEntity> entities) throws Exception {
		UserDatabaseHelper dbh = new UserDatabaseHelper();
		try {
			dbh.postPhotos(uid, eid, entities);
		} finally {
			dbh.closeConnection();
		}
	}

	public List<PhotoEntity> getPhotos(int userId, int estateId) throws SQLException, ClassNotFoundException {
		UserDatabaseHelper dbh = new UserDatabaseHelper();
		try {
			return dbh.getPhotos(userId, estateId);
		} finally {
			dbh.closeConnection();
		}
	}
}
