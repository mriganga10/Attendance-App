package com.example.android.android_attendance_system;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.android_attendance_system.Login.LoginActivity;
import com.example.android.android_attendance_system.Login.SessionManager;
import com.example.android.android_attendance_system.StudentsOfABatch.Attendence;
import com.example.android.android_attendance_system.StudentsOfABatch.StudentsList;
import com.example.android.android_attendance_system.data.DbExportImport;
import com.example.android.android_attendance_system.data.Dbhelper;
import com.example.android.android_attendance_system.model.Batch;
import com.example.android.android_attendance_system.model.Students;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MyCursorAdapter myAdapter;
    ListView batchListView;
    Dbhelper db;
    Batch batchGlobal;
    SessionManager session;
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        session = new SessionManager(MainActivity.this);
//        Boolean w = session.checkLogin();
//
//        if(!w){
//            finish();
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewBatch.class);
                intent.putExtra("@strings/AddingBatch","1");
                intent.putExtra("BatchName","-1");
                intent.putExtra("BatchBranch","-1");
                intent.putExtra("BatchSub","-1");
                intent.putExtra("BatchSem","-1");
                startActivity(intent);
            }
        });
        db=new Dbhelper(this);
        ArrayList<Batch> batchList = db.getAllBatches();
        Log.i("mlkj", ""+batchList.size());
        batchListView=(ListView) findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        batchListView.setEmptyView(emptyView);

        myAdapter=new MyCursorAdapter(this,batchList);
        batchListView.setAdapter(myAdapter);

        registerForContextMenu(batchListView);

        batchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                batchGlobal=(Batch) batchListView.getItemAtPosition(position);
                Intent i=new Intent(MainActivity.this, StudentsList.class);
                long idd=db.getBatchID(batchGlobal.getBatchName(),batchGlobal.getBatchBranch(),batchGlobal.getSemester(),
                       batchGlobal.getSubject());
                i.putExtra("BatchName",batchGlobal.getBatchName());
                i.putExtra("BatchID",idd);
                startActivity(i);
                //return true;

                /*batchGlobal=(Batch) batchListView.getItemAtPosition(position);
                Intent intent=new Intent(MainActivity.this,AddNewBatch.class);
                intent.putExtra("BatchName",batchGlobal.getBatchName());
                intent.putExtra("BatchBranch",batchGlobal.getBatchBranch());
                intent.putExtra("BatchSub",batchGlobal.getSubject());
                intent.putExtra("BatchSem",batchGlobal.getSemester());

                intent.putExtra("@strings/AddingBatch","2");
                startActivity(intent);*/

            }
        });
        /*RadioGroup group=(RadioGroup) findViewById(R.id.tot_classes_radio);
        final RadioButton r=(RadioButton) findViewById(R.id.radio_tot_classes);
        final long iDD=db.getBatchID(batchGlobal.getBatchName(),batchGlobal.getBatchBranch(),batchGlobal.getSemester(),
                batchGlobal.getSubject());
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // TODO Auto-generated method stub
                if(r.isChecked()) {
                    int k=db.updateBatchTotClasses(iDD,1);
                    Log.i("totclassesupdate",""+k);
                }
            }
        });*/
    }


    /**
     * MENU on action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_options, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_sign_out:
                session.logoutUser();
                signOut();
                db.removeAll();
                finish();
                //Log.i("asdfgh", "");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Menu on long clicking batch item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.list) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
        batchGlobal=(Batch) batchListView.getItemAtPosition(((AdapterView.AdapterContextMenuInfo)menuInfo).position);
        menu.setHeaderTitle("Batch: "+batchGlobal.getBatchName()+"\nBranch: "+batchGlobal.getBatchBranch());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.update_batch:
                /*Intent i=new Intent(MainActivity.this, StudentsList.class);
                long idd=db.getBatchID(batchGlobal.getBatchName(),batchGlobal.getBatchBranch(),batchGlobal.getSemester(),
                        batchGlobal.getSubject());
                i.putExtra("BatchName",batchGlobal.getBatchName());
                i.putExtra("BatchID",idd);
                startActivity(i);*/

                Intent intent=new Intent(MainActivity.this,AddNewBatch.class);
                intent.putExtra("BatchName",batchGlobal.getBatchName());
                intent.putExtra("BatchBranch",batchGlobal.getBatchBranch());
                intent.putExtra("BatchSub",batchGlobal.getSubject());
                intent.putExtra("BatchSem",batchGlobal.getSemester());

                intent.putExtra("@strings/AddingBatch","2");
                startActivity(intent);
                return true;
            case R.id.TakeAttendance:
                // edit stuff here
                //Dbhelper d=new Dbhelper(this);
                long iddd=db.getBatchID(batchGlobal.getBatchName(),batchGlobal.getBatchBranch(),batchGlobal.getSemester(),
                        batchGlobal.getSubject());
                Intent in=new Intent(MainActivity.this, Attendence.class);
                Log.i("zzzzzz",""+iddd);
                in.putExtra("BatchID",iddd);
                in.putExtra("BatchName",batchGlobal.getBatchName());
                in.putExtra("BatchBranch",batchGlobal.getBatchBranch());
                in.putExtra("BatchSub",batchGlobal.getSubject());
                in.putExtra("BatchSem",batchGlobal.getSemester());
                startActivity(in);

                //showAttendConfirmationDialog();
                return true;
            case R.id.Getpdf:
                long idd=db.getBatchID(batchGlobal.getBatchName(),batchGlobal.getBatchBranch(),batchGlobal.getSemester(),
                        batchGlobal.getSubject());
                getbatchpdf(idd);

                return true;
            case R.id.delete:
                long idddd=db.getBatchID(batchGlobal.getBatchName(),batchGlobal.getBatchBranch(),batchGlobal.getSemester(),
                        batchGlobal.getSubject());
                showDeleteConfirmationDialog(idddd);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//        mGoogleApiClient.connect();
        super.onStart();
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
//                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
//                        startActivity(i);
                    }
                });
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
                db.deleteBatch(i,true);
                finish();
                startActivity(getIntent());
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

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("mnbvc","Permission is granted");
                return true;
            } else {

                Log.v("mnbvc","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("mnbvc","Permission is granted");
            return true;
        }
    }

    public void getbatchpdf(long batchId){
        DbExportImport dv = new DbExportImport();
        isStoragePermissionGranted();
        dv.exportDb();
        //dv.restoreDb();
        //dv.importIntoDb(MainActivity.this);
        ArrayList<Students> st=db.getAllStudentsByBatch(batchId);
        Document doc=new Document();
        File root = new File(Environment.getExternalStorageDirectory(), batchGlobal.getBatchName()+"("+batchGlobal.getBatchBranch()+")");
        if (!root.exists()) {
            root.mkdirs();   // create root directory in sdcard
        }
        File pdffile = new File(root,batchGlobal.getSubject()+".pdf");
        //String outPath= Environment.getExternalStorageDirectory()+"/"+e.getText().toString()+".pdf";
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(pdffile));
            doc.open();
            doc.add(new Paragraph("Batch: "+batchGlobal.getBatchName()+"\n"));
            doc.add(new Paragraph("Branch: "+batchGlobal.getBatchBranch()+"("+batchGlobal.getSemester()+")"+"\n"));
            doc.add(new Paragraph("Sub: "+batchGlobal.getSubject()+"\n\n\n"));

            PdfPTable table = new PdfPTable(7);

            table.setWidthPercentage(100);
            //table.setLockedWidth(true);
            table.addCell("SCHOLAR ID");
            table.addCell("NAME");
            table.addCell("ATTENDANCE %");
            table.addCell("MIDSEM");
            table.addCell("ENDSEM");
            table.addCell("Contact No.");
            table.addCell("Email ID");


            for(int j=0;j<st.size();j++) {
                table.addCell(st.get(j).getID());
                table.addCell(st.get(j).getName());
                table.addCell(st.get(j).getAttendancePercent());
                table.addCell(st.get(j).getMidSem());
                table.addCell(st.get(j).getEndSem());
                table.addCell(st.get(j).getContactNo());
                table.addCell(st.get(j).getEmail_id());
            }

            doc.add(table);
            doc.addCreationDate();
            Toast.makeText(this,"Pdf of "+batchGlobal.getBatchName()+"("+batchGlobal.getBatchBranch()+")"+" is created in external storage"
                    ,Toast.LENGTH_SHORT).show();
            doc.close();
        } catch (DocumentException e1) {

            Toast.makeText(this,"Unable to create PDF",Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            Toast.makeText(this,"Unable to create PDF",Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
        }
    }
}
