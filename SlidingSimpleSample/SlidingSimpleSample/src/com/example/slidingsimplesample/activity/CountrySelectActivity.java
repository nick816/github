package com.example.slidingsimplesample.activity;

import java.util.ArrayList;

import com.example.slidingsimplesample.Country;
import com.example.slidingsimplesample.CountryListAdapter;
import com.example.slidingsimplesample.MainActivity;
import com.example.slidingsimplesample.R;
import com.example.slidingsimplesample.Storage;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class CountrySelectActivity extends Activity implements TextWatcher {

    private static ArrayList<Country> countries = Storage.getItems();
    private EditText editTextFilter;
    private ListView listViewCountries;
    private CountryListAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_select);

        ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C4CD")));

        adapter = new CountryListAdapter(this, countries);

        editTextFilter = (EditText)findViewById(R.id.editTextFilter);
        editTextFilter.addTextChangedListener(this);

        listViewCountries = (ListView)findViewById(R.id.listViewCountries);
        listViewCountries.setAdapter(adapter);

        listViewCountries.setOnItemClickListener(new OnItemClickListener() {

        	@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent country_get = new Intent(CountrySelectActivity.this, MainActivity.class);
				country_get.putExtra("countryName",countries.get(position).getName() );
				startActivity(country_get);
			}
		});
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {
    	    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

}
