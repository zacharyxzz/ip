package bob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Parser {

    /**
     * Parses and executes the user's input command.
     *
     * @param input the input command from the user.
     * @param tasks the TaskList that the command will operate on.
     * @param ui the Ui object used to interact with the user.
     * @param storage the Storage object used to save and load tasks.
     */
    public static void parse(String input, TaskList tasks, Ui ui, Storage storage) {
        String[] inputSplit = input.split(" ", 2);

        try {
            switch (inputSplit[0]) {
                case "bye":
                    ui.showGoodbye();
                    System.exit(0);
                    break;
                case "list":
                    ui.showTaskList(tasks);
                    break;
                case "mark":
                    handleMarkCommand(inputSplit, tasks, ui, storage);
                    break;
                case "unmark":
                    handleUnmarkCommand(inputSplit, tasks, ui, storage);
                    break;
                case "todo":
                    handleTodoCommand(inputSplit, tasks, ui, storage);
                    break;
                case "deadline":
                    handleDeadlineCommand(inputSplit, tasks, ui, storage);
                    break;
                case "event":
                    handleEventCommand(inputSplit, tasks, ui, storage);
                    break;
                case "delete":
                    handleDeleteCommand(inputSplit, tasks, ui, storage);
                    break;
                case "find":
                    handleFindCommand(inputSplit, tasks, ui);
                    break;
                default:
                    throw new BobException("I'm sorry, but I don't know what that means :-(");
            }
        } catch (BobException e) {
            ui.showError(e.getMessage());
        }
    }

    // The following methods are helper methods to handle specific commands

    private static void handleFindCommand(String[] inputSplit, TaskList tasks, Ui ui) throws BobException {
        if (inputSplit.length < 2 || inputSplit[1].trim().isEmpty()) {
            throw new BobException("The search keyword cannot be empty.");
        }
        String keyword = inputSplit[1];
        ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
        ui.showMatchingTasks(matchingTasks);
    }

    private static void handleMarkCommand(String[] inputSplit, TaskList tasks, Ui ui, Storage storage) throws BobException {
        int index = Integer.parseInt(inputSplit[1]) - 1;
        tasks.get(index).markAsDone();
        ui.showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + tasks.get(index));
        ui.showLine();
        storage.save(tasks);
    }

    private static void handleUnmarkCommand(String[] inputSplit, TaskList tasks, Ui ui, Storage storage) throws BobException {
        int index = Integer.parseInt(inputSplit[1]) - 1;
        tasks.get(index).unmarkAsDone();
        ui.showLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + tasks.get(index));
        ui.showLine();
        storage.save(tasks);
    }

    private static void handleTodoCommand(String[] inputSplit, TaskList tasks, Ui ui, Storage storage) throws BobException {
        if (inputSplit.length < 2 || inputSplit[1].trim().isEmpty()) {
            throw new BobException("The description of a todo cannot be empty.");
        }
        Task task = new Todo(inputSplit[1]);
        tasks.addTask(task);
        ui.showAddedTask(task, tasks);
        storage.save(tasks);
    }

    private static void handleDeadlineCommand(String[] inputSplit, TaskList tasks, Ui ui, Storage storage) throws BobException {
        if (inputSplit.length < 2 || !inputSplit[1].contains(" /by ")) {
            throw new BobException("The description of a deadline must include '/by' followed by a date/time.");
        }
        String[] parts = inputSplit[1].split(" /by ", 2);
        try {
            LocalDateTime deadline = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            Task task = new Deadline(parts[0], deadline);
            tasks.addTask(task);
            ui.showAddedTask(task, tasks);
            storage.save(tasks);
        } catch (DateTimeParseException e) {
            throw new BobException("Invalid date/time format. Please use 'yyyy-MM-dd HHmm'.");
        }
    }

    private static void handleEventCommand(String[] inputSplit, TaskList tasks, Ui ui, Storage storage) throws BobException {
        if (inputSplit.length < 2 || !inputSplit[1].contains(" /from ") || !inputSplit[1].contains(" /to ")) {
            throw new BobException("The description of an event must include '/from' followed by start time and '/to' followed by end time.");
        }
        String[] parts = inputSplit[1].split(" /from ", 2);
        String[] fromTo = parts[1].split(" /to ", 2);

        try {
            LocalDateTime from = LocalDateTime.parse(fromTo[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            LocalDateTime to = LocalDateTime.parse(fromTo[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            Task task = new Event(parts[0], from, to);
            tasks.addTask(task);
            ui.showAddedTask(task, tasks);
            storage.save(tasks);
        } catch (DateTimeParseException e) {
        throw new BobException("Invalid date/time format. Please use 'yyyy-MM-dd HHmm'.");
        }
    }

    private static void handleDeleteCommand(String[] inputSplit, TaskList tasks, Ui ui, Storage storage) throws BobException {
        int index = Integer.parseInt(inputSplit[1]) - 1;
        Task removedTask = tasks.removeTask(index);
        ui.showRemovedTask(removedTask, tasks);
        storage.save(tasks);
    }
}
