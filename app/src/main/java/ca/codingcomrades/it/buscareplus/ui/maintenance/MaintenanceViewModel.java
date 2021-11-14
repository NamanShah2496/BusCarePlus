// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB



package ca.codingcomrades.it.buscareplus.ui.maintenance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MaintenanceViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public MaintenanceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Maintenance Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}