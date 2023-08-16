package com.nerdymykl.nerdytorch


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nerdymykl.nerdytorch.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var _binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        _binding.torchState = ViewModelProvider(this)[TorchState::class.java]
        _binding.lifecycleOwner = this
        _binding.context = this


    }




}