package uencom.xgame.engine.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import uencom.xgame.engine.offlinexGameList;
import uencom.xgame.engine.onDeviceGameChecker;
import uencom.xgame.engine.views.GameView;
import uencom.xgame.xgame.R;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Installer extends AsyncTask<String, String, String> {

	private static Activity ctx;
	private static String unzipLocation;
	private String logoUrl;
	private int progress;
	private static String gameName;
	private static File f;
	private ListView list;
	NotificationManager mNotifyManager;
	NotificationCompat.Builder mBuilder;

	public Installer(Activity c, String unzip, String logoUrl, String name,
			ListView l) {
		ctx = c;
		unzipLocation = unzip;
		this.logoUrl = logoUrl;
		gameName = name;
		progress = 0;
		list = l;
	}

	@Override
	protected void onPreExecute() {

		mNotifyManager = (NotificationManager) ctx
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(ctx);
		mBuilder.setContentTitle("Downloading " + gameName + " Game")
				.setContentText("Checking for any corrupt installation")
				.setSmallIcon(R.drawable.iconnot);
		mBuilder.setProgress(100, progress, false);
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... arg0) {

		mNotifyManager.notify(1, mBuilder.build());
		checkForCorruptDownload();
		download(arg0[0]);
		install(Environment.getExternalStorageDirectory() + "/xGame/Games/"
				+ gameName + ".xgame");

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		mBuilder.setContentText("Finalizing..");
		if (f.exists())
			f.delete();

		// When the loop is finished, updates the notification
		mBuilder.setContentTitle(gameName);
		mBuilder.setContentText("Download complete")
		// Removes the progress bar
				.setProgress(0, 0, false);
		mNotifyManager.notify(1, mBuilder.build());
		@SuppressWarnings("unchecked")
		ArrayAdapter<Game> AD = (ArrayAdapter<uencom.xgame.engine.web.Game>) list
				.getAdapter();
		AD.notifyDataSetChanged();
		Intent I = new Intent(ctx.getApplicationContext(), GameView.class);
		I.putExtra("Logo", logoUrl);
		I.putExtra("Name", gameName);
		I.putExtra("Folder", unzipLocation + gameName);
		ctx.startActivity(I);
		super.onPostExecute(result);
	}

	public void download(String url) {

		InputStream input = null;
		OutputStream output = null;
		HttpURLConnection connection = null;
		try {
			URL downUrl = new URL(url);
			connection = (HttpURLConnection) downUrl.openConnection();
			connection.connect();
			// download the file
			progress += 10;
			mBuilder.setProgress(100, progress, false);
			mNotifyManager.notify(1, mBuilder.build());
			input = connection.getInputStream();
			f = new File(Environment.getExternalStorageDirectory()
					+ "/xGame/Games/" + gameName + ".xgame");
			if (!f.exists())
				f.createNewFile();

			output = new FileOutputStream(f);
			progress += 10;
			mBuilder.setProgress(100, progress, false);
			mNotifyManager.notify(1, mBuilder.build());
			byte data[] = new byte[4096];
			int count;
			while ((count = input.read(data)) != -1) {

				output.write(data, 0, count);
			}

			progress += 10;
			mBuilder.setProgress(100, progress, false);
			mNotifyManager.notify(1, mBuilder.build());
			mBuilder.setContentText("Installing..");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null)
					output.close();
				if (input != null)
					input.close();
			} catch (IOException ignored) {
			}

			if (connection != null)
				connection.disconnect();
		}

	}

	public void install(String path) {

		File f = new File(unzipLocation + gameName);

		// _dirChecker("");
		try {

			f.mkdir();
			FileInputStream fin = new FileInputStream(path);
			FileInputStream fin2 = new FileInputStream(path);
			final ZipInputStream zin = new ZipInputStream(fin);
			final ZipInputStream zin2 = new ZipInputStream(fin2);
			ZipEntry ze, ze2;
			int count = 0, percent = 0;
			while ((ze = zin.getNextEntry()) != null) {
				count++;
				System.out.println(ze.getName());
				zin.closeEntry();
			}
			zin.close();
			percent = count / 30;
			int cur = 0;
			while ((ze2 = zin2.getNextEntry()) != null) {
				System.out.println("FOKK MENI!!!");
				cur++;
				if (ze2.isDirectory()) {
					_dirChecker(ze2.getName());
				} else {

					FileOutputStream fout = new FileOutputStream(unzipLocation
							+ gameName + "/" + ze2.getName());
					progress += percent;
					mBuilder.setProgress(100, progress, false);
					mNotifyManager.notify(1, mBuilder.build());
					mBuilder.setContentText("Extracting data: " + cur + "/"
							+ count);
					for (int c = zin2.read(); c != -1; c = zin2.read()) {
						fout.write(c);
					}

					zin2.closeEntry();
					fout.close();
				}

			}
			zin2.close();

		} catch (Exception e) {
			Log.e("Decompress", "unzip", e);
		}
	}

	private static void _dirChecker(String dir) {
		File f = new File(unzipLocation + gameName + "/" + dir);

		if (!f.isDirectory()) {
			f.mkdirs();
		}
	}

	private void checkForCorruptDownload() {
		Looper.prepare();
		onDeviceGameChecker checkInstallations = new onDeviceGameChecker(ctx);
		offlinexGameList currentInstallations = checkInstallations
				.isOfflineGameExists(gameName);
		if (currentInstallations == null) {

			mBuilder.setContentText("A corrupted download found for this game and deleted successfully");

		}
		progress += 30;
		mBuilder.setProgress(100, progress, false);
		mBuilder.setContentText("Connecting to xGame server");
		mNotifyManager.notify(1, mBuilder.build());

	}

}
