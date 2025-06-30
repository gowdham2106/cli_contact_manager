package cli_contact_manager.service;

import cli_contact_manager.db.ContactDatabase;
import cli_contact_manager.models.Contact;

import java.sql.*;
import java.util.Scanner;

public class ContactService {

	int MAX_CONTACTS = 100;
	Contact[] contacts = new Contact[MAX_CONTACTS];
   public  int contactCount = 0;

    Scanner sc = new Scanner(System.in);

   
    public void loadContactsFromDatabase() {
        contactCount = 0;
        String sql = "SELECT * FROM contacts";

        try (Connection conn = ContactDatabase.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next() && contactCount < MAX_CONTACTS) {
                Contact c = new Contact(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("blood_group"),
                        rs.getString("email"),
                        rs.getString("place")
                );
                contacts[contactCount++] = c;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error loading contacts: " + e.getMessage());
        }
    }

    //  add contact 
    public void addContact(Contact c) {
        String sql = "INSERT INTO contacts(name, phone,blood_group, email, place) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ContactDatabase.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getName());
            stmt.setString(2, c.getPhone());
            stmt.setString(3, c.getBloodGroup());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getPlace());

            stmt.executeUpdate();
            System.out.println("✅ Contact added.");
            loadContactsFromDatabase();

        } catch (SQLException e) {
            System.out.println("❌ Failed to add: " + e.getMessage());
        }
    }

    //view contact
    public void viewAllContacts() {
        if (contactCount == 0) {
            System.out.println("⚠️ No contacts found.");
        } else {
            System.out.println("\n📇 Contact List:");
            for (int i = 0; i < contactCount; i++) {
                display(contacts[i]);
            }
        }
    }

    //  search menu
    public void searchMenu() {
        System.out.println("\n=== Search Menu ===");
        System.out.println("1. By Name");
        System.out.println("2. By Phone");
        System.out.println("3. By Blood Group");
        System.out.println("4. By Place");
        System.out.println("5. By First Letter of Name");
        System.out.print("Enter option: ");
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1 ->{
                System.out.print("Enter name: ");
                String name = sc.nextLine().toLowerCase();
                searchByField("name", name);
            }
            case 2 -> {
                System.out.print("Enter phone: ");
                String phone = sc.nextLine();
                searchByField("phone", phone);
               
            }
            case 3 -> {
                System.out.print("Enter blood group: ");
                String bg = sc.nextLine().toUpperCase();
                searchByField("bloodGroup", bg);
               
            }
            case 4 -> {
                System.out.print("Enter place: ");
                String place = sc.nextLine().toLowerCase();
                for (int i = 0; i < contactCount; i++) {
                	searchByField("place", place);
                    
                }
                
            }
            case 5-> {
                System.out.print("Enter first letter: ");
                String ch = sc.nextLine().toLowerCase();
                boolean found = false;
                for (int i = 0; i < contactCount; i++) {
                    if (contacts[i].getName().toLowerCase().startsWith(ch)) {
                        display(contacts[i]);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("⚠️ No match found.");
                }
                break;
            }

            default ->{
            	System.out.println("❌ Invalid option.");
            }
        }
    }

    // search by field
    private void searchByField(String field, String value) {
        boolean found = false;
        for (int i = 0; i < contactCount; i++) {
            Contact c = contacts[i];
            switch (field) {
                case "name" -> {
                    if (c.getName().equalsIgnoreCase(value)) {
                        display(c);
                        found = true;
                    }
                }
                case "phone" -> {
                    if (c.getPhone().equals(value)) {
                        display(c);
                        found = true;
                    }
                }
                case "bloodGroup" -> {
                    if (c.getBloodGroup().equalsIgnoreCase(value)) {
                        display(c); 
                        found = true;
                    }
                }
                case "place" -> {
                    if (c.getPlace().equalsIgnoreCase(value)) {
                        display(c); 
                        found = true;
                    }
                }
            }
        }
        if (!found) System.out.println("⚠️ No match found.");
    }

    //  update 
    public void updateContact() {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean found = false;
        for (int i = 0; i < contactCount; i++) {
            if (contacts[i].getId() == id) {
                Contact c = contacts[i];
                System.out.println("Editing:");
                display(c);

                System.out.print("New Name: ");
                c.setName(sc.nextLine());

                System.out.print("New Phone: ");
                c.setPhone(sc.nextLine());

                System.out.print("New Blood Group: ");
                c.setBloodGroup(sc.nextLine());

                System.out.print("New Email: ");
                c.setEmail(sc.nextLine());

                System.out.print("New Place: ");
                c.setPlace(sc.nextLine());

                // update in DB
                String sql = "UPDATE contacts SET name=?, phone=?, blood_group=?, email=?, place=? WHERE id=?";
                try (Connection conn = ContactDatabase.connect();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setString(1, c.getName());
                    stmt.setString(2, c.getPhone());
                    stmt.setString(3, c.getBloodGroup());
                    stmt.setString(4, c.getEmail());
                    stmt.setString(5, c.getPlace());
                    stmt.setInt(6, id);

                    stmt.executeUpdate();
                    System.out.println("✅ Contact updated.");
                    loadContactsFromDatabase();
                    found = true;

                } catch (SQLException e) {
                    System.out.println("❌ Update failed: " + e.getMessage());
                }
                break;
            }
        }

        if (!found) {
            System.out.println("❌ Contact ID not found.");
        }
    }
    public void deleteContact() {
        System.out.print("Enter Contact ID to delete: ");
        int idToDelete = sc.nextInt();
        sc.nextLine(); 

        boolean found = false;
        for (int i = 0; i < contactCount; i++) {
            if (contacts[i].getId() == idToDelete) {
                found = true;

                System.out.println("Are you sure you want to delete this contact?");
                display(contacts[i]);
                System.out.print("Type 'yes' to confirm: ");
                String confirm = sc.nextLine().toLowerCase();

                if (confirm.equals("yes")) {
                    String sql = "DELETE FROM contacts WHERE id = ?";
                    try (Connection conn = ContactDatabase.connect();
                         PreparedStatement stmt = conn.prepareStatement(sql)) {

                        stmt.setInt(1, idToDelete);
                        stmt.executeUpdate();
                        System.out.println("✅ Contact deleted successfully.");
                        loadContactsFromDatabase(); 

                    } catch (SQLException e) {
                        System.out.println("❌ Error deleting contact: " + e.getMessage());
                    }
                } else {
                    System.out.println("❌ Deletion cancelled.");
                }

                break;
            }
        }

        if (!found) {
            System.out.println("⚠️ Contact ID not found.");
        }
    }

    
    
    private void display(Contact c) {
        System.out.println("\n----------------------------");
        System.out.println("ID          : " + c.getId());
        System.out.println("Name        : " + c.getName());
        System.out.println("Phone       : " + c.getPhone());
        System.out.println("Blood Group : " + c.getBloodGroup());
        System.out.println("Email       : " + c.getEmail());
        System.out.println("Place       : " + c.getPlace());
        
    }
}
