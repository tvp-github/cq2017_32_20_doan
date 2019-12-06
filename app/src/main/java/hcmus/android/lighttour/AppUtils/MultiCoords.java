package hcmus.android.lighttour.AppUtils;

import android.widget.ArrayAdapter;

public class MultiCoords {
    ArrayAdapter<OneCoord> coords;

    public ArrayAdapter<OneCoord> getCoords() {
        return coords;
    }

    public void setCoords(ArrayAdapter<OneCoord> coords) {
        this.coords = coords;
    }

}
