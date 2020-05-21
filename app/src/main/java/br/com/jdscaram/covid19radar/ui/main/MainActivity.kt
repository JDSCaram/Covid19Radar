package br.com.jdscaram.covid19radar.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import br.com.jdscaram.covid19radar.R
import br.com.jdscaram.covid19radar.databinding.MainActivityBinding

private const val REQUEST_CODE = 767

class MainActivity : AppCompatActivity() {

    private val permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION)
    private lateinit var binding: MainActivityBinding

    override fun onStart() {
        super.onStart()
        if (!hasPermission()) requestPermissions()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.apply {
            initMainFragmentOrPermissionView(this)
        }
    }

    private fun initMainFragmentOrPermissionView(binding: MainActivityBinding) {
        with(binding) {
            if (hasPermission()) {
                handleMainFragment(this)
            } else {
                handlePermissionAlert(this)
            }
        }
    }

    private fun handlePermissionAlert(binding: MainActivityBinding) {
        binding.container.visibility = View.GONE
        binding.permission.root.visibility = View.VISIBLE
        binding.permission.root.setOnClickListener {
            requestPermissions()
        }
    }

    private fun handleMainFragment(binding: MainActivityBinding) {
        binding.container.visibility = View.VISIBLE
        binding.permission.root.visibility = View.GONE

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
    }

    private fun requestPermissions() {
        val convertedPermissions = arrayOfNulls<String>(permissions.size)
        for (i in permissions.indices) {
            convertedPermissions[i] = permissions[i]
        }
        ActivityCompat.requestPermissions(this, convertedPermissions, REQUEST_CODE)
    }

    private fun hasPermission(): Boolean =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.any { it == PackageManager.PERMISSION_GRANTED }) {
                handleMainFragment(binding)
            } else {
                handlePermissionAlert(binding)
            }
        }
    }
}