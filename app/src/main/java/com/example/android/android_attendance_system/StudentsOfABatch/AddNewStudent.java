package com.example.android.android_attendance_system.StudentsOfABatch;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.android_attendance_system.R;
import com.example.android.android_attendance_system.data.Dbhelper;
import com.example.android.android_attendance_system.model.Batch;
import com.example.android.android_attendance_system.model.Students;

import static android.os.Build.VERSION_CODES.N;
import static com.example.android.android_attendance_system.R.id.branch;

/**
 * Created by LOKESH on 19-08-2017.
 */

public class AddNewStudent extends AppCompatActivity {
    Dbhelper db;
    Intent i;
    EditText studentID;
    EditText studentName;
    EditText studentContact;
    EditText studentEmail;
    EditText studentClassesAttended;
    EditText studentAttendPercent;
    EditText studentMidSem;
    EditText studentEndSem;

    String mstudentID;
    String mstudentName;
    String mstudentContact;
    String mstudentEmail;
    String mstudentClassesAttended;
    String mstudentAttendPercent;
    String mstudentMidSem;
    String mstudentEndSem;
    Students uStudent;

    Boolean update;
    String ustudentID;
    String ustudentName;
    long batchId=-1;
    String batchName="a";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db=new Dbhelper(this.getApplicationContext());
        //setTitle("Add Student");
        Intent intent=getIntent();
        batchId=intent.getExtras().getLong("BatchI");
        batchName=intent.getExtras().getString("BatchNam").toString();
        update=intent.getExtras().getBoolean("Update");
        ustudentID=intent.getExtras().getString("StuID");
        ustudentName=intent.getExtras().getString("StuName");



        studentID=(EditText) findViewById(R.id.edit_scholar_ID);
        studentName=(EditText) findViewById(R.id.edit_name);
        //studentAttendPercent=(EditText) findViewById(R.id.edit_attendence_percent);
        //studentClassesAttended=(EditText) findViewById(R.id.edit_classesAttended);
        studentContact=(EditText) findViewById(R.id.edit_contact);
        studentEmail=(EditText) findViewById(R.id.edit_emailID);
        studentMidSem=(EditText) findViewById(R.id.edit_midSem);
        studentEndSem=(EditText) findViewById(R.id.edit_endSem);

        if(update){
            uStudent=db.getStudent(ustudentID,ustudentName);
            setTitle("Update Student");
            studentID.setText(uStudent.getID());
            studentName.setText(uStudent.getName());
            //studentAttendPercent.setText(uStudent.getAttendancePercent());
            //studentClassesAttended.setText(uStudent.getClasses());
            studentMidSem.setText(uStudent.getMidSem());
            studentContact.setText(uStudent.getContactNo());
            studentEndSem.setText(uStudent.getEndSem());
            studentEmail.setText(uStudent.getEmail_id());
        }
        else{
            setTitle("Add Student");
        }

        i = new Intent(AddNewStudent.this, StudentsList.class);
        i.putExtra("BatchName", batchName);
        i.putExtra("BatchID", batchId);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_addstudent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save_student:
                //s=getStudentFromEditField();

                if(update) {
                    mstudentID = studentID.getText().toString().trim();
                    mstudentName = studentName.getText().toString();
                    mstudentContact = studentContact.getText().toString();
                    mstudentEmail = studentEmail.getText().toString();
                    mstudentClassesAttended = Integer.toString(65);
                    mstudentAttendPercent = Integer.toString(65);
                    mstudentMidSem = studentMidSem.getText().toString();
                    mstudentEndSem = studentEndSem.getText().toString();

                    if (!TextUtils.isEmpty(mstudentID) && !TextUtils.isEmpty(mstudentName) && !TextUtils.isEmpty(mstudentContact) && !TextUtils.isEmpty(mstudentEmail)) {
                        Students s = new Students(mstudentID, mstudentName, mstudentEmail, mstudentContact, mstudentClassesAttended, mstudentAttendPercent,
                                mstudentMidSem, mstudentEndSem);
                        s.setPrimaryID(uStudent.getPrimaryID());
                        Log.i("sddd", s.getContactNo() + s.getAttendancePercent() + s.getClasses() + s.getName() + batchId);
                        //long sid=-1;*/
                        int sid = db.updateStudent(s,1);

                        startActivity(i);
                        Log.i("Saveeeee", "Afterupdate" + sid);
                    } else {
                        notChangedDialogueBox();
                        return true;
                    }
                    return true;
                }

                else {
                    mstudentID = studentID.getText().toString().trim();
                    mstudentName = studentName.getText().toString();
                    mstudentContact = studentContact.getText().toString();
                    mstudentEmail = studentEmail.getText().toString();
                    mstudentClassesAttended = Integer.toString(0);
                    mstudentAttendPercent = Integer.toString(0);
                    mstudentMidSem = studentMidSem.getText().toString();
                    mstudentEndSem = studentEndSem.getText().toString();

                    if (!TextUtils.isEmpty(mstudentID) && !TextUtils.isEmpty(mstudentName) && !TextUtils.isEmpty(mstudentContact) && !TextUtils.isEmpty(mstudentEmail)) {
                        Students s = new Students(mstudentID, mstudentName, mstudentEmail, mstudentContact, mstudentClassesAttended, mstudentAttendPercent,
                                mstudentMidSem, mstudentEndSem);

                        Log.i("sddd", s.getContactNo() + s.getAttendancePercent() + s.getClasses() + s.getName() + batchId);
                        //long sid=-1;
                        long sid = db.createStudent(s, batchId);

                        startActivity(i);
                        Log.i("Saveeeee", "AfterAdd"+sid);
                    } else {
                        notChangedDialogueBox();
                        return true;
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void notChangedDialogueBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Some reqd. fields are empty");
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
