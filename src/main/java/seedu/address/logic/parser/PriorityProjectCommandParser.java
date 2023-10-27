package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PriorityProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.ProjectPriority;

/**
 * Parses input arguments and creates a new PriorityProjectCommand object.
 */
public class PriorityProjectCommandParser implements Parser<PriorityProjectCommand> {

    /**
     * Parses the given String of argumetns in the context of the PriorityProjectCommand and returns
     * a PriorityProjectCommand for execution.
     *
     * @throws ParseException if the user does not conform to the expected format.
     */
    public PriorityProjectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PRIORITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_PRIORITY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityProjectCommand.MESSAGE_USAGE));
        }

        Index projectIndex;
        try {
            projectIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityProjectCommand.MESSAGE_USAGE), ive);
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PRIORITY);
        ProjectPriority priority = ParserUtil.parseProjectPriority(argMultimap.getValue(PREFIX_PRIORITY).get());

        return new PriorityProjectCommand(priority, projectIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}