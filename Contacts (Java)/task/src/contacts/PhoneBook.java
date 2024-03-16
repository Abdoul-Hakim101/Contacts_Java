package contacts;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PhoneBook implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Contact> contacts;

    public PhoneBook() {
        contacts = new ArrayList<>();
    }

    public void saveToFile(String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static PhoneBook loadFromFile(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (PhoneBook) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new PhoneBook();
        }
    }

    private void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("The record added.");
        saveToFile("phonebook.db");
    }

    public void addContact() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the type (person or organization): ");
        String contactType = scanner.nextLine().trim().toLowerCase();

        if (contactType.equals("person")) {
            System.out.print("Enter the name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter the surname: ");
            String surname = scanner.nextLine().trim();

            System.out.print("Enter the birth date: ");
            String birthDate = scanner.nextLine().trim();
            if (!isValidDateOfBirth(birthDate)) {
                birthDate = "[no data]";
                System.out.println("Bad birth date!");
            }

            System.out.print("Enter the gender (M, F): ");
            String gender = scanner.nextLine().trim();
            if (Gender.fromString(gender).equals(Gender.UNKNOWN)) {
                gender = "[no data]";
            }

            System.out.print("Enter the number: ");
            String phoneNumber = scanner.nextLine().trim();
            if (!isValidPhoneNumber(phoneNumber)) {
                phoneNumber = "[no number]";
                System.out.println("Wrong number format!");
            }

            Contact person = new Person(name, surname, birthDate, gender, phoneNumber);
            addContact(person);

        } else if (contactType.equals("organization")) {
            System.out.print("Enter the organization name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter the address: ");
            String address = scanner.nextLine().trim();

            System.out.print("Enter the number: ");
            String phoneNumber = scanner.nextLine().trim();
            if (!isValidPhoneNumber(phoneNumber)) {
                phoneNumber = "[no number]";
                System.out.println("Wrong number format!");
            }

            Contact organization = new Organization(name, address, phoneNumber);
            addContact(organization);

        } else {
            System.out.println("Invalid contact type.");
        }

    }

    private void editContact(int index) {
        Scanner scanner = new Scanner(System.in);

        Contact contact = contacts.get(index);
        List<String> fields = contact.getFields();

        System.out.print("Select a field (");
        for (int i = 0; i < fields.size(); i++) {
            System.out.print(fields.get(i));
            if (i < fields.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("): ");

        String field = scanner.nextLine().trim().toLowerCase();
        if (contact.getFieldValue(field) == null) {
            return;
        }
        System.out.printf("Enter %s: ", field);
        String value = scanner.nextLine().trim();
        contact.changeFieldValue(field, value);
        contact.setLastEdit();
        contacts.add(index, contact);
        saveToFile("phonebook.db");
        System.out.println("Saved");
        contact.displayContactDetails();
    }

    public void searchContacts() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter search query: ");
        String query = scanner.nextLine().trim();
        List<Contact> searchResults = searchContacts(query);
        System.out.println("Found " + searchResults.size() + " results:");
        for (int i = 0; i < searchResults.size(); i++) {
            String surname =  searchResults.get(i).getFieldValue("surname");
            System.out.println((i + 1) + ". " + searchResults.get(i).getFieldValue("name") +
                    (surname == null ? "" : " " + surname));
        }
        record(scanner);
    }

    private List<Contact> searchContacts(String query) {
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contacts) {
            StringBuilder sb = new StringBuilder();
            for (String field : contact.getFields()) {
                sb.append(contact.getFieldValue(field)).append(" ");
            }
            String combinedFields = sb.toString().toLowerCase();
            if (Pattern.compile(Pattern.quote(query), Pattern.CASE_INSENSITIVE).matcher(combinedFields).find()) {
                result.add(contact);
            }
        }
        return result;
    }

    private void removeContact(int index) {
        contacts.remove(index);
    }

    private void list() {
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            String name = contact.getFieldValue("name");
            String surname = contact.getFieldValue("surname");
            System.out.println((i + 1) + ". " + name + (surname == null ? "" : " " + surname));
        }
    }

    public void listContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No records available.");
        } else {
            list();
            Scanner scanner = new Scanner(System.in);
            System.out.print("\n[list] Enter action ([number], back): ");
            String actionList = scanner.nextLine().trim();
            if (actionList.equals("back")) {
                return;
            }
            try {
                int contactIndex = Integer.parseInt(actionList);
                if (isValidIndex(contactIndex)) {
                    displayContactDetails(contactIndex - 1);
                    record(contactIndex - 1);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'back'.");
            }
        }
    }

    private void displayContactDetails(int index) {
        Contact contact = contacts.get(index);
        contact.displayContactDetails();
    }

    public int getContactCount() {
        return contacts.size();
    }

    private void record(int index) {
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.print("\n[record] Enter action (edit, delete, menu): ");
            String action = scanner.nextLine().trim().toLowerCase();
            switch (action) {
                case "edit":
                    editContact(index);
                    break;
                case "delete":
                    removeContact(index);
                    running = false;
                    break;
                case "menu":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid action.");
            }
        }
    }

    private void record(Scanner scanner) {

        boolean running = true;
        while (running) {
            System.out.print("\n[search] Enter action ([number], back, again): ");
            String action = scanner.nextLine().trim().toLowerCase();

            if (action.equals("again")) {
                searchContacts();
            } else if (action.equals("back")) {
                running = false;
            } else {
                try {
                    int contactIndex = Integer.parseInt(action);
                    if (isValidIndex(contactIndex)) {
                        displayContactDetails(contactIndex - 1);
                    }
                    running = false;
                    record(contactIndex - 1);
                } catch (NumberFormatException e) {
                    running = false;
                    //System.out.println("Invalid input. Please enter a number, 'back' or 'again'.");
                }
            }
        }
    }

    private boolean isValidDateOfBirth(String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(dateOfBirth, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\+?\\d* ?(?:\\(\\w{2,}\\) ?)?(?:\\w{2,}[- ])*\\w{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    private boolean isValidIndex(int index) {
        if (index < 1 || index > getContactCount()) {
            System.out.println("Invalid record");
            return false;
        }
        return true;
    }

}