package ueda.social.wishing.activity;

import java.util.ArrayList;
import java.util.HashMap;

import ueda.social.wishing.R;
import ueda.social.wishing.adapter.ExpandableListViewAdapter;
import ueda.social.wishing.http.Friend_Grouping;
import ueda.social.wishing.http_request.Friend_GroupingHttpRequest;
import ueda.social.wishing.model.User_Info;
import ueda.social.wishing.model.Userdata_DB_Helper;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class AddressActivity extends Activity implements Friend_GroupingHttpRequest{

	private ExpandableListAdapter listAdapter;
    private ExpandableListView friend_list;
    ArrayList<String> listDataHeader;
    ArrayList<Integer> listHeaderImage;
    HashMap<String, ArrayList<User_Info>> listDataChild;	
//	private ListView friend_list;
	private Context context;	
	private Userdata_DB_Helper db;
	ArrayList<User_Info> friends=new ArrayList<User_Info>();
	private Bundle bndanimation_to_child,bndanimation_to_parent;
	
	private Friend_GroupingHttpRequest fg=this;
	private ProgressDialog progress_dg;
	private int current_user_id=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		context=this;
		setContentView(R.layout.activity_address);
		
		bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3, R.anim.animation4).toBundle();
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		db=new Userdata_DB_Helper(context);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		current_user_id=sharedPreferences.getInt("current_userid", -1);
		friends=db.getAll_users(); 
		
        friend_list = (ExpandableListView)this.findViewById(R.id.expandable_friendList);
        //Expandable List View////////////////////////////////////////////////////////////////////////////////////////////////////////////           
        // preparing list data        
        prepareListData(friends);
        listAdapter = new ExpandableListViewAdapter(context, listDataHeader, listDataChild, listHeaderImage); 
        // setting list adapter
        friend_list.setAdapter(listAdapter); 
        // ListView Group click listener
        friend_list.setOnGroupClickListener(new OnGroupClickListener() {
 
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });
 
        // Listview Group expanded listener
        friend_list.setOnGroupExpandListener(new OnGroupExpandListener() {
 
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });
 
        // Listview Group collasped listener
        friend_list.setOnGroupCollapseListener(new OnGroupCollapseListener() {
 
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
 
        // Listview on child click listener
        friend_list.setOnChildClickListener(new OnChildClickListener() {
 
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    final int groupPosition, final int childPosition, long id) {
                // TODO Auto-generated method stub
//                Toast.makeText(context,String.valueOf(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).get_db_id()), Toast.LENGTH_SHORT).show();
            	final CharSequence[] items = {
                        "Edit Group", "Send Message"
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
                builder.setTitle("Select");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection  
                    	switch (item) {
						case 0:	
//							Toast.makeText(AddressActivity.this, "Select Edit Group!", Toast.LENGTH_SHORT).show();
							
							final CharSequence[] groups = {
			                        "Group1", "Group2","Group3","Group4","Group5","Group6"
			                };
			                AlertDialog.Builder builder2 = new AlertDialog.Builder(AddressActivity.this);
			                builder2.setTitle("Select Group");
			                builder2.setItems(groups, new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int item) {
			                        // Do something with the selection  
			                    	switch (item) {
									case 0:
										Friend_Grouping change_group1=new Friend_Grouping(context, String.valueOf(current_user_id),String.valueOf(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).get_user_id()), "1", fg);
										change_group1.onRun();
//										Toast.makeText(AddressActivity.this, "Select Group1!", Toast.LENGTH_SHORT).show();										
										break;
									case 1:
										Friend_Grouping change_group2=new Friend_Grouping(context, String.valueOf(current_user_id),String.valueOf(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).get_user_id()), "2", fg);
										change_group2.onRun();
//										Toast.makeText(AddressActivity.this, "Select Group2!", Toast.LENGTH_SHORT).show();
										break;
									case 2:
										Friend_Grouping change_group3=new Friend_Grouping(context, String.valueOf(current_user_id),String.valueOf(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).get_user_id()), "3", fg);
										change_group3.onRun();
//										Toast.makeText(AddressActivity.this, "Select Group3!", Toast.LENGTH_SHORT).show();
										break;
									case 3:
										Friend_Grouping change_group4=new Friend_Grouping(context, String.valueOf(current_user_id),String.valueOf(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).get_user_id()), "4", fg);
										change_group4.onRun();
//										Toast.makeText(AddressActivity.this, "Select Group4!", Toast.LENGTH_SHORT).show();
										break;
									case 4:
										Friend_Grouping change_group5=new Friend_Grouping(context, String.valueOf(current_user_id),String.valueOf(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).get_user_id()), "5", fg);
										change_group5.onRun();
//										Toast.makeText(AddressActivity.this, "Select Group5!", Toast.LENGTH_SHORT).show();
										break;
									case 5:
										Friend_Grouping change_group6=new Friend_Grouping(context, String.valueOf(current_user_id),String.valueOf(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).get_user_id()), "6", fg);
										change_group6.onRun();
//										Toast.makeText(AddressActivity.this, "Select Group6!", Toast.LENGTH_SHORT).show();
										break;
									default:
										break;
									}
			                    }
			                });
			                AlertDialog alert = builder2.create();
			                alert.show();
			                
							break;
						case 1:
//							Toast.makeText(AddressActivity.this, "Select Send Message", Toast.LENGTH_SHORT).show();
							SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
							SharedPreferences.Editor editor = sharedPreferences.edit();		
							editor.putInt("friend_db_id", listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).get_db_id());
							editor.commit();
	                    	Intent intent=new Intent(AddressActivity.this, New_Message_Activity.class);	                    	
	                    	startActivity(intent, bndanimation_to_child);
	                    	finish();
							break;
						default:
							break;
						}
//                    	Intent intent=new Intent(AddressActivity.this, Modify_Friend_Category_Activity.class);
//                    	startActivity(intent, bndanimation_to_child);
//                    	finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            	return false;
            }
        });
	}
	private void prepareListData(ArrayList<User_Info> friends) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<User_Info>>();
        listHeaderImage=new ArrayList<Integer>();
        
        // Adding child data
        listDataHeader.add("Close Friend and Family.");
        listDataHeader.add("Good Friend and less acquainted Family");
        listDataHeader.add("Would like to be a friend");
        listDataHeader.add("Long lost Friend and Family Member");
        listDataHeader.add("Someone very special (Intimate)");
        listDataHeader.add("Would like to be a intimate friend");
        listDataHeader.add("Others");
 
        // Adding group image
        listHeaderImage.add(R.drawable.group_1);
        listHeaderImage.add(R.drawable.group_2);
        listHeaderImage.add(R.drawable.group_3);
        listHeaderImage.add(R.drawable.group_4);
        listHeaderImage.add(R.drawable.group_5);
        listHeaderImage.add(R.drawable.group_6);
        listHeaderImage.add(R.drawable.group_0);
        
        ArrayList<User_Info> group_one=new ArrayList<User_Info>();
        ArrayList<User_Info> group_two=new ArrayList<User_Info>();
        ArrayList<User_Info> group_three=new ArrayList<User_Info>();
        ArrayList<User_Info> group_four=new ArrayList<User_Info>();
        ArrayList<User_Info> group_five=new ArrayList<User_Info>();
        ArrayList<User_Info> group_six=new ArrayList<User_Info>();
        ArrayList<User_Info> gruop_seven=new ArrayList<User_Info>();
         
        for (int i = 0; i < friends.size(); i++) {
			switch (Integer.parseInt(friends.get(i).get_category())) {
			case 0:
				gruop_seven.add(friends.get(i));
				break;
			case 1:		
				group_one.add(friends.get(i));
				break;
			case 2:
				group_two.add(friends.get(i));
				break;
			case 3:
				group_three.add(friends.get(i));
				break;
			case 4:
				group_four.add(friends.get(i));
				break;
			case 5:
				group_five.add(friends.get(i));
				break;
			case 6:
				group_six.add(friends.get(i));
				break;
			default:
				break;
			}
		}
 
        listDataChild.put(listDataHeader.get(0), group_one); // Header, Child data
        listDataChild.put(listDataHeader.get(1), group_two);
        listDataChild.put(listDataHeader.get(2), group_three);
        listDataChild.put(listDataHeader.get(3), group_four);
        listDataChild.put(listDataHeader.get(4), group_five);
        listDataChild.put(listDataHeader.get(5), group_six);
        listDataChild.put(listDataHeader.get(6), gruop_seven);
    }
	@Override
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(AddressActivity.this,SecondActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
		return true;
	}
	@Override
	public void onBackPressed(){
		
	}
	@Override
	public void friend_grouping_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void friend_grouping_requestSuccess() {
		// TODO Auto-generated method stub
		Toast.makeText(context, "Group changed!", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(AddressActivity.this,SecondActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
	}
}
