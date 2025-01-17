package Types_For_Table_view;

public class Subscriber_Type {
	String name, history, email, id, phonenumber;

	public Subscriber_Type(String id, String phonenumber, String name, String history, String email) {
		this.id = id;
		this.phonenumber = phonenumber;
		this.name = name;
		this.history = history;
		this.email = email;
	}

	public String getID() {
		return id;
	}

	public String getPhoneNumber() {
		return phonenumber;
	}

	public String getName() {
		return name;
	}

	public String getHistory() {
		return history;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return id + phonenumber + name + history + email;
	}
}
