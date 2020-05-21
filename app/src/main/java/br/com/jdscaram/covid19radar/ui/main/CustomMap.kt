package br.com.jdscaram.covid19radar.ui.main

import android.content.Context
import android.location.Location
import android.util.AttributeSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng


class CustomMap : MapView {

    private val lifecycleListener = MapLifecycleObserver()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, style: Int) : super(context, attrs, style)

    fun init(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(lifecycleListener)
    }

    fun setCurrentLocation(location: Location) {
        getMapAsync {
            val latLng = LatLng(location.latitude, location.longitude)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 4f)
            it?.moveCamera(cameraUpdate)
        }
    }

    inner class MapLifecycleObserver : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            this@CustomMap.onCreate(null)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            this@CustomMap.onResume()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            this@CustomMap.onStart()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            this@CustomMap.onPause()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            this@CustomMap.onStop()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            this@CustomMap.onDestroy()
        }
    }
}