package com.alexey.notes.about

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alexey.notes.R
import com.alexey.notes.databinding.ActivityAboutBinding
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource

/**
 * Экран "О приложении"
 */
class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about)

        init()
    }

    private fun init() = with(binding) {
        webView.loadUrl(URL)

        customTextView.setOnClickListener {
            startAnimation(it)
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    }

    /**
     * Запускает анимацию для вью
     *
     * @param view объект для которого запустится анимация
     */
    private fun startAnimation(view: View) {
        val y = ObjectAnimator.ofFloat(view, View.SCALE_Y, SCALE_FROM, SCALE_TO, SCALE_FROM)
        val x = ObjectAnimator.ofFloat(view, View.SCALE_X, SCALE_FROM, SCALE_TO, SCALE_FROM)

        val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, ALPHA_FROM, ALPHA_TO, ALPHA_FROM)

        AnimatorSet().apply {
            playTogether(y, x, alpha)
            duration = DURATION_MS
            interpolator = AccelerateInterpolator()
            start()
        }
    }

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            when {
                it[Manifest.permission.ACCESS_FINE_LOCATION] != true
                        && it[Manifest.permission.ACCESS_COARSE_LOCATION] != true ->
                    showToast(R.string.location_access_required)

                isLocationEnabled() -> showUserLocation()
                else -> showToast(R.string.turn_on_location)
            }
        }

    @SuppressLint("MissingPermission")
    fun showUserLocation() {
        LocationServices.getFusedLocationProviderClient(this)
            .getCurrentLocation(
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token
            )
            .addOnSuccessListener {
                binding.userLocation.text = it.latitude.toString() + " " + it.longitude
            }
            .addOnFailureListener {
                showToast(R.string.location_failed)
            }
    }

    /**
     * Проверяет включена ли геолокация на устройстве
     */
    private fun isLocationEnabled(): Boolean =
        (getSystemService(Context.LOCATION_SERVICE) as LocationManager).let {
            it.isProviderEnabled(LocationManager.GPS_PROVIDER) || it.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        }

    /**
     * Показать всплывающее сообщение
     *
     * @param resId идентификатор строкового ресурса сообщения
     */
    private fun showToast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val URL = "file:///android_asset/about.html"

        private const val SCALE_FROM = 1f
        private const val SCALE_TO = 0.85f

        private const val ALPHA_FROM = 1f
        private const val ALPHA_TO = 0.7f

        private const val DURATION_MS = 1800L
    }
}