package toannguyen.rem.dal.mapping;

public enum BrokerEstateColumn {
	ESTATE_ID("EstateID"), BROKER_ID("BrokerID");
	
	String columnName;

	private BrokerEstateColumn(String columnName) {
		this.columnName = columnName;
	}

	private BrokerEstateColumn(EntityColumn entityColumn) {
		this.columnName = entityColumn.getColumnName();
	}

	public String getColumnName() {
		return columnName;
	}

	public static final String TABLE_NAME = "broker_estate";
	
	@Override
	public String toString() {
		return columnName;
	}
}
