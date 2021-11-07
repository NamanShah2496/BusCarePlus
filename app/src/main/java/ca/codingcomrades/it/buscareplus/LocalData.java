package ca.codingcomrades.it.buscareplus;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class LocalData {


    public void savePreferences(Activity act,String context, Boolean value){
        SharedPreferences sp =act.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(context,value);
        editor.apply();

    }
    public boolean getPreference(Activity act, String context){
        SharedPreferences sp =act.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        return sp.getBoolean(context,false);

    }
}
