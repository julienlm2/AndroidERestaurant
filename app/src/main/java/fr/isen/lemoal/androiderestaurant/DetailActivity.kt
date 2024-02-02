package fr.isen.lemoal.androiderestaurant

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        setContent {
            val itemId = intent.getStringExtra("itemId")
            val item = remember { mutableStateOf<Items?>(null) }

            fetchItemDetails(itemId, item)

            AndroidERestaurantTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item.value?.let {
                            Text(
                                text = it.nameFr ?: "",
                                style = MaterialTheme.typography.headlineLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                            Text(
                                text = "${it.prices.firstOrNull()?.price} €" ?: "", // Concatenate € to the price string
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                            // Display the ingredients
                            it.ingredients.forEach { ingredient ->
                                Text(
                                    text = ingredient.nameFr ?: "Unknown Ingredient",
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center, // Center the text
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )


                            }
                            Image(
                                    painter = rememberImagePainter(data = it.images.firstOrNull()),
                            contentDescription = it.nameFr ?: "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth() // Use fillMaxWidth() instead of fillMaxSize()
                                .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }


    private fun fetchItemDetails(itemId: String?, item: MutableState<Items?>) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu" // Replace with your API endpoint

        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")

        Log.d("fetchItemDetails", "Request Body: $jsonObject") // Log the request body

        // Request a string response from the provided URL.
        val stringRequest = JsonObjectRequest(
            POST, url, jsonObject,
            { response ->
                // Parse the response
                Log.d("fetchItemDetails", "Response: $response") // Log the response

                val gson = Gson()
                val dishResult = gson.fromJson(response.toString(), DishResult::class.java)
                val fetchedItem = dishResult.data.flatMap { it.items }.firstOrNull { it.id == itemId }
                fetchedItem?.let { item.value = it } // Update the state
            },
            { error ->
                Log.e("fetchItemDetails", "Error fetching item details", error)
                error.networkResponse?.data?.let { data ->
                    val str = String(data, Charsets.UTF_8)
                    Log.e("fetchItemDetails", "Error response body: $str") // Log the error response body
                }
            }
        )

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}