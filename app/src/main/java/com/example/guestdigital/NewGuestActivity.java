package com.example.guestdigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class NewGuestActivity extends AppCompatActivity {
    public EditText et_firstName, et_secondName, et_lastName, et_address, et_phoneNum, et_email, et_comment;
    public TextView Tv_DateOfVisit, Tv_TimeOfVisit;
    public Button btn_submitInfo;
    public ImageView img_visitor;
    public GuestEverything mGuestEverything;
    public DatabaseReference dbRefGuest;
    public Timer timer;
    public Bitmap myBitmap;
    private String currentPhotoPath;
    public File imagefile;
    public Uri imageUri, galleryLocationPath, cropUri;

    //    constants
    private static final int GALLERY_IMAGE_REQUEST = 23;
    private static final int CROP_IMAGE_REQUEST = 24;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_CODE = 100;
    //private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guest);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        dbRefGuest = FirebaseDatabase.getInstance().getReference("guests");

        img_visitor = findViewById(R.id.img_visitor);
        et_firstName = findViewById(R.id.et_firstName);
        et_secondName = findViewById(R.id.et_secondName);
        et_lastName = findViewById(R.id.et_lastName);
        et_address = findViewById(R.id.et_address);
        et_phoneNum = findViewById(R.id.et_phoneNum);
        et_email = findViewById(R.id.et_email);
        et_comment = findViewById(R.id.et_comment);
        Tv_DateOfVisit = findViewById(R.id.Tv_DateOfVisit);
        Tv_TimeOfVisit = findViewById(R.id.Tv_TimeOfVisit);

        //format date
        String formattedDate = new SimpleDateFormat("dd-MMMM-YYYY", Locale.getDefault()).format(new Date());
        Tv_DateOfVisit.setText(formattedDate);
        String formattedTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        Tv_TimeOfVisit.setText(formattedTime);


        btn_submitInfo = findViewById(R.id.btn_submitInfo);
        btn_submitInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGuestInfo();
            }
        });

    }

    //    Camera Permissions
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(NewGuestActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(NewGuestActivity.this,
                    new String[]{permission},
                    requestCode);
        } else {
            Toast.makeText(NewGuestActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }
    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(NewGuestActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(NewGuestActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
       /* else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(NewGuestActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(NewGuestActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }*/
    }

    public void setPhotoPath() {
        //        Photopath to set bitmap
        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
        String fileName = "guestDigitalPhotos";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            imagefile = File.createTempFile(fileName, ".jpg", storageDir);
            currentPhotoPath = imagefile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    camera capture method
    public void dispatchTakePictureIntent() {
        imageUri = FileProvider.getUriForFile(NewGuestActivity.this, "com.example.guestdigital.fileprovider", imagefile);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                myBitmap = BitmapFactory.decodeFile(currentPhotoPath);

                if (myBitmap != null) {
                    img_visitor.setImageBitmap(myBitmap);
                }
            }
            if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                galleryLocationPath = data.getData();
                myBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), galleryLocationPath);
                img_visitor.setImageBitmap(myBitmap);
            }
            //            BIG MOVE CROP BUTTON
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult mResult = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    cropUri = mResult.getUri();
                    img_visitor.setImageURI(cropUri);
//                    myBitmap = mResult.getBitmap();
//                    my_image_viewer.setImageBitmap(myBitmap);

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception ex = mResult.getError();
                    Toast.makeText(this, "Possible Error is: " + ex, Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void addGuestInfo() {
        String firstName = et_firstName.getText().toString().trim();
        if (TextUtils.isEmpty(firstName)) {
            et_firstName.setError("FirstName is Required:!!!");
        }
        String secondName = et_secondName.getText().toString().trim();
        if (TextUtils.isEmpty(secondName)) {
            et_secondName.setError("SecondName is Required:!!!");
        }
        String lastName = et_lastName.getText().toString().trim();
        if (TextUtils.isEmpty(lastName)) {
            et_lastName.setError("LastName is Required:!!!");
        }
        String address = et_address.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            et_address.setError("Address is Required:!!!");
        }
        String phone_num = et_phoneNum.getText().toString().trim();
        if (TextUtils.isEmpty(phone_num)) {
            et_phoneNum.setError("Phone Number is Required:!!!");
        } else if (phone_num.length() <= 9) {
            et_phoneNum.setError("Phone Number length is too short:!!!");
        } else if (phone_num.length() > 10) {
            et_phoneNum.setError("Phone Number length is too long:!!!");
        }
        String email = et_email.getText().toString().trim();
        String comment = et_comment.getText().toString().trim();
        if (TextUtils.isEmpty(comment)) {
            et_comment.setError("Please give a visit reason:!!!");
        }


        String dateOfVisit = Tv_DateOfVisit.getText().toString();

        String timeOfVisit = Tv_TimeOfVisit.getText().toString();

//        push to firebase validation
        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(secondName) && !TextUtils.isEmpty(lastName) &&
                !TextUtils.isEmpty(address) && phone_num.length() == 10 && !TextUtils.isEmpty(comment)) {
            String vID = dbRefGuest.push().getKey();
            mGuestEverything = new GuestEverything(vID, firstName, secondName, lastName, address,
                    phone_num, email, comment, dateOfVisit, timeOfVisit);
            assert vID != null;
            dbRefGuest.child(vID).setValue(mGuestEverything);
            Toast.makeText(NewGuestActivity.this, "Guest Added Successfully:", Toast.LENGTH_LONG).show();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(new Intent(NewGuestActivity.this, GuestList.class));
                    finish();
                }
            }, 3000);
        } else {
            Toast.makeText(NewGuestActivity.this, "Error!!! Visitor Not Added:", Toast.LENGTH_LONG).show();
        }
// end of code
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_guest_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        View mView = findViewById(R.id.img_visitor);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_capture) {
            setPhotoPath();
            dispatchTakePictureIntent();
        } else if (id == R.id.action_gallery) {
            chooseImageFromGallery(mView);
        } else if (id == R.id.action_crop) {
            captureCroppedImage();
        }
        return super.onOptionsItemSelected(item);
    }

    public void captureCroppedImage() {
        CropImage.activity().start(NewGuestActivity.this);
    }

    public void chooseImageFromGallery(View view) {
        try {
            Intent myIntent = new Intent();
            myIntent.setType("image/*");
            myIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(myIntent, GALLERY_IMAGE_REQUEST);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        img_visitor.setImageBitmap(null);
//    }
}