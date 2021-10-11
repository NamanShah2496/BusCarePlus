package ca.codingcomrades.it.buscareplus.data.model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import ca.codingcomrades.it.buscareplus.databinding.ActivityLoginBinding;
import ca.codingcomrades.it.buscareplus.databinding.ActivityMainBinding;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityLoginBinding binding;

    private String userId;
    private String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}