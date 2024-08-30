package bob;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task removeTask(int index) throws BobException {
        if (index < 0 || index >= tasks.size()) {
            throw new BobException("Invalid task number.");
        }
        return tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) throws BobException {
        if (index < 0 || index >= tasks.size()) {
            throw new BobException("Invalid task number.");
        }
        return tasks.get(index);
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
}