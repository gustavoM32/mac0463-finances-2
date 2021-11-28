package com.gustavodmcarlos.finances

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gustavodmcarlos.finances.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("userInfo", MODE_PRIVATE)
        val userName = sharedPref?.getString("displayName", "user")
        val userEmail = sharedPref?.getString("email", "user")
        val photoUrl = sharedPref?.getString("photoUrl", "user")
        binding.userName.text = userName
        binding.userEmail.text = userEmail

        Glide.with(this)
            .load(photoUrl)
            .into(binding.userPicture)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

}