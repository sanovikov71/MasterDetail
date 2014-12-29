package com.example.senovik.masterdetail;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.example.senovik.masterdetail.data.DbContract.ContactEntry;


public class SaveRecordTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = SaveRecordTask.class.getSimpleName();
    private final Context mContext;

    public SaveRecordTask(Context context) {
        mContext = context;
    }

    private void saveToContentProvider(String inputStr) {

        String[] projection = inputStr.split(";");

        String fistName;
        String secondName;
        String phone;

        fistName = projection[0];
        secondName = projection[1];
        phone = projection[2];

        ContentValues newRecord = new ContentValues();

        newRecord.put(ContactEntry.COLUMN_FIRST_NAME, fistName);
        newRecord.put(ContactEntry.COLUMN_SECOND_NAME, secondName);
        newRecord.put(ContactEntry.COLUMN_PHONE, phone);

        mContext.getContentResolver().insert(ContactEntry.CONTENT_URI, newRecord);

    }

    @Override
    protected Void doInBackground(String... params) {

        // If there's no zip code, there's nothing to look up.  Verify size of params.
        if (params.length == 0) {
            return null;
        }

        saveToContentProvider("Valery;Sidorov;9005");

        return null;
    }
}
