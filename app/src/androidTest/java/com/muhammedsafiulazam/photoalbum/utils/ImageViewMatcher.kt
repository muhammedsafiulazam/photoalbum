package com.muhammedsafiulazam.photoalbum.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


class ImageViewMatcher {
    companion object {
        fun withDrawable(resourceId: Int): Matcher<View> = DrawableMatcher(resourceId)

        fun noDrawable(): Matcher<View> = DrawableMatcher(-1)
    }

    open class DrawableMatcher(private val resourceId: Int) : TypeSafeMatcher<View>() {
        private var expectedId = 0

        init {
            expectedId = resourceId
        }

        override fun describeTo(description: Description?) {
            description?.appendText("with drawable from resource id: ")
            description?.appendValue(expectedId)
        }

        override fun matchesSafely(item: View?): Boolean {
            if (!(item != null && item is ImageView))
                return false

            val imageView = item as ImageView
            if (expectedId < 0)
                return imageView.drawable == null

            val resources = item.getContext().resources
            val expectedDrawable = resources.getDrawable(expectedId) ?: return false
            val bitmap = getBitmap(imageView.drawable)
            val otherBitmap = getBitmap(expectedDrawable)
            return bitmap.sameAs(otherBitmap)
        }

        private fun getBitmap(drawable: Drawable): Bitmap {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
    }
}