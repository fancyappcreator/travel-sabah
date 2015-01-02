package com.fancyapp.github.travel.sabah.data;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fancyapp.github.travel.sabah.R;
import com.fancyapp.github.travel.sabah.TravelSabahController;
import com.fancyapp.github.travel.sabah.activity.DestinationListingActivity;
import com.fancyapp.github.travel.sabah.model.SubCategory;

public class SubCategoryArrayAdapter extends ArrayAdapter<SubCategory> {
	private Context context;
	private List<SubCategory> data;
	private TravelSabahController app;

	public SubCategoryArrayAdapter(Context context, int layoutResourceId,
			List<SubCategory> data, TravelSabahController app) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.data = data;
		this.app = app;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			final LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.listing_row, null);
		}

		final SubCategory subCategory = data.get(position);

		if (subCategory != null) {
			String iconName = "ic_list";
			TextView subCategoryTitleTextView = (TextView) view
					.findViewById(R.id.category_listing_row_title);
			subCategoryTitleTextView.setText(subCategory.getTitle());
			if (subCategory.getIcon() != null && subCategory.getIcon() != null)
			{
				iconName = subCategory.getIcon();
			}
			ImageView image =  (ImageView) view
					.findViewById(R.id.listing_icon);
			int id = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
			Drawable drawable = context.getResources().getDrawable(id);
			image.setImageDrawable(drawable);

			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					final ListView parent = (ListView) v.getParent();
					final int pos = parent.getPositionForView(v);
					final SubCategory subCategory = (SubCategory) parent
							.getItemAtPosition(pos);
					startDestinationActivity(subCategory);
				}
			});
		}
		return view;
	}

	private void startDestinationActivity(SubCategory subCategory) {
		final Bundle bundle = new Bundle();
		bundle.putString(context.getString(R.string.sub_cat_id),
				String.valueOf(subCategory.getId()));
		bundle.putString(context.getString(R.string.sub_cat_name),
				subCategory.getTitle());
		bundle.remove(context.getString(R.string.cat_id));
		bundle.remove(context.getString(R.string.cat_name));
		
		final Intent intent = new Intent(context,
				DestinationListingActivity.class);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
}
