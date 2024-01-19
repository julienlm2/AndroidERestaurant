package fr.isen.lemoal.androiderestaurant

import EntreeActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.isen.lemoal.androiderestaurant.ui.theme.AndroidERestaurantTheme
import androidx.compose.material3.MaterialTheme
import android.content.Intent
import androidx.compose.ui.platform.LocalContext

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                val Context = LocalContext.current
                // Use Box to set the background image for the entire app
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    // Image of a pizza with clip and content scale
                    Image(
                        painter = painterResource(id = R.drawable.pizzabackground),
                        contentDescription = "Pizza",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Image of a pizza with clip and content scale

                        Text(
                            text = "Bienvenue chez EL PIZZAIOLO",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier
                                .padding(8.dp)
                                .padding(8.dp)
                                .background(color = Color.Black) // Set the background color here
                        )
                        Image(
                            painter = painterResource(id = R.drawable.pizza),
                            contentDescription = "Pizza Image",
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .clip(shape = MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop
                        )

                        // Title

                        // Buttons for ENTREE, PLAT, DESSERT
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            // I want the buttons to be centered horizontally
                            Button(
                                onClick = {
                                    // Handle button click for ENTREE
                                    showToast("ENTREE clicked")

                                    Context.startActivity(Intent(this@HomeActivity, EntreeActivity::class.java))



                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Yellow
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text("ENTREE")
                            }
                            // I want to add spaces between the buttons
                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    // Handle button click for PLAT
                                    showToast("PLAT clicked")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Yellow
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text("PLAT")
                            }

                            Button(
                                onClick = {
                                    // Handle button click for DESSERT
                                    showToast("DESSERT clicked")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Yellow
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text("DESSERT")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

