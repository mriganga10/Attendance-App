package com.example.android.android_attendance_system.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.android.android_attendance_system.Login.LoginActivity;
import com.example.android.android_attendance_system.model.Batch;
import com.example.android.android_attendance_system.model.Students;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import static com.example.android.android_attendance_system.data.Dbhelper.KEY_BATCH_BRANCH;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_BATCH_NAME;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_BATCH_SEMESTER;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_BATCH_SUBJECT;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_CLASSES_PLUS;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_ID;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_STUDENT_ATTEND_PERCENT;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_STUDENT_CLASSES_ATTENDED;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_STUDENT_CONTACT;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_STUDENT_EMAIL;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_STUDENT_ENDSEM;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_STUDENT_FK;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_STUDENT_MIDSEM;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_STUDENT_NAME;
import static com.example.android.android_attendance_system.data.Dbhelper.KEY_STUDENT_SCHOLARID;

/**
 * Created by LOKESH on 24-01-2018.
 */

public class DbExportImport {


    public static final String TAG = DbExportImport.class.getName();

    /** Directory that files are to be read from and written to **/
    protected static final File DATABASE_DIRECTORY =
            new File(Environment.getExternalStorageDirectory(),"Android Attendance System-Backup");

    /** File path of Db to be imported **/
    protected static final File IMPORT_FILE =
            new File(DATABASE_DIRECTORY,"database.db");

    public static final String PACKAGE_NAME = "com.example.android.android_attendance_system";
    public static final String DATABASE_NAME = "contactsManager";
    public static final String DATABASE_TABLE = "batches";
    public static final String DATABASE_TABLE2 = "students";

    /** Contains: /data/data/com.example.app/databases/contactsManager.db **/
    private static final File DATA_DIRECTORY_DATABASE =
            new File(Environment.getDataDirectory() +
                    "/data/" + PACKAGE_NAME +
                    "/databases/" + DATABASE_NAME );



    /** Saves the application database to the
     * export directory under MyDb.db **/


    public static  boolean exportDb(){
        if( ! SdIsPresent() ) return false;

        File dbFile = DATA_DIRECTORY_DATABASE;
        String filename = "database.db";

        File exportDir = DATABASE_DIRECTORY;
        File file = new File(exportDir, filename);

        Log.i("mlkjhgff", ""+IMPORT_FILE);
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        try {
            file.createNewFile();
            copyFile(dbFile, file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /** Replaces current database with the IMPORT_FILE if
     * import database is valid and of the correct type **/
    public static boolean restoreDb(){

        if( ! SdIsPresent() ) return false;

        File exportFile = DATA_DIRECTORY_DATABASE;
        File importFile = IMPORT_FILE;

        if( ! checkDbIsValid(importFile, DATABASE_TABLE) ) return false;
        if( ! checkDbIsValid(importFile, DATABASE_TABLE2) ) return false;
        Log.i("mlkjhgff", "File does not exist");
        if (!importFile.exists() || !exportFile.exists()) {

            return false;
        }

        try {
            exportFile.createNewFile();
            copyFile(importFile, exportFile);
            Log.i("mlkjhgf1", "File does not exist");
            return true;
        } catch (IOException e) {
            Log.i("mlkjhgf2", "File does not exist");
            e.printStackTrace();
            return false;
        }
    }

//    /** Imports the file at IMPORT_FILE **/
//    public static boolean importIntoDb(Context ctx){
//        if( ! SdIsPresent() ) return false;
//
//        File importFile = IMPORT_FILE;
//        File currentFile = DATA_DIRECTORY_DATABASE;
//
//
//        if( ! checkDbIsValid(importFile,DATABASE_TABLE) ) return false;
//        if( ! checkDbIsValid(importFile,DATABASE_TABLE2) ) return false;
//
//        try{
//
//
//            SQLiteDatabase sqlDb = SQLiteDatabase.openDatabase
//                    (importFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
//            SQLiteDatabase currentDB = SQLiteDatabase.openDatabase
//                    (currentFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
//            // Cursor for batches
//            Cursor c = sqlDb.query(true, DATABASE_TABLE,
//                    null, null, null, null, null, null, null
//            );
//            // Cursor for students
//            Cursor c2 = sqlDb.query(true, DATABASE_TABLE2,
//                    null, null, null, null, null, null, null
//            );
//
//            //DbAdapter dbAdapter = new DbAdapter(ctx);
//            Dbhelper db = new Dbhelper(ctx);
//
//            //db.getWritableDatabase();
//            //Log.i("mlkpo","a");
//            //dbAdapter.open();
//            db.onUpgrade(currentDB,1,2);
//
//            //Log.i("mlkpo","b");
//
//            // Adds all items in cursor to current database
////            cursor.moveToPosition(-1);
////            while(cursor.moveToNext()){
//////                dbAdapter.createQuote(
//////                        cursor.getString(titleColumn),
//////                        cursor.getString(timestampColumn)
//////                );
////            }
//
//            if (c.moveToFirst()) {
//                do {
//                    Log.i("mlkpo",(c.getInt(c.getColumnIndex(KEY_STUDENT_CLASSES_ATTENDED)))+" ");
//
//                    Batch t = new Batch(c.getString(c.getColumnIndex(KEY_BATCH_NAME)),
//                            c.getString(c.getColumnIndex(KEY_BATCH_BRANCH)),
//                            c.getString(c.getColumnIndex(KEY_BATCH_SEMESTER)),
//                            c.getString(c.getColumnIndex(KEY_BATCH_SUBJECT)));
//                    t.setBatch_pri_ID(c.getLong(c.getColumnIndex(KEY_ID)));
//                    t.setTotClasses(c.getInt(c.getColumnIndex(KEY_STUDENT_CLASSES_ATTENDED)));
//                    db.createBatchesFromBackup(t);
//                } while (c.moveToNext());
//            }
//            if (c2.moveToFirst()) {
//                do {
//
//                    Students s = new Students(c2.getString(c2.getColumnIndex(KEY_STUDENT_SCHOLARID)),c2.getString(c2.getColumnIndex(KEY_STUDENT_NAME)),
//                            c2.getString(c2.getColumnIndex(KEY_STUDENT_EMAIL)),c2.getString(c2.getColumnIndex(KEY_STUDENT_CONTACT)),
//                            Integer.toString(c2.getInt(c2.getColumnIndex(KEY_STUDENT_CLASSES_ATTENDED))),
//                            Integer.toString(c2.getInt(c2.getColumnIndex(KEY_STUDENT_ATTEND_PERCENT))),c2.getString(c2.getColumnIndex(KEY_STUDENT_MIDSEM)),
//                            c2.getString(c2.getColumnIndex(KEY_STUDENT_ENDSEM)));
//                    s.setPrimaryID(c2.getLong(c2.getColumnIndex(KEY_ID)));
//                    s.setClassesPlus(c2.getInt(c2.getColumnIndex(KEY_CLASSES_PLUS)));
//                    Log.i("mlkpo",""+s.getAttendancePercent()+" "+Integer.toString(c2.getInt(c2.getColumnIndex(KEY_STUDENT_ATTEND_PERCENT))));
//
//                    db.createStudentFromBackup(s,c2.getInt(c2.getColumnIndex(KEY_STUDENT_FK)));
//                } while (c2.moveToNext());
//            }
//            sqlDb.close();
//            currentDB.close();
//            c.close();
//            c2.close();
//            db.close();
//        } catch( Exception e ){
//            Log.i("mlkpo",""+e);
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }

    /** Given an SQLite database file, this checks if the file
     * is a valid SQLite database and that it contains all the
     * columns represented by ALL_COLUMN_KEYS **/
    protected static boolean checkDbIsValid( File db , String database){
        //Log.i("mlkjhgff", "File does not exist");
        try{
            SQLiteDatabase sqlDb = SQLiteDatabase.openDatabase
                    (db.getPath(), null, SQLiteDatabase.OPEN_READONLY);

            Cursor cursor = sqlDb.query(true, database,
                    null, null, null, null, null, null, null
            );

            if(database==DATABASE_TABLE){
                for( String s : Dbhelper.ALL_BATCH_COLUMNS){
                    cursor.getColumnIndexOrThrow(s);
                }

            }else{
                for( String s : Dbhelper.ALL_STUDENTS_COLUMNS){
                    cursor.getColumnIndexOrThrow(s);
                }
            }
            // ALL_COLUMN_KEYS should be an array of keys of essential columns.
            // Throws exception if any column is missing


            sqlDb.close();
            cursor.close();
        } catch( IllegalArgumentException e ) {
            Log.d(TAG, "Database valid but not the right type");
            e.printStackTrace();
            return false;
        } catch( SQLiteException e ) {
            Log.d(TAG, "Database file is invalid.");
            e.printStackTrace();
            return false;
        } catch( Exception e){
            Log.d(TAG, "checkDbIsValid encountered an exception");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static void copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

    /** Returns whether an SD card is present and writable **/
    public static boolean SdIsPresent() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
}
