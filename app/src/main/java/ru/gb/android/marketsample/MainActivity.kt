package ru.gb.android.marketsample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.gb.android.marketsample.clean.CleanActivity
import ru.gb.android.marketsample.databinding.ActivityMainBinding
import ru.gb.android.marketsample.layered.LayeredActivity
import ru.gb.android.marketsample.start.StartActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener {
            val intent = Intent(this@MainActivity, StartActivity::class.java)
            startActivity(intent)
        }

        binding.layered.setOnClickListener {
            val intent = Intent(this@MainActivity, LayeredActivity::class.java)
            startActivity(intent)
        }

        binding.clean.setOnClickListener {
            val intent = Intent(this@MainActivity, CleanActivity::class.java)
            startActivity(intent)
        }
    }
}