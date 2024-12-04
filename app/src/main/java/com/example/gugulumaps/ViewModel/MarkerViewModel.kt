package com.example.gugulumaps.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gugulumaps.dao.MarkerDao
import com.example.gugulumaps.dao.MarkerWithCoordinates
import com.example.gugulumaps.entities.CoordinatesEntity
import com.example.gugulumaps.entities.MarkerEntity
import kotlinx.coroutines.launch

class MarkerViewModel(application: Application) : AndroidViewModel(application) {

    private val markerDao: MarkerDao = MarkerDatabase.getDatabase(application).markerDao()

    private val _markersWithCoordinates = MutableLiveData<List<MarkerWithCoordinates>>()
    val markersWithCoordinates: LiveData<List<MarkerWithCoordinates>> get() = _markersWithCoordinates

    fun loadMarkers() {
        viewModelScope.launch {
            _markersWithCoordinates.value = markerDao.getMarkersWithCoordinates()
        }
    }

    fun addMarker(marker: MarkerEntity, coordinates: CoordinatesEntity) {
        viewModelScope.launch {
            val coordinatesId = markerDao.insertCoordinates(coordinates)
            markerDao.insertMarker(marker.copy(coordinatesId = coordinatesId))
            loadMarkers()
        }
    }
}
