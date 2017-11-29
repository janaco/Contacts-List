package com.nandy.contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;

/**
 * Created by yana on 29.11.17.
 */

public class BitmapUtils {


    public static Bitmap drawTextToBitmap(Context context, String text) {
        return drawTextToBitmap(context, text, 120, 120,
                ContextCompat.getColor(context, R.color.colorPrimary),
                Color.WHITE, 14);
    }


    public static Bitmap drawTextToBitmap(Context context,
                                          String gText, int width, int height, int backgroundColor, int textColor, float textSize) {
        float density = context.getResources().getDisplayMetrics().density;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);
        float roundPx = 100 * density;

        Paint paintCircle = new Paint();
        paintCircle.setColor(backgroundColor);
        paintCircle.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paintCircle);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize * density);

        Rect textBounds = new Rect();
        textPaint.getTextBounds(gText, 0, gText.length(), textBounds);

        int x = (bitmap.getWidth() - textBounds.width()) / 2;
        int y = (bitmap.getHeight() + textBounds.height()) / 2;

        canvas.drawText(gText, x, y, textPaint);

        return bitmap;
    }

    public static Bitmap convertToCircle(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.WHITE;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

}
