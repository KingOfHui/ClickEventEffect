package com.dh.clickevent.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * @ClassName ViewStateUtil
 * @Description
 * @Author dinghui
 * @Date 2020/8/18 0018 10:04
 */
public class ViewStateUtil {


    public static Bitmap getColorImage(Bitmap bitmap, float sx, float bhd, float ld) {// 参数分别是色相，饱和度和亮度
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ColorMatrix sxMatrix = new ColorMatrix();// 设置色调
        sxMatrix.setRotate(0, sx);
        sxMatrix.setRotate(1, sx);
        sxMatrix.setRotate(2, sx);

        ColorMatrix bhdMatrix = new ColorMatrix();// 设置饱和度
        bhdMatrix.setSaturation(bhd);

        ColorMatrix ldMatrix = new ColorMatrix();// 设置亮度
        ldMatrix.setScale(ld, ld, ld, 1);

        ColorMatrix mixMatrix = new ColorMatrix();// 设置整体效果
        mixMatrix.postConcat(sxMatrix);
        mixMatrix.postConcat(bhdMatrix);
        mixMatrix.postConcat(ldMatrix);
        paint.setColorFilter(new ColorMatrixColorFilter(mixMatrix));// 用颜色过滤器过滤

        canvas.drawBitmap(bmp, 0, 0, paint);// 重新画图
        return bmp;
    }

    //改变图片的亮度方法 0--原样  >0---调亮  <0---调暗
    public static void changeLight(ImageView imageView, int brightness) {
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness,// 改变亮度
                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        imageView.setColorFilter(new ColorMatrixColorFilter(cMatrix));
    }

    public static void changeLight(Drawable drawable, int brightness) {
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness,// 改变亮度
                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        drawable.setColorFilter(new ColorMatrixColorFilter(cMatrix));
    }

    public static int getTextViewDarkColor(int color) {

        // 获取本来颜色
        String hexColorVaule = String.format("%06X", (0xFFFFFF & color));
        int hexColor10Vaule = Integer.valueOf(hexColorVaule, 16);

        int hexColor10Vaule2 = getDarkerColor(hexColor10Vaule);
        String hexColorVaule2 = Integer.toHexString(hexColor10Vaule2);
        return Color.parseColor("#" + hexColorVaule2);
    }

    // 获取更深颜色
    private static int getDarkerColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv); // convert to hsv
        // make darker
        hsv[1] = hsv[1] + 0.1f; // 饱和度更高
        hsv[2] = hsv[2] - 0.1f; // 明度降低
        return Color.HSVToColor(hsv);
    }

    // 获取更浅的颜色
    private static int getBrighterColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv); // convert to hsv

        hsv[1] = hsv[1] - 0.1f; // less saturation
        hsv[2] = hsv[2] + 0.1f; // more brightness
        return Color.HSVToColor(hsv);
    }

}
