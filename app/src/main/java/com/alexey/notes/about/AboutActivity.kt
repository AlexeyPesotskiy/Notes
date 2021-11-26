package com.alexey.notes.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexey.notes.R

/**
 * Экран "О приложении"
 */
class AboutActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}