package seamonster.kraken.tasks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;

import seamonster.kraken.tasks.databinding.ListItemTaskBinding;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    ArrayList<Task> data;
    Drawable repeatIcon;
    static final String TAG = "TaskAdapter";

    public TaskAdapter(ArrayList<Task> data, Context context) {
        this.data = data;
        repeatIcon = ContextCompat.getDrawable(context, R.drawable.round_repeat_24);
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        Task task = data.get(position);
        holder.binding.setTask(task);
        holder.binding.setRepeatIcon(repeatIcon);
        holder.binding.checkbox.addOnCheckedStateChangedListener((checkBox, state) -> {
            Log.d(TAG, "onBindViewHolder: "+task.isFinished());
            holder.binding.invalidateAll();
        });
        Log.d(TAG, "onBindViewHolder: passed");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ListItemTaskBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ListItemTaskBinding.bind(itemView);
        }
    }
}
