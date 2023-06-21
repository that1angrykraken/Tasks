package seamonster.kraken.tasks;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM Category")
    List<Category> getCategories();

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Insert
    void addCategory(Category category);
}
