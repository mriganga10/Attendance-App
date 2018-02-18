package com.example.android.android_attendance_system;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.NavUtils;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.android_attendance_system.Login.SessionManager;
import com.example.android.android_attendance_system.data.Dbhelper;
import com.example.android.android_attendance_system.model.Batch;

import static android.R.attr.id;

/**
 * Created by LOKESH on 13-08-2017.
 */

public class AddNewBatch extends AppCompatActivity {
    private EditText batch;
    private  EditText branch;
    private EditText sub;
    private EditText sem;
    Boolean mchanged=false;
    Dbhelper db;
    String s;
    String clickedBatchName;
    String clickedBatchBranch;
    String clickedBatchSub;
    String clickedBatchSem;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mchanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_batch);
        Intent intent = getIntent();
        s=intent.getExtras().get("@strings/AddingBatch").toString();

//        SessionManager see = new SessionManager(this);
//        see.checkLogin();

        Log.i("intentvalue",s);
        if(Integer.parseInt(s)==1) {
            setTitle("Add Batch");
            clickedBatchBranch=intent.getExtras().get("BatchBranch").toString();
            clickedBatchName=intent.getExtras().get("BatchName").toString();
            clickedBatchSem=intent.getExtras().get("BatchSem").toString();
            clickedBatchSub=intent.getExtras().get("BatchSub").toString();
        }
        else{
            setTitle("Update Batch");
            /*TextView b=(TextView) findViewById(R.id.batch_in_list);
            TextView su=(TextView) findViewById(R.id.subjec);
            TextView branchh=(TextView) findViewById(R.id.branch_item);
            TextView semester=(TextView) findViewById(R.id.sem_item);*/
            clickedBatchBranch=intent.getExtras().get("BatchBranch").toString();
            clickedBatchName=intent.getExtras().get("BatchName").toString();
            clickedBatchSem=intent.getExtras().get("BatchSem").toString();
            clickedBatchSub=intent.getExtras().get("BatchSub").toString();

            batch=(EditText) findViewById(R.id.batch_name);
            sub=(EditText) findViewById(R.id.edit_sub);
            branch=(EditText) findViewById(R.id.branch);
            sem=(EditText) findViewById(R.id.semester);

            batch.setText(clickedBatchName);
            sub.setText(clickedBatchSub);
            branch.setText(clickedBatchBranch);
            sem.setText(clickedBatchSem);


        }

        try{batch.setOnTouchListener(mTouchListener);
        branch.setOnTouchListener(mTouchListener);
        sub.setOnTouchListener(mTouchListener);
        sem.setOnTouchListener(mTouchListener);}
        catch (Exception e){

        }

        Log.i("List",""+clickedBatchName);
        //catch (Exception e){}
        db = new Dbhelper(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu, menu);
        /*if(Integer.parseInt(s)==1) {
            MenuItem m=menu.findItem(R.id.action_delete);
            m.setVisible(false);
            invalidateOptionsMenu();
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                if(Integer.parseInt(s)==1) {
                    batch=(EditText) findViewById(R.id.batch_name);
                    sub=(EditText) findViewById(R.id.edit_sub);
                    //EditText mBaseID=(EditText) findViewById(R.id.edit_baseID);
                    branch=(EditText) findViewById(R.id.branch);
                    sem=(EditText) findViewById(R.id.semester);
                    String mbatch="";
                    String msubject="";
                    String mbranch="";
                    String msemester="";
                    mbatch = batch.getText().toString().trim();
                    msubject = sub.getText().toString().trim();
                    //String baseId = mBaseID.getText().toString().trim();
                    mbranch = branch.getText().toString().trim();
                    msemester = sem.getText().toString().trim();
                    //Log.i("bbbbb",mbatch);
                    if(TextUtils.isEmpty(mbatch) || TextUtils.isEmpty(mbranch) || TextUtils.isEmpty(msemester) || TextUtils.isEmpty(msubject)){
                        notChangedDialogueBox();
                        return true;
                    }else{
                        insertBatch(mbatch,msubject,mbranch,msemester);
                        NavUtils.navigateUpFromSameTask(this);
                        return true;
                    }
                }
                if(Integer.parseInt(s)==2){
                    //Log.i("Detect","detected");
                    long idd=db.getBatchID(clickedBatchName,clickedBatchBranch,clickedBatchSem,clickedBatchSub);
                    Log.i("IIII",""+idd);

                    batch=(EditText) findViewById(R.id.batch_name);
                    branch=(EditText) findViewById(R.id.branch);
                    sem=(EditText) findViewById(R.id.semester);
                    sub=(EditText) findViewById(R.id.edit_sub);
                    String mbatch=batch.getText().toString();
                    String mbranch=branch.getText().toString();
                    String mSem=sem.getText().toString();
                    String mSub=sub.getText().toString();
                    //Log.i("TTT",""+TextUtils.isEmpty(mbatch));
                    if(TextUtils.isEmpty(mbatch) && TextUtils.isEmpty(mbranch) && TextUtils.isEmpty(mSem) && TextUtils.isEmpty(mSub)){
                        notChangedDialogueBox();
                        Log.i("TTT",""+TextUtils.isEmpty(mbatch));
                        return true;
                    }
                    else if(TextUtils.isEmpty(mbatch)){
                        Toast.makeText(this,"Batch Name not entered",Toast.LENGTH_SHORT).show();
                        //Log.i("TTTt",""+TextUtils.isEmpty(mbatch));
                        return true;
                    }
                    else if(TextUtils.isEmpty(mbranch)){
                        Toast.makeText(this,"Branch not entered",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else if(TextUtils.isEmpty(mSem)){
                        Toast.makeText(this,"Semester not entered",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else if(TextUtils.isEmpty(mSub)){
                        Toast.makeText(this,"Subject not entered",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    //Log.i("checj",""+mCurrentBatchSemchanged+mCurrentBatchNamechanged+mCurrentBatchSubchanged+mCurrentBatchBranchchanged);
                    else{
                        Batch b=new Batch(mbatch,mbranch,mSem,mSub);
                        int i=db.updateBatch(b,idd);
                        NavUtils.navigateUpFromSameTask(this);
                        return true;
                    }


                }
        }
        return super.onOptionsItemSelected(item);
    }
    private void insertBatch(String mbatch,String msubject,String mbranch,String msemester) {
        //nothingChangedDialogueBox();
        // Read from input fields
        Batch b=new Batch(mbatch,mbranch,msemester,msubject);
        long id=db.createBatches(b);
        Log.i("IDDD",""+ id);




    }
   /* private void showDeleteConfirmationDialog(final long i) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirm Deletion");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                //Batch b=new Batch(batch,branch,semester,subject);
                Log.i("IIIII",""+i);
                db.deleteBatch(i,false);
                NavUtils.navigateUpFromSameTask(AddNewBatch.this);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // Close the activity
        //finish();
    }
*/
    public void notChangedDialogueBox() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Nothing entered!!!");
            builder.setPositiveButton("Don't Save", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    finish();
                }
            });
            builder.setNegativeButton("Stay Here", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

    }
}
