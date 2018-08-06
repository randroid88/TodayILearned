package io.winf.todayilearned

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil.setContentView
import io.winf.todayilearned.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val entryViewModel: EntryViewModel by lazy {
        ViewModelProviders.of(this).get(EntryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        val binding = setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.entryViewModel = entryViewModel
    }
}
