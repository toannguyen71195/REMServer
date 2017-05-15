package toannguyen.rem.dal.mapping;

public enum InterestedEstateColumn {
	ESTATE_ID("EstateID"), USER_ID("BuyerID"), IS_VISITED("isVisited"), TIME("Time");
	
	String columnName;

	private InterestedEstateColumn(String columnName) {
		this.columnName = columnName;
	}

	private InterestedEstateColumn(EntityColumn entityColumn) {
		this.columnName = entityColumn.getColumnName();
	}

	public String getColumnName() {
		return columnName;
	}

	public static final String TABLE_NAME = "interested_estate";
	
	@Override
	public String toString() {
		return columnName;
	}
}
