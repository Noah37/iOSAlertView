package com.noah37.iosalertview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.noah37.alertview.*
import com.noah37.iosalertview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var alertBtb:Button
    private lateinit var actionBtb:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alertBtb = binding.alert
        actionBtb = binding.action

        alertBtb.setOnClickListener{
            val alertController = UIAlertController.alerControllerWithTitle("", "" , UIAlertControllerStyle.ACTIONSHEET)

            alertController.addAction(UIAlertAction("重要选项", UIAlertActionStyle.DESTRUCTIVE) { action ->
                Toast.makeText(this, action.title, Toast.LENGTH_SHORT).show()
            })
            alertController.addAction(UIAlertAction("普通选项一", UIAlertActionStyle.DEFAULT) { action ->
                Toast.makeText(this, action.title, Toast.LENGTH_SHORT).show()
            })
            alertController.addAction(UIAlertAction("普通选项二", UIAlertActionStyle.DEFAULT) { action ->
                Toast.makeText(this, action.title, Toast.LENGTH_SHORT).show()
            })
            alertController.addAction(UIAlertAction("取消", UIAlertActionStyle.CANCEL) { action ->
                Toast.makeText(this, action.title, Toast.LENGTH_SHORT).show()
            })
            presentModalViewController(alertController)
        }

        actionBtb.setOnClickListener{
            val alert = UIAlertView("The Post", "What do you want to do with this post?", UIAlertControllerStyle.ACTIONSHEET)

            alert.addAction(UIAlertAction("Bookmark", UIAlertActionStyle.DESTRUCTIVE) { action ->
                Toast.makeText(this, action.title, Toast.LENGTH_SHORT).show()
            })
            alert.addAction(UIAlertAction("Report", UIAlertActionStyle.DEFAULT) { action ->
                Toast.makeText(this, action.title, Toast.LENGTH_SHORT).show()
            })
            alert.addAction(UIAlertAction("Share", UIAlertActionStyle.DEFAULT) { action ->
                Toast.makeText(this, action.title, Toast.LENGTH_SHORT).show()
            })
            alert.addAction(UIAlertAction("Cancel", UIAlertActionStyle.DEFAULT) { action ->
                Toast.makeText(this, action.title, Toast.LENGTH_SHORT).show()
            })

            alert.show(this)
        }
    }
}