package com.wodud7308.movieinfo.feature.discover

import com.google.android.material.tabs.TabLayout

abstract class BaseTabSelectedListener : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab?) {
        if(tab == null)
            return

        onTab(tab)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}

    protected abstract fun onTab(tab: TabLayout.Tab)
}
