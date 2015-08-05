package uencom.xgame.web;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

public class Installer extends AsyncTask<String, String, String> {

	private DownloadManager dm;
	private Context ctx;
	@SuppressWarnings("unused")
	private long downloadReference;
	public Installer(Context c)
	{
		ctx = c;
	}
	@Override
	protected void onPreExecute() {
		Toast.makeText(ctx, "Downloading Game", Toast.LENGTH_LONG).show();
		super.onPreExecute();
	}
	@Override
	protected String doInBackground(String... arg0) {
		download(arg0[0]);
		install(Environment.DIRECTORY_DOWNLOADS + "/game.zip");
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	
	private void download(String url)
	{
		dm = (DownloadManager)ctx.getSystemService(Context.DOWNLOAD_SERVICE);
		   Uri Download_Uri = Uri.parse(url);
		   DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
		    
		   //Restrict the types of networks over which this download may proceed.
		   request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
		   //Set whether this download may proceed over a roaming connection.
		   request.setAllowedOverRoaming(false);
		   //Set the title of this download, to be displayed in notifications (if enabled).
		   request.setTitle("Game Download");
		   //Set a description of this download, to be displayed in notifications (if enabled)
		   request.setDescription("Your game is being downloading now");
		   //Set the local destination for the downloaded file to a path within the application's external files directory
		   request.setDestinationInExternalFilesDir(ctx,Environment.DIRECTORY_DOWNLOADS,"game.zip");
		 
		   //Enqueue a new download and same the referenceId
		   downloadReference = dm.enqueue(request);
	}
	
	public void install(String path)
	{
		
	}

}
