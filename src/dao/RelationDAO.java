package dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import models.Group;
import models.GroupUserRelation;
import util.MySQLiteHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RelationDAO {

	private MySQLiteHelper dbHelper;

	public RelationDAO(MySQLiteHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	public List<Group> findGroupsByUserAccount(String userAccount) {
		List<Group> groupList = new ArrayList<Group>();
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_RELATION,
				new String[] {MySQLiteHelper.TABLE_RELATION_COLUMN_ID,MySQLiteHelper.TABLE_RELATION_COLUMN_GROUPNAME,MySQLiteHelper.TABLE_RELATION_COLUMN_USERACCOUNT},
				MySQLiteHelper.TABLE_RELATION_COLUMN_USERACCOUNT +"= '"+userAccount+"'", null, null, null, null);
		cursor.moveToFirst();
		GroupDAO groupDAO = new GroupDAO(dbHelper);
		while (!cursor.isAfterLast()) {
			String groupName = cursor.getString(1);
			Group group = groupDAO.getGroupByName(groupName);
			groupList.add(group);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		database.close();
		return groupList;
	}

	public void createRelation(GroupUserRelation relation){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.TABLE_RELATION_COLUMN_GROUPNAME, relation.getGroupName()); // Group Name
		values.put(MySQLiteHelper.TABLE_RELATION_COLUMN_USERACCOUNT,relation.getUserAccount()); // 
		// Inserting Row
		db.insert(MySQLiteHelper.TABLE_RELATION, null, values);
		db.close(); // Closing database connection
	}

	public void deleteRelation(String groupName, String userAccount){
		SQLiteDatabase database = dbHelper.getWritableDatabase();

		database.delete(MySQLiteHelper.TABLE_RELATION, MySQLiteHelper.TABLE_RELATION_COLUMN_GROUPNAME
				+ " = '" + groupName+"' AND "+MySQLiteHelper.TABLE_RELATION_COLUMN_USERACCOUNT +" ='"+userAccount+"'" , null);
		database.close();
	}

	public HashSet<String> getUserIdsByGroup(String groupName) {
		HashSet<String> userIdSet = new HashSet<String>();
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_RELATION,
				new String[] {MySQLiteHelper.TABLE_RELATION_COLUMN_ID,MySQLiteHelper.TABLE_RELATION_COLUMN_GROUPNAME,MySQLiteHelper.TABLE_RELATION_COLUMN_USERACCOUNT},
				MySQLiteHelper.TABLE_RELATION_COLUMN_GROUPNAME +"= '"+groupName+"'", null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String userId = cursor.getString(2);
			userIdSet.add(userId);
			cursor.moveToNext();
		}
		return userIdSet;
	}
}
