package toannguyen.rem.dal.mapping;

public enum EstateDetailColumn {
	ID("EstateDetailID"), AREA("Area"), BATHROOM("Bathroom"), BEDROOM("Bedroom"), CONDITION("Cond"), DESCRIPTION(
			"Description"), FLOOR("Floor"), LENGTH("Length"), WIDTH("Width");

	String columnName;

	private EstateDetailColumn(String columnName) {
		this.columnName = columnName;
	}

	private EstateDetailColumn(EntityColumn entityColumn) {
		this.columnName = entityColumn.getColumnName();
	}

	public String getColumnName() {
		return columnName;
	}

	public static final String TABLE_NAME = "estate_detail";

	@Override
	public String toString() {
		return columnName;
	}
}
