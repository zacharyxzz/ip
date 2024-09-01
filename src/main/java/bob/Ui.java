package bob;

import java.util.ArrayList;

public class Ui {
    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Bob");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public void showGoodbye() {
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    public void showLoadingError() {
        System.out.println("Error loading tasks. Starting with an empty task list.");
    }


    /**
     * Displays the list of tasks to the user.
     *
     * @param tasks the TaskList containing the tasks to be displayed.
     */
    public void showTaskList(TaskList tasks) throws BobException {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println("No tasks added yet.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
        showLine();
    }


    /**
     * Displays a message indicating that a task has been removed.
     *
     * @param task the task that was removed.
     * @param tasks the TaskList containing all tasks.
     */
    public void showAddedTask(Task task, TaskList tasks) {
        showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        showLine();
    }

    public void showRemovedTask(Task task, TaskList tasks) {
        showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        showLine();
    }

    /**
     * Displays the list of tasks that match the search keyword.
     *
     * @param matchingTasks the list of matching tasks to be displayed.
     */
    public void showMatchingTasks(ArrayList<Task> matchingTasks) {
        showLine();
        if (matchingTasks.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + "." + matchingTasks.get(i));
            }
        }
        showLine();
    }
}