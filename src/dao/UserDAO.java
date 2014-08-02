package dao;


import models.User;
import util.MySQLiteHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.model.GraphUser;

public class UserDAO {
	private MySQLiteHelper dbHelper;

	public UserDAO(MySQLiteHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	public void createUser(GraphUser user) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.TABLE_USER_COLUMN_FACEBOOKACCOUNT, user.getId()); // Group Name
		values.put(MySQLiteHelper.TABLE_USER_COLUMN_USERNAME,user.getName()); // 
		// Inserting Row
		db.insert(MySQLiteHelper.TABLE_USER, null, values);
		db.close(); // Closing database connection
	}

	public User getUserByAccount(String userId) {
		// Select All Query
		//String selectQuery = "SELECT  * FROM " + MySQLiteHelper.TABLE_GROUP + " WHERE groupName = " + groupName;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_USER,
				new String[] {MySQLiteHelper.TABLE_USER_COLUMN_FACEBOOKACCOUNT,MySQLiteHelper.TABLE_USER_COLUMN_USERNAME},
				MySQLiteHelper.TABLE_USER_COLUMN_FACEBOOKACCOUNT +"= '"+userId+"'", null, null, null, null);
		User user = new User();		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			user.setFacebookAccount(userId);
			user.setUsername(cursor.getString(1));
		}else {
			return null;
		}
		// return Group list
		return user;
	}
	
	

}
