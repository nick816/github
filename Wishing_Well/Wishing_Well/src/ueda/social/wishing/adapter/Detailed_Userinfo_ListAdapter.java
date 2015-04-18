package ueda.social.wishing.adapter;

import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.http.HttpConstants;
import ueda.social.wishing.image.SmartImageView;
import ueda.social.wishing.model.User_Info;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Detailed_Userinfo_ListAdapter extends BaseAdapter {
	
	private Context context;	
	ArrayList<User_Info> search_users;
	
	public Detailed_Userinfo_ListAdapter(Context context,ArrayList<User_Info> users){
		this.context = context;
		this.search_users=users;
	}

	@Override
	public int getCount() {
		return search_users.size();
	}

	@Override
	public Object getItem(int position) {		
		return search_users.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.detailed_user_info, null);
        }
        SmartImageView user=(SmartImageView)convertView.findViewById(R.id.my_image);
        TextView  username = (TextView) convertView.findViewById(R.id.detailed_username);        
        TextView  useremail=(TextView)convertView.findViewById(R.id.detailed_email);
        if (search_users.get(position).get_avarta().equals("")) {
			user.setImageResource(R.drawable.empty_user);
		}
        else{
        	user.setImageUrl(HttpConstants.PROFILE_IMAGE+search_users.get(position).get_avarta());
        }
        username.setText(search_users.get(position).get_username());
        useremail.setText(search_users.get(position).get_email());
        return convertView;
	}

}
