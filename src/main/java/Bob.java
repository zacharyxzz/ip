import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Bob {
    private static final String FILE_PATH = "./data/duke.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = loadTasks();
        String input;

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Bob");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            input = scanner.nextLine();
            String[] inputSplit = input.split(" ", 2);

            try {
                if (input.equals("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________________________");
                    break;
                } else if (input.equals("list")) {
                    listTasks(tasks);
                } else if (inputSplit[0].equals("mark")) {
                    handleMarkCommand(inputSplit, tasks);
                    saveTasks(tasks);
                } else if (inputSplit[0].equals("unmark")) {
                    handleUnmarkCommand(inputSplit, tasks);
                    saveTasks(tasks);
                } else if (inputSplit[0].equals("todo")) {
                    handleTodoCommand(inputSplit, tasks);
                    saveTasks(tasks);
                } else if (inputSplit[0].equals("deadline")) {
                    handleDeadlineCommand(inputSplit, tasks);
                    saveTasks(tasks);
                } else if (inputSplit[0].equals("event")) {
                    handleEventCommand(inputSplit, tasks);
                    saveTasks(tasks);
                } else if (inputSplit[0].equals("delete")) {
                    handleDeleteCommand(inputSplit, tasks);
                    saveTasks(tasks);
                } else {
                    throw new BobException("I'm sorry, but I don't know what that means :-(");
                }
            } catch (BobException e) {
                System.out.println("____________________________________________________________");
                System.out.println("OOPS!!! " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }

    private static void listTasks(ArrayList<Task> tasks) {
        System.out.println("____________________________________________________________");
        if (tasks.isEmpty()) {
            System.out.println("No tasks added yet.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println("____________________________________________________________");
    }

    private static void handleMarkCommand(String[] inputSplit, ArrayList<Task> tasks) throws BobException {
        int index = parseIndex(inputSplit[1], tasks.size());
        tasks.get(index).markAsDone();
        System.out.println("____________________________________________________________");
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + tasks.get(index));
        System.out.println("____________________________________________________________");
    }

    private static void handleUnmarkCommand(String[] inputSplit, ArrayList<Task> tasks) throws BobException {
        int index = parseIndex(inputSplit[1], tasks.size());
        tasks.get(index).unmarkAsDone();
        System.out.println("____________________________________________________________");
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + tasks.get(index));
        System.out.println("____________________________________________________________");
    }

    private static void handleTodoCommand(String[] inputSplit, ArrayList<Task> tasks) throws BobException {
        if (inputSplit.length < 2 || inputSplit[1].trim().isEmpty()) {
            throw new BobException("The description of a todo cannot be empty.");
        }
        tasks.add(new Todo(inputSplit[1]));
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  [T][ ] " + inputSplit[1]);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private static void handleDeadlineCommand(String[] inputSplit, ArrayList<Task> tasks) throws BobException {
        if (inputSplit.length < 2 || !inputSplit[1].contains(" /by ")) {
            throw new BobException("The description of a deadline must include '/by' followed by a date/time.");
        }
        String[] parts = inputSplit[1].split(" /by ", 2);
        try {
            LocalDateTime deadline = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            tasks.add(new Deadline(parts[0], deadline));
            System.out.println("____________________________________________________________");
            System.out.println("Got it. I've added this task:");
            System.out.println("  [D][ ] " + parts[0] + " (by: " + deadline.format(DATETIME_FORMATTER) + ")");
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("____________________________________________________________");
        } catch (Exception e) {
            throw new BobException("Please use the date format 'yyyy-mm-dd HHmm'.");
        }
    }

    private static void handleEventCommand(String[] inputSplit, ArrayList<Task> tasks) throws BobException {
        if (inputSplit.length < 2 || !inputSplit[1].contains(" /from ") || !inputSplit[1].contains(" /to ")) {
            throw new BobException("The description of an event must include '/from' followed by start time and '/to' followed by end time.");
        }
        String[] parts = inputSplit[1].split(" /from ", 2);
        String[] fromTo = parts[1].split(" /to ", 2);
        try {
            LocalDateTime from = LocalDateTime.parse(fromTo[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            LocalDateTime to = LocalDateTime.parse(fromTo[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            tasks.add(new Event(parts[0], from, to));
            System.out.println("____________________________________________________________");
            System.out.println("Got it. I've added this task:");
            System.out.println("  [E][ ] " + parts[0] + " (from: " + from.format(DATETIME_FORMATTER) + " to: " + to.format(DATETIME_FORMATTER) + ")");
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("____________________________________________________________");
        } catch (Exception e) {
            throw new BobException("Please use the date format 'yyyy-mm-dd HHmm'.");
        }
    }

    private static void handleDeleteCommand(String[] inputSplit, ArrayList<Task> tasks) throws BobException {
        int index = parseIndex(inputSplit[1], tasks.size());
        Task removedTask = tasks.remove(index);
        System.out.println("____________________________________________________________");
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private static int parseIndex(String input, int size) throws BobException {
        try {
            int index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= size) {
                throw new BobException("Invalid task number.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new BobException("Invalid task number.");
        }
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            FileWriter writer = new FileWriter(FILE_PATH);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return tasks;  // No data file exists, start with an empty list.
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                switch (parts[0]) {
                    case "T":
                        tasks.add(new Todo(parts[2], parts[1].equals("1")));
                        break;
                    case "D":
                        tasks.add(new Deadline(parts[2], LocalDateTime.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")), parts[1].equals("1")));
                        break;
                    case "E":
                        tasks.add(new Event(parts[2], LocalDateTime.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")), LocalDateTime.parse(parts[4], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")), parts[1].equals("1")));
                        break;
                    default:
                        System.out.println("Unknown task type: " + parts[0]);
                        break;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Corrupted data file. Starting with an empty task list.");
            tasks.clear();  // Start with an empty list if data is corrupted.
        }
        return tasks;
    }
}
