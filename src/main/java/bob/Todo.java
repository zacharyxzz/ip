package bob;
class Todo extends Task {

    /**
     * Constructs a Todo task with a description.
     *
     * @param description the description of the task.
     */
    public Todo(String description) {
        super(description);
    }


    /**
     * Constructs a Todo task with a description and a completion status.
     *
     * @param description the description of the task.
     * @param isDone the completion status of the task.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }


    /**
     * Returns a string representation of the Todo task formatted for saving to a file.
     *
     * @return a formatted string for file storage.
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}