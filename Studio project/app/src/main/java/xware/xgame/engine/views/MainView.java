package xware.xgame.engine.views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONObject;

import xware.xgame.engine.navxgameAdapter;
import xware.xgame.engine.navxgameList;
import xware.xgame.engine.onDeviceGameChecker;
import xware.xgame.engine.xGame;
import xware.xgame.engine.web.Game;
import xware.xgame.engine.web.GameCategory;
import xware.xgame.engine.web.Installer;
import xware.xgame.engine.web.Server;
import xware.engine_lib.gestures.HandGestures;
import xware.xgame.xgame.R;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings("deprecation")
public class MainView extends AppCompatActivity implements
        android.app.ActionBar.OnNavigationListener,
        SwipeRefreshLayout.OnRefreshListener {

    ImageView mainImage, next, pre;
    ArrayList<GameCategory> categories;
    ArrayList<Game> games;
    Toolbar actionBar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<navxgameList> mNavItems = new ArrayList<>();
    boolean cat;
    LinearLayout trans;
    // http://192.168.1.102/xgame-app/public/
    // http://xgameapp.com/games/
    private static final String IMAGE_PREFIX = "http://xgameapp.com/games/";
    // String[] games = { "Catch The Beep", "Snake" };
    // Integer[] images = { R.drawable.beep, R.drawable.beep };
    final String[] languages = {" English ", " العربية "};
    AlertDialog levelDialog = null;
    ListView list;
    int currentIndex, lastIndex;
    TextView header, loading;
    ProgressBar proBar;
    Typeface arabic, english;
    HandGestures CatHG;
    xGame application;
    SharedPreferences appSharedPrefs;
    Editor prefsEditor;
    int ICONS[] = {R.drawable.homeicon, R.drawable.contactusicon, R.drawable.abouticon};
    String EMAIL = "";
    int PROFILE = 0;
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;
    // xGameAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(MainView.this);
        prefsEditor = appSharedPrefs.edit();

        setContentView(R.layout.main_view);
        application = (xGame) getApplication();
        String TITLES[] = {getResources().getString(R.string.home), getResources().getString(R.string.cont), getResources().getString(R.string.about)};
        String uName = appSharedPrefs.getString("uName", "");

        actionBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(actionBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.navList); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size
        mAdapter = new navxgameAdapter(MainView.this, TITLES, ICONS, EMAIL, PROFILE);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture
        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView
        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                actionBar, R.string.drawer_open, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //mDrawerToggle.syncState();
        Log.d("version", Integer.toString(Build.VERSION.SDK_INT));
        /*
         * String fileContents = User.readFromFile(); if
		 * (!fileContents.equals("")) { String uName = fileContents.substring(
		 * fileContents.indexOf(',') + 1, fileContents.lastIndexOf(',')); String
		 * uID = fileContents.substring(0, fileContents.indexOf(','));
		 * 
		 * String uPass = fileContents.substring( fileContents.lastIndexOf(',')
		 * + 1, fileContents.length() - 1); SharedPreferences appSharedPrefs =
		 * PreferenceManager
		 * .getDefaultSharedPreferences(getApplicationContext()); Editor edit =
		 * appSharedPrefs.edit(); edit.putString("uID", uID);
		 * edit.putString("uName", uName); edit.putString("uPass", uPass);
		 * edit.commit(); }
		 */


        CatHG = new HandGestures(this) {
            @Override
            public void onSwipeRight() {
                @SuppressWarnings("unchecked")
                ArrayAdapter<Game> AD = (ArrayAdapter<xware.xgame.engine.web.Game>) list
                        .getAdapter();
                AD.notifyDataSetChanged();
                lastIndex = currentIndex;
                currentIndex++;
                if (currentIndex >= categories.size())
                    currentIndex = 0;

                if (currentIndex != lastIndex) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            trans.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.VISIBLE);
                            proBar.setVisibility(View.VISIBLE);
                            list.setVisibility(View.GONE);
                            System.out.println("CURIND: "
                                    + categories.get(currentIndex).getId());
                            new Server(MainView.this, null, null, loading,
                                    null, proBar, trans, list).execute("game",
                                    categories.get(currentIndex).getId(),
                                    String.valueOf(0));
                            String lang = appSharedPrefs.getString("Lang", "EN");
                            if (lang.equals("AR")) {
                                header.setContentDescription("ألعاب"
                                        + categories.get(currentIndex)
                                        .getName());
                            } else if (lang.equals("EN")) {
                                header.setContentDescription(categories.get(
                                        currentIndex).getName()
                                        + " Games");
                            }

                        }
                    });
                }
                setNames();
                super.onSwipeRight();
            }

            @Override
            public void onSwipeleft() {
                @SuppressWarnings("unchecked")
                ArrayAdapter<Game> AD = (ArrayAdapter<xware.xgame.engine.web.Game>) list
                        .getAdapter();
                AD.notifyDataSetChanged();
                lastIndex = currentIndex;
                currentIndex--;
                if (currentIndex < 0)
                    currentIndex = categories.size() - 1;

                if (currentIndex != lastIndex) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            trans.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.VISIBLE);
                            proBar.setVisibility(View.VISIBLE);
                            list.setVisibility(View.GONE);
                            System.out.println("CURIND: "
                                    + categories.get(currentIndex).getId());
                            new Server(MainView.this, null, null, loading,
                                    null, proBar, trans, list).execute("game",
                                    categories.get(currentIndex).getId(),
                                    String.valueOf(0));
                            String lang = appSharedPrefs.getString("Lang", "EN");
                            if (lang.equals("AR")) {
                                header.setContentDescription("ألعاب"
                                        + categories.get(currentIndex)
                                        .getName());
                            } else if (lang.equals("EN")) {
                                header.setContentDescription(categories.get(
                                        currentIndex).getName()
                                        + " Games");
                            }

                        }
                    });
                }
                setNames();
                super.onSwipeleft();
            }

            @Override
            public void onSwipeDown() {

                super.onSwipeDown();
            }
        };

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);


        list = (ListView) findViewById(R.id.listView1);
        arabic = Typeface.createFromAsset(getAssets(),
                "fonts/Kharabeesh Font.ttf");
        english = Typeface.createFromAsset(getAssets(),
                "fonts/klavika-regular-opentype.otf");

        Intent I = getIntent();
        String catJson = I.getStringExtra("catJSON");
        System.out.println("JSON: " + catJson);
        // System.out.println(catJson);
        // new User(this, "AlyMoanes@yahoo.com", "456789").execute("register");
        String englishLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String arabicLetters = "ابتثجحخدذرزسشصضطظعغفقكلمنهوية";
        Gson g = new Gson();
        java.lang.reflect.Type type = new TypeToken<ArrayList<GameCategory>>() {
        }.getType();
        categories = new ArrayList<GameCategory>();
        categories = g.fromJson(catJson, (java.lang.reflect.Type) type);
        String lang = appSharedPrefs.getString("Lang", "EN");
        if (lang.equals("AR")) {
            for (int i = 0; i < categories.size(); ) {
                System.out.println(categories.get(i).getName().charAt(0)
                        + " "
                        + englishLetters.indexOf(categories.get(i).getName()
                        .charAt(0)));
                if (englishLetters.indexOf(categories.get(i).getName()
                        .charAt(0)) != -1) {
                    categories.remove(i);
                } else
                    i++;
            }
        } else if (lang.equals("EN")) {
            for (int i = 0; i < categories.size(); ) {

                if (arabicLetters
                        .indexOf(categories.get(i).getName().charAt(0)) != -1) {
                    categories.remove(i);
                } else
                    i++;
            }
        }

        cat = true;
        currentIndex = 0;
        lastIndex = -1;
        header = (TextView) findViewById(R.id.textView1);
        if (lang.equals("AR")) {
            header.setContentDescription("ألعاب"
                    + categories.get(currentIndex).getName());
        } else if (lang.equals("EN")) {
            header.setContentDescription(categories.get(currentIndex).getName()
                    + " Games");
        }
        header.setText(categories.get(currentIndex).getName());
        loading = (TextView) findViewById(R.id.textView2);
        if (lang.equals("AR")) {
            loading.setTypeface(arabic);
            header.setTypeface(english);
        } else if (lang.equals("EN")) {
            loading.setTypeface(english);
            header.setTypeface(english);
        }
        proBar = (ProgressBar) findViewById(R.id.progressBar2);
        mainImage = (ImageView) findViewById(R.id.catImg);
        trans = (LinearLayout) findViewById(R.id.translay);
        pre = (ImageView) findViewById(R.id.leftArrow);
        next = (ImageView) findViewById(R.id.rightArrow);
        System.out.println("CURINDO: " + categories.get(currentIndex).getId());
        new Server(MainView.this, null, null, loading, null, proBar, trans,
                list).execute("game", categories.get(currentIndex).getId(),
                String.valueOf(0));

        initonClicks();
        super.onCreate(savedInstanceState);
    }

    private void initonClicks() {

        pre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                lastIndex = currentIndex;
                currentIndex--;
                if (currentIndex < 0)
                    currentIndex = categories.size() - 1;

                if (currentIndex != lastIndex) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            trans.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.VISIBLE);
                            proBar.setVisibility(View.VISIBLE);
                            list.setVisibility(View.GONE);
                            System.out.println("CURIND: "
                                    + categories.get(currentIndex).getId());
                            new Server(MainView.this, null, null, loading,
                                    null, proBar, trans, list).execute("game",
                                    categories.get(currentIndex).getId(),
                                    String.valueOf(0));
                            String lang = appSharedPrefs.getString("Lang", "EN");
                            if (lang.equals("AR")) {
                                header.setContentDescription("ألعاب"
                                        + categories.get(currentIndex)
                                        .getName());
                            } else if (lang.equals("EN")) {
                                header.setContentDescription(categories.get(
                                        currentIndex).getName()
                                        + " Games");
                            }

                        }
                    });
                }
                setNames();
            }

        });

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                lastIndex = currentIndex;
                currentIndex++;
                if (currentIndex >= categories.size())
                    currentIndex = 0;

                if (currentIndex != lastIndex) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            trans.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.VISIBLE);
                            proBar.setVisibility(View.VISIBLE);
                            list.setVisibility(View.GONE);
                            System.out.println("CURIND: "
                                    + categories.get(currentIndex).getId());
                            new Server(MainView.this, null, null, loading,
                                    null, proBar, trans, list).execute("game",
                                    categories.get(currentIndex).getId(),
                                    String.valueOf(0));
                            String lang = appSharedPrefs.getString("Lang", "EN");
                            if (lang.equals("AR")) {
                                header.setContentDescription("ألعاب"
                                        + categories.get(currentIndex)
                                        .getName());
                            } else if (lang.equals("EN")) {
                                header.setContentDescription(categories.get(
                                        currentIndex).getName()
                                        + " Games");
                            }

                        }
                    });
                }
                setNames();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                checkDirs();
                SharedPreferences appSharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                Editor edit = appSharedPrefs.edit();

                String json = appSharedPrefs.getString("games", "");
                Gson g = new Gson();
                java.lang.reflect.Type type = new TypeToken<ArrayList<Game>>() {
                }.getType();
                games = g.fromJson(json, (java.lang.reflect.Type) type);
                edit.putString("game_id", games.get(arg2).getId());
                edit.putString("cat_id", categories.get(currentIndex).getId());
                edit.commit();
                String ifGameExistsLocation = Environment
                        .getExternalStorageDirectory().toString()
                        + "/xGame/Games/" + games.get(arg2).getName();

                if (!new File(ifGameExistsLocation).exists()) {

                    arg1.setClickable(true);
                    arg1.setBackgroundColor(Color.LTGRAY);
                    String textToToast = "Installing in background";
                    String lang = appSharedPrefs.getString("Lang", "EN");
                    if (lang.equals("AR"))
                        textToToast = "جاري تحميل اللعبة";
                    Toast.makeText(MainView.this, textToToast,
                            Toast.LENGTH_LONG).show();
                    final String logUrl = IMAGE_PREFIX
                            + games.get(arg2)
                            .getFileName()
                            .substring(
                                    0,
                                    games.get(arg2).getFileName()
                                            .lastIndexOf('.')) + "/"
                            + games.get(arg2).getImgPath();
                    final String downUrl = IMAGE_PREFIX
                            + games.get(arg2)
                            .getFileName()
                            .substring(
                                    0,
                                    games.get(arg2).getFileName()
                                            .lastIndexOf('.')) + "/"
                            + games.get(arg2).getFileName();
                    String unzipLocation = Environment
                            .getExternalStorageDirectory().toString()
                            + "/xGame/Games/";

                    new Installer(MainView.this, unzipLocation, logUrl, games
                            .get(arg2).getName(), games.get(arg2).getId(), list)
                            .execute(downUrl);

                } else {
                    onDeviceGameChecker installations = new onDeviceGameChecker(
                            MainView.this);
                    if (installations.isOfflineGameExists(list.getAdapter()
                            .getItem(arg2).toString()) == null) {
                        String offlineVersion = readFromFile(arg2);
                        String onlineVersion = games.get(arg2).getVersion();
                        System.out.println("On: " + onlineVersion + " Off: "
                                + offlineVersion);
                        if (offlineVersion.equals(onlineVersion)) {
                            Intent I = new Intent(getApplicationContext(),
                                    GameView.class);
                            I.putExtra("Folder", ifGameExistsLocation);
                            I.putExtra("Name", games.get(arg2).getName());
                            I.putExtra("gameid", games.get(arg2).getId());
                            I.putExtra(
                                    "Logo",
                                    IMAGE_PREFIX
                                            + games.get(arg2)
                                            .getFileName()
                                            .substring(
                                                    0,
                                                    games.get(arg2)
                                                            .getFileName()
                                                            .lastIndexOf(
                                                                    '.'))
                                            + "/"
                                            + games.get(arg2).getImgPath());
                            startActivity(I);
                            overridePendingTransition(R.anim.transition5,
                                    R.anim.transition4);
                        } else {
                            arg1.setClickable(true);
                            arg1.setBackgroundColor(Color.LTGRAY);
                            String textToToast = "Updating ..";
                            String lang = appSharedPrefs.getString("Lang", "EN");
                            if (lang.equals("AR"))
                                textToToast = "جاري تحديث اللعبة";
                            Toast.makeText(MainView.this, textToToast,
                                    Toast.LENGTH_LONG).show();
                            final String logUrl = IMAGE_PREFIX
                                    + games.get(arg2)
                                    .getFileName()
                                    .substring(
                                            0,
                                            games.get(arg2)
                                                    .getFileName()
                                                    .lastIndexOf('.'))
                                    + "/" + games.get(arg2).getImgPath();
                            final String downUrl = IMAGE_PREFIX
                                    + games.get(arg2)
                                    .getFileName()
                                    .substring(
                                            0,
                                            games.get(arg2)
                                                    .getFileName()
                                                    .lastIndexOf('.'))
                                    + "/" + games.get(arg2).getFileName();
                            String unzipLocation = Environment
                                    .getExternalStorageDirectory().toString()
                                    + "/xGame/Games/";

                            new Installer(MainView.this, unzipLocation, logUrl,
                                    games.get(arg2).getName(), games.get(arg2)
                                    .getId(), list).execute(downUrl);
                        }
                    } else {
                        arg1.setClickable(true);
                        arg1.setBackgroundColor(Color.LTGRAY);
                        String textToToast = "Installing in background";
                        String lang = appSharedPrefs.getString("Lang", "EN");
                        if (lang.equals("AR"))
                            textToToast = "جاري تحميل اللعبة";
                        Toast.makeText(MainView.this, textToToast,
                                Toast.LENGTH_LONG).show();

                        textToToast = "This installation is corrupted..The game will be downloaded again";
                        if (lang.equals("AR"))
                            textToToast = "تم العثور على تحميل غير صالح..سيتم إعادة تحميل هذه اللعبة من جديد";
                        Toast.makeText(MainView.this, textToToast,
                                Toast.LENGTH_LONG).show();

                        final String logUrl = IMAGE_PREFIX
                                + games.get(arg2)
                                .getFileName()
                                .substring(
                                        0,
                                        games.get(arg2).getFileName()
                                                .lastIndexOf('.'))
                                + "/" + games.get(arg2).getImgPath();
                        final String downUrl = IMAGE_PREFIX
                                + games.get(arg2)
                                .getFileName()
                                .substring(
                                        0,
                                        games.get(arg2).getFileName()
                                                .lastIndexOf('.'))
                                + "/" + games.get(arg2).getFileName();
                        String unzipLocation = Environment
                                .getExternalStorageDirectory().toString()
                                + "/xGame/Games/";

                        new Installer(MainView.this, unzipLocation, logUrl,
                                games.get(arg2).getName(), games.get(arg2)
                                .getId(), list).execute(downUrl);
                    }
                }

            }

            private void checkDirs() {
                String path1;
                path1 = Environment.getExternalStorageDirectory()
                        + "/xGame/Games";
                File f = new File(path1);
                if (!f.isDirectory()) {
                    f.mkdirs();
                }
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int arg2, long arg3) {
                final Game toBeDeleted = (Game) list.getAdapter().getItem(arg2);
                final File Game = new File(Environment
                        .getExternalStorageDirectory()
                        + "/xGame/Games/"
                        + toBeDeleted.getName());
                if (Game.exists()) {
                    String textToToast = "Do you really want to Delete "
                            + toBeDeleted.getName() + "?";
                    String lang = appSharedPrefs.getString("Lang", "EN");
                    if (lang.equals("AR"))
                        textToToast = "هل تريد مسح لعبة  "
                                + toBeDeleted.getName() + "؟";
                    Toast.makeText(MainView.this, textToToast,
                            Toast.LENGTH_LONG).show();

                    @SuppressWarnings("unused")
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(MainView.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(MainView.this);
                    }
                    if (lang.equals("EN"))
                        builder.setTitle("Delete " + toBeDeleted.getName() + "?");
                    else if (lang.equals("AR"))
                        builder.setTitle("مسح " + toBeDeleted.getName() + "؟");
                    builder.setMessage(textToToast);
                    builder.setIcon(R.drawable.icon_scaled);
                    builder.setPositiveButton(android.R.string.yes,
                            new DialogInterface.OnClickListener() {

                                public void onClick(
                                        DialogInterface dialog,
                                        int whichButton) {

                                    onDeviceGameChecker
                                            .DeleteGame(Game);
                                    String textToToast = "Game successfully deleted";
                                    String lang = appSharedPrefs.getString("Lang", "EN");
                                    if (lang.equals("AR"))
                                        textToToast = "تم مسح اللعبة بنجاح";
                                    Toast.makeText(MainView.this,
                                            textToToast,
                                            Toast.LENGTH_LONG).show();

                                    @SuppressWarnings("unchecked")
                                    ArrayAdapter<Game> AD = (ArrayAdapter<xware.xgame.engine.web.Game>) list
                                            .getAdapter();
                                    AD.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                }
                return true;
            }
        });

    }

    private void setNames() {
        if (cat == true) {
            header.setText(categories.get(currentIndex).getName());
        } else {
            header.setText(categories.get(currentIndex).getGames()
                    .get(currentIndex).getName());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
/*
        if (item.getItemId() == android.R.id.home) {

            try {
                if (mNavItems.size() != 0) {
                    mNavItems.removeAll(mNavItems);
                }

                if (current.getDisplayLanguage().equals("English"))
                    mNavItems.add(new navxgameList("Games List",
                            R.drawable.games));

                else if (lang.equals("AR"))
                    mNavItems.add(new navxgameList("قائمة الألعاب",
                            R.drawable.games));

                SharedPreferences appSharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                String uName = appSharedPrefs.getString("uName", "");
                if (!uName.equals("")) {
                    String uNameSideMenu = uName.substring(0,
                            uName.indexOf('@'));
                    mNavItems.add(new navxgameList(uNameSideMenu,
                            R.drawable.reg));

                } else {
                    // String fileContents = User.readFromFile();
                    // if (fileContents.equals("")) {
                    if (current.getDisplayLanguage().equals("English"))
                        mNavItems
                                .add(new navxgameList("Login", R.drawable.reg));

                    else if (lang.equals("AR"))
                        mNavItems.add(new navxgameList("تسجيل الدخول",
                                R.drawable.reg));

                    // }

					/*
					 * else {
					 * 
					 * String uNameSideMenu = uName.substring(0,
					 * uName.indexOf('@')); mNavItems.add(new
					 * navxgameList(uNameSideMenu, R.drawable.reg));
					 * 
					 * }
					 *
                }
                if (current.getDisplayLanguage().equals("English")) {
                    mNavItems
                            .add(new navxgameList("Contact Us", R.drawable.env));
                    mNavItems
                            .add(new navxgameList("About Us", R.drawable.about));
                } else if (lang.equals("AR")) {
                    mNavItems.add(new navxgameList("إتصل بنا", R.drawable.env));
                    mNavItems
                            .add(new navxgameList("من نحن؟", R.drawable.about));
                }

                navxgameAdapter navAdapter = new navxgameAdapter(this,
                        mNavItems);
                mDrawerList.setAdapter(navAdapter);

                if (getActionBar().getTitle().equals("xGame"))
                    mDrawerLayout.openDrawer(GravityCompat.START);
                else
                    mDrawerLayout.closeDrawer(GravityCompat.START);
            } catch (Exception e) {
                File xGameFolder = new File(
                        Environment.getExternalStorageDirectory()
                                + "/xGame/Data/");
                xGameFolder.mkdirs();
                File DataFile = new File(xGameFolder, "test.txt");
                BufferedWriter writer;
                try {
                    DataFile.createNewFile();
                    writer = new BufferedWriter(new FileWriter(DataFile));
                    writer.write(e.getMessage());
                    writer.close();
                } catch (Exception ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
            }

        }*/

        if (item.getItemId() == R.id.action_testhead) {
            Intent I = new Intent(getApplicationContext(),
                    HeadphoneTester.class);
            startActivity(I);
            overridePendingTransition(R.anim.transition5, R.anim.transition4);
        }

        if (item.getItemId() == R.id.action_user) {
            Intent I;
            String uName = appSharedPrefs.getString("uName", "");
            if (uName.equals(""))
                I = new Intent(this, Register.class);
            else
                I = new Intent(this, Edit.class);
            startActivity(I);
            overridePendingTransition(R.anim.transition5, R.anim.transition4);


        }

        if (item.getItemId() == R.id.action_langSelection) {
            int checkedLanguage = -1;
            if (appSharedPrefs.getString("Lang", "EN").equals("AR")) checkedLanguage = 1;
            else if (appSharedPrefs.getString("Lang", "EN").equals("EN")) checkedLanguage = 0;
            final int langNum = checkedLanguage;
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(MainView.this, android.R.style.Theme_Material_Light_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(MainView.this);
            }
            builder.setTitle(getResources().getString(R.string.selectYourLang));
            builder.setIcon(R.drawable.icon_scaled);
            builder.setSingleChoiceItems(languages, checkedLanguage, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {


                    switch (item) {
                        case 0:

                            if (langNum != 0) {
                                prefsEditor.putString("Lang", "EN");
                                prefsEditor.commit();

                                Locale myLocale = Locale.forLanguageTag("en-US");
                                Resources res = getResources();
                                DisplayMetrics dm = res.getDisplayMetrics();
                                Configuration conf = res.getConfiguration();
                                conf.locale = myLocale;
                                res.updateConfiguration(conf, dm);
                                Intent I;
                                I = new Intent(MainView.this, SplashActivity.class);
                                I.putExtra("FirstUse", false);
                                startActivity(I);
                                overridePendingTransition(R.anim.transition5, R.anim.transition4);
                                finish();
                            }
                            break;
                        case 1:
                            if (langNum != 1) {
                                prefsEditor.putString("Lang", "AR");
                                prefsEditor.commit();
                                Locale myLocale = Locale.forLanguageTag("ar-EG");
                                Resources res = getResources();
                                DisplayMetrics dm = res.getDisplayMetrics();
                                Configuration conf = res.getConfiguration();
                                conf.locale = myLocale;
                                res.updateConfiguration(conf, dm);
                                Intent I = new Intent(MainView.this, SplashActivity.class);
                                I.putExtra("FirstUse", false);
                                startActivity(I);
                                overridePendingTransition(R.anim.transition5, R.anim.transition4);
                                finish();
                            }
                            break;


                    }
                    levelDialog.dismiss();
                }
            });
            levelDialog = builder.create();
            levelDialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

        if (itemId == 0) {
            list.setVisibility(View.GONE);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                mainImage.setVisibility(View.VISIBLE);

            currentIndex = 0;
            header.setVisibility(View.VISIBLE);
            pre.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);

        }

        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        mDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    private void selectItemFromDrawer(int position) {
       /* Intent I = null;
        if (position == 0) {
            System.out.println(position);
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (position == 1) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            if (mNavItems.get(1).getTitle().equalsIgnoreCase("login")
                    || mNavItems.get(1).getTitle()
                    .equalsIgnoreCase("تسجيل الدخول")) {
                I = new Intent(this, Register.class);
                I.putExtra("TAG", "main");
                System.out.println(position);
                startActivity(I);
            } else
                I = new Intent(this, Edit.class);
            startActivity(I);

        } else if (position == 2) {
            System.out.println(position);
            mDrawerLayout.closeDrawer(GravityCompat.START);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());
            String uID = appSharedPrefs.getString("uID", "");
            String uName = appSharedPrefs.getString("uName", "");
            String uPass = appSharedPrefs.getString("uPass", "");
            System.out.println(uID + " " + uName + " " + uPass);
            if (!uID.equals("") && !uName.equals("")) {
                String textToToast = "Welcome " + uName
                        + " ,We are very glad to hear your feedback";
                if (lang.equals("AR"))
                    textToToast = "أهﻻ بك  " + uName
                            + " ,سعداء دائما بتلقي مقترحاتك";
                Toast.makeText(MainView.this, textToToast, Toast.LENGTH_LONG)
                        .show();
                I = new Intent(this, ContactUs.class);// contact
                I.putExtra("ID", uID);
                I.putExtra("Name", uName);

            } else {
                // String fileContents = User.readFromFile();
                if (uID.equals("")) {
                    I = new Intent(this, Register.class);
                    I.putExtra("TAG", "main2");
                    System.out.println("HERE!!!");
                    String textToToast = "You need to login first before you can contact us";
                    if (lang.equals("AR"))
                        textToToast = "تحتاج أوﻻ لتسجيل الدخول بحسابك حتى تستطيع إرسال أي مقترحات أو شكاوى";
                    Toast.makeText(MainView.this, textToToast,
                            Toast.LENGTH_LONG).show();
                }
                // 9,ali,123
                else {

                    String textToToast = "Welcome " + uName
                            + " ,We are very glad to hear your feedback";
                    if (lang.equals("AR"))
                        textToToast = "أهﻻ بك  " + uName
                                + " ,سعداء دائما بتلقي مقترحاتك";
                    Toast.makeText(MainView.this, textToToast,
                            Toast.LENGTH_LONG).show();
                    I = new Intent(this, ContactUs.class);// contact
                    I.putExtra("ID", uID);
                    I.putExtra("Name", uName);
                    I.putExtra("Pass", uPass);
                }

            }
            startActivity(I);
            overridePendingTransition(R.anim.transition5, R.anim.transition4);
        } else if (position == 3) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            I = new Intent(this, AboutUs.class);// about
            System.out.println(position);
            startActivity(I);
            overridePendingTransition(R.anim.transition5, R.anim.transition4);
        }*/

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        CatHG.OnTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CatHG.OnTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onRefresh() {
        refreshGames();

    }

    private void refreshGames() {
        swipeRefreshLayout.setRefreshing(true);
        if (ContextCompat.checkSelfPermission(MainView.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat
                    .requestPermissions(
                            MainView.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
        }
        new Server(MainView.this, null, null, loading, null, proBar, trans,
                list).execute("game", categories.get(currentIndex).getId(),
                String.valueOf(0));

        swipeRefreshLayout.setRefreshing(false);
    }

    public String readFromFile(int position) {
        File userDataFile = new File(Environment.getExternalStorageDirectory()
                + "/xGame/Games/" + games.get(position).getName()
                + "/manifest.json");
        String ret = "";
        String ver = "";
        if (userDataFile.exists()) {

            BufferedReader bufferedReader;
            try {
                bufferedReader = new BufferedReader(
                        new FileReader(userDataFile));
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                bufferedReader.close();
                ret = stringBuilder.toString();
                JSONObject jO = new JSONObject(ret);
                ver = jO.getString("version");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return ver;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    prefsEditor.putBoolean("per", true);
                    prefsEditor.commit();
                    @SuppressWarnings("unchecked")
                    ArrayAdapter<Game> AD = (ArrayAdapter<xware.xgame.engine.web.Game>) list
                            .getAdapter();
                    AD.notifyDataSetChanged();
                    String contentD = "Games status updated after allowing the permission";
                    String lang = appSharedPrefs.getString("Lang", "EN");
                    if (lang.equals("AR"))
                        contentD = "تم منح الإذن وتحديث الحالة الخاصة بالألعاب";
                    Toast.makeText(MainView.this, contentD, Toast.LENGTH_LONG)
                            .show();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    prefsEditor.putBoolean("per", false);
                    prefsEditor.commit();
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
