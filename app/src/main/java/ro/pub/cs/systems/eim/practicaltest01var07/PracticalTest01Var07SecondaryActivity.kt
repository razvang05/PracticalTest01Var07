package ro.pub.cs.systems.eim.practicaltest01var07

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat.enableEdgeToEdge

class PracticalTest01Var07SecondaryActivity : AppCompatActivity(){

    private lateinit var btn_suma : Button
    private lateinit var btn_produs : Button
    private lateinit var edit_text_1 : EditText
    private lateinit var edit_text_2 : EditText
    private lateinit var edit_text_3 : EditText
    private lateinit var edit_text_4 : EditText

    private lateinit var numere: List<Int>

    private var listener_button = View.OnClickListener { v->
        when(v.id) {
            R.id.btn_sum -> suma_edit()
            R.id.btn_prod -> prod_edit()
        }
    }

    private fun suma_edit () {
        val suma = numere.sum()
        val intent_res = Intent().apply {
            putExtra(Constants.EXTRA_RESULT_TYPE,"sum")
            putExtra(Constants.EXTRA_RESULT_VALUE,suma)
        }

        setResult(RESULT_OK,intent_res)
        finish()

    }

    private fun prod_edit() {
        var produs = 1
        for(nums in numere) {
            produs *= nums
        }

        val intent_Result= Intent().apply {
            putExtra(Constants.EXTRA_RESULT_TYPE,"prod")
            putExtra(Constants.EXTRA_RESULT_VALUE,produs)

        }
        setResult(RESULT_OK,intent_Result)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var07_secondary)

        btn_suma = findViewById(R.id.btn_sum)
        btn_produs = findViewById(R.id.btn_prod)
        edit_text_1 = findViewById(R.id.edit_text1)
        edit_text_2 = findViewById(R.id.edit_text2)
        edit_text_3 = findViewById(R.id.edit_text3)
        edit_text_4 = findViewById(R.id.edit_text4)

        btn_produs.setOnClickListener(listener_button)
        btn_suma.setOnClickListener(listener_button)

        val int = intent
        val v1 = intent.getStringExtra(Constants.VAL1)
        val v2 = intent.getStringExtra(Constants.VAL2)
        val v3 = intent.getStringExtra(Constants.VAL3)
        val v4 = intent.getStringExtra(Constants.VAL4)

        edit_text_1.setText(v1)
        edit_text_1.isEnabled= false
        edit_text_2.setText(v2)
        edit_text_2.isEnabled=false
        edit_text_3.setText(v3)
        edit_text_3.isEnabled=false
        edit_text_4.setText(v4)
        edit_text_4.isEnabled=false


        numere = listOf(v1?.toInt() ?: 0, v2?.toInt()?:0,v3?.toInt()?:0,v4?.toInt()?:0)

    }
}