package com.ramadan.challenge.feature.restaurants

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.ramadan.challenge.core.navigator.AppNavigator
import com.ramadan.challenge.core.navigator.Screens
import com.ramadan.challenge.databinding.ActivityMapsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    @Inject
    lateinit var appNavigator: AppNavigator
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null)
            appNavigator.navigateTo(Screens.MAP)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0)
            finish()
    }

    fun displayError(message: String?) {
        message?.let { Snackbar.make(binding.mainView, it, Snackbar.LENGTH_SHORT).show() }
    }

    fun handleLoading(isDisplayed: Boolean) {
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
}
