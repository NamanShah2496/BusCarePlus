// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB



package ca.codingcomrades.it.buscareplus.ui.maintenance;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.codingcomrades.it.buscareplus.R;
import ca.codingcomrades.it.buscareplus.databinding.FragmentMaintenanceBinding;
import ca.codingcomrades.it.buscareplus.databinding.FragmentSafetyBinding;

public class MaintenanceFragment extends Fragment {

    private MaintenanceViewModel maintenanceViewModel;
    private FragmentMaintenanceBinding binding;

    public static MaintenanceFragment newInstance() {
        return new MaintenanceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       maintenanceViewModel = new ViewModelProvider(this).get(MaintenanceViewModel.class);
        binding = FragmentMaintenanceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMaintenance;
        maintenanceViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
//        return inflater.inflate(R.layout.fragment_maintenance, container, false);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        maintenanceViewModel = new ViewModelProvider(this).get(MaintenanceViewModel.class);
//        // TODO: Use the ViewModel
//    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}