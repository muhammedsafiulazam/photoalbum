package com.muhammedsafiulazam.photoalbum.feature.photolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import androidx.recyclerview.widget.GridLayoutManager
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo


/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class PhotoListAdapter(val photoList: MutableList<Photo>, val albumListListener: IPhotoListListener) : RecyclerView.Adapter<PhotoListViewHolder>() {

    companion object {
        const val SPAN_SIZE: Int = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_photo_item, parent, false)

        val layoutParams = view.getLayoutParams() as GridLayoutManager.LayoutParams
        layoutParams.width = parent.measuredWidth / SPAN_SIZE
        layoutParams.height = layoutParams.width
        view.setLayoutParams(layoutParams)

        return PhotoListViewHolder(view, albumListListener)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        val photo: Photo = photoList.get(position);
        holder.bind(photo)
    }
}