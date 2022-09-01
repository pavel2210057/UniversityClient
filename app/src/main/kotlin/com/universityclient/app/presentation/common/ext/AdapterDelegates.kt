package com.universityclient.app.presentation.common.ext

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

fun <I : Any, T : AdapterDelegate<List<I>>> listAdapterDelegates(
    vararg delegates: T,
    diffCallback: DiffUtil.ItemCallback<I> = defaultDiffCallback()
) =
    AsyncListDifferDelegationAdapter(
        diffCallback,
        *delegates
    )

private fun<T : Any> defaultDiffCallback() = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem

    override fun areContentsTheSame(oldItem: T, newItem: T) = true
}
