package xware.xgame.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Environment;

public class onDeviceGameChecker {

	Context ctx;

	public onDeviceGameChecker(Context c) {
		ctx = c;
	}

	public offlinexGameList isOfflineGameExists(String gameName) {
		offlinexGameList res = null;
		ArrayList<String> offGames = new ArrayList<String>();
		String searchLocation = Environment.getExternalStorageDirectory()
				+ "/xGame/games/";
		File gamesFolder = new File(searchLocation);
		ArrayList<File> folderContents = null;
		if (gamesFolder.exists() == true) {
			folderContents = new ArrayList<File>(Arrays.asList(gamesFolder
					.listFiles()));
		} else {
			return null;

		}

		for (int i = 0; i < folderContents.size(); i++) {
			File mainGameFolder = folderContents.get(i);
			if (folderContents.get(i).getName().contains(".xgame")) {
				folderContents.get(i).delete();
			} else {

				ArrayList<File> game = new ArrayList<File>(
						Arrays.asList(mainGameFolder.listFiles()));
				boolean xmlFileExists = false, sourceFoldeNotEmpty = false;
				String xmlFileName = "App.xml";
				if (mainGameFolder.getName().equalsIgnoreCase(gameName)
						|| gameName.equalsIgnoreCase("any")) {

					for (int j = 0; j < game.size(); j++) {
						if (game.get(j).getName().equals(xmlFileName))
							xmlFileExists = true;
						/*else if (game.get(j).getName().equals("Images")
								&& game.get(j).isDirectory()
								&& game.get(j).list().length > 0)
							imagesFoldeNotEmpty = true;
						else if (game.get(j).getName().equals("Sound")
								&& game.get(j).isDirectory()
								&& game.get(j).list().length > 0)
							soundFoldeNotEmpty = true;*/
						else if (game.get(j).getName().equals("Source")
								&& game.get(j).isDirectory()
								&& game.get(j).list().length > 0)
							sourceFoldeNotEmpty = true;
					}

					if (xmlFileExists && sourceFoldeNotEmpty) {

						offGames.add(mainGameFolder.getName());
						if (!gameName.equalsIgnoreCase("any")) {
							res = new offlinexGameList((Activity) ctx, offGames);
							System.out.println(gameName);
							return res;
						}

					}

					else {

						DeleteGame(mainGameFolder);
					}

					if (i == folderContents.size() - 1 && offGames.size() > 0) {

						res = new offlinexGameList((Activity) ctx, offGames);

					}
				}
			}
		}

		return res;
	}

	public static void DeleteGame(File mainGameFolder) {
		if (mainGameFolder.isDirectory())
			for (File child : mainGameFolder.listFiles())
				DeleteGame(child);

		mainGameFolder.delete();

	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

}
