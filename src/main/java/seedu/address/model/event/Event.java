package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Description;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Represents an Event in Amigos.
 * Guarantees: fields are present and not null, field values are validated, immutable.
 */
public class Event {

    // Identity fields
    private final Name name;
    private final DateTime dateTime;

    // Data fields
    private final Description description;
    private final Set<Name> friendNames = new HashSet<>();

    /**
     * Constructor for event.
     *
     * @param name        name of event
     * @param description description of event
     * @param dateTime    date and time of event
     * @param friendNames     set of friend's Names linked with the event.
     */
    public Event(Name name, DateTime dateTime, Description description, Set<Name> friendNames) {
        requireAllNonNull(name, dateTime, description, friendNames);
        this.name = name;
        this.dateTime = dateTime;
        this.description = description;
        this.friendNames.addAll(friendNames);
    }

    public Name getName() {
        return name;
    }

    private LocalDate getDate() {
        return dateTime.getDate();
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns an immutable person set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Name> getFriendNames() {
        return Collections.unmodifiableSet(friendNames);
    }

    /**
     * Returns true if all friend names correspond to actual Friends in the AddressBook.
     *
     * @return true if all friend names correspond to actual Friends in the AddressBook.
     */
    public boolean areFriendNamesValid(AddressBook addressBook) {
        // There ought to be a better way of doing this - search AddressBook by name perhaps?
        // worth thinking about - how to enforce search specifically by name, rather than relying
        // on the ::hasPerson(Person) method. todo implement search by name (specifically name objects match)

        for (Name name : getFriendNames()) {
            Person beingLookedFor = new Person(name);
            if (!addressBook.hasPerson(beingLookedFor)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if both events have the same name and date.
     * This defines a weaker notion of equality between two events and is used to
     * prevent duplication.
     *
     * @param otherEvent The other event to compare to.
     * @return True if both events have the same name and date.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && otherEvent.getDate().equals(getDate());
    }

    /**
     * Returns true if both events have the exact same fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(getName())
                && otherEvent.getDateTime().equals(getDateTime())
                && otherEvent.getDescription().equals(getDescription())
                && otherEvent.getFriendNames().equals(getFriendNames());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, dateTime, description, friendNames);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; DateTime: ")
                .append(getDateTime())
                .append("; Description: ")
                .append(getDescription());
        Set<Name> friendNames = getFriendNames();
        if (!friendNames.isEmpty()) {
            builder.append("; Friends: ");
            friendNames.forEach(name -> builder.append(name + " "));
        }
        return builder.toString();
    }
}
