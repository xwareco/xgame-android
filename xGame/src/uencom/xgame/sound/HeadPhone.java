package uencom.xgame.sound;

import java.io.File;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;

public class HeadPhone implements OnErrorListener{

	// properties
	private float leftLevel;
	private float rightLevel;
	private MediaPlayer mediaPlayer;
	private Context context;
	private int currentPosition;
	private int currentPosition2;
	private int totalTime;

	// constructor
	public HeadPhone(Context ctx) {
		context = ctx;
		leftLevel = 0;
		rightLevel = 0;
		currentPosition = 0;
		currentPosition2 = 0;
		totalTime = 0;
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnErrorListener(this);
	}

	// methods
	public void play(String path, int flag) {

		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying())
				stopCurrentPlay();
		}

		mediaPlayer = MediaPlayer.create(context, Uri.fromFile(new File(path)));
		totalTime = mediaPlayer.getDuration();

		if (flag == 0) {
			playOnce();
		}

		else if (flag == 1) {
			playLoop();
		}

		else if (flag == 2) {
			playRightToLeft();
		}

		else if (flag == 3) {
			playLeftToRight();
		}

	}

	private void playLeftToRight() {
		float right = 0, left = 0.1f;
		mediaPlayer.setVolume(left, right);
		mediaPlayer.start();
		while (mediaPlayer.isPlaying() && currentPosition2 < totalTime) {
			if (currentPosition2 >= (totalTime * 1 / 6)
					&& currentPosition2 < (totalTime * 2 / 6)) {
				left = 0.3f;
				right = 0;
				mediaPlayer.setVolume(left, right);
			}
			if (currentPosition2 >= (totalTime * 2 / 6)
					&& currentPosition2 < (totalTime * 3 / 6)) {
				left = 0.8f;
				right = 0;
				mediaPlayer.setVolume(left, right);
			}
			if (currentPosition2 >= (totalTime * 3 / 6)
					&& currentPosition2 < (totalTime * 4 / 6)) {
				left = 1;
				right = 1;
				mediaPlayer.setVolume(left, right);
			}
			if (currentPosition2 >= (totalTime * 4 / 6)
					&& currentPosition2 < (totalTime * 5 / 6)) {
				left = 0;
				right = 0.8f;
				mediaPlayer.setVolume(left, right);
			}
			if (currentPosition2 >= (totalTime * 5 / 6)
					&& currentPosition2 < (totalTime * 0.90f)) {
				left = 0;
				right = 0.3f;
				mediaPlayer.setVolume(left, right);
			}
			if (currentPosition2 >= (totalTime * 0.90f)) {
				left = 0;
				right = 0.1f;
				mediaPlayer.setVolume(left, right);
			}
			currentPosition2 = mediaPlayer.getCurrentPosition();
		}
		currentPosition = 0;
		currentPosition2 = 0;
	}

	private void playRightToLeft() {
		float left = 0, right = 0.1f;
		mediaPlayer.setVolume(left, right);
		mediaPlayer.start();
		while (mediaPlayer.isPlaying() && currentPosition < totalTime) {
			if (currentPosition >= (totalTime * 1 / 6)
					&& currentPosition < (totalTime * 2 / 6)) {
				right = 0.3f;
				left = 0;
				mediaPlayer.setVolume(left, right);
			}
			if (currentPosition >= (totalTime * 2 / 6)
					&& currentPosition < (totalTime * 3 / 6)) {
				right = 0.8f;
				left = 0;
				mediaPlayer.setVolume(left, right);
			}
			if (currentPosition >= (totalTime * 3 / 6)
					&& currentPosition < (totalTime * 4 / 6)) {
				right = 1;
				left = 1;
				mediaPlayer.setVolume(left, right);
			}
			if (currentPosition >= (totalTime * 4 / 6)
					&& currentPosition < (totalTime * 5 / 6)) {
				right = 0;
				left = 0.8f;
				mediaPlayer.setVolume(left, right);
			}
			if (currentPosition >= (totalTime * 5 / 6)
					&& currentPosition < (totalTime * 0.90f)) {
				right = 0;
				left = 0.3f;
				mediaPlayer.setVolume(left, right);
			}
			if (currentPosition >= (totalTime * 0.90f)) {
				right = 0;
				left = 0.1f;
				mediaPlayer.setVolume(left, right);
			}
			currentPosition = mediaPlayer.getCurrentPosition();
		}
		currentPosition = 0;
		currentPosition2 = 0;
	}

	private void playLoop() {
		mediaPlayer.setLooping(true);
		mediaPlayer.setVolume(leftLevel, rightLevel);
		mediaPlayer.start();

	}

	private void playOnce() {
		mediaPlayer.setLooping(false);
		mediaPlayer.setVolume(leftLevel, rightLevel);
		mediaPlayer.start();

	}

	@SuppressWarnings("deprecation")
	public Boolean detectHeadPhones() {
		// MODIFY_AUDIO_SETTINGS must be added in manifest permissions
		AudioManager aManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		return aManager.isWiredHeadsetOn();
	}

	public void stopCurrentPlay() {
		mediaPlayer.stop();
	}

	public void setLeftLevel(float level) {
		leftLevel = level;
	}

	public void setRightLevel(float level) {
		rightLevel = level;
	}
	
	public boolean isPlaying()
	{
		return mediaPlayer.isPlaying();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		mp.reset();
		return false;
	}

}
