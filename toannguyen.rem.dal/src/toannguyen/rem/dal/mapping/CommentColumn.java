package toannguyen.rem.dal.mapping;

public enum CommentColumn {
	ID("CommentID"), ESTATE_ID("EstateID"), USER_ID("UserID"), COMMENT("Comment"), TIME("Time");
	
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
