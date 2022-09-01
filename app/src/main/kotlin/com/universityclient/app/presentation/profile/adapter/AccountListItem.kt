package com.universityclient.app.presentation.profile.adapter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.universityclient.app.R

sealed class AccountListItem {

    sealed class SingleTitleItem(
        @StringRes val titleRes: Int,
        @DrawableRes val iconRes: Int,
        val isWarning: Boolean
    ) : AccountListItem() {

        object MyGroup : SingleTitleItem(
            titleRes = R.string.profileScreen_accountList_myGroup,
            iconRes = R.drawable.ic_group,
            isWarning = false
        )

        object MyCourses : SingleTitleItem(
            titleRes = R.string.profileScreen_accountList_myCourses,
            iconRes = R.drawable.ic_book,
            isWarning = false
        )

        object MyDebts : SingleTitleItem(
            titleRes = R.string.profileScreen_accountList_myDebts,
            iconRes = R.drawable.ic_exclamation,
            isWarning = true
        )

        object Settings : SingleTitleItem(
            titleRes = R.string.profileScreen_accountList_settings,
            iconRes = R.drawable.ic_gear,
            isWarning = false
        )
    }
}
