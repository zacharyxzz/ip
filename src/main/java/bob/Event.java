package bob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructs an Event task with a description, start time, and end time.
     *
     * @param description the description of the task.
     * @param from the start date and time of the event.
     * @param to the end date and time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs an Event task with a description, start time, end time, and a completion status.
     *
     * @param description the description of the task.
     * @param from the start date and time of the event.
     * @param to the end date and time of the event.
     * @param isDone the completion status of the task.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM dd yyyy h:mma")) + " to: " + to.format(DateTimeFormatter.ofPattern("MMM dd yyyy h:mma")) + ")";
    }
}
