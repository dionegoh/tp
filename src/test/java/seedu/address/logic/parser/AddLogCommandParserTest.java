package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOG_TITLE_EMPTY_STRING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOG_TITLE_ONLY_SPACES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOG_TITLE_TOO_LONG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOG_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOG_DESCRIPTION_DIFFERENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOG_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOG_TITLE_DESC_PRECEDING_SPACE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_DESCRIPTION_OTHER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_TITLE_PRECEDING_SPACE_TRIMMED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.AddLogCommandParser.MESSAGE_INVALID_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLogCommand;
import seedu.address.logic.commands.Command;
import seedu.address.model.person.Log;
import seedu.address.model.person.Name;

public class AddLogCommandParserTest {

    private static final String MESSAGE_INVALID_TITLE = Log.TITLE_CONSTRAINTS;
    private final AddLogCommandParser parser = new AddLogCommandParser();

    @Test
    public void parse_missingParts_failure() {

        String args;

        // no index or name specified
        args = LOG_TITLE_DESC;
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);
        args = LOG_TITLE_DESC + LOG_DESCRIPTION_DESC;
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);

        // both index and name specified
        args = INDEX_FIRST_PERSON.getOneBased() + NAME_DESC_AMY + LOG_TITLE_DESC;
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);

        // no title specified
        args = "1" + LOG_DESCRIPTION_DESC; // with description
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);
        args = "1";
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);

        // no index and no title specified
        args = LOG_DESCRIPTION_DESC;
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        /*
        Preamble (i.e. argument before any /PREFIX) used as index. User could throw garbage input.
         */

        String args;

        // negative index
        args = "-5" + LOG_TITLE_DESC;
        assertParseFailure(parser, args, MESSAGE_INVALID_INDEX);

        // zero index, since list should be 1-indexed
        args = "0" + LOG_TITLE_DESC;
        assertParseFailure(parser, args, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        args = "somegarbagepreamble" + LOG_TITLE_DESC;
        assertParseFailure(parser, args, MESSAGE_INVALID_INDEX);

    }

    @Test
    public void parse_invaildPrefixesPresent_failure() {
        String args;

        // random garbage input
        args = "1 i/ string";
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);
        args = "1 i/string /g something else";
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {

        String args;

        // ===== WITH INDEX =====
        // invalid titles
        args = "1" + INVALID_LOG_TITLE_EMPTY_STRING_DESC;
        assertParseFailure(parser, args, MESSAGE_INVALID_TITLE);
        args = "1" + INVALID_LOG_TITLE_ONLY_SPACES_DESC;
        assertParseFailure(parser, args, MESSAGE_INVALID_TITLE);
        args = "1" + INVALID_LOG_TITLE_TOO_LONG_DESC;
        assertParseFailure(parser, args, MESSAGE_INVALID_TITLE);

        // prefixes without actual arguments
        args = "1" + " " + PREFIX_TITLE;
        assertParseFailure(parser, args, MESSAGE_INVALID_TITLE);
        args = "1" + " " + PREFIX_TITLE + " " + PREFIX_DESCRIPTION;
        assertParseFailure(parser, args, MESSAGE_INVALID_TITLE);
        args = "1" + " " + PREFIX_TITLE + " " + PREFIX_DESCRIPTION;
        assertParseFailure(parser, args, MESSAGE_INVALID_TITLE);

        // ===== WITH NAME =====
        args = NAME_DESC_AMY + INVALID_LOG_TITLE_EMPTY_STRING_DESC;
        assertParseFailure(parser, args, MESSAGE_INVALID_TITLE);
        args = NAME_DESC_AMY + INVALID_LOG_TITLE_ONLY_SPACES_DESC;
        assertParseFailure(parser, args, MESSAGE_INVALID_TITLE);
        args = NAME_DESC_AMY + INVALID_LOG_TITLE_TOO_LONG_DESC;
        assertParseFailure(parser, args, MESSAGE_INVALID_TITLE);

        // prefixes without actual arguments
        args = NAME_DESC_AMY + " " + PREFIX_TITLE;
        assertParseFailure(parser, args, MESSAGE_INVALID_TITLE);
        args = NAME_DESC_AMY + " " + PREFIX_TITLE + " " + PREFIX_DESCRIPTION;
        assertParseFailure(parser, args, MESSAGE_INVALID_TITLE);
        args = NAME_DESC_AMY + " " + PREFIX_TITLE + " " + PREFIX_DESCRIPTION;
        assertParseFailure(parser, args, MESSAGE_INVALID_TITLE);
    }

    @Test
    public void parse_allFieldsValid_success() {
        Command expectedCommand;
        String args;
        AddLogCommand.AddLogDescriptor descriptor;
        Index targetIndex = INDEX_FIRST_PERSON;

        // ===== WITH INDEX =====
        // valid title
        args = targetIndex.getOneBased() + LOG_TITLE_DESC;
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(VALID_LOG_TITLE);
        expectedCommand = new AddLogCommand(targetIndex, descriptor);
        assertParseSuccess(parser, args, expectedCommand);

        // another valid title
        args = targetIndex.getOneBased() + LOG_TITLE_DESC_PRECEDING_SPACE;
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(VALID_LOG_TITLE_PRECEDING_SPACE_TRIMMED);
        expectedCommand = new AddLogCommand(targetIndex, descriptor);
        assertParseSuccess(parser, args, expectedCommand);

        // valid title and description
        args = targetIndex.getOneBased() + LOG_TITLE_DESC + LOG_DESCRIPTION_DESC;
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(VALID_LOG_TITLE);
        descriptor.setNewDescription(VALID_LOG_DESCRIPTION);
        expectedCommand = new AddLogCommand(targetIndex, descriptor);
        assertParseSuccess(parser, args, expectedCommand);

        // ===== WITH NAME =====
        Name targetName = new Name(VALID_NAME_AMY);

        // valid title
        args = NAME_DESC_AMY + LOG_TITLE_DESC;
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(VALID_LOG_TITLE);
        expectedCommand = new AddLogCommand(targetName, descriptor);
        assertParseSuccess(parser, args, expectedCommand);

        // another valid title
        args = NAME_DESC_AMY + LOG_TITLE_DESC_PRECEDING_SPACE;
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(VALID_LOG_TITLE_PRECEDING_SPACE_TRIMMED);
        expectedCommand = new AddLogCommand(targetName, descriptor);
        assertParseSuccess(parser, args, expectedCommand);

        // valid title and description
        args = NAME_DESC_AMY + LOG_TITLE_DESC + LOG_DESCRIPTION_DESC;
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(VALID_LOG_TITLE);
        descriptor.setNewDescription(VALID_LOG_DESCRIPTION);
        expectedCommand = new AddLogCommand(targetName, descriptor);
        assertParseSuccess(parser, args, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {

        // ===== WITH INDEX =====
        Index targetIndex = INDEX_FIRST_PERSON;
        String args = targetIndex.getOneBased() + LOG_TITLE_DESC + LOG_TITLE_DESC_PRECEDING_SPACE
                + LOG_DESCRIPTION_DESC + LOG_DESCRIPTION_DIFFERENT_DESC;

        AddLogCommand.AddLogDescriptor descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(VALID_LOG_TITLE_PRECEDING_SPACE_TRIMMED);
        descriptor.setNewDescription(VALID_LOG_DESCRIPTION_OTHER);
        AddLogCommand expectedCommand = new AddLogCommand(targetIndex, descriptor);

        assertParseSuccess(parser, args, expectedCommand);

        // ===== WITH NAME =====
        // expected to take last name
        Name targetName = new Name(VALID_NAME_BOB);
        args = NAME_DESC_AMY + NAME_DESC_BOB + LOG_TITLE_DESC + LOG_DESCRIPTION_DESC;
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(VALID_LOG_TITLE);
        descriptor.setNewDescription(VALID_LOG_DESCRIPTION);
        expectedCommand = new AddLogCommand(targetName, descriptor);
        assertParseSuccess(parser, args, expectedCommand);
    }

    @Test
    public void parse_multipleInvalidFieldsButLastValid_success() {

        // ===== WITH INDEX =====
        Index targetIndex = INDEX_FIRST_PERSON;
        String args = targetIndex.getOneBased() + INVALID_LOG_TITLE_EMPTY_STRING_DESC + LOG_TITLE_DESC_PRECEDING_SPACE
                + LOG_DESCRIPTION_DESC + LOG_DESCRIPTION_DIFFERENT_DESC;
        AddLogCommand.AddLogDescriptor descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(VALID_LOG_TITLE_PRECEDING_SPACE_TRIMMED);
        descriptor.setNewDescription(VALID_LOG_DESCRIPTION_OTHER);
        AddLogCommand expectedCommand = new AddLogCommand(targetIndex, descriptor);
        assertParseSuccess(parser, args, expectedCommand);

        // ===== WITH NAME =====
        // expected to take last name
        Name targetName = new Name(VALID_NAME_BOB);
        args = INVALID_NAME_DESC + NAME_DESC_BOB + LOG_TITLE_DESC + LOG_DESCRIPTION_DESC;
        descriptor = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(VALID_LOG_TITLE);
        descriptor.setNewDescription(VALID_LOG_DESCRIPTION);
        expectedCommand = new AddLogCommand(targetName, descriptor);
        assertParseSuccess(parser, args, expectedCommand);
    }


}
