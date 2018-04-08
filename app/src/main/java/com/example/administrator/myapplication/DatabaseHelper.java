package com.example.administrator.myapplication;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Discipulus
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Acounts.db";
    public static String TABLE_NAME_SPENDING = "acounts";
    public static String TABLE_NAME_INCOME = "income";
    public String COL_2 = "type";
    public String COL_3 = "time";
    public String COL_4 = "comment";
    public String COL_5 = "money";

    //Default constructor

    /*
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
     */

    //Simplified constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create the database

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_SPENDING + " (" +
                "id integer primary key autoincrement," +
                "type text," +
                "time text," +
                "comment text," +
                "money float)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_INCOME + " (" +
                "id integer primary key autoincrement," +
                "type text," +
                "time text," +
                "comment text," +
                "money float)");

        sqLiteDatabase.execSQL("CREATE TABLE budget" + "(" +
                "id integer primary key autoincrement," +
                "month text," +
                "money float)");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void updateTime(String table, int num, String time) {
        //update the time of one order
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", time);

        sqLiteDatabase.update(table, contentValues, "id=?", new String[]{String.valueOf(num)});
    }

    public void updateType(String table, int num, String type) {

        //update the type of one order
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", type);

        sqLiteDatabase.update(table, contentValues, "id=?", new String[]{String.valueOf(num)});
    }

    public void updateComment(String table, int num, String comment) {

        //update the comment of one order
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("comment", comment);

        sqLiteDatabase.update(table, contentValues, "id=?", new String[]{String.valueOf(num)});
    }

    public void updateMoney(String table, int num, float money) {

        //update the money of one order
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("money", money);

        sqLiteDatabase.update(table, contentValues, "id=?", new String[]{String.valueOf(num)});
    }

    public void deleteOneOrder(String table, int num) {
        //delete one order from database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(table, "id=?", new String[]{String.valueOf(num)});
    }

    public boolean insertData(String table, String type, String time, String comment, float money) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, type);
        contentValues.put(COL_3, time);
        contentValues.put(COL_4, comment);
        contentValues.put(COL_5, money);

        //Table name, null and the content values are needed as param


        long result = sqLiteDatabase.insert(table, null, contentValues);

        if (result == -1) {
            //Insert has failed
            return false;
        } else {
            //Successful insertion
            return true;
        }
    }

    public void setBudget(String month, float money) {
        Cursor cursor = readData("budget");
        cursor.moveToLast();
        DateFormat formatter = new SimpleDateFormat("YYYY-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String time = formatter.format(calendar.getTime());
        ContentValues contentValues = new ContentValues();
        contentValues.put("month", time);
        contentValues.put("money", money);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        if (cursor.getCount() != 0 && cursor.getString(1).equals(time)){
            Log.e("test",cursor.getString(0));
            sqLiteDatabase.update("budget", contentValues, "id=?", new String[]{cursor.getString(0)});
        }

        else {
            sqLiteDatabase.insert("budget", null, contentValues);
        }
    }

    public Cursor readData(String table) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + table, null);
        return cursor;
    }
}
