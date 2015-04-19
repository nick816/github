package com.androidbegin.customlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleItemView extends Activity {
	
	// Declare Variables
	TextView txtrank;
	TextView txtcountry;
	TextView txtpopulation;
	String[] rank;
	String[] country;
	String[] population;
	int position;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singleitemview);
		// Retrieve data from MainActivity on listview item click
		Intent i = getIntent();
		// Get the listview item click position
		position = i.getExtras().getInt("position");
		// Get the list of rank
		rank = i.getStringArrayExtra("rank");
		// Get the list of country
		country = i.getStringArrayExtra("country");
		// Get the list of population
		population = i.getStringArrayExtra("population");

		// Locate the TextViews in singleitemview.xml
		txtrank = (TextView) findViewById(R.id.rank);
		txtcountry = (TextView) findViewById(R.id.country);
		txtpopulation = (TextView) findViewById(R.id.population);

		// Load the text into the TextViews followed by the position
		txtrank.setText(rank[position]);
		txtcountry.setText(country[position]);
		txtpopulation.setText(population[position]);
	}
}