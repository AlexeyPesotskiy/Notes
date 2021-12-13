package com.alexey.notes.note

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.alexey.notes.Constants
import com.alexey.notes.databinding.ActivityNotePagerBinding
import com.alexey.notes.db.AppDataBase

class NotePagerActivity : AppCompatActivity(), HomeButtonSupport {

    private lateinit var binding: ActivityNotePagerBinding
    private var adapter = NotePagerAdapter(this)
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotePagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        adapter.dataBase = AppDataBase.getDataBase(this)
        adapter.items = ArrayList(adapter.dataBase.noteDao().getAll())

        viewPager = binding.viewPager
        viewPager.adapter = adapter

        viewPager.setCurrentItem(intent.getIntExtra(Constants.NOTE_POSITION, 0), false)
    }

    override fun showHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun hideHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}