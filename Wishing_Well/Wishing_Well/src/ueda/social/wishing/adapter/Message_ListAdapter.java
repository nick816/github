package ueda.social.wishing.adapter;

import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.http.HttpConstants;
import ueda.social.wishing.image.SmartImageView;
import ueda.social.wishing.model.Message;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Message_ListAdapter extends BaseAdapter {
	
	private Context context;	
	ArrayList<Message> inboxes;
	ArrayList<String> senders;
	
	public Message_ListAdapter(Context context,ArrayList<Message> messages,ArrayList<String> avartas){
		this.context = context;
		this.inboxes=messages;
		this.senders=avartas;
	}

	@Override
	public int getCount() {
		return inboxes.size();
	}

	@Override
	public Object getItem(int position) {		
		return inboxes.get(position);
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
            convertView = mInflater.inflate(R.layout.detailed_message, null);
        }
         
        SmartImageView senderIcon = (SmartImageView) convertView.findViewById(R.id.message_friend_profile);
        TextView  sender_name = (TextView) convertView.findViewById(R.id.message_sender_name);        
        TextView  send_date=(TextView)convertView.findViewById(R.id.message_send_date);
        TextView send_time=(TextView)convertView.findViewById(R.id.message_send_time);
        TextView content=(TextView)convertView.findViewById(R.id.message_content);
        
//        senderIcon.setImageResource(sender_users.get(position).getprofile);
        if (senders.get(position).equals("")) {
			senderIcon.setImageResource(R.drawable.empty_user);
		}else{
			senderIcon.setImageUrl(HttpConstants.PROFILE_IMAGE+senders.get(position));
		}
        
        sender_name.setText(inboxes.get(position).get_username());
        if (inboxes.get(position).get_content().length()<=50) {
			content.setText(inboxes.get(position).get_content());
		}
        else{
        	content.setText(inboxes.get(position).get_content().substring(0, 50)+"...");
        }        
        send_date.setText(inboxes.get(position).get_date());
        send_time.setText(inboxes.get(position).get_time());
        return convertView;
	}

}
