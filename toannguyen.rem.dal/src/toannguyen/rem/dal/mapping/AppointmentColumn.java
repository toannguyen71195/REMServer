package toannguyen.rem.dal.mapping;

public enum AppointmentColumn {
	ID("ApptID"), TITLE("Title"), ADDRESS("Address"), NOTE("Note"), TIME("Time"), STATUS("Status"), USER1("User1"), USER2("User2");

	String columnName;

	private AppointmentColumn(String columnName) {
		this.columnName = columnName;
	}

	private AppointmentColumn(EntityColumn entityColumn) {
		this.columnName = entityColumn.getColumnName();
	}

	public String getColumnName() {
		return columnName;
	}

	public static final String TABLE_NAME = "appointment";

	@Override
	public String toString() {
		return columnName;
	}
}
