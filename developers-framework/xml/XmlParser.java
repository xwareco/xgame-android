package xware.xgame.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.content.Context;
import android.util.Xml;
import android.widget.LinearLayout;

public class XmlParser {

	private ArrayList<State> statesList;
	private ArrayList<Transition> transitionsList;
	public Map<String, String> environmentVariables;
	private Context ctx;
	private LinearLayout gamelayout;
	private State tempState;
	private Transition tempTransition;
	private String tagContents;
	private String name, path;

	public XmlParser(Context c, String p, LinearLayout lay) {
		ctx = c;
		path = p;
		gamelayout = lay;

	}

	public ArrayList<State> getStatesList() {
		return statesList;
	}

	public ArrayList<Transition> getTransitionsList() {
		return transitionsList;
	}

	public void parse() {
		XmlPullParser parser = Xml.newPullParser();
		InputStream in_s;
		try {
			in_s = new FileInputStream(path + "/App.xml");
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in_s, null);
			parsePrivately(parser);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void parsePrivately(XmlPullParser parser) {
		try {
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					name = parser.getName();
					processStart(name, parser);
					break;
				case XmlPullParser.TEXT:
					tagContents = parser.getText();
					break;
				case XmlPullParser.END_TAG:
					name = parser.getName();
					processEnd(name, tagContents, parser);
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processEnd(String name, String contents2, XmlPullParser parser) {

		if (name.equals("State") && tempState != null) {
			statesList.add(tempState);
		} else if (name.equals("Description") && tempState != null) {
			tempState.setDescribtion(contents2);
		} else if (name.equals("Text") && tempState != null) {
			tempState.setText(contents2);
		} else if (name.equals("Transition") && tempTransition != null) {
			transitionsList.add(tempTransition);
		}

	}

	private void processStart(String name, XmlPullParser parser) {
		if (name.equals("States"))
			statesList = new ArrayList<State>();
		else if (name.equals("State")) {
			tempState = new State(environmentVariables.get("APK_FILE_NAME"));
			tempState.setV(gamelayout);
			tempState.setCtx(ctx);
			tempState.setId(parser.getAttributeValue(0));
			tempState.setClassName(parser.getAttributeValue(1));
			tempState.setClassLoader(path);
		} else if (name.equals("Image")) {
			tempState.setImgPath(path + "/Images" + "/"
					+ parser.getAttributeValue(0));
		}

		else if (name.equals("SwipeRight")) {
			tempState.addToMap("SwipeRight", parser.getAttributeValue(0));
		} else if (name.equals("SwipeLeft")) {
			tempState.addToMap("SwipeLeft", parser.getAttributeValue(0));
		} else if (name.equals("SwipeUp")) {
			tempState.addToMap("SwipeUp", parser.getAttributeValue(0));
		} else if (name.equals("SwipeDown")) {
			tempState.addToMap("SwipeDown", parser.getAttributeValue(0));
		} else if (name.equals("SingleTap")) {
			tempState.addToMap("SingleTap", parser.getAttributeValue(0));
		} else if (name.equals("DoubleTap")) {
			tempState.addToMap("DoubleTap", parser.getAttributeValue(0));
		} else if (name.equals("LongPress")) {
			tempState.addToMap("LongPress", parser.getAttributeValue(0));
		} else if (name.equals("ZGoodRight")) {
			tempState.addToMap("ZGoodRight", parser.getAttributeValue(0));
		} else if (name.equals("ZGoodLeft")) {
			tempState.addToMap("ZGoodLeft", parser.getAttributeValue(0));
		} else if (name.equals("ZHugeRight")) {
			tempState.addToMap("ZHugeRight", parser.getAttributeValue(0));
		} else if (name.equals("ZHugeLeft")) {
			tempState.addToMap("ZHugeLeft", parser.getAttributeValue(0));
		} else if (name.equals("YGoodRight")) {
			tempState.addToMap("YGoodRight", parser.getAttributeValue(0));
		} else if (name.equals("YGoodLeft")) {
			tempState.addToMap("YGoodLeft", parser.getAttributeValue(0));
		} else if (name.equals("YHugeRight")) {
			tempState.addToMap("YHugeRight", parser.getAttributeValue(0));
		} else if (name.equals("YHugeLeft")) {
			tempState.addToMap("YHugeLeft", parser.getAttributeValue(0));
		} else if (name.equals("XGoodRight")) {
			tempState.addToMap("XGoodRight", parser.getAttributeValue(0));
		} else if (name.equals("XGoodLeft")) {
			tempState.addToMap("XGoodLeft", parser.getAttributeValue(0));
		} else if (name.equals("XHugeRight")) {
			tempState.addToMap("XHugeRight", parser.getAttributeValue(0));
		} else if (name.equals("XHugeLeft")) {
			tempState.addToMap("XHugeLeft", parser.getAttributeValue(0));
		}

		else if (name.equals("Transitions"))
			transitionsList = new ArrayList<Transition>();
		else if (name.equals("Transition")) {
			tempTransition = new Transition(
					environmentVariables.get("APK_FILE_NAME"));
			tempTransition.setId(parser.getAttributeValue(0));
			tempTransition.setClassName(parser.getAttributeValue(1));
			tempTransition.setCtx(ctx);
			tempTransition.setClassLoader(path);
			tempTransition.setFrom(parser.getAttributeValue(2));
			tempTransition.setTo(parser.getAttributeValue(3));
		}

		else if (name.equals("EnvironmentVariables"))
			environmentVariables = new HashMap<String, String>();
		else if (name.equals("EnvironmentVariable"))
			environmentVariables.put(parser.getAttributeValue(0),
					parser.getAttributeValue(1));

	}

}
