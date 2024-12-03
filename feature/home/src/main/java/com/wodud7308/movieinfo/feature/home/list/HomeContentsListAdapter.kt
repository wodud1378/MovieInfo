package com.wodud7308.movieinfo.feature.home.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.wodud7308.movieinfo.core.domain.common.PopularContentType
import com.wodud7308.movieinfo.core.domain.common.TrendingContentType
import com.wodud7308.movieinfo.core.ui.common.BaseListAdapter
import com.wodud7308.movieinfo.core.ui.common.BaseViewHolder
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.content.ContentCardListAdapter
import com.wodud7308.movieinfo.core.ui.deco.SpaceItemDecoration
import com.wodud7308.movieinfo.feature.home.R
import com.wodud7308.movieinfo.feature.home.databinding.HolderHomeContentsBinding
import com.wodud7308.movieinfo.feature.home.model.HomeContentsModel
import com.wodud7308.movieinfo.feature.home.model.HomeContentsType
import com.wodud7308.movieinfo.feature.home.model.HomeUiModel
import com.wodud7308.movieinfo.feature.home.util.getTabStringId

class HomeContentsListAdapter(
    private val context: Context,
    private val data: HomeUiModel,
    private val onClickTab: ItemClickListener<Enum<*>>
) : BaseListAdapter<HomeContentsModel, HomeContentsListAdapter.ViewHolder>(diffCallback) {
    inner class ViewHolder(binding: HolderHomeContentsBinding) :
        BaseViewHolder<HomeContentsModel, HolderHomeContentsBinding>(binding) {
        private val buttonEnumMap: HashMap<Int, Enum<*>> = hashMapOf()
        override fun setData(item: HomeContentsModel) {
            with(binding) {
                val adapter = ContentCardListAdapter()

                title.text = context.getString(item.type.title)

                setupToggleButtons(item, context, toggles) { selectedEnum ->
                    onClickTab.onClick(selectedEnum)
                }

                list.adapter = adapter
                list.addItemDecoration(
                    SpaceItemDecoration(
                        context,
                        8,
                        SpaceItemDecoration.SpaceApply(right = true)
                    )
                )

                adapter.submitList(item.list)
            }
        }

        private fun setupToggleButtons(
            item: HomeContentsModel,
            context: Context,
            toggles: MaterialButtonToggleGroup,
            onClickTab: (Enum<*>) -> Unit
        ) {
            val entries = when (item.type) {
                HomeContentsType.Trending -> TrendingContentType.entries
                HomeContentsType.Popular -> PopularContentType.entries
            }

            entries.forEach { enumValue ->
                val button =
                    MaterialButton(ContextThemeWrapper(context, R.style.HomeContentToggle)).apply {
                        text = context.getString(enumValue.getTabStringId())
                        id = View.generateViewId()
                        isChecked = item.tab == enumValue
                    }
                buttonEnumMap[button.id] = enumValue
                toggles.addView(button)
            }

            toggles.addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    buttonEnumMap[checkedId]?.let { onClickTab(it) }
                }
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<HomeContentsModel>() {
            override fun areItemsTheSame(
                oldItem: HomeContentsModel,
                newItem: HomeContentsModel
            ): Boolean = hasSameTitle(oldItem, newItem)

            override fun areContentsTheSame(
                oldItem: HomeContentsModel,
                newItem: HomeContentsModel
            ): Boolean = hasSameTitle(oldItem, newItem) && oldItem.list == newItem.list

            private fun hasSameTitle(
                oldItem: HomeContentsModel,
                newItem: HomeContentsModel
            ): Boolean = oldItem.type.title == newItem.type.title
        }
    }

    override fun getItemCount(): Int = data.list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        HolderHomeContentsBinding.inflate(LayoutInflater.from(parent.context), parent, false).run {
            return ViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(data.list[holder.bindingAdapterPosition])
    }
}
