package toannguyen.rem.dal.mapping;

public enum CommentColumn {

	ID("ID"), USER_ID1("User1"), USER_ID2("User2"), ESTATE_ID("EstateID"), MESSAGE("Message"), TIME("Time");
	
	String columnName;

	private CommentColumn(String columnName) {
		this.columnName = columnName;
	}

	private CommentColumn(EntityColumn entityColumn) {
		this.columnName = entityColumn.getColumnName();
	}

	public String getColumnName() {
		return columnName;
	}

	public static final String TABLE_NAME = "comment";
	
	@Override
	public String toString() {
		return columnName;
	}
}
