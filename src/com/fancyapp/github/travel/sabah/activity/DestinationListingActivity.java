package com.fancyapp.github.travel.sabah.activity;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.fancyapp.github.travel.sabah.R;
import com.fancyapp.github.travel.sabah.TravelSabahController;
import com.fancyapp.github.travel.sabah.data.DestinationArrayAdapter;
import com.fancyapp.github.travel.sabah.model.Destination;

public class DestinationListingActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listing);

		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		final Bundle bundle = getIntent().getExtras();

		if (bundle != null && bundle.containsKey(getString(R.string.cat_id))) {
			// parent category
			if (bundle != null
					&& bundle.containsKey(getString(R.string.cat_name))) {
				setTitle(bundle.getString(getString(R.string.cat_name)));
			}
			String catId = bundle.getString(getString(R.string.cat_id));
			populateDestinationListing(Integer.valueOf(catId), true);
		} else if (bundle != null
				&& bundle.containsKey(getString(R.string.sub_cat_id))) {
			// parent category
			if (bundle != null
					&& bundle.containsKey(getString(R.string.sub_cat_name))) {
				setTitle(bundle.getString(getString(R.string.sub_cat_name)));
			}
			String subCatId = bundle.getString(getString(R.string.sub_cat_id));
			populateDestinationListing(Integer.valueOf(subCatId), false);
		}
	}

	private void populateDestinationListing(int id, boolean isParentCategory) {
		ListView destinationListview = (ListView) findViewById(R.id.listview_listing);
		List<Destination> destinationList;
		if (isParentCategory) {
			destinationList = ((TravelSabahController) DestinationListingActivity.this
					.getApplication()).getDestinationByCategoryId(id,
					DestinationListingActivity.this);
		} else {
			destinationList = ((TravelSabahController) DestinationListingActivity.this
					.getApplication()).getDestinationBySubCategoryId(id,
					DestinationListingActivity.this);
		}

		final DestinationArrayAdapter destinationArrayAdapter = new DestinationArrayAdapter(
				DestinationListingActivity.this, R.layout.listing,
				destinationList,
				((TravelSabahController) DestinationListingActivity.this
						.getApplication()));

		destinationListview.setAdapter(destinationArrayAdapter);
		destinationArrayAdapter.notifyDataSetChanged();
	}
}
