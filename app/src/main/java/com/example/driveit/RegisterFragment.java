package com.example.driveit;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class RegisterFragment extends Fragment {
    Button conBtnReg;
    ToggleButton isTeacherBtn;
    EditText usernameEtReg, emailEtReg, pwdEtReg, phoneEtReg;
    ImageView ivReg;

    String[] info;
    Bitmap image;
    User user;
    Teacher teacher;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    Bitmap userImage;
    ActivityResultLauncher<Intent> arlPhoto;
    ActivityResultLauncher<String> arlGallery;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        init(view);
        // permissions
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        arlPhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    Intent data = result.getData();
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ivReg.setImageBitmap(bitmap);
                }
            }
        });

        arlGallery = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<android.net.Uri>() {
                    @Override
                    public void onActivityResult(android.net.Uri result) {
                        if (result != null) {
                            Toast.makeText(getActivity(), "Gallery", Toast.LENGTH_SHORT).show();
                            ivReg.setImageURI(result);
                        }
                    }
                });
        conBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InputCheckSystem.checkAllFieldRegister(v.getContext(), usernameEtReg, phoneEtReg, emailEtReg, pwdEtReg)) {
                    info[0] = usernameEtReg.getText().toString();
                    info[1] = pwdEtReg.getText().toString();
                    info[2] = emailEtReg.getText().toString();
                    info[3] = phoneEtReg.getText().toString();
                    image = ((BitmapDrawable) ivReg.getDrawable()).getBitmap();
                    int isTeacher = 0;
                    if (isTeacherBtn.isChecked()) {
                        isTeacher = 1;
                    }
                    user = new User(info[0], info[1], info[2], info[3], image, isTeacher);
                    sqdb = mydb.getWritableDatabase();
                    if (!mydb.userExists(user.getUsername(), user.getPassword())){
                        mydb.insert(user);
                        if(isTeacher==1){
                            user.setId(mydb.searchUserId(user.getUsername(),user.getPhone()));
                            teacher = new Teacher(user, 0, 0,"");
//                            Toast.makeText(getActivity(),teacher.toString() , Toast.LENGTH_LONG).show();
//                            mydb.insertTeacher(teacher);
                            Intent intent = new Intent(getActivity(), TeacherRegister.class);
                            userImage = teacher.getImage();
                            teacher.setImage(null);
                            intent.putExtra("teacher", teacher);
                            startActivity(intent);
                        }
                    }
                    else
                        Toast.makeText(getActivity(),"User already exists!!!", Toast.LENGTH_SHORT).show();
                    sqdb.close();
                    usernameEtReg.setText("");
                    pwdEtReg.setText("");
                    emailEtReg.setText("");
                    phoneEtReg.setText("");
                    isTeacherBtn.setChecked(false);
                }
            }
        });

        ivReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        return view;
    }

    private void chooseImage() {
        final CharSequence[] options = {"Take photo", "Choose from Gallery", "Exit"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(options[i].equals("Take photo")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    arlPhoto.launch(intent);
                }else if(options[i].equals("Choose from Gallery")){
                    arlGallery.launch("image/*");
                }else if(options[i].equals("Exit")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void init(View view){
        conBtnReg = view.findViewById(R.id.conBtnReg);
        usernameEtReg = view.findViewById(R.id.usernameEtReg);
        emailEtReg = view.findViewById(R.id.emailEtReg);
        pwdEtReg = view.findViewById(R.id.pwdEtReg);
        phoneEtReg = view.findViewById(R.id.phoneEtReg);
        ivReg = view.findViewById(R.id.ivReg);
        isTeacherBtn = view.findViewById(R.id.isTeacherBtn);
        info = new String[4];
        mydb = new DBHelper(getActivity());
    }


}