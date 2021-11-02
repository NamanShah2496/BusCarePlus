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