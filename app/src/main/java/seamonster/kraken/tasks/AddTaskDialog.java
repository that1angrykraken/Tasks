package seamonster.kraken.tasks;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;

import java.util.Calendar;

import seamonster.kraken.tasks.databinding.DialogAddTaskBinding;

public class AddTaskDialog extends DialogFragment {

    AppDatabase db;
    Task task;
    IDataChangedListener listener;
    IDialogDismissedListener listener1;
    DialogAddTaskBinding binding;

    public AddTaskDialog() {

    }

    public AddTaskDialog(Task task, IDataChangedListener listener) {
        this.task = task;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogAddTaskBinding.inflate(getLayoutInflater());
        db = AppDatabase.getInstance(requireContext());

        if(listener == null)
            listener = (IDataChangedListener) getParentFragmentManager().findFragmentByTag("f0");
        if(task == null) task = new Task();
        binding.setTask(task);

        addChipCategories();
        addChipRepeatFrequencies();
        binding.btnSetDate.setOnClickListener(v -> setDate());
        binding.btnSetTime.setOnClickListener(v -> setTime());

        Dialog dialog = new Dialog(requireContext(), R.style.DialogTheme);
        binding.toolBar.setNavigationOnClickListener(v -> dialog.cancel());
        setSaveButton(dialog);
        setDeleteButton(dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.setContentView(binding.getRoot());
        return dialog;
    }

    public void setDialogDismissedListener(IDialogDismissedListener listener1) {
        this.listener1 = listener1;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        listener1.onDialogDismissed();
    }

    void setDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(task.year, task.month, task.day);
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Set date")
                .setSelection(calendar.getTimeInMillis())
                .build();
        picker.addOnPositiveButtonClickListener(selection -> {
            calendar.setTimeInMillis(selection);
            task.setDay(calendar.get(Calendar.DATE));
            task.setMonth(calendar.get(Calendar.MONTH));
            task.setYear(calendar.get(Calendar.YEAR));
        });
        picker.show(getParentFragmentManager(), "Set date");
    }

    void setTime(){
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTitleText("Set time")
                .setHour(task.hour)
                .setMinute(task.minute)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .build();
        picker.addOnPositiveButtonClickListener(v -> {
           task.setHour(picker.getHour());
           task.setMinute(picker.getMinute());
        });
        picker.show(getParentFragmentManager(), "Set time");
    }

    private void addChipRepeatFrequencies() {
        String[] frequencies = getResources().getStringArray(R.array.repeat_frequency);
        for (int i = 0; i < frequencies.length; i++) {
            Chip chip = new Chip(requireContext());
            chip.setText(frequencies[i]);
            chip.setCheckable(true);
            int currentIndex = i;
            if(currentIndex == task.getRepeatFrequency()) chip.setChecked(true);
            chip.setOnCheckedChangeListener((compoundButton, b) ->
                    task.setRepeatFrequency(currentIndex));
            binding.chipGroupRF.addView(chip);
        }
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
            listener.onDataChanged();
            dialog.dismiss();
        });
    }

    private void setDeleteButton(Dialog dialog) {
        binding.btnDelete.setOnClickListener(v -> {
            db.taskDAO().removeTask(task);
            listener.onDataChanged();
            dialog.dismiss();
        });
    }
}
