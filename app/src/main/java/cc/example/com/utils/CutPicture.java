package cc.example.com.utils;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * Created by Administrator on 2017/2/15 0015.
 * y用于图片的裁剪
 */
public class CutPicture implements Transformation {


    @Override
    public Bitmap transform(Bitmap source) {
        int size=Math.min(source.getWidth(),source.getHeight());
        int x=(source.getWidth()-size)/2;
        int y=(source.getHeight()-size)/2;
        Bitmap bitmap=Bitmap.createBitmap(source,x,y,size,size);
        if (bitmap!=source){
            source.recycle();    //对source进行回收
        }
        return bitmap;
    }

    @Override
    public String key() {
        return "sqare";
    }
}
