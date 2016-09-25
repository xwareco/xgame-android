package xware.xgame.sensors;

/*import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
*/
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

public abstract class GPS implements LocationListener/*, ConnectionCallbacks,
		OnConnectionFailedListener*/ {

	// Variables
	private Location bestCurrentLocation;
	private LocationManager manager;
	private AdressResultReceiver mResultReceiver;
	private Context context;
	private static String currentAddress;
	//private GoogleApiClient mGoogleApiClient;

	public GPS(Context ctx) {
		manager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
				this);
		context = ctx;
		//buildGoogleApiClient();
	//	mGoogleApiClient.connect();

		mResultReceiver = new AdressResultReceiver(new Handler());
	}

	@Override
	public void onLocationChanged(Location arg0) {
		if (isBetterLocation(arg0, bestCurrentLocation)) {
			// detect best location the call the user code
			bestCurrentLocation = arg0;
			// Convert location data into readable address
			startAddressFetchService();
			onBestLocationFound(bestCurrentLocation);
		}

	}

	public void onBestLocationFound(Location bestCurrentLocation2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}
/*
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub

	}
*/
	private boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		int TWO_MINUTES = 1000 * 60 * 2;
		if (currentBestLocation == null) {
			return true;
		}
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;
		if (isSignificantlyNewer) {
			return true;
		} else if (isSignificantlyOlder) {
			return false;
		}
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	public void onGPSPause() {
		manager.removeUpdates(this);

	}

	public void onGPSResume() {
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
				this);

	}

	private void startAddressFetchService() {
		// just create an intent and start the service
		Intent intent = new Intent(context, AddressFetcher.class);
		// give the service the data it needs
		intent.putExtra(StaticConstants.RECEIVER, mResultReceiver);
		intent.putExtra(StaticConstants.LOCATION_DATA_EXTRA,
				bestCurrentLocation);
		context.startService(intent);
	}

	public static void setAdress(String str) {
		currentAddress = str;
	}

	public String getAddress() {
		return currentAddress;
	}

	/*private synchronized void buildGoogleApiClient() {
		// build google api connection to location services
		mGoogleApiClient = new GoogleApiClient.Builder(context)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
	}*/

}
