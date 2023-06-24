package seamonster.kraken.tasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import seamonster.kraken.tasks.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements IDialogDismissedListener{

    ActivityMainBinding binding;
    Pager2Adapter adapter;
    AppDatabase db;
    IDataChangedListener listener;
    private static final List<Integer> filters = new ArrayList<>();
    private static final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getInstance(getApplicationContext());

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Tasks"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Finished"));
        adapter = new Pager2Adapter(getSupportFragmentManager(), getLifecycle());
        binding.viewPager.setAdapter(adapter);

        setTabSelect();
        setPageSwipe();
        addChipCategories();
        setImportantFilter();
        setAddTaskButton();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("f0");
        listener = (IDataChangedListener) fragment;
        return super.onCreateView(name, context, attrs);
    }

    private void addChipCategories() {
        String[] categories = getResources().getStringArray(R.array.categories);
        Context context = new ContextThemeWrapper(getBaseContext(), R.style.AppTheme);
        for (int i = 0; i < categories.length; i++) {
            Chip chip = new Chip(context);
            chip.setText(categories[i]);
            chip.setCheckable(true);
            int currentIndex = i;
            chip.setOnCheckedChangeListener((compoundButton, b) -> {
                if(b) {
                    binding.chipFilterImportant.setChecked(false);
                    filters.add(currentIndex);
                }
                else filters.remove((Object) currentIndex);
                listener.onDataChanged(db.taskDAO().getTasks(filters.toArray(new Integer[0]))
                        .toArray(new Task[0]));
            });
            binding.chipGroupFilters.addView(chip);
        }
    }

    void uncheckSelectedChips(){
        for(Integer item : binding.chipGroupFilters.getCheckedChipIds()){
            Chip chip = binding.chipGroupFilters.findViewById(item);
            chip.setChecked(false);
        }
        binding.chipGroupFilters.invalidate();
    }

    void setImportantFilter(){
        binding.chipFilterImportant.setOnCheckedChangeListener((compoundButton, b) -> {
            Task[] data = null;
            if(b){
                data = db.taskDAO().getImportantTasks().toArray(new Task[0]);
                uncheckSelectedChips();
                compoundButton.setChecked(true);
            }
            else
                data = db.taskDAO().getActiveTasks().toArray(new Task[0]);
            listener.onDataChanged(data);
        });
    }

    void setAddTaskButton(){
        binding.extendedFAB.setOnClickListener(v -> {
            AddTaskDialog dialog = new AddTaskDialog(new Task(), null);
            dialog.setDialogDismissedListener(this);
            dialog.show(getSupportFragmentManager().beginTransaction(), "DialogFragment");
        });
    }

    @Override
    public void onDialogDismissed() {
        uncheckSelectedChips();
    }

    void setPageSwipe(){
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));

                binding.extendedFAB.setVisibility(position==0?View.VISIBLE:View.INVISIBLE);
                binding.bottomBar.setVisibility(binding.extendedFAB.getVisibility());
            }
        });
    }

    void setTabSelect(){
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
}