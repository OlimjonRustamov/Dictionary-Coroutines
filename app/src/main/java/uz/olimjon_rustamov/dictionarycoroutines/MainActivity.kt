package uz.olimjon_rustamov.dictionarycoroutines

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.menu.MenuBuilder
import androidx.navigation.findNavController
import uz.olimjon_rustamov.dictionarycoroutines.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMain)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setUI()
    }

    @SuppressLint("RestrictedApi")
    private fun setUI() {
        val navController = findNavController(R.id.nav_host_fragment)
        val menu = MenuBuilder(this)
        menuInflater.inflate(R.menu.btm_nav_view, menu)
        binding.btmNavView.setupWithNavController(menu,navController)
        binding.toolbarMain.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

    fun shareProgram(view: View) {
//        binding.drawerLayout.closeDrawers()
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "https://github.com/OlimjonRustamov/Dictionary-Coroutines"
        )
        startActivity(Intent.createChooser(shareIntent, "Share to"))
    }

    fun aboutProgram(view: View) {
//        binding.drawerLayout.closeDrawers()
        Toast.makeText(this@MainActivity, "Info!", Toast.LENGTH_SHORT).show()
    }

    fun setVisiblities(bool: Boolean) {
        val appBarLayout = binding.appbarLayout
        val btmNavView = binding.btmNavView
        val navView = binding.navView
        if (bool) {
            appBarLayout.visibility = View.VISIBLE
            btmNavView.visibility = View.VISIBLE
            navView.visibility = View.VISIBLE
        } else {
            appBarLayout.visibility = View.GONE
            btmNavView.visibility = View.GONE
            navView.visibility = View.GONE
        }
    }
}
