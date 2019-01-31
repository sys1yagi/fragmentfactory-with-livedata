package com.sys1yagi.fragmentfactorywithlivedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.MutableLiveData
import com.sys1yagi.fragmentfactorywithlivedata.searchbook.BookSearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val query = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            val factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String, args: Bundle?): Fragment {
                    return when (className) {
                        BookSearchFragment::class.java.name -> {
                            BookSearchFragment(query = query)
                        }
                        else -> {
                            super.instantiate(classLoader, className, args)
                        }
                    }
                }
            }

            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        factory.instantiate(classLoader, BookSearchFragment::class.java.name, null)
                    }
                    1 -> {
                        factory.instantiate(classLoader, BookSearchFragment::class.java.name, null)
                    }
                    else -> {
                        TODO("illegal position ${position}")
                    }
                }
            }

            override fun getCount() = 2

            override fun getPageTitle(position: Int): CharSequence? {
                return when (position) {
                    0 -> {
                        "作品名でさがす"
                    }
                    1 -> {
                        "作家名でさがす"
                    }
                    else -> {
                        TODO("illegal position ${position}")
                    }
                }
            }
        }
        tab.setupWithViewPager(view_pager)

        search.setOnClickListener {
            val text = form.text.toString()
            if (text.isEmpty()) {
                return@setOnClickListener
            }
            query.postValue(text)
        }

        query.value = form.text.toString()
    }
}
