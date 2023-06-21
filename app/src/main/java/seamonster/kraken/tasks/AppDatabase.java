package seamonster.kraken.tasks;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDAO taskDAO();
    public abstract CategoryDAO categoryDAO();
}
