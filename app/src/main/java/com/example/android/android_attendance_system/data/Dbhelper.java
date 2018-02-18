package com.example.android.android_attendance_system.data;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;


import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.android_attendance_system.R;
import com.example.android.android_attendance_system.model.Batch;
import com.example.android.android_attendance_system.model.Students;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.EditText;

import static android.R.attr.id;
import static android.R.attr.tag;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.provider.Contacts.SettingsColumns.KEY;
import static android.support.v7.widget.AppCompatDrawableManager.get;

public class Dbhelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "Dbhelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Table Names
    private static final String TABLE_BATCHES = "batches";
    private static final String TABLE_STUDENTS = "students";
    //private static final String TABLE_TODO_TAG = "todo_tags";

    // Common column names
    public static final String KEY_ID = "id";
    //private static final String KEY_CREATED_AT = "created_at";

    // BATCH Table - column nmaes
    public static final String KEY_BATCH_NAME = "batchName";
    public static final String KEY_BATCH_SEMESTER = "batchSem";
    public static final String KEY_BATCH_BRANCH="batchBranch";
    public static final String KEY_BATCH_SUBJECT="batchSubject";
    public static final String KEY_BATCH_BASE_SCHOLAR_ID="baseScholarID";
    public static final String KEY_TOT_CLASSES="totClasses";

    public static final String ALL_BATCH_COLUMNS[] = {KEY_BATCH_NAME,KEY_BATCH_SEMESTER,KEY_BATCH_BRANCH,KEY_BATCH_SUBJECT};

    // students Table - column names
    public static final String KEY_STUDENT_SCHOLARID = "studentID";
    public static final String KEY_STUDENT_NAME="studentName";
    public static final String KEY_STUDENT_EMAIL="emailID";
    public static final String KEY_STUDENT_CLASSES_ATTENDED="classesAttend";
    public static final String KEY_STUDENT_ATTEND_PERCENT="percentAttend";
    public static final String KEY_STUDENT_MIDSEM="midsem";
    public static final String KEY_STUDENT_ENDSEM="endSem";
    public static final String KEY_STUDENT_CONTACT="contactNo";
    public static final String KEY_CLASSES_PLUS="classesPlus";
    public static final String KEY_STUDENT_FK="foreignKey";

    public static final String ALL_STUDENTS_COLUMNS[] = {KEY_STUDENT_FK,KEY_STUDENT_SCHOLARID,KEY_STUDENT_NAME,KEY_STUDENT_EMAIL,KEY_STUDENT_CONTACT};

    // Table Create Statements
    // BATCHES table create statement
    private static final String CREATE_TABLE_BATCHES = "CREATE TABLE "
            + TABLE_BATCHES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_BATCH_NAME
            + " TEXT," + KEY_BATCH_SEMESTER + " TEXT," + KEY_BATCH_BRANCH
            + " TEXT,"+ KEY_TOT_CLASSES+" INTEGER DEFAULT 0,"+ KEY_BATCH_SUBJECT +" TEXT"+")";
    //,"+  KEY_BATCH_BASE_SCHOLAR_ID +" TEXT"
    //CREATE TABLE TABLE_BATCHE (KEY_ID INTEGER, KEY_BATCH_NAME TEXT, KEY_BATCH_SEMESTER TEXT, KEY_BATCH_BRANCH TEXT, KEY_BATCH_SUBJECT TEXT, KEY_BATCH_BASE_SCHOLAR_ID TEXT)


    // STUDENT table create statement
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_STUDENT_SCHOLARID + " TEXT,"
            + KEY_STUDENT_NAME + " TEXT," + KEY_STUDENT_EMAIL +" TEXT,"+ KEY_STUDENT_CLASSES_ATTENDED +" INTEGER DEFAULT 0,"
            + KEY_CLASSES_PLUS +" INTEGER DEFAULT 0,"
            + KEY_STUDENT_ATTEND_PERCENT +" REAL DEFAULT 0,"
            +  KEY_STUDENT_MIDSEM +" TEXT,"+ KEY_STUDENT_ENDSEM +" TEXT,"+ KEY_STUDENT_CONTACT +" TEXT,"
            + KEY_STUDENT_FK +" INTEGER NOT NULL"+")";



    public Dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_BATCHES);
        db.execSQL(CREATE_TABLE_STUDENTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BATCHES);

        // create new tables
        onCreate(db);
    }

    // ------------------------ "STUDENT" table methods ----------------//

    /*
     * Creating a STUDENT
     */
    public long createStudent(Students student, long fk) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("mnbv","poiu");

        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_SCHOLARID, student.getID());
        values.put(KEY_STUDENT_NAME, student.getName());
        values.put(KEY_STUDENT_EMAIL, student.getEmail_id());
        //values.put(KEY_CLASSES_PLUS, 0);
        //values.put(KEY_STUDENT_ATTEND_PERCENT, student.getAttendancePercent());
        values.put(KEY_STUDENT_MIDSEM, student.getMidSem());
        values.put(KEY_STUDENT_ENDSEM, student.getEndSem());
        values.put(KEY_STUDENT_CONTACT, student.getContactNo());
        values.put(KEY_STUDENT_FK, fk);

        // insert row
        long student_pri_id = db.insert(TABLE_STUDENTS, null, values);
        Log.i("sddddd",""+student_pri_id);
        student.setPrimaryID(student_pri_id);


        return student_pri_id;
    }

//    public long createStudentFromBackup(Students student, long fk){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Log.i("mnbv","poiu");
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_STUDENT_CLASSES_ATTENDED, student.getClasses());
//        values.put(KEY_STUDENT_SCHOLARID, student.getID());
//        values.put(KEY_STUDENT_NAME, student.getName());
//        values.put(KEY_STUDENT_EMAIL, student.getEmail_id());
//        values.put(KEY_CLASSES_PLUS, 0);
//        values.put(KEY_STUDENT_ATTEND_PERCENT, student.getAttendancePercent());
//        values.put(KEY_STUDENT_MIDSEM, student.getMidSem());
//        values.put(KEY_STUDENT_ENDSEM, student.getEndSem());
//        values.put(KEY_STUDENT_CONTACT, student.getContactNo());
//        values.put(KEY_STUDENT_FK, fk);
//
//        // insert row
//        long student_pri_id = db.insert(TABLE_STUDENTS, null, values);
//        Log.i("sddddd",""+student_pri_id);
//        student.setPrimaryID(student_pri_id);
//
//
//        return student_pri_id;
//    }

    /*
     * get single student
     */
    public Students getStudent(String student_id,String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS + " WHERE "
                + KEY_STUDENT_SCHOLARID + " = '" + student_id+"'"+" AND "+KEY_STUDENT_NAME+" = '"+name+"'";

        //Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        Log.i("mmmmm",""+Integer.toString(c.getInt(c.getColumnIndex(KEY_STUDENT_CLASSES_ATTENDED))));

        Students s = new Students(c.getString(c.getColumnIndex(KEY_STUDENT_SCHOLARID)),c.getString(c.getColumnIndex(KEY_STUDENT_NAME)),
                c.getString(c.getColumnIndex(KEY_STUDENT_EMAIL)),c.getString(c.getColumnIndex(KEY_STUDENT_CONTACT)),
                Integer.toString(c.getInt(c.getColumnIndex(KEY_STUDENT_CLASSES_ATTENDED))),
                                Integer.toString(c.getInt(c.getColumnIndex(KEY_STUDENT_ATTEND_PERCENT))),c.getString(c.getColumnIndex(KEY_STUDENT_MIDSEM)),
                                        c.getString(c.getColumnIndex(KEY_STUDENT_ENDSEM)));
        s.setPrimaryID(c.getLong(c.getColumnIndex(KEY_ID)));
        s.setClassesPlus(c.getInt(c.getColumnIndex(KEY_CLASSES_PLUS)));
        Log.i("lllllll",""+c.getInt(c.getColumnIndex(KEY_CLASSES_PLUS))+" "+c.getLong(c.getColumnIndex(KEY_ID)));
        return s;
    }

//    public int getStudentClassesPlus(String student_id,String name) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS + " WHERE "
//                + KEY_STUDENT_SCHOLARID + " = '" + student_id+"'"+" AND "+KEY_STUDENT_NAME+" = '"+name+"'";
//
//        //Log.e(LOG, selectQuery);
//
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c != null)
//            c.moveToFirst();
//        Log.i("classesPlus",""+Integer.toString(c.getInt(c.getColumnIndex(KEY_CLASSES_PLUS)))+" "+student_id);
//        return c.getInt(c.getColumnIndex(KEY_CLASSES_PLUS));
//    }
/*

    /**
     * getting all students under single batch
     * */
    public ArrayList<Students> getAllStudentsByBatch(long fk) {
        ArrayList<Students> student_list = new ArrayList<Students>();

        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS
                +" WHERE "
                + KEY_STUDENT_FK+" = "+fk;

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Students td = new Students(c.getString(c.getColumnIndex(KEY_STUDENT_SCHOLARID)),c.getString(c.getColumnIndex(KEY_STUDENT_NAME)),
                        c.getString(c.getColumnIndex(KEY_STUDENT_EMAIL)),c.getString(c.getColumnIndex(KEY_STUDENT_CONTACT)),
                        c.getString(c.getColumnIndex(KEY_STUDENT_CLASSES_ATTENDED)),
                        c.getString(c.getColumnIndex(KEY_STUDENT_ATTEND_PERCENT)),c.getString(c.getColumnIndex(KEY_STUDENT_MIDSEM)),
                        c.getString(c.getColumnIndex(KEY_STUDENT_ENDSEM)));

                // adding to student list
                td.setPrimaryID(c.getLong(c.getColumnIndex(KEY_ID)));
                student_list.add(td);
            } while (c.moveToNext());
        }

        Collections.sort(student_list,new StudentIdComparator());
        Iterator itr=student_list.iterator();
        while(itr.hasNext()){
            Students st=(Students)itr.next();
            Log.i("comp",st.getID());
        }

        return student_list;
    }

    class StudentIdComparator implements Comparator {
        public int compare(Object o1,Object o2){
            Students s1=(Students)o1;
            Students s2=(Students)o2;

            return s1.rollNo.compareTo(s2.rollNo);
        }
    }

    /*
     * getting students count
     */
//    public int getStudentCount(int fk) {
//        String countQuery = "SELECT  * FROM " + TABLE_STUDENTS+" WHERE "+KEY_STUDENT_FK+"="+fk;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//
//        int count = cursor.getCount();
//        cursor.close();
//
//        // return count
//        return count;
//    }

    /*
     * Updating a student
    */
    public int updateStudent(Students student,int i) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(i==1) {
            values.put(KEY_STUDENT_SCHOLARID, student.getID());
            values.put(KEY_STUDENT_NAME, student.getName());
            values.put(KEY_STUDENT_EMAIL, student.getEmail_id());
            //values.put(KEY_STUDENT_CLASSES_ATTENDED, student.getClasses());
            //values.put(KEY_STUDENT_ATTEND_PERCENT, student.getAttendancePercent());
            values.put(KEY_STUDENT_MIDSEM, student.getMidSem());
            values.put(KEY_STUDENT_ENDSEM, student.getEndSem());
            values.put(KEY_STUDENT_CONTACT, student.getContactNo());
            //foreign key fk remains same
            // values.put(KEY_STUDENT_FK, fk);
        }
        else if(i==2){
            values.put(KEY_STUDENT_CLASSES_ATTENDED, student.getClasses());
        }
        else if(i==3){
            values.put(KEY_CLASSES_PLUS, 1);
        }
        else if(i==4){
            values.put(KEY_CLASSES_PLUS, 0);
        }

        // updating row
        int e=db.update(TABLE_STUDENTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(student.getPrimaryID()) });
        Log.i("Updated rows",""+" "+student.getPrimaryID());
        return e;
    }
    public int updateStuAttendPercent(Float i,long id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_ATTEND_PERCENT, i);
        int e=db.update(TABLE_STUDENTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        Log.i("Updated rows",""+" "+id);
        return e;
    }

    /*
     * Deleting a student
     */
    public void deleteStudent(long student_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, KEY_ID + " = ?",
                new String[] { String.valueOf(student_id) });
    }


    /*
     * Creating batches
     */
    public long createBatches(Batch batch) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Log.i("batcg",""+batch.getBaseScholarID());

        values.put(KEY_BATCH_NAME, batch.getBatchName());
        values.put(KEY_BATCH_SEMESTER, batch.getSemester());
        values.put(KEY_BATCH_BRANCH, batch.getBatchBranch());
        values.put(KEY_BATCH_SUBJECT, batch.getSubject());
        //values.put("base", batch.getBaseScholarID());
        // insert row
        long batch_id = db.insert(TABLE_BATCHES, null, values);
        Log.v(Dbhelper.class.getSimpleName(),"batch_id "+ batch_id);
        batch.setBatch_pri_ID(batch_id);
        return batch_id;
    }

//    public long createBatchesFromBackup(Batch batch) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//
//
//        values.put(KEY_BATCH_NAME, batch.getBatchName());
//        values.put(KEY_BATCH_SEMESTER, batch.getSemester());
//        values.put(KEY_BATCH_BRANCH, batch.getBatchBranch());
//        values.put(KEY_BATCH_SUBJECT, batch.getSubject());
//        values.put(KEY_STUDENT_CLASSES_ATTENDED, batch.getTotClasses());
//        values.put(KEY_ID,batch.getBatch_pri_ID());
//        //values.put("base", batch.getBaseScholarID());
//        // insert row
//        long batch_id = db.insert(TABLE_BATCHES, null, values);
//        Log.i("mlkpoi",""+batch_id+" "+batch.getBatch_pri_ID()+" "+batch.getBatchName());
//        batch.setBatch_pri_ID(batch_id);
//        return batch_id;
//    }


    public long getBatchID(String batchName,String branch,String semester,String subject){
        String selectQuery = "SELECT * FROM " + TABLE_BATCHES+" WHERE "+KEY_BATCH_BRANCH+" = '"+branch+"'"
                +" AND "+KEY_BATCH_NAME+" = '"+batchName+"'"+" AND "+KEY_BATCH_SUBJECT+" = '"+subject+"'"
                +" AND "+KEY_BATCH_SEMESTER+" = '"+semester+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        int index=c.getColumnIndex(KEY_ID);
        long i=c.getLong(index);
        Log.i("Index",""+i);
        return  i;
    }


    /**
     * getting all batches
     * */
    public ArrayList<Batch> getAllBatches() {
        ArrayList<Batch> batches = new ArrayList<Batch>();
        String selectQuery = "SELECT  * FROM " + TABLE_BATCHES;

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Batch t = new Batch(c.getString(c.getColumnIndex(KEY_BATCH_NAME)),
                        c.getString(c.getColumnIndex(KEY_BATCH_BRANCH)),
                        c.getString(c.getColumnIndex(KEY_BATCH_SEMESTER)),
                        c.getString(c.getColumnIndex(KEY_BATCH_SUBJECT)));
                // adding to batch list
                batches.add(t);
            } while (c.moveToNext());
        }
        return batches;
    }

    /*
     * Updating a batch
     */
    public int updateBatch(Batch batch,long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BATCH_NAME, batch.getBatchName());
        values.put(KEY_BATCH_SEMESTER, batch.getSemester());
        values.put(KEY_BATCH_BRANCH, batch.getBatchBranch());
        values.put(KEY_BATCH_SUBJECT, batch.getSubject());
        //values.put(KEY_BATCH_BASE_SCHOLAR_ID, batch.getBaseScholarID());

        // updating row
        return db.update(TABLE_BATCHES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
   public int updateBatchTotClasses(long id,int plusOrMinus){
        //SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_BATCHES +" WHERE "+KEY_ID+" = "+id;

        //Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        int i=0;
       Log.i("zz","ijkhg"+id);
       if(c.moveToFirst()) {
           Log.i("zzz", "mn");
           i = c.getInt(c.getColumnIndex(KEY_TOT_CLASSES));
           Log.i("pppp", "" + i);
       }
        ContentValues values = new ContentValues();
        values.put(KEY_TOT_CLASSES, i+(plusOrMinus));
       //values.put(KEY_BATCH_BASE_SCHOLAR_ID, batch.getBaseScholarID());

       // updating row
       SQLiteDatabase d=this.getWritableDatabase();
       //c=d.rawQuery(selectQuery,null);
       d.update(TABLE_BATCHES, values, KEY_ID + " = ?",
               new String[] { String.valueOf(id) });
       return i+1;

    }
    /*
     * Deleting a batch
     */
    public void deleteBatch(long id, boolean should_delete_all_batch_students) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting batch
        // check if students under this batch should also be deleted
        if (should_delete_all_batch_students) {
            // get all students under this tag
            ArrayList<Students> allBatchStudents = getAllStudentsByBatch(id);

            // delete all students
            for (Students s : allBatchStudents) {
                // delete student
                deleteStudent(s.getPrimaryID());
            }
        }

        // now delete the batch
        db.delete(TABLE_BATCHES, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(TABLE_BATCHES, null, null);
        db.delete(TABLE_STUDENTS, null, null);
    }
}