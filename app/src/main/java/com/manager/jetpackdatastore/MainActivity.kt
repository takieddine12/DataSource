package com.manager.jetpackdatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    var VALUE1_PREFERNECE = preferencesKey<String>(name = "value1")
    var VALUE2_PREFERNECE = preferencesKey<String>(name = "value2")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        save.setOnClickListener {
            var dataStorePreference = this.createDataStore(name = "prefs")
            var value1 = text1?.text.toString()
            var value2 = text2?.text.toString()

            //Storing Data
            lifecycleScope.launch {
                dataStorePreference.edit { preferences ->
                    preferences[VALUE1_PREFERNECE] = value1
                    preferences[VALUE2_PREFERNECE] = value2
                    launch(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity,"values have been saved..",Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
        load.setOnClickListener {

            var dataStorePreference = this.createDataStore(name = "prefs")
            CoroutineScope(Dispatchers.IO).launch {
                dataStorePreference.data.collect {
                    var value11 = it[VALUE1_PREFERNECE]
                    var value22 = it[VALUE2_PREFERNECE]
                    launch (Dispatchers.Main){
                        Toast.makeText(this@MainActivity,"Value 1" + value11,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        }

}