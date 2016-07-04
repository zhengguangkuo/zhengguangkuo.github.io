package com.mt.android.db;

import com.mt.android.sys.mvc.controller.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * ���ݿ������
 * @author dw
 *
 */
public class DbHelper
	{
		private DatabaseHelper mDbHelper;    //SQLiteOpenHelperʵ������
	    public SQLiteDatabase mDb;    //���ݿ�ʵ������
	    private static DbHelper openHelper = null;//���ݿ����ʵ��
	    
		private static int version = 1;//���ݿ�汾��
		  
		private static String myDBName = "dbtest";
		private static String TableNames[];//����
		private static String FieldNames[][];//�ֶ���
		private static String FieldTypes[][];//�ֶ�����
		private static String NO_CREATE_TABLES = "no tables";
		private static String message = "";
		private static Integer  count = 0;  //�򿪵����ݿ�������
		
		private final Context mCtx;    //������ʵ��
		
		private DbHelper(Context ctx) {
		        this.mCtx = ctx;
		}
		/*�ж����ݿ�ʵ���Ƿ����*/
		public static boolean hasInstance(){
			if(openHelper == null){
				return false;
			}
			return true;
		}
		/*������ݿ�ʵ��*/
		public static DbHelper getInstance(){
			return openHelper;
		}
		/*��ʼ��ʱ������ݿ�ʵ��*/
		public static DbHelper getInstance(Context context,IDbInfo dbInfo){
			if (openHelper == null) {
				openHelper = new DbHelper(context);
				TableNames = dbInfo.getTableNames();
				FieldNames = dbInfo.getFieldNames();
				FieldTypes = dbInfo.getFieldTypes();
				Controller.appdbhelper = openHelper;
			}
			return openHelper;
		}
		
		private static class DatabaseHelper extends SQLiteOpenHelper {    //���ݿ⸨����
		        
			DatabaseHelper(Context context) {
	            super(context, myDBName, null, version);
	        }
	        @Override
		  	public void onCreate(SQLiteDatabase db)
		  	{
		  	    if (TableNames == null)
		  	    {
		  	    	message = NO_CREATE_TABLES;
		  	    	return;
		  	    }
		  	    for (int i = 0; i < TableNames.length; i++)
		  	    {
		  	    	String sql = "CREATE TABLE " + TableNames[i] + " (";
		  	    	for (int j = 0; j < FieldNames[i].length; j++)
		  	    	{
		  	    		sql += FieldNames[i][j] + " " + FieldTypes[i][j] + ",";
		  	    	}
		  	    	sql = sql.substring(0, sql.length() - 1);
		  	    	sql += ")";
		  	    	db.execSQL(sql);
		  	    } 
		  	}
		  	@Override
		  	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2)
		  	{
		  		for (int i = 0; i < TableNames[i].length(); i++)
		  	    {
		  			String sql = "DROP TABLE IF EXISTS " + TableNames[i];
		  			db.execSQL(sql);
		  	    }
		  		onCreate(db);
		  	}
		}
		
		/**������ݿ������Ϣ*/
		public void insertTables(String[] tableNames,String[][] fieldNames,String[][] fieldTypes){
			TableNames = tableNames;
			FieldNames = fieldNames;
			FieldTypes = fieldTypes;
		}
	  
		/**�����ݿ�*/
		public DbHelper open() throws SQLException {
			synchronized (count) {
				count++;
				 if (count == 1) {
		        	 mDbHelper = new DatabaseHelper(mCtx);
		 	        mDb = mDbHelper.getWritableDatabase();
		        }
		        return this;
			}
	       
	    }
	    
		/**�ر����ݿ�*/
	    public void close() {
	    	synchronized (count) {
				count--;
				if (count == 0) {
		    		mDbHelper.close();
		    		mDbHelper = null;
		    	}else if(count < 0){
		    		count = 0;
		    	}
			}
	    	
	    }
	
		public void execSQL(String sql) throws java.sql.SQLException
		{
			mDb.execSQL(sql);
		}
		
		/**sql����ѯ����*/
		public Cursor rawQuery(String sql,String[] selectionArgs)
		{
			Cursor cursor = mDb.rawQuery(sql, selectionArgs);
			return cursor;
		}
	
		/**��ѯ����*/
		public Cursor select(String table, String[] columns, 
				String selection, String[] selectionArgs, String groupBy, 
				String having, String orderBy)
		{
			Cursor cursor = mDb.query
			(
					table, columns, selection, selectionArgs, 
					groupBy, having, orderBy
			);
			return cursor;
		}
	
		/**�������*/
		public long insert(String table, String fields[], String values[])
		{
			ContentValues cv = new ContentValues();
			for (int i = 0; i < fields.length; i++)
			{
				cv.put(fields[i], values[i]);
			}
			return mDb.insert(table, null, cv);
		}
	
		/**ɾ������*/
		public int delete(String table, String where, String[] whereValue)
		{
			return mDb.delete(table, where, whereValue);
		}
	
		/**��������*/
		public int update(String table, String updateFields[],
				String updateValues[], String where, String[] whereValue)
		{
			ContentValues cv = new ContentValues();
			for (int i = 0; i < updateFields.length; i++)
			{
				cv.put(updateFields[i], updateValues[i]);
			}
			return mDb.update(table, cv, where, whereValue);
		}
	
		/**������Ϣ�� ��Ϊnull���ݿ�δ����*/
		public String getMessage()
		{
			return message;
		}
	
	  
}

