package com.abma.texttimer;

import java.util.Stack;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.abma.texttimer.additional.ChangeFragmentListener;
import com.abma.texttimer.utility.NotificationReceiver;

import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends FragmentActivity implements ChangeFragmentListener {

	public static final String APP_NAME = "Text Timer";
	public static final String SCHEDULE = "00";
	public static final String COMPOSE = "01";
	public static final String HOME = "10";
	public static final String HOW_TO = "20";
	public static final String ABOUT_US = "30";
	public static final String CONTACT_US = "40";
	public static final String LEGALS_TERMS = "50";
	public static final String SHARE_THIS_APP = "60";
	
	public static final String CURRENT_ACTION = "current_action";
	
	public static String _mCurrentAction;
	private static Fragment _mCurrentFragment;
	
	public static boolean isRunning = false;
	public static boolean isBackground = true;
	
	public static MainActivity _mainActivity = null;
	
	Button btnHome = null;
	Button btnBack = null;
	
	Button btnCompose = null;
	
	// This is needed in order to manage the fragments.
    public static Stack<String> _mFragmentStack;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        System.out.println("MainActivity onCreate");
        
        isRunning = true;
        
        btnHome = (Button)findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					changeFragment(HOME);
			}
		});
        
        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
        
        if (savedInstanceState == null) {
        	System.out.println("New Action");
        }
        else {
        	_mCurrentAction = savedInstanceState.getString(CURRENT_ACTION);
        }
    	
    	_mainActivity = this;
    }
    
    private void init() {
    	if ( _mCurrentAction == null 
    			|| _mCurrentAction.isEmpty() 
    			|| _mFragmentStack == null 
    			|| _mFragmentStack.size() == 0 ) {
    		_mCurrentAction = SCHEDULE;
    		System.out.println("INIT");
	    	_mFragmentStack = null;
			if ( _mFragmentStack == null )
				_mFragmentStack = new Stack<String>();
	    	_mCurrentFragment = getActionFragment(_mCurrentAction);
	    	String tag = _mCurrentFragment.toString();
	    	if ( _mFragmentStack.search(tag) == -1 ) {
	            _mFragmentStack.add(tag);
	            System.out.println("Start Activity");
	            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	            System.out.println("Tag Name : " + tag);
	            transaction.add(R.id.container, _mCurrentFragment, tag);
	            transaction.addToBackStack(tag);
	            transaction.commit();
	    	}
    	}
    	else if ( NotificationReceiver.hasNotification == true && _mCurrentAction != SCHEDULE ) {
			onBackPressed();
		}
    	showMenuButton(_mCurrentAction);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	System.out.println("MainActivity onSaveInstanceState");
    	outState.putString(CURRENT_ACTION, _mCurrentAction);
    	super.onSaveInstanceState(outState);
    }
    
    @Override
    public void onResume() {
    	System.out.println("OnResume");
		super.onResume();
		isBackground = false;
		init();
    }

    @Override
    protected void onPause() {
      isBackground = true;
      super.onPause();
    } 
    
    @Override
    public void onStart() {
      super.onStart();
      EasyTracker.getInstance(this).activityStart(this);  // Add this method.
    }

    @Override
    public void onStop() {
      super.onStop();
      EasyTracker.getInstance(this).activityStop(this);  // Add this method.
    }
    
    private void hiddenKeyboard() {
    	View focus = getCurrentFocus();
        if (focus != null) {
        	InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(focus.getWindowToken(), 0);
        }
    }
    
    private void showMenuButton(String action) {
    	hiddenKeyboard();
    	if ( action == SCHEDULE ) {
    		btnBack.setVisibility(View.GONE);
    		btnHome.setVisibility(View.VISIBLE);
    	}
    	else {
    		btnBack.setVisibility(View.VISIBLE);
    		btnHome.setVisibility(View.GONE);
    	}
    }
    
    private Fragment getActionFragment(String action) {
    	_mCurrentAction = action;
    	if ( action.equals(SCHEDULE) ) {
    		Fragment fragment = getSupportFragmentManager().findFragmentByTag(SCHEDULE);
    		if ( fragment != null ) {
    			return fragment;
    		}
    		else {
    			return FragmentSchedule.getInstance();
    		}
    	}
    	else if ( action.equals(COMPOSE) ) {
    		Fragment fragment = getSupportFragmentManager().findFragmentByTag(COMPOSE);
    		if ( fragment != null ) {
    			return fragment;
    		}
    		else {
    			return new FragmentCompose();
    		}
    	}
    	else if ( action.equals(HOME) ) {
    		Fragment fragment = getSupportFragmentManager().findFragmentByTag(HOME);
    		if ( fragment != null ) {
    			return fragment;
    		}
    		else {
    			return new FragmentHome();
    		}
    	}
    	else if ( action.equals(HOW_TO) ) {
    		Fragment fragment = getSupportFragmentManager().findFragmentByTag(HOW_TO);
    		if ( fragment != null ) {
    			return fragment;
    		}
    		else {
    			return new FragmentHowTos();
    		}
    	}
    	else if ( action.equals(ABOUT_US) ) {
    		Fragment fragment = getSupportFragmentManager().findFragmentByTag(ABOUT_US);
    		if ( fragment != null ) {
    			return fragment;
    		}
    		else {
    			return new FragmentAboutUS();
    		}
    	}
    	else if ( action.equals(CONTACT_US) ) {
    		Fragment fragment = getSupportFragmentManager().findFragmentByTag(CONTACT_US);
    		if ( fragment != null ) {
    			return fragment;
    		}
    		else {
    			return new FragmentContactUS();
    		}
    	}
    	return null;
    }
    
    // Notice how this class implements OnFragment1ClickkedListener? Well this causes us to 
    // implement the abstract method onFragment1Clicked() from Fragment1.java. 
    public void changeFragment(String action) {
    	if ( _mCurrentAction.equals(action) )
    		return;
    	_mCurrentAction = action;
    	
    	Fragment newFragment = getActionFragment(action);
        Bundle args = new Bundle();
        // by doing newFragment.toString(), I am taking a unique identified of that object.
        String tag = newFragment.toString();
        newFragment.setArguments(args);
 
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // setting the animations here. the fade_in animation for prev fragment and fade_out for next fragment
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        // I find what the current fragment from the stack is, take it and hide it
        // using transaction.hide(currentFragment);
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(_mFragmentStack.peek());
        transaction.hide(currentFragment);
         
        transaction.add(R.id.container, newFragment, tag);
        // This is important, we must addToBackStack so we can pull it out later.
        transaction.addToBackStack(tag);
        // Instead of using replace we use add. Why? If we use replace, then the previous
        // fragment will always have to be re-created. What if you don't want to do that. In
        // my case, I didn't want it to be re-created all the time because I had a position
        // set and by re-creating it, I would have lost the position or had to use static flag.
        _mFragmentStack.add(tag);
 
        _mCurrentFragment = newFragment;
        
        transaction.commit();
        showMenuButton(_mCurrentAction);
    }
    
    @Override
    public void onBackPressed(){
        // from the stack we can get the latest fragment
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(_mFragmentStack.peek());
        // If its an instance of Fragment1 I don't want to finish my activity, so I launch a Toast instead.
        if (fragment instanceof FragmentSchedule){
            _mCurrentAction = SCHEDULE;
        }
        else{
            // Remove the fragment
            removeFragment();
            super.onBackPressed();
        }
    }
    private void removeFragment(){
        // remove the current fragment from the stack.
    	_mFragmentStack.pop();
    	
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // get fragment that is to be shown (in our case fragment1).
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(_mFragmentStack.peek());
        // This time I set an animation with no fade in, so the user doesn't wait for the animation in back press
        transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
        // We must use the show() method.
        transaction.show(fragment);
        _mCurrentFragment = fragment;
        
        setCurrentAction(fragment);
        transaction.commit();
        showMenuButton(_mCurrentAction);
    }
    
    private void setCurrentAction(Fragment fragment) {
    	if ( fragment instanceof FragmentSchedule )
    		_mCurrentAction = SCHEDULE;
        else if ( fragment instanceof FragmentHome ) 
        	_mCurrentAction = HOME;
        else if ( fragment instanceof FragmentCompose ) 
        	_mCurrentAction = COMPOSE;
        else if ( fragment instanceof FragmentHowTos ) 
        	_mCurrentAction = HOW_TO;
    }
    
    @Override
    public void onDestroy(){
    	isRunning = false;
    	_mainActivity = null;
    	super.onDestroy();
    }
}
