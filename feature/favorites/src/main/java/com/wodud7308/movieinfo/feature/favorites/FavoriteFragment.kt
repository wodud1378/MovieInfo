package com.wodud7308.movieinfo.feature.favorites

import android.content.Context
import androidx.fragment.app.viewModels
import com.wodud7308.movieinfo.core.ui.common.BaseFragment
import com.wodud7308.movieinfo.core.ui.layout.EnumTabLayout
import com.wodud7308.movieinfo.core.ui.util.getString
import com.wodud7308.movieinfo.feature.favorites.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(
    FragmentFavoriteBinding::inflate
) {
    private val viewModel: FavoriteViewModel by viewModels()

    private var tab: EnumTabLayout<FavoriteTab>? = null

    private fun initTab() {
        tab?.clear()
        tab = EnumTabLayout(
            binding.mediaTab,
            requireContext(),
            FavoriteTab.entries,
            ::getTabString
        ) { type ->
            viewModel.setMediaType(type.mediaType)
        }
    }

    private fun getTabString(tab: FavoriteTab, context: Context): String =
        when (tab) {
            FavoriteTab.All -> ""
            else -> tab.mediaType?.getString(context) ?: ""
        }

    override fun onDestroyView() {
        super.onDestroyView()

        tab?.clear()
        tab = null
    }
}
