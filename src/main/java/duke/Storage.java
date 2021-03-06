package duke;

import duke.exception.DukeException;
import duke.exception.StorageException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Loads tasks from the data file and saves tasks in the data file.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage with the specified file path.
     *
     * @param filePath The file path where the tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the tasks from the data file.
     *
     * @return A list of tasks from the data file.
     * @throws DukeException If the data in the data file is not in the correct format.
     */
    public List<Task> load() throws DukeException {
        List<Task> tasks = new ArrayList<>();
        try {
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(" \\| ");
                Task task;
                switch (data[0]) {
                case "T":
                    task = new Todo(data[2], data[1].equals("1"));
                    break;
                case "D":
                    task = new Deadline(data[2], data[3], data[1].equals("1"));
                    break;
                case "E":
                    task = new Event(data[2], data[3], data[1].equals("1"));
                    break;
                default:
                    throw new StorageException("Could not load tasks.");
                }
                tasks.add(task);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            createFile();
        }
        return tasks;
    }

    /**
     * Creates the data file.
     *
     * @throws DukeException If the data file cannot be created.
     */
    private void createFile() throws DukeException {
        try {
            Path path = Paths.get(filePath);
            Path parentDir = path.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Could not create data file.");
        }
    }

    /**
     * Saves the tasks into the data file.
     *
     * @param tasks The tasks to be saved.
     * @throws DukeException If the tasks cannot be saved.
     */
    public void save(TaskList tasks) throws DukeException {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (Task task : tasks.getTasks()) {
                fw.write(task.formatToSave() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new StorageException("Could not save tasks.");
        }
    }
}
