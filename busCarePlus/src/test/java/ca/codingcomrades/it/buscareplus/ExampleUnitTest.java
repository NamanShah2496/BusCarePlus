// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//public class ExampleUnitTest {
//
//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//    }


public class ExampleUnitTest {
    UserData login;
    @After
    public void shutdown() {
        // executed after each test case
    }
    @Before
    public void setUp() throws Exception {
        // executed before each test case
        login = new UserData();
        System.out.println("Rune with every test case");
    }
    @Test
    public void loginTest() throws Exception{

    assertTrue(login.validateName("vishesh@gmail.com","#Humber2021"));
    }
    }
//}