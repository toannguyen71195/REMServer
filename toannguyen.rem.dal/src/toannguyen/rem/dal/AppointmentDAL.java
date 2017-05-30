package toannguyen.rem.dal;

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

//	public List<AppointmentEntity> getAll(int userId) {
//		AppointmentDatabaseHelper databaseHelper = new AppointmentDatabaseHelper();
//		try {
//			
//		} finally {
//			
//		}
//	}
}
