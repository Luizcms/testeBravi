package com.luiz.saindodotedio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private static final String DATABASE_NAME="database.db";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_ACTIVIT="_activit";

    private static final String KEY_ID="id";
    private static final String KEY_DESCRIPTION="description";
    private static final String KEY_TYPE="type";
    private static final String KEY_TIME="time";
    private static final String KEY_SITUATION="situation";



    public DbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String Query_Table=" CREATE TABLE " +TABLE_ACTIVIT+ "(" +KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +KEY_DESCRIPTION+ " TEXT, " +KEY_TYPE+ " TEXT, " +KEY_TIME+ " TEXT, " +KEY_SITUATION+ " TEXT);";
        db.execSQL(Query_Table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ACTIVIT);
        onCreate(db);

    }
    public long insertActivity(String description, String type, String time, String situation){
        db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPTION,description);
        values.put(KEY_TYPE,type);
        values.put(KEY_TIME,time);
        values.put(KEY_SITUATION,situation);
        return db.insert(TABLE_ACTIVIT,null,values);
    }
    public String getData(){
        db=this.getReadableDatabase();
        String[] columns = new String[]{KEY_ID,KEY_DESCRIPTION,KEY_TYPE,KEY_TIME,KEY_SITUATION};
        Cursor cursor = db.query(TABLE_ACTIVIT,columns,null,null,null,null,null);

        int iId = cursor.getColumnIndex(KEY_ID);
        int iDesc = cursor.getColumnIndex(KEY_DESCRIPTION);
        int iType = cursor.getColumnIndex(KEY_TYPE);
        int iTime = cursor.getColumnIndex(KEY_TIME);
        int iSit = cursor.getColumnIndex(KEY_SITUATION);
        String result = "";
        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            result = result +
                    "Id: "+cursor.getString(iId)+"\n"+
                    "Descrição: "+cursor.getString(iDesc)+"\n"+
                    "Tipo: "+cursor.getString(iType)+"\n"+
                    "Tempo: "+cursor.getString(iTime)+"\n"+
                    "Situação: "+cursor.getString(iSit)+"\n\n";

        }
        db.close();
        return result;
    }
    public void deleteActivity(long l){
        db = this.getWritableDatabase();
        db.delete(TABLE_ACTIVIT,KEY_ID+"="+l,null);
    }
    public void updateActivity(long l,String description, String type, String time, String situation){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_TYPE, type);
        values.put(KEY_TIME, time);
        values.put(KEY_SITUATION, situation);
        db.update(TABLE_ACTIVIT,values,KEY_ID+"="+l,null);
        db.close();
    }
    public String getDescription(long l1){
        db = this.getReadableDatabase();
        String[] columns = new String[]{KEY_ID,KEY_DESCRIPTION,KEY_TYPE,KEY_TIME,KEY_SITUATION};
        Cursor cursor = db.query(TABLE_ACTIVIT,columns,KEY_ID+"="+l1,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
            String desc = cursor.getString(1);
            return desc;
        }
        return null;
    }
    public String getType(long l1){
        db = this.getReadableDatabase();
        String[] columns = new String[]{KEY_ID,KEY_DESCRIPTION,KEY_TYPE,KEY_TIME,KEY_SITUATION};
        Cursor cursor = db.query(TABLE_ACTIVIT,columns,KEY_ID+"="+l1,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
            String desc = cursor.getString(2);
            return desc;
        }
        return null;
    }
    public String getTime(long l1){
        db = this.getReadableDatabase();
        String[] columns = new String[]{KEY_ID,KEY_DESCRIPTION,KEY_TYPE,KEY_TIME,KEY_SITUATION};
        Cursor cursor = db.query(TABLE_ACTIVIT,columns,KEY_ID+"="+l1,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
            String desc = cursor.getString(3);
            return desc;
        }
        return null;
    }
    public String getSituation(long l1){
        db = this.getReadableDatabase();
        String[] columns = new String[]{KEY_ID,KEY_DESCRIPTION,KEY_TYPE,KEY_TIME,KEY_SITUATION};
        Cursor cursor = db.query(TABLE_ACTIVIT,columns,KEY_ID+"="+l1,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
            String desc = cursor.getString(4);
            return desc;
        }
        return null;
    }

}