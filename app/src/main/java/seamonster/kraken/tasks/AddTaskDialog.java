package seamonster.kraken.tasks;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import seamonster.kraken.tasks.databinding.DialogAddTaskBinding;

public class AddTaskDialog extends DialogFragment {

    AppDatabase db;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext(), R.style.DialogTheme);
//        Dialog dialog = new Dialog(requireContext());
        DialogAddTaskBinding binding = DialogAddTaskBinding.inflate(getLayoutInflater());
        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "tasksDB")
                        .allowMainThreadQueries().build();
        Task task = new Task();
        binding.setTask(task);
        binding.btnSave.setOnClickListener(v -> {
            db.taskDAO().addTask(task);
            dialog.dismiss();
        });
        binding.toolBar.setNavigationOnClickListener(v -> dialog.cancel());
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        return dialog;
    }
}
