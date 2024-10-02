package br.edu.scl.ifsp.sdm.contactlist.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.scl.ifsp.sdm.contactlist.R
import br.edu.scl.ifsp.sdm.contactlist.databinding.ActivityContactBinding
import br.edu.scl.ifsp.sdm.contactlist.model.Constant
import br.edu.scl.ifsp.sdm.contactlist.model.Contact

class ContactActivity : AppCompatActivity() {

    private val acb: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(acb.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(acb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.contact_details)

        val receiveContract = intent.getParcelableExtra<Contact>(Constant.EXTRA_CONTACT)
        receiveContract?.let { received ->

            val viewContact = intent.getBooleanExtra(Constant.EXTRA_VIEW_CONTENT, false)

            with(acb) {
                if(viewContact) {
                    nameEt.isEnabled = false
                    addressEt.isEnabled = false
                    phoneEt.isEnabled = false
                    emailEt.isEnabled = false
                    saveBt.visibility = View.GONE

                }
                nameEt.setText(received.name)
                addressEt.setText(received.address)
                phoneEt.setText(received.phone)
                emailEt.setText(received.email)
            }
        }

        with(acb) {
            saveBt.setOnClickListener {
                val contact = Contact(
                    id = receiveContract?.id?: hashCode(),
                    name = nameEt.text.toString(),
                    address = addressEt.text.toString(),
                    phone = phoneEt.text.toString(),
                    email = emailEt.text.toString()
                )

                val resultIntent = Intent()
                resultIntent.putExtra(Constant.EXTRA_CONTACT, contact)
                setResult(RESULT_OK, resultIntent)
                finish()


            }
        }
    }
}