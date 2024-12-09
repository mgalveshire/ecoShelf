//package edu.arizona.cast.mollyalveshire.ecoshelf
//
//import android.location.Address
//import android.location.Geocoder
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import org.json.JSONObject
//import java.util.Locale
//
//class OptionsFragment : Fragment(), OnMapReadyCallback {
//
//    private lateinit var mMap: GoogleMap
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_options, container, false)
//        val myButton: Button = view.findViewById(R.id.btn_locations)
//        val cityInput: EditText = view.findViewById(R.id.city_input)
//        val radiusInput: EditText = view.findViewById(R.id.radius_input)
//        var message: TextView = view.findViewById(R.id.message)
//        myButton.setOnClickListener {
//            val cityName = cityInput.text.toString()
//            val radiusText = radiusInput.text.toString()
//            val radius: Int = radiusText.toIntOrNull() ?: 0
//            if (isValidZipCode(cityName)) {
//                message.setText("")
//                //val latLong: Pair<Double, Double> = getLatLongFromCity(cityName)
//                val latLong: Pair<Double, Double> = getLatLongFromZip(cityName)
//                val latitude: Double = latLong.first
//                val longitude: Double = latLong.second
//                updateMap(mMap,latitude, longitude)
//                getGoodwills(latitude,longitude, radius)
//
//            } else {
//                message.setText("Not valid zipcode")
//            }
//        }
//
//        // Get the SupportMapFragment and request notification when the map is ready
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//
//        return view
//    }
//    fun isValidZipCode(zipCode: String): Boolean {
//        // Regular expression for matching a 5-digit or 9-digit ZIP code (with or without the hyphen)
//        val regex = Regex("^\\d{5}(-\\d{4})?$")
//        return zipCode.matches(regex)
//    }
//
//
//    private fun isValidUSCity(cityName: String): Boolean {
//        val geocoder = Geocoder(requireContext(), Locale.getDefault())
//        val addresses: List<Address>?
//        try {
//            // Get a list of addresses based on the city name
//            addresses = geocoder.getFromLocationName(cityName, 1)
//            if (addresses != null && addresses.isNotEmpty()) {
//                // Check if the city is in the US by checking the country name
//                val countryName = addresses[0].countryName
//                Log.d("InventoryActivity", "${addresses[0].latitude}")
//                return countryName == "United States"
//            }
//        } catch (e: Exception) {
//            Log.d("SpecialActivity", "invalid city fail")
//            e.printStackTrace()
//        }
//        return false
//    }
//
//    private fun isValidUSZipcode(zipcode: String): Boolean {
//
//        val geocoder = Geocoder(requireContext(), Locale.getDefault())
//        val addresses: List<Address>?
//        try {
//            // Get a list of addresses based on the zipcode
//            addresses = geocoder.getFromLocationName(zipcode, 1)
//            if (addresses != null && addresses.isNotEmpty()) {
//                // Check if the address corresponds to a valid location in the U.S.
//                val countryName = addresses[0].countryName
//                val postalCode = addresses[0].postalCode
//
//                Log.d("InventoryActivity", "Latitude: ${addresses[0].latitude}, Longitude: ${addresses[0].longitude}")
//
//                // Ensure the country is the United States and the postal code matches the input
//                return countryName == "United States" && postalCode == zipcode
//            }
//        } catch (e: Exception) {
//            Log.d("SpecialActivity", "Invalid zipcode check failed")
//            e.printStackTrace()
//        }
//        return false
//    }
//    fun checkAddressResults(address: String): Boolean {
//        val client = OkHttpClient()
//        val url = "https://maps.googleapis.com/maps/api/geocode/json?address=${address}&key=AIzaSyDWsZJml04RiIh3QFZCtTo4-gRGuTCEn20"
//        val request = Request.Builder().url(url).build()
//
//        try {
//            client.newCall(request).execute().use { response ->
//                if (!response.isSuccessful) {
//                    println("Error: ${response.message}")
//                    return false
//                }
//
//                val responseBody = response.body?.string() ?: return false
//                val json = JSONObject(responseBody)
//
//                // Check if the results array has one or more items
//                val results = json.getJSONArray("results")
//                return results.length() > 0
//            }
//        } catch (e: Exception) {
//            println("Exception occurred: ${e.message}")
//            return false
//        }
//    }
//
//
//   private fun getLatLongFromCity(zipCode: String): Pair<Double, Double> {
//       val url = "https://api.opencagedata.com/geocode/v1/json?q=$zipCode&key=AIzaSyDWsZJml04RiIh3QFZCtTo4-gRGuTCEn20"
//
//       // Create an OkHttpClient to send the HTTP request
//       val client = OkHttpClient()
//       val request = Request.Builder().url(url).build()
//
//       // Execute the request
//       client.newCall(request).execute().use { response ->
//           if (!response.isSuccessful) {
//               println("Error: ${response.code}")
//               val latLongPair = Pair(1.1, 1.1)
//               return latLongPair
//           }
//           // Parse the JSON response
//           val jsonResponse = response.body?.string()
//           val jsonObject = JSONObject(jsonResponse)
//           val results = jsonObject.getJSONArray("results")
//
//           if (results.length() > 0) {
//               val geometry = results.getJSONObject(0).getJSONObject("geometry")
//               val lat = geometry.getDouble("lat")
//               val lng = geometry.getDouble("lng")
//               return Pair(lat, lng) // Return a Pair of latitude and longitude
//           }
//       }
//       val latLongPair = Pair(1.1, 1.1)
//       return latLongPair
//   }
////        val geocoder = Geocoder(requireContext(), Locale.getDefault())
////        val addresses: List<Address>?
////        // Get the list of addresses that match the city name
////        addresses = geocoder.getFromLocationName(cityName, 1)
////
////        if (addresses != null && addresses.isNotEmpty()) {
////            // Get the first address and return its latitude and longitude
////            val address = addresses[0]
////            val latitude = address.latitude
////            val longitude = address.longitude
////            val latLongPair = Pair(latitude, longitude)
////            return latLongPair
////        } else {
////            val latLongPair = Pair(1.1, 1.1)
////            return latLongPair
////        }
////    }
//
//    private fun getLatLongFromZip(zipcode: String): Pair<Double, Double> {
//        val geocoder = Geocoder(requireContext(), Locale.getDefault())
//        val addresses: List<Address>?
//
//        // Get the list of addresses that match the zipcode
//        addresses = geocoder.getFromLocationName(zipcode, 1)
//
//        if (addresses != null && addresses.isNotEmpty()) {
//            // Get the first address and return its latitude and longitude
//            val address = addresses[0]
//            val latitude = address.latitude
//            val longitude = address.longitude
//            val latLongPair = Pair(latitude, longitude)
//            return latLongPair
//        } else {
//            // Return a default value if no address found
//            val latLongPair = Pair(0.0, 0.0)
//            return latLongPair
//        }
//    }
//
//    // This method is called when the map is ready
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        // Set the map to show Tucson, Arizona (latitude: 32.2226, longitude: -110.9747)
//        val tucson = LatLng(32.2226, -110.9747)
//        mMap.addMarker(MarkerOptions().position(tucson).title("Marker in Tucson"))
//        mMap.moveCamera(
//            CameraUpdateFactory.newLatLngZoom(
//                tucson,
//                12f
//            )
//        ) // Zoom level 12 for city view
//    }
//    fun updateMap(googleMap: GoogleMap, lat: Double, long: Double){
//        mMap = googleMap
//
//        val tucson = LatLng(lat, long)
//        mMap.addMarker(MarkerOptions().position(tucson).title("Marker in Tucson"))
//        mMap.moveCamera(
//            CameraUpdateFactory.newLatLngZoom(
//                tucson,
//                12f
//            )
//        ) // Zoom level 12 for city view
//    }
//    fun getGoodwills(lat: Double, long: Double, radius: Int) {
//
//        val apiKey = "AIzaSyDWsZJml04RiIh3QFZCtTo4-gRGuTCEn20"
//
//        getGoodwillsNearby(lat, long, radius, apiKey) { goodwillStores ->
//            if (goodwillStores.isEmpty()) {
//                println("No Goodwills found within the radius.")
//
//            } else {
//                goodwillStores.forEach {
//                    println("Goodwill Store: ${it.name}, Latitude: ${it.latitude}, Longitude: ${it.longitude}")
//                }
//
//            }
//            Log.d("GoodwillStores", "Found ${goodwillStores.size} Goodwills nearby")
//        }
//    }
//
package edu.arizona.cast.mollyalveshire.ecoshelf

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class OptionsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_options, container, false)
        val myButton: Button = view.findViewById(R.id.btn_locations)
        val cityInput: EditText = view.findViewById(R.id.city_input)
        val radiusInput: EditText = view.findViewById(R.id.radius_input)
        val message: TextView = view.findViewById(R.id.message)
        // Get the SupportMapFragment and request notification when the map is ready
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        myButton.setOnClickListener {
            val cityName = cityInput.text.toString()
            val radiusText = radiusInput.text.toString()
                val radius: Int = radiusText.toIntOrNull() ?: 0
                if (true) {
                    message.setText("")
                        //val latLong: Pair<Double, Double> = getLatLongFromCity(cityName)
                    val latLong: Pair<Double, Double> = getLatLongFromZip(cityName)
                    val latitude: Double = latLong.first
                    val longitude: Double = latLong.second
                    updateMap(mMap, latitude, longitude)
                    getGoodwills(latitude, longitude, radius)

                } else {
                    message.setText("Invalid Zipcode")
                }
            }




        return view
    }



    private fun isValidUSCity(cityName: String): Boolean {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses: List<Address>?
        try {
            // Get a list of addresses based on the city name
            addresses = geocoder.getFromLocationName(cityName, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                // Check if the city is in the US by checking the country name
                val countryName = addresses[0].countryName
                Log.d("InventoryActivity", "${addresses[0].latitude}")
                return countryName == "United States"
            }
        } catch (e: Exception) {
            Log.d("SpecialActivity", "invalid city fail")
            e.printStackTrace()
        }
        return false
    }

    private fun isValidUSZipcode(zipcode: String): Boolean {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses: List<Address>?
        try {
            // Get a list of addresses based on the zipcode
            addresses = geocoder.getFromLocationName(zipcode, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                // Check if the address corresponds to a valid location in the U.S.
                val countryName = addresses[0].countryName
                val postalCode = addresses[0].postalCode

                Log.d("InventoryActivity", "Latitude: ${addresses[0].latitude}, Longitude: ${addresses[0].longitude}")

                // Ensure the country is the United States and the postal code matches the input
                return countryName == "United States" && postalCode == zipcode
            }
        } catch (e: Exception) {
            Log.d("SpecialActivity", "Invalid zipcode check failed")
            e.printStackTrace()
        }
        return false
    }


    private fun getLatLongFromCity(cityName: String): Pair<Double, Double> {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses: List<Address>?
        // Get the list of addresses that match the city name
        addresses = geocoder.getFromLocationName(cityName, 1)

        if (addresses != null && addresses.isNotEmpty()) {
            // Get the first address and return its latitude and longitude
            val address = addresses[0]
            val latitude = address.latitude
            val longitude = address.longitude
            val latLongPair = Pair(latitude, longitude)
            return latLongPair
        } else {
            val latLongPair = Pair(1.1, 1.1)
            return latLongPair
        }
    }

    private fun getLatLongFromZip(zipcode: String): Pair<Double, Double> {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses: List<Address>?

        // Get the list of addresses that match the zipcode
        addresses = geocoder.getFromLocationName(zipcode, 1)

        if (addresses != null && addresses.isNotEmpty()) {
            // Get the first address and return its latitude and longitude
            val address = addresses[0]
            val latitude = address.latitude
            val longitude = address.longitude
            val latLongPair = Pair(latitude, longitude)
            return latLongPair
        } else {
            // Return a default value if no address found
            val latLongPair = Pair(0.0, 0.0)
            return latLongPair
        }
    }

    // This method is called when the map is ready
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Set the map to show Tucson, Arizona (latitude: 32.2226, longitude: -110.9747)
        val tucson = LatLng(32.2226, -110.9747)
        mMap.addMarker(MarkerOptions().position(tucson).title("Marker in Tucson"))
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                tucson,
                12f
            )
        ) // Zoom level 12 for city view
    }
    fun updateMap(googleMap: GoogleMap, lat: Double, long: Double){
        mMap = googleMap

        val tucson = LatLng(lat, long)
        mMap.addMarker(MarkerOptions().position(tucson).title("Marker in Tucson"))
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                tucson,
                12f
            )
        ) // Zoom level 12 for city view
    }
    fun getGoodwills(lat: Double, long: Double, radius: Int) {
        val apiKey = "AIzaSyDWsZJml04RiIh3QFZCtTo4-gRGuTCEn20"

        getGoodwillsNearby(lat, long, radius, apiKey) { goodwillStores ->
            if (goodwillStores.isEmpty()) {
                Log.d("GoodwillStores", "No Goodwills found within the radius.")
            } else {
                requireActivity().runOnUiThread {
                    goodwillStores.forEach { store ->
                        val storeLocation = LatLng(store.latitude, store.longitude)
                        mMap.addMarker(MarkerOptions().position(storeLocation).title(store.name))
                    }
                    // Optionally move the camera to the first store
                    val firstStore = goodwillStores.firstOrNull()
                    firstStore?.let {
                        val firstLocation = LatLng(it.latitude, it.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 12f))
                    }
                }
            }
            Log.d("GoodwillStores", "Found ${goodwillStores.size} Goodwills nearby")
        }
    }
//    fun getGoodwills(lat: Double, long: Double, radius: Int, mMap: GoogleMap) {
//
//        val apiKey = "AIzaSyDWsZJml04RiIh3QFZCtTo4-gRGuTCEn20"
//        getGoodwillsNearby(lat, long, radius, apiKey) { goodwillStores ->
//            if (goodwillStores.isEmpty()) {
//                println("No Goodwills found within the radius.")
//
//            } else {
//                goodwillStores.forEach {
//                    println("Goodwill Store: ${it.name}, Latitude: ${it.latitude}, Longitude: ${it.longitude}")
//                }
//
//            }
//            Log.d("GoodwillStores", "Found ${goodwillStores.size} Goodwills nearby")
//        }
//    }

}


//package edu.arizona.cast.mollyalveshire.ecoshelf
//
//import android.graphics.Color.RED
//import android.location.Address
//import android.location.Geocoder
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import java.util.Locale
//
//class OptionsFragment : Fragment(), OnMapReadyCallback {
//    private lateinit var mMap: GoogleMap
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        //return inflater.inflate(R.layout.fragment_options, container, false) // Ensure you have a corresponding layout
//        val view = inflater.inflate(R.layout.fragment_options, container, false)
//        val myButton: Button = view.findViewById(R.id.btn_locations)
//        val cityInput: EditText = view.findViewById(R.id.city_input)
//        var message: TextView = view.findViewById(R.id.message)
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//        myButton.setOnClickListener {
//            val cityName = cityInput.text.toString()
//            if (isValidUSCity(cityName)) {
//                message.setText("Valid City")
//                val latLong: String = getLatLongFromCity(cityName)
//
//
//            } else {
//                message.setText("User input invalid")
//                message.setTextColor(RED)
//            }
//        }
//        return view
//    }
//    private fun isValidUSCity(cityName: String): Boolean {
//        val geocoder = Geocoder(requireContext(), Locale.getDefault())
//        val addresses: List<Address>?
//        try {
//            // Get a list of addresses based on the city name
//            addresses = geocoder.getFromLocationName(cityName, 1)
//            if (addresses != null && addresses.isNotEmpty()) {
//                // Check if the city is in the US by checking the country name
//                val countryName = addresses[0].countryName
//                return countryName == "United States"
//            }
//        } catch (e: Exception) {
//            Log.d("InventoryActivity", "invalid city fail")
//            e.printStackTrace()
//        }
//        return false
//    }
//    private fun getLatLongFromCity(cityName: String): String {
//        val geocoder = Geocoder(requireContext(), Locale.getDefault())
//        val addresses: List<Address>?
//        try {
//            // Get the list of addresses that match the city name
//            addresses = geocoder.getFromLocationName(cityName, 1)
//
//            if (addresses != null && addresses.isNotEmpty()) {
//                // Get the first address and return its latitude and longitude
//                val address = addresses[0]
//                val latitude = address.latitude
//                val longitude = address.longitude
//                val latLongPair = Pair(latitude, longitude)
//                val latLongString = latLongPair.toString()
//                return latLongString
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//
//    }
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        // Add a marker in a location (e.g., Sydney) and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f))
//    }
//}
//
//
