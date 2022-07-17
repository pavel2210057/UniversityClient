package com.universityclient.app.presentation.base.navigator

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

interface FragmentNavigationExecutor {

    fun Fragment.navigateUp() {
        findNavController().navigateUp()
    }

    fun Fragment.navigate(navParam: NavDirections) {
        findNavController().navigate(navParam)
    }
}
