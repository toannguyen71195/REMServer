package toannguyen.rem.dal.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toannguyen.rem.entity.Entity;

public class DataDatabaseHelper extends DatabaseHelper {

		public DataDatabaseHelper() throws ClassNotFoundException, SQLException {
			super();
		}

		@Override
		protected Entity getEntityFromResultSet(ResultSet resultSet) throws Exception {
			return null;
		}

		public List<String> getWard(int id) throws SQLException {
			// SELECT * FROM data_address where DistrictID = 1;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			List<String> result = new ArrayList<>();
			try {
				StringBuilder builder = new StringBuilder();
				builder.append("SELECT * FROM data_address where DistrictID = ");
				builder.append(id + ";");
				stmt = con.prepareStatement(builder.toString());
				rs = stmt.executeQuery();
				while (rs.next()) {
					result.add(rs.getString("Name"));
				}
				return result;
			} finally {
				closeQuery(stmt, rs);
			}
		}
}
