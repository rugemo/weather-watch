package com.russellmorris.getlocation.ui.view

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.russellmorris.extensions.*
import com.russellmorris.getlocation.R
import com.russellmorris.getlocation.injectFeature
import com.russellmorris.getlocation.ui.model.City
import com.russellmorris.getlocation.ui.viewmodel.GetLocationViewModel
import com.russellmorris.location.LocationProvider
import com.russellmorris.location.LocationProviderImpl
import com.russellmorris.location.LocationResultListener
import com.russellmorris.presentation.base.BaseFragment
import com.russellmorris.presentation.base.BaseViewModel
import kotlinx.android.synthetic.main.get_location_fragment.*
import org.koin.androidx.viewmodel.ext.viewModel

class GetLocationFragment : BaseFragment(), LocationResultListener {

    private val getLocationViewModel: GetLocationViewModel by viewModel()
    lateinit var locationProvider: LocationProvider

    private val itemClick: (City) -> Unit =
        {
            if (findNavController().currentDestination?.id == R.id.locationFragment) {
                getLocationViewModel.navigate(
                    GetLocationFragmentDirections.actionLaunchesFragmentToDetailFragment(
                        it.lat.toString(), it.lon.toString(), it.name, it.countryCode
                    )
                )
            }
        }

    private val snackBar by lazy {
        Snackbar.make(cityList, "Error", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") { }
    }

    private val adapter = CitiesAdapter(itemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationProvider = LocationProviderImpl(requireContext(), this)
        locationProvider.initialiseLocationClient()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.get_location_fragment, container, false)
    }

    override fun getViewModel(): BaseViewModel = getLocationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectFeature()
        locationPermissionButton.setOnClickListener{_ -> activity?.let {
            locationProvider.getLocation(it)
        } }
        cityList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getLocationViewModel.cities.observe(this, Observer { updateLaunches(it) })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cityList.adapter = null
        cityList.layoutManager?.removeAllViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater?.inflate(R.menu.search_menu, menu)
        val searchView = SearchView(requireContext())
        menu.findItem(R.id.search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                getLocationViewModel.getCities(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
        searchView.setOnClickListener {view -> }
    }

    override fun locationPermissionPreviouslyDeniedWithNeverAskAgain() {
        val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val settingUri = Uri.fromParts("package", activity?.packageName, null)
        settingsIntent.setData(settingUri)
        startActivity(settingsIntent)
    }

    override fun setLocation(location: Location) {
        launchNextView(location.latitude, location.longitude)
    }

    private fun launchNextView(latitude: Double?, longitude: Double?) {
        if (findNavController().currentDestination?.id == R.id.locationFragment) {
            getLocationViewModel.navigate(
                GetLocationFragmentDirections.actionLaunchesFragmentToDetailFragment(
                    latitude.toString(), longitude.toString()
                )
            )
        }
    }

    private fun updateLaunches(resource: Resource<List<City>>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> cityList.invisible()
                ResourceState.SUCCESS -> cityList.visible()
                ResourceState.ERROR -> cityList.visible()
            }
            it.data?.let { updateAdapter(it) }
            it.message?.let { snackBar.show() }
        }
    }

    private fun updateAdapter(list: List<City>) {
        adapter.submitList(list)
    }

}
