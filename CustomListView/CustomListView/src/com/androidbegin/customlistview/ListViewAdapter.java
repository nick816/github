package com.androidbegin.customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	
	// Declare Variables
	Context context;
	String[] rank;
	String[] country;
	String[] population;
	LayoutInflater inflater;

	public ListViewAdapter(Context context, String[] rank, String[] country,
			String[] population) {
		this.context = context;
		this.rank = rank;
		this.country = country;
		this.population = population;
	}

	@Override
	public int getCount() {
		return rank.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		// Declare Variables
		TextView txtrank;
		TextView txtcountry;
		TextView txtpopulation;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		View itemView = inflater.inflate(R.layout.listview_item, parent, false);
		
		// Locate the TextViews in listview_item.xml
		txtrank = (TextView) itemView.findViewById(R.id.rank);
		txtcountry = (TextView) itemView.findViewById(R.id.country);
		txtpopulation = (TextView) itemView.findViewById(R.id.population);

		// Capture position and set to the TextViews
		txtrank.setText(rank[position]);
		txtcountry.setText(country[position]);
		txtpopulation.setText(population[position]);

		return itemView;
	}
}
