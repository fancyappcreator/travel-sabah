package com.fancyapp.github.travel.sabah.data;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fancyapp.github.travel.sabah.R;
import com.fancyapp.github.travel.sabah.TravelSabahController;
import com.fancyapp.github.travel.sabah.activity.DestinationDetailsActivity;
import com.fancyapp.github.travel.sabah.model.Destination;
import com.google.gson.Gson;

public class DestinationArrayAdapter extends ArrayAdapter<Destination> {
	private Context context;
	private List<Destination> data;
	private TravelSabahController app;

	public DestinationArrayAdapter(Context context, int layoutResourceId,
			List<Destination> data, TravelSabahController app) {
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

		final Destination destination = data.get(position);

		if (destination != null) {
			TextView destTitleTextView = (TextView) view
					.findViewById(R.id.category_listing_row_title);
			destTitleTextView.setText(destination.getTitle());

			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					final ListView parent = (ListView) v.getParent();
					final int pos = parent.getPositionForView(v);
					final Destination destination = (Destination) parent
							.getItemAtPosition(pos);
					startDetailsActivity(destination);
				}
			});
		}
		return view;
	}
	
	private void startDetailsActivity(Destination destination) {
		final Bundle bundle = new Bundle();
		bundle.putString(context.getString(R.string.destination),
				new Gson().toJson(destination));
		
		final Intent intent = new Intent(context,
				DestinationDetailsActivity.class);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	
	
}
