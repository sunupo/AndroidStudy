package com.example.bottomnavigationactivityapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public String TAG = "TAGBaseFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BaseFragment() {
        // Required empty public constructor
        Log.i(TAG, "BaseFragment: "+this.toString());
//        Log.i("getActivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "BaseFragment: ");

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseFragment newInstance(String param1, String param2) {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onInflate: ");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onAttach: ");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onViewStateRestored: ");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onCreate: ");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_base, container, false);
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onActivityCreated: ");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onActivityResult: ");
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onAttachFragment: ");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onCreateOptionsMenu: ");
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onDestroyOptionsMenu: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onPause: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onSaveInstanceState: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("getAcrivity taskId="+getActivity().getTaskId()+"\t"+TAG+"\t"+this.toString().split("Fragment")[1].substring(1,8), "onDetach: ");
    }

}