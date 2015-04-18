package ueda.social.wishing.adapter;

import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.http.HttpConstants;
import ueda.social.wishing.image.SmartImageView;
import ueda.social.wishing.model.User_Info;
import ueda.social.wishing.model.Userdata_DB_Helper;
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

public class Detailed_Friend_WishInfo_ListAdapter extends BaseAdapter {
	
	private Context context;	
	ArrayList<Wish_Info> wishes;
	
	public Detailed_Friend_WishInfo_ListAdapter(Context context,ArrayList<Wish_Info> wish){
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
            convertView = mInflater.inflate(R.layout.detailed_friend_wish, null);
        }
//		String[] events={"Birthday","X mas","Wedding","Anniversary","Valentine day","House warming","Leaving present","New baby","Other"};
		Userdata_DB_Helper db=new Userdata_DB_Helper(context);
		
        SmartImageView wish_image=(SmartImageView)convertView.findViewById(R.id.cell_wish_image);
        TextView  wishname = (TextView) convertView.findViewById(R.id.cell_wish_name);        
        TextView  username=(TextView)convertView.findViewById(R.id.cell_friend_name);
        TextView  like_count=(TextView)convertView.findViewById(R.id.like_count);
        if (wishes.get(position).get_img().equals("")) {
			wish_image.setImageResource(R.drawable.empty_wish);
		}
        else{
        	wish_image.setImageUrl(HttpConstants.PROFILE_IMAGE+wishes.get(position).get_img());
        }
        like_count.setText(String.valueOf(wishes.get(position).get_like_count()));
        int user_id=Integer.parseInt(wishes.get(position).get_user_id());
        User_Info temp=new User_Info();
        temp=db.get_user(user_id);
        wishname.setText(wishes.get(position).get_title());
        username.setText("Posted by "+temp.get_username());        
        return convertView;
	}

}
