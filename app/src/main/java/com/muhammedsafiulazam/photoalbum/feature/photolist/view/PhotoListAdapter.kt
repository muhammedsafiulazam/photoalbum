package com.muhammedsafiulazam.photoalbum.feature.photolist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammedsafiulazam.photoalbum.R
import androidx.recyclerview.widget.GridLayoutManager
import com.muhammedsafiulazam.photoalbum.feature.photolist.listener.IPhotoListListener
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo


/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class PhotoListAdapter(val photoList: MutableList<Photo>, val albumListListener: IPhotoListListener) : RecyclerView.Adapter<PhotoListViewHolder>() {

    companion object {
        const val SPAN_SIZE: Int = 3
        private const val SEPARATOR_SIZE: Int = 8
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_photo_item, parent, false)

        val layoutParams = view.getLayoutParams() as GridLayoutManager.LayoutParams
        layoutParams.width = (parent.measuredWidth - (SEPARATOR_SIZE * 2)) / SPAN_SIZE
        layoutParams.height = layoutParams.width
        view.setLayoutParams(layoutParams)

        return PhotoListViewHolder(
            view,
            albumListListener
        )
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, index: Int) {
        val photo: Photo = photoList.get(index)

        val layoutParams = holder.itemView.getLayoutParams() as GridLayoutManager.LayoutParams

        val column = index % SPAN_SIZE

        var marginLeft = 0
        var marginRight = 0
        var marginTop = 0

        // Left.
        if (column == 0) {
            marginRight = SEPARATOR_SIZE / 2
        } else if (column == 1) {
            marginLeft = SEPARATOR_SIZE / 2
            marginRight = SEPARATOR_SIZE / 2
        } else {
            marginLeft = SEPARATOR_SIZE / 2
        }

        if (index >= SPAN_SIZE) {
            marginTop =
                SEPARATOR_SIZE
        }

        layoutParams.setMargins(marginLeft, marginTop, marginRight, 0)

        holder.bind(photo)
    }
}