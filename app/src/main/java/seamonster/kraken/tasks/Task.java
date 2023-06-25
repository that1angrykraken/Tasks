package seamonster.kraken.tasks;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "categoryId")
    int categoryId;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "desc")
    String desc;
    @ColumnInfo(name = "day")
    int day;
    @ColumnInfo(name = "month")
    int month;
    @ColumnInfo(name = "year")
    int year;
    @ColumnInfo(name = "hour")
    int hour;
    @ColumnInfo(name = "minute")
    int minute;
    @ColumnInfo(name = "important")
    boolean important;
    @ColumnInfo(name = "finished")
    boolean finished;
    @ColumnInfo(name = "repeat")
    boolean repeat;
    @ColumnInfo(name = "repeatFrequency")
    int repeatFrequency;
    @ColumnInfo(name = "imgPath")
    String imgPath;

    public Task() {
    }

    @Bindable
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
        notifyPropertyChanged(BR.imgPath);
    }

    @Bindable
    public int getId() {
        return id;
    }

    @Bindable
    public int getCategoryId() {
        return categoryId;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public String getDesc() {
        return desc;
    }

    @Bindable
    public int getDay() {
        return day;
    }

    @Bindable
    public int getMonth() {
        return month;
    }

    @Bindable
    public int getYear() {
        return year;
    }

    @Bindable
    public int getHour() {
        return hour;
    }

    @Bindable
    public int getMinute() {
        return minute;
    }

    @Bindable
    public boolean isImportant() {
        return important;
    }

    @Bindable
    public boolean isFinished() {
        return finished;
    }

    @Bindable
    public boolean isRepeat() {
        return repeat;
    }

    @Bindable
    public int getRepeatFrequency() {
        return repeatFrequency;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        notifyPropertyChanged(BR.categoryId);
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);
    }

    public void setDay(int day) {
        this.day = day;
        notifyPropertyChanged(BR.day);
    }

    public void setMonth(int month) {
        this.month = month;
        notifyPropertyChanged(BR.month);
    }

    public void setYear(int year) {
        this.year = year;
        notifyPropertyChanged(BR.year);
    }

    public void setHour(int hour) {
        this.hour = hour;
        notifyPropertyChanged(BR.hour);
    }

    public void setMinute(int minute) {
        this.minute = minute;
        notifyPropertyChanged(BR.minute);
    }

    public void setImportant(boolean important) {
        this.important = important;
        notifyPropertyChanged(BR.important);
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
        notifyPropertyChanged(BR.finished);
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
        notifyPropertyChanged(BR.repeat);
    }

    public void setRepeatFrequency(int repeatFrequency) {
        this.repeatFrequency = repeatFrequency;
        notifyPropertyChanged(BR.repeatFrequency);
    }
}
