package fr.isen.lemoal.androiderestaurant

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.android.volley.Request.Method.POST
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.lemoal.androiderestaurant.model.DishResult
import fr.isen.lemoal.androiderestaurant.model.Items
import fr.isen.lemoal.androiderestaurant.ui.theme.AndroidERestaurantTheme
import org.json.JSONObject

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemId = intent.getStringExtra("itemId") // Retrieve the item id
        fetchItemDetails(itemId) { item ->
            setContent {
                AndroidERestaurantTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        // Display the item image
                        item.images.firstOrNull()?.let { imageUrl ->
                            Image(
                                painter = rememberImagePainter(data = imageUrl),
                                contentDescription = "Item Image",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun fetchItemDetails(itemId: String?, callback: (Items) -> Unit) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        val url = "http://test.api.catering.bluecodegames.com/menu" // Replace with your API endpoint

        val jsonObject = JSONObject()
        jsonObject.put("id_item", itemId)

        // Request a string response from the provided URL.
        val stringRequest = JsonObjectRequest(
            POST, url, jsonObject,
            { response ->
                // Parse the response
                val gson = Gson()
                val dishResult = gson.fromJson(response.toString(), DishResult::class.java)
                val item = dishResult.data.flatMap { it.items }.firstOrNull { it.id == itemId }
                item?.let { callback(it) }
            },
            { error ->
                Log.e("fetchItemDetails", "Error fetching item details", error)
            }
        )

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}