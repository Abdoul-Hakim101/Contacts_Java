package contacts;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Person extends Contact {
    private String name;
    private String surname;
    private String birthDate;
    private String gender;
    private String phoneNumber;

    public Person(String name, String surname, String birthDate, String gender, String phoneNumber) {
        this.name = name.isEmpty() ? "[no data]" : name;
        this.surname = surname.isEmpty() ? "[no data]" : surname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void displayContactDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        System.out.println("Name: " + getName());
        System.out.println("Surname: " + getSurname());
        System.out.println("Birth date: " + getBirthDate());
        System.out.println("Gender: " + getGender());
        System.out.println("Number: " + getPhoneNumber());
        System.out.println("Time created: " + getTimeCreated().format(formatter));
        System.out.println("Time last edit: " + getLastEdit().format(formatter));
    }


    @Override
    public List<String> getFields() {
        List<String> fields = new ArrayList<>();
        fields.add("name");
        fields.add("surname");
        fields.add("birth");
        fields.add("gender");
        fields.add("number");
        return fields;
    }

    @Override
    public void changeFieldValue(String field, String value) {
        switch (field) {
            case "name":
                setName(value);
                break;
            case "surname":
                setSurname(value);
                break;
            case "birth":
                setBirthDate(value);
                break;
            case "gender":
                setGender(value);
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
            case "surname" -> getSurname();
            case "birth" -> getBirthDate();
            case "gender" -> getGender();
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}