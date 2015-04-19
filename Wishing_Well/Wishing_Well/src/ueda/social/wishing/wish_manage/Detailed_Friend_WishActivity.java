package ueda.social.wishing.wish_manage;

import ueda.social.wishing.R;
import ueda.social.wishing.activity.SecondActivity;
import ueda.social.wishing.http.HttpConstants;
import ueda.social.wishing.http.Wish_like;
import ueda.social.wishing.http_request.Wish_likeHttpRequest;
import ueda.social.wishing.image.SmartImageView;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Detailed_Friend_WishActivity extends Activity implements Wish_likeHttpRequest{

	private Bundle bndanimation_to_child,bndanimation_to_parent;
	private Wish_likeHttpRequest w_like=this;
	private int current_user_id=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_friend_wish);
		
		bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3, R.anim.animation4).toBundle();
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);	
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		current_user_id=sharedPreferences.getInt("current_userid", -1);	
		
		final TextView wish_title = (TextView) this.findViewById(R.id.wish_title_detail);		
		final TextView wish_event=(TextView)this.findViewById(R.id.wish_event_detail);
		final TextView wish_date=(TextView)this.findViewById(R.id.wish_date_detail);
		final TextView wish_code=(TextView)this.findViewById(R.id.wish_product_detail);
		final TextView wish_price=(TextView)this.findViewById(R.id.wish_price_detail);
		final TextView wish_description=(TextView)this.findViewById(R.id.wish_desc_detail);
		SmartImageView image=(SmartImageView)this.findViewById(R.id.friend_image);
		SmartImageView wish_image=(SmartImageView)this.findViewById(R.id.wish_image_detail);
		TextView posted=(TextView)this.findViewById(R.id.posted_by);
		Button btn_like=(Button)this.findViewById(R.id.btn_wish_like);
		Button btn_unlike=(Button)this.findViewById(R.id.btn_wish_unlike);
		
		Bundle bundle=getIntent().getExtras();
		
		// set the custom dialog components - text, image and button
		wish_title.setText(bundle.getString("title"));
		wish_event.setText(bundle.getString("event"));
		wish_date.setText(bundle.getString("date"));
		wish_code.setText(bundle.getString("code"));
		wish_price.setText(bundle.getString("price"));
//		wish_like.setText(bundle.getString("like_count"));
		wish_description.setText(bundle.getString("description"));
		posted.setText("Posted by "+bundle.getString("name"));
		image.setImageUrl(HttpConstants.PROFILE_IMAGE+bundle.getString("image_url"));
		wish_image.setImageUrl(HttpConstants.PROFILE_IMAGE+bundle.getString("wish_image"));
		int is_like=bundle.getInt("is_like");
		final String wish_id=bundle.getString("wish_id");
//		is_like=friend_wishes.get(position).get
		if (is_like==0) {
			btn_like.setEnabled(true);
			btn_unlike.setEnabled(true);
		}
		else{
			btn_like.setEnabled(false);
			btn_unlike.setEnabled(false);
		}
		btn_like.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				friend_wishes.get(position).set_like_count(friend_wishes.get(position).get_like_count()+1);
				Wish_like like=new Wish_like(Detailed_Friend_WishActivity.this, String.valueOf(current_user_id), wish_id, 1, w_like);
				like.onRun();				
			}
		});
		
		btn_unlike.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				friend_wishes.get(position).set_like_count(friend_wishes.get(position).get_like_count()-1);
				Wish_like like=new Wish_like(Detailed_Friend_WishActivity.this, String.valueOf(current_user_id), wish_id, -1, w_like);
				like.onRun();	
			}
		});				
	}
	@Override
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Detailed_Friend_WishActivity.this,SecondActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
		return true;
	}
	@Override
	public void onBackPressed(){
		
	}
	@Override
	public void wish_like_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void wish_like_requestSuccess() {
		// TODO Auto-generated method stub
		
	}
}
