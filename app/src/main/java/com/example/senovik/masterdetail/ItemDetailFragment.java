package com.example.senovik.masterdetail;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.senovik.masterdetail.data.DbContract.ContactEntry;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    private TextView mFirstNameView;
    private TextView mSecondNameView;
    private TextView mPhoneView;

    private static final String[] CONTACT_COLUMNS = {
            ContactEntry.TABLE_NAME + "." + ContactEntry._ID,
            ContactEntry.COLUMN_FIRST_NAME,
            ContactEntry.COLUMN_SECOND_NAME,
            ContactEntry.COLUMN_PHONE
    };

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);


        mFirstNameView = (TextView) rootView.findViewById(R.id.detail_first_name);
        mSecondNameView = (TextView) rootView.findViewById(R.id.detail_second_name);
        mPhoneView = (TextView) rootView.findViewById(R.id.detail_phone);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = ContactEntry.COLUMN_FIRST_NAME + " ASC";

        // TODO param of buildContactUri must be changed
        Uri contactUri = ContactEntry.buildContactUri(Integer.valueOf(ContactEntry._ID));

        return new CursorLoader(
                getActivity(),
                contactUri,
                CONTACT_COLUMNS,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null && data.moveToFirst()) {

            String firstName = data.getString(data.getColumnIndex(ContactEntry.COLUMN_FIRST_NAME));
            mFirstNameView.setText(firstName);

            String secondName = data.getString(data.getColumnIndex(ContactEntry.COLUMN_SECOND_NAME));
            mSecondNameView.setText(secondName);

            String phone = data.getString(data.getColumnIndex(ContactEntry.COLUMN_PHONE));
            mPhoneView.setText(phone);

//            // We still need this for the share intent
//            mForecast = String.format("%s - %s - %s/%s", zipcode, name, city, "sergeynovikov");
//
//            if (mShareActionProvider != null) {
//                mShareActionProvider.setShareIntent(createShareForecastIntent());
//            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
