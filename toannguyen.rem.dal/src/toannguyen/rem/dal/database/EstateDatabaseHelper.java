package toannguyen.rem.dal.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toannguyen.rem.dal.mapping.AddressColumn;
import toannguyen.rem.dal.mapping.EstateColumn;
import toannguyen.rem.dal.mapping.EstateTypeColumn;
import toannguyen.rem.entity.AddressEntity;
import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.EstateEntity;
import toannguyen.rem.entity.UserEntity;

public class EstateDatabaseHelper extends DatabaseHelper {

	private static int STATUS_AVAILABLE = 1;

	public EstateDatabaseHelper() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	protected Entity getEntityFromResultSet(ResultSet resultSet)
			throws SQLException, ClassNotFoundException, IOException {
		// EstateEntity(UserEntity owner, AddressEntity address, boolean status,
		// String type)
		UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper();
		try {
			UserEntity userEntity = userDatabaseHelper
					.getUserByID(resultSet.getInt(EstateColumn.OWNER_ID.getColumnName()));
			if (resultSet.getInt(EstateColumn.STATUS_ID.getColumnName()) == STATUS_AVAILABLE) {
				return new EstateEntity(resultSet.getInt(EstateColumn.ID.getColumnName()),
						resultSet.getString(EstateColumn.NAME.getColumnName()), userEntity,
						getAddressEntityFromResultSet(resultSet), true,
						resultSet.getString(EstateTypeColumn.NAME.getColumnName()));
			} else {
				return new EstateEntity(resultSet.getInt(EstateColumn.ID.getColumnName()),
						resultSet.getString(EstateColumn.NAME.getColumnName()), userEntity,
						getAddressEntityFromResultSet(resultSet), false,
						resultSet.getString(EstateTypeColumn.NAME.getColumnName()));
			}
		} finally {
			if (userDatabaseHelper != null) {
				userDatabaseHelper.closeConnection();
			}
		}
	}

	private AddressEntity getAddressEntityFromResultSet(ResultSet resultSet) throws SQLException {
		return new AddressEntity(resultSet.getInt(AddressColumn.ID.getColumnName()),
				resultSet.getString(AddressColumn.CITY.getColumnName()),
				resultSet.getString(AddressColumn.DISTRICT.getColumnName()),
				resultSet.getString(AddressColumn.WARD.getColumnName()),
				resultSet.getString(AddressColumn.STREET.getColumnName()),
				resultSet.getString(AddressColumn.ADDRESS.getColumnName()));
	}

	public List<EstateEntity> queryAllEstate() throws SQLException, ClassNotFoundException, IOException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EstateEntity> result = new ArrayList<>();
		try {
			// SELECT * FROM estate e left join address a on e.AddressID =
			// a.AddressID
			// left join estate_type t on e.EstateTypeID = t.EstateTypeID;
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(EstateColumn.TABLE_NAME + " e left join ");
			builder.append(AddressColumn.TABLE_NAME + " a on e.");
			builder.append(EstateColumn.ADDRESS_ID + " = a.");
			builder.append(AddressColumn.ID);
			builder.append(" left join ");
			builder.append(EstateTypeColumn.TABLE_NAME + " t on e.");
			builder.append(EstateColumn.ESTATE_TYPE_ID + " = t.");
			builder.append(EstateTypeColumn.ID + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				EstateEntity entity = (EstateEntity) getEntityFromResultSet(rs);
				result.add(entity);
			}
			return result;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}
}
