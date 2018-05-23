package com.mt.robot.tuling;

import android.content.Context;

public class DensityUtil {

    public static int dip2px(Context context, float dpValue) {//根据手机的分辨率从 dp 的单位 转成为 px(像素)
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static int px2dip(Context context, float pxValue) {//根据手机的分辨率从 px(像素) 的单位 转成为 dp
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
