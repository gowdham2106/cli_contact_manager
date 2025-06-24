package cli_contact_manager.app;

import cli_contact_manager.models.Contact;
import cli_contact_manager.service.ContactService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ContactService service = new ContactService();
        service.loadContactsFromDatabase();  

        int choice;

        do {
        	
            System.out.println("\n📱 CLI CONTACT MANAGER");
            System.out.println("==========================");
            System.out.println("📊 Total Contacts: "+service.contactCount);
            System.out.println("\n***************************");
            System.out.println("🚨 Emergency Numbers:");
            System.out.println("\nPolice 100 || Ambulance 108 ");
            System.out.println("\n***************************");
            System.out.println("\n1. Add Contact");
            System.out.println("2. View All Contacts");
            System.out.println("3. Search Contacts");
            System.out.println("4. Update Contact");
            System.out.println("5. Delete Contacts");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1 -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine();
                    System.out.print("Blood Group: ");
                    String bloodGroup = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Place: ");
                    String place = sc.nextLine();

                   
                    Contact contact = new Contact(name, phone, bloodGroup, email, place);

                    service.addContact(contact);
                }

                case 2 -> service.viewAllContacts();

                case 3 -> service.searchMenu();

                case 4 -> service.updateContact();
                
                case 5 -> service.deleteContact();
                
                case 0 -> System.out.println("👋 Thank you for using Contact Manager. Goodbye!");

                default -> System.out.println("❌ Invalid option. Try again.");
            }

        } while (choice != 0);

        
    }
}
