
// Naman Shah , n01392496, Section RNA
// Aryan Sood , n01393003 ,Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;

import ca.codingcomrades.it.buscareplus.HelpActivity;
import ca.codingcomrades.it.buscareplus.R;
import ca.codingcomrades.it.buscareplus.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getText();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        String msg1 = "Welcome to BusCare+";
        String msg2 = "Name of User";
        String msg3 = "Hope you have a wonderful day!!";
        view = inflater.inflate(R.layout.fragment_home,container,false);
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        final TextView time = view.findViewById(R.id.textView3);
        final TextView welcome = view.findViewById(R.id.textView2);
        final TextView name = view.findViewById(R.id.textView4);
        final TextView greeting = view.findViewById(R.id.textView5);
        time.setText(currentDateTimeString);
        welcome.setText(msg1);
        name.setText(msg2);
        greeting.setText(msg3);
return view;
    }

}