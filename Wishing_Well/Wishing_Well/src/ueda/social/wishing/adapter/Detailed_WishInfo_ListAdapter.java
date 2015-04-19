package ueda.social.wishing.adapter;

import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.http.HttpConstants;
import ueda.social.wishing.image.SmartImageView;
import ueda.social.wishing.model.User_Info;
import ueda.social.wishing.model.Wish_Info;
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

public class Detailed_WishInfo_ListAdapter extends BaseAdapter {
	
	private Context context;	
	ArrayList<Wish_Info> wishes;
	
	public Detailed_WishInfo_ListAdapter(Context context,ArrayList<Wish_Info> wish){
		this.context = context;
		this.wishes=wish;
	}

	@Override
	public int getCount() {
		return wishes.size();
	}

	@Override
	public Object getItem(int position) {		
		return wishes.get(position);
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
            convertView = mInflater.inflate(R.layout.detailed_wish_info, null);
        }
		String[] events={"Birthday","X mas","Wedding","Anniversary","Valentine day","House warming","Leaving present","New baby","Other"};
        SmartImageView user=(SmartImageView)convertView.findViewById(R.id.wish_image);
        TextView  username = (TextView) convertView.findViewById(R.id.detailed_wishtitle);        
        TextView  useremail=(TextView)convertView.findViewById(R.id.detailed_wish_event);
        if (wishes.get(position).get_img().equals("")) {
			user.setImageResource(R.drawable.empty_wish);
		}
        else{
        	user.setImageUrl(HttpConstants.PROFILE_IMAGE+wishes.get(position).get_img());
        }
        username.setText(wishes.get(position).get_title());
        useremail.setText(events[Integer.parseInt(wishes.get(position).get_event())-1]);
        return convertView;
	}

}
