package com.lizlam.randomman;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    TextView tv;
    String manpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);

        try {
            manpage = RandomManPage.get(getAssets().open("cmds_list.txt"));
            tv.setText(manpage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String cellNumber = null;
        // Uncomment if you want to generate randome cell number
        // Warning app will text this number a man page without confirmation!
        // String cellNumber = getRandomCellNumber();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cellNumber != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + cellNumber));
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"));
                intent.putExtra("sms_body", manpage);
                startActivity(intent);
                tv.setText(manpage);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getRandomCellNumber() {
        String phone = null;

        // Get all contacts with a phone number
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER + " = 1", null, null);

        // Randomly pick one of those contacts
        int rand = (int) Math.floor(Math.random() * cursor.getCount());
        cursor.move(rand);

        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

        // Create phone cursor object using Phone table and join on id column of above cursor object
        Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);

        // Iterate through phone numbers until there is one of Mobile type
        while (phoneCursor.moveToNext()) {
            String name = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            int type = phoneCursor.getInt(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
            if (type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                Toast.makeText(this, name + " " + phone, Toast.LENGTH_LONG).show();
            }
        }
        phoneCursor.close();
        cursor.close();
        return phone;
    }
}
