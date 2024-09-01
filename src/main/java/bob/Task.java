package bob;
class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with a description.
     *
     * @param description the description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructs a Task with a description and a completion status.
     *
     * @param description the description of the task.
     * @param isDone the completion status of the task.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if the task is done, otherwise " ".
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        isDone = true;
    }

    public void unmarkAsDone() {
        isDone = false;
    }

    public String toFileFormat() {
        return "";
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}