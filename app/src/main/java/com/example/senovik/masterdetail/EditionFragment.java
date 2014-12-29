package com.example.senovik.masterdetail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EditionFragment extends Fragment implements View.OnClickListener {

    private EditText mFirstName;
    private EditText mSecondName;

    public static final String ARG_ITEM_ID = "item_id";

    private OnButtonClickedListener mCallback;

    private int mActivatedPosition = ListView.INVALID_POSITION;

    public interface OnButtonClickedListener {
        public void onButtonClicked(String firstName, String secondName);
    }

    public EditionFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnButtonClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edition, container, false);

//        if (mItem != null) {
//            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.content);
//        }

        mFirstName = (EditText) rootView.findViewById(R.id.first_name_field);
        mSecondName = (EditText) rootView.findViewById(R.id.second_name_field);
        EditText phone = (EditText) rootView.findViewById(R.id.phone_field);

        mFirstName.setText("Ivan");
        mSecondName.setText("Petrov");
        phone.setText("9001");

        Button ok = (Button) rootView.findViewById(R.id.ok_button);
        Button cancel = (Button) rootView.findViewById(R.id.cancel_button);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        Log.d("edition_fragment", "in onCreateView");

        return rootView;
    }


    @Override
    public void onClick(View v) {

        Log.d("edition_fragment", "in onClick");
        mCallback.onButtonClicked(mFirstName.getText().toString(),
                mSecondName.getText().toString());

    }

}
