package toannguyen.rem.dal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import toannguyen.rem.dal.database.DatabaseHelper;
import toannguyen.rem.dal.mapping.UserColumn;
import toannguyen.rem.entity.Entity;

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

	public List<Entity> getAll(DatabaseHelper dbh) throws ClassNotFoundException, SQLException, IOException {
		return super.getAll(UserColumn.TABLE_NAME, dbh);
	}
	
	public Entity getUserByID(DatabaseHelper dbh, int id) throws ClassNotFoundException, SQLException, IOException {
		return super.getByID(UserColumn.TABLE_NAME, UserColumn.ID.getColumnName(), id, dbh);
	}
}
