package com.example.readcontactusingcursorload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public  class MainActivity extends AppCompatActivity {

      private static final String TAG ="MainActivity";
      private ArrayList<Contact> contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts = new ArrayList<>();
        ContentResolver  resolver = getContentResolver();

        Cursor cursor             = resolver.query(ContactsContract.Contacts.CONTENT_URI,null,null, null , null);

        String id ;
        String name ;



        while (cursor.moveToNext()) {

            id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Log.i("My Info", id + "= " + name);
            Contact contact = new Contact();
            Cursor phones = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
            String number = null;
            while (phones.moveToNext()) {
                number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                switch (type) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        // do something with the Home number here...
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                        // do something with the Mobile number here...
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                        // do something with the Work number here...
                        break;
                }
            }
            phones.close();
            contact.setPhoneNumber(number);
            contact.setName(name);
            contacts.add(contact);
        }
     

    }
}
