
// Naman Shah , n01392496, Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus.ui.safety;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SafetyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SafetyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Safety Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}