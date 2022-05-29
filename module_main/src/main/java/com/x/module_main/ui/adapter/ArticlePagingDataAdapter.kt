package com.x.module_main.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.x.module_main.R
import com.x.module_main.databinding.PagingItemArticleBinding
import com.x.module_main.logic.model.DataX

/**
 * @desc
 * @author wei
 * @date  2022/3/4
 **/
class ArticlePagingDataAdapter :
    PagingDataAdapter<DataX, ArticlePagingDataAdapter.ArticleViewHolder>(DIFF_CALLBACK)
{
    var itemClickBlock : ((DataX)->Unit)? = null

    companion object {
        //差分，只更新需要更新的元素，而不是整个刷新数据源
        val DIFF_CALLBACK: DiffUtil.ItemCallback<DataX> =
            object : DiffUtil.ItemCallback<DataX>() {
                override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemBinding: PagingItemArticleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.paging_item_article,
            parent,
            false
        )
        return ArticleViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val datax: DataX? = getItem(position)
        holder.itemBinding.article = datax
        holder.itemBinding.adapter = this
    }


    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var itemBinding: PagingItemArticleBinding

        constructor(itemBinding: PagingItemArticleBinding) : this(itemBinding.root) {
            this.itemBinding = itemBinding
        }
    }

    fun getAuthor(author: String, shareUser: String): String {
        return if (author.isEmpty()) {
            "推荐者: $shareUser"
        } else {
            "作者: $author"
        }
    }

    fun itemClick(article: DataX) {
        itemClickBlock?.invoke(article)
    }
}