package com.example.android.android_attendance_system.StudentsOfABatch;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.android_attendance_system.AddNewBatch;
import com.example.android.android_attendance_system.MainActivity;
import com.example.android.android_attendance_system.R;
import com.example.android.android_attendance_system.data.Dbhelper;
import com.example.android.android_attendance_system.model.Batch;
import com.example.android.android_attendance_system.model.Students;

import java.io.Serializable;
import java.util.ArrayList;

import static com.example.android.android_attendance_system.R.id.branch;
import static com.example.android.android_attendance_system.R.id.fab;

/**
 * Created by LOKESH on 19-08-2017.
 */

public class StudentsList extends AppCompatActivity {
    Dbhelper db;
    Intent i;
    String batch;
    long batch_id;
    Students s;
    ListView studentsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_list);
        db = new Dbhelper(this.getApplicationContext());
        Intent intent = getIntent();

        i = new Intent(StudentsList.this, AddNewStudent.class);

        try {
            batch = intent.getExtras().getString("BatchName").toString();
            setTitle(batch);
        } catch (Exception e) {
        }
        try {
            batch_id = intent.getExtras().getLong("BatchID");
        } catch (Exception e) {
        }
        Log.i("Savecreate", "lok " + batch_id + batch);
        setTitle(batch);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addStudentss);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("StuID", "");
                i.putExtra("StuName","");

                i.putExtra("Update", false);
                i.putExtra("BatchI", batch_id);
                i.putExtra("BatchNam", batch);
                startActivity(i);
            }
        });
        ArrayList<Students> studentsList = db.getAllStudentsByBatch(batch_id);
        studentsListView = (ListView) findViewById(R.id.listofstudents);
        StudentListAdapter myadapter = new StudentListAdapter(this,R.layout.student_list_item, studentsList);
        studentsListView.setAdapter(myadapter);
        myadapter.setNotifyOnChange(true);
        registerForContextMenu(studentsListView);

        studentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Students s1=(Students) studentsListView.getItemAtPosition(i);
                Intent in=new Intent(StudentsList.this,Student_Details.class);
                in.putExtra("StuID", s1.getID());
                in.putExtra("StuName",s1.getName());
                in.putExtra("BatchI", batch_id);
                in.putExtra("BatchNam", batch);
                startActivity(in);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listofstudents) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_student, menu);
        }
        s=(Students) studentsListView.getItemAtPosition(((AdapterView.AdapterContextMenuInfo)menuInfo).position);
        menu.setHeaderTitle("Scholar Id: "+s.getID()+  "\nName: "+s.getName());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            //case R.id.get_studentdetailspdf:

              //  return true;
            case R.id.update_student:
                i.putExtra("StuID", s.getID());
                i.putExtra("StuName",s.getName());

                i.putExtra("Update", true);
                i.putExtra("BatchI", batch_id);
                i.putExtra("BatchNam", batch);
                startActivity(i);
                return true;
            case R.id.delete_student:
                long id=s.getPrimaryID();
                showDeleteConfirmationDialog(id);

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showDeleteConfirmationDialog(final long i) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirm Deletion");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                //Batch b=new Batch(batch,branch,semester,subject);
                Log.i("IIIII",""+i);
                db.deleteStudent(i);
                Intent it=new Intent(StudentsList.this,StudentsList.class);
                it.putExtra("BatchName",batch);
                it.putExtra("BatchID",batch_id);
                startActivity(it);
                finish();
                //NavUtils.navigateUpFromSameTask(AddNewBatch.this);
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
}
