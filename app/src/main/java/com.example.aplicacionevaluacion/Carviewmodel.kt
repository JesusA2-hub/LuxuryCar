package com.example.aplicacionevaluacion

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CarViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val carsCollection = db.collection("cars")

    private val _cars = MutableStateFlow<List<Car>>(emptyList())
    val cars: StateFlow<List<Car>> = _cars

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchCarsRealtime()
    }

    private fun fetchCarsRealtime() {
        carsCollection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                _isLoading.value = false
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val carList = snapshot.documents.mapNotNull { it.toCarObject() }
                _cars.value = carList
                _isLoading.value = false
            }
        }
    }

    private fun DocumentSnapshot.toCarObject(): Car? {
        return try {
            val car = this.toObject(Car::class.java)
            car?.copy(documentId = this.id)
        } catch (e: Exception) {
            null
        }
    }

    fun saveCar(car: Car, onComplete: (Boolean) -> Unit) {
        val data = car.toMap()
        val ref = if (car.documentId.isNullOrBlank()) {
            carsCollection.document()
        } else {
            carsCollection.document(car.documentId!!)
        }
        ref.set(data)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun deleteCar(documentId: String, onComplete: (Boolean) -> Unit) {
        if (documentId.isBlank()) {
            onComplete(false)
            return
        }
        carsCollection.document(documentId)
            .delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun savePurchase(purchase: Purchase, onComplete: (Boolean) -> Unit) {
        db.collection("purchases")
            .add(purchase)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }
}

data class Purchase(
    val carId: String = "",
    val brand: String = "",
    val model: String = "",
    val price: Long = 0L,
    val buyerName: String = "",
    val buyerEmail: String = "",
    val cardLast4: String = "",
    val purchaseDate: Long = System.currentTimeMillis()
)

private fun Car.toMap(): Map<String, Any?> = mapOf(
    "brand" to brand,
    "model" to model,
    "year" to year,
    "price" to price,
    "seller" to seller,
    "legalStatus" to legalStatus,
    "warranty" to warranty
)
