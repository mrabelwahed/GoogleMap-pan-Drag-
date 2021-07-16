package com.ramadan.challenge.feature.restaurants.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ramadan.challenge.R
import com.ramadan.challenge.core.common.BaseFragment
import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.core.navigator.AppNavigator
import com.ramadan.challenge.core.navigator.Screens
import com.ramadan.challenge.databinding.FragmentMapRestaurantsBinding
import com.ramadan.challenge.domain.entity.Restaurant
import com.ramadan.challenge.domain.error.Failure
import com.ramadan.challenge.feature.restaurants.map.drag.IDragCallback
import dagger.hilt.android.AndroidEntryPoint
import permissions.dispatcher.*
import timber.log.Timber
import javax.inject.Inject

@RuntimePermissions
@AndroidEntryPoint
class MapRestaurantsFragment : BaseFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, IDragCallback {
    @Inject
    lateinit var appNavigator: AppNavigator
     private var mMap: GoogleMap? =null
    private val restaurantMapViewModel: RestaurantMapViewModel by viewModels()
    private val openSettingsActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            getCurrentLocation()
        }

    private val openLocationSettingsScreen = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        getCurrentLocation()
    }

    private var _binding: FragmentMapRestaurantsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapRestaurantsBinding.inflate(inflater, container, false)
        _binding?.root?.setDrag(this)
        restaurantMapViewModel.fragCreated = (savedInstanceState != null)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        this.getCurrentLocationWithPermissionCheck()
    }

    private fun observerRestaurants() {
        restaurantMapViewModel.restaurantsDataState.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is DataState.Success -> {
                        getRootActivity().handleLoading(false)
                        renderMarkers(it.data)
                    }
                    is DataState.Error -> {
                        getRootActivity().handleLoading(false)
                        if (it.error is Failure.NetworkConnection)
                            getRootActivity().displayError(getString(R.string.no_internet_connection))
                        else
                            getRootActivity().displayError(getString(R.string.general_error))
                    }
                    is DataState.Loading -> getRootActivity().handleLoading(true)
                }
            }
        )
    }

    private fun renderMarkers(venues: List<Restaurant>) {
        val markersToBeDisplayed = ArrayList<Restaurant>()
        val mainList = restaurantMapViewModel.markers.values
       venues.forEach {
           if (!mainList.contains(it))
              markersToBeDisplayed.add(it)
       }
        if(restaurantMapViewModel.fragCreated && markersToBeDisplayed.isEmpty()){
            mainList.forEach {
                val markerLoc = LatLng(it.latitude, it.longitude)
                val marker = mMap?.addMarker(MarkerOptions().position(markerLoc).title(it.name))
                mMap?.moveCamera(CameraUpdateFactory.newLatLng(markerLoc))
            }
        }else{
            markersToBeDisplayed.forEach {
                val markerLoc = LatLng(it.latitude, it.longitude)
                val marker = mMap?.addMarker(MarkerOptions().position(markerLoc).title(it.name))
                mMap?.moveCamera(CameraUpdateFactory.newLatLng(markerLoc))
                if (marker!=null)
                    restaurantMapViewModel.markers[marker] = it
            }
        }
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun getCurrentLocation() {
        if (isLocationEnabled()) {
            getUserLocation { location ->
                Timber.e("available lat is %s , lon is %s", location.latitude, location.longitude)
                val currentBounds = mMap?.projection?.visibleRegion?.latLngBounds
                val latlng = LatLng(location.latitude, location.longitude)
                currentBounds?.let {  restaurantMapViewModel.getRestaurants(Dto(latlng, it)) }

            }
        } else {
            MaterialAlertDialogBuilder(getRootActivity())
                .setTitle(getString(R.string.enable_location))
                .setMessage(R.string.location_disabled)
                .setPositiveButton(getString(R.string.enable)) {
                    dialog, _ ->
                    openSettingsScreen()

                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.ignore)) {
                    dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showRationaleForLocation(request: PermissionRequest) {
        MaterialAlertDialogBuilder(getRootActivity())
            .setMessage(R.string.permission_alert)
            .setPositiveButton(getString(R.string.accept)) { dialog, _ ->
                request.proceed()
                dialog.dismiss()
            }.setNegativeButton(getString(R.string.deny)) { dialog, _ ->
                request.cancel()
                dialog.dismiss()
            }.show()
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onLocationDenied() {
        Toast.makeText(activity, getString(R.string.location_permission_denied), Toast.LENGTH_SHORT)
            .show()
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onLocationNeverAskAgain() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", activity?.packageName, null)
        intent.data = uri
        openSettingsActivity.launch(intent)
    }

    private fun openSettingsScreen() {
        openLocationSettingsScreen.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.setOnMarkerClickListener(this)
        mMap?.setMinZoomPreference(15f)
        mMap?.isMyLocationEnabled = true
        observerRestaurants()
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        appNavigator.navigateTo(Screens.RESTAURANT_DETAILS, restaurantMapViewModel.markers[marker])
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        this.onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDrag() {
        if(restaurantMapViewModel.fragCreated)
             restaurantMapViewModel.fragCreated = false
        val currentLatLng = mMap?.cameraPosition?.target
        val currentBounds = mMap?.projection?.visibleRegion?.latLngBounds
        restaurantMapViewModel.resetRestaurantsDataState()
        if(currentBounds !=null && currentLatLng!=null)
          restaurantMapViewModel.getRestaurants(Dto(currentLatLng, currentBounds))
        Timber.e("current latlng %s , %s",currentLatLng?.longitude , currentLatLng?.longitude)
    }






}
