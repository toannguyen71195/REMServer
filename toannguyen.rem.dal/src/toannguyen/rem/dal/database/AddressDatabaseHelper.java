package toannguyen.rem.dal.database;

import java.io.IOException;
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
				resultSet.getString(AddressColumn.STREET.getColumnName()), 
				resultSet.getString(AddressColumn.ADDRESS.getColumnName()));
	}
	
	public AddressEntity getAddressByID(int id) throws ClassNotFoundException, SQLException, IOException {
		return (AddressEntity) super.queryByID(AddressColumn.TABLE_NAME, AddressColumn.ID.getColumnName(), id);
	}

}
