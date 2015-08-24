package uencom.xgame.sensors;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class AdressResultReceiver extends ResultReceiver {

	// User address
	private String addressOrError = "";

	public AdressResultReceiver(Handler handler) {
		super(handler);

	}

	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {

		System.out.println("Entered");
		// Display the address string
		// or an error message sent from the intent service.
		addressOrError = resultData.getString(StaticConstants.RESULT_DATA_KEY);
		if (resultCode == StaticConstants.SUCCESS_RESULT)
			GPS.setAdress(addressOrError);
		else
			GPS.setAdress("Failure");
	}
}
