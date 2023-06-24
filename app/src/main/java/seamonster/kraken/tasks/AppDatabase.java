package seamonster.kraken.tasks;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context, AppDatabase.class, "tasksDB")
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract TaskDAO taskDAO();
}
