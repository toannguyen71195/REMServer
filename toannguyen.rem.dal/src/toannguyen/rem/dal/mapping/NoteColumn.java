package toannguyen.rem.dal.mapping;

public enum NoteColumn {
	ID("NoteID"), NOTE("Note"), ESTATE_ID("EstateID"), USER_ID("UserID");
	
	String columnName;

	private NoteColumn(String columnName) {
		this.columnName = columnName;
	}

	private NoteColumn(EntityColumn entityColumn) {
		this.columnName = entityColumn.getColumnName();
	}

	public String getColumnName() {
		return columnName;
	}

	public static final String TABLE_NAME = "note";
	
	@Override
	public String toString() {
		return columnName;
	}
}
