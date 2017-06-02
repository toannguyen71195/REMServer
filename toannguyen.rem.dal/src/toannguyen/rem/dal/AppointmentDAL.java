package toannguyen.rem.dal;

import java.sql.SQLException;
import java.util.List;

import toannguyen.rem.dal.database.AppointmentDatabaseHelper;
import toannguyen.rem.entity.AppointmentEntity;

public class AppointmentDAL extends EntityDAL {
	private static AppointmentDAL _instance;

	protected AppointmentDAL() {
		// hide constructor
	}

	public static AppointmentDAL getInstance() {
		if (_instance == null) {
			_instance = new AppointmentDAL();
		}
		return _instance;
	}

	public List<AppointmentEntity> getAll(int userId) throws Exception {
		AppointmentDatabaseHelper databaseHelper = null;
		try {
			databaseHelper = new AppointmentDatabaseHelper();
			return databaseHelper.getListAppointment(userId);
		} finally {
			if (databaseHelper != null) {
				databaseHelper.closeConnection();
			}
		}
	}

	public AppointmentEntity updateStatus(int apptId, int status, String name, String note) throws Exception {
		AppointmentDatabaseHelper databaseHelper = null;
		try {
			databaseHelper = new AppointmentDatabaseHelper();
			return databaseHelper.updateStatus(apptId, status, name, note);
		} finally {
			if (databaseHelper != null) {
				databaseHelper.closeConnection();
			}
		}
	}

	public AppointmentEntity updateStatus(int apptId, int status) throws Exception {
		AppointmentDatabaseHelper databaseHelper = null;
		try {
			databaseHelper = new AppointmentDatabaseHelper();
			return databaseHelper.updateStatus(apptId, status);
		} finally {
			if (databaseHelper != null) {
				databaseHelper.closeConnection();
			}
		}
	}

	public void book(AppointmentEntity entity) throws SQLException, ClassNotFoundException {
		AppointmentDatabaseHelper databaseHelper = null;
		try {
			databaseHelper = new AppointmentDatabaseHelper();
			databaseHelper.book(entity);
		} finally {
			if (databaseHelper != null) {
				databaseHelper.closeConnection();
			}
		}
	}
}
