package seamonster.kraken.tasks;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {
    @Query("SELECT * FROM Task")
    List<Task> getTasks();

    @Query("SELECT * FROM Task WHERE finished = 0")
    List<Task> getActiveTasks();

    @Query("SELECT * FROM Task WHERE finished = 1")
    List<Task> getFinishedTasks();

    @Query("SELECT * FROM Task WHERE categoryId IN (:categoryIds) " +
            "AND finished = 0")
    List<Task> getTasks(Integer... categoryIds);

    @Query("SELECT * FROM Task WHERE important = 1 " +
            "AND finished = 0")
    List<Task> getImportantTasks();

    @Insert
    void addTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void removeTask(Task task);
}
