package toannguyen.rem.dal.mapping;

public enum PhotoColumn {
	ID("PhotoID"), PHOTO("Photo"), ESTATE_ID("EstateID"), TIME("Time");
	
	String columnName;

	private PhotoColumn(String columnName) {
		this.columnName = columnName;
	}

	private PhotoColumn(EntityColumn entityColumn) {
		this.columnName = entityColumn.getColumnName();
	}

	public String getColumnName() {
		return columnName;
	}

	public static final String TABLE_NAME = "photo";
	
	@Override
	public String toString() {
		return columnName;
	}
}
