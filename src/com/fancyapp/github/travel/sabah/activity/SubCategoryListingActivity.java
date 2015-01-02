package com.fancyapp.github.travel.sabah.activity;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.fancyapp.github.travel.sabah.R;
import com.fancyapp.github.travel.sabah.TravelSabahController;
import com.fancyapp.github.travel.sabah.data.SubCategoryArrayAdapter;
import com.fancyapp.github.travel.sabah.model.SubCategory;

public class SubCategoryListingActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listing);
		final Bundle bundle = getIntent().getExtras();

		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// load sub category listing
		if (bundle != null && bundle.containsKey(getString(R.string.cat_id))) {
			// set screen title (category name)
			if (bundle != null
					&& bundle.containsKey(getString(R.string.cat_name))) {
				setTitle(bundle.getString(getString(R.string.cat_name)));
			}
			String catId = bundle.getString(getString(R.string.cat_id));
			populateSubCategoryListing(Integer.valueOf(catId));
		}
	}

	private void populateSubCategoryListing(int catId) {
		ListView categoryListview = (ListView) findViewById(R.id.listview_listing);
		List<SubCategory> categoryList = ((TravelSabahController) SubCategoryListingActivity.this
				.getApplication()).getSubCategoryByCategoryId(catId,
				SubCategoryListingActivity.this);

		final SubCategoryArrayAdapter categoryAdapter = new SubCategoryArrayAdapter(
				SubCategoryListingActivity.this, R.layout.listing,
				categoryList,
				((TravelSabahController) SubCategoryListingActivity.this
						.getApplication()));

		categoryListview.setAdapter(categoryAdapter);
		categoryAdapter.notifyDataSetChanged();
	}
}
