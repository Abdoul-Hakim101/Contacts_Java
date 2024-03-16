package contacts;

import java.util.List;
import java.util.Scanner;

import static contacts.PhoneBook.loadFromFile;


public class Main {

    public static void main(String[] args) {
        PhoneBook phoneBook;
        if (args.length > 0) {
            phoneBook = loadFromFile(args[0]);
        } else {
            phoneBook = new PhoneBook();
        }
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.print("[menu] Enter action (add, list, search, count, exit): ");
            String action = scanner.nextLine().trim().toLowerCase();
            switch (action) {
                case "add":
                    phoneBook.addContact();
                    break;
                case "list":
                    phoneBook.listContacts();
                    break;
                case "search":
                    if (phoneBook.getContactCount() == 0) {
                        System.out.println("No records available.");
                    } else {
                        phoneBook.searchContacts();
                    }
                    break;
                case "count":
                    System.out.printf("The Phone Book has %d records.%n", phoneBook.getContactCount());
                    break;
                case "exit":
                    phoneBook.saveToFile("phonebook.db");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid action.");
            }
            System.out.println();
        }
        scanner.close();
    }
}