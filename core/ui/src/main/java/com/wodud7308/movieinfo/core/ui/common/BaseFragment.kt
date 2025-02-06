package com.wodud7308.movieinfo.core.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.wodud7308.movieinfo.core.ui.R
import com.wodud7308.movieinfo.core.ui.model.Message
import kotlinx.coroutines.launch

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding>(
    private val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {
    protected abstract val viewModel: VM

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateLayout(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.messageFlow.collect { message -> onMessageReceived(message) }
                }
            }
        }
    }

    protected open fun onMessageReceived(message: Message) {
        val text = when(message.type) {
            MessageType.Error -> {
                message.extraText?: ""
            }
            MessageType.FavoriteAdded -> getString(R.string.favorite_added)
            MessageType.FavoriteDeleted -> getString(R.string.favorite_deleted)
        }

        showSnackBar(text)
    }

    protected fun showSnackBar(text: String) {
        getSnackBar(text).show()
    }

    protected fun showSnackBar(text: String, actionText: String, action: ()-> Unit) {
        getSnackBar(text).setAction(actionText) {
            action.invoke()
        }.show()
    }

    private fun getSnackBar(text: String): Snackbar =
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT)
}
