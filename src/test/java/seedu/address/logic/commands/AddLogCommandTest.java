package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Log;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddLogCommand.
 */
public class AddLogCommandTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLogCommand.MESSAGE_USAGE);
    private static final String MESSAGE_INVALID_INDEX = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
    private static final String MESSAGE_PERSON_NOT_FOUND = AddLogCommand.MESSAGE_PERSON_NOT_FOUND;

    // ===== UNIT TESTS =====
    @Test
    public void equals() {
        AddLogCommand command;
        AddLogCommand other;
        AddLogCommand.AddLogDescriptor descriptor;
        AddLogCommand.AddLogDescriptor otherDescriptor;
        Index targetIndex = INDEX_FIRST_PERSON;
        Index otherIndex = INDEX_SECOND_PERSON;
        Name targetName = new Name(VALID_NAME_AMY);
        Name otherName = new Name(VALID_NAME_BOB);
        String title = "some title";
        String otherTitle = "some other title";

        // same values -> returns true
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        command = new AddLogCommand(targetIndex, descriptor);
        other = new AddLogCommand(targetIndex, descriptor);
        assertEquals(command, other);

        command = new AddLogCommand(targetName, descriptor);
        other = new AddLogCommand(targetName, descriptor);
        assertEquals(command, other);

        // same object -> returns true
        assertEquals(command, command);

        // null -> returns false
        assertNotEquals(command, null);

        // different types -> returns false
        assertNotEquals(command, "some other type");

        // different index -> returns false
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        command = new AddLogCommand(targetIndex, descriptor);
        other = new AddLogCommand(otherIndex, descriptor);
        assertNotEquals(command, other);

        // different name -> returns false
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        command = new AddLogCommand(targetName, descriptor);
        other = new AddLogCommand(otherName, descriptor);
        assertNotEquals(command, other);

        // different descriptor -> returns false
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        otherDescriptor = new AddLogCommand.AddLogDescriptor();
        otherDescriptor.setNewTitle(otherTitle);
        command = new AddLogCommand(targetIndex, descriptor);
        other = new AddLogCommand(targetIndex, otherDescriptor);
        assertNotEquals(command, other);
    }


    /* ===== INTEGRATION TESTS WITH MODEL =====
    Integration tests with Model component. Note that we do tests for both filtered and unfiltered lists
    as these states control what the user sees, but regardless the changes apply to the true underlying
    data state of the model.
     */
    @Test
    public void execute_withIndexValidIInputUnfilteredList_success() {

        String title = VALID_LOG_TITLE;
        String description = VALID_LOG_DESCRIPTION;
        String expectedMessage = AddLogCommand.MESSAGE_ADD_LOG_SUCCESS;
        AddLogCommand command;
        Index targetIndex = INDEX_FIRST_PERSON;
        Log log;
        Person basePerson;
        Person addedLogPerson;
        Model expectedModel;
        AddLogCommand.AddLogDescriptor descriptor;
        Model model;

        // only title
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        log = new Log(title, null);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        basePerson = expectedModel.getFilteredPersonList().get(targetIndex.getZeroBased());
        addedLogPerson = new PersonBuilder(basePerson).withLogs(log).build();
        expectedModel.setPerson(basePerson, addedLogPerson);

        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        command = new AddLogCommand(targetIndex, descriptor);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // title and description
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        log = new Log(title, description);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        basePerson = expectedModel.getFilteredPersonList().get(targetIndex.getZeroBased());
        addedLogPerson = new PersonBuilder(basePerson).withLogs(log).build();
        expectedModel.setPerson(basePerson, addedLogPerson);

        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        descriptor.setNewDescription(description);
        command = new AddLogCommand(targetIndex, descriptor);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_withNameValidIInputUnfilteredList_success() {

        String title = VALID_LOG_TITLE;
        String description = VALID_LOG_DESCRIPTION;
        String expectedMessage = AddLogCommand.MESSAGE_ADD_LOG_SUCCESS;
        AddLogCommand command;
        Index targetIndex = INDEX_FIRST_PERSON;
        Name targetName;
        Log log;
        Person basePerson;
        Person addedLogPerson;
        Model expectedModel;
        AddLogCommand.AddLogDescriptor descriptor;
        Model model;

        // only title
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        log = new Log(title, null);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        basePerson = expectedModel.getFilteredPersonList().get(targetIndex.getZeroBased());
        targetName = basePerson.getName();
        addedLogPerson = new PersonBuilder(basePerson).withLogs(log).build();
        expectedModel.setPerson(basePerson, addedLogPerson);

        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        command = new AddLogCommand(targetName, descriptor);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // title and description
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        log = new Log(title, description);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        basePerson = expectedModel.getFilteredPersonList().get(targetIndex.getZeroBased());
        targetName = basePerson.getName();
        addedLogPerson = new PersonBuilder(basePerson).withLogs(log).build();
        expectedModel.setPerson(basePerson, addedLogPerson);

        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        descriptor.setNewDescription(description);
        command = new AddLogCommand(targetName, descriptor);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_withIndexValidInputFilteredList_success() {

        String title = VALID_LOG_TITLE;
        String description = VALID_LOG_DESCRIPTION;
        String expectedMessage = AddLogCommand.MESSAGE_ADD_LOG_SUCCESS;
        AddLogCommand command;
        Index targetIndex = INDEX_FIRST_PERSON;
        Name targetName;
        Log log;
        Person basePerson;
        Person addedLogPerson;
        Model expectedModel;
        AddLogCommand.AddLogDescriptor descriptor;
        Model model;

        // only title
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        showPersonAtIndex(model, INDEX_FIRST_PERSON); // apply filter

        log = new Log(title, null);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON); // apply filter

        basePerson = expectedModel.getFilteredPersonList().get(targetIndex.getZeroBased());
        targetName = basePerson.getName();
        addedLogPerson = new PersonBuilder(basePerson).withLogs(log).build();
        expectedModel.setPerson(basePerson, addedLogPerson);

        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        command = new AddLogCommand(targetName, descriptor);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // title and description
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        showPersonAtIndex(model, INDEX_FIRST_PERSON); // apply filter

        log = new Log(title, description);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON); // apply filter

        basePerson = expectedModel.getFilteredPersonList().get(targetIndex.getZeroBased());
        targetName = basePerson.getName();
        addedLogPerson = new PersonBuilder(basePerson).withLogs(log).build();
        expectedModel.setPerson(basePerson, addedLogPerson);

        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        descriptor.setNewDescription(description);
        command = new AddLogCommand(targetName, descriptor);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_withIndexDuplicateLog_failure() {

        String title = VALID_LOG_TITLE;
        Log log = new Log(title, null);
        Model testModel;
        Index targetIndex = INDEX_FIRST_PERSON;
        AddLogCommand.AddLogDescriptor descriptor;
        AddLogCommand command;
        Model model;
        Person withLogPerson;

        // ===== UNFILTERED LIST =====
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // add log into list
        testModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        withLogPerson = new PersonBuilder().withLogs(log).build(); // build person with log

        testModel.setPerson((model.getFilteredPersonList().get(targetIndex.getZeroBased())), withLogPerson); // add him in

        // try to add again
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        command = new AddLogCommand(targetIndex, descriptor);
        assertCommandFailure(command, testModel, AddLogCommand.MESSAGE_DUPLICATE_LOG);

        // ===== FILTERED LIST =====
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        showPersonAtIndex(model, INDEX_FIRST_PERSON); // apply filter

        // add log into list
        testModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        withLogPerson = new PersonBuilder().withLogs(log).build(); // build person with log
        testModel.setPerson((model.getFilteredPersonList().get(targetIndex.getZeroBased())), withLogPerson); // add him in

        // try to add again
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        command = new AddLogCommand(targetIndex, descriptor);
        assertCommandFailure(command, testModel, AddLogCommand.MESSAGE_DUPLICATE_LOG);
    }

    @Test
    public void execute_withNameDuplicateLog_failure() {

        String title = VALID_LOG_TITLE;
        Log log = new Log(title, null);
        Model testModel;
        Index targetIndex = INDEX_FIRST_PERSON;
        Name targetName;
        AddLogCommand.AddLogDescriptor descriptor;
        AddLogCommand command;
        Model model;
        Person withLogPerson;

        // ===== UNFILTERED LIST =====
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // add log into list
        testModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        withLogPerson = new PersonBuilder().withLogs(log).build(); // build person with log
        targetName = withLogPerson.getName();
        testModel.setPerson((model.getFilteredPersonList().get(targetIndex.getZeroBased())), withLogPerson); // add him in

        // try to add again
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        command = new AddLogCommand(targetName, descriptor);
        assertCommandFailure(command, testModel, AddLogCommand.MESSAGE_DUPLICATE_LOG);

        // ===== FILTERED LIST =====
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        showPersonAtIndex(model, INDEX_FIRST_PERSON); // apply filter

        // add log into list
        testModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        withLogPerson = new PersonBuilder().withLogs(log).build(); // build person with log
        targetName = withLogPerson.getName();
        testModel.setPerson((model.getFilteredPersonList().get(targetIndex.getZeroBased())), withLogPerson); // add him in

        // try to add again
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        command = new AddLogCommand(targetName, descriptor);
        assertCommandFailure(command, testModel, AddLogCommand.MESSAGE_DUPLICATE_LOG);
    }
    @Test //todo
    public void execute_invalidInputUnfilteredList_failure() {

        String title = VALID_LOG_TITLE;
        String invalidTitle = "";
        AddLogCommand command;
        Index outOfBoundIndex;
        AddLogCommand.AddLogDescriptor descriptor;
        Model model;

        // ===== INVALID INDEX =====

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        command = new AddLogCommand(outOfBoundIndex, descriptor);
        assertCommandFailure(command, model, MESSAGE_INVALID_INDEX);

        // ===== INVALID NAME =====

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Name notInList = new Name("somegibberish");
        command = new AddLogCommand(notInList, descriptor);
        assertCommandFailure(command, model, MESSAGE_PERSON_NOT_FOUND);


        // ===== INVALID TITLE =====

        assertThrows(AssertionError.class, () -> {
            Model m = new ModelManager(getTypicalAddressBook(), new UserPrefs());
            AddLogCommand.AddLogDescriptor d = new AddLogCommand.AddLogDescriptor();
            d.setNewTitle(invalidTitle);
            Command c = new AddLogCommand(INDEX_FIRST_PERSON, d);
            c.execute(m);
        });

        // ===== MISSING TITLE =====
        assertThrows(AssertionError.class, () -> {
            Command c = new AddLogCommand(INDEX_FIRST_PERSON, new AddLogCommand.AddLogDescriptor());
            Model m = new ModelManager(getTypicalAddressBook(), new UserPrefs());
            c.execute(m);

        });
    }
}

