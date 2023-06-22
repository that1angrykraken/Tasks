package seamonster.kraken.tasks;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import com.google.android.material.chip.Chip;

import seamonster.kraken.tasks.databinding.DialogAddTaskBinding;

public class AddTaskDialog extends DialogFragment {

    AppDatabase db;
    Task task;
    IDataChangedListener listener;
    DialogAddTaskBinding binding;

    public AddTaskDialog(Task task, IDataChangedListener listener) {
        this.task = task;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        Log.d("dialog", "onCreateDialog: oke");

        binding = DialogAddTaskBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(requireContext(), R.style.DialogTheme);
        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "tasksDB")
                .allowMainThreadQueries().build();

        if(listener == null)
            listener = (IDataChangedListener) getParentFragmentManager().findFragmentByTag("f0");
        if(task == null) task = new Task();
        binding.setTask(task);

        addChipCategories();
        setSaveButton(dialog);
        setDeleteButton(dialog);
        binding.toolBar.setNavigationOnClickListener(v -> dialog.cancel());

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.setContentView(binding.getRoot());
        return dialog;
    }

    private void addChipCategories() {
        String[] categories = getResources().getStringArray(R.array.categories);
        for (int i = 0; i < categories.length; i++) {
            Chip chip = new Chip(requireContext());
            chip.setText(categories[i]);
            chip.setCheckable(true);
            int currentIndex = i;
            if(currentIndex == task.getCategoryId()) chip.setChecked(true);
            chip.setOnCheckedChangeListener((compoundButton, b) ->
                    task.setCategoryId(currentIndex));
            binding.chipGroupCategories.addView(chip);
        }
    }

    private void setSaveButton(Dialog dialog) {
        binding.btnSave.setOnClickListener(v -> {
            if(task.getId() < 1) db.taskDAO().addTask(task);
            else db.taskDAO().updateTask(task);
            listener.onDataChanged(task);
            dialog.dismiss();
        });
    }

    private void setDeleteButton(Dialog dialog) {
        binding.btnDelete.setOnClickListener(v -> {
            db.taskDAO().removeTask(task);
            listener.onDataChanged(task);
            dialog.dismiss();
        });
    }
}
