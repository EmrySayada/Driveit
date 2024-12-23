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
import android.widget.Toast;

public class RegisterFragment extends Fragment {
    Button conBtnReg, updateBtnReg;
    EditText usernameEtReg, emailEtReg, pwdEtReg, phoneEtReg;
    ImageView ivReg;

    String[] info;
    Bitmap image;
    User user;
    DBHelper mydb;
    SQLiteDatabase sqdb;
    ActivityResultLauncher<Intent> arlPhoto;
    ActivityResultLauncher<String> arlGallery;

    User oldUser, userToUpdate;

    String flagActivity="register";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        init(view);
        // permissions
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        Intent intent=getActivity().getIntent();
        flagActivity = intent.getStringExtra("flagActivity");
        if(intent.hasExtra("flagActivity")&&flagActivity.equals("login")){
            if (intent.hasExtra("user")){
                oldUser=(User)intent.getSerializableExtra("user");
                Bitmap oldImage = mydb.getPicture(intent.getByteArrayExtra("image"));
                oldUser.setImage(oldImage);
                if(oldUser!=null){
                    conBtnReg.setEnabled(false);
                    usernameEtReg.setText(oldUser.getUsername());
                    usernameEtReg.setEnabled(false);
                    pwdEtReg.setText(oldUser.getPassword());
                    pwdEtReg.setEnabled(false);
                    emailEtReg.setText(oldUser.getEmail());
                    phoneEtReg.setText(oldUser.getPhone());
                    ivReg.setImageBitmap(oldUser.getImage());
                }
            }
        }
        updateBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info[0]=usernameEtReg.getText().toString();
                info[1]=pwdEtReg.getText().toString();
                info[2]=emailEtReg.getText().toString();
                info[3]=phoneEtReg.getText().toString();
                image=((BitmapDrawable)ivReg.getDrawable()).getBitmap();
                userToUpdate=new User(info[0],info[1],info[2],info[3],image);

                if(!info[0].equals("")&&!info[1].equals("")&&!info[2].equals("")&&!info[3].equals("")) {
                    //TODO check inputs
                    sqdb = mydb.getWritableDatabase();
                    if(mydb.userExists(userToUpdate.getUsername(),userToUpdate.getPassword()))
                        mydb.updateUser(oldUser,userToUpdate);
                    else
                        Toast.makeText(getActivity(),"User already exists!!!", Toast.LENGTH_SHORT).show();
                    sqdb.close();
                }
                else
                    Toast.makeText(getActivity(),"Fill all fields!!!", Toast.LENGTH_SHORT).show();
                usernameEtReg.setText("");
                pwdEtReg.setText("");
                emailEtReg.setText("");
                phoneEtReg.setText("");
                getActivity().finish();
            }
        });
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
                if(InputCheckSystem.checkAllFieldRegister(v.getContext(), usernameEtReg, phoneEtReg, emailEtReg, pwdEtReg)){
                    info[0]=usernameEtReg.getText().toString();
                    info[1]=pwdEtReg.getText().toString();
                    info[2]=emailEtReg.getText().toString();
                    info[3]=phoneEtReg.getText().toString();
                    image=((BitmapDrawable)ivReg.getDrawable()).getBitmap();
                    user=new User(info[0], info[1], info[2], info[3], image);
                    sqdb = mydb.getWritableDatabase();
                    if(!mydb.userExists(user.getUsername(),user.getPassword()))
                        mydb.insert(user);
                    else
                        Toast.makeText(getActivity(),"User already exists!!!", Toast.LENGTH_SHORT).show();
                    sqdb.close();
                    usernameEtReg.setText("");
                    pwdEtReg.setText("");
                    emailEtReg.setText("");
                    phoneEtReg.setText("");
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
        updateBtnReg = view.findViewById(R.id.updateBtnReg);
        usernameEtReg = view.findViewById(R.id.usernameEtReg);
        emailEtReg = view.findViewById(R.id.emailEtReg);
        pwdEtReg = view.findViewById(R.id.pwdEtReg);
        phoneEtReg = view.findViewById(R.id.phoneEtReg);
        ivReg = view.findViewById(R.id.ivReg);
        info = new String[4];
        mydb = new DBHelper(getActivity());
    }


}