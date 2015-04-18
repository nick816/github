package com.example.slidingsimplesample.activity;

import java.util.List;

import com.example.slidingsimplesample.Country;
import com.example.slidingsimplesample.CountryListAdapter;
import com.example.slidingsimplesample.R;
import com.example.slidingsimplesample.Storage;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

public class CountrySelectActivity extends Activity implements TextWatcher {
    
    private static List<Country> countries = Storage.getItems();
    private EditText editTextFilter;
    private ListView listViewCountries;
    private CountryListAdapter adapter;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_select);
        adapter = new CountryListAdapter(this, countries);
        
        editTextFilter = (EditText)findViewById(R.id.editTextFilter);
        editTextFilter.addTextChangedListener(this);
        
        listViewCountries = (ListView)findViewById(R.id.listViewCountries);
        listViewCountries.setAdapter(adapter);
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
