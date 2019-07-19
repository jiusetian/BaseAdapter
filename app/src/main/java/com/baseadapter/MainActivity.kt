package com.baseadapter

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_multi).setOnClickListener {
            startActivity(Intent(this, MultiItemRvActivity::class.java))
        }

        findViewById<Button>(R.id.btn_com).setOnClickListener {
            startActivity(Intent(this, CommonActivity::class.java))
        }
    }
}
