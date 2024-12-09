package edu.arizona.cast.mollyalveshire.ecoshelf

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

// Data class for storing the place information
data class GoodwillStore(val name: String, val latitude: Double, val longitude: Double)

fun getGoodwillsNearby(lat: Double, lon: Double, radiusMeters: Int, apiKey: String, callback: (List<GoodwillStore>) -> Unit,) {
    val radius = radiusMeters * 1609.34
    val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$lat,$lon&radius=$radius&type=store&keyword=thrift+store&key=$apiKey"

    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            callback(emptyList())  // Return empty list on failure
        }

        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                Log.e("API Error", "Request failed with status code: ${response.code}")
                callback(emptyList())  // Return empty list on failure
                return
            }

            val jsonResponse = response.body?.string() ?: ""
            if (jsonResponse.isEmpty()) {
                Log.e("API Error", "Empty response body")
                callback(emptyList())  // Return empty list if response is empty
                return
            }

            Log.d("JSON Response", jsonResponse)  // Log the raw JSON for debugging

            try {
                val jsonObject = JSONObject(jsonResponse)
                val results = jsonObject.getJSONArray("results")

                val goodwillStores = mutableListOf<GoodwillStore>()
                for (i in 0 until results.length()) {
                    val place = results.getJSONObject(i)
                    val name = place.getString("name")
                    val latitude = place.getJSONObject("geometry").getJSONObject("location").getDouble("lat")
                    val longitude = place.getJSONObject("geometry").getJSONObject("location").getDouble("lng")
                    goodwillStores.add(GoodwillStore(name, latitude, longitude))
                }

                // Ensure callback is called on the main thread
                android.os.Handler(android.os.Looper.getMainLooper()).post {
                    callback(goodwillStores)  // Return the list of Goodwill stores
                }
            } catch (e: Exception) {
                Log.e("API Error", "Error parsing JSON response", e)
                callback(emptyList())  // Return empty list if there's a parsing error
            }
        }
    })
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            callback(emptyList())  // Return empty list on failure
        }

        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                Log.e("API Error", "Request failed with status code: ${response.code}")
                callback(emptyList())  // Return empty list on failure
                return
            }

            val jsonResponse = response.body?.string() ?: ""
            if (jsonResponse.isEmpty()) {
                Log.e("API Error", "Empty response body")
                callback(emptyList())  // Return empty list if response is empty
                return
            }

            Log.d("JSON Response", jsonResponse)  // Log the raw JSON for debugging

            try {
                val jsonObject = JSONObject(jsonResponse)
                val results = jsonObject.getJSONArray("results")

                val goodwillStores = mutableListOf<GoodwillStore>()
                for (i in 0 until results.length()) {
                    val place = results.getJSONObject(i)
                    val name = place.getString("name")
                    val latitude = place.getJSONObject("geometry").getJSONObject("location").getDouble("lat")
                    val longitude = place.getJSONObject("geometry").getJSONObject("location").getDouble("lng")
                    goodwillStores.add(GoodwillStore(name, latitude, longitude))
                }

                // Ensure callback is called on the main thread
                android.os.Handler(android.os.Looper.getMainLooper()).post {
                    callback(goodwillStores)  // Return the list of Goodwill stores
                }
            } catch (e: Exception) {
                Log.e("API Error", "Error parsing JSON response", e)
                callback(emptyList())  // Return empty list if there's a parsing error
            }
        }
    })
}
