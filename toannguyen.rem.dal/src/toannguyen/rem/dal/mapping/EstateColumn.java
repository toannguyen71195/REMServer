package toannguyen.rem.dal.mapping;

public enum EstateColumn {
	ID("EstateID"), ADDRESS_ID("AddressID"), NAME("Name"), OWNER_ID("OwnerID"), RATE("Rate"), STATUS_ID(
			"StatusID"), ESTATE_TYPE_ID("EstateTypeID"), POST_TIME(
					"PostTime"), PRICE("Price"), AREA("Area"), PHOTO_ID("PhotoID"), EDIT_TIME("EditTime");

	String columnName;

	private EstateColumn(String columnName) {
		this.columnName = columnName;
	}

	private EstateColumn(EntityColumn entityColumn) {
		this.columnName = entityColumn.getColumnName();
	}

	public String getColumnName() {
		return columnName;
	}

	public static final String TABLE_NAME = "estate";

	@Override
	public String toString() {
		return columnName;
	}
}
