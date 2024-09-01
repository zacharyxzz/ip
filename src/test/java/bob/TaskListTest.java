package bob;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {

    @Test
    public void addTask_taskAdded_success() {
        TaskList taskList = new TaskList();
        Task task = new Todo("Test task");
        taskList.addTask(task);
        assertEquals(1, taskList.size());
        try {
            assertEquals(task, taskList.get(0));
        } catch (BobException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void removeTask_taskRemoved_success() {
        TaskList taskList = new TaskList();
        Task task1 = new Todo("Test task 1");
        Task task2 = new Todo("Test task 2");
        taskList.addTask(task1);
        taskList.addTask(task2);
        Task removedTask = null;
        try {
            removedTask = taskList.removeTask(0);
        } catch (BobException e) {
            throw new RuntimeException(e);
        }
        assertEquals(task1, removedTask);
        assertEquals(1, taskList.size());
        try {
            assertEquals(task2, taskList.get(0));
        } catch (BobException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void removeTask_invalidIndex_exceptionThrown() {
        TaskList taskList = new TaskList();
        Task task = new Todo("Test task");
        taskList.addTask(task);
        Exception exception = assertThrows(BobException.class, () -> {
            taskList.removeTask(1); // Index out of bounds
        });
        assertEquals("Invalid task number.", exception.getMessage());
    }
}
