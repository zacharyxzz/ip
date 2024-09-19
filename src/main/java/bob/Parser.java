package bob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Parser {

    public static String parse(String input, TaskList tasks, Ui ui, Storage storage) {
        String[] inputSplit = input.split(" ");
        String command = inputSplit[0];

        try {
            switch (command) {
                case "bye":
                    return ui.showGoodbye();
                case "list":
                    return ui.showTaskList(tasks);
                case "mark":
                    return handleMarkCommand(inputSplit, tasks, ui, storage);
                case "unmark":
                    return handleUnmarkCommand(inputSplit, tasks, ui, storage);
                case "todo":
                    return handleTodoCommand(inputSplit, tasks, ui, storage);
                case "deadline":
                    return handleDeadlineCommand(inputSplit, tasks, ui, storage);
                case "event":
                    return handleEventCommand(inputSplit, tasks, ui, storage);
                case "delete":
                    return handleDeleteCommand(inputSplit, tasks, ui, storage);
                case "expense":
                    return handleExpenseCommand(inputSplit, tasks, ui, storage);
                case "find":
                    return handleFindCommand(inputSplit, tasks, ui);
                default:
                    throw new BobException("I'm sorry, but I don't know what that means :-(");
            }
        } catch (BobException e) {
            return ui.showError(e.getMessage());
        }
    }

    // Helper methods to handle specific commands

    private static String handleExpenseCommand(String[] inputSplit, TaskList tasks, Ui ui, Storage storage) throws BobException {
        if (inputSplit.length < 3) {
            throw new BobException("An Expense command should follow this format: expense [name] [amount]");
        }

        String expenseName = inputSplit[1].trim();
        String amountStr = inputSplit[2].trim();

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            throw new BobException("The amount should be a valid number.");
        }
        Task task = new Expense(expenseName, amount);
        tasks.addTask(task);
        storage.save(tasks);
        return "Expense '" + expenseName + "' of amount " + amount + " recorded!";
    }

    private static String handleFindCommand(String[] inputSplit, TaskList tasks, Ui ui) throws BobException {
        if (inputSplit.length < 2 || inputSplit[1].trim().isEmpty()) {
            throw new BobException("The search keyword cannot be empty.");
        }
        String keyword = inputSplit[1];
        ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
        return ui.showMatchingTasks(matchingTasks);
    }

    private static String handleMarkCommand(String[] inputSplit,
                                            TaskList tasks, Ui ui, Storage storage) throws BobException {
        int index = Integer.parseInt(inputSplit[1]) - 1;
        assert index >= 0 && index < tasks.size();
        tasks.get(index).markAsDone();
        storage.save(tasks);
        return "Nice! I've marked this task as done:\n  " + tasks.get(index);
    }

    private static String handleUnmarkCommand(String[] inputSplit,
                                              TaskList tasks, Ui ui, Storage storage) throws BobException {
        int index = Integer.parseInt(inputSplit[1]) - 1;
        assert index >= 0 && index < tasks.size();
        tasks.get(index).unmarkAsDone();
        storage.save(tasks);
        return "OK, I've marked this task as not done yet:\n  " + tasks.get(index);
    }

    private static String handleTodoCommand(String[] inputSplit,
                                            TaskList tasks, Ui ui, Storage storage) throws BobException {
        if (inputSplit.length < 2 || inputSplit[1].trim().isEmpty()) {
            throw new BobException("The description of a todo cannot be empty.");
        }
        Task task = new Todo(inputSplit[1]);
        tasks.addTask(task);
        storage.save(tasks);
        return ui.showAddedTask(task, tasks);
    }

    private static String handleDeadlineCommand(String[] inputSplit,
                                                TaskList tasks, Ui ui, Storage storage) throws BobException {
        if (inputSplit.length < 2 || !inputSplit[1].contains(" /by ")) {
            throw new BobException("The description of a deadline must include '/by' followed by a date/time.");
        }
        String[] parts = inputSplit[1].split(" /by ", 2);
        try {
            LocalDateTime deadline = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            Task task = new Deadline(parts[0], deadline);
            tasks.addTask(task);
            storage.save(tasks);
            return ui.showAddedTask(task, tasks);
        } catch (DateTimeParseException e) {
            throw new BobException("Invalid date/time format. Please use 'yyyy-MM-dd HHmm'.");
        }
    }

    private static String handleEventCommand(String[] inputSplit,
                                             TaskList tasks, Ui ui, Storage storage) throws BobException {
        if (inputSplit.length < 2 || !inputSplit[1].contains(" /from ") || !inputSplit[1].contains(" /to ")) {
            throw new BobException("The description of an event must include '/from' " +
                    "followed by start time and '/to' followed by end time.");
        }
        String[] parts = inputSplit[1].split(" /from ", 2);
        String[] eventStartEnd = parts[1].split(" /to ", 2);

        try {
            LocalDateTime from = LocalDateTime.parse(eventStartEnd[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            LocalDateTime to = LocalDateTime.parse(eventStartEnd[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            Task task = new Event(parts[0], from, to);
            tasks.addTask(task);
            storage.save(tasks);
            return ui.showAddedTask(task, tasks);
        } catch (DateTimeParseException e) {
            throw new BobException("Invalid date/time format. Please use 'yyyy-MM-dd HHmm'.");
        }
    }

    private static String handleDeleteCommand(
            String[] inputSplit, TaskList tasks, Ui ui, Storage storage)
            throws BobException {
        int index = Integer.parseInt(inputSplit[1]) - 1;
        assert index >= 0 && index < tasks.size();
        Task removedTask = tasks.removeTask(index);
        storage.save(tasks);
        return ui.showRemovedTask(removedTask, tasks);
    }
}

