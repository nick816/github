package com.abma.texttimer.additional;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.support.v4.app.Fragment;

public class ReadFileFragment extends Fragment {
	public String _filename="";

	public ReadFileFragment() {
	}

	public String readFromAssetFile() {
		// To load text file
        InputStream input;
        String text = null;
        try {
        	AssetManager assetManager = this.getActivity().getAssets();
            input = assetManager.open(_filename);
 
             int size = input.available();
             byte[] buffer = new byte[size];
             input.read(buffer);
             input.close();
 
             // byte buffer into a string
             text = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
	}
}
