package bob;

import java.util.Scanner;

public class Bob {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private static final String DEFAULT_FILE_PATH = "data/duke.txt";

    /**
     * Constructs a Bob object that initializes the UI, Storage, and TaskList components.
     *
     * @param filePath the path to the file where tasks are stored.
     */
    public Bob(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (BobException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
        assert storage != null;
    }
    public Bob() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return Parser.parse(input, tasks, ui, storage);
    }

    /**
     * Returns the welcome message from the Ui.
     */
    public String getWelcomeMessage() {
        return ui.showWelcome();
    }

}