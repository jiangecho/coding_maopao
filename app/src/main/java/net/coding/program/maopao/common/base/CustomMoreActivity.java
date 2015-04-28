package net.coding.program.maopao.common.base;

import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

import net.coding.program.maopao.BaseActivity;
import net.coding.program.R;
import net.coding.program.maopao.common.DialogUtil;
import net.coding.program.maopao.common.Global;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

import java.util.ArrayList;

/**
 * Created by chenchao on 15/3/9.
 */

@EActivity
public abstract class CustomMoreActivity extends BaseActivity {

    protected abstract View getAnchorView();
    protected abstract String getLink();

    @OptionsItem
    protected void action_more() {
        showRightTopPop();
    }


    private DialogUtil.RightTopPopupWindow mRightTopPopupWindow = null;


    private void showRightTopPop() {
        if (mRightTopPopupWindow == null) {
            ArrayList<DialogUtil.RightTopPopupItem> popupItemArrayList = new ArrayList();
            DialogUtil.RightTopPopupItem downloadItem = new DialogUtil.RightTopPopupItem(getString(R.string.copy_link), R.drawable.ic_menu_link);
            popupItemArrayList.add(downloadItem);
            mRightTopPopupWindow = DialogUtil.initRightTopPopupWindow(this, popupItemArrayList, onRightTopPopupItemClickListener);
        }

        mRightTopPopupWindow.adapter.notifyDataSetChanged();

        Rect rectgle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
        int contentViewTop =
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        mRightTopPopupWindow.adapter.notifyDataSetChanged();
        mRightTopPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        mRightTopPopupWindow.showAtLocation(getAnchorView(), Gravity.TOP | Gravity.RIGHT, 0, contentViewTop);
    }

    private AdapterView.OnItemClickListener onRightTopPopupItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            switch (position) {
                case 0:
                    String link = getLink();
                    if (link.isEmpty()) {
                        showButtomToast("复制链接失败");
                    } else {
                        Global.copy(CustomMoreActivity.this, link);
                        showButtomToast("已复制链接 " + link);
                    }
                    break;
            }
            mRightTopPopupWindow.dismiss();
        }
    };


}
