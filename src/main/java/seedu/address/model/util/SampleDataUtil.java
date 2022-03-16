package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Description;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Description("Friends since high school"),
                getTagSet("friends"), null),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Description("Friday lunch buddy"),
                getTagSet("colleagues", "friends"), null),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Description("Deskmate in CS1010S"),
                getTagSet("neighbours"), null),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Description("Loves to steal my food"),
                getTagSet("family"), null),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Description("Friends since junior college"),
                getTagSet("classmates"), null),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Description("Takes the same bus as me"),
                getTagSet("colleagues"), null)
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new Name("Alex Birthday"), new DateTime("10-03-2022 1430"),
                    new Description("Remember to bring cake."), getNameSet("Alex Yeoh", "David Li")),
            new Event(new Name("Weekends at Bernice"), new DateTime("10-04-2022 1835"),
                 new Description(null), getNameSet("Bernice Yu")),
            new Event(new Name("Boardgame Night"), new DateTime("12-04-2022 1945"),
                 new Description("We will be playing Monopoly."),
                 getNameSet("Roy Balakrishnan", "Irfan Ibrahim"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyEventBook getSampleEventBook() {
        EventBook sampleEb = new EventBook();
        for (Event sampleEvent : getSampleEvents()) {
            sampleEb.addEvent(sampleEvent);
        }
        return sampleEb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a name set containing the list of strings given.
     */
    public static Set<Name> getNameSet(String... strings) {
        return Arrays.stream(strings)
                .map(Name::new)
                .collect(Collectors.toSet());
    }
}
