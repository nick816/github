package ueda.social.wishing.http_request;

public interface InterfaceFileDownloader {
	public void requestFileFailure();
	public void requestFileSuccess(String filename);
}
