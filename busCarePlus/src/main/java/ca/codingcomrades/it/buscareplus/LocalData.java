// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB



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
    public void savePreferences(Context act, String context, Boolean value){
        SharedPreferences sp =act.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(context, value);
        editor.apply();

    }
    public boolean getPreference(Context act, String context){
        SharedPreferences sp =act.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        return sp.getBoolean(context,false);

    }
    public String getPreference(Activity act, String context,int i){

        SharedPreferences sp =act.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        return sp.getString(context,"false");

    }
}
