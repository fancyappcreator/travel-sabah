package com.fancyapp.github.travel.sabah.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.fancyapp.github.travel.sabah.R;
import com.fancyapp.github.travel.sabah.TravelSabahController;

public class BaseActivity extends Activity {
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_save_favourite:
			break;
		case R.id.menu_favourite_settings:
			break;
		case android.R.id.home:
			final Intent intent = new Intent(this,
					CategoryListingActivity.class);
			this.startActivity(intent);
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);

		return true;
	}
}
