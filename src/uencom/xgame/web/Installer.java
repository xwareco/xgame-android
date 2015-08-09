package uencom.xgame.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import uencom.xgame.engine.GameView;
import uencom.xgame.xgame.R;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Installer extends AsyncTask<String, String, String> {

	private DownloadManager dm;
	private Activity ctx;
	private String location;
	private TextView status;
	private String logoUrl;
	private String gameName;
	@SuppressWarnings("unused")
	private long downloadReference;
	public Installer(Activity c , String path , String logoUrl , String name)
	{
		ctx = c;
		this.location = path;
		status = (TextView)c.findViewById(R.id.textView2);
		this.logoUrl = logoUrl;
	}
	@Override
	protected void onPreExecute() {
		Toast.makeText(ctx, "Downloading Game", Toast.LENGTH_LONG).show();
		super.onPreExecute();
	}
	@Override
	protected String doInBackground(String... arg0) {
		//download(arg0[0]);
		install(Environment.getExternalStorageDirectory() + "/catch_the_peeb.xgame");
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		 ctx.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					 status.setText("Installation complete");
					 Intent I = new Intent(ctx.getApplicationContext() , GameView.class);
					 I.putExtra("Logo", logoUrl);
					 I.putExtra("Name", gameName);
					 I.putExtra("Folder", location);
					 ctx.finish();
					 ctx.startActivity(I);
					
				}
			});  
		super.onPostExecute(result);
	}
	
	private void download(String url)
	{
		ctx.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				status.setText("Download started");
				
			}
		});
		
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
		   ctx.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					status.setText("Download completed");
					
				}
			});
		   
	}
	
	public void install(String path)
	{
		 ctx.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					status.setText("Installing..");
					
				}
			});
		
		//_dirChecker("");
		 try  { 
		      FileInputStream fin = new FileInputStream(path); 
		      ZipInputStream zin = new ZipInputStream(fin); 
		      ZipEntry ze = null; 
		      while ((ze = zin.getNextEntry()) != null) { 

		       
		 
		        if(ze.isDirectory()) { 
		          _dirChecker(ze.getName()); 
		        } else { 
		          FileOutputStream fout = new FileOutputStream(this.location + ze.getName()); 
		          for (int c = zin.read(); c != -1; c = zin.read()) { 
		            fout.write(c); 
		          } 
		 
		          zin.closeEntry(); 
		          fout.close(); 
		        } 
		         
		      } 
		      zin.close(); 
		    } catch(Exception e) { 
		      Log.e("Decompress", "unzip", e); 
		    } 
	}
	
	private void _dirChecker(String dir) { 
	    File f = new File(location + dir); 
	 
	    if(!f.isDirectory()) { 
	      f.mkdirs(); 
	    } 
	  } 

}
