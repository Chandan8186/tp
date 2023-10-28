package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX = "The employee index provided is invalid";
    public static final String MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX = "The project index provided is invalid";
    public static final String MESSAGE_EMPLOYEES_LISTED_OVERVIEW = "%1$d employee(s) and their project(s) listed!";
    public static final String MESSAGE_PROJECTS_LISTED_OVERVIEW = "%1$d project(s) and their employee(s) listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code employee} for display to the user.
     */
    public static String format(Employee employee) {
        final StringBuilder builder = new StringBuilder();
        builder.append(employee.getName())
                .append("; Phone: ")
                .append(employee.getPhone())
                .append("; Email: ")
                .append(employee.getEmail())
                .append("; Address: ")
                .append(employee.getAddress())
                .append("; Project: ")
                .append(employee.getProject())
                .append("; Tags: ");
        employee.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code project} for display to the user.
     */
    public static String format(Project project) {
        final StringBuilder builder = new StringBuilder();
        builder.append(project.name);
        builder.append("; Priority: " + project.getProjectPriority().value);
        builder.append("; Employees: ");
        for (Employee employee : project.getEmployees()) {
            builder.append(employee.getName() + ", ");
        }
        builder.substring(0, builder.lastIndexOf(", ") == -1 ? builder.length() : builder.lastIndexOf(", "));
        return builder.toString();
    }

    /**
     * Formats the {@code Task} for display to the user.
     */
    public static String format(Task task) {
        final StringBuilder builder = new StringBuilder();
        builder.append(task.getName());
        builder.append("; Deadline: " + task.getDeadline().toString());
        return builder.toString();
    }

}
