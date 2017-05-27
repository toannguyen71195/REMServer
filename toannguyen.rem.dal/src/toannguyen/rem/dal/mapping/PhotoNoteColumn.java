package toannguyen.rem.dal.mapping;

public enum PhotoNoteColumn {
	ID("NoteID"), PHOTO("Photo"), ESTATE_ID("EstateID"), USER_ID("UserID");

	String columnName;

	private PhotoNoteColumn(String columnName) {
		this.columnName = columnName;
	}

	private PhotoNoteColumn(EntityColumn entityColumn) {
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
