package com.nandy.contacts

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat

/**
 * Created by yana on 03.12.17.
 */
class BitmapUtils {

    companion object Transformations {

        @JvmStatic
        fun drawTextToBitmap(context: Context, text: String): Bitmap {
            return drawTextToBitmap(context, text, 120, 120,
                    ContextCompat.getColor(context, R.color.colorPrimary),
                    Color.WHITE, 14f)
        }

        @JvmStatic
        fun drawTextToBitmap(context: Context, text: String, width: Int, height: Int, backgroundColor: Int,
                             textColor: Int, textSize: Float): Bitmap {

            val density = context.resources.displayMetrics.density

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            val rect = Rect(0, 0, width, height)
            val rectF = RectF(rect)
            val roundPx = 100 * density

            val paintCircle = Paint()
            paintCircle.color = backgroundColor
            paintCircle.isAntiAlias = true

            canvas.drawARGB(0, 0, 0, 0)
            canvas.drawRoundRect(rectF, roundPx, roundPx, paintCircle)

            val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            textPaint.color = textColor
            textPaint.textSize = textSize * density

            val textBounds = Rect()
            textPaint.getTextBounds(text, 0, text.length, textBounds)

            val x: Float = ((bitmap.width - textBounds.width()) / 2).toFloat()
            val y: Float = ((bitmap.height + textBounds.height()) / 2).toFloat()

            canvas.drawText(text, x, y, textPaint)

            return bitmap
        }

        @JvmStatic
        fun convertToCircle(bitmap: Bitmap): Bitmap {

            val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(output)

            val color = Color.WHITE
            val paint = Paint()
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            val rectF = RectF(rect)

            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color
            canvas.drawOval(rectF, paint)

            paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
            canvas.drawBitmap(bitmap, rect, rect, paint)

            bitmap.recycle()

            return output
        }

    }

}