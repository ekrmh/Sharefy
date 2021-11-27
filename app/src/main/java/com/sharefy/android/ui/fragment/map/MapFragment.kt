package com.sharefy.android.ui.fragment.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.sharefy.android.R
import com.sharefy.android.base.BaseFragment
import com.sharefy.android.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint
import com.sharefy.android.model.Advert
import com.sharefy.android.ui.fragment.map.marker_detail.MarkerDetailBottomSheetFragment
import observeNonNull
import com.google.android.gms.maps.model.MarkerOptions


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>(),
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback,
    GoogleMap.OnInfoWindowClickListener {

    override val layoutId: Int = R.layout.fragment_map

    override val viewModel: MapViewModel by viewModels()

    private lateinit var map: GoogleMap

    private var markerCenter: Marker? = null


    private val markerClickListener = GoogleMap.OnMarkerClickListener { marker ->
        when (marker.tag) {
            is Advert -> {
                MarkerDetailBottomSheetFragment(marker.tag as Advert) {
                    markerCenter?.showInfoWindow()
                }.show(parentFragmentManager, "Marker Detail Bottom Sheet Fragment")
            }
            is LatLng -> {
                //Nothing
            }
        }
        true
    }


    override fun onReady(savedInstanceState: Bundle?) {
        initMap()

        viewModel.advertList.observeNonNull(this) { list ->
            addMarkersForAdverts(list)
        }
    }

    private fun addMarkersForAdverts(list: List<Advert>) {
        list.forEach { advert: Advert ->
            map.addMarker(
                MarkerOptions()
                    .position(LatLng(advert.lat, advert.long))

            ).apply {
                hideInfoWindow()
                tag = advert
            }
        }
    }


    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun moveCamera(location: LatLng) {
        val update = CameraUpdateFactory.newLatLngZoom(
            location,
            10f
        )
        map.animateCamera(update)
    }

    override fun onInfoWindowClick(marker: Marker?) {
        marker?.let { t ->
            val latLng = t.tag as LatLng
            viewModel.goToAddNewAdvertFragment(latLng)
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onMyLocationClick(location: Location) {
        moveCamera(LatLng(location.latitude, location.longitude))
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)

        map.setOnMarkerClickListener(markerClickListener)
        map.setOnInfoWindowClickListener(this)

        initCenterMarker()
        enableMyLocation()
        viewModel.fetchAllAdvertData()
    }

    private fun initCenterMarker() {
        val markerOptions = MarkerOptions()
            .position(map.cameraPosition.target)
            .title("Yeni bir ilan eklemek icin buraya tiklayin")
            .draggable(true)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))


        markerCenter = map.addMarker(markerOptions)
            .apply {
                showInfoWindow()
                tag = map.cameraPosition.target
            }

        map.setOnCameraMoveListener {
            markerCenter!!.position = map.cameraPosition.target
            markerCenter!!.tag = map.cameraPosition.target
        }
    }


    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        } else {
            // REQUEST PERMISSION HERE

        }
        // [END maps_check_location_permission]
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


}