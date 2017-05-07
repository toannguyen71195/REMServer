package toannguyen.rem.dal.mapping;

public enum EstateTypeColumn {
	ID("EstateTypeID"), NAME("TypeName");

	String columnName;

	private EstateTypeColumn(String columnName) {
		this.columnName = columnName;
	}

	private EstateTypeColumn(EntityColumn entityColumn) {
		this.columnName = entityColumn.getColumnName();
	}

	public String getColumnName() {
		return columnName;
	}

	public static final String TABLE_NAME = "estate_type";
	
	@Override
	public String toString() {
		return columnName;
	}
}
