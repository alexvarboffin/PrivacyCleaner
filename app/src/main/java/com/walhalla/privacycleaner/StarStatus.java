package com.walhalla.privacycleaner;

import android.widget.ImageView;



public class StarStatus {

    private ImageView icon;
    private boolean selected;

    public StarStatus(ImageView icon) {
        this.icon = icon;
        this.selected = false;
    }

    public ImageView getIcon() {
        return icon;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        icon.setImageResource(selected ? R.drawable.selected_star : R.drawable.unselected_star);
    }
}
