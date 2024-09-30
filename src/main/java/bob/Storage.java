package bob;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Storage {
    private String filePath;


    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath the path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }


    /**
     * Loads tasks from the file.
     *
     * @return an ArrayList of tasks loaded from the file.
     * @throws BobException if there is an error loading the tasks.
     */
    public ArrayList<Task> load() throws BobException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return tasks;  // No data file exists, start with an empty list.
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                String[] parts = nextLine.split(" \\| ");
                switch (parts[0]) {
                    case "T":
                        tasks.add(new Todo(parts[2], parts[1].equals("1")));
                        break;
                    case "D":
                        tasks.add(new Deadline(parts[2], LocalDateTime.parse(parts[3],
                                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")), parts[1].equals("1")));
                        break;
                    case "E":
                        tasks.add(new Event(parts[2], LocalDateTime.parse(parts[3],
                                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")), LocalDateTime.parse(parts[4],
                                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")), parts[1].equals("1")));
                        break;
                    default:
                        throw new BobException("Unknown task type in data file.");
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new BobException("Error loading tasks: " + e.getMessage());
        } catch (Exception e) {
            throw new BobException("Corrupted data file.");
        }
        return tasks;
    }

    /**
     * Saves tasks to the file.
     *
     * @param tasks the TaskList to be saved to the file.
     * @throws BobException if there is an error saving the tasks.
     */
    public void save(TaskList tasks) throws BobException {
        try {
            File directory = new File(filePath).getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks.getAllTasks()) {
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new BobException("Error saving tasks: " + e.getMessage());
        }
    }
}
