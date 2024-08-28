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
                int index = Integer.parseInt(inputSplit[1]) - 1;
                if (index >= 0 && index < taskCount) {
                    tasks[index].markAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[index]);
                    System.out.println("____________________________________________________________");
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println("Invalid task number.");
                    System.out.println("____________________________________________________________");
                }
            } else if (inputSplit[0].equals("unmark")) {
                int index = Integer.parseInt(inputSplit[1]) - 1;
                if (index >= 0 && index < taskCount) {
                    tasks[index].unmarkAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[index]);
                    System.out.println("____________________________________________________________");
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println("Invalid task number.");
                    System.out.println("____________________________________________________________");
                }
            } else if (inputSplit[0].equals("todo")) {
                tasks[taskCount] = new Todo(inputSplit[1]);
                taskCount++;
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println("  [T][ ] " + inputSplit[1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println("____________________________________________________________");
            } else if (inputSplit[0].equals("deadline")) {
                String[] parts = inputSplit[1].split(" /by ", 2);
                tasks[taskCount] = new Deadline(parts[0], parts[1]);
                taskCount++;
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println("  [D][ ] " + parts[0] + " (by: " + parts[1] + ")");
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println("____________________________________________________________");
            } else if (inputSplit[0].equals("event")) {
                String[] parts = inputSplit[1].split(" /from ", 2);
                String[] fromTo = parts[1].split(" /to ", 2);
                tasks[taskCount] = new Event(parts[0], fromTo[0], fromTo[1]);
                taskCount++;
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println("  [E][ ] " + parts[0] + " (from: " + fromTo[0] + " to: " + fromTo[1] + ")");
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println("____________________________________________________________");
            } else {
                System.out.println("____________________________________________________________");
                System.out.println("Invalid command.");
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }
}

