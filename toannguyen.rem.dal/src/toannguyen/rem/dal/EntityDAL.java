package toannguyen.rem.dal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import toannguyen.rem.dal.database.DatabaseHelper;
import toannguyen.rem.entity.Entity;

public abstract class EntityDAL {

	protected List<Entity> getAll(String tableName, DatabaseHelper dbh) throws Exception {
		List<Entity> result = null;
		try {
			result = dbh.queryAll(tableName);
			return result;
		} finally {
			if (dbh != null && result != null) {
				dbh.closeConnection();
			}
		}
	}

	protected Entity getByID(String tableName, String idColumn, int id, DatabaseHelper dbh) throws Exception {
		try {
			Entity result = dbh.queryByID(tableName, idColumn, id);
			return result;
		} finally {
			if (dbh != null) {
				dbh.closeConnection();
			}
		}
	}
}
