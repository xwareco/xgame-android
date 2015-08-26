package uencom.xgame.sensors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

public class AddressFetcher extends IntentService {

	private ResultReceiver mReceiver;
	private List<Address> addresses;

	public AddressFetcher() {
		super("ADDRESS_FETCHER");
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
	@Override
	protected void onHandleIntent(Intent intent) {

		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		String errorMessage = "";
		// Get the location passed to this service through an extra.
		Location location = intent
				.getParcelableExtra(StaticConstants.LOCATION_DATA_EXTRA);
		mReceiver = intent.getParcelableExtra(StaticConstants.RECEIVER);
		try {
			addresses = geocoder.getFromLocation(location.getLatitude(),
					location.getLongitude(),
					// In this sample, get just a single address.
					1);
		} catch (IOException ioException) {
			// Catch network or other I/O problems.
			errorMessage = "Network problem";

		} catch (IllegalArgumentException illegalArgumentException) {
			// Catch invalid latitude or longitude values.
			errorMessage = "Invalid latitude or longitude";

		}

		handleNoAddressFound(errorMessage);
	}

	@SuppressLint("NewApi") private void handleNoAddressFound(String errorMessage) {
		// Handle case where no address was found.
		if (addresses == null || addresses.size() == 0) {
			if (errorMessage.isEmpty()) {
				errorMessage = "Sorry, no address found for this location";

			}
			deliverResultToReceiver(StaticConstants.FAILURE_RESULT,
					errorMessage);
		} else {
			Address address = addresses.get(0);
			ArrayList<String> addressFragments = new ArrayList<String>();

			// Fetch the address lines using getAddressLine,
			// join them, and send them to the thread.
			for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
				addressFragments.add(address.getAddressLine(i));
			}

			deliverResultToReceiver(StaticConstants.SUCCESS_RESULT,
					TextUtils.join(System.getProperty("line.separator"),
							addressFragments));
		}

	}

	private void deliverResultToReceiver(int resultCode, String message) {
		// deliver the results
		Bundle bundle = new Bundle();
		bundle.putString(StaticConstants.RESULT_DATA_KEY, message);
		mReceiver.send(resultCode, bundle);

	}

}
