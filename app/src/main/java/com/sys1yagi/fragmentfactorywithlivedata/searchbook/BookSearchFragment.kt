package com.sys1yagi.fragmentfactorywithlivedata.searchbook

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sys1yagi.fragmentfactorywithlivedata.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_search_result.*

@SuppressLint("ValidFragment")
class BookSearchFragment(val query: LiveData<String>) : Fragment() {

    lateinit var viewModel: BookSearchViewModel

    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookSearchViewModel::class.java)
        viewModel.searchBook(query).observe({ lifecycle }) {
            if (it.isSuccess) {
                val result = it.getOrNull() ?: return@observe
                adapter.clear()
                adapter.addAll(result.map(::BookItem))
            } else {
                // show error
            }
        }
        viewModel.viewState().observe({ lifecycle }) {
            when (it) {
                BookSearchViewModel.ViewState.PROGRESS -> {
                    progress_circular.isVisible = true
                    recycler_view.isVisible = false
                }
                else -> {
                    progress_circular.isVisible = false
                    recycler_view.isVisible = true
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
    }

}
