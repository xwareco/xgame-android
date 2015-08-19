package uencom.xgame.speech;

import android.content.Intent;
import android.speech.RecognizerIntent;

public class SpeechRecognition {

	// Speech Recognition required data
	private static SpeechRecognition instance = new SpeechRecognition();
	private Intent listenIntent;
	private int maxWords;
	private String message;
	private String language;

	// Constructor (Singleton)
	private SpeechRecognition() {
	}

	// Methods
	public static SpeechRecognition getInstance() {
		return instance;
	}

	public void initRecognition(int max, String msg , String lang) {
		maxWords = max;
		message = msg;
		language = lang;
		listenIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		// indicate package
		listenIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
				getClass().getPackage().getName());
		// message to display while listening
		listenIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, message);
		// set speech model
		listenIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		// specify number of results to retrieve
		listenIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, maxWords);
		listenIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);

	}

	public Intent getListenIntent() {
		return listenIntent;
	}

}
