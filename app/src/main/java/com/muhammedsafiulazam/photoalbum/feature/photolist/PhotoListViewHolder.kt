package com.muhammedsafiulazam.photoalbum.feature.photolist

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class PhotoListViewHolder(view: View, albumListListener: IPhotoListListener) : RecyclerView.ViewHolder(view){
    private var mView: View
    private var mImvCover: AppCompatImageView
    private var mPgbLoader: ProgressBar
    private var mPhoto: Photo? = null

    init {
        mView = view
        mImvCover = view.findViewById(R.id.photo_imv_thumbnail)
        mPgbLoader = view.findViewById(R.id.photo_pgb_loader)
        mPgbLoader.visibility = View.GONE

        view.setOnClickListener {
            albumListListener.onClickPhoto(mPhoto!!)
        }
    }

    fun bind(photo: Photo) {
        mPhoto = photo

        mPgbLoader.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            Picasso.get().load(mPhoto!!.thumbnailUrl).into(mImvCover, object: Callback {
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