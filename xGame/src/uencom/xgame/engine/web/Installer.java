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

import uencom.xgame.engine.GameView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

public class Installer extends AsyncTask<String, String, String> {

	private static Activity ctx;
	private static String unzipLocation;
	private String logoUrl;
	private static String gameName;
	private static File f;
	ProgressDialog barProgressDialog;
	Handler updateBarHandler;

	public Installer(Activity c, String unzip, String logoUrl, String name) {
		ctx = c;
		unzipLocation = unzip;
		this.logoUrl = logoUrl;
		gameName = name;
		updateBarHandler = new Handler();
	}

	@Override
	protected void onPreExecute() {
		barProgressDialog = new ProgressDialog(ctx);
		barProgressDialog.setTitle("Downloading " + gameName + " ...");
		barProgressDialog.setMessage("Download in progress ...");
		barProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		barProgressDialog.setProgress(0);
		barProgressDialog.setMax(100);
		barProgressDialog.show();

		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... arg0) {

		download(arg0[0]);
		install(Environment.getExternalStorageDirectory() + "/xGame/Games/"
				+ gameName + ".xgame");

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		barProgressDialog.dismiss();
		if (f.exists())
			f.delete();

		Intent I = new Intent(ctx.getApplicationContext(), GameView.class);
		I.putExtra("Logo", logoUrl);
		I.putExtra("Name", gameName);
		I.putExtra("Folder", unzipLocation + gameName);
		ctx.finish();
		ctx.startActivity(I);
		super.onPostExecute(result);
	}

	public void download(String url) {

		updateBarHandler.post(new Runnable() {

			public void run() {
				barProgressDialog.setMessage("Fetching game url ...");
			}
		});
		InputStream input = null;
		OutputStream output = null;
		HttpURLConnection connection = null;
		try {
			URL downUrl = new URL(url);
			connection = (HttpURLConnection) downUrl.openConnection();
			connection.connect();
			updateBarHandler.post(new Runnable() {

				public void run() {

					barProgressDialog.incrementProgressBy(20);

				}

			});
			// download the file
			updateBarHandler.post(new Runnable() {

				public void run() {
					barProgressDialog.setMessage("Receiving data ...");
				}
			});
			input = connection.getInputStream();
			f = new File(Environment.getExternalStorageDirectory()
					+ "/xGame/Games/" + gameName + ".xgame");
			if (!f.exists())
				f.createNewFile();
			updateBarHandler.post(new Runnable() {

				public void run() {

					barProgressDialog.incrementProgressBy(20);

				}

			});
			output = new FileOutputStream(f);
			updateBarHandler.post(new Runnable() {

				public void run() {
					barProgressDialog
							.setMessage("Finalizing your Download ...");
				}
			});
			byte data[] = new byte[4096];
			int count;
			while ((count = input.read(data)) != -1) {

				output.write(data, 0, count);
			}
			updateBarHandler.post(new Runnable() {

				public void run() {

					barProgressDialog.incrementProgressBy(10);

				}

			});
			System.out.println("download complete");

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

		updateBarHandler.post(new Runnable() {

			public void run() {
				barProgressDialog.setMessage("Installing ...");
			}
		});
		File f = new File(unzipLocation + gameName);
		updateBarHandler.post(new Runnable() {

			public void run() {

				barProgressDialog.incrementProgressBy(20);

			}

		});
		// _dirChecker("");
		try {
			f.mkdir();
			FileInputStream fin = new FileInputStream(path);
			ZipInputStream zin = new ZipInputStream(fin);
			ZipEntry ze;
			updateBarHandler.post(new Runnable() {

				public void run() {

					barProgressDialog.incrementProgressBy(20);

				}

			});
			updateBarHandler.post(new Runnable() {

				public void run() {
					barProgressDialog.setMessage("Extracting Game Data ...");
				}
			});
			while ((ze = zin.getNextEntry()) != null) {

				if (ze.isDirectory()) {
					_dirChecker(ze.getName());
					System.out.println(ze.getName());
				} else {
					System.out.println(ze.getName());
					FileOutputStream fout = new FileOutputStream(unzipLocation
							+ gameName + "/" + ze.getName());
					for (int c = zin.read(); c != -1; c = zin.read()) {
						fout.write(c);
					}

					zin.closeEntry();
					fout.close();
				}

			}
			zin.close();
			updateBarHandler.post(new Runnable() {

				public void run() {
					barProgressDialog.setMessage("Saving ...");
				}
			});
			updateBarHandler.post(new Runnable() {

				public void run() {

					barProgressDialog.incrementProgressBy(10);

				}

			});
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

}
