package toannguyen.rem.dal.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import toannguyen.rem.dal.mapping.AddressColumn;
import toannguyen.rem.entity.AddressEntity;
import toannguyen.rem.entity.Entity;

public class AddressDatabaseHelper extends DatabaseHelper {

	public AddressDatabaseHelper() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	protected Entity getEntityFromResultSet(ResultSet resultSet)
			throws SQLException, ClassNotFoundException, IOException {
		return new AddressEntity(resultSet.getInt(AddressColumn.ID.getColumnName()),
				resultSet.getString(AddressColumn.CITY.getColumnName()),
				resultSet.getString(AddressColumn.DISTRICT.getColumnName()),
				resultSet.getString(AddressColumn.WARD.getColumnName()),
				resultSet.getString(AddressColumn.ADDRESS.getColumnName()));
	}

	public AddressEntity getAddressByID(int id) throws Exception {
		return (AddressEntity) super.queryByID(AddressColumn.TABLE_NAME, AddressColumn.ID.getColumnName(), id);
	}

	/*
	 * throw Exception 1. Duplicate address 2. Db problem, never return null
	 */
	public AddressEntity insertAddress(AddressEntity entity) throws Exception {
		AddressEntity addressEntity = queryAddress(entity);
		StringBuilder builder = new StringBuilder();
		try {
			// if (addressEntity != null) {
			// throw new Exception("Unable to add new address: duplicate
			// address");
			// }
			builder.append("insert into ");
			builder.append(AddressColumn.TABLE_NAME);
			builder.append(" (" + AddressColumn.CITY + ",");
			builder.append(AddressColumn.DISTRICT + ",");
			builder.append(AddressColumn.WARD + ",");
			builder.append(AddressColumn.ADDRESS + ") values ('");
			builder.append(entity.getCity() + "','");
			builder.append(entity.getDistrict() + "','");
			builder.append(entity.getWard() + "','");
			builder.append(entity.getAddress() + "');");
			executeUpdate(builder.toString());
			addressEntity = queryAddress(entity);
			if (addressEntity != null) {
				return addressEntity;
			}
			throw new Exception("Error after insert address, query return empty");
		} catch (Exception e) {
			throw new Exception("Error when insert address: " + e.getMessage() + " Query: " + builder.toString());
		}
	}

	private AddressEntity queryAddress(AddressEntity entity) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuilder builder = new StringBuilder();
		builder.append("select * from ");
		builder.append(AddressColumn.TABLE_NAME + " where ");
		builder.append(AddressColumn.CITY + " = '" + entity.getCity() + "' and ");
		builder.append(AddressColumn.DISTRICT + " = '" + entity.getDistrict() + "' and ");
		builder.append(AddressColumn.WARD + " = '" + entity.getWard() + "' and ");
		builder.append(AddressColumn.ADDRESS + " = '" + entity.getAddress() + "';");
		try {
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return (AddressEntity) getEntityFromResultSet(rs);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new Exception("Error when query address: " + e.getMessage());
		} finally {
			closeQuery(stmt, rs);
		}
	}

}
