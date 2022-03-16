package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;


/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "deletefriend";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the friend identified by name.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME (must be name of a friend who exists in Amigos)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "John Doe";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted friend: %1$s";

    //these dummy fields are needed to instantiate a person to be used to check if such a name entered exists
    // in the address book
    private static final Phone dummyPhone = new Phone("12345678");
    private static final Email dummyEmail = new Email("dummyemail@gmail.com");
    private static final Address dummyAddress = new Address("Dummy Address");

    private final Name nameOfPersonToDelete;


    public DeleteCommand(Name nameOfPersonToDelete) {
        this.nameOfPersonToDelete = nameOfPersonToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        Person personWithNameToDelete = new Person(nameOfPersonToDelete);

        if (!model.hasPerson(personWithNameToDelete)) { // model.hasPerson considers 2 Persons with same name
            // to be the same Person
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NAME);
        }

        ObservableList<Person> personList = model.getAddressBook().getPersonList();

        final Person[] personToDelete = new Person[1];

        personList.forEach((person) -> {
            if (person.isSamePerson(personWithNameToDelete)) {
                personToDelete[0] = person;
                model.deletePerson(person);
            }
        });

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete[0]));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && nameOfPersonToDelete.equals(((DeleteCommand) other).nameOfPersonToDelete)); // state check
    }
}
