package com.example.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pickContact();
    }

    private void pickContact() {
        // Create an intent to "pick" a contact, as defined by the content provider URI
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the request went well (OK) and the request was PICK_CONTACT_REQUEST
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            // Perform a query to the contact's content provider for the contact's name
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[] {ContactsContract.Contacts.DISPLAY_NAME}, null, null, null);
            if (cursor.moveToFirst()) { // True if the cursor is not empty
                int columnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = cursor.getString(columnIndex);
                // Do something with the selected contact's name...
                Log.i("lichao", name);
            }
        }
    }
}
