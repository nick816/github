package com.example.slidingsimplesample;

import com.example.slidingsimplesample.activity.FeedbackActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Feedback extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);
        
        Intent intent = new Intent(getActivity(), FeedbackActivity.class);
		getActivity().startActivity(intent);
		
        return v;
    }
}
