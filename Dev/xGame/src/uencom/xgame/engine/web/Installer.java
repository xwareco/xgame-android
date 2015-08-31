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
import uencom.xgame.xgame.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Installer extends AsyncTask<String, String, String> {

	private Activity ctx;
	private String unzipLocation;
	private TextView status;
	private String logoUrl;
	private String gameName;
	private File f;

	public Installer(Activity c, String unzip, String logoUrl, String name) {
		ctx = c;
		this.unzipLocation = unzip;
		status = (TextView) c.findViewById(R.id.textView2);
		this.logoUrl = logoUrl;
	}

	@Override
	protected void onPreExecute() {
		Toast.makeText(ctx, "Downloading Game", Toast.LENGTH_LONG).show();
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... arg0) {

		download(arg0[0]);
		install(Environment.getExternalStorageDirectory() + "/xGame/Games/"
				+ "null");
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		ctx.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (f.exists())
					f.delete();
				status.setText("Installation complete");
				Intent I = new Intent(ctx.getApplicationContext(),
						GameView.class);
				I.putExtra("Logo", logoUrl);
				I.putExtra("Name", gameName);
				I.putExtra("Folder", unzipLocation
						+ ctx.getIntent().getStringExtra("gamename"));
				ctx.finish();
				ctx.startActivity(I);

			}
		});
		super.onPostExecute(result);
	}

	private void download(String url) {
		ctx.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				status.setText("Download started");

			}
		});

		InputStream input = null;
		OutputStream output = null;
		HttpURLConnection connection = null;
		try {
			URL downUrl = new URL(url);
			connection = (HttpURLConnection) downUrl.openConnection();
			connection.connect();

			// download the file
			input = connection.getInputStream();
			f = new File(Environment.getExternalStorageDirectory()
					+ "/xGame/Games/"
					+ ctx.getIntent().getStringExtra("gamename" + ".xgame"));
			if (!f.exists())
				f.createNewFile();
			output = new FileOutputStream(f);

			byte data[] = new byte[4096];
			int count;
			while ((count = input.read(data)) != -1) {

				output.write(data, 0, count);
			}

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
		ctx.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				status.setText("Download completed");

			}
		});

	}

	public void install(String path) {
		ctx.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				status.setText("Installing..");

			}
		});
		File f = new File(unzipLocation
				+ ctx.getIntent().getStringExtra("gamename"));

		// _dirChecker("");
		try {
			f.mkdir();
			FileInputStream fin = new FileInputStream(path);
			ZipInputStream zin = new ZipInputStream(fin);
			ZipEntry ze = null;
			while ((ze = zin.getNextEntry()) != null) {

				if (ze.isDirectory()) {
					_dirChecker(ze.getName());
				} else {
					FileOutputStream fout = new FileOutputStream(
							this.unzipLocation + ctx.getIntent().getStringExtra("gamename") + "/" + ze.getName());
					for (int c = zin.read(); c != -1; c = zin.read()) {
						fout.write(c);
					}

					zin.closeEntry();
					fout.close();
				}

			}
			zin.close();
		} catch (Exception e) {
			Log.e("Decompress", "unzip", e);
		}
	}

	private void _dirChecker(String dir) {
		File f = new File(unzipLocation + ctx.getIntent().getStringExtra("gamename") + "/" +  dir);

		if (!f.isDirectory()) {
			f.mkdirs();
		}
	}

}
