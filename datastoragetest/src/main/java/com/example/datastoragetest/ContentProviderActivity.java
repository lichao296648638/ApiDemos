package com.example.datastoragetest;

import android.Manifest;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore;
import android.provider.UserDictionary;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ContentProviderActivity extends
        Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    TextView tv_data;
    EditText et_search;
    Button bt_search;
    String[] mProjection = new String[]{
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
    };

    String mSelectionClause = null;

    // If non-null, this is the current filter the user has provided.
    String mCurFilter;

    String[] mSelectionArgs = {""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content_provider);
        tv_data = (TextView) findViewById(R.id.tv_data);
        et_search = (EditText) findViewById(R.id.et_search);
        bt_search = (Button) findViewById(R.id.bt_search);
//        queryType();
//        delete();
//        update();
//        insert();
//        queryProvider();
//        querySingleRow();
//        getLoaderManager().initLoader(0, null, this);

        batchOperate();

    }

    private void queryProvider() {

        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
        );
        String temp = "";
        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            int i_displaynameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME),
                    i_heightIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT),
                    i_idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            do {
                String displayname = cursor.getString(
                        i_displaynameIndex
                );
                String height = cursor.getString(
                        i_heightIndex
                );
                String id = cursor.getString(
                        i_idIndex
                );

                temp += "displayname:" + displayname + "\n" +
                        "height:" + height + "\n" +
                        "id:" + id + "\n" + "\n";

            } while (cursor.moveToNext());
            tv_data.setText(temp);
        }


    }

    private void querySingleRow() {
        Uri singleUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 32);
        Cursor cursor = getContentResolver().query(
                singleUri,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        int i_displaynameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME),
                i_heightIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT),
                i_idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

        String displayname = cursor.getString(
                i_displaynameIndex
        );
        String height = cursor.getString(
                i_heightIndex
        );
        String id = cursor.getString(
                i_idIndex
        );
        String temp = "";
        temp += "displayname:" + displayname + "\n" +
                "height:" + height + "\n" +
                "id:" + id + "\n" + "\n";
        tv_data.setText(temp);
    }

    public void search(View view) {
        String mSearchString = et_search.getText().toString();

        if (TextUtils.isEmpty(mSearchString)) {
            mSelectionClause = null;
            mSelectionArgs = null;
        } else {
            mSelectionClause = MediaStore.Images.Media._ID + " = ?";
            mSelectionArgs[0] = mSearchString;
        }


        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                mProjection,
                mSelectionClause,
                mSelectionArgs,
                null);


        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int i_displaynameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME),
                    i_idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

            String displayname = cursor.getString(
                    i_displaynameIndex
            );
            String id = cursor.getString(
                    i_idIndex
            );
            String temp = "";
            temp += "displayname:" + displayname + "\n" +
                    "id:" + id + "\n" + "\n";
            tv_data.setText(temp);
        }
    }

    private void insert() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media._ID, 99);
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    private void update() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.HEIGHT, "100");
        getContentResolver().update(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues,
                MediaStore.Images.Media._ID + "=36",
                null);
    }

    private void delete() {
        getContentResolver().delete(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.Media._ID + "=36",
                null);
    }

    private void queryType(){
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        tv_data.setText(cursor.getType(0) + "\n"
                + getContentResolver().getType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI));

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are
        // currently filtering.

        String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
            Contacts._ID,
            Contacts.DISPLAY_NAME
        };

        Uri baseUri;
        if (mCurFilter != null) {
            baseUri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI,
                    Uri.encode(mCurFilter));
        } else {
            baseUri = Contacts.CONTENT_URI;
        }

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + Contacts.DISPLAY_NAME + " != '' ))";
        return new CursorLoader(this, baseUri,
                CONTACTS_SUMMARY_PROJECTION, select, null,
                Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        data.moveToFirst();

        int idIndex = data.getColumnIndexOrThrow(Contacts._ID);
        int displayIndex = data.getColumnIndexOrThrow(Contacts.DISPLAY_NAME);
        String temp = "";
        if (data != null && data.getCount() > 0) {
            do {
                String id = data.getString(idIndex);
                String display = data.getString(displayIndex);
                temp += "id = " + id + "\n" +
                        "display = " + display + "\n\n";
            } while (data.moveToNext());
        }
        tv_data.setText(temp);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void batchOperate() {

        String mSelectionClause = ContactsContract.RawContacts._ID + "=1";

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withYieldAllowed(true)
                .build());



        ops.add(
                ContentProviderOperation.newAssertQuery(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(mSelectionClause, null)
                .withExpectedCount(1)
                .build()
        );

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "235555555")
                .build()
        );

        // 批量执行,返回执行结果集
        ContentProviderResult[] results = null;
        try {
            String temp = "";
            results = getContentResolver().applyBatch(ContactsContract.AUTHORITY,
                    ops);
            for (int i = 0; i < results.length;i ++) {
                temp += results[i].toString() + "\n";
            }
            tv_data.setText(temp);

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

