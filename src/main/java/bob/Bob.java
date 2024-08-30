package bob;

import java.util.Scanner;

public class Bob {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Bob(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (BobException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            Parser.parse(input, tasks, ui, storage);
        }
    }

    public static void main(String[] args) {
        new Bob("data/duke.txt").run();
    }
}