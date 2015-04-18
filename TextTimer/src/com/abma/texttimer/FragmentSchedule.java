package com.abma.texttimer;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.abma.texttimer.additional.ChangeFragmentListener;
import com.abma.texttimer.schedule.ListViewSchedule;
import com.abma.texttimer.schedule.ListViewScheduleAll;
import com.abma.texttimer.schedule.ListViewScheduleSent;
import com.abma.texttimer.schedule.ListViewScheduled;
import com.abma.texttimer.schedule.Schedule;
import com.abma.texttimer.utility.NotificationReceiver;

@SuppressLint({ "ResourceAsColor", "ValidFragment" }) 
public class FragmentSchedule extends Fragment {
	
	public static final int ALL = 0;
	public static final int SCHEDULED = 1;
	public static final int SENT = 2;
	
	private static final int TAB_COUNT = 3;
	
	public static final String CURRENT_TAB = "current_tab";
	
	public static int _mCurrentTab;
	public static ListViewSchedule _mCurrentListView;
	

	View view = null;
	
	Button btnAll = null;
	Button btnScheduled = null;
	Button btnSent = null;
	
	ViewPager layoutScheduleViewPager = null;
	ViewPagerAdapter adapter = null;
	
	public static ListViewScheduleAll _mAllListView = null;
	public static ListViewScheduled _mScheduledListView = null;
	public static ListViewScheduleSent _mSentListView = null;
	
	RelativeLayout btnCompose = null;
	
	static FragmentSchedule _mInstance; 
	public FragmentSchedule() {
		
	}

	public static FragmentSchedule getInstance() {
		if ( _mInstance == null ) {
			_mInstance = new FragmentSchedule();
		}
		return _mInstance;
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule, container, false);

        System.out.println("FragmentSchedule onCreateView");
        
    	btnAll = (Button)view.findViewById(R.id.btnHome);
    	btnScheduled = (Button)view.findViewById(R.id.btnScheduled);
    	btnSent = (Button) view.findViewById(R.id.btnSent);
    	btnCompose = (RelativeLayout)view.findViewById(R.id.layoutButtonSave);
        
        btnAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layoutScheduleViewPager.setCurrentItem(ALL);
			}
		});
        
        btnScheduled.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layoutScheduleViewPager.setCurrentItem(SCHEDULED);
			}
		});
        
        btnSent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layoutScheduleViewPager.setCurrentItem(SENT);
			}
		});
        
        btnCompose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentCompose.setState(null);
				((ChangeFragmentListener)getActivity()).changeFragment(MainActivity.COMPOSE);
			}
		});
    
        layoutScheduleViewPager = (ViewPager)view.findViewById(R.id.layoutScheduleViewPager);
    	adapter = new ViewPagerAdapter();
        layoutScheduleViewPager.setAdapter(adapter);
        layoutScheduleViewPager.setOffscreenPageLimit(TAB_COUNT);
    
        layoutScheduleViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				setTab(arg0);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {	
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
        
    	onRestoreInstanceState(savedInstanceState);
    	refreshSchedule();
        
        this.setRetainInstance(true);
        
        return view;
    }

	@Override
	public void onSaveInstanceState(Bundle outState) {
		System.out.println("FragmentSchedule onSaveInstanceState");
		outState.putInt(CURRENT_TAB, _mCurrentTab);
    	super.onSaveInstanceState(outState);
    }
	
	public void onRestoreInstanceState(Bundle savedInstanceState) {
    	System.out.println("FragmentSchedule onRestoreInstanceState");
    	 if ( NotificationReceiver.hasNotification == true )
         	_mCurrentTab = SENT;
     	else if ( savedInstanceState == null )
    		_mCurrentTab = SCHEDULED;
    	else 
    		_mCurrentTab = savedInstanceState.getInt(CURRENT_TAB);
    	layoutScheduleViewPager.setCurrentItem(_mCurrentTab);
    	setTab(_mCurrentTab);
    	NotificationReceiver.hasNotification = false;
    }
	
	@Override
	public void onResume() {
		System.out.println("Fragment OnResume");
		super.onResume();
		if ( NotificationReceiver.hasNotification == true )
			changeTab(SENT);
		NotificationReceiver.hasNotification = false;
	}
	
	public void changeTab(int action) {
		layoutScheduleViewPager.setCurrentItem(action);
	}
	
	private void setTab(int tab) {		
		_mCurrentTab = tab;
		switch (tab) {
		case ALL:
			setTabButton(btnAll, true);
			setTabButton(btnScheduled, false);
			setTabButton(btnSent, false);
			break;
		case SCHEDULED:
			setTabButton(btnAll, false);
			setTabButton(btnScheduled, true);
			setTabButton(btnSent, false);
			break;
		case SENT:
			setTabButton(btnAll, false);
			setTabButton(btnScheduled, false);
			setTabButton(btnSent, true);
			break;
		}
	}
	
	@SuppressLint("NewApi") 
	private void setTabButton(Button button, boolean isActivate) {
		button.setActivated(isActivate);
		int color;
		if ( isActivate == true ) {
			color = this.getActivity().getResources().getColor(R.color.selected_tab_bar_label_color);
		}
		else {
			color = this.getActivity().getResources().getColor(R.color.tab_bar_label_color);
		}
		button.setTextColor(color);
	}
	
	public void setSentTab() {
		layoutScheduleViewPager.setCurrentItem(SENT);
	}
	
	public ArrayList<Schedule> getScheduledList() {
		if ( _mScheduledListView != null )
			return _mScheduledListView.getScheduelList();
		return null;
	}
	
	public void addSchedule(Schedule schedule, boolean isUpdate) {
		if ( _mScheduledListView != null ) {
			if ( schedule.isSent() == false )
				_mScheduledListView.addSchedule(schedule, isUpdate);
			else 
				_mSentListView.addSchedule(schedule, isUpdate);
		}
	}
	public void updateSchedule(Schedule schedule, boolean isUpdate) {
		if ( _mScheduledListView != null ) {
			if ( schedule.isSent() == false )
				_mScheduledListView.updateSchedule(schedule, isUpdate);
			else 
				_mSentListView.updateSchedule(schedule, isUpdate);
		}
	}
	public void removeSchedule(Schedule schedule, boolean isUpdate){
		if ( _mScheduledListView != null ) {
			if ( schedule.isSent() == false ) {
				_mScheduledListView.removeSchedule(schedule, isUpdate);
			}
			else { 
				_mSentListView.removeSchedule(schedule, isUpdate);
				_mSentListView.refresh();
			}
		}
	}
	public void changeSchedule(Schedule schedule){
		if ( _mScheduledListView != null ) {
			_mSentListView.removeSchedule(schedule, false);
			_mScheduledListView.addSchedule(schedule, false);
			_mScheduledListView.updateSchedule(schedule, true);
		}
	}
	public void refreshSchedule() {
		if ( _mScheduledListView != null )
			_mScheduledListView.refresh();
		if ( _mSentListView != null )
			_mSentListView.refresh();
		if ( _mAllListView != null )
			_mAllListView.refresh();
	}
	
	private class ViewPagerAdapter  extends PagerAdapter {
	    
		@Override
	    public int getCount() {
	        return TAB_COUNT;
	    }

	    @Override
	    public Object instantiateItem(View container, int position) {
	    	switch (position) {
			case ALL:
				if ( _mAllListView == null ) {
					_mAllListView = new ListViewScheduleAll(view.getContext());
				}
				_mCurrentListView = _mAllListView;
				break;
			case SCHEDULED:
				if ( _mScheduledListView == null ) {
					_mScheduledListView = new ListViewScheduled(view.getContext());
				}
				_mCurrentListView = _mScheduledListView;
				break;
			case SENT:
				if ( _mSentListView == null ) {
					_mSentListView = new ListViewScheduleSent(view.getContext());
				}
				_mCurrentListView = _mSentListView;
				break;
			}
	    	
	    	_mCurrentTab = position;
	    	if ( _mCurrentListView.getParent() != null ) {
	    		View parent = (View)_mCurrentListView.getParent();
	    		((ViewPager) container).addView(parent, 0);
	    		return parent;
	    	}
	    	LinearLayout v = new LinearLayout(FragmentSchedule.this.getActivity());
    		_mCurrentListView.init();
    		v.addView(_mCurrentListView);
    		((ViewPager) container).addView(v, 0);

			return v;
	    }

	    @Override
	    public void destroyItem(View arg0, int arg1, Object arg2) {
	        ((ViewPager) arg0).removeView((View) arg2);
	    }

	    @Override
	    public boolean isViewFromObject(View arg0, Object arg1) {
	        return arg0 == ((View) arg1);
	    }

	    @Override
	    public Parcelable saveState() {
	        return null;
	    }
	}
}
