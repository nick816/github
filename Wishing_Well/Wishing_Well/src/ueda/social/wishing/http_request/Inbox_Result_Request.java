package ueda.social.wishing.http_request;

import java.util.ArrayList;

import ueda.social.wishing.model.Message;

public interface Inbox_Result_Request {	
	public void requestFailure(String errMsg);
	public void requestSuccess(ArrayList<Message> messages);
}
