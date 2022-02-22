package com.example.bottomnavigationactivityapp;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.bottomnavigationactivityapp.contact.ContactViewModel;
import com.example.bottomnavigationactivityapp.utils.PinyinUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        ContactViewModel model1 = new ContactViewModel("n1","p1");
        ContactViewModel model2 = new ContactViewModel("n2","p1");
        List<ContactViewModel> l1 = new ArrayList<>();
        List<ContactViewModel> l2 = new ArrayList<>();
        l1.add(model1);
        l1.add(model2);
        customClone(l2,l1);


        List<ContactViewModel> n1 = l2.stream().filter(i -> i.name.equals("n1")).collect(Collectors.toList());
        Log.i(TAG, "useAppContext: n1"+n1);
        n1.get(0).name="a1";
        Log.i(TAG, "useAppContext: n1"+n1);
        Log.i(TAG, "useAppContext: l2"+l2);
        n1.clear();
        Log.i(TAG, "useAppContext: n1"+n1);
        Log.i(TAG, "useAppContext: l2"+l2);



        Log.i(TAG, "useAppContext: l1"+l1);
        Log.i(TAG, "useAppContext: l2"+l2);
        model1.setName("name");
        Log.i(TAG, "useAppContext: l1"+l1);
        Log.i(TAG, "useAppContext: l2"+l2);
        l1.remove(0);
        Log.i(TAG, "useAppContext: l1"+l1);
        Log.i(TAG, "useAppContext: l2"+l2);


    }


    public void customClone(List<ContactViewModel> a, List<ContactViewModel> b) {
        for (ContactViewModel model: b) {
//            a.add(model.clone());
            a.add(model);
        }
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
