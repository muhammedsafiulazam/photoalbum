package com.muhammedsafiulazam.photoalbum.feature.albumlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import androidx.recyclerview.widget.GridLayoutManager



/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class AlbumListAdapter(val albumList: MutableList<Album>, val albumListListener: IAlbumListListener) : RecyclerView.Adapter<AlbumListViewHolder>() {

    companion object {
        const val SPAN_SIZE: Int = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_album_item, parent, false)

        val layoutParams = view.getLayoutParams() as GridLayoutManager.LayoutParams
        layoutParams.width = parent.measuredWidth / SPAN_SIZE
        layoutParams.height = layoutParams.width
        view.setLayoutParams(layoutParams)

        return AlbumListViewHolder(view, albumListListener)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {
        val album: Album = albumList.get(position);
        holder.bind(album)
    }
}