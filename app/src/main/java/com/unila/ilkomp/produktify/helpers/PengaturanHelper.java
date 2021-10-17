package com.unila.ilkomp.produktify.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.unila.ilkomp.produktify.R;

public class PengaturanHelper {

    public static void applyTemaToolbar(Toolbar toolbar, Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        String namaTema =sharedPreferences.getString("tema_aplikasi","Default");
        if(namaTema.equals("Default")){
            toolbar.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }else if(namaTema.equals("Merah")){
            toolbar.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryMerah));
        }else if(namaTema.equals("Hijau")){
            toolbar.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryHijau));
        }else if(namaTema.equals("Biru")){
            toolbar.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryBiru));
        }
    }

    public static void applyTemaTextView(TextView textView,Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        String themeName=sharedPreferences.getString("tema_aplikasi","Default");
        if(themeName.equals("Default")){
            textView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }else if(themeName.equals("Merah")){
            textView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryMerah));
        }else if(themeName.equals("Hijau")){
            textView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryHijau));
        }else if(themeName.equals("Biru")){
            textView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryBiru));
        }
    }

    public static void applyTextColor(TextView textView,Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        String themeName=sharedPreferences.getString("tema_aplikasi","Default");
        if(themeName.equals("Default")){
            textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else if(themeName.equals("Merah")){
            textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryMerah));
        }else if(themeName.equals("Hijau")){
            textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryHijau));
        }else if(themeName.equals("Biru")){
            textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryBiru));
        }
    }

    public static void applyTema(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        String themeName=sharedPreferences.getString("tema_aplikasi","Default");
        if(themeName.equals("Default")){
            context.setTheme(R.style.AppTheme);
        }else if(themeName.equals("Merah")){
            context.setTheme(R.style.MerahTheme);
        }else if(themeName.equals("Hijau")){
            context.setTheme(R.style.HijauTheme);
        }else if(themeName.equals("Biru")){
            context.setTheme(R.style.BiruTheme);
        }
    }
}
