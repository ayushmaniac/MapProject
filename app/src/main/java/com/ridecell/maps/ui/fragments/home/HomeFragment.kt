package com.ridecell.maps.ui.fragments.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.ridecell.maps.R
import com.ridecell.maps.di.component.FragmentComponent
import com.ridecell.maps.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

import android.graphics.Canvas

import android.graphics.Bitmap
import com.google.android.gms.maps.model.*
import com.ridecell.maps.ui.customdialog.CustomDialog


class HomeFragment : BaseFragment<HomeViewModel>(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    companion object{
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val DEFAULT_ZOOM = 0
    }
    private var locationPermissionGranted = false
    private lateinit var googleMaps : GoogleMap
    private lateinit var placesClient : PlacesClient
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private var lastKnownLocation: Location? = null
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)

    private val raipurAirport : LatLng  = LatLng(21.1859, 81.7403)
    private val raipurRailwayStation : LatLng = LatLng(21.2571,81.6296)
    private val raipurUniversity : LatLng = LatLng(21.2469, 81.5974)

    private val arrayListOfMarkers = ArrayList<LatLng>()
    var toShowOtherMarkers = true



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
        constructPlaces()
    }

    private fun constructPlaces() {
        // Construct a PlacesClient
        placesClient = Places.createClient(requireActivity())
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(requireActivity())

        //Create arraylist
        arrayListOfMarkers.add(raipurAirport)
        arrayListOfMarkers.add(raipurRailwayStation)
        arrayListOfMarkers.add(raipurUniversity)
    }

    override fun provideLayoutId(): Int = R.layout.fragment_home

    override fun injectDependencies(fragmentComponent: FragmentComponent) = fragmentComponent.inject(this)

    override fun setupView(view: View) {
        ivCenter.setOnClickListener {
            rotateCameraToCurrentPlace()
        }
    }

    private fun rotateCameraToCurrentPlace() {
        googleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(
            LatLng(lastKnownLocation!!.latitude,
                lastKnownLocation!!.longitude), 50F))
    }

    override fun onMapReady(map: GoogleMap) {
        googleMaps = map
        map.setOnMarkerClickListener(this)
        map.setOnMapClickListener(this)
        updateLocationUI()
        getLocationPermission()
        getDeviceLocation()
        drawDifferentMarkers(toShowOtherMarkers)

    }

    private fun drawDifferentMarkers(toShow : Boolean) {
        if(toShow){
            for( i in 0 until arrayListOfMarkers.size){
                googleMaps.addMarker(MarkerOptions().position(arrayListOfMarkers[i]).icon(BitmapFromVector(
                    requireContext(), R.drawable.ic_doremon
                )))
            }
        }
        else
        {
            googleMaps.clear()
        }

    }

    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(requireContext().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    private fun updateLocationUI() {
        if (!this::googleMaps.isInitialized) {
            return
        }
        try {
            if (locationPermissionGranted) {
                googleMaps.isMyLocationEnabled = true
                googleMaps.uiSettings.isMyLocationButtonEnabled = true
            } else {
                googleMaps.isMyLocationEnabled = false
                googleMaps.uiSettings.isMyLocationButtonEnabled = false
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            googleMaps.addMarker(MarkerOptions().position(getLatLongObject(
                                lastKnownLocation!!
                            )).title("Here I am"))
                            googleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        Log.d("HomeFragment", "Current location is null. Using defaults.")
                        Log.e("HomeFragment", "Exception: %s", task.exception)
                        googleMaps.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                        googleMaps.uiSettings.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getLatLongObject(lastKnownLocation: Location): LatLng {
        return LatLng(
            lastKnownLocation.latitude,
            lastKnownLocation.longitude
        )
    }


    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        // Retrieve the data from the marker.
        if(marker.position == lastKnownLocation?.let { getLatLongObject(it) }){
            zoomMapInitial(marker.position)
        }
        else
        {
            val bundle = Bundle()
            bundle.putString("LAT",marker.position.latitude.toString())
            bundle.putString("LONG",marker.position.longitude.toString())
            CustomDialog.getInstance(bundle).show(childFragmentManager, "CUSTOM_DIALOG")
        }
        return true
    }

    private fun zoomMapInitial(latLang: LatLng) {
        googleMaps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang, 50F))

    }

    override fun onMapClick(latLong: LatLng) {
        if(toShowOtherMarkers){
            toShowOtherMarkers = false
            drawDifferentMarkers(toShowOtherMarkers)
        }
        else{
            toShowOtherMarkers = true
            drawDifferentMarkers(toShowOtherMarkers)
            showMarkerOnCurrentLocation()

        }
    }

    private fun showMarkerOnCurrentLocation() {
        googleMaps.addMarker(MarkerOptions().position(getLatLongObject(
            lastKnownLocation!!
        )).title("Here I am"))
    }
}