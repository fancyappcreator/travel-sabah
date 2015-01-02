package com.fancyapp.github.travel.sabah;

import java.util.List;
import java.util.Map;

import android.app.Application;
import android.content.Context;

import com.fancyapp.github.travel.sabah.data.DataManager;
import com.fancyapp.github.travel.sabah.model.Category;
import com.fancyapp.github.travel.sabah.model.Destination;
import com.fancyapp.github.travel.sabah.model.SubCategory;

public class TravelSabahController extends Application {
	private DataManager dataManager;
	private List<Category> categoryList;
	private Map<Integer, List<Destination>> subCategoryDestinationsMapping;
	private Map<Integer, List<Destination>> categoryDestinationsMapping;
	private Map<Integer, List<SubCategory>> categorySubCategoryMapping;
	

	public DataManager getDataManager(Context context) {
		if (dataManager == null) {
			dataManager = new DataManager(context);
		}
		return dataManager;
	}

	public Map<Integer, List<Destination>> getCategoryDestinationsMapping(
			Context context) {
		if (categoryDestinationsMapping == null) {
			categoryDestinationsMapping = getDataManager(context)
					.getCategoryDestinationMapping();
		}
		return categoryDestinationsMapping;
	}

	public List<Destination> getDestinationByCategoryId(int categoryId,
			Context context) {
		if (getCategoryDestinationsMapping(context) != null) {
			return getCategoryDestinationsMapping(context).get(categoryId);
		}
		return null;
	}

	public Map<Integer, List<Destination>> getSubCategoryDestinationsMapping(
			Context context) {
		if (subCategoryDestinationsMapping == null) {
			subCategoryDestinationsMapping = getDataManager(context)
					.getSubCategoryDestinationMapping();
		}
		return subCategoryDestinationsMapping;
	}

	public List<Destination> getDestinationBySubCategoryId(int subCategoryId,
			Context context) {
		if (getCategoryDestinationsMapping(context) != null) {
			return getSubCategoryDestinationsMapping(context)
					.get(subCategoryId);
		}
		return null;
	}

	public Map<Integer, List<SubCategory>> getCategorySubCategoryMapping(
			Context context) {
		if (categorySubCategoryMapping == null) {
			categorySubCategoryMapping = getDataManager(context)
					.getCategorySubCategoryMapping();
		}
		return categorySubCategoryMapping;
	}

	public List<SubCategory> getSubCategoryByCategoryId(int categoryId,
			Context context) {
		if (getCategorySubCategoryMapping(context) != null) {
			return getCategorySubCategoryMapping(context).get(categoryId);
		}
		return null;
	}

	public List<Category> getAllCategory(Context context) {
		if (categoryList == null) {
			categoryList = getDataManager(context).getAllCategory();
		}
		return categoryList;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		dataManager.close();
	}
}
