package com.alexey.notes.note

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexey.notes.Constants
import com.alexey.notes.databinding.ActivityNotePagerBinding
import com.alexey.notes.db.AppDataBase

class NotePagerActivity : AppCompatActivity(), HomeButtonSupport {

    private lateinit var binding: ActivityNotePagerBinding
    private var pagerAdapter = NotePagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotePagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        pagerAdapter.dataBase = AppDataBase.getDataBase(this)
        pagerAdapter.items = ArrayList(pagerAdapter.dataBase.noteDao().getAll())

        binding.viewPager.apply {
            adapter = pagerAdapter
            setCurrentItem(intent.getIntExtra(Constants.NOTE_POSITION, 0), false)
        }
    }

    override fun showHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun hideHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}