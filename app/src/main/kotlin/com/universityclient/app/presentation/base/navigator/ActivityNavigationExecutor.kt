package com.universityclient.app.presentation.base.navigator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

interface ActivityNavigationExecutor {

    fun AppCompatActivity.navigateUp() {
        onBackPressedDispatcher.onBackPressed()
    }

    fun AppCompatActivity.navigate(navParam: Intent) {
        startActivity(navParam)
    }

    fun AppCompatActivity.close() {
        finish()
    }
}
