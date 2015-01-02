package com.fancyapp.github.travel.sabah.data;

import android.app.Activity;
import android.text.Html;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.fancyapp.github.travel.sabah.R;
import com.fancyapp.github.travel.sabah.model.ExpandableListGroup;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private final SparseArray<ExpandableListGroup> groups;
	public LayoutInflater inflater;
	public Activity activity;

	public ExpandableListAdapter(Activity act, SparseArray<ExpandableListGroup> groups) {
		this.activity = act;
		this.groups = groups;
		this.inflater = act.getLayoutInflater();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).getChildren().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final String children = (String) getChild(groupPosition, childPosition);
		TextView text = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.expandable_listrow_details, null);
		}
		text = (TextView) convertView.findViewById(R.id.textView1);
		text.setText(children != null ? Html.fromHtml(children) : children);
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).getChildren().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.expandable_listrow_group, null);
		}
		ExpandableListGroup group = (ExpandableListGroup) getGroup(groupPosition);
		((CheckedTextView) convertView).setText(group.getGroupName());
		((CheckedTextView) convertView)
				.setCompoundDrawablesWithIntrinsicBounds(0,// left
						0, // top
						group.getIconResourceId(), // right
						0);// bottom);
		((CheckedTextView) convertView).setChecked(isExpanded);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
}