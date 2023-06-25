package seamonster.kraken.tasks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.Arrays;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        db = AppDatabase.getInstance(requireContext());

        ArrayList<Task> data = new ArrayList<>(db.taskDAO().getActiveTasks());
        adapter = new TaskAdapter(data, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDataChanged(Task... tasks) {
//        if(tasks.length == 0)
////            adapter.setData(new ArrayList<>(db.taskDAO().getActiveTasks()));
//        else
            adapter.setData(new ArrayList<>(Arrays.asList(tasks)));
    }

    @Override
    public void onItemClick(View view, Task task) {
        AddTaskDialog dialog = new AddTaskDialog(task, this);
        dialog.setDialogDismissedListener((IDialogDismissedListener) requireActivity());
        dialog.show(getParentFragmentManager(), "");
    }

    @Override
    public void onItemCheckedChange(MaterialCheckBox checkBox, Task task) {
        adapter.notifyItemRemoved(adapter.getData().indexOf(task));
        adapter.getData().remove(task);
        task.setFinished(true);
        db.taskDAO().updateTask(task);
    }

}