package com.wodud7308.movieinfo.core.ui.util

import android.content.Context
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab

class EnumTabLayout<E : Enum<E>>(
    private val tabLayout: TabLayout,
    context: Context,
    entries: Iterable<E>,
    getTabString: (E, Context) -> String,
    onTabSelected: (E) -> Unit,
) {
    private val map: HashMap<Tab, E> = hashMapOf()

    init {
        entries.forEach { key ->
            addNewEnumTab(key, context, getTabString)
        }

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                if (tab == null)
                    return

                map[tab]?.let(onTabSelected)
            }

            override fun onTabUnselected(tab: Tab?) {}

            override fun onTabReselected(tab: Tab?) {}
        })
    }

    fun clear() {
        map.clear()
        tabLayout.removeAllTabs()
        tabLayout.clearOnTabSelectedListeners()
    }

    fun getTab(key: E): Tab? =
        map.entries.firstOrNull {
            it.value == key
        }?.key

    private fun addNewEnumTab(
        key: E,
        context: Context,
        getTabString: (E, Context) -> String,
    ) {
        val tab = tabLayout.newTab()
        tab.text = getTabString.invoke(key, context)
        map[tab] = key

        tabLayout.addTab(tab)
    }
}
