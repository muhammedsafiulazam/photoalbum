package com.muhammedsafiulazam.photoalbum.feature.albumlist.view

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.feature.albumlist.listener.IAlbumListListener
import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import com.muhammedsafiulazam.photoalbum.picture.IPictureManager
import com.muhammedsafiulazam.photoalbum.picture.PictureCallback
import com.muhammedsafiulazam.vinci.Vinci
import com.muhammedsafiulazam.vinci.VinciCallback

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class AlbumListViewHolder(view: View, listener: IAlbumListListener) : RecyclerView.ViewHolder(view){
    private var mPictureManager: IPictureManager
    private var mView: View
    private var mTxvTitle: AppCompatTextView
    private var mPgbLoader: ProgressBar
    private var mImvThumbnail: AppCompatImageView
    private var mAlbum: Album? = null

    init {
        mPictureManager = AddOnManager.getAddOn(AddOnType.PICTURE_MANAGER) as IPictureManager

        mView = view
        mTxvTitle = view.findViewById(R.id.album_txv_title)
        mPgbLoader = view.findViewById(R.id.album_pgb_loader)
        mImvThumbnail = view.findViewById(R.id.album_imv_thumbnail)
        mPgbLoader.visibility = View.GONE

        view.setOnClickListener {
            listener.onClickAlbum(mAlbum!!)
        }
    }

    fun bind(album: Album) {
        mAlbum = album

        mTxvTitle.text = mView.context!!.getString(R.string.albumist_album_title, mAlbum!!.id)
        mImvThumbnail.setImageDrawable(null)
        mPgbLoader.visibility = View.VISIBLE

        mPictureManager.load(mAlbum!!.thumbnailUrl!!, mImvThumbnail, object: PictureCallback {
            override fun onSuccess(url: String, bitmap: Bitmap) {
                mPgbLoader.visibility = View.GONE
                mImvThumbnail.scaleType = ImageView.ScaleType.CENTER_CROP
            }

            override fun onFailure(url: String, throwable: Throwable) {
                mPgbLoader.visibility = View.GONE
                mImvThumbnail.scaleType = ImageView.ScaleType.CENTER
                mImvThumbnail.setImageResource(R.drawable.ic_cloud_off_black)
            }

        })
    }
}