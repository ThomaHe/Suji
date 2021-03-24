package fr.henry.tapptic.ui.details

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import fr.henry.tapptic.R
import fr.henry.tapptic.data.Numbers
import fr.henry.tapptic.network.ApiCalls
import fr.henry.tapptic.ui.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class DetailsFragment : Fragment() {

    private lateinit var itemName: String
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        arguments?.let {
            if (it.containsKey(NUMBER_NAME)) {
                itemName =it.getString(NUMBER_NAME)!!
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        sharedViewModel.numberDetailsError.observe(this, numberDetailsErrorObserver)
        if(itemName.isNotEmpty())
            sharedViewModel.getNumberDetails(itemName)?.observe(this, numberDetailsUpdateObserver)

        return rootView
    }

    private var numberDetailsUpdateObserver: Observer<Numbers?> =
        Observer {
            updateView(it!!)
        }

    private var numberDetailsErrorObserver: Observer<Boolean> =
        Observer {
            if(it) {
                val builder = AlertDialog.Builder(view!!.context)
                builder.setTitle(getString(R.string.error_title))
                builder.setMessage(getString(R.string.error_retry_message))

                builder.setPositiveButton(getString(R.string.retry)) { dialog, which ->
                    sharedViewModel.getNumberDetails(itemName)?.observe(this, numberDetailsUpdateObserver)
                }

                builder.setNegativeButton(getString(R.string.abort)) { dialog, which ->
                    Toast.makeText(
                        view!!.context,
                        getString(R.string.retry_with_connexion), Toast.LENGTH_SHORT
                    ).show()
                }

                builder.show()
            }
        }

    private fun updateView(item :Numbers){
        Glide.with(view!!).load(item.image).into(view!!.findViewById(R.id.item_detail_image))
        view!!.findViewById<TextView>(R.id.item_detail_text).text = item.text
    }

    companion object {
        const val NUMBER_NAME = "number_name"
    }
}