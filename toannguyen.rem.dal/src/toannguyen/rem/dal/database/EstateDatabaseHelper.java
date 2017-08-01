package toannguyen.rem.dal.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import toannguyen.rem.dal.mapping.AddressColumn;
import toannguyen.rem.dal.mapping.BrokerEstateColumn;
import toannguyen.rem.dal.mapping.CommentColumn;
import toannguyen.rem.dal.mapping.EstateColumn;
import toannguyen.rem.dal.mapping.EstateDetailColumn;
import toannguyen.rem.dal.mapping.EstateTypeColumn;
import toannguyen.rem.dal.mapping.InterestedEstateColumn;
import toannguyen.rem.dal.mapping.NotificationColumn;
import toannguyen.rem.dal.mapping.PhotoColumn;
import toannguyen.rem.entity.AddressEntity;
import toannguyen.rem.entity.CommentEntity;
import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.EstateDetailEntity;
import toannguyen.rem.entity.EstateEntity;
import toannguyen.rem.entity.PhotoEntity;
import toannguyen.rem.entity.SearchEstateEntity;
import toannguyen.rem.entity.UserEntity;

public class EstateDatabaseHelper extends DatabaseHelper {

	private static final int PAGE_COUNT = 6;
	boolean first = true;

	public EstateDatabaseHelper() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	protected Entity getEntityFromResultSet(ResultSet resultSet) throws Exception {
		UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper();
		try {
			UserEntity userEntity = userDatabaseHelper
					.getUserByID(resultSet.getInt(EstateColumn.OWNER_ID.getColumnName()));
			int id = resultSet.getInt(EstateColumn.ID.getColumnName());
			EstateDetailEntity detailEntity = queryEstateDetail(id);
			EstateEntity estateEntity = new EstateEntity(id, resultSet.getString(EstateColumn.NAME.getColumnName()),
					userEntity, getAddressEntityFromResultSet(resultSet),
					resultSet.getInt(EstateColumn.STATUS_ID.getColumnName()) == EstateEntity.STATUS_AVAILABLE,
					resultSet.getString(EstateTypeColumn.NAME.getColumnName()),
					resultSet.getTimestamp(EstateColumn.POST_TIME.getColumnName()),
					resultSet.getTimestamp(EstateColumn.EDIT_TIME.getColumnName()),
					resultSet.getDouble(EstateColumn.PRICE.getColumnName()),
					resultSet.getDouble(EstateColumn.AREA.getColumnName()));
			estateEntity.setDetail(detailEntity);
			return estateEntity;
		} finally {
			if (userDatabaseHelper != null) {
				userDatabaseHelper.closeConnection();
			}
		}
	}

	protected Entity getEntityFromResultSet_onlyEstate(ResultSet resultSet) throws Exception {
		UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper();
		try {
			int id = resultSet.getInt(EstateColumn.ID.getColumnName());
			EstateEntity estateEntity = new EstateEntity(id, resultSet.getString(EstateColumn.NAME.getColumnName()),
					getAddressEntityFromResultSet(resultSet),
					resultSet.getInt(EstateColumn.STATUS_ID.getColumnName()) == EstateEntity.STATUS_AVAILABLE,
					resultSet.getString(EstateTypeColumn.NAME.getColumnName()),
					resultSet.getTimestamp(EstateColumn.POST_TIME.getColumnName()),
					resultSet.getTimestamp(EstateColumn.EDIT_TIME.getColumnName()),
					resultSet.getDouble(EstateColumn.PRICE.getColumnName()),
					resultSet.getDouble(EstateColumn.AREA.getColumnName()));
			return estateEntity;
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
				resultSet.getString(AddressColumn.ADDRESS.getColumnName()));
	}

	protected PhotoEntity getPhotoEntityFromResultSet(ResultSet resultSet) throws SQLException {
		return new PhotoEntity(resultSet.getInt(PhotoColumn.ID.getColumnName()),
				resultSet.getString(PhotoColumn.PHOTO.getColumnName()),
				resultSet.getInt(PhotoColumn.ESTATE_ID.getColumnName()));
	}
	
	protected CommentEntity getCommentEntityFromResultSet(ResultSet resultSet) throws SQLException {
		return new CommentEntity(resultSet.getInt(CommentColumn.ID.getColumnName()), 
				resultSet.getString(CommentColumn.MESSAGE.getColumnName()),
				resultSet.getInt(CommentColumn.USER_ID1.getColumnName()),
				resultSet.getInt(CommentColumn.USER_ID2.getColumnName()),
				resultSet.getInt(CommentColumn.ESTATE_ID.getColumnName()));
	}

	public List<EstateEntity> queryAllEstate() throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EstateEntity> result = new ArrayList<>();
		try {
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
			closeQuery(stmt, rs);
		}
	}

	public List<EstateEntity> queryEstateByOwnerID(int id) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EstateEntity> entities = new ArrayList<>();
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(EstateColumn.TABLE_NAME + " e left join ");
			builder.append(AddressColumn.TABLE_NAME + " a on e.");
			builder.append(EstateColumn.ADDRESS_ID + " = a.");
			builder.append(AddressColumn.ID);
			builder.append(" left join ");
			builder.append(EstateTypeColumn.TABLE_NAME + " t on e.");
			builder.append(EstateColumn.ESTATE_TYPE_ID + " = t.");
			builder.append(EstateTypeColumn.ID + " where e.");
			builder.append(EstateColumn.OWNER_ID + " = ");
			builder.append(id);
			builder.append(" order by " + EstateColumn.POST_TIME + " desc;");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				EstateEntity entity = (EstateEntity) getEntityFromResultSet(rs);
				entities.add(entity);
			}
			return entities;
		} finally {
			closeQuery(stmt, rs);
		}
	}
	
	public int queryEstateNumberByOwnerID(int id) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(EstateColumn.TABLE_NAME + " e ");
			builder.append(" where e.");
			builder.append(EstateColumn.OWNER_ID + " = ");
			builder.append(id);
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			int t = 0;
			while (rs.next()) {
				t++;
			}
			return t;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public List<EstateEntity> queryNewEstate(int page) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EstateEntity> result = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		try {
			builder.append("SELECT * FROM ");
			builder.append(EstateColumn.TABLE_NAME + " e left join ");
			builder.append(AddressColumn.TABLE_NAME + " a on e.");
			builder.append(EstateColumn.ADDRESS_ID + " = a.");
			builder.append(AddressColumn.ID);
			builder.append(" left join ");
			builder.append(EstateTypeColumn.TABLE_NAME + " t on e.");
			builder.append(EstateColumn.ESTATE_TYPE_ID + " = t.");
			builder.append(EstateTypeColumn.ID + " where e.");
			builder.append(EstateColumn.STATUS_ID + " = " + EstateEntity.STATUS_AVAILABLE);
			builder.append(" order by " + EstateColumn.POST_TIME + " desc");
			builder.append(" limit " + page * PAGE_COUNT + ", " + PAGE_COUNT);
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				EstateEntity entity = (EstateEntity) getEntityFromResultSet(rs);
				result.add(entity);
			}
			return result;
		} catch (Exception e) {
			throw new Exception("Error query new estates: " + e.getMessage() + ". Query: " + builder.toString());
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public EstateDetailEntity queryEstateDetail(int estateId) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(EstateDetailColumn.TABLE_NAME);
			builder.append(" where ");
			builder.append(EstateDetailColumn.ID + " = ");
			builder.append(estateId + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return (EstateDetailEntity) getEstateDetailFromResultSet(rs);
			}
			return null;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	private EstateDetailEntity getEstateDetailFromResultSet(ResultSet rs) throws Exception {
		try {
			EstateDetailEntity detailEntity = new EstateDetailEntity(rs.getInt(EstateDetailColumn.ID.getColumnName()),
					rs.getInt(EstateDetailColumn.BATHROOM.getColumnName()),
					rs.getInt(EstateDetailColumn.BEDROOM.getColumnName()),
					rs.getString(EstateDetailColumn.CONDITION.getColumnName()),
					rs.getString(EstateDetailColumn.DESCRIPTION.getColumnName()),
					rs.getInt(EstateDetailColumn.FLOOR.getColumnName()),
					rs.getDouble(EstateDetailColumn.LENGTH.getColumnName()),
					rs.getDouble(EstateDetailColumn.WIDTH.getColumnName()),
					rs.getDouble(EstateDetailColumn.LATITUDE.getColumnName()),
					rs.getDouble(EstateDetailColumn.LONGITUDE.getColumnName()));
			return detailEntity;
		} catch (Exception e) {
			throw new Exception("rs detail " + e.getMessage());
		}

	}

	public List<EstateEntity> queryInterestedEstate(int userId) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EstateEntity> entities = new ArrayList<>();
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(InterestedEstateColumn.TABLE_NAME + " i left join ");
			builder.append(EstateColumn.TABLE_NAME + " e on i.");
			builder.append(InterestedEstateColumn.ESTATE_ID + " = e.");
			builder.append(EstateColumn.ID);
			builder.append(" left join ");
			builder.append(AddressColumn.TABLE_NAME + " a on e.");
			builder.append(EstateColumn.ADDRESS_ID + " = a.");
			builder.append(AddressColumn.ID);
			builder.append(" left join ");
			builder.append(EstateTypeColumn.TABLE_NAME + " t on e.");
			builder.append(EstateColumn.ESTATE_TYPE_ID + " = t.");
			builder.append(EstateTypeColumn.ID);
			builder.append(" where i.");
			builder.append(InterestedEstateColumn.USER_ID + " = ");
			builder.append(userId);
			builder.append(" order by " + EstateColumn.POST_TIME + " desc;");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				EstateEntity entity = (EstateEntity) getEntityFromResultSet(rs);
				entities.add(entity);
			}
			return entities;
		} finally {
			closeQuery(stmt, rs);
		}
	}
	
	public int queryInterestedEstateNumber(int userId) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(InterestedEstateColumn.TABLE_NAME + " i");
			builder.append(" where i.");
			builder.append(InterestedEstateColumn.USER_ID + " = ");
			builder.append(userId);
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			int t = 0;
			while (rs.next()) {
				t++;
			}
			return t;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public int getRate(int userId, int estateId) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(InterestedEstateColumn.TABLE_NAME);
			builder.append(" where ");
			builder.append(InterestedEstateColumn.USER_ID + " = ");
			builder.append(userId);
			builder.append(" and ");
			builder.append(InterestedEstateColumn.ESTATE_ID + " = ");
			builder.append(estateId + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(InterestedEstateColumn.RATE.getColumnName());
			} else {
				return 0;
			}
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public void setInterested(int userId, int estateId, int rate) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			// check if interested => delete
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(InterestedEstateColumn.TABLE_NAME);
			builder.append(" where ");
			builder.append(InterestedEstateColumn.USER_ID + " = ");
			builder.append(userId + " and ");
			builder.append(InterestedEstateColumn.ESTATE_ID + " = ");
			builder.append(estateId + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				if (rate == 0) // delete
				{
					builder = new StringBuilder();
					builder.append("delete from " + InterestedEstateColumn.TABLE_NAME);
					builder.append(" where ");
					builder.append(InterestedEstateColumn.USER_ID + " = ");
					builder.append(userId + " and ");
					builder.append(InterestedEstateColumn.ESTATE_ID + " = ");
					builder.append(estateId + ";");
					executeUpdate(builder.toString());
				} else { // update
					builder = new StringBuilder();
					builder.append("update " + InterestedEstateColumn.TABLE_NAME);
					builder.append(" set " + InterestedEstateColumn.RATE + " = " + rate);
					builder.append(" where ");
					builder.append(InterestedEstateColumn.USER_ID + " = ");
					builder.append(userId + " and ");
					builder.append(InterestedEstateColumn.ESTATE_ID + " = ");
					builder.append(estateId + ";");
					executeUpdate(builder.toString());
				}
			} else { // insert
				builder = new StringBuilder();
				builder.append("insert into " + InterestedEstateColumn.TABLE_NAME);
				builder.append(" (" + InterestedEstateColumn.ESTATE_ID.getColumnName() + ", ");
				builder.append(InterestedEstateColumn.USER_ID + ", ");
				builder.append(InterestedEstateColumn.RATE + ") ");
				builder.append("values (" + estateId + ", " + userId + ", " + rate + ");");
				executeUpdate(builder.toString());
			}
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public String getRepresentPhoto(int estateId) throws SQLException {
		// SELECT * FROM estate e left join photo p on e.PhotoID = p.PhotoID
		// where e.EstateID = 1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM " + EstateColumn.TABLE_NAME);
			builder.append(" e left join " + PhotoColumn.TABLE_NAME + " p on e.");
			builder.append(EstateColumn.PHOTO_ID + " = p." + PhotoColumn.ID);
			builder.append(" where e." + EstateColumn.ID + " = " + estateId + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString(PhotoColumn.PHOTO.getColumnName());
			}
			return null;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public EstateEntity postEstate(EstateEntity reqEntity, int userId, boolean isBroker) throws Exception {
		// insert into address table
		AddressEntity addressEntity = null;
		AddressDatabaseHelper addressDatabaseHelper = null;
		EstateEntity rtEstate = null;
		EstateDetailEntity rtDetail = null;
		StringBuilder builder = new StringBuilder();
		try {
			addressDatabaseHelper = new AddressDatabaseHelper();
			addressEntity = addressDatabaseHelper.insertAddress(reqEntity.getAddress());
			// insert into estate (AddressID, Name, OwnerID,
			// StatusID, EstateTypeID, PostTime, Price, Area, Photo)
			// values (1, 1, 'Phòng cho thuê', 3, 1, 2, '2017-05-06 05:06:07',
			// 12000, 80, 1);
			builder.append("insert into " + EstateColumn.TABLE_NAME);
			builder.append(" (" + EstateColumn.ADDRESS_ID + ", ");
			builder.append(EstateColumn.NAME + ", ");
			builder.append(EstateColumn.OWNER_ID + ", ");
			builder.append(EstateColumn.STATUS_ID + ", ");
			builder.append(EstateColumn.ESTATE_TYPE_ID + ", ");
			builder.append(EstateColumn.POST_TIME + ", ");
			builder.append(EstateColumn.EDIT_TIME + ", ");
			builder.append(EstateColumn.PRICE + ", ");
			builder.append(EstateColumn.AREA + ") ");
			builder.append(" values (");
			builder.append("'" + addressEntity.getId() + "', ");
			builder.append("'" + reqEntity.getName() + "', ");
			builder.append(reqEntity.getOwner().getId() + ", ");
			builder.append("1, ");
			builder.append(reqEntity.getType() + ", ");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			builder.append("'" + timestamp.toString() + "', ");
			builder.append("'" + timestamp.toString() + "', ");
			builder.append(reqEntity.getPrice() + ", ");
			builder.append(reqEntity.getArea() + ");");
			executeUpdate(builder.toString());
			rtEstate = queryEstateByAddressID_onlyEstate(addressEntity.getId());
			if (rtEstate == null) {
				throw new Exception("Server error after insert estate. Query return empty.");
			}
			// insert into estate detail
			reqEntity.setId(rtEstate.getId());
			rtDetail = insertEstateDetail(reqEntity);
			if (rtDetail == null) {
				throw new Exception("Server error after insert detail. Query return empty.");
			}
			rtEstate.setDetail(rtDetail);
			if (isBroker) {
				// insert into broker table
				builder = new StringBuilder();
				builder.append("insert into " + BrokerEstateColumn.TABLE_NAME);
				builder.append(" values (");
				builder.append(userId + ", ");
				builder.append(rtEstate.getId() + ");");
				executeUpdate(builder.toString());
			}
			return rtEstate;
		} catch (Exception e) {
			if (addressEntity != null) {
				addressDatabaseHelper.deleteByID(AddressColumn.TABLE_NAME, AddressColumn.ID.getColumnName(),
						addressEntity.getId());
			}
			if (rtEstate != null) {
				deleteByID(EstateColumn.TABLE_NAME, EstateColumn.ID.getColumnName(), rtEstate.getId());
			}
			if (rtDetail != null) {
				deleteByID(EstateDetailColumn.TABLE_NAME, EstateDetailColumn.ID.getColumnName(), rtDetail.getId());
			}
			throw new Exception("Error when insert estate: " + e.getMessage() + " Query: " + builder.toString());
		} finally {
			if (addressDatabaseHelper != null) {
				addressDatabaseHelper.closeConnection();
			}
		}
	}

	private EstateEntity queryEstateByAddressID_onlyEstate(int id) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(EstateColumn.TABLE_NAME + " e left join ");
			builder.append(AddressColumn.TABLE_NAME + " a on e.");
			builder.append(EstateColumn.ADDRESS_ID + " = a.");
			builder.append(AddressColumn.ID);
			builder.append(" left join ");
			builder.append(EstateTypeColumn.TABLE_NAME + " t on e.");
			builder.append(EstateColumn.ESTATE_TYPE_ID + " = t.");
			builder.append(EstateTypeColumn.ID + " where e.");
			builder.append(EstateColumn.ADDRESS_ID + " = ");
			builder.append(id + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return (EstateEntity) getEntityFromResultSet_onlyEstate(rs);
			}
			return null;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	private EstateDetailEntity insertEstateDetail(EstateEntity entity) throws Exception {
		// insert into estate_detail(Bathroom, Bedroom, Cond, Description,
		// Floor, Length, Width) values(1, 1, 'New', 'New', 2, 5, 5);
		EstateDetailEntity detail = entity.getDetail();
		StringBuilder builder = new StringBuilder();
		builder.append("insert into ");
		builder.append(EstateDetailColumn.TABLE_NAME);
		builder.append(" (" + EstateDetailColumn.ID + ",");
		builder.append(EstateDetailColumn.BATHROOM + ",");
		builder.append(EstateDetailColumn.BEDROOM + ",");
		builder.append(EstateDetailColumn.CONDITION + ",");
		builder.append(EstateDetailColumn.DESCRIPTION + ",");
		builder.append(EstateDetailColumn.FLOOR + ",");
		builder.append(EstateDetailColumn.LENGTH + ",");
		builder.append(EstateDetailColumn.WIDTH + ",");
		builder.append(EstateDetailColumn.LATITUDE + ",");
		builder.append(EstateDetailColumn.LONGITUDE + ") values ('");
		builder.append(entity.getId() + "','");
		builder.append(detail.getBathroom() + "','");
		builder.append(detail.getBedroom() + "','");
		builder.append(detail.getCondition() + "','");
		builder.append(detail.getDescription() + "', ");
		builder.append(detail.getFloor() + ", ");
		builder.append(detail.getLength() + ", ");
		builder.append(detail.getWidth() + ", ");
		builder.append(detail.getLatitude() + ", ");
		builder.append(detail.getLongitude() + ");");
		executeUpdate(builder.toString());
		EstateDetailEntity detailEntity = queryEstateDetail(entity.getId());
		if (detailEntity != null) {
			return detailEntity;
		}
		throw new Exception("Error after insert detail: Query return null");
	}

	public EstateEntity editEstate(EstateEntity reqEntity) throws Exception {
		StringBuilder builder = new StringBuilder();
		try {
			// update address
			AddressEntity reqAddress = reqEntity.getAddress();
			builder.append("update " + AddressColumn.TABLE_NAME);
			builder.append(" set " + AddressColumn.CITY + " = '" + reqAddress.getCity() + "', ");
			builder.append(AddressColumn.DISTRICT + " = '" + reqAddress.getDistrict() + "', ");
			builder.append(AddressColumn.WARD + " = '" + reqAddress.getWard() + "', ");
			builder.append(AddressColumn.ADDRESS + " = '" + reqAddress.getAddress() + "'");
			builder.append(" where " + AddressColumn.ID + " = " + reqAddress.getId() + ";");
			executeUpdate(builder.toString());
		} catch (Exception e) {
			throw new Exception("Server error after update address: " + e.getMessage());
		}
		// update estate
		try {
			builder = new StringBuilder();
			builder.append("update " + EstateColumn.TABLE_NAME);
			builder.append(" set " + EstateColumn.NAME + " = '" + reqEntity.getName() + "', ");
			if (reqEntity.isAvailable()) {
				builder.append(EstateColumn.STATUS_ID + " = 1, ");
			} else {
				builder.append(EstateColumn.STATUS_ID + " = 2, ");
			}
			builder.append(EstateColumn.AREA + " = " + reqEntity.getArea() + ", ");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			builder.append(EstateColumn.EDIT_TIME + " = '" + timestamp.toString() + "', ");
			builder.append(EstateColumn.PRICE + " = " + reqEntity.getPrice());
			builder.append(" where " + EstateColumn.ID + " = " + reqEntity.getId() + ";");
			executeUpdate(builder.toString());
		} catch (Exception e) {
			throw new Exception("Server error after update estate: " + e.getMessage());
		}
		// update detail
		try {
			EstateDetailEntity reqDetail = reqEntity.getDetail();
			builder = new StringBuilder();
			builder.append("update " + EstateDetailColumn.TABLE_NAME);
			builder.append(" set " + EstateDetailColumn.BATHROOM + " = " + reqDetail.getBathroom() + ", ");
			builder.append(EstateDetailColumn.BEDROOM + " = " + reqDetail.getBedroom() + ", ");
			builder.append(EstateDetailColumn.CONDITION + " = '" + reqDetail.getCondition() + "', ");
			builder.append(EstateDetailColumn.DESCRIPTION + " = '" + reqDetail.getDescription() + "', ");
			builder.append(EstateDetailColumn.FLOOR + " = " + reqDetail.getFloor() + ", ");
			builder.append(EstateDetailColumn.LENGTH + " = " + reqDetail.getLength() + ", ");
			builder.append(EstateDetailColumn.WIDTH + " = " + reqDetail.getWidth());
			builder.append(" where " + EstateDetailColumn.ID + " = " + reqDetail.getId() + ";");
			executeUpdate(builder.toString());
		} catch (Exception e) {
			throw new Exception("Server error after update detail: " + e.getMessage());
		}
		return reqEntity;

	}

	public void upRepresentPhoto(PhotoEntity reqEntity, int id, Timestamp timestamp) throws Exception {
		int upID = 0;
		StringBuilder builder = new StringBuilder();
		builder = new StringBuilder();
		builder.append("select * from " + PhotoColumn.TABLE_NAME);
		builder.append(" where ");
		builder.append(PhotoColumn.PHOTO + " = '" + reqEntity.getPhoto() + "' and ");
		builder.append(PhotoColumn.ESTATE_ID + " = " + id + ";");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				upID = rs.getInt(PhotoColumn.ID.getColumnName());
			} else {
				throw new Exception("This photo cannot be found in DB, please upload again ");// +
																								// builder.toString());
			}
		} finally {
			closeQuery(stmt, rs);
		}

		builder = new StringBuilder();
		builder.append("update " + EstateColumn.TABLE_NAME);
		builder.append(" set " + EstateColumn.PHOTO_ID + " = " + upID);
		builder.append(" where " + EstateColumn.ID + " = " + id);
		executeUpdate(builder.toString());
	}

	public List<EstateEntity> queryTopRateEstate(int count) throws Exception {
		// SELECT *, SUM(i.Rate) as SumRate FROM interested_estate i
		// left join estate e on i.EstateID = e.EstateID
		// left join address a on a.AddressID = e.AddressID
		// group by i.EstateID
		// order by SumRate desc
		// limit 1;
		List<EstateEntity> entities = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT e.*, a.*, t.*, SUM(i.Rate) as SumRate FROM interested_estate i");
		builder.append(" left join estate e on i.EstateID = e.EstateID");
		builder.append(" left join address a on a.AddressID = e.AddressID");
		builder.append(" left join estate_type t on e.EstateTypeID = t.EstateTypeID");
		builder.append(" group by i.EstateID");
		builder.append(" order by SumRate desc");
		builder.append(" limit " + count + ";");
		try {
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				entities.add((EstateEntity) getEntityFromResultSet(rs));
			}
			return entities;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public void postPhotos(int id, List<PhotoEntity> entities, int avatar) throws Exception {
		if (avatar < 0 || avatar >= entities.size()) {
			throw new Exception("Represent photo index out of range");
		}
		try {
			StringBuilder builder = new StringBuilder();
			con.setAutoCommit(false);
			for (int i = 0; i < entities.size(); ++i) {
				PhotoEntity reqEntity = entities.get(i);
				builder = new StringBuilder();
				builder.append("insert into " + PhotoColumn.TABLE_NAME);
				builder.append(" (" + PhotoColumn.PHOTO + ", ");
				builder.append(PhotoColumn.TIME + ", ");
				builder.append(PhotoColumn.ESTATE_ID + ") ");
				builder.append("values ('" + reqEntity.getPhoto() + "','");
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				builder.append(timestamp.toString() + "', ");
				builder.append(id + ");");
				executeUpdate(builder.toString());
				if (avatar == i) {
					try {
						upRepresentPhoto(reqEntity, id, timestamp);
					} catch (Exception e) {
						throw new Exception("Error up avatar: " + e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("Error insert photo: " + e.getMessage());
		}

	}

	public List<PhotoEntity> getPhotos(int estateId) throws SQLException {
		List<PhotoEntity> photoEntities = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		builder = new StringBuilder();
		builder.append("select * from " + PhotoColumn.TABLE_NAME);
		builder.append(" where ");
		builder.append(PhotoColumn.ESTATE_ID + " = " + estateId + ";");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				PhotoEntity entity = getPhotoEntityFromResultSet(rs);
				photoEntities.add(entity);
			}
			return photoEntities;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public EstateEntity queryByID(int id) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM estate e");
		builder.append(" left join address a on a.AddressID = e.AddressID");
		builder.append(" left join estate_type t on e.EstateTypeID = t.EstateTypeID");
		builder.append(" where " + EstateColumn.ID);
		builder.append(" = " + id + ";");
		try {
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return (EstateEntity) getEntityFromResultSet(rs);
			}
			return null;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public List<EstateEntity> search(SearchEstateEntity searchEntity, int page) throws Exception {
		// SELECT * FROM estate e
		// left join address a on a.AddressID = e.AddressID
		// left join estate_type t on e.EstateTypeID = t.EstateTypeID
		// left join estate_detail d on e.EstateID = d.EstateID
		// where
		// a.City = "" and
		// a.District = "" and
		// a.Ward = "" and
		// a.Address like "%%" and
		// t.TypeName = "" and
		// e.Area > 1 and e.Area < 1 and
		// e.Price > 1 and e.Price < 1 and
		// d.Floor > 1 and d.Floor < 1 and
		// d.Bedroom > 1 and d.Bedroom < 1 and
		// d.Bathroom > 1 and d.Bathroom < 1 and
		// d.Cond = ""
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuilder builder = new StringBuilder();
		try {
			builder.append("SELECT * FROM estate e");
			builder.append(" left join address a on a.AddressID = e.AddressID");
			builder.append(" left join estate_type t on e.EstateTypeID = t.EstateTypeID");
			builder.append(" left join estate_detail d on e.EstateID = d.EstateID");
			builder.append(" where ");
			first = true;
			builder.append(addConditionToQuery(addToSearchQueryEqual(searchEntity.getAddress().getCity(), "a.City")));
			builder.append(
					addConditionToQuery(addToSearchQueryEqual(searchEntity.getAddress().getDistrict(), "a.District")));
			builder.append(addConditionToQuery(addToSearchQueryEqual(searchEntity.getAddress().getWard(), "a.Ward")));
			builder.append(
					addConditionToQuery(addToSearchQueryLike(searchEntity.getAddress().getAddress(), "a.Address")));
			builder.append(addConditionToQuery(addToSearchQueryEqual(searchEntity.getType(), "t.TypeName")));
			builder.append(addConditionToQuery(
					addToSearchQueryBetween(searchEntity.getAreaMin(), searchEntity.getAreaMax(), "e.Area")));
			builder.append(addConditionToQuery(
					addToSearchQueryBetween(searchEntity.getPriceMin(), searchEntity.getPriceMax(), "e.Price")));
			builder.append(addConditionToQuery(
					addToSearchQueryBetween(searchEntity.getFloarMin(), searchEntity.getFloorMax(), "d.Floor")));
			builder.append(addConditionToQuery(
					addToSearchQueryBetween(searchEntity.getBedMin(), searchEntity.getBedMax(), "d.Bedroom")));
			builder.append(addConditionToQuery(
					addToSearchQueryBetween(searchEntity.getBathMin(), searchEntity.getBathMax(), "d.Bathroom")));
			builder.append(addConditionToQuery(addToSearchQueryEqual(searchEntity.getDirection(), "d.Cond")));
			builder.append(" and ");
			builder.append(EstateColumn.STATUS_ID + " = " + EstateEntity.STATUS_AVAILABLE);
			builder.append(" limit " + page * PAGE_COUNT + ", " + PAGE_COUNT);
		} catch (Exception e) {
			throw new Exception("Error build query: " + e.getMessage());
		}
		try {
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			List<EstateEntity> entities = new ArrayList<>();
			while (rs.next()) {
				entities.add((EstateEntity) getEntityFromResultSet(rs));
			}
			return entities;
		} catch (Exception e) {
			throw new Exception("Error parse result: " + e.getMessage()  + ". Query: " + builder.toString());
		} finally {
			closeQuery(stmt, rs);
		}
	}

	private String addConditionToQuery(String condition) {
		if (condition.isEmpty()) {
			return "";
		}
		if (first) {
			first = false;
			return condition;
		}
		return " and " + condition;
	}

	private String addToSearchQueryEqual(String data, String column) {
		if (data != null && !data.isEmpty()) {
			return " " + column + " = '" + data + "'";
		}
		return "";
	}

	private String addToSearchQueryLike(String data, String column) {
		if (data != null && !data.isEmpty()) {
			return " " + column + " like '%" + data + "%'";
		}
		return "";
	}

	private String addToSearchQueryBetween(int dataFrom, int dataTo, String column) {
		if (dataFrom == -1) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(" " + column + " >= " + dataFrom);
		if (dataTo != -1) {
			builder.append(" and " + column + " <= " + dataTo);
		}
		return builder.toString();
	}

	public List<EstateEntity> searchGPS(String address, int page) throws Exception {
		// Select *, FullAddress,
		// levenshtein(FullAddress, 'xxx') as distance
		// from (SELECT *, concat(Address, ', ', Ward, ', ', District, ',
		// ',City) as FullAddress
		// FROM address) as a
		// left join estate e on a.AddressID = e.AddressID
		// left join estate_type t on e.EstateTypeID = t.EstateTypeID
		// left join estate_detail d on e.EstateID = d.EstateID
		// order by distance;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuilder builder = new StringBuilder();
		builder.append("Select *, FullAddress,");
		builder.append(" levenshtein(FullAddress, '" + address + "') as distance");
		builder.append(" from (SELECT *, concat(Address, ', ', Ward, ', ', District, ', ',City) as FullAddress");
		builder.append(" FROM address) as a");
		builder.append(" left join estate e on a.AddressID = e.AddressID");
		builder.append(" left join estate_type t on e.EstateTypeID = t.EstateTypeID");
		builder.append(" left join estate_detail d on e.EstateID = d.EstateID");
		builder.append(" where ");
		builder.append(EstateColumn.STATUS_ID + " = " + EstateEntity.STATUS_AVAILABLE);
		builder.append(" order by distance");
		builder.append(" limit " + page * PAGE_COUNT + ", " + PAGE_COUNT);
		try {
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			List<EstateEntity> entities = new ArrayList<>();
			while (rs.next()) {
				entities.add((EstateEntity) getEntityFromResultSet(rs));
			}
			return entities;
		} catch (Exception e) {
			throw new Exception("Error parse result: " + e.getMessage());
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public List<EstateEntity> searchText(String text, int page) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuilder builder = new StringBuilder();
		builder.append("Select * from (Select *, (levenshtein_ratio(FullAddress, '");
		builder.append(text + "')) as Distance");
		builder.append(" FROM (select e.*, a.City, a.District, a.Ward, a.Address,");
		builder.append(" t.TypeName, d.Bathroom, d.Bedroom, d.Cond, d.Description,");
		builder.append(" d.Floor, d.Length, d.Width, d.Longitude, d.Latitude,");
		builder.append(" concat(Address, ', ', Ward, ', ', District, ', ', City)");
		builder.append(" as FullAddress from address a");
		builder.append(" left join estate e on a.AddressID = e.AddressID");
		builder.append(" left join estate_type t on e.EstateTypeID = t.EstateTypeID");
		builder.append(" left join estate_detail d on e.EstateID = d.EstateID) as tmp1) as tmp2");
		builder.append(" where (");
		builder.append(" MATCH(Name) AGAINST('");
		builder.append(text + "' IN NATURAL LANGUAGE MODE)");
		builder.append(" or MATCH(TypeName) AGAINST('");
		builder.append(text + "' IN NATURAL LANGUAGE MODE)");
		builder.append(" or MATCH(Description) AGAINST('");
		builder.append(text + "' IN NATURAL LANGUAGE MODE))");
		builder.append(" and StatusID = 1");
		builder.append(" order by Distance desc");
		builder.append(" limit " + page * PAGE_COUNT + ", " + PAGE_COUNT);
		try {
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			List<EstateEntity> entities = new ArrayList<>();
			while (rs.next()) {
				entities.add((EstateEntity) getEntityFromResultSet(rs));
			}
			return entities;
		} catch (Exception e) {
			throw new Exception("Error parse result: " + e.getMessage() + ". Query: " + builder.toString());
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public List<EstateEntity> searchGPS(double lat, double lng, int dist, int page) throws Exception {
		// select * from (
		// SELECT e.*, a.City, a.District, a.Ward, a.Address, t.TypeName,
		// d.Bathroom, d.Bedroom,
		// d.Cond, d.Description, d.Floor, d.Length, d.Width, d.Longitude,
		// d.Latitude,
		// gpsDistance(d.Latitude, d.Longitude, 10.778759, 106.665939) as
		// Distance
		// FROM estate e
		// left join address a on e.AddressID = a.AddressID
		// left join estate_type t on e.EstateTypeID = t.EstateTypeID
		// left join estate_detail d on e.EstateID = d.EstateID
		// where e.StatusID = 1
		// order by Distance, PostTime desc) as tmp
		// where Distance < 5000
		// limit 0, 6
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuilder builder = new StringBuilder();
		builder.append("select * from (SELECT e.*, a.City, a.District, a.Ward, a.Address,");
		builder.append(" t.TypeName,d.Bathroom, d.Bedroom, d.Cond, d.Description,");
		builder.append(" d.Floor, d.Length, d.Width, d.Longitude, d.Latitude,");
		builder.append(" gpsDistance(d.Latitude, d.Longitude, " + lat + ", " + lng + ") as Distance");
		builder.append(" FROM estate e");
		builder.append(" left join address a on e.AddressID = a.AddressID");
		builder.append(" left join estate_type t on e.EstateTypeID = t.EstateTypeID");
		builder.append(" left join estate_detail d on e.EstateID = d.EstateID");
		builder.append(" where e.StatusID = 1");
		builder.append(" order by Distance, PostTime desc) as tmp");
		builder.append(" where Distance < " + (dist * 1000));
		builder.append(" limit " + page * PAGE_COUNT + ", " + PAGE_COUNT);
		try {
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			List<EstateEntity> entities = new ArrayList<>();
			while (rs.next()) {
				entities.add((EstateEntity) getEntityFromResultSet(rs));
			}
			return entities;
		} catch (Exception e) {
			throw new Exception("Error parse result: " + e.getMessage());
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public void updateStatus(int estateId, int status) throws SQLException {
		StringBuilder builder = new StringBuilder();
		builder.append("update estate set StatusID = ");
		builder.append(status + "where EstateID = ");
		builder.append(estateId);
		executeUpdate(builder.toString());
	}

	public void postComment(CommentEntity entity) throws SQLException {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into ");
		builder.append(CommentColumn.TABLE_NAME);
		builder.append(" (" + CommentColumn.MESSAGE + ", ");
		builder.append(CommentColumn.USER_ID1 + ", ");
		builder.append(CommentColumn.USER_ID2 + ", ");
		builder.append(CommentColumn.TIME + ", ");
		builder.append(CommentColumn.ESTATE_ID + ") values (");
		builder.append("'" + entity.getMessage() + "', ");
		builder.append(entity.getUser1() + ", ");
		builder.append(entity.getUser2() + ", ");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		builder.append("'" + timestamp.toString() + "', ");
		builder.append(entity.getEstateId() + ");");
		executeUpdate(builder.toString());
		
		builder = new StringBuilder();
		builder.append("insert into " + NotificationColumn.TABLE_NAME);
		builder.append(" (" + NotificationColumn.USER_ID + ", ");
		builder.append(NotificationColumn.REQUEST_ID + ", ");
		builder.append(NotificationColumn.ESTATE_ID + ", ");
		builder.append(NotificationColumn.MESSAGE + ", ");
		builder.append(NotificationColumn.NOTI_TYPE + ") ");
		builder.append("values (" + entity.getUser1() + ",");
		builder.append(entity.getUser2() + ", ");
		builder.append(entity.getEstateId() + ", ");
		builder.append("'question', ");
		builder.append(" ");
		builder.append("2);");
		executeUpdate(builder.toString());
	}

	public List<CommentEntity> getComment(int estateId, int ownerId, int buyerId) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<CommentEntity> entities = new ArrayList<>();
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(CommentColumn.TABLE_NAME);
			builder.append(" where ");
			builder.append(CommentColumn.USER_ID1 + " = " + buyerId);
			builder.append(" or " + CommentColumn.USER_ID2 + " = " + ownerId);
			builder.append(" order by " + CommentColumn.TIME + " desc");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				CommentEntity entity = (CommentEntity) getCommentEntityFromResultSet(rs);
				entities.add(entity);
			}
			return entities;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public void answerComment(CommentEntity entity) throws SQLException {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into ");
		builder.append(CommentColumn.TABLE_NAME);
		builder.append(" (" + CommentColumn.MESSAGE + ", ");
		builder.append(CommentColumn.USER_ID1 + ", ");
		builder.append(CommentColumn.USER_ID2 + ", ");
		builder.append(CommentColumn.TIME + ", ");
		builder.append(CommentColumn.ESTATE_ID + ") values (");
		builder.append("'" + entity.getMessage() + "', ");
		builder.append(entity.getUser1() + ", ");
		builder.append(entity.getUser2() + ", ");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		builder.append("'" + timestamp.toString() + "', ");
		builder.append(entity.getEstateId() + ");");
		executeUpdate(builder.toString());
		
		builder = new StringBuilder();
		builder.append("insert into " + NotificationColumn.TABLE_NAME);
		builder.append(" (" + NotificationColumn.USER_ID + ", ");
		builder.append(NotificationColumn.REQUEST_ID + ", ");
		builder.append(NotificationColumn.ESTATE_ID + ", ");
		builder.append(NotificationColumn.MESSAGE + ", ");
		builder.append(NotificationColumn.NOTI_TYPE + ") ");
		builder.append("values (" + entity.getUser2() + ",");
		builder.append(entity.getUser1() + ", ");
		builder.append(entity.getEstateId() + ", ");
		builder.append("'question', ");
		builder.append(" ");
		builder.append("3);");
		executeUpdate(builder.toString());
	}
}
