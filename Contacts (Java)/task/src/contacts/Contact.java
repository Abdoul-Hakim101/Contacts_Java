package contacts;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

abstract class Contact implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final LocalDateTime timeCreated;
    private LocalDateTime lastEdit;

    public Contact() {
        this.timeCreated = LocalDateTime.now();
        this.lastEdit = LocalDateTime.now();
    }

    public abstract List<String> getFields();

    public abstract void changeFieldValue(String field, String value);

    public abstract String getFieldValue(String field);

    public abstract void displayContactDetails();

    public LocalDateTime getLastEdit() {
        return lastEdit;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setLastEdit() {
        this.lastEdit = LocalDateTime.now();
    }
}