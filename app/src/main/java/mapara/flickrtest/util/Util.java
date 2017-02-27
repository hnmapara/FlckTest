package mapara.flickrtest.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by mapara on 2/26/17.
 */

public class Util {

    public static int calculateNoOfColumns(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 100); //100 is width of one cell in a grid
        return noOfColumns;
    }
}
