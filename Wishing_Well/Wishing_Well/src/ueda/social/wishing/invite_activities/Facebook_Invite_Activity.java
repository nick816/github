package ueda.social.wishing.invite_activities;

import android.app.Activity;


public class Facebook_Invite_Activity extends Activity {

//	private String name,email;
//	private LoginButton loginBtn;
//	private Button view_as_guest;	
//	private UiLifecycleHelper uiHelper;
//	private Bundle bndanimation_to_child,bndanimation_to_parent;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		uiHelper = new UiLifecycleHelper(this, statusCallback);
//		uiHelper.onCreate(savedInstanceState);
//
//		setContentView(R.layout.facebook_conact_activity);
//
//		// get the action bar
//		ActionBar actionBar = getActionBar();
//		// Enabling Back navigation on Action Bar icon
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		
//		bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
//		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();		
//		
////		view_as_guest=(Button)this.findViewById(R.id.view_as_guest);
//		loginBtn = (LoginButton) findViewById(R.id.authButton);
//		
//		loginBtn.setReadPermissions(Arrays.asList("user_location", "user_birthday", "user_likes,email"));
//		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
//			@Override
//			public void onUserInfoFetched(GraphUser user) {
//				if (user != null) {
//
//				} else {
//
//				}
//			}
//		});
//	}
//	
//	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
//		@SuppressWarnings("deprecation")
//		@Override
//		public void call(Session session, SessionState state,
//				Exception exception) {
//			
//			if (state.isOpened()) {
//				Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
//
//			        @Override
//			        public void onCompleted(GraphUser user, Response response) {
//			            if (user != null) {
//			                // Display the parsed user info			                
//			            	email="Email "+ user.asMap().get("email");
//			                name="Name "+user.asMap().get("name");
//			                Log.d("fdjagfjkdghjekfjk", name);
//			                Log.d("fdbghfueighreuihgreui",email);			                
//			            }
//			        }
//			    });
//				Log.d("MainActivity", "Facebook session opened.");
//			} else if (state.isClosed()) {
//				Log.d("MainActivity", "Facebook session closed.");
//			}
//		}		
//	};	
//	@Override
//	public void onResume() {
//		super.onResume();
//		uiHelper.onResume();
//	}
//
//	@Override
//	public void onPause() {
//		super.onPause();
//		uiHelper.onPause();
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		uiHelper.onDestroy();
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		uiHelper.onActivityResult(requestCode, resultCode, data);
//	}
//
//	@Override
//	public void onSaveInstanceState(Bundle savedState) {
//		super.onSaveInstanceState(savedState);
//		uiHelper.onSaveInstanceState(savedState);
//	}
//	@Override
//	public void onBackPressed(){
//		
//	}
}
