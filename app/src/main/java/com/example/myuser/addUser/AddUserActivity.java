package com.example.myuser.addUser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myuser.R;
import com.example.myuser.database.SqliteHelper;
import com.example.myuser.model.User;;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddUserActivity extends AppCompatActivity {

    EditText name,email,location;
    Uri image=null;
    Button save,pickImage;
    private static final int IMAGE_PICKER_PERMISSIONS = 1;
    private static final int PICK_IMAGE_REQUEST_RES = 2;
    byte[] byteArray;


    public static SqliteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        getSupportActionBar().setTitle(R.string.addUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sqLiteHelper = new SqliteHelper(this);
        findItems();
    }

    private void findItems() {
        name = findViewById(R.id.et_user_name);
        email = findViewById(R.id.et_user_email);
        location = findViewById(R.id.et_user_location);
        save = findViewById(R.id.btn_save);
        pickImage = findViewById(R.id.btn_pick_image);


        //Click Events
        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestForImage();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSomethingIsEmpty()){
                    convertImagetoByteArrayAndSave(image);
                }else{
                    Toast.makeText(AddUserActivity.this, "All Fields are Mandatory !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void convertImagetoByteArrayAndSave(Uri image) {

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();
        sqLiteHelper.insertUser(new User(name.getText().toString(),email.getText().toString(),location.getText().toString(),byteArray));
        Toast.makeText(AddUserActivity.this, "Data Added Successfully !!", Toast.LENGTH_SHORT).show();
        finish();
        onBackPressed();
    }

    private void requestForImage() {
        String[] PERMISSIONS = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        if (hasPermissions(AddUserActivity.this, PERMISSIONS)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "SELECT_PICTURE"), PICK_IMAGE_REQUEST_RES);
        } else {
            ActivityCompat.requestPermissions(AddUserActivity.this, PERMISSIONS, IMAGE_PICKER_PERMISSIONS);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_RES && resultCode == RESULT_OK && data != null && data.getData() != null) {
            image = data.getData();
            pickImage.setText("Image is selected");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case IMAGE_PICKER_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestForImage();
                } else {
                    Toast.makeText(this, "Permission Denied !!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    private boolean isSomethingIsEmpty() {
        if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || location.getText().toString().isEmpty() || image==null){
            return false;
        }
        return true;
    }
}