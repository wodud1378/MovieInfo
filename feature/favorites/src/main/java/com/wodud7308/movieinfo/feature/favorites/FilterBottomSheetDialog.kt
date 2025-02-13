package com.wodud7308.movieinfo.feature.favorites

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.radiobutton.MaterialRadioButton
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.util.getString
import com.wodud7308.movieinfo.feature.favorites.databinding.BottomSheetDialogFilterBinding

class FilterBottomSheetDialog(
    activity: Activity,
    private val initialValue: Filter
) : BottomSheetDialog(activity) {
    private lateinit var binding: BottomSheetDialogFilterBinding
    private var itemClickListener: ItemClickListener<Filter>? = null

    fun setItemClickListener(listener: ItemClickListener<Filter>) {
        itemClickListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        binding = BottomSheetDialogFilterBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        with(binding.radioGroupFilter) {
            Filter.entries.forEach { filter ->
                val button = MaterialRadioButton(context).apply {
                    id = View.generateViewId()
                    text =
                        filter.mediaType?.getString(context)
                            ?: context.getString(R.string.filter_all)
                    setOnClickListener {
                        itemClickListener?.onClick(filter)
                        dismiss()
                    }
                }

                addView(button)

                if(filter == initialValue) {
                    check(button.id)
                }
            }
        }

    }
}
