package com.example.newapp;

import java.util.List;

import models.Group;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupListAdapter extends ArrayAdapter<Group>{

	Context context; 
	int layoutResourceId; 
	List<Group> groupList;
	public GroupListAdapter(Context context, int resource, List<Group> objects) {
		super(context, resource, objects);
		this.context = context;
		this.layoutResourceId = resource;
		this.groupList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		GroupHolder holder = null;

		if(row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(R.layout.group_list_item, parent, false);
			holder = new GroupHolder();
			holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
			holder.txtTitle = (TextView)row.findViewById(R.id.label);

			row.setTag(holder);
		}
		else
		{
			holder = (GroupHolder)row.getTag();
		}

		Group group = groupList.get(position);
		holder.txtTitle.setText(group.getGroupName());
		holder.imgIcon.setImageBitmap(group.getGroupThumnail());

		return row;
	}
	static class GroupHolder
	{
		ImageView imgIcon;
		TextView txtTitle;
	}
	@Override
	public int getCount(){
		return groupList.size();
	}
}
