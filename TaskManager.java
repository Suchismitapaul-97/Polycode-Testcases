import java.util.*;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getPendingTasks() {
        List<Task> pending = new ArrayList<>();
        for (Task t : tasks) {
            if (!t.isCompleted()) {
                pending.add(t);
            }
        }
        return pending;
    }

    public void markAllComplete() {
        for (Task t : tasks) {
            t.markComplete();
        }
    }

    public int getTotalTaskCount() {
        return tasks.size();
    }
}
