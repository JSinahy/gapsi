package com.laraguzman.ecommercegapsi

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.laraguzman.ecommercegapsi.data.OnClickInterface
import com.laraguzman.ecommercegapsi.data.models.HistorySearchesModel
import com.laraguzman.ecommercegapsi.data.persistance.Preferences
import com.laraguzman.ecommercegapsi.databinding.ActivityMainBinding
import com.laraguzman.ecommercegapsi.ui.main.viewmodels.MainActivityViewModel
import com.laraguzman.ecommercegapsi.utils.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    var binding : ActivityMainBinding? = null
    var arraySearch : ArrayList<HistorySearchesModel>? = null

    companion object {
        lateinit var prefs: Preferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        prefs = Preferences(this)

        var viewModel = makeFromAPI()

        binding?.mainViewModel = viewModel

        clickOnSearchButton(viewModel)
        ShowHistory()
    }

    fun clickOnSearchButton(viewModel: MainActivityViewModel){
        binding?.searchButton?.setOnClickListener {
            addSearch(binding?.textSearch?.text.toString())
            binding?.pbgLoading?.visibility = View.VISIBLE
            viewModel.GetListPhotosEcommerce().observe(this, Observer { photos ->
                if(it != null){
                    binding?.pbgLoading?.visibility = View.GONE
                    viewModel.SetAdapter(photos.items)
                }else{
                    binding?.pbgLoading?.visibility = View.GONE
                    Toast.makeText(this, "Error in fetching data", Toast.LENGTH_LONG).show()
                }
            })
            viewModel.GetMyItemsFromAPI(binding?.textSearch?.text.toString())
        }
    }


    fun makeFromAPI() : MainActivityViewModel{
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding?.mainViewModel = viewModel

        // SearchText
        viewModel.searchText.observe( this, Observer {
            Log.wtf("BUSQUEDA", it)
        })

        // Uso el ViewModel ==============================================================
        viewModel.GetListPhotosEcommerce().observe(this, Observer { photos ->
            binding?.pbgLoading?.visibility = View.VISIBLE
            if(photos != null){
                binding?.pbgLoading?.visibility = View.GONE
                viewModel.SetAdapter(photos.items)
            }else{
                binding?.pbgLoading?.visibility = View.GONE
                Toast.makeText(this, "Error al obtener la informacion del Enpoint", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.GetMyItemsFromAPI(binding?.textSearch?.text.toString())

        return viewModel
    }

    fun addSearch(search: String){
        if(prefs.name != "" && prefs.name != null){
            var modelo  = prefs.name
            modelo = modelo + search + ","
            Log.wtf("Almacenado", modelo)
            prefs.name = modelo
        }else{
            var modelo  = prefs.name
            modelo = modelo + search + ","
            Log.wtf("Almacenado", modelo)
            prefs.name = modelo
        }
    }

    fun GetDataHistory() : ArrayList<HistorySearchesModel>?{
        val listType = object : TypeToken<ArrayList<HistorySearchesModel?>?>() {}.type
        val modelo : ArrayList<HistorySearchesModel>? = Gson().fromJson(prefs.name, listType)
        return modelo
    }

    fun ShowHistory(){
        if(prefs.name != ""){
            var builderSingle : AlertDialog.Builder = AlertDialog.Builder(this)
                .setTitle("Historial de Busqueda")


            val arreglo = prefs.name?.split(",")?.toTypedArray()

            builderSingle.setNegativeButton("CANCEL", object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0?.dismiss()
                }
            })
            builderSingle.setItems(arreglo, object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    binding?.textSearch?.setText(arreglo?.get(p1))
                }

            })
            var dialog : AlertDialog = builderSingle.create()
            dialog.show()
        }

    }
}