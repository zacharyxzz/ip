package bob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Deadline extends Task {
    protected LocalDateTime by;


    /**
     * Constructs a Deadline task with a description and a due date/time.
     *
     * @param description the description of the task.
     * @param by the date and time by which the task is due.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }


    /**
     * Constructs a Deadline task with a description, a due date/time, and a completion status.
     *
     * @param description the description of the task.
     * @param by the date and time by which the task is due.
     * @param isDone the completion status of the task.
     */
    public Deadline(String description, LocalDateTime by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }


    /**
     * Returns a string representation of the Deadline task formatted for saving to a file.
     *
     * @return a formatted string for file storage.
     */
    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy h:mma")) + ")";
    }
}
