package ueda.social.wishing.http_request;

import java.util.ArrayList;

import ueda.social.wishing.model.Wish_Info;

public interface Get_Friend_Wish_Result_Request {
	public void get_wish_requestFailure(String errMsg);
	public void get_wish_requestSuccess(ArrayList<Wish_Info> wish_infos);	
}
