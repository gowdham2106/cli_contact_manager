package cli_contact_manager.models;

public class Contact {
	private int id;
    private String name;
    private String phone;
    private String bloodGroup;
    private String email;
    private String place;
    public Contact(String name, String phone, String bloodGroup, String email, String place) {
        this.name = name;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.email = email;
        this.place = place;
    }
    public Contact(int id, String name, String phone, String bloodGroup, String email, String place) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.email = email;
        this.place = place;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getEmail() {
        return email;
    }

    public String getPlace() {
        return place;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}
