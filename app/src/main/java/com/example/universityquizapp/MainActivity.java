package com.example.universityquizapp;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Database db;
    EditText user,pass1,pass2;
    Button btnRegister, btnLogin, takePicture, fingerprint, google;
    ImageView profilePicture;

    Uri image_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Database resources
        db = new Database(this);
        user = (EditText) findViewById(R.id.usernameText);
        pass1 = (EditText) findViewById(R.id.passwordText);
        pass2 = (EditText) findViewById(R.id.passwordConfirmText);

        // user authentication buttons
        btnRegister = (Button) findViewById(R.id.registerBtn);
        btnLogin = (Button) findViewById(R.id.loginRegisterBtn);

        // Capture image
        profilePicture = findViewById(R.id.profile_picture);
        takePicture = findViewById(R.id.captureBtn);

        //FingerPrint
        final Executor executor = Executors.newSingleThreadExecutor();

        final BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("Fingerprint Authentication")
                .setSubtitle("Another way to log in")
                .setDescription("Place finger under your fingerprint sensor")
                .setNegativeButton("Canel", executor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).build();

        Button authenticate = findViewById(R.id.fingerprintBtn);

        final MainActivity activity = this;

        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startGame();
                            }
                        });
                    }
                });
            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If OS is marshmallow or higher, request runtime permissions
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        // Request again
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        // Show popup request
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        // Permissions granted
                        openCamera();
                    }
                } else {
                    //System is lower than marshmallow
                    openCamera();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, loginActivity.class);
                startActivity(i);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = user.getText().toString();
                String s2 = pass1.getText().toString();
                String s3 = pass2.getText().toString();
                if(s1.equals("") || s2.equals("") || s3.equals("") ) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (s2.equals(s3)) {
                        Boolean checkUser = db.checkUser(s1);
                        if (checkUser == true) {
                            Boolean insert = db.insert(s1, s2);
                            if (insert == true) {
                                Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                            loginAct();
                        }
                    }
                }
            }
        });
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    // Permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted from popup
                    openCamera();
                } else {
                    //Permission not granted from popup
                    Toast.makeText(this, "Permission was denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //When image is captured from camera
        if(resultCode == RESULT_OK) {
            // set image to image view on page
            profilePicture.setImageURI(image_uri);
        }
    }

    /** The login page once registered **/
    public void loginAct() {
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }

    private void startGame() {
        Intent intent = new Intent(this, main.class);
        startActivity(intent);
    }

//    /** The main home page once logged in */
//    public void mainHome(View view) {
//        Intent intent = new Intent(this, mainScreenActivity.class);
//        startActivities(intent);
//    }
//
//    /** Called when the user clicks the Instruction button */
//    public void instructionAct(View view) {
//        Intent intent = new Intent(this, InstructionActivity.class);
//        startActivity(intent);
//    }
//
//    /** Called when the user clicks the leaderboard button */
//    public void leaderboardAct(View view) {
//        Intent intent = new Intent(this, leaderboardActivity.class);
//        startActivity(intent);
//    }
}
