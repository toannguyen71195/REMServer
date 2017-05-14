package toannguyen.rem.dal.mapping;

public enum UserColumn {
	ID("UserID"), USER_TYPE("UserType"), USER_NAME("UserName"), FULL_NAME("FullName"), EMAIL("Email"), ADDRESS(
			"Address"), PASSWORD("Password"), PHONE("Phone");

	String columnName;

	private UserColumn(String columnName) {
		this.columnName = columnName;
	}

	private UserColumn(EntityColumn entityColumn) {
		this.columnName = entityColumn.getColumnName();
	}

	public String getColumnName() {
		return columnName;
	}

	public static final String TABLE_NAME = "users";

	@Override
	public String toString() {
		return columnName;
	}
}
