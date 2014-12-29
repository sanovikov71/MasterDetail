package com.example.senovik.masterdetail.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class Provider extends ContentProvider {

    private static final int ALL_CONTACTS = 100;
    private static final int ONE_CONTACT = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private DbHelper mOpenHelper;
    private static final SQLiteQueryBuilder sContactQueryBuilder;

    static {

        sContactQueryBuilder = new SQLiteQueryBuilder();
        sContactQueryBuilder.setTables(
                DbContract.ContactEntry.TABLE_NAME
        );

    }

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DbContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DbContract.PATH_CONTACTS, ALL_CONTACTS);
        matcher.addURI(authority, DbContract.PATH_CONTACTS + "/*", ONE_CONTACT);

        return matcher;

    }

    @Override
    public boolean onCreate() {

        mOpenHelper = new DbHelper(getContext());
        return true;

    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case ALL_CONTACTS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DbContract.ContactEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                Log.d("MYLOG", "in provider all stores: count = " + retCursor.getCount());
                break;
            }
            case ONE_CONTACT: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DbContract.ContactEntry.TABLE_NAME,
                        projection,
                        DbContract.ContactEntry._ID + " = '" +
                                ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder);
                Log.d("MYLOG", "in provider one store: count = " + retCursor.getCount());
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        Log.d("MYLOG", "in provider: count = " + retCursor.getCount());
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ALL_CONTACTS:
                return DbContract.ContactEntry.CONTENT_TYPE;
            case ONE_CONTACT:
                return DbContract.ContactEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match) {
            case ALL_CONTACTS:
                long _id = db.insert(DbContract.ContactEntry.TABLE_NAME, null,
                        values);
                if (_id > 0)
                    returnUri = DbContract.ContactEntry.buildContactUri(_id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        int rowsDeleted;

        switch (match) {
            case ALL_CONTACTS:
                rowsDeleted = db.delete(DbContract.ContactEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (null == selection || 0 != rowsDeleted) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        int rowsUpdated;

        switch (match) {
            case ALL_CONTACTS:
                rowsUpdated = db.update(DbContract.ContactEntry.TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (0 != rowsUpdated) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;

    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ALL_CONTACTS:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DbContract.ContactEntry.TABLE_NAME,
                                null, value);
                        if (-1 != _id) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }

    }

}
