
// Naman Shah , n01392496 ,Section RNA
// Aryan Sood , n01393003 ,Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus.ui.safety;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ca.codingcomrades.it.buscareplus.databinding.FragmentSafetyBinding;

public class SafetyFragment extends Fragment {

    private SafetyViewModel safetyViewModel;
    private FragmentSafetyBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        safetyViewModel =
                new ViewModelProvider(this).get(SafetyViewModel.class);

        binding = FragmentSafetyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSafety;
        safetyViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}