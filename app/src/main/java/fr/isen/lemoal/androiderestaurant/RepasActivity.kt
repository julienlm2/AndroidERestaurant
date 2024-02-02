package fr.isen.lemoal.androiderestaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.Request.Method.POST
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.lemoal.androiderestaurant.model.DishResult
import fr.isen.lemoal.androiderestaurant.model.Items
import fr.isen.lemoal.androiderestaurant.ui.theme.AndroidERestaurantTheme
import org.json.JSONObject
import kotlin.math.log

class RepasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val menuType = intent.getStringExtra("menuType")
            val title = intent.getStringExtra("title")
            val meals = remember { mutableStateOf(listOf<Items>()) }

            fetchMenu { fetchedMeals ->
                meals.value = fetchedMeals
            }

            AndroidERestaurantTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = title ?: "",
                            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(50.dp)) // Add a big space here
                        MealList(meals.value)
                    }
                }
            }
        }
    }

    @Composable
    fun MealList(meals: List<Items>) {
        val context = LocalContext.current // Get the current context
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(meals) { meal ->
                Text(
                    text = meal.nameFr ?: "",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable {
                            showToast("${meal.nameFr} clicked") // Pass the context to the showToast function

                            val intent = Intent(context, DetailActivity::class.java)
                            intent.putExtra("itemId", meal.id) // Pass the item id
                            context.startActivity(intent) // Use the context to start the activity
                        }
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            MaterialTheme.shapes.medium
                        )
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    private fun fetchMenu(callback: (List<Items>) -> Unit) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        val url = "http://test.api.catering.bluecodegames.com/menu"

        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")

        // Request a string response from the provided URL.
        val stringRequest = JsonObjectRequest(
            POST, url, jsonObject,
            { response ->
                // Parse the response
                val gson = Gson()
                val dishResult = gson.fromJson(response.toString(), DishResult::class.java)
                val meals = dishResult.data.firstOrNull { it.nameFr == intent.getStringExtra("menuType")}?.items
                meals?.let { callback(it) }
            },
            { error ->
                Log.e("fetchMenu", "Error fetching meals", error)
            }
        )

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}