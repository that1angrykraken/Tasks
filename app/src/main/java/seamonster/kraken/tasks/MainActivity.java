package seamonster.kraken.tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import seamonster.kraken.tasks.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Tasks"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Finished"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Unfinished"));
    }
}