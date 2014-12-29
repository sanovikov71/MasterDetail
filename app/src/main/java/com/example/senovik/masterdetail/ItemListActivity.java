package com.example.senovik.masterdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details
 * (if present) is a {@link ItemDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ItemListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ItemListActivity extends FragmentActivity
        implements ItemListFragment.Callbacks, EditionFragment.OnButtonClickedListener {

    public static final String LOG_TAG = "novikov_logs";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link ItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {

        Log.d(LOG_TAG, "in onItemSelected");

        if (mTwoPane) {

            if (id.equals("1")) {

                Bundle arguments = new Bundle();
                arguments.putString(EditionFragment.ARG_ITEM_ID, id);
                EditionFragment fragment = new EditionFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();

            } else {

                Bundle arguments = new Bundle();
                arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
                ItemDetailFragment fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            }

        } else {

            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);

        }

    }

    @Override
    public void onEditContextItemClicked(String id) {


        Log.d(LOG_TAG, "in onEditContextItemClicked");

        if (mTwoPane) {

            if (!id.equals("1")) {

                Bundle arguments = new Bundle();
                arguments.putString(EditionFragment.ARG_ITEM_ID, id);
                EditionFragment fragment = new EditionFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();

            }

        } else {

//            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
//            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
//            startActivity(detailIntent);

        }

    }

    @Override
    public void onButtonClicked(String firstName, String secondName) {

        Log.d(LOG_TAG, "in onButtonClicked");

        new SaveRecordTask(this).execute(firstName);

        ItemListFragment.mListAdapter.notifyDataSetChanged();

    }

}
