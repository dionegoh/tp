package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentMultimap.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLogCommand;
import seedu.address.logic.commands.AddLogCommand.AddLogDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AddLogCommand object
 */
public class AddLogCommandParser implements Parser<AddLogCommand> {

    public static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLogCommand.MESSAGE_USAGE);

    /**
     * Parses a string of arguments and creates an {@code AddLogCommand}.
     *
     * @throws ParseException if command string is formatted wrongly
     */
    public AddLogCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TITLE, PREFIX_DESCRIPTION);

        Name name = null;
        Index index = null;

        // ensure title prefix is present
        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE)) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        // ensure exactly one of index or name prefix is present
        boolean hasIndex = !argMultimap.getPreamble().isEmpty();
        boolean hasName = arePrefixesPresent(argMultimap, PREFIX_NAME);
        if ((!hasIndex && !hasName)
            || (hasIndex && hasName)) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        // parse index or name
        if (hasIndex) {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } else {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        }

        // wrap new log into helper class
        AddLogDescriptor addLogDescriptor = new AddLogDescriptor();

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            addLogDescriptor.setNewTitle(ParserUtil.parseTitle(argMultimap
                    .getValue(PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            addLogDescriptor.setNewDescription(ParserUtil.parseLogDescription(argMultimap
                    .getValue(PREFIX_DESCRIPTION).get()));
        }

        // check validity
        if (!addLogDescriptor.isTitleEdited()) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        AddLogCommand command;
        if (hasIndex) {
            command = new AddLogCommand(index, addLogDescriptor);
        } else {
            command = new AddLogCommand(name, addLogDescriptor);
        }
        return command;
    }
}
