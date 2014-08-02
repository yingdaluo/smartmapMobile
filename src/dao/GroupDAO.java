package dao;



import models.Group;
import util.Helper;
import util.MySQLiteHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GroupDAO {
	private MySQLiteHelper dbHelper;

	public GroupDAO(MySQLiteHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	public void createGroup(Group group) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.TABLE_GROUP_COLUMN_GROUPNAME, group.getGroupName()); // Group Name
		values.put(MySQLiteHelper.TABLE_GROUP_COLUMN_OWNERACCOUNT,group.getOwnerAccount()); // 
		values.put(MySQLiteHelper.TABLE_GROUP_COLUMN_THUMBNAIL, Helper.bitMapToByteArray(group.getGroupThumnail()));
		// Inserting Row
		db.insert(MySQLiteHelper.TABLE_GROUP, null, values);

		ContentValues relationValues = new ContentValues();
		relationValues.put(MySQLiteHelper.TABLE_RELATION_COLUMN_GROUPNAME, group.getGroupName()); // Group Name
		relationValues.put(MySQLiteHelper.TABLE_RELATION_COLUMN_USERACCOUNT,group.getOwnerAccount()); // 
		db.insert(MySQLiteHelper.TABLE_RELATION, null, relationValues);

		db.close(); // Closing database connection
	}

	public Group getGroupByName(String groupName) {
		// Select All Query
		//String selectQuery = "SELECT  * FROM " + MySQLiteHelper.TABLE_GROUP + " WHERE groupName = " + groupName;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(MySQLiteHelper.TABLE_GROUP,
				new String[] {MySQLiteHelper.TABLE_GROUP_COLUMN_ID,MySQLiteHelper.TABLE_GROUP_COLUMN_GROUPNAME,MySQLiteHelper.TABLE_GROUP_COLUMN_OWNERACCOUNT,MySQLiteHelper.TABLE_GROUP_COLUMN_THUMBNAIL},
				MySQLiteHelper.TABLE_GROUP_COLUMN_GROUPNAME +"= '"+groupName+"'", null, null, null, null);
		Group group = new Group();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			while(!cursor.isAfterLast()){
				if(groupName.equals(cursor.getString(1))){
					group.setId(Integer.parseInt(cursor.getString(0)));
					group.setGroupName(cursor.getString(1));
					group.setOwnerAccount(cursor.getString(2));
					// Adding Group to list
					group.setGroupThumnail(Helper.byteArrayToBitmap(cursor.getBlob(3)));
					break;
				}
				cursor.moveToNext();
			};
		}
		// return Group list
		return group;
	}


	public void deleteGroupByName(String groupName) {
		Group group = getGroupByName(groupName);
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		database.delete(MySQLiteHelper.TABLE_GROUP, MySQLiteHelper.TABLE_GROUP_COLUMN_ID
				+ " = " + group.getId(), null);

		database.delete(MySQLiteHelper.TABLE_RELATION, MySQLiteHelper.TABLE_RELATION_COLUMN_GROUPNAME
				+ " = '" + group.getGroupName()+"'", null);
		database.close();
	}

}
