package seamonster.kraken.tasks;

import android.view.View;

import com.google.android.material.checkbox.MaterialCheckBox;

public interface ItemClickListener {
    void onItemClick(View view, Task task);
    void onItemCheckedChange(MaterialCheckBox checkBox,Task task);
}
