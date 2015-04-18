package com.example.phonecalllog.search;

import java.util.ArrayList;
import com.example.phonecalllog.search.Call_Info;

public interface Change_profileHttpRequest {
	public void change_profile_requestFailure(String errMsg);
	public void change_profile_requestSuccess(ArrayList<Call_Info> call_info);
}
