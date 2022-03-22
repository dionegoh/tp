package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEW_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEW_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEW_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEW_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEW_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEW_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NEW_ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NEW_ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NEW_DESCRIPTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NEW_DESCRIPTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NEW_EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NEW_EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NEW_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NEW_NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NEW_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NEW_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NEW_TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.NEW_TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.common.Description;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.FriendName;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String NEW_TAG_EMPTY = " " + PREFIX_NEW_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NEW_NAME_DESC, FriendName.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_NEW_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_NEW_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_NEW_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_NEW_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS); //invalid description
        assertParseFailure(parser, "1" + INVALID_NEW_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_NEW_PHONE_DESC + NEW_EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + NEW_PHONE_DESC_BOB + INVALID_NEW_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + NEW_TAG_DESC_FRIEND + NEW_TAG_DESC_HUSBAND + NEW_TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + NEW_TAG_DESC_FRIEND + NEW_TAG_EMPTY + NEW_TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + NEW_TAG_EMPTY + NEW_TAG_DESC_FRIEND + NEW_TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NEW_NAME_DESC + INVALID_NEW_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                FriendName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + NEW_PHONE_DESC_BOB + NEW_TAG_DESC_HUSBAND
                + NEW_EMAIL_DESC_AMY + NEW_ADDRESS_DESC_AMY + NEW_NAME_DESC_AMY + NEW_TAG_DESC_FRIEND
                + NEW_DESCRIPTION_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withDescription(VALID_DESCRIPTION_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + NEW_PHONE_DESC_BOB + NEW_EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NEW_NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + NEW_PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + NEW_EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + NEW_ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //description
        userInput = targetIndex.getOneBased() + NEW_DESCRIPTION_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withDescription(VALID_DESCRIPTION_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + NEW_TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + NEW_PHONE_DESC_AMY + NEW_ADDRESS_DESC_AMY + NEW_EMAIL_DESC_AMY
                + NEW_TAG_DESC_FRIEND + NEW_PHONE_DESC_AMY + NEW_ADDRESS_DESC_AMY + NEW_EMAIL_DESC_AMY + NEW_TAG_DESC_FRIEND
                + NEW_PHONE_DESC_BOB + NEW_ADDRESS_DESC_BOB + NEW_EMAIL_DESC_BOB + NEW_TAG_DESC_HUSBAND
                + NEW_DESCRIPTION_DESC_AMY + NEW_DESCRIPTION_DESC_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withDescription(VALID_DESCRIPTION_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {

        //name
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_NEW_NAME_DESC + NEW_NAME_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        targetIndex = INDEX_FIRST_PERSON;
        userInput = targetIndex.getOneBased() + INVALID_NEW_PHONE_DESC + NEW_PHONE_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + INVALID_NEW_ADDRESS_DESC + NEW_ADDRESS_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + INVALID_NEW_EMAIL_DESC + NEW_EMAIL_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //description
        userInput = targetIndex.getOneBased() + INVALID_NEW_DESCRIPTION_DESC + NEW_DESCRIPTION_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withDescription(VALID_DESCRIPTION_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //combination of arguments
        userInput = targetIndex.getOneBased() + INVALID_NEW_ADDRESS_DESC + INVALID_NEW_EMAIL_DESC
                + NEW_ADDRESS_DESC_BOB + INVALID_NEW_NAME_DESC + NEW_NAME_DESC_BOB + NEW_PHONE_DESC_BOB
                + NEW_EMAIL_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_BOB).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NEW_TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
