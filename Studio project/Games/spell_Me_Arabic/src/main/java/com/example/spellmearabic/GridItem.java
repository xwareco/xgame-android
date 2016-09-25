package com.example.spellmearabic;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.Keep;
import android.util.StateSet;

@Keep
public class GridItem<T> {

    public static final int ICON_OFFSET = 10;

    private StateListDrawable mIcon;
    private String mLabel;
    private T mObject;

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIcon(Drawable d) {
        if (null == d) {
            mIcon = null;
        } else if (d instanceof StateListDrawable) {
            mIcon = (StateListDrawable) d;
        } else {
            InsetDrawable d1 = new InsetDrawable(d, 0, 0, ICON_OFFSET, ICON_OFFSET);
            InsetDrawable d2 = new InsetDrawable(d, ICON_OFFSET, ICON_OFFSET, 0, 0);
            mIcon = new StateListDrawable();
            mIcon.addState(new int[]{android.R.attr.state_pressed}, d2);
            mIcon.addState(StateSet.WILD_CARD, d1);
            //This won't help either: mIcon.addState(new int[]{}, d1);
        }
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String l) {
        mLabel = l;
    }

    public T getObject() {
        return mObject;
    }

    public void setObject(T o) {
        mObject = o;
    }

}
