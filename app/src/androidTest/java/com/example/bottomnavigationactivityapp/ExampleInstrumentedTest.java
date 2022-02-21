package com.example.bottomnavigationactivityapp;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.bottomnavigationactivityapp.utils.PinyinUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    String TAG= "TESTTEST";
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.bottomnavigationactivityapp", appContext.getPackageName());
        new B();
        String sun = Integer.toHexString('孙').toUpperCase();
        Log.i(TAG, "useAppContext: "+sun+Integer.toHexString('孙'));
        String str = "婚纱照";
        Log.i(TAG,PinyinUtil.getPinyinFirst(str));
    }
}
class A{
    public String TAG="TAGA";
    public String c="---C";
    public A(){
        Log.i(TAG, "A: "+this+this.TAG+this.c);
        this.funA1();

    }
    public void funA1(){
        Log.i(TAG, "funA1: ");
    }
}
class B extends A{
    public String TAG="TAGB";
    public B(){
        super();
        Log.i(TAG, "B: "+this+this.TAG+this.c);
        this.funA1();
    }

    @Override
    public void funA1() {
        Log.i(TAG, "funA1: ");
        super.funA1();
    }

    //public static void main(String[] args) {
//        new B();
//    }
}
