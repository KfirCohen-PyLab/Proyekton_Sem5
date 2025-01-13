package Types_For_Table_view;

public class Book_type_available {
	String name, location, borrowed;

	public Book_type_available(String name, String location, String borrowed) {
		this.name = name;
		this.location = location;
		this.borrowed = borrowed;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public String getBorrowed() {
		return borrowed;
	}

	@Override
	public String toString() {
		return name + location + borrowed;
	}
}
