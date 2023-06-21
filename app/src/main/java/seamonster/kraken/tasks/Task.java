package seamonster.kraken.tasks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "categoryId")
    int categoryId;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "desc")
    String desc;
    @ColumnInfo(name = "expiredDate")
    String expiredDate;
    @ColumnInfo(name = "important")
    boolean important;
    @ColumnInfo(name = "expired")
    boolean expired;
    @ColumnInfo(name = "finished")
    boolean finished;
    @ColumnInfo(name = "repeat")
    boolean repeat;

    public Task() {
    }

    @Ignore
    public Task(int id, int categoryId, String title, String desc, String expiredDate, boolean important, boolean expired, boolean finished, boolean repeat) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.desc = desc;
        this.expiredDate = expiredDate;
        this.important = important;
        this.expired = expired;
        this.finished = finished;
        this.repeat = repeat;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }
}
