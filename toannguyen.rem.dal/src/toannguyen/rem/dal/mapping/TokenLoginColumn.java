package toannguyen.rem.dal.mapping;

public enum TokenLoginColumn {
	USER_ID("UserID"), TOKEN("Token");
	
	String columnName;

	private TokenLoginColumn(String columnName) {
		this.columnName = columnName;
	}

	private TokenLoginColumn(EntityColumn entityColumn) {
		this.columnName = entityColumn.getColumnName();
	}

	public String getColumnName() {
		return columnName;
	}

	public static final String TABLE_NAME = "token_login";
	
	@Override
	public String toString() {
		return columnName;
	}
}
