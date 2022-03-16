package seedu.address.model.person;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;


    // Data fields
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final Description description;
    private final Set<Tag> tags = new HashSet<>();
    private final UniqueLogList logs = new UniqueLogList();

    /**
     * Constructs a person.
     *
     * @param name        Name of the person. Compulsory.
     * @param phone       Phone object of the person. If null, default to no phone.
     * @param email       Phone object of the person. If null, default to no email.
     * @param address     Phone object of the person. If null, default to no address.
     * @param description Phone object of the person. If null, default to no description.
     * @param tags        Set of tags of the person. If null, default to no tags.
     * @param logs        Log list of the person. if null, default to no logs.
     */
    public Person(Name name, Phone phone, Email email, Address address, Description description, Set<Tag> tags, List<Log> logs) {
        requireNonNull(name);
        this.name = name;
        this.phone = isNull(phone) ? new Phone(null) : phone;
        this.email = isNull(email) ? new Email(null) : email;
        this.address = isNull(address) ? new Address(null) : address;
        this.description = isNull(description) ? new Description(null) : description;
        this.tags.addAll(isNull(tags) ? new HashSet<>() : tags);
        this.logs.setLogs(isNull(logs) ? new ArrayList<>() : logs);

    }

    /**
     * Overloaded method to construct a person with only a name.
     *
     * @param name Name of the person. Compulsory.
     */
    public Person(Name name) {
        this(name, null, null, null, null, null, null);
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
     * Returns true if both persons are the same, which we define to be
     * having the same name.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons explicitly have the same name.
     */
    public boolean hasSameName(Person otherPerson) {
        return this.name.equals(otherPerson.name);
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
