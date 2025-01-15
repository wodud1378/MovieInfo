package com.wodud7308.movieinfo.core.ui.layout

import android.content.Context
import com.google.android.material.tabs.TabLayout

class EnumTabLayout<E : Enum<E>>(
    private val tabLayout: TabLayout,
    context: Context,
    entries: Iterable<E>,
    getTabString: (E, Context) -> String,
    private val onTabSelected: (E) -> Unit,
) {
    private val map: HashMap<TabLayout.Tab, E> = hashMapOf()

    init {
        entries.forEach { key ->
            addNewEnumTab(key, context, getTabString)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null)
                    return

                map[tab]?.let(onTabSelected)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun clear() {
        map.clear()
        tabLayout.removeAllTabs()
        tabLayout.clearOnTabSelectedListeners()
    }

    fun getTab(tab: E): TabLayout.Tab? =
        map.entries.firstOrNull {
            it.value == tab
        }?.key

    fun selectTab(tab: E) {
        map.entries.firstOrNull {
            it.value == tab
        }?.let {
            onTabSelected.invoke(tab)
            tabLayout.selectTab(it.key)
        }
    }

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
