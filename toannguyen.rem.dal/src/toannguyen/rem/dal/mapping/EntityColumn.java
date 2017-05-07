package toannguyen.rem.dal.mapping;

public enum EntityColumn {
	ID("ID"), NAME("Name");

	String columnName;

	private EntityColumn(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnName() {
		return columnName;
	}
}
