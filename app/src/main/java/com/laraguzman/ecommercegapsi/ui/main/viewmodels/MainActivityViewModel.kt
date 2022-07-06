package com.laraguzman.ecommercegapsi.ui.main.viewmodels


import android.app.Application
import android.preference.Preference
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.laraguzman.ecommercegapsi.data.models.HistorySearchesModel
import com.laraguzman.ecommercegapsi.data.persistance.Preferences
import com.laraguzman.ecommercegapsi.ui.main.adapters.PhotosAdapter
import com.laraguzman.gapsiecommerce.data.api.EcommerceApiInstance
import com.laraguzman.gapsiecommerce.data.api.EcommerceServices
import com.laraguzman.gapsiecommerce.data.models.GeneralResponse
import com.laraguzman.gapsiecommerce.data.models.PhotosEcommerceModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// View Model
class MainActivityViewModel : ViewModel() {
    var myitems : MutableLiveData<GeneralResponse<ArrayList<PhotosEcommerceModel>>>
    var myPhotosAdapter : PhotosAdapter
    var searchText = MutableLiveData<String>()

    public fun testFunction(){
        System.out.println("Esto esta perro");
    }

    // Se inicializan os mutable
    init{
        myitems = MutableLiveData()
        myPhotosAdapter = PhotosAdapter()
    }

    fun GetAdapter(): PhotosAdapter{
        return myPhotosAdapter
    }

    // Se setea el daptar aunque debe de ir en la vista.
    fun SetAdapter(data: ArrayList<PhotosEcommerceModel>){
        myPhotosAdapter.setDataList(data)
        myPhotosAdapter.notifyDataSetChanged()
    }

    // Estos son los nuevos cambios
    fun GetListPhotosEcommerce() : MutableLiveData<GeneralResponse<ArrayList<PhotosEcommerceModel>>>{
        return myitems
    }

    // En teoria primro debo de hacer pull
    fun GetMyItemsFromAPI(search: String){

        val retrofitInstance = EcommerceApiInstance().GetInstance().create(EcommerceServices::class.java)
        val call = retrofitInstance.searchCriteria(search, "adb8204d-d574-4394-8c1a-53226a40876e")

        call.enqueue(object: Callback<GeneralResponse<ArrayList<PhotosEcommerceModel>>>{
            override fun onResponse(call: Call<GeneralResponse<ArrayList<PhotosEcommerceModel>>>, response: Response<GeneralResponse<ArrayList<PhotosEcommerceModel>>>) {
                if(response.isSuccessful){
                    myitems.postValue(response.body())
                }else{
                    myitems.postValue(null)
                }

            }

            override fun onFailure(call: Call<GeneralResponse<ArrayList<PhotosEcommerceModel>>>, t: Throwable) {
                myitems.postValue(null)
            }

        })
    }
    /**
     * Aquiza aqui podamos hacer algo mas para saber que hay cambios
     */

    /**
     * Y posterior a ello debo de hacer el rebase para reorganizar mi branch
     */



}