package ueda.social.wishing.http_request;

import java.util.ArrayList;

import ueda.social.wishing.model.User_Info;

public interface Search_Result_Request {
	public void requestFailure(String errMsg);
	public void requestSuccess(ArrayList<User_Info> user_infos);	
}
