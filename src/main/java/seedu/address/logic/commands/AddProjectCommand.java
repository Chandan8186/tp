package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Project;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

public class AddProjectCommand extends Command{
    public static final String COMMAND_WORD = "addP";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a project to the TaskHub. "
            + "Parameters: "
            + PREFIX_PROJECT + "PROJECT_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROJECT + "Rebuild Taskhub";


    public static final String MESSAGE_SUCCESS = "New project added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the TaskHub";

    private final Project toAdd;
    private final List<Index> employeeIndexes;

    /**
     * Creates an AddCommand to add the specified {@code Project}
     */
    public AddProjectCommand(Project project, List<Index> employeeIndexes) {
        requireNonNull(project);
        toAdd = project;
        this.employeeIndexes = employeeIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Employee> lastShownList = model.getFilteredEmployeeList();
        for (Index targetIndex : employeeIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
            }
            //changes project of employee
            Employee employeeToAdd = lastShownList.get(targetIndex.getZeroBased());
            EditCommand.EditEmployeeDescriptor editEmployeeDescriptor = new EditCommand.EditEmployeeDescriptor();
            editEmployeeDescriptor.setProject(toAdd);
            editEmployeeDescriptor.setName(employeeToAdd.getName());
            editEmployeeDescriptor.setAddress(employeeToAdd.getAddress());
            editEmployeeDescriptor.setEmail(employeeToAdd.getEmail());
            editEmployeeDescriptor.setPhone(employeeToAdd.getPhone());
            editEmployeeDescriptor.setTags(employeeToAdd.getTags());
            new EditCommand(targetIndex,editEmployeeDescriptor).execute(model);

            //removes employee from previous project
            if (employeeToAdd.getProject().name != "") {
                //TODO: get project by name
            }
            toAdd.addEmployee(employeeToAdd);
        }

        if (model.hasProject(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        model.addProject(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddProjectCommand otherAddProjectCommand = (AddProjectCommand) other;
        return toAdd.equals(otherAddProjectCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
