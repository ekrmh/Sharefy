package com.sharefy.android.ui.fragment.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
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

import android.util.TypedValue

import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import android.graphics.drawable.BitmapDrawable

import android.graphics.Bitmap
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.graphics.drawable.toBitmap
import com.sharefy.android.model.Category


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

    val items by lazy {
        mutableListOf<Category>().apply{
            add(Category(name = getString(R.string.all)).apply {
                docId = "-1"
            })
            addAll(viewModel.appSession.categories.orEmpty())
        }
    }

    private val spinnerAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            items
        )
    }

    private val markerClickListener = GoogleMap.OnMarkerClickListener { marker ->
        when (marker.tag) {
            is Advert -> {
                MarkerDetailBottomSheetFragment(marker.tag as Advert, closeCallback = {
                    markerCenter?.showInfoWindow()
                }) { advert ->
                    viewModel.goToContributeFragment(advert)
                }.show(parentFragmentManager, "TAG")
            }
            is LatLng -> {
                //Nothing
                marker.showInfoWindow()
            }
        }
        true
    }


    override fun onReady(savedInstanceState: Bundle?) {
        binding.spinnerCategory.adapter = spinnerAdapter
        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val category = items[p2]

                    map.clear()
                    initCenterMarker()
                    val list = viewModel.advertList.value ?: return
                    addMarkersForAdverts(if (category.docId == "-1") list else list.filter { it.category.docId == category.docId })
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        initMap()

        viewModel.advertList.observeNonNull(this) { list ->
            addMarkersForAdverts(list)
        }
    }

    private fun addMarkersForAdverts(list: List<Advert>) {
        list.forEach { advert: Advert ->
            val drawable = getMarkerDrawable(requireContext(), R.drawable.ic_dot, Color.parseColor(
                advert.category.markerColor
            ))?.toBitmap()
            map.addMarker(
                MarkerOptions()
                    .position(LatLng(advert.lat, advert.long))
                    .icon(BitmapDescriptorFactory.fromBitmap(drawable))

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
        googleMap.setOnMapClickListener {
            markerCenter?.showInfoWindow()
        }
        googleMap.setOnMarkerClickListener(markerClickListener)
        googleMap.setOnInfoWindowClickListener(this)

        initCenterMarker()
        enableMyLocation()
        viewModel.fetchAllAdvertData()
    }

    private fun initCenterMarker() {
        val markerOptions = MarkerOptions()
            .position(map.cameraPosition.target)
            .title(getString(R.string.add_new_advert))
            .draggable(true)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.help))

        markerCenter?.remove()
        markerCenter = map.addMarker(markerOptions)
            .apply {
                showInfoWindow()
                tag = map.cameraPosition.target
            }

        map.setOnCameraMoveListener {
            markerCenter?.position = map.cameraPosition.target
            markerCenter?.tag = map.cameraPosition.target
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
    }

    fun getMarkerDrawable(context: Context, drawableResId: Int, colorFilter: Int): Drawable? {
        val drawable = getDrawable(context, drawableResId)
        drawable?.setColorFilter(
            colorFilter,
            PorterDuff.Mode.SRC_IN
        )
        return drawable
    }

}