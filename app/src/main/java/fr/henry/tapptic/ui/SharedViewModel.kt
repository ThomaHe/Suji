package fr.henry.tapptic.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.henry.tapptic.data.Numbers
import fr.henry.tapptic.data.NumbersMapper
import fr.henry.tapptic.network.ApiCalls
import fr.henry.tapptic.network.NumbersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SharedViewModel() : ViewModel() {

    private val TAG = "SharedViewModel"
    private val mutableNumbersList: MutableLiveData<MutableList<Numbers>?> = MutableLiveData()
    private var mutableNumber: MutableLiveData<Numbers> = MutableLiveData()
    var numberListError: MutableLiveData<Boolean> = MutableLiveData()
    var numberDetailsError: MutableLiveData<Boolean> = MutableLiveData()

    fun getNumbersList(): LiveData<MutableList<Numbers>?>? {
        if (mutableNumbersList.value == null) {
            loadNumbersList()
        }
        return mutableNumbersList
    }

    fun getNumberDetails(name:String): LiveData<Numbers>? {
        if(mutableNumber.value?.name != name)
            loadNumberDetails(name)

        return mutableNumber
    }

   private fun loadNumbersList(){
       val mCall: Call<List<NumbersResponse>> = ApiCalls.getNumbersList()
       mCall.enqueue(object : Callback<List<NumbersResponse>> {
           override fun onResponse(call: Call<List<NumbersResponse>>, response: Response<List<NumbersResponse>>?) {
               response?.let {
                   if (it.isSuccessful && it.code() == 200&&it.body()!=null) {

                       formatNumbersList(it.body()!!)
                   } else {
                       numberListError.postValue(true)
                       Log.e("API CALL", response.message())
                   }
               }
           }

           override fun onFailure(call: Call<List<NumbersResponse>>, t: Throwable) {
               if (!call.isCanceled) {
                   numberListError.postValue(true)
                   Log.e("API CALL FAILED", t.message!!)
               }
           }
       })
   }

    private fun loadNumberDetails(name:String){
        val mCall: Call<NumbersResponse> = ApiCalls.getNumberByName(name)
        mCall.enqueue(object : Callback<NumbersResponse> {
            override fun onResponse(call: Call<NumbersResponse>, response: Response<NumbersResponse>?) {
                response?.let {
                    if (it.isSuccessful && it.code() == 200&&it.body()!=null) {
                        formatNumberDetails(it.body()!!)
                    } else {
                        numberDetailsError.postValue(true)
                        Log.e("API CALL", response.message())
                    }
                }
            }

            override fun onFailure(call: Call<NumbersResponse>, t: Throwable) {
                if (!call.isCanceled) {
                    numberDetailsError.postValue(true)
                    Log.e("API CALL FAILED", t.message!!)
                }
            }
        })
    }

    private fun formatNumbersList(list:List<NumbersResponse>){
        val numbersList :MutableList<Numbers> = mutableListOf()
        list.forEach(){
            numbersList.add(NumbersMapper.MapResponseToNumbers(it))
        }
        mutableNumbersList.postValue(numbersList)
    }

    private fun formatNumberDetails(nbr:NumbersResponse){
        val number = NumbersMapper.MapResponseToNumbers(nbr)
        mutableNumber.postValue(number)
    }
}
