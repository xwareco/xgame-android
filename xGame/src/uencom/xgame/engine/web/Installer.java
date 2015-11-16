package uencom.xgame.engine.web;

import java.io.BufferedOutputStream;
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

		list.setEnabled(false);
		mNotifyManager = (NotificationManager) ctx
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(ctx);
		mBuilder.setContentTitle("Downloading " + gameName + " Game")
				.setContentText("Checking for any corrupt installation")
				.setSmallIcon(R.drawable.iconnot).setOngoing(true);
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
		mBuilder.setContentText("Download complete").setOngoing(false)
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
		Activity act = (Activity)ctx;
		act.overridePendingTransition(R.anim.transition5, R.anim.transition4);
		list.setEnabled(true);
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
			long start, end;
			start = System.currentTimeMillis();
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
			percent = 30 / count;
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
					byte[] b = new byte[1024];
					BufferedOutputStream bos = new BufferedOutputStream(fout,
							b.length);

					int size;

					while ((size = zin2.read(b, 0, b.length)) != -1) {
						bos.write(b, 0, size);
					}
					// Close up shop..
					bos.flush();
					bos.close();

					fout.flush();
					fout.close();
					zin2.closeEntry();
				}

			}
			zin2.close();
			end = System.currentTimeMillis();
			long dur = (end - start) / 10000;
			System.out.println(dur + " Second(s)");

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
