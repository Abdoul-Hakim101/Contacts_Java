package contacts;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Organization extends Contact {
    private String name;
    private String address;
    private String phoneNumber;


    public Organization(String name, String address, String phoneNumber) {
        this.name = name.isEmpty() ? "[no data]" : name;
        this.address = address.isEmpty() ? "[no data]" : address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void displayContactDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        System.out.println("Organization name: " + getName());
        System.out.println("Address: " + getAddress());
        System.out.println("Number: " + getPhoneNumber());
        System.out.println("Time created: " + getTimeCreated().format(formatter));
        System.out.println("Time last edit: " + getLastEdit().format(formatter));
    }

    @Override
    public List<String> getFields() {
        List<String> fields = new ArrayList<>();
        fields.add("name");
        fields.add("address");
        fields.add("number");
        return fields;
    }

    @Override
    public void changeFieldValue(String field, String value) {
        switch (field) {
            case "name":
                setName(value);
                break;
            case "address":
                setAddress(value);
                break;
            case "number":
                setPhoneNumber(value);
                break;
            default:
                System.out.println("Invalid field!");
                break;
        }
    }

    @Override
    public String getFieldValue(String field) {
        return switch (field) {
            case "name" -> getName();
            case "address" -> getAddress();
            case "number" -> getPhoneNumber();
            default -> null;
        };
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}