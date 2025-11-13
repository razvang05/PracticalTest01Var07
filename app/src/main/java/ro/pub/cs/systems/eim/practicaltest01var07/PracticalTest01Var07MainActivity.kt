package ro.pub.cs.systems.eim.practicaltest01var07

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.os.Build

class PracticalTest01Var07MainActivity : AppCompatActivity() {

    private lateinit var button_set : Button
    private lateinit var edit1 : EditText
    private lateinit var edit2 : EditText
    private lateinit var edit3 : EditText
    private lateinit var edit4 : EditText

    private val listener_button = View.OnClickListener { v->
        when(v.id) {
            R.id.btn_set -> touch_set()
        }

    }
    private fun isNumber(text: String) : Boolean {
        return try {
            text.toLong()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
    private fun touch_set () {
        val nr1 = edit1.text.toString()
        val nr2 = edit2.text.toString()
        val nr3 = edit3.text.toString()
        val nr4 = edit4.text.toString()
        if(!(isNumber(nr1)&&isNumber(nr2)&&isNumber(nr3)&&isNumber(nr4))) {
            return
        }

        val intent = Intent("ro.pub.cs.systems.eim.practicaltest01.intent.action.PracticalTest01Var07SecondaryActivity")
        intent.putExtra(Constants.VAL1,nr1)
        intent.putExtra(Constants.VAL2,nr2)
        intent.putExtra(Constants.VAL3,nr3)
        intent.putExtra(Constants.VAL4,nr4)
        startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var07_main)

        button_set= findViewById(R.id.btn_set)
        edit1 = findViewById(R.id.edit_text1)
        edit2 = findViewById(R.id.edit_text2)
        edit3 = findViewById(R.id.edit_text3)
        edit4 = findViewById(R.id.edit_text4)

        button_set.setOnClickListener(listener_button)
        startService(Intent(this, PracticalTest01Var07Service::class.java))

    }

    private val randomReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Constants.ACTION_RANDOM) {
                val v1 = intent.getIntExtra(Constants.V1, 0)
                val v2 = intent.getIntExtra(Constants.V2, 0)
                val v3 = intent.getIntExtra(Constants.V3, 0)
                val v4 = intent.getIntExtra(Constants.V4, 0)

                // Suprascrie câmpurile text cu valorile primite
                edit1.setText(v1.toString())
                edit2.setText(v2.toString())
                edit3.setText(v3.toString())
                edit4.setText(v4.toString())
            }
        }
    }


    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(Constants.ACTION_RANDOM)

        if (Build.VERSION.SDK_INT >= 33) {
            // Android 13+ – trebuie flags
            registerReceiver(randomReceiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            // Android 12 și mai jos – fără flags
            @Suppress("UnspecifiedRegisterReceiverFlag")
            registerReceiver(randomReceiver, filter)
        }
    }
    override fun onPause() {
        // Dezînregistrează ca să eviți scurgeri de context
        unregisterReceiver(randomReceiver)
        super.onPause()
    }

    override fun onDestroy() {
        // Oprește serviciul când activitatea e distrusă (D.1.b)
        stopService(Intent(this, PracticalTest01Var07Service::class.java))
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("value1",edit1.text.toString())
        outState.putString("value2",edit2.text.toString())
        outState.putString("value3",edit3.text.toString())
        outState.putString("value4",edit4.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        edit1.setText(savedInstanceState.getString("value1"))
        edit2.setText(savedInstanceState.getString("value2"))
        edit3.setText(savedInstanceState.getString("value3"))
        edit4.setText(savedInstanceState.getString("value4"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && data!=null) {
            val type = data.getStringExtra(Constants.EXTRA_RESULT_TYPE)
            val value = data.getIntExtra(Constants.EXTRA_RESULT_VALUE,0)
            Toast.makeText(this,"Rezultat $type = $value",Toast.LENGTH_LONG).show()
        }
    }
}