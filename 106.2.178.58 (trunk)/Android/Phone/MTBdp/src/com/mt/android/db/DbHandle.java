package com.mt.android.db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.database.Cursor;
import android.util.Log;

import com.mt.android.sys.util.StringFormat;

public class DbHandle {
	private DbHelper db;
	private Object obj = new Object();

	/**
	 * ��ִ�г��˲�ѯ�����������
	 * 
	 * @param sql
	 *            ������sql���
	 * @throws SQLException
	 */
	public void execSQL(String sql) throws SQLException {

		synchronized (obj) {

			try{
				db = DbHelper.getInstance();
				db.open();

				db.execSQL(sql);

			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
			}finally{
				if(db != null){
					db.close();
				}
			}
			
		}
	}

	/**
	 * sql����ѯ����
	 * 
	 * ��ѯ����,����ѯ����ж���ʱ����ʹ�ø÷��� ÿһ������Ϊһ��map�� map�е�key��������value�Ƕ�Ӧ��ֵ
	 * 
	 * 
	 * 
	 * 
	 * @param sql
	 *            ����ռλ���Ĳ�ѯ��� ���� select * from tab1 where name = ? and age = ?
	 * @param selectionArgs
	 *            ռλ����Ӧ�Ĳ��� ���� new String[]{"zhang","21"}
	 * @return
	 */
	public List<Map<String, String>> rawQuery(String sql, String[] selectionArgs) {
		synchronized (obj) {try{

			db = DbHelper.getInstance();
			db.open();
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Cursor cursor = db.rawQuery(sql, selectionArgs);

			while (cursor != null && cursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					if (cursor.getString(i) != null) {
						map.put(cursor.getColumnName(i), cursor.getString(i));
					}
				}
				list.add(map);
			}
			cursor.close();

			return list;
		
		}catch(Exception ex){
			Log.i("db Exception", " db error");
			ex.printStackTrace();
			return null;
		}finally{
			if(db != null){
				db.close();
			}
		}
	   }
	}

	/**
	 * sql����ѯ����
	 * 
	 * ��ѯ����,����ѯ���Ϊһ��ʱ����ʹ�ø÷��� ��ѯ���Ϊmap��map�е�key��������value�Ƕ�Ӧ��ֵ
	 * 
	 * 
	 * 
	 * @param sql
	 *            ����ռλ���Ĳ�ѯ��� ���� select * from tab1 where name = ? and age = ?
	 * @param selectionArgs
	 *            ռλ����Ӧ�Ĳ��� ���� new String[]{"zhang","21"}
	 * @return
	 */
	public Map<String, String> rawQueryOneRecord(String sql,
			String[] selectionArgs) {
		synchronized (obj) {try{

			db = DbHelper.getInstance();
			db.open();
			Map<String, String> map = new HashMap<String, String>();
			Cursor cursor = db.rawQuery(sql, selectionArgs);

			if (cursor != null && cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					if (cursor.getString(i) != null) {
						map.put(cursor.getColumnName(i), cursor.getString(i));
					}
				}
			}
			cursor.close();
			return map;
		
		}catch(Exception ex){
			Log.i("db Exception", " db error");
			ex.printStackTrace();
			return null;
		}finally{
			if(db != null){
				db.close();
			}
		}}
		
	}

	/**
	 * ��ѯ����,����ѯ����ж���ʱ����ʹ�ø÷��� ÿһ������Ϊһ��map�� map�е�key��������value�Ƕ�Ӧ��ֵ
	 * 
	 * 
	 * @param table
	 *            Ҫ��ѯ�ı���
	 * @param columns
	 *            Ҫ��ѯ������
	 * @param selection
	 *            �ɴ�ռλ����ѯ���� ��where ������ ���磺 name = ? and age = ?
	 * @param selectionArgs
	 *            ���ռλ�����ַ��� new String[]{"zhang","21"}
	 * @param groupBy
	 *            group by ��������
	 * @param having
	 *            having ��������
	 * @param orderBy
	 *            order by ��������
	 * @return
	 */
	public List<Map<String, String>> select(String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		synchronized (obj) {try{

			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			db = DbHelper.getInstance();
			db.open();

			Cursor cursor = db.select(table, columns, selection, selectionArgs,
					groupBy, having, orderBy);
			while (cursor != null && cursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					if (cursor.getString(i) != null) {
						map.put(cursor.getColumnName(i), cursor.getString(i));
					}
				}
				list.add(map);
			}
			cursor.close();

			return list;
		
		}catch(Exception ex){
			Log.i("db Exception", " db error");
			ex.printStackTrace();
			return null;
		}finally{
			if(db != null){
				db.close();
			}
		}
		}
		
		
	}

	/**
	 * ����ѯ���ֻ��һ����¼ʱ����ʹ�ø÷����� ����map��map�е�key��������value�Ƕ�Ӧ��ֵ
	 * 
	 * @param table
	 *            Ҫ��ѯ�ı���
	 * @param columns
	 *            Ҫ��ѯ������
	 * @param selection
	 *            �ɴ�ռλ����ѯ���� ��where ������ ���磺 name = ? and age = ?
	 * @param selectionArgs
	 *            ���ռλ�����ַ��� new String[]{"zhang","21"}
	 * @param groupBy
	 *            group by ��������
	 * @param having
	 *            having ��������
	 * @param orderBy
	 *            order by ��������
	 * @return
	 */
	public Map<String, String> selectOneRecord(String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		synchronized (obj) {
			try{

				Map<String, String> map = new HashMap<String, String>();

				db = DbHelper.getInstance();
				db.open();

				Cursor cursor = db.select(table, columns, selection, selectionArgs,
						groupBy, having, orderBy);
				if (cursor != null && cursor.moveToFirst()) {
					for (int i = 0; i < cursor.getColumnCount(); i++) {
						if (cursor.getString(i) != null) {
							map.put(cursor.getColumnName(i), cursor.getString(i));
						}
					}
				}
				cursor.close();

				return map;
			
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return null;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
		
	}

	/*
	 * �������
	 * 
	 * @param table ����
	 * 
	 * @param fields ����
	 * 
	 * @param values ����˳�����Ӧ��ֵ
	 * 
	 * @return ����-1���ʾ����
	 */
	public long insert(String table, String fields[], String values[]) {
		synchronized (obj) {
			try{

				db = DbHelper.getInstance();
				db.open();

				long result = db.insert(table, fields, values);

				return result;
			
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return -1;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
		
	}

	/**
	 * ɾ�����ݣ�����ɾ������
	 * 
	 * @param table
	 *            ����
	 * @param where
	 *            �ɴ�ռλ��������
	 * @param whereValue
	 *            ռλ��������ַ�
	 * @return ɾ��������
	 */
	public int delete(String table, String where, String[] whereValue) {
		synchronized (obj) {
			try{

				db = DbHelper.getInstance();

				db.open();

				int result = db.delete(table, where, whereValue);

				return result;
			
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return 0;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
	}

	/**
	 * ��������
	 * 
	 * @param table
	 *            ����
	 * @param updateFields
	 *            ���µ�����
	 * @param updateValues
	 *            ��Ӧ��ֵ
	 * @param where
	 *            �������� �ɴ�ռλ��
	 * @param whereValue
	 *            ���ռλ�����ַ��� ��Ϊ��
	 * @return
	 */
	public int update(String table, String updateFields[],
			String updateValues[], String where, String[] whereValue) {
		synchronized (obj) {
			try{

				db = DbHelper.getInstance();

				db.open();

				int result = db.update(table, updateFields, updateValues, where,
						whereValue);

				return result;
			
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return 0;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
		
	}

	/**
	 * ��object�������table��
	 * 
	 * @param table
	 *            Ҫ����ı���
	 * @param object
	 *            Ҫ����Ķ���
	 * @return ���������
	 */
	public long insertObject(String table, Object object) {
		synchronized (obj) {
			
			try{

				db = DbHelper.getInstance();
				db.open();
				String sql = "PRAGMA table_info(" + table + ")";
				Cursor cursor = db.rawQuery(sql, null);

				Class objClass = object.getClass();

				Map<String, String> map = new HashMap<String, String>();

				int i = 0;
				while (cursor != null && cursor.moveToNext()) { // �������
					try {

						String mName = getMethodName("get", cursor.getString(1));
						if (cursor.getString(1).equals("MSG_ID")) {
							Method fatherMethod = objClass.getSuperclass()
									.getDeclaredMethod(mName, null);
							if (fatherMethod != null) {

								try {
									map.put(cursor.getString(1), StringFormat
											.isNull(objClass.getMethod(
													getMethodName("get",
															cursor.getString(1)))
													.invoke(object, null)));
								} catch (IllegalArgumentException e) {

								} catch (IllegalAccessException e) {

								} catch (InvocationTargetException e) {

								} catch (NoSuchMethodException e) {

								}
							}
							i++;
							continue;
						}
						Method m = objClass.getDeclaredMethod(mName, null);
						if (objClass.getDeclaredMethod(mName, null) != null) {

							try {
								map.put(cursor.getString(1), StringFormat
										.isNull(objClass.getMethod(
												getMethodName("get",
														cursor.getString(1)))
												.invoke(object, null)));
							} catch (IllegalArgumentException e) {

							} catch (IllegalAccessException e) {

							} catch (InvocationTargetException e) {

							} catch (NoSuchMethodException e) {

							}
						}

					} catch (NoSuchMethodException e) {

					}
					i++;
				}

				String[] fields = new String[map.size()];
				String[] values = new String[map.size()];

				Iterator iter = map.entrySet().iterator();

				int j = 0;
				while (iter.hasNext()) {

					Map.Entry entry = (Map.Entry) iter.next();

					fields[j] = entry.getKey().toString();

					values[j] = entry.getValue().toString();

					j++;
				}
				long result = db.insert(table, fields, values);
				cursor.close();

				return result;
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return 0;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
		
	}

	/**
	 * ������Ӧ���󣬸��±����� ����_1 ��β���ֶ� ������
	 * 
	 * @param table
	 *            ���µı���
	 * @param object
	 *            ������ݵĶ���
	 * @param where
	 *            �������� �ɴ�ռλ��
	 * @param whereValue
	 *            ���ռλ�����ַ��� ��Ϊ��
	 * @return ��Ӱ��ļ�¼����
	 */
	public long updateRespObject(String table, Object object, String where,
			String[] whereValue) {
		synchronized (obj) {
			
			try{

				db = DbHelper.getInstance();
				db.open();
				String sql = "PRAGMA table_info(" + table + ")";
				Cursor cursor = db.rawQuery(sql, null);

				Class objClass = object.getClass();

				Map<String, String> map = new HashMap<String, String>();

				while (cursor != null && cursor.moveToNext()) {

					if (cursor.getString(1).endsWith("_1")) {
						String mName = getMethodName("get", cursor.getString(1));
						try {
							if (!cursor.getString(1).equals("MSG_ID_1")) {

								Method m = objClass.getDeclaredMethod(mName, null);
								if (objClass.getDeclaredMethod(mName, null) != null) {

									map.put(cursor.getString(1), StringFormat
											.isNull(objClass.getMethod(
													getMethodName("get",
															cursor.getString(1)))
													.invoke(object, null)));
								}
							} else {

								Method fatherMethod = objClass.getSuperclass()
										.getDeclaredMethod(mName, null);
								if (fatherMethod != null) {

									map.put(cursor.getString(1), StringFormat
											.isNull(objClass.getMethod(
													getMethodName("get",
															cursor.getString(1)))
													.invoke(object, null)));

								}
							}

						} catch (NoSuchMethodException e) {

						} catch (IllegalArgumentException e) {

						} catch (IllegalAccessException e) {

						} catch (InvocationTargetException e) {

						}
					}

				}

				String[] fields = new String[map.size()];
				String[] values = new String[map.size()];

				Iterator iter = map.entrySet().iterator();

				int j = 0;
				while (iter.hasNext()) {

					Map.Entry entry = (Map.Entry) iter.next();

					fields[j] = entry.getKey().toString();

					values[j] = entry.getValue().toString();

					j++;
				}
				long result = db.update(table, fields, values, where, whereValue);
				cursor.close();

				return result;
			
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return 0;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
		
	}

	/**
	 * ���ݶ��󣬸��±��е�����
	 * 
	 * @param table
	 *            ���µı���
	 * @param object
	 *            ������ݵĶ���
	 * @param where
	 *            �������� �ɴ�ռλ��
	 * @param whereValue
	 *            ���ռλ�����ַ��� ��Ϊ��
	 * @return ��Ӱ��ļ�¼����
	 */
	public long updateObject(String table, Object object, String where,
			String[] whereValue) {
		synchronized (obj) {
			
			try{

				db = DbHelper.getInstance();
				db.open();
				String sql = "PRAGMA table_info(" + table + ")";
				Cursor cursor = db.rawQuery(sql, null);

				Class objClass = object.getClass();

				Map<String, String> map = new HashMap<String, String>();

				while (cursor != null && cursor.moveToNext()) {
					try {
						String mName = getMethodName("get", cursor.getString(1));
						Method m = objClass.getDeclaredMethod(mName, null);
						if (objClass.getDeclaredMethod(mName, null) != null) {

							try {
								map.put(cursor.getString(1), StringFormat
										.isNull(objClass.getMethod(
												getMethodName("get",
														cursor.getString(1)))
												.invoke(object, null)));
							} catch (IllegalArgumentException e) {

							} catch (IllegalAccessException e) {

							} catch (InvocationTargetException e) {

							} catch (NoSuchMethodException e) {

							}
						}
					} catch (NoSuchMethodException e) {

					}

				}

				String[] fields = new String[map.size()];
				String[] values = new String[map.size()];

				Iterator iter = map.entrySet().iterator();

				int j = 0;
				while (iter.hasNext()) {

					Map.Entry entry = (Map.Entry) iter.next();

					fields[j] = entry.getKey().toString();

					values[j] = entry.getValue().toString();

					j++;
				}
				long result = db.update(table, fields, values, where, whereValue);
				cursor.close();

				return result;
			
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return 0;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
		
	}

	/**
	 * �÷���ʵ�ֽ�srcTable���е�����ת�浽dstTable�� ��ɾ��srcTable������ �Ĺ��� , ֧�ֶ���ת��
	 * 
	 * @param srcTable
	 *            Դ��
	 * @param dstTable
	 *            Ŀ���
	 * @return true ��ʾת��ɹ�
	 */
	public boolean transferTable(String srcTable, String dstTable) {
		synchronized (obj) {
			try{

				boolean isSucc = false;

				db = DbHelper.getInstance();
				db.open();
				String srcSql = "select * from " + srcTable;
				Cursor srcCursor = db.rawQuery(srcSql, null);

				String sql = "PRAGMA table_info(" + dstTable + ")";
				Cursor dstCursor = db.rawQuery(sql, null);

				Map<String, String> map = new HashMap<String, String>();
				while (dstCursor != null && dstCursor.moveToNext()) {
					map.put(dstCursor.getString(1), "");

				}
				int ss = srcCursor.getColumnCount();
				if (srcCursor != null && srcCursor.moveToFirst()) {
					for (int i = 0; i < srcCursor.getColumnCount(); i++) {
						if (map.get(srcCursor.getColumnName(i)) != null) {
							map.put(srcCursor.getColumnName(i),
									StringFormat.isNull(srcCursor.getString(i)));
						}
					}

				}
				map.remove("_ID");
				String dstFields[] = new String[dstCursor.getCount() - 1];
				String dstValues[] = new String[dstCursor.getCount() - 1];
				Iterator iter = map.entrySet().iterator();

				int j = 0;
				while (iter.hasNext()) {

					Map.Entry entry = (Map.Entry) iter.next();

					dstFields[j] = entry.getKey().toString();

					dstValues[j] = entry.getValue().toString();

					j++;
				}

				long result = db.insert(dstTable, dstFields, dstValues);

				if (result >= 1) {
					isSucc = true;
					db.delete(srcTable, null, null);
				}
				srcCursor.close();
				dstCursor.close();

				return isSucc;
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return false;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
	}

	/**
	 * ��װget,set������
	 * 
	 * @param type
	 *            Ҫ��װset��get����
	 * @param name
	 *            ������
	 * @return
	 */
	private String getMethodName(String type, String name) {
		String methodName = "";
		if (name != null && name.length() > 1) {
			if (name.equalsIgnoreCase("service_PIN_Capture_Code")) {
				return "getServicePINCaptureCode";
			}
			if (name.equals("_ID")) {
				return "get_ID";
			}
			if (name.endsWith("_1")) {
				name = name.substring(0, name.length() - 2);
			}
			String[] arr = name.split("_");
			if (type != null && type.equals("get")) {
				methodName = "get";
				for (int i = 0; i < arr.length; i++) {
					methodName = methodName
							+ arr[i].substring(0, 1)
									.toUpperCase(Locale.ENGLISH)
							+ arr[i].substring(1, arr[i].length()).toLowerCase(
									Locale.ENGLISH);
				}

			}
			if (type != null && type.equals("set")) {
				methodName = "set";
				for (int i = 0; i < arr.length; i++) {
					methodName = methodName
							+ arr[i].substring(0, 1)
									.toUpperCase(Locale.ENGLISH)
							+ arr[i].substring(1, arr[i].length()).toLowerCase(
									Locale.ENGLISH);
				}

			}
		}
		return methodName;
	}
}
