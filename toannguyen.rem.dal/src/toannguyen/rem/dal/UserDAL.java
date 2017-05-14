package toannguyen.rem.dal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import toannguyen.rem.dal.database.UserDatabaseHelper;
import toannguyen.rem.dal.mapping.UserColumn;
import toannguyen.rem.entity.Entity;
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

	public List<Entity> getAll() throws ClassNotFoundException, SQLException, IOException {
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

	public Entity getUserByID(int id) throws ClassNotFoundException, SQLException, IOException {
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
}
