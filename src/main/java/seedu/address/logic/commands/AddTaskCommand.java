package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;



/**
 * Changes the remark of an existing person in the address book.
 */
public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "addT";

    public static final String MESSAGE_SUCCESS = "New task to project %1$s, %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the TaskHub.\n"
            + "Parameters: "
            + PREFIX_NAME + "TASK_NAME\n"
            + PREFIX_PROJECT + "PROJECT_INDEX\n"
            + PREFIX_DEADLINE + "TASK_DEADLINE{yyyy-MM-dd HHmm}\n\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_NAME + "name of task"
            + PREFIX_PROJECT + "1 "
            + PREFIX_DEADLINE + "2023-11-31 2359";

    private final Task task;
    private final Index projectIndex;

    /**
     * Constructs an AddTaskCommand.
     * @param task The task to be added.
     * @param projectIndex The index of the project that should contain the task.
     */
    public AddTaskCommand(Task task, Index projectIndex) {

        this.task = task;
        this.projectIndex = projectIndex;
    }



    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // TODO modifying employee ? do we need to do this? employees should know all the tasks that they have?
        List<Project> lastShownProjectList = model.getFilteredProjectList();
        if (projectIndex.getZeroBased() >= lastShownProjectList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }
        Project projectToEdit = lastShownProjectList.get(projectIndex.getZeroBased());

        // Add a task to the project with the selected index.
        projectToEdit.addTask(this.task);

        Project editedProject = new Project(projectToEdit.name, projectToEdit.employeeList, projectToEdit.getTasks(),
                projectToEdit.getProjectPriority(), projectToEdit.deadline);

        // update model and filtered list for Ui update.
        model.setProject(projectToEdit, editedProject);
        model.addTask(this.task);

        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, projectIndex.getOneBased(),
                                               Messages.format(this.task)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (other instanceof AddTaskCommand) {
            AddTaskCommand e = (AddTaskCommand) other;
            return this.task.equals(e.task);
        }
        return false;
    }
}
