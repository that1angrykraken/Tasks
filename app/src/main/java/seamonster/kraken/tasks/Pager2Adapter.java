package seamonster.kraken.tasks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Pager2Adapter extends FragmentStateAdapter {

    public Pager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) return new TaskFragment();
        if(position == 1) return new FinishedFragment();
        if(position == 2) return new MissedFragment();
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
