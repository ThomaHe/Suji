package fr.henry.tapptic.ui.details

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import android.view.MenuItem
import android.widget.Toast
import fr.henry.tapptic.R
import fr.henry.tapptic.ui.main.MainActivity

class DetailsActivity : AppCompatActivity() {

    private var numberName =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            numberName = intent.getStringExtra(DetailsFragment.NUMBER_NAME)!!
            val fragment = DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        DetailsFragment.NUMBER_NAME,
                            intent.getStringExtra(DetailsFragment.NUMBER_NAME))
                }
            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    NavUtils.navigateUpTo(this, Intent(this, MainActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE && newConfig.screenWidthDp>=900) {

            val intent = Intent(this, MainActivity::class.java).apply {
              putExtra(DetailsFragment.NUMBER_NAME, numberName)
            }
            startActivity(intent)
        }
    }
}