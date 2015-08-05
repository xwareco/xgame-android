package uencom.xgame.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;

public class GameCategory {

	private String name;
	private String imgPath;
	private String id;
	private static final String IMAGE_RESOURCE_PREFIX = "http://xgameapp.com/categories/";
	private ArrayList<Game> games;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgPath() {
		return imgPath;
	}

	@SuppressLint("DefaultLocale")
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;

	}

	public ArrayList<Game> getGames() {
		return games;
	}

	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}

	@SuppressLint("DefaultLocale") public Drawable setCategoryLogo() {
		String url = IMAGE_RESOURCE_PREFIX + this.name.toLowerCase() + "/"
				+ this.imgPath;
		InputStream is;
		Drawable res = null;
		try {
			is = new URL(url).openStream();
			res = Drawable.createFromStream(is, "src");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}
