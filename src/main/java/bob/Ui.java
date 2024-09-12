package bob;

import java.util.ArrayList;

public class Ui {
    public String showWelcome() {
        return "_______________________________________________________\n" +
                "Hello! I'm Bob\n" +
                "What can I do for you?\n" +
                "_______________________________________________________";
    }

    public String showGoodbye() {
        return "_______________________________________________________\n" +
                "Bye. Hope to see you again soon!\n" +
                "_______________________________________________________";
    }

    public String showLine() {
        return "_______________________________________________________";
    }

    public String showError(String message) {
        return "OOPS!!! " + message;
    }

    public String showLoadingError() {
        return "Error loading tasks. Starting with an empty task list.";
    }

    /**
     * Displays the list of tasks to the user.
     *
     * @param tasks the TaskList containing the tasks to be displayed.
     */
    public String showTaskList(TaskList tasks) throws BobException {
        StringBuilder sb = new StringBuilder();
        sb.append(showLine()).append("\n");
        if (tasks.isEmpty()) {
            sb.append("No tasks added yet.\n");
        } else {
            sb.append("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
            }
        }
        sb.append(showLine());
        return sb.toString();
    }

    /**
     * Displays a message indicating that a task has been added.
     *
     * @param task the task that was added.
     * @param tasks the TaskList containing all tasks.
     */
    public String showAddedTask(Task task, TaskList tasks) {
        return showLine() + "\n" +
                "Got it. I've added this task:\n" +
                "  " + task + "\n" +
                "Now you have " + tasks.size() + " tasks in the list.\n" +
                showLine();
    }

    /**
     * Displays a message indicating that a task has been removed.
     *
     * @param task the task that was removed.
     * @param tasks the TaskList containing all tasks.
     */
    public String showRemovedTask(Task task, TaskList tasks) {
        return showLine() + "\n" +
                "Noted. I've removed this task:\n" +
                "  " + task + "\n" +
                "Now you have " + tasks.size() + " tasks in the list.\n" +
                showLine();
    }

    /**
     * Displays the list of tasks that match the search keyword.
     *
     * @param matchingTasks the list of matching tasks to be displayed.
     */
    public String showMatchingTasks(ArrayList<Task> matchingTasks) {
        StringBuilder sb = new StringBuilder();
        sb.append(showLine()).append("\n");
        if (matchingTasks.isEmpty()) {
            sb.append("No matching tasks found.\n");
        } else {
            sb.append("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                sb.append((i + 1)).append(".").append(matchingTasks.get(i)).append("\n");
            }
        }
        sb.append(showLine());
        return sb.toString();
    }
}
