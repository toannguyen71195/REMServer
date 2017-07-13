package toannguyen.rem.dal.mapping;

public enum NotificationColumn {
	ID("ID"), USER_ID("UserID"), REQUEST_ID("RequestID"), ESTATE_ID("EstateID"), MESSAGE("Message"), NOTI_TYPE("NotiType");

	String columnName;

	private NotificationColumn(String columnName) {
		this.columnName = columnName;
	}

	private NotificationColumn(EntityColumn entityColumn) {
		this.columnName = entityColumn.getColumnName();
	}

	public String getColumnName() {
		return columnName;
	}

	public static final String TABLE_NAME = "notification";
	
	@Override
	public String toString() {
		return columnName;
	}
}
