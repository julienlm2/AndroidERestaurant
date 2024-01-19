// Add imports as needed
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import fr.isen.lemoal.androiderestaurant.R

class EntreeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entree)

        // Fetch entree meal data (replace with your actual data)
        val entreeMeals = listOf("Entree Meal 1", "Entree Meal 2", "Entree Meal 3")

        // Get the ListView from the layout
        val entreeListView: ListView = findViewById(R.id.entreeList)

        // Create an ArrayAdapter to populate the ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, entreeMeals)

        // Set the adapter for the ListView
        entreeListView.adapter = adapter
    }
}