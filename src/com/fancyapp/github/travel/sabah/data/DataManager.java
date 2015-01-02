package com.fancyapp.github.travel.sabah.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.fancyapp.github.travel.sabah.model.Category;
import com.fancyapp.github.travel.sabah.model.City;
import com.fancyapp.github.travel.sabah.model.Destination;
import com.fancyapp.github.travel.sabah.model.State;
import com.fancyapp.github.travel.sabah.model.SubCategory;

@SuppressLint("UseSparseArrays")
public class DataManager extends BaseDataSource {
	private final String TAG = this.getClass().getSimpleName();

	public DataManager(Context context) {
		super(context);
		this.open();
	}

	public List<Category> getAllCategory() {
		return loadAllCategories();
	}

	public List<SubCategory> getAllSubCategory() {
		return loadAllSubCategory();
	}

	public Map<Integer, List<Destination>> getCategoryDestinationMapping() {
		Map<Integer, List<Destination>> mapping = new HashMap<Integer, List<Destination>>();
		List<Category> categoryList = getAllCategory();
		int id = 0;
		if (categoryList != null) {
			for (Category category : categoryList) {
				id = category.getId();
				mapping.put(id, LoadDestinationByCategoryId(id));
			}
		}
		return mapping;
	}

	public Map<Integer, List<SubCategory>> getCategorySubCategoryMapping() {
		Map<Integer, List<SubCategory>> mapping = new HashMap<Integer, List<SubCategory>>();
		List<Category> categoryList = getAllCategory();
		int id = 0;
		if (categoryList != null) {
			for (Category category : categoryList) {
				id = category.getId();
				mapping.put(id, this.LoadSubCategoryByCategoryId(id));
			}
		}
		return mapping;
	}

	public Map<Integer, List<Destination>> getSubCategoryDestinationMapping() {
		Map<Integer, List<Destination>> mapping = new HashMap<Integer, List<Destination>>();
		List<SubCategory> subCategoryList = getAllSubCategory();
		int id = 0;
		if (subCategoryList != null) {
			for (SubCategory subCategory : subCategoryList) {
				id = subCategory.getId();
				mapping.put(id, LoadDestinationBySubCategoryId(id));
			}
		}
		return mapping;
	}

	private List<Category> loadAllCategories() {
		final List<Category> categoryList = new ArrayList<Category>();
		try {
			Category category = null;
			final Cursor cursor = getDatabase().rawQuery(
					"SELECT cat_id, cat_title, cat_desc, cat_icon, cat_status, cat_sort_order "
							+ "FROM CATEGORY ORDER BY cat_sort_order", null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				category = new Category();
				category.setId(cursor.getInt(0));
				category.setTitle(cursor.getString(1));
				category.setDesc(cursor.getString(2));
				category.setIcon(cursor.getString(3));
				category.setStatus(cursor.getInt(4));
				category.setSort_order(cursor.getInt(5));
				categoryList.add(category);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (final SQLiteException e) {
			Log.e(TAG, "Error occurred in loading category listing", e);
		}
		return categoryList;
	}

	private List<SubCategory> loadAllSubCategory() {
		final List<SubCategory> subCategoryList = new ArrayList<SubCategory>();
		try {
			SubCategory subCategory = null;
			final Cursor cursor = getDatabase()
					.rawQuery(
							"select sub_cat_id, cat_id, sub_cat_title, sub_cat_desc, sub_cat_icon, sub_cat_status "
									+ "from subcategory", null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				subCategory = new SubCategory();
				subCategory.setId(cursor.getInt(0));
				subCategory.setCat_id(cursor.getInt(1));
				subCategory.setTitle(cursor.getString(2));
				subCategory.setDesc(cursor.getString(3));
				subCategory.setIcon(cursor.getString(4));
				subCategory.setStatus(cursor.getInt(5));
				subCategoryList.add(subCategory);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (final SQLiteException e) {
			Log.e(TAG, "Error occurred in loading sub category listing", e);
		}
		return subCategoryList;
	}

	/**
	 * Load all destinations fall under the specified category group id
	 * 
	 * @param categoryId
	 * @return
	 */
	private List<Destination> LoadDestinationByCategoryId(int categoryId) {

		final List<Destination> destinationList = new ArrayList<Destination>();
		try {
			Destination destination = null;
			final Cursor cursor = getDatabase()
					.rawQuery(
							String.format(
									"select d.des_id, d.des_title, d.des_photo, d.city_id, d.des_description, d.des_transportation, d.des_price_range, d.des_location_lat, d.des_location_long, d.des_video, d.des_address, d.des_operation_time, d.des_source, d.des_recommend, d.des_date,"
											+ "l.city_id, l.city_title, l.state_title "
											+ "from destination d inner join destination_category dc on d.des_id=dc.des_id "
											+ "inner join (select city_id, city_title, state_title from city, state where city.state_id=state.state_id and city.city_status=1 and state.state_status=1) as l on l.city_id=d.city_id "
											+ "where (dc.sub_cat_id=0 OR dc.sub_cat_id is null) and dc.cat_id=%1$s;",
									categoryId), null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				destination = new Destination();
				destination.setId(cursor.getInt(0));
				destination.setTitle(cursor.getString(1));
				destination.setPhoto(cursor.getString(2));
				destination.setDesc(cursor.getString(4));
				destination.setTransportation(cursor.getString(5));
				destination.setPrice_range(cursor.getString(6));
				destination.setLatitude(cursor.getString(7));
				destination.setLongitude(cursor.getString(8));
				destination.setVideo_url(cursor.getString(9));
				destination.setAddress(cursor.getString(10));
				destination.setOperation_time(cursor.getString(11));
				destination.setSource(cursor.getString(12));

				destination.setCity(new City(cursor.getInt(15), cursor
						.getString(16), new State(cursor.getString(17))));
				
				destinationList.add(destination);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (final SQLiteException e) {
			Log.e(TAG,
					"Error occurred in loading destination under category listing",
					e);
		}
		return destinationList;
	}

	/**
	 * Load all destinations fall under the specified sub category group id
	 * 
	 * @param categoryId
	 * @return
	 */
	private List<Destination> LoadDestinationBySubCategoryId(int subCategoryId) {

		final List<Destination> destinationList = new ArrayList<Destination>();
		try {
			Destination destination = null;
			final Cursor cursor = getDatabase()
					.rawQuery(
							String.format(
									"select d.des_id, d.des_title, d.des_photo, d.city_id, d.des_description, d.des_transportation, d.des_price_range, d.des_location_lat, d.des_location_long, d.des_video, d.des_address, d.des_operation_time, d.des_source, d.des_recommend, d.des_date,"
											+ "l.city_id, l.city_title, l.state_title "
											+ "from destination d inner join destination_category dc on d.des_id=dc.des_id "
											+ "inner join (select city_id, city_title, state_title from city, state where city.state_id=state.state_id and city.city_status=1 and state.state_status=1) as l on l.city_id=d.city_id "
											+ "where dc.sub_cat_id=%1$s;",
									subCategoryId), null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				destination = new Destination();
				destination.setId(cursor.getInt(0));
				destination.setTitle(cursor.getString(1));
				destination.setPhoto(cursor.getString(2));
				destination.setDesc(cursor.getString(4));
				destination.setTransportation(cursor.getString(5));
				destination.setPrice_range(cursor.getString(6));
				destination.setLatitude(cursor.getString(7));
				destination.setLongitude(cursor.getString(8));
				destination.setVideo_url(cursor.getString(9));
				destination.setAddress(cursor.getString(10));
				destination.setOperation_time(cursor.getString(11));
				destination.setSource(cursor.getString(12));

				
				destination.setCity(new City(cursor.getInt(15), cursor
						.getString(16), new State(cursor.getString(17))));

				destinationList.add(destination);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (final SQLiteException e) {
			Log.e(TAG,
					"Error occurred in loading destination under category listing",
					e);
		}
		return destinationList;
	}

	private List<SubCategory> LoadSubCategoryByCategoryId(int categoryId) {
		final List<SubCategory> subCategoryList = new ArrayList<SubCategory>();
		try {
			SubCategory subCategory = null;
			final Cursor cursor = getDatabase()
					.rawQuery(
							String.format(
									"select sc.sub_cat_id, sc.cat_id, sc.sub_cat_title, sc.sub_cat_desc, sc.sub_cat_icon, "
											+ "sc.sub_cat_status from subcategory sc inner join category c on sc.cat_id=c.cat_id and c.cat_id=%1$s;",
									categoryId), null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				subCategory = new SubCategory();
				subCategory.setId(cursor.getInt(0));
				subCategory.setCat_id(cursor.getInt(1));
				subCategory.setTitle(cursor.getString(2));
				subCategory.setDesc(cursor.getString(3));
				subCategory.setIcon(cursor.getString(4));
				subCategory.setStatus(cursor.getInt(5));

				subCategoryList.add(subCategory);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (final SQLiteException e) {
			Log.e(TAG,
					"Error occurred in loading sub categories under category listing",
					e);
		}
		return subCategoryList;
	}

	private City LoadCityById(int cityId) {
		return null;
	}

	private State loadStateById(int stateId) {
		return null;
	}
}