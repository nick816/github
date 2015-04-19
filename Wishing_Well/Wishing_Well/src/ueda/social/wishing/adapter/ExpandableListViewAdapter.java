package ueda.social.wishing.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import ueda.social.wishing.R;
import ueda.social.wishing.http.HttpConstants;
import ueda.social.wishing.image.SmartImageView;
import ueda.social.wishing.model.User_Info;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
	 
    private Context _context;
    private ArrayList<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<User_Info>> _listDataChild;
    private ArrayList<Integer> _listHeaderImages;
 
    public ExpandableListViewAdapter(Context context, ArrayList<String> listDataHeader,HashMap<String, ArrayList<User_Info>> listChildData,ArrayList<Integer> listHeaderImage) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._listHeaderImages=listHeaderImage;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
//        final String childText = (String) getChild(groupPosition, childPosition);
        User_Info temp=(User_Info)getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.detailed_user_info, null);
        }
 
        SmartImageView user=(SmartImageView)convertView.findViewById(R.id.my_image);
        TextView  username = (TextView) convertView.findViewById(R.id.detailed_username);        
        TextView  useremail=(TextView)convertView.findViewById(R.id.detailed_email);
        if (temp.get_avarta().equals("")) {
			user.setImageResource(R.drawable.empty_user);
		}
        else{
        	user.setImageUrl(HttpConstants.PROFILE_IMAGE+temp.get_avarta());
        }
        username.setText(temp.get_username());
        useremail.setText(temp.get_email());

        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        int image=_listHeaderImages.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.xml.friend_group, null);
        }
 
        ImageView img_group_image=(ImageView)convertView.findViewById(R.id.group_image);
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.friend_group_name);
        img_group_image.setImageResource(image);
//        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setGravity(Gravity.CENTER_VERTICAL);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}