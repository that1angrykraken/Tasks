package seamonster.kraken.tasks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import seamonster.kraken.tasks.databinding.FragmentTaskBinding;

public class TaskFragment extends Fragment {

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
        ArrayList<Task> data = new ArrayList<>(db.taskDAO().getTasks());
        adapter = new TaskAdapter(data, getContext());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }
}