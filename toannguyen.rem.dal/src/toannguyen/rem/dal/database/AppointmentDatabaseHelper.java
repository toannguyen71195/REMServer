package toannguyen.rem.dal.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import toannguyen.rem.entity.Entity;

public class AppointmentDatabaseHelper extends DatabaseHelper {

	public AppointmentDatabaseHelper() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	protected Entity getEntityFromResultSet(ResultSet resultSet) throws Exception {

		return null;
	}

}
