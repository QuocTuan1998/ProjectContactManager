package com.example.quoctuan.projectcontactmanager.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quoctuan.projectcontactmanager.R;
import com.example.quoctuan.projectcontactmanager.model.Contact;

public class SmsActivity extends AppCompatActivity {
    TextView txtNguoiNhan;
    EditText editND;
    ImageButton btnSend;

    Contact selectedContact = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        
        addControls();
        addEvents();
    }

    private void addEvents() {

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySend();
            }
        });

    }

    private void xuLySend() {

        final SmsManager sms = SmsManager.getDefault();
        Intent intentSms = new Intent("ACTION_MSG_SENT");
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intentSms,0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int result = getResultCode();
                String msg = "Đã gửi thành công";
                if (result != Activity.RESULT_OK){
                    msg = "Gửi tin thất bại";
                }
                Toast.makeText(SmsActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        },new IntentFilter("ACTION_MSG_SENT"));

        sms.sendTextMessage(selectedContact.getPhone(),
                            null,
                            editND.getText()+"",
                            pendingIntent,null
        );
    }

    private void addControls() {

        txtNguoiNhan = (TextView) findViewById(R.id.txtNguoiNhan);
        editND = (EditText) findViewById(R.id.editND);
        btnSend = (ImageButton) findViewById(R.id.btnSend);

        Intent intent = getIntent();
        selectedContact = (Contact) intent.getSerializableExtra("CONTACT");
        txtNguoiNhan.setText(selectedContact.getName() + "--" + selectedContact.getPhone());

    }
}
