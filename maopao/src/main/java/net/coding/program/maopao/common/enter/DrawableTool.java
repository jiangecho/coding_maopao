package net.coding.program.maopao.common.enter;

import android.graphics.drawable.Drawable;
import net.coding.program.app.MyApp;

/**
 * Created by chaochen on 14-11-12.
 */
public class DrawableTool {
    public static void zoomDrwable(Drawable drawable, boolean isMonkey) {
        int width = isMonkey ? MyApp.sEmojiMonkey : MyApp.sEmojiNormal;
        drawable.setBounds(0, 0, width, width);
    }
}
