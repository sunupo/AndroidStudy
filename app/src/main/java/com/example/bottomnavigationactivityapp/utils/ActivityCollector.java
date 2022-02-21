package com.example.bottomnavigationactivityapp.utils;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<AppCompatActivity> list = new ArrayList<>();
    public static void add(AppCompatActivity appCompatActivity){
        list.add(appCompatActivity);
    }
    public static void remove(AppCompatActivity appCompatActivity){
        list.remove(appCompatActivity);
    }
    public static void finishAll(){
        list.forEach(Finish::finishAll);
    }

    static class Finish{
        public static void finishAll(AppCompatActivity appCompatActivity){
            if(!appCompatActivity.isFinishing()) {
                appCompatActivity.finish();
            }
        }
    }
}
