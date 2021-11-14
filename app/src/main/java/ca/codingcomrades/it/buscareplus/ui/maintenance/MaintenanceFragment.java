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
import ca.codingcomrades.it.buscareplus.ui.settings.SettingViewModel;

public class MaintenanceFragment extends Fragment {

    private MaintenanceViewModel maintenanceViewModel;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        maintenanceViewModel = new ViewModelProvider(this).get(MaintenanceViewModel.class);
        maintenanceViewModel.getText();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maintenance,container,false);
        return view;

    }



}