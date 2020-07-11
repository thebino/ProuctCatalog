package de.stuermerbenjamin.productcatalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.stuermerbenjamin.productcatalog.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setContentView(binding.root)
    }
}
