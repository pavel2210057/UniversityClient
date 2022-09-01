package com.universityclient.app.presentation.profile.adapter

import android.content.res.ColorStateList
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.universityclient.app.R
import com.universityclient.app.databinding.ItemAccountListBinding
import com.universityclient.app.presentation.common.ext.resolveColor

fun accountSingleTitleListItemDelegate(
    onClicked: (AccountListItem.SingleTitleItem) -> Unit
) = adapterDelegateViewBinding<AccountListItem.SingleTitleItem, AccountListItem,
        ItemAccountListBinding>(
    viewBinding = { inflater, root -> ItemAccountListBinding.inflate(inflater, root, false) },
) {

    binding.root.setOnClickListener { onClicked(item) }

    bind { _ ->
        binding.textViewTitle.text = getString(item.titleRes)
        binding.imageViewIcon.setImageResource(item.iconRes)

        if (item.isWarning) {
            val alertColor = context.resolveColor(R.attr.alertColor)
            binding.textViewTitle.setTextColor(alertColor)
            binding.imageViewIcon.imageTintList = ColorStateList.valueOf(alertColor)
        } else {
            binding.textViewTitle.setTextColor(
                context.resolveColor(androidx.appcompat.R.attr.colorPrimaryDark)
            )
            binding.imageViewIcon.imageTintList = null
        }
    }
}
