package com.example.gugulumaps.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.example.gugulumaps.entities.CoordinatesEntity
import com.example.gugulumaps.entities.MarkerEntity

@Dao
interface MarkerDao {

    @Insert
    suspend fun insertMarker(marker: MarkerEntity)

    @Insert
    suspend fun insertCoordinates(coordinate: CoordinatesEntity)

    @Transaction
    @Query("SELECT * FROM markers")
    suspend fun getMarkersWithCoordinates(): List<MarkerWithCoordinates>
}

data class MarkerWithCoordinates(
    @Embedded val marker: MarkerEntity,
    @Relation(
        parentColumn = "coordinates_id",
        entityColumn = "id"
    )
    val coordinates: CoordinatesEntity
)
