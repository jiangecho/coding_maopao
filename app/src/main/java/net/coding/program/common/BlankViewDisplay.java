package net.coding.program.common;

import android.view.View;
import android.widget.TextView;

import net.coding.program.R;
import net.coding.program.maopao.MaopaoListFragment;
import net.coding.program.message.UsersListFragment;

/**
 * Created by chaochen on 14-10-24.
 */
public class BlankViewDisplay {

    public static void setBlank(int itemSize, Object fragment, boolean request, View v, View.OnClickListener onClick) {
        setBlank(itemSize, fragment, request, v, onClick, "");
    }


    public static void setBlank(int itemSize, Object fragment, boolean request, View v, View.OnClickListener onClick, String blankMessage) {
        View btn = v.findViewById(R.id.btnRetry);
        if (request) {
            btn.setVisibility(View.INVISIBLE);
        } else {
            btn.setVisibility(View.VISIBLE);
            btn.setOnClickListener(onClick);
        }

        setBlank(itemSize, fragment, request, v, blankMessage);
    }

    private static void setBlank(int itemSize, Object fragment, boolean request, View v, String customMessage) {
        boolean show = (itemSize == 0);
        if (!show) {
            v.setVisibility(View.GONE);
            return;
        }
        v.setVisibility(View.VISIBLE);

        int iconId = R.drawable.ic_exception_no_network;
        String text = "";

        if (request) {
            if (customMessage == null || customMessage.isEmpty()) {

                if (fragment instanceof MaopaoListFragment) {
                    iconId = R.drawable.ic_exception_blank_maopao;
                    text = "来，冒个泡吧～";
                } else if (fragment instanceof UsersListFragment) {
                    iconId = R.drawable.ic_exception_blank_maopao;
                    text = "快和你的好友打个招呼吧~";
                }
            } else {
                text = customMessage;
            }

        } else {
            iconId = R.drawable.ic_exception_no_network;
            text = "获取数据失败\n请检查下网络是否通畅";
        }

        v.findViewById(R.id.icon).setBackgroundResource(iconId);
        ((TextView) v.findViewById(R.id.message)).setText(text);
    }

}
