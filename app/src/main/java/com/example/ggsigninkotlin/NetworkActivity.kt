package com.example.ggsigninkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ggsigninkotlin.check_connect.NetworkConnection
import com.example.ggsigninkotlin.databinding.ActivityNetworkBinding
import com.example.ggsigninkotlin.databinding.NetworkErrorLayoutBinding

class NetworkActivity : AppCompatActivity() {
    private  lateinit var  binding: ActivityNetworkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this) {
            if (it) {
                Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
                showNetworkDisconnectedPopup()
            }
        }
    }

    private fun showNetworkDisconnectedPopup() {
        val dialogBinding = NetworkErrorLayoutBinding.inflate(LayoutInflater.from(this))

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(dialogBinding.root)
        dialogBuilder.setCancelable(false)
        val dialog = dialogBuilder.create()

        dialogBinding.btnOk.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}