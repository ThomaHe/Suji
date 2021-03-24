package fr.henry.tapptic.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.henry.tapptic.R
import fr.henry.tapptic.data.Numbers
import fr.henry.tapptic.ui.SharedViewModel
import fr.henry.tapptic.ui.details.DetailsActivity
import fr.henry.tapptic.ui.details.DetailsFragment


class MainActivity : AppCompatActivity(), MainAdapter.OnItemClickListener {

    private var twoPane: Boolean = false

    private var mNumbersList: MutableList<Numbers> = mutableListOf()
    private var lastNumberClicked : Numbers = Numbers("-1","","")
    private lateinit var sharedViewModel:SharedViewModel
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            twoPane = true
        }
        setupRecyclerView(findViewById(R.id.item_list))

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        sharedViewModel.numberListError.observe(this, numberListErrorObserver)
        sharedViewModel.getNumbersList()?.observe(this, numberListUpdateObserver)

        //permet de réafficher la valeur qui avait été choisie en mode portrait quand on change d'orientation depuis les détails
        if(intent.getStringExtra(DetailsFragment.NUMBER_NAME)!=null){
            if (twoPane) {
                mainAdapter.selectedItem=intent.getStringExtra(DetailsFragment.NUMBER_NAME)!!
                mainAdapter.notifyDataSetChanged()

                val fragment = DetailsFragment().apply {
                    arguments = Bundle().apply {
                        putString(DetailsFragment.NUMBER_NAME, intent.getStringExtra(DetailsFragment.NUMBER_NAME))
                    }
                }
                this.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            }
        }
    }

    override fun onItemClicked(number: Numbers){

        mainAdapter.selectedItem=number.name
        lastNumberClicked= number

        if (twoPane) {
            mainAdapter.notifyDataSetChanged()

            val fragment = DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(DetailsFragment.NUMBER_NAME, number.name)
                }
            }
            this.supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, fragment)
                .commit()
        } else {
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(DetailsFragment.NUMBER_NAME, number.name)
            }
            startActivity(intent)
        }
    }

    private var numberListUpdateObserver: Observer<MutableList<Numbers>?> =
        Observer { numbers ->
            mNumbersList.clear()
            mNumbersList.addAll(numbers!!)
            mainAdapter.notifyDataSetChanged()
        }

    private var numberListErrorObserver: Observer<Boolean> =
        Observer {
           if(it) {
               val builder = AlertDialog.Builder(this)
               builder.setTitle(getString(R.string.error_title))
               builder.setMessage(getString(R.string.error_retry_message))

               builder.setPositiveButton(getString(R.string.retry)) { dialog, which ->
                   sharedViewModel.getNumbersList()?.observe(this, numberListUpdateObserver)
               }

               builder.setNegativeButton(getString(R.string.abort)) { dialog, which ->
                   Toast.makeText(
                       applicationContext,
                       getString(R.string.retry_with_connexion), Toast.LENGTH_SHORT
                  ).show()
               }

               builder.show()
           }
        }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        mainAdapter=MainAdapter(
            this,
            mNumbersList,
            twoPane,this
        )
        recyclerView.adapter = mainAdapter
        recyclerView.apply {
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

}