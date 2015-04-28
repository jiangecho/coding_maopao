package net.coding.program.maopao.maopao.item;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import net.coding.program.R;
import net.coding.program.maopao.common.DialogCopy;
import net.coding.program.maopao.common.LongClickLinkMovementMethod;

/**
 * Created by chaochen on 14/12/22.
 */
public class ContentAreaBase {

    protected TextView content;
    protected Html.ImageGetter imageGetter;

    public ContentAreaBase(View convertView, View.OnClickListener onClickContent, Html.ImageGetter imageGetterParamer) {
        content = (TextView) convertView.findViewById(R.id.content);
        content.setMovementMethod(LongClickLinkMovementMethod.getInstance());
        content.setOnClickListener(onClickContent);
        content.setOnLongClickListener(DialogCopy.getInstance());

        imageGetter = imageGetterParamer;
    }

}
