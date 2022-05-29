package com.x.module_main.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.x.module_main.R
import com.x.module_main.databinding.ItemArticleBinding
import com.x.module_main.logic.model.DataX

/**
 * @desc
 * @author wei
 * @date  2022/3/4
 **/
class ArticleAdapter(private val datas: List<DataX>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemBinding: ItemArticleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_article,
            parent,
            false
        )
        return ArticleViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val data: DataX = datas[position]
        holder.itemBinding.article = data
        holder.itemBinding.adapter = this
    }

    override fun getItemCount(): Int {
        return datas.size
    }


    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var itemBinding: ItemArticleBinding

        constructor(itemBinding: ItemArticleBinding) : this(itemBinding.root) {
            this.itemBinding = itemBinding
        }
    }

    fun getAuthor(author: String, shareUser: String) : String {
        return if (author.isEmpty()) {
            "推荐者: $shareUser"
        } else {
            "作者: $author"
        }
    }
}