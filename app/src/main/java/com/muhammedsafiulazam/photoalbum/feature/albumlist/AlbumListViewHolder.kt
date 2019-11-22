package com.muhammedsafiulazam.photoalbum.feature.albumlist

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class AlbumListViewHolder(view: View, albumListListener: IAlbumListListener) : RecyclerView.ViewHolder(view){
    private var mView: View
    private var mTxvTitle: AppCompatTextView
    private var mPgbLoader: ProgressBar
    private var mImvThumbnail: AppCompatImageView
    private var mAlbum: Album? = null

    init {
        mView = view
        mTxvTitle = view.findViewById(R.id.album_txv_title)
        mPgbLoader = view.findViewById(R.id.album_pgb_loader)
        mImvThumbnail = view.findViewById(R.id.album_imv_thumbnail)
        mPgbLoader.visibility = View.GONE

        view.setOnClickListener {
            albumListListener.onClickAlbum(mAlbum!!)
        }
    }

    fun bind(album: Album) {
        mAlbum = album

        mTxvTitle.text = mView.context!!.getString(R.string.albumist_album_title, mAlbum!!.id)

        mPgbLoader.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            Picasso.get().load(mAlbum!!.thumbnailUrl).into(mImvThumbnail, object: Callback {
                override fun onSuccess() {
                    mPgbLoader.visibility = View.GONE
                    mImvThumbnail.scaleType = ImageView.ScaleType.FIT_CENTER
                }

                override fun onError(e: Exception) {
                    mPgbLoader.visibility = View.GONE
                    mImvThumbnail.scaleType = ImageView.ScaleType.CENTER
                    mImvThumbnail.setImageResource(R.drawable.ic_cloud_off_black)
                }
            })
        }
    }
}