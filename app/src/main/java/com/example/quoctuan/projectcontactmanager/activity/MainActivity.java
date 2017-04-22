package com.example.quoctuan.projectcontactmanager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.quoctuan.projectcontactmanager.R;
import com.example.quoctuan.projectcontactmanager.adapter.ContactAdapter;
import com.example.quoctuan.projectcontactmanager.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editTen,editPhone;
    Button btnLuu;
    ListView lvContact;
    ArrayList<Contact> listContact;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addControls() {

        editTen = (EditText) findViewById(R.id.editTen);
        editPhone = (EditText) findViewById(R.id.editPhone);
        btnLuu = (Button) findViewById(R.id.btnLuu);
        lvContact = (ListView) findViewById(R.id.lvContact);
        listContact = new ArrayList<>();
        contactAdapter = new ContactAdapter(
                MainActivity.this,
                R.layout.item,
                listContact
        );
        lvContact.setAdapter(contactAdapter);

    }

    private void addEvents() {

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyLuu();
            }
        });

    }

    private void xuLyLuu() {
        Contact contact = new Contact(
                editTen.getText().toString(),
                editPhone.getText().toString()
        );
        listContact.add(contact);
        contactAdapter.notifyDataSetChanged();


    }
}
