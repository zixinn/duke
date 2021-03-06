package duke.task;

import duke.exception.DoneException;
import duke.exception.DukeException;

/**
 * Represents a task.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the specified description.
     *
     * @param description The description of the Task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructs a Task with the specified description and status.
     *
     * @param description The description of the Task.
     * @param isDone Whether the Task is done.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the status of the task.
     *
     * @return Y if the task is done and N is the task is not done.
     */
    public String getStatusIcon() {
        return (isDone ? "Y" : "N");
    }

    /**
     * Marks the task as done.
     *
     * @throws DukeException If the task is already marked as done.
     */
    public void markAsDone() throws DukeException {
        if (isDone) {
            throw new DoneException();
        }
        this.isDone = true;
    }

    /**
     * Returns a string representation of the task for saving to the disk.
     *
     * @return String representation of the task for saving to the disk.
     */
    public String formatToSave() {
        return " | " + (isDone ? 1 : 0) + " | " + description;
    }

    /**
     * Returns a string representation of the task for printing.
     *
     * @return String representation of the task for printing.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
