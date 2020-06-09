package cn.xhu.www.setting.ui.adapter

import android.net.Uri
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.xhu.www.setting.databinding.WallpaperImageRecycleItemBinding
import com.bumptech.glide.Glide

class WallpaperAdapter : RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {
    var items: List<Uri> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            WallpaperImageRecycleItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: WallpaperImageRecycleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUri: Uri) {
//            binding.image = imageUri
            Glide.with(binding.imageView).load(imageUri).into(binding.imageView)
        }
    }
}
