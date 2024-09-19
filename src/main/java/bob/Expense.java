package bob;

class Expense extends Task {

    protected double amount;
    /**
     * Constructs a Todo task with a description.
     *
     * @param description the description of the task.
     */
    public Expense(String description, Double amount) {
        super(description);
        this.amount = amount;
    }

    /**
     * Returns a string representation of the Todo task formatted for saving to a file.
     *
     * @return a formatted string for file storage.
     */
    @Override
    public String toFileFormat() {
        return "Expense | " + description + " | " + amount;
    }

    @Override
    public String toString() {
        return "[Ex] " + description + " " + amount;
    }
}
