package com.example.driveit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
    Button btnLogin;
    EditText etEmailLogin, etPasswordLogin;

    DBHelper mydb;
    SQLiteDatabase sqdb;

    SharedPreferences sp;

    SharedPreferences.Editor editor;
    Boolean conn = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        conn = sp.getBoolean("isConnected", false);
        if(conn){
            Toast.makeText(getActivity(), "You are already logged in!", Toast.LENGTH_SHORT).show();
            // user already logged in move it to the next page
            transfetUserAfterLogin(sp.getBoolean("isTeacher", false));
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InputCheckSystem.checkAllFieldLogin(v.getContext(), etEmailLogin, etPasswordLogin)){
                    String email = etEmailLogin.getText().toString();
                    String password = etPasswordLogin.getText().toString();

                    sqdb = mydb.getWritableDatabase();
                    boolean isTeacher = mydb.isTeacherInDB(email, password);
                    if(mydb.userExists(email, password)){
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.putBoolean("isTeacher", isTeacher);
                        editor.putBoolean("isConnected", true);
                        editor.commit();
                        transfetUserAfterLogin(isTeacher);
                    }else{
                        Toast.makeText(getActivity(), "Incorrect credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public void init(View view){
        btnLogin = view.findViewById(R.id.btnLogin);
        etEmailLogin = view.findViewById(R.id.etEmailLogin);
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin);
        sp = getActivity().getSharedPreferences("PREFS_FILE", Context.MODE_PRIVATE);
        editor=sp.edit();
        mydb = new DBHelper(getActivity());
        sqdb = mydb.getWritableDatabase();

        sqdb.close();
    }

    public void transfetUserAfterLogin(boolean isTeacher){
        if(isTeacher){
            Intent intent = new Intent(getActivity(), TeacherActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getActivity(), PupilActivity.class);
            startActivity(intent);
        }
    }
}