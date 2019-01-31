package com.sys1yagi.fragmentfactorywithlivedata.searchbook

import com.sys1yagi.fragmentfactorywithlivedata.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.list_search_result.view.*

class BookItem(private val title: String) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.list_search_result
    }

    override fun bind(viewHolder: com.xwray.groupie.ViewHolder, position: Int) {
        viewHolder.root.title.text = title
    }
}
