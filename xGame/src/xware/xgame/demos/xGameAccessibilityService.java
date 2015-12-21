package xware.xgame.demos;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;

public class xGameAccessibilityService extends AccessibilityService {
	@Override
	public void onCreate() {
		System.out.println("Service Created");
		super.onCreate();
	}

	@Override
	protected void onServiceConnected() {
		System.out.println("Service Connected!");
		AccessibilityServiceInfo info = new AccessibilityServiceInfo();
		info.eventTypes = AccessibilityEvent.TYPE_GESTURE_DETECTION_END
				| AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END | AccessibilityEvent.TYPE_VIEW_CLICKED;
		//info.packageNames = new String[] { "uencom.xgame.engine" };
		info.notificationTimeout = 100;
		info.flags = AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE;
		this.setServiceInfo(info);
		super.onServiceConnected();
	}

	@Override
	public void onAccessibilityEvent(AccessibilityEvent arg0) {
		int eventType = arg0.getEventType();
		switch (eventType) {
		case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
			System.out.println("Gesture Detected");
			break;

		case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
			System.out.println("Gesture Detected 2");
			break;
			
		case AccessibilityEvent.TYPE_VIEW_CLICKED:
			System.out.println("View Clicked!");
			break;
		}

	}

	@Override
	public void onInterrupt() {
		// TODO Auto-generated method stub

	}

}
