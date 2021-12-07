package ca.codingcomrades.it.buscareplus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import com.airbnb.lottie.L;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

//@Config(constants = BuildConfig.class)
@RunWith(RobolectricTestRunner.class)
public class LoginTest {
//    private Activity activity;
    LoginActivity login;
    EditText emailField, passwordField;
    TextView emailTitle,passwordTitle;
    @After
    public void shutdown() {
        // executed after each test case
    }
    @Before
    public void setUp() throws Exception {
        // executed before each test case
        login = new LoginActivity();
        System.out.println("Run with every test case");
    }
    @Test
    public void loginWithEmptyEmail() throws Exception{
//        try(ActivityScenario<LoginActivity> activity = ActivityScenario.launch(LoginActivity.class)) {
//          activity.moveToState(Lifecycle.State.CREATED);
//            assertThat(activity.getResult().isEqualTo("something");
//        }
//        emailTitle = (TextView) activity.findViewById(R.id.LoginPasswordTitle);
//        passwordTitle = (TextView) activity.findViewById(R.id.LoginEmailAdd_Title);
//        assertEquals("Password",passwordTitle.getText().toString());
        assertFalse(login.validator("","Humber2021"));
    }  @Test
    public void loginWithInvalidEmail() throws Exception{

        assertFalse(login.validator("vishesh@.com","Humber2021"));
    }  @Test
    public void loginWithInvalidEmail_1() throws Exception{

        assertFalse(login.validator("visheshgmail.com","Humber2021"));
    }  @Test
    public void loginWithInvalidEmail_2() throws Exception{

        assertFalse(login.validator("vishesh@gmailcom","Humber2021"));
    }  @Test
    public void loginWithEmptyPassword() throws Exception{

        assertFalse(login.validator("vishesh@gmail.com",""));
    }  @Test
    public void loginWithInvalidPassword() throws Exception{

        assertFalse(login.validator("vishesh@gmail.com","Humber2021"));
    }
    @Test
    public void loginWithInvalidPassword_1() throws Exception{
        assertFalse(login.validator("vishesh@gmail.com","#humber"));
    }

    @Test
    public void loginWithCorrectFormat() throws Exception{
        assertTrue(login.validator("vishesh@gmail.com","#Humber2021"));
    }

}
