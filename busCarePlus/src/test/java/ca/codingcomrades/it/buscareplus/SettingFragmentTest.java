package ca.codingcomrades.it.buscareplus;

import org.junit.Test;

import static org.junit.Assert.*;

import ca.codingcomrades.it.buscareplus.ui.settings.SettingFragment;


public class SettingFragmentTest {

    SettingFragment settingFragment = new SettingFragment();

    @Test
    public void whenPortraitIsOnTest() {
        boolean result= settingFragment.isPortraitOn("true");
        assertTrue(result);
    }

    @Test
    public void WhenPortraitIsOFFTest() {
        boolean result= settingFragment.isPortraitOn("false");
        assertFalse(result);
    }

    @Test
    public void WhenDarkThemeIsOnTest() {
        boolean result = settingFragment.isDarkThemeOn("true");
        assertTrue(result);
    }

    @Test
    public void WhenDarkThemeIsOffTest() {
        boolean result = settingFragment.isDarkThemeOn("false");
        assertFalse(result);
    }

    @Test
    public void isMetricOnTest() {
        boolean result = settingFragment.isMetricOn("true");
        assertTrue(result);
    }

    @Test
    public void isMetricOFFTest() {
        boolean result = settingFragment.isMetricOn("false");
        assertFalse(result);
    }

    @Test
    public void isImperialOnTest() {
        boolean result = settingFragment.isImperialOn("true");
        assertTrue(result);
    }

    @Test
    public void isImperialOffTest() {
        boolean result = settingFragment.isImperialOn("false");
        assertFalse(result);
    }
}