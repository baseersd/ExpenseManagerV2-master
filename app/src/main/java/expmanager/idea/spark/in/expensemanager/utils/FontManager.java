package expmanager.idea.spark.in.expensemanager.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by RamanaRedddy on 3/18/17.
 */

public class FontManager {

    public static final String FONTAWESOME = "fontawesome.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

}
