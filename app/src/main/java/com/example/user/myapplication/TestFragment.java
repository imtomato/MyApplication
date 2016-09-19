package com.example.user.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by user on 2016/9/19.
 */
public class TestFragment extends Fragment {

    public static final String debug_tag = "fragmentTag";
    public static final String msgTextKey = "msg";
    String mMessage;
    View fragmentView;

    public static TestFragment newInstance(String message) {

        Bundle args = new Bundle();
        args.putString(msgTextKey, message);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle params = this.getArguments();
        mMessage = params.getString(msgTextKey);
        Log.d(debug_tag, mMessage + ":onCreate");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(debug_tag, mMessage + ":onCreateView");
        if (fragmentView == null) { //initialize frangmentView
            fragmentView = inflater.inflate(R.layout.fragment_test, null);
            TextView msgText = (TextView) fragmentView.findViewById(R.id.textView);
            msgText.setText(mMessage);
        }
        return fragmentView; //return which layout to show
    }

    @Override
    public void onAttach(Context context) {
        Log.d(debug_tag, mMessage + ":onAttach");
        super.onAttach(context);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(debug_tag, mMessage + ":onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(debug_tag, mMessage + ":onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(debug_tag, mMessage + ":onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(debug_tag, mMessage + ":onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(debug_tag, mMessage + ":onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(debug_tag, mMessage + ":onDetach");
    }
}
