package com.fancyapp.github.travel.sabah.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.fancyapp.github.travel.sabah.R;
import com.fancyapp.github.travel.sabah.data.ExpandableListAdapter;
import com.fancyapp.github.travel.sabah.model.Destination;
import com.fancyapp.github.travel.sabah.model.ExpandableListGroup;
import com.google.gson.Gson;

public class DestinationDetailsActivity extends BaseActivity {
	// more efficient than HashMap for mapping integers to objects
	SparseArray<ExpandableListGroup> groups = new SparseArray<ExpandableListGroup>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.destination_details);

		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		setTitle("");
		final Bundle bundle = getIntent().getExtras();
		if (bundle != null
				&& bundle.containsKey(getString(R.string.destination))) {
			final String destinationJson = bundle
					.getString(getString(R.string.destination));
			Destination destination = new Gson().fromJson(destinationJson,
					Destination.class);
			loadDestinationDetailsScreen(destination);
		}

		ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
		ExpandableListAdapter adapter = new ExpandableListAdapter(this, groups);
		listView.setAdapter(adapter);
	}

	public void loadDestinationDetailsScreen(Destination destination) {
		setTitle(destination.getTitle());

		if (destination.getPhoto() != null && destination.getPhoto() != "") {
			ImageView destMainImageView = (ImageView) findViewById(R.id.destination_main_image);
			int id = this.getResources().getIdentifier(destination.getPhoto(),
					"drawable", this.getPackageName());
			Drawable drawable = this.getResources().getDrawable(id);
			destMainImageView.setImageDrawable(drawable);
		}
		ExpandableListGroup groupItem;
		groupItem = new ExpandableListGroup(this.getString(R.string.title_desc), R.drawable.ic_info);
		groupItem.addChild(destination.getDesc());
		groups.append(0, groupItem);

		groupItem = new ExpandableListGroup(this.getString(R.string.title_address), R.drawable.ic_address);
		groupItem.addChild(String.format("<p>%1$s</p> <p>%2$s</p>", destination.getAddress(), destination.getCity().getTitle() + ", " + destination.getCity().getState().getTitle()));
		groups.append(1, groupItem);

		groupItem = new ExpandableListGroup(this.getString(R.string.title_location), R.drawable.ic_location);
		groupItem.addChild(String.format("Longitude:%1$s; Latitude:%2$s",
				destination.getLongitude(), destination.getLatitude()));
		groups.append(2, groupItem);

		groupItem = new ExpandableListGroup(this.getString(R.string.title_price_range), R.drawable.ic_price);
		groupItem.addChild(destination.getPrice_range());
		groups.append(3, groupItem);

		groupItem = new ExpandableListGroup(this.getString(R.string.title_transportation), R.drawable.ic_bus);
		groupItem.addChild(destination.getTransportation());
		groups.append(4, groupItem);

		groupItem = new ExpandableListGroup(this.getString(R.string.title_video), R.drawable.ic_utube);
		groupItem.addChild(destination.getVideo_url());
		groups.append(5, groupItem);

		groupItem = new ExpandableListGroup(this.getString(R.string.title_source), R.drawable.ic_note);
		groupItem.addChild(destination.getSource());
		groups.append(6, groupItem);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.destination_menu, menu);

		return true;
	}
}
