package com.hien.final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                    Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();}
                }, 4000);
            }


        // Create dialog
//        AlertDialog.Builder builder = new AlertDialog.Builder( StartActivity.this);
//        builder.setTitle("Notification");
//        builder.setMessage("Do you want to continue?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Handle the action when the user clicks "Yes"
//                new Handler().postDelayed(() -> {
//                    Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
//                    startActivity(mainIntent);
//                    finish();
//                }, 4000);
//            }});
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
//        {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Handle the action when the user clicks "Cancel"
//                MyDialogFragment dialogFragment = new MyDialogFragment();
//                dialogFragment.show(getSupportFragmentManager(), "dialog_tag");
//            }});
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();

    }

//    public static class MyDialogFragment extends DialogFragment {
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
//                savedInstanceState) {
//            View view = inflater.inflate(R.layout.fragment_dialog, container, false);
//            return view;
//        }
//    }
