package com.muhammedsafiulazam.photoalbum.picture

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.muhammedsafiulazam.photoalbum.addon.AddOn
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.context.IContextManager
import com.muhammedsafiulazam.vinci.Vinci
import com.muhammedsafiulazam.vinci.VinciCallback
import com.squareup.picasso.Callback
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

class PictureManager() : AddOn(), IPictureManager {

    companion object {
        // Loader types.
        const val LOADER_VINCI = 100
        const val LOADER_PICASSO = 101
    }

    // Loader
    private var loader: Int = LOADER_VINCI

    // Picasso instance.
    val mPicasso: Picasso by lazy {
        val contextManager: IContextManager? = AddOnManager.getAddOn(AddOnType.CONTEXT_MANAGER) as IContextManager?
        val context: Context? = contextManager?.getContext()
        val builder = Picasso.Builder(context!!)
        // Maximize cache.
        builder.downloader(OkHttp3Downloader(context, Long.MAX_VALUE))
        val built = builder.build()
        Picasso.setSingletonInstance(built)
        Picasso.get()
    }

    // Vinci instance.
    val mVinci: Vinci by lazy {
        Vinci.get()
    }

    /**
     * Specify type of loader
     * @param loader loader type
     */
    override fun useLoader(loader: Int) {
        this.loader = loader
    }

    /**
     * Load image from url.
     * @param url associate url
     * @param imageView associate image view
     * @param callback associate callback
     */
    override fun load(url: String, imageView: ImageView?, callback: PictureCallback?) {
        if (loader == LOADER_PICASSO) {
            mPicasso.load(url).into(object: Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    callback?.onFailure(url, e!!.cause!!)
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    callback?.onSuccess(url, bitmap!!)
                }
            })
        } else {
            mVinci.load(url, imageView, object: VinciCallback {
                override fun onSuccess(url: String, bitmap: Bitmap) {
                    callback?.onSuccess(url, bitmap)
                }

                override fun onFailure(url: String, throwable: Throwable) {
                    callback?.onFailure(url, throwable)
                }
            })
        }
    }
}