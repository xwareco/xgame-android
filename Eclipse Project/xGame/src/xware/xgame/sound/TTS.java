package xware.xgame.sound;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import xware.xgame.security.Mcrypt;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

public class TTS extends AsyncTask<String, Void, String> {
	private Context ctx;
	private String language;
	private long start, end;
	private double duration;

	public TTS(Context c, String lang) {
		ctx = c;
		language = lang;
	}

	public void speak(String word) throws IOException {
		Mcrypt securityLayer = new Mcrypt();
		String name = Mcrypt.bytesToHex(securityLayer.encrypt("johnMicheal")), Pass = Mcrypt
				.bytesToHex(securityLayer
						.encrypt("0266cd1aff008a26140e32e1b92c6f8b")), str = URLEncoder
				.encode(word, "UTF-8"), lang = Mcrypt.bytesToHex(securityLayer
				.encrypt(language));
		String url2, parameter = "str=" + str + "&lang=" + lang + "&username="
				+ name + "&password=" + Pass;
		url2 = "http://xgame.uencom.com/speech.php";
		URL url = null;
		try {
			url = new URL(url2);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		HttpURLConnection mUrlConnection = null;
		try {
			mUrlConnection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mUrlConnection.setRequestMethod("POST");
		mUrlConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		mUrlConnection.setRequestProperty("Content-Length",
				"" + Integer.toString(parameter.getBytes().length));
		mUrlConnection.setUseCaches(false);
		mUrlConnection.setDoInput(true);
		mUrlConnection.setDoOutput(true);
		SendRequest(mUrlConnection, parameter);
		String res = getResponse(mUrlConnection , securityLayer);
		res = res.substring(res.indexOf("h"), res.lastIndexOf("3") + 1);
		PlayFile(res);
	}

	private void PlayFile(String res) {
		try {
			MediaPlayer mp = new MediaPlayer();
			mp.setDataSource(ctx, Uri.parse(res));
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp.prepare();
			mp.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getResponse(HttpURLConnection mUrlConnection, Mcrypt securityLayer) {
		// Get Response
		String res = "";
		try {
		InputStream inStream = mUrlConnection.getInputStream();
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(
				inStream));
		String line;
		StringBuffer response = new StringBuffer();
		while ((line = buffReader.readLine()) != null) {
			response.append(line);
		}
		buffReader.close();
		 res = response.toString();
			res = new String(securityLayer.decrypt(response.toString().trim()));
			res = res.trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	private void SendRequest(HttpURLConnection mUrlConnection, String parameter) {
		DataOutputStream outStream;
		try {
			outStream = new DataOutputStream(mUrlConnection.getOutputStream());
			outStream.writeBytes(parameter);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onPreExecute() {
		start = System.nanoTime();
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			// Looper.prepare();

			speak(params[0]);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		end = System.nanoTime();
		duration = (end - start) / 1000000;
		Toast.makeText(ctx, "Latency Time: " + duration / 1000 + " Seconds",
				Toast.LENGTH_LONG).show();
		super.onPostExecute(result);
	}

}
