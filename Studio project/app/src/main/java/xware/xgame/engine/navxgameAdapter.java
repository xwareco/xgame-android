package xware.xgame.engine;

import java.util.ArrayList;
import java.util.Locale;

import xware.xgame.engine.views.AboutUs;
import xware.xgame.engine.views.ContactUs;
import xware.xgame.engine.views.MainView;
import xware.xgame.engine.views.Register;
import xware.xgame.engine.views.SplashActivity;
import xware.xgame.xgame.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class navxgameAdapter extends RecyclerView.Adapter<navxgameAdapter.ViewHolder> {

    static Context context;
    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;
    private String mNavTitles[]; // String Array to store the passed titles Value from MainActivity.java
    private int mIcons[];       // Int Array to store the passed icons resource value from MainActivity.java
    private int profile;        //int Resource for header view profile picture
    private String email;       //String Resource for header view email
    Typeface arabic, english;
    static Locale current;


    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int Holderid;

        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView email;


        public ViewHolder(View itemView, int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            itemView.setClickable(false);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

            if (ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.title); // Creating TextView object with the id of textView from item_row.xml
                imageView = (ImageView) itemView.findViewById(R.id.icon);// Creating ImageView object with the id of ImageView from item_row.xml
                Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            } else {


                email = (TextView) itemView.findViewById(R.id.email);       // Creating Text View object from header.xml for email
                profile = (ImageView) itemView.findViewById(R.id.circleView);// Creating Image view object from header.xml for profile pic
                Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            }


        }

        @Override
        public void onClick(View v) {

            int position = getPosition();
            Intent I = null;
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
            String uName = appSharedPrefs.getString("uName", "");
            Activity act = (Activity)context;
            String lang = appSharedPrefs.getString("Lang","EN");
            switch (position) {
                case 1:
                    I = new Intent(context, SplashActivity.class);
                    act.finish();
                    break;
                case 2:
                    if (!uName.equals("")) {
                        String textToToast = "Welcome " + uName
                                + " ,We are very glad to hear your feedback";

                        if (lang.equals("AR"))
                            textToToast = "أهﻻ بك  " + uName
                                    + " ,سعداء دائما بتلقي مقترحاتك";
                        Toast.makeText(context, textToToast,
                                Toast.LENGTH_LONG).show();
                        I = new Intent(context, ContactUs.class);
                    } else {
                        I = new Intent(context, Register.class);
                        I.putExtra("TAG", "main2");
                        System.out.println("HERE!!!");
                        String textToToast = "You need to login first before you can contact us";
                        if (lang.equals("AR"))
                            textToToast = "تحتاج أوﻻ لتسجيل الدخول بحسابك حتى تستطيع إرسال أي مقترحات أو شكاوى";
                        Toast.makeText(context, textToToast,
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case 3:
                    I = new Intent(context, AboutUs.class);
                    break;

            }
            context.startActivity(I);
            act.overridePendingTransition(R.anim.transition5, R.anim.transition4);

        }
    }

    public navxgameAdapter(Context context, String titles[], int icons[], String email, int profile) {
        this.context = context;
        this.mNavTitles = titles;
        this.mIcons = icons;
        this.email = email;
        this.profile = profile;
        arabic = Typeface.createFromAsset(context.getAssets(),
                "fonts/Kharabeesh Font.ttf");
        english = Typeface.createFromAsset(context.getAssets(),
                "fonts/klavika-regular-opentype.otf");
        current = context.getResources().getConfiguration().locale;
    }

    //Below first we override the method onCreateViewHolder which is called when the ViewHolder is
    //Created, In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
    // if the viewType is TYPE_HEADER
    // and pass it to the view holder

    @Override
    public navxgameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item, parent, false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v, viewType); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object

            //inflate your layout and pass it to view holder

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v, viewType); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created


        }
        return null;

    }

    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(navxgameAdapter.ViewHolder holder, int position) {
        if (holder.Holderid == 1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            holder.textView.setText(mNavTitles[position - 1]); // Setting the Text with the array of our Titles
            holder.imageView.setImageResource(mIcons[position - 1]);// Settimg the image with array of our icon
        } else {

            holder.profile.setImageResource(profile);           // Similarly we set the resources for header view
            holder.email.setText(email);
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length + 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // With the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
/*
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Locale current = context.getResources().getConfiguration().locale;
            System.out.println("Lang: " + current.getDisplayLanguage());
            if (current.getDisplayLanguage().equals("English"))
                view = inflater.inflate(R.layout.drawer_item, null);

            else if (lang.equals("AR"))
                view = inflater.inflate(R.layout.drawer_item_arabic, null);
        } else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        ImageView editIconView = (ImageView) view.findViewById(R.id.editicon);
        if (lang.equals("AR")) {
            titleView.setTypeface(arabic);
        } else if (current.getDisplayLanguage().equals("English")) {
            titleView.setTypeface(english);
        }
        titleView.setText(mNavItems.get(position).title);
        iconView.setImageResource(mNavItems.get(position).icon);

        if (position == 1
                && !mNavItems.get(1).title.equalsIgnoreCase("register")) {
            System.out.println(position + " " + mNavItems.get(1).title);
            editIconView.setVisibility(View.VISIBLE);
        }
        if (position == 0)
            editIconView.setVisibility(View.GONE);
        return view;
    }*/
}
