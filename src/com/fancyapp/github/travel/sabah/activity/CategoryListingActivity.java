package com.fancyapp.github.travel.sabah.activity;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.fancyapp.github.travel.sabah.R;
import com.fancyapp.github.travel.sabah.TravelSabahController;
import com.fancyapp.github.travel.sabah.data.CategoryArrayAdapter;
import com.fancyapp.github.travel.sabah.model.Category;

public class CategoryListingActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listing);
		populateCategoryListing();
	}

	private void populateCategoryListing() {
		ListView categoryListview = (ListView) findViewById(R.id.listview_listing);
		List<Category> categoryList = ((TravelSabahController) CategoryListingActivity.this.getApplication()).getAllCategory(CategoryListingActivity.this);
		final CategoryArrayAdapter categoryAdapter = new CategoryArrayAdapter(
				CategoryListingActivity.this, R.layout.listing,
				categoryList,  ((TravelSabahController) CategoryListingActivity.this.getApplication()));

		categoryListview.setAdapter(categoryAdapter);
		categoryAdapter.notifyDataSetChanged();
	}
}
