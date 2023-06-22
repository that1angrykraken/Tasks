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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;

import seamonster.kraken.tasks.databinding.ListItemTaskBinding;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    ArrayList<Task> data;
    Drawable repeatIcon;
    ItemClickListener listener;
    static final String TAG = "TaskAdapter";

    public TaskAdapter(ArrayList<Task> data, Fragment fragment) {
        this.data = data;
        repeatIcon = ContextCompat.getDrawable(fragment.requireContext(), R.drawable.round_repeat_24);
        listener = (ItemClickListener) fragment;
    }

    public ArrayList<Task> getData() {
        return data;
    }

    public void setData(ArrayList<Task> data) {
        this.data = data;
        notifyDataSetChanged();
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
        String[] categories = holder.binding.getRoot()
                .getContext().getResources().getStringArray(R.array.categories);
        holder.binding.setTaskCategory(categories[task.categoryId]);
        holder.binding.getRoot().setOnClickListener(v -> {
            listener.onItemClick(v, task);
        });
        holder.binding.checkbox.addOnCheckedStateChangedListener((checkBox, state) -> {
            if(state == MaterialCheckBox.STATE_CHECKED){
                notifyItemRemoved(data.indexOf(task));
                data.remove(task);
                listener.onItemCheckedChange(checkBox, task);
            }
        });
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
