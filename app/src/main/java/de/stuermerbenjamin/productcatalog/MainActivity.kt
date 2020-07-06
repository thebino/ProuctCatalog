package de.stuermerbenjamin.productcatalog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
