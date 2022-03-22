package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_FRIENDNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIEND_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_FRIENDNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_DESCRIPTION_AMY = "Met in primary school";
    public static final String VALID_DESCRIPTION_BOB = "Met in high school";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_LOG_TITLE = "some valid title";
    public static final String VALID_LOG_TITLE_PRECEDING_SPACE = " still a valid title";
    public static final String VALID_LOG_TITLE_PRECEDING_SPACE_TRIMMED = "still a valid title";

    public static final String VALID_LOG_DESCRIPTION = "some description!";
    public static final String VALID_LOG_DESCRIPTION_OTHER = "some other description!";

    public static final String VALID_EVENT_NAME = "Some valid event";
    public static final String VALID_EVENT_NAME_OTHER = "Some other valid event";
    public static final String VALID_EVENT_DATETIME_AMY = "10-10-2020 1835";
    public static final String VALID_EVENT_DATETIME_AMY_DIFF_TIME = "10-10-2020 1830";
    public static final String VALID_EVENT_DATETIME = "15-12-2000 2201";
    public static final String VALID_EVENT_DATETIME_OTHER = "15-12-2020 1400";
    public static final String VALID_EVENT_DESCRIPTION = "Some valid description";
    public static final String VALID_EVENT_DESCRIPTION_OTHER = "Some other valid description";


    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NEW_NAME_DESC_AMY = " " + PREFIX_NEW_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NEW_NAME_DESC_BOB = " " + PREFIX_NEW_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String NEW_PHONE_DESC_AMY = " " + PREFIX_NEW_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String NEW_PHONE_DESC_BOB = " " + PREFIX_NEW_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String NEW_EMAIL_DESC_AMY = " " + PREFIX_NEW_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String NEW_EMAIL_DESC_BOB = " " + PREFIX_NEW_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String NEW_ADDRESS_DESC_AMY = " " + PREFIX_NEW_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String NEW_ADDRESS_DESC_BOB = " " + PREFIX_NEW_ADDRESS + VALID_ADDRESS_BOB;
    public static final String DESCRIPTION_DESC_AMY = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_AMY;
    public static final String NEW_DESCRIPTION_DESC_AMY = " " + PREFIX_NEW_DESCRIPTION + VALID_DESCRIPTION_AMY;
    public static final String DESCRIPTION_DESC_BOB = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BOB;
    public static final String NEW_DESCRIPTION_DESC_BOB = " " + PREFIX_NEW_DESCRIPTION + VALID_DESCRIPTION_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String NEW_TAG_DESC_FRIEND = " " + PREFIX_NEW_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String NEW_TAG_DESC_HUSBAND = " " + PREFIX_NEW_TAG + VALID_TAG_HUSBAND;
    public static final String LOG_TITLE_DESC = " " + PREFIX_TITLE + VALID_LOG_TITLE;
    public static final String LOG_TITLE_DESC_PRECEDING_SPACE = " " + PREFIX_TITLE + VALID_LOG_TITLE_PRECEDING_SPACE;
    public static final String LOG_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + VALID_LOG_DESCRIPTION;
    public static final String LOG_DESCRIPTION_DIFFERENT_DESC = " " + PREFIX_DESCRIPTION + VALID_LOG_DESCRIPTION_OTHER;
    public static final String EVENT_NAME_DESC_A = " " + PREFIX_NAME + VALID_EVENT_NAME;
    public static final String EVENT_NAME_DESC_B = " " + PREFIX_NAME + VALID_EVENT_NAME_OTHER;
    public static final String EVENT_DATETIME_DESC_A = " " + PREFIX_DATETIME + VALID_EVENT_DATETIME;
    public static final String EVENT_DATETIME_DESC_B = " " + PREFIX_DATETIME + VALID_EVENT_DATETIME_OTHER;
    public static final String EVENT_DESCRIPTION_DESC_A = " " + PREFIX_DESCRIPTION + VALID_EVENT_DESCRIPTION;
    public static final String EVENT_DESCRIPTION_DESC_B = " " + PREFIX_DESCRIPTION + VALID_EVENT_DESCRIPTION_OTHER;
    public static final String EVENT_FRIEND_NAME_DESC_A = " " + PREFIX_FRIEND_NAME + VALID_NAME_AMY;
    public static final String EVENT_FRIEND_NAME_DESC_B = " " + PREFIX_FRIEND_NAME + VALID_NAME_BOB;
    public static final String EVENT_ADDFRIEND_DESC_A = " " + PREFIX_ADD_FRIENDNAME + VALID_NAME_AMY;
    public static final String EVENT_ADDFRIEND_DESC_B = " " + PREFIX_ADD_FRIENDNAME + VALID_NAME_BOB;
    public static final String EVENT_REMOVEFRIEND_DESC_A = " " + PREFIX_REMOVE_FRIENDNAME + VALID_NAME_AMY;
    public static final String EVENT_REMOVEFRIEND_DESC_B = " " + PREFIX_REMOVE_FRIENDNAME + VALID_NAME_BOB;


    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NEW_NAME_DESC = " " + PREFIX_NEW_NAME + "James&";
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_NEW_PHONE_DESC = " " + PREFIX_NEW_PHONE + "911a";
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_NEW_EMAIL_DESC = " " + PREFIX_NEW_EMAIL + "bob!yahoo";
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_NEW_ADDRESS_DESC = " " + PREFIX_NEW_ADDRESS;
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_NEW_TAG_DESC = " " + PREFIX_NEW_TAG + "hubby*";
    public static final String INVALID_NEW_DESCRIPTION_DESC = " " + PREFIX_NEW_DESCRIPTION
            + " "; //empty descriptions not allowed
    public static final String INVALID_LOG_TITLE_EMPTY_STRING_DESC = " " + PREFIX_TITLE + "";
    public static final String INVALID_LOG_TITLE_ONLY_SPACES_DESC = " " + PREFIX_TITLE + "     ";
    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_NAME + "James\nBirthday";
    public static final String INVALID_EVENT_DATETIME_DESC = " " + PREFIX_DATETIME + "1400-20-10 %%";
    public static final String INVALID_EVENT_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "";
    public static final String INVALID_EVENT_FRIENDNAME_DESC = " " + PREFIX_FRIEND_NAME + "Tom $% Arthur";
    public static final String INVALID_EVENT_ADDFRIEND_DESC = " " + PREFIX_ADD_FRIENDNAME + "Jack,Hilary";
    public static final String INVALID_EVENT_REMOVE_DESC = " " + PREFIX_REMOVE_FRIENDNAME + "Tom,Arthur";
    public static final String INVALID_LOG_TITLE_TOO_LONG_DESC = " " + PREFIX_TITLE
            + "cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    public static final EditEventCommand.EditEventDescriptor DESC_A;
    public static final EditEventCommand.EditEventDescriptor DESC_B;

    static {
        DESC_A = new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME)
                .withDateTime(VALID_EVENT_DATETIME)
                .withDescription(VALID_EVENT_DESCRIPTION)
                .withAddFriend(VALID_NAME_AMY).build();
        DESC_B = new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME_OTHER)
                .withDateTime(VALID_EVENT_DATETIME_OTHER)
                .withDescription(VALID_EVENT_DESCRIPTION_OTHER)
                .withAddFriend(VALID_NAME_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertEventCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, true);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }



    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and events list in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedPersonList = new ArrayList<>(actualModel.getFilteredPersonList());
        List<Event> expectedEventList = new ArrayList<>(actualModel.getEventsList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedPersonList, actualModel.getFilteredPersonList());
        assertEquals(expectedEventList, actualModel.getEventsList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
