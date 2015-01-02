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
import com.fancyapp.github.travel.sabah.activity.SubCategoryListingActivity;
import com.fancyapp.github.travel.sabah.model.Category;
import com.fancyapp.github.travel.sabah.model.Destination;
import com.fancyapp.github.travel.sabah.model.SubCategory;

public class CategoryArrayAdapter extends ArrayAdapter<Category> {
	private Context context;
	private List<Category> data;
	private TravelSabahController app;

	public CategoryArrayAdapter(Context context, int layoutResourceId,
			List<Category> data, TravelSabahController app) {
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

		final Category category = data.get(position);

		if (category != null) {
			String iconName = "ic_list";
			TextView categoryTitleTextView = (TextView) view
					.findViewById(R.id.category_listing_row_title);
			categoryTitleTextView.setText(category.getTitle());
			
			if (category.getIcon() != null && category.getIcon() != null)
			{
				iconName = category.getIcon();
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
					final Category category = (Category) parent
							.getItemAtPosition(pos);
					startNextActivity(category);
				}
			});
		}
		return view;
	}

	private void startNextActivity(Category category) {
		List<Destination> destinationList = app.getDestinationByCategoryId(
				category.getId(), context);

		List<SubCategory> subCatList = app.getSubCategoryByCategoryId(
				category.getId(), context);

		final Bundle bundle = new Bundle();
		bundle.putString(context.getString(R.string.cat_id),
				String.valueOf(category.getId()));
		bundle.putString(context.getString(R.string.cat_name),
				category.getTitle());

		if (destinationList != null && destinationList.size() > 0) {
			final Intent intent = new Intent(context,
					DestinationListingActivity.class);
			intent.putExtras(bundle);
			context.startActivity(intent);
		} else if (subCatList != null && subCatList.size() > 0) {
			final Intent intent = new Intent(context,
					SubCategoryListingActivity.class);
			intent.putExtras(bundle);
			context.startActivity(intent);
		} else {

		}
	}
}
