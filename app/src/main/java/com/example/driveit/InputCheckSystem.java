package com.example.driveit;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class InputCheckSystem {
    public static void createErrorDialog(Context context, String message){
        new AlertDialog.Builder(context).setTitle("Error!").setMessage(message).setPositiveButton("OK", null).setNegativeButton("Cancel", null).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    public static boolean checkAllFieldRegister(Context context,EditText nameField,EditText telField, EditText emailField, EditText passwordField){
        if(nameField.getText().length() == 0 || telField.getText().length() == 0 || emailField.getText().length() == 0 || passwordField.getText().length() == 0){
            createErrorDialog(context, "You must fill every field!");
            return false;
        }else if(!checkNameField(context, nameField)){
            createErrorDialog(context, "Please enter a legal name!");
            return false;
        }else if(!checkTelField(context, telField)){
            createErrorDialog(context, "You must enter a legal phone number!");
            return false;
        }else if(!checkEmailField(context, emailField)){
            createErrorDialog(context, "You must enter a legal email address!");
            return false;
        }
        // TODO add password check system. if else statement
        return true;
    }

    public static boolean checkAllFieldLogin(Context context, EditText emailField, EditText passwordField){
        if(emailField.getText().length() == 0 || passwordField.getText().length() == 0){
            createErrorDialog(context, "You must fill every field!");
            return false;
        }else if(!checkEmailField(context, emailField)){
            createErrorDialog(context, "You must enter a legal email address!");
            return false;
        }
        return true;
    }

    public static boolean checkTelField(Context context, EditText telField){
        if(telField.getText().length() == 13 && telField.getText().charAt(0) != '+') {
            return false;
        }else if(telField.getText().length() != 13){
            if(telField.getText().length() > 10 || telField.getText().length() < 9){
                return false;
            }
        }
        return true;
    }

    public static boolean checkEmailField(Context context, EditText emailField){
        if(emailField.getText().toString().indexOf("@") == 0 || emailField.getText().toString().indexOf("@") == emailField.getText().length() - 1){
            return false;
        }else if(emailField.getText().toString().lastIndexOf('.') - emailField.getText().toString().lastIndexOf('@') == 1){
            return false;
        }
        return true;
    }

    public static boolean checkNameField(Context context, EditText nameField){
        String name = nameField.getText().toString();
        for(int i = 0; i<name.length(); i++){
            int asciiVal = (int)name.charAt(i);
            if(!(asciiVal > 65 && asciiVal < 90) && !(asciiVal > 97 && asciiVal < 122)){
                return false;
            }
        }
        if(nameField.getText().length() < 2){
            return false;
        }
        return true;
    }
}
