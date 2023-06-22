package seamonster.kraken.tasks;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;

import seamonster.kraken.tasks.databinding.FragmentTaskBinding;

public class TaskFragment extends Fragment implements IDataChangedListener, ItemClickListener{

    FragmentTaskBinding binding;
    TaskAdapter adapter;
    AppDatabase db;
    public TaskFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "tasksDB")
                .allowMainThreadQueries().build();
        ArrayList<Task> data = new ArrayList<>(db.taskDAO().getActiveTasks());
        adapter = new TaskAdapter(data, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onDataChanged(Task task) {
        adapter.setData(new ArrayList<>(db.taskDAO().getActiveTasks()));
    }

    @Override
    public void onItemClick(View view, Task task) {
        AddTaskDialog dialog = new AddTaskDialog(task, this);
        dialog.show(getParentFragmentManager(), "");
    }

    @Override
    public void onItemCheckedChange(MaterialCheckBox checkBox, Task task) {
        db.taskDAO().updateTask(task);
    }
}