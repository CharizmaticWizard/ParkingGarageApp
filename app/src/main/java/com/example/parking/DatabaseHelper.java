package com.example.parking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.os.Build;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper{
    public int userid;
    private static String DB_PATH="";
    private static String DB_NAME="Parking.db";
    private SQLiteDatabase mDataBase;
    private Context mContext=null;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        if(Build.VERSION.SDK_INT >= 17)
            DB_PATH=context.getApplicationInfo().dataDir+"/databases/";
        else
            DB_PATH= "/data/data/"+context.getPackageName()+"/databases/";
        mContext=context;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    private boolean checkDatabase() {
        SQLiteDatabase tempDB = null;
        try{
            String path= DB_PATH+DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }
        catch(Exception ignored)
        {

        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null;
    }

    public void copyDatabase() {
        try{
            InputStream myInput = mContext.getAssets().open(DB_NAME);
            String outputFilename = DB_PATH+DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFilename);

            byte[] buffer= new byte[1024];
            int length;
            while((length=myInput.read(buffer))>0){
                myOutput.write(buffer,0,length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDatabase(){
        String path = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void createDatabase() {
        boolean isDBexist= checkDatabase();
        if (!isDBexist) {
            this.getReadableDatabase();
            try {
                copyDatabase();
            }
            catch(Exception ignored){

            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    long addUser(String user, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",user);
        contentValues.put("Email",email);
        contentValues.put("Password",password);
        long res = db.insert("Users",null,contentValues);
        db.close();
        return  res;
    }

    int checkUser(String username, String password){
        String[] columns = { "USERID" };
        SQLiteDatabase db = getReadableDatabase();
        String selection = "Name =? and Password =?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query("Users",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0) {
            String[] columns1 = { "USERID" };
            SQLiteDatabase db1 = getReadableDatabase();
            String selection1 = "Name =? and Password =? and Admin =?";
            String[] selectionArgs1 = { username, password, "1"};
            Cursor cursor1 = db1.query("Users",columns1,selection1,selectionArgs1,null,null,null);
            int count1 = cursor1.getCount();
            cursor1.close();
            db.close();
            if(count1>0)
                return 2;
            return 1;
        }
        else
            return 3;
    }

    String checkUserID(String user, String pass) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT USERID FROM users WHERE Name = ? and Password= ?";
        String[] selectionArgs = {user,pass};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        cursor.moveToFirst();
        String id = null;
        while ( !cursor.isAfterLast()) {
            id= cursor.getString(cursor.getColumnIndex("USERID"));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        userid= Integer.valueOf(id);
        return id;
    }

    boolean checkSlot(int i) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT AVAILABILITY FROM Garage WHERE SLOTNAME = ?";
        String[] selectionArgs = {String.valueOf(i)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        cursor.moveToFirst();
        String avail = null;
        while ( !cursor.isAfterLast()) {
            avail= cursor.getString(cursor.getColumnIndex("AVAILABILITY"));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return avail.equals("1");
    }

    long makeRes(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SLOTNAME",i);
        contentValues.put("USERID",userid);
        contentValues.put("STATUS","Booked");
        long res = db.insert("Reservations",null,contentValues);
        String query="UPDATE Garage SET AVAILABILITY = 0 WHERE SLOTNAME = "+i;
        db.execSQL(query);
        db.close();
        return res;
    }

    String dispRes() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM Reservations";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String res="";
        String slot="";
        String status="";
        while ( !cursor.isAfterLast()) {
            res= cursor.getString(cursor.getColumnIndex("RESID"));
            slot= cursor.getString(cursor.getColumnIndex("SLOTNAME"));
            status= cursor.getString(cursor.getColumnIndex("STATUS"));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return "Reservation ID: " + res +"\n"+ "Slot Name: " + slot+ "\n" + "Status: " + status;
    }

    void cancelRes() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM Reservations";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String res="";
        String slot="";
        while ( !cursor.isAfterLast()) {
            res= cursor.getString(cursor.getColumnIndex("RESID"));
            slot= cursor.getString(cursor.getColumnIndex("SLOTNAME"));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        SQLiteDatabase db1 = this.getWritableDatabase();
        String C= "\"Cancel\"";
        String query1= String.format("UPDATE Reservations SET STATUS = %s WHERE RESID = %s", C, res);
        String query2= String.format("UPDATE Garage SET AVAILABILITY = 1 WHERE SLOTNAME = %s", slot);
        db1.execSQL(query1);
        db1.execSQL(query2);
        db1.close();
    }

    void genInv() {

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM Reservations";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String res="";
        while ( !cursor.isAfterLast()) {
            res= cursor.getString(cursor.getColumnIndex("RESID"));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        int start = (int) Calendar.getInstance().getTimeInMillis()/3600000;

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("RESID", res);
        contentValues.put("CHECKIN", start);
        db1.insert("Invoice",null,contentValues);
        db1.close();
    }

    void updInv() {
        SQLiteDatabase db = getReadableDatabase();
        String query0 = "SELECT * FROM Invoice";
        Cursor cursor = db.rawQuery(query0, null);
        cursor.moveToFirst();
        String res="";
        String checkin="";
        while ( !cursor.isAfterLast()) {
            res= cursor.getString(cursor.getColumnIndex("RESID"));
            checkin= cursor.getString(cursor.getColumnIndex("CHECKIN"));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        SQLiteDatabase db2 = getReadableDatabase();
        String query = "SELECT * FROM Reservations";
        Cursor cursor2 = db2.rawQuery(query, null);
        cursor2.moveToFirst();
        String slot="";
        while ( !cursor2.isAfterLast()) {
            slot= cursor2.getString(cursor2.getColumnIndex("SLOTNAME"));
            cursor2.moveToNext();
        }
        cursor2.close();
        String query3= String.format("UPDATE Garage SET AVAILABILITY = 1 WHERE SLOTNAME = %s", slot);
        db2.execSQL(query3);
        db2.close();

        int end = (int) Calendar.getInstance().getTimeInMillis()/3600000;
        int total = (int) (end-Integer.parseInt(checkin))*60;
        if(total<60)
            total=60;

        SQLiteDatabase db1 = this.getWritableDatabase();
        String query1= String.format("UPDATE Invoice SET CHECKOUT = %d WHERE RESID = %s", end, res);
        String query2= String.format("UPDATE Invoice SET Price = %d WHERE RESID = %s", total, res);
        db1.execSQL(query1);
        db1.execSQL(query2);
        db1.close();
        return;
    }

    String calcInv() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM Invoice";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String inv= "";
        String res = "";
        String price = "";
        while ( !cursor.isAfterLast()) {
            inv= cursor.getString(cursor.getColumnIndex("INVID"));
            res= cursor.getString(cursor.getColumnIndex("RESID"));
            price= cursor.getString(cursor.getColumnIndex("Price"));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        String s= "Invoice ID: " + inv+ "\nReservation ID: " + res +"\n"+ "Price per Hour: 60 rupees\n"  + "Total Charges: " +price+ "\nInvoice Email Sent.";
        return s;
    }

    //Admin functions

    void lock(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query="UPDATE Garage SET AVAILABILITY = 0 WHERE SLOTNAME = "+i;
        db.execSQL(query);
        db.close();
    }

    void unlock(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query="UPDATE Garage SET AVAILABILITY = 1 WHERE SLOTNAME = "+i;
        db.execSQL(query);
        db.close();
    }

    String printRes() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM Reservations";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String resid;
        String slot;
        String uid;
        String status;
        String s= "";
        while ( !cursor.isAfterLast()) {
            resid= cursor.getString(cursor.getColumnIndex("RESID"));
            slot= cursor.getString(cursor.getColumnIndex("SLOTNAME"));
            uid= cursor.getString(cursor.getColumnIndex("USERID"));
            status= cursor.getString(cursor.getColumnIndex("STATUS"));
            s= "RES ID: " + resid+ "\nSlot: " + slot +"\n"+ "User ID: " +uid+ "\nStatus: " +status+ "\n\n"+ s;
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return s;
    }

    String printRec() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM Invoice";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String inv;
        String res;
        String price;
        String s= "";
        while ( !cursor.isAfterLast()) {
            inv= cursor.getString(cursor.getColumnIndex("INVID"));
            res= cursor.getString(cursor.getColumnIndex("RESID"));
            price= cursor.getString(cursor.getColumnIndex("Price"));
            s= "Invoice ID: " + inv+ "\nReservation ID: " + res + "\nPrice per Hour: 60 rupees\n"  + "Total Charges: " +price+ "\nInvoice Email Sent.\n\n" +s;
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return s;
    }

    String printUL() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM Users";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String uid;
        String name;
        String email;
        String pass;
        String admin;
        String s= "";
        while ( !cursor.isAfterLast()) {
            uid= cursor.getString(cursor.getColumnIndex("USERID"));
            name= cursor.getString(cursor.getColumnIndex("Name"));
            email= cursor.getString(cursor.getColumnIndex("Email"));
            pass= cursor.getString(cursor.getColumnIndex("Password"));
            admin= cursor.getString(cursor.getColumnIndex("Admin"));
            if (admin=="1")
                admin="Admin";
            else
                admin="";
            s= admin + "\nUSERID: " + uid+ "\nName: " + name +"\n"+ "Email: " +email+ "\nPassword: " +pass+ "\n\n"+ s;
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return s;
    }

    //WIP. Garage 2 not in database, Reservation table needs parking lot column.

    boolean checkSlot2(int i) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT AVAILABILITY FROM Garage2 WHERE SLOTNAME = ?";
        String[] selectionArgs = {String.valueOf(i)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        cursor.moveToFirst();
        String avail = null;
        while ( !cursor.isAfterLast()) {
            avail= cursor.getString(cursor.getColumnIndex("AVAILABILITY"));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return avail.equals("1");
    }

    long makeRes2(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SLOTNAME","2-"+ i);
        contentValues.put("USERID",userid);
        contentValues.put("STATUS","Booked");
        long res = db.insert("Reservations",null,contentValues);
        String query="UPDATE Garage2 SET AVAILABILITY = 0 WHERE SLOTNAME = "+i;
        db.execSQL(query);
        db.close();
        return res;
    }

    void lock2(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query="UPDATE Garage2 SET AVAILABILITY = 0 WHERE SLOTNAME = "+i;
        db.execSQL(query);
        db.close();
    }

    void unlock2(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query="UPDATE Garage2 SET AVAILABILITY = 1 WHERE SLOTNAME = "+i;
        db.execSQL(query);
        db.close();
    }
}