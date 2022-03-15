package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.common.Description;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Description description;
    private final Set<Tag> tags = new HashSet<>();
    private final UniqueLogList logs = new UniqueLogList();

    /**
     * Constructs a Person
     *
     * @param name Name of person
     * @param phone Phone of person
     * @param email Email of person
     * @param address Address of person
     * @param description Description of person
     * @param tags Tag(s) of person
     */
    public Person(Name name, Phone phone, Email email, Address address, Description description, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.tags.addAll(tags);
        this.logs.setLogs(new ArrayList<>());

    }

    /**
     * Overloaded method to construct a person to have a description with null value.
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, List<Log> logs) {
        requireAllNonNull(name, phone, email, address, tags, logs);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = new Description(null);
        this.tags.addAll(tags);
        this.logs.setLogs(logs);
    }

    /**
     * Overloaded method to construct a person to have an empty list of logs.
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Description description, Set<Tag> tags,
                  List<Log> logs) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.tags.addAll(tags);
        this.logs.setLogs(logs);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public List<Log> getLogs() {
        return this.logs.asUnmodifiableObservableList();
    }

    public boolean containsLog(Log log) {
        return this.logs.contains(log);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getDescription().equals(getDescription())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getLogs().equals(getLogs());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, description, tags, logs);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(getPhone().value == null ? "" : ("; Phone: " + getPhone()))
                .append(getEmail().value == null ? "" : ("; Email: " + getEmail()))
                .append(getAddress().value == null ? "" : ("; Address: " + getAddress()))
                .append(getDescription().value == null ? ""
                        : ("; Description: " + getDescription()));

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        List<Log> logs = this.getLogs();
        if (!logs.isEmpty()) {
            builder.append(": Logs: \n");
            logs.forEach(builder::append);
        }
        return builder.toString();
    }

}
