package net.coding.program.maopao.maopao.item;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import net.coding.program.R;
import net.coding.program.maopao.common.Global;
import net.coding.program.maopao.common.HtmlContent;
import net.coding.program.maopao.common.ImageLoadTool;
import net.coding.program.maopao.maopao.MaopaoListFragment;
import net.coding.program.maopao.model.BaseComment;
import net.coding.program.maopao.model.Maopao;

import java.util.ArrayList;

/**
 * Created by chenchao on 15/3/31.
 * 有6张图片的控件，比如任务的评论
 */
public class ContentAreaImages extends ContentAreaBase {

    protected ImageLoadTool imageLoad;

    private int contentMarginBottom = 0;

    protected View imageLayout0;
    protected View imageLayout1;

    private static final int[] itemImages = new int[]{
            R.id.image0,
            R.id.image1,
            R.id.image2,
            R.id.image3,
            R.id.image4,
            R.id.image5
    };

    protected DisplayImageOptions imageOptions = new DisplayImageOptions
            .Builder()
            .showImageOnLoading(R.drawable.ic_default_image)
            .showImageForEmptyUri(R.drawable.ic_default_image)
            .showImageOnFail(R.drawable.ic_default_image)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();

    private static final int itemImagesMaxCount = itemImages.length;

    private ImageView images[] = new ImageView[itemImagesMaxCount];


    public ContentAreaImages(View convertView, View.OnClickListener onClickContent, View.OnClickListener onclickImage, Html.ImageGetter imageGetterParamer, ImageLoadTool loadParams, int pxImageWidth) {
        super(convertView, onClickContent, imageGetterParamer);

        imageLoad = loadParams;

        imageLayout0 = convertView.findViewById(R.id.imagesLayout0);
        imageLayout1 = convertView.findViewById(R.id.imagesLayout1);

        for (int i = 0; i < itemImagesMaxCount; ++i) {
            images[i] = (ImageView) convertView.findViewById(itemImages[i]);
            images[i].setOnClickListener(onclickImage);
            images[i].setFocusable(false);
            images[i].setLongClickable(true);
            ViewGroup.LayoutParams lp = images[i].getLayoutParams();
            lp.width = pxImageWidth;
            lp.height = pxImageWidth;
        }

        contentMarginBottom = convertView.getResources().getDimensionPixelSize(R.dimen.message_text_margin_bottom);
    }

    // 用来设置冒泡的
    public void setData(Maopao.MaopaoObject maopaoObject) {
        setDataContent(maopaoObject.content, maopaoObject);
    }

    public void setData(BaseComment comment) {
        setDataContent(comment.content, comment);
    }

    private void setDataContent(String s, Object contentObject) {
        String data = s;

        Global.MessageParse maopaoData = HtmlContent.parseMaopao(data);

        if (maopaoData.text.isEmpty()) {
            content.setVisibility(View.GONE);
        } else {
            content.setVisibility(View.VISIBLE);
            content.setText(Global.changeHyperlinkColor(maopaoData.text, imageGetter, Global.tagHandler));
            content.setTag(contentObject);
        }

        setImageUrl(maopaoData.uris);
    }


    // 用来设置message的
    public void setData(String data) {
        Global.MessageParse maopaoData;
        maopaoData = HtmlContent.parseMessage(data);

        if (maopaoData.text.isEmpty()) {
            content.setVisibility(View.GONE);
        } else {
            content.setVisibility(View.VISIBLE);
            content.setText(Global.changeHyperlinkColor(maopaoData.text, imageGetter, Global.tagHandler));

            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) content.getLayoutParams();
            if (maopaoData.uris.size() > 0) {
                lp.bottomMargin = contentMarginBottom;
            } else {
                lp.bottomMargin = 0;
            }
            content.setLayoutParams(lp);
        }

        setImageUrl(maopaoData.uris);
    }

    protected void setImageUrl(ArrayList<String> uris) {
        if (uris.size() == 0) {
            imageLayout0.setVisibility(View.GONE);
            imageLayout1.setVisibility(View.GONE);
        } else if (uris.size() < 3) {
            imageLayout0.setVisibility(View.VISIBLE);
            imageLayout1.setVisibility(View.GONE);
        } else {
            imageLayout0.setVisibility(View.VISIBLE);
            imageLayout1.setVisibility(View.VISIBLE);
        }

        int min = Math.min(uris.size(), images.length);
        int i = 0;
        for (; i < min; ++i) {
            images[i].setVisibility(View.VISIBLE);
            images[i].setTag(new MaopaoListFragment.ClickImageParam(uris, i, false));
            imageLoad.loadImage(images[i], uris.get(i), imageOptions);
        }

        for (; i < itemImagesMaxCount; ++i) {
            images[i].setVisibility(View.GONE);
        }
    }

}
