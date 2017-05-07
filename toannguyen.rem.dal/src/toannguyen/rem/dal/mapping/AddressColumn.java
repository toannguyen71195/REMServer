package toannguyen.rem.dal.mapping;

public enum AddressColumn {
	ID("AddressID"), CITY("City"), DISTRICT("District"), WARD("Ward"), STREET("Street"), ADDRESS("Address");

	String columnName;

	private AddressColumn(String columnName) {
		this.columnName = columnName;
	}

	private AddressColumn(EntityColumn entityColumn) {
		this.columnName = entityColumn.getColumnName();
	}

	public String getColumnName() {
		return columnName;
	}

	public static final String TABLE_NAME = "address";
	
	@Override
	public String toString() {
		return columnName;
	}
}
