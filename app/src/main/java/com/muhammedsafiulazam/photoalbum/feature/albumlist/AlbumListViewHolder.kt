package com.muhammedsafiulazam.photoalbum.feature.albumlist

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
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
    private var mImvCover: AppCompatImageView
    private var mPgbLoader: ProgressBar
    private var mAlbum: Album? = null

    init {
        mView = view
        mImvCover = view.findViewById(R.id.album_imv_cover)
        mPgbLoader = view.findViewById(R.id.album_pgb_loader)
        mPgbLoader.visibility = View.GONE

        view.setOnClickListener {
            albumListListener.onClickAlbum(mAlbum!!)
        }
    }

    fun bind(album: Album) {
        mAlbum = album

        mPgbLoader.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            Picasso.get().load(mAlbum!!.photos!!.first().thumbnailUrl).into(mImvCover, object: Callback {
                override fun onSuccess() {
                    mPgbLoader.visibility = View.GONE
                }

                override fun onError(e: Exception) {
                    mPgbLoader.visibility = View.GONE
                }
            })
        }
    }
}