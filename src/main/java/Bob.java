import java.util.Scanner;

public class Bob {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;
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
                    System.out.println("____________________________________________________________");
                    if (taskCount == 0) {
                        System.out.println("No tasks added yet.");
                    } else {
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < taskCount; i++) {
                            System.out.println((i + 1) + "." + tasks[i]);
                        }
                    }
                    System.out.println("____________________________________________________________");
                } else if (inputSplit[0].equals("mark")) {
                    handleMarkCommand(inputSplit, tasks, taskCount);
                } else if (inputSplit[0].equals("unmark")) {
                    handleUnmarkCommand(inputSplit, tasks, taskCount);
                } else if (inputSplit[0].equals("todo")) {
                    handleTodoCommand(inputSplit, tasks, taskCount);
                    taskCount++;
                } else if (inputSplit[0].equals("deadline")) {
                    handleDeadlineCommand(inputSplit, tasks, taskCount);
                    taskCount++;
                } else if (inputSplit[0].equals("event")) {
                    handleEventCommand(inputSplit, tasks, taskCount);
                    taskCount++;
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

    private static void handleMarkCommand(String[] inputSplit, Task[] tasks, int taskCount) throws BobException {
        if (inputSplit.length < 2 || !isValidNumber(inputSplit[1], taskCount)) {
            throw new BobException("Invalid task number.");
        }
        int index = Integer.parseInt(inputSplit[1]) - 1;
        tasks[index].markAsDone();
        System.out.println("____________________________________________________________");
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + tasks[index]);
        System.out.println("____________________________________________________________");
    }

    private static void handleUnmarkCommand(String[] inputSplit, Task[] tasks, int taskCount) throws BobException {
        if (inputSplit.length < 2 || !isValidNumber(inputSplit[1], taskCount)) {
            throw new BobException("Invalid task number.");
        }
        int index = Integer.parseInt(inputSplit[1]) - 1;
        tasks[index].unmarkAsDone();
        System.out.println("____________________________________________________________");
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + tasks[index]);
        System.out.println("____________________________________________________________");
    }

    private static void handleTodoCommand(String[] inputSplit, Task[] tasks, int taskCount) throws BobException {
        if (inputSplit.length < 2 || inputSplit[1].trim().isEmpty()) {
            throw new BobException("The description of a todo cannot be empty.");
        }
        tasks[taskCount] = new Todo(inputSplit[1]);
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  [T][ ] " + inputSplit[1]);
        System.out.println("Now you have " + (taskCount + 1) + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private static void handleDeadlineCommand(String[] inputSplit, Task[] tasks, int taskCount) throws BobException {
        if (inputSplit.length < 2 || !inputSplit[1].contains(" /by ")) {
            throw new BobException("The description of a deadline must include '/by' followed by a date/time.");
        }
        String[] parts = inputSplit[1].split(" /by ", 2);
        tasks[taskCount] = new Deadline(parts[0], parts[1]);
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  [D][ ] " + parts[0] + " (by: " + parts[1] + ")");
        System.out.println("Now you have " + (taskCount + 1) + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private static void handleEventCommand(String[] inputSplit, Task[] tasks, int taskCount) throws BobException {
        if (inputSplit.length < 2 || !inputSplit[1].contains(" /from ") || !inputSplit[1].contains(" /to ")) {
            throw new BobException("The description of an event must include '/from' followed by start time and '/to' followed by end time.");
        }
        String[] parts = inputSplit[1].split(" /from ", 2);
        String[] fromTo = parts[1].split(" /to ", 2);
        tasks[taskCount] = new Event(parts[0], fromTo[0], fromTo[1]);
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  [E][ ] " + parts[0] + " (from: " + fromTo[0] + " to: " + fromTo[1] + ")");
        System.out.println("Now you have " + (taskCount + 1) + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private static boolean isValidNumber(String input, int taskCount) {
        try {
            int number = Integer.parseInt(input);
            return number > 0 && number <= taskCount;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

