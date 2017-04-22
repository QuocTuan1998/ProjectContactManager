package com.example.quoctuan.projectcontactmanager.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.quoctuan.projectcontactmanager.R;
import com.example.quoctuan.projectcontactmanager.activity.MainActivity;
import com.example.quoctuan.projectcontactmanager.activity.SmsActivity;
import com.example.quoctuan.projectcontactmanager.model.Contact;

import java.util.List;

/**
 * Created by Tuấn on 4/22/2017.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {
    @NonNull
    Activity context;
    @LayoutRes
    int resource;
    @NonNull
    List<Contact> objects;

    public ContactAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);

        TextView txtTen = (TextView) row.findViewById(R.id.txtTen);
        TextView txtPhone = (TextView) row.findViewById(R.id.txtPhone);
        ImageButton btnCall = (ImageButton) row.findViewById(R.id.btnCall);
        ImageButton btnDelete = (ImageButton) row.findViewById(R.id.btnDelete);
        ImageButton btnSms = (ImageButton) row.findViewById(R.id.btnSms);

        final Contact contact = this.objects.get(position);
        txtTen.setText(contact.getName());
        txtPhone.setText(contact.getPhone());

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyCall(contact);
            }
        });
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySms(contact);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyDelete(contact);
            }
        });
        return row;
    }

    private void xuLyDelete(Contact contact) {
//        xóa(adapter có thế xóa trực tiếp)
        this.remove(contact);
    }

    private void xuLySms(Contact contact) {
//        vì ở đây ta gọi MainActivity là context cho nên phải dùng là this.context
        Intent intent = new Intent(this.context, SmsActivity.class);
//        gửi contact qua
        intent.putExtra("CONTACT",contact);
        this.context.startActivity(intent);

    }

    private void xuLyCall(Contact contact) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uriCall = Uri.parse("tel:" + contact.getPhone());
        intent.setData(uriCall);
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.context.startActivity(intent);

    }
}
