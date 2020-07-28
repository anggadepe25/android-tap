package com.angga.telkomselapprenticeprogam.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.activities.MainActivity
import com.angga.telkomselapprenticeprogam.extensions.disabled
import com.angga.telkomselapprenticeprogam.extensions.enabled
import com.angga.telkomselapprenticeprogam.utilities.Constants
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel : LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel.listenToState().observer(this, Observer { handleUI(it) })
        login()
    }

    private fun handleUI(it : LoginState){
        when(it){
            is LoginState.IsLoading -> {
                if (it.state){
                    btn_login.disabled()
                }else{
                    btn_login.enabled()
                }
            }
            is LoginState.ShowToast -> toast(it.message)
            is LoginState.Reset -> {
                setEmailError(null)
                setPasswordError(null)
            }
            is LoginState.Validate -> {
                it.email?.let { setEmailError(it) }
                it.password?.let { setPasswordError(it) }
            }
            is LoginState.Success -> {
                Constants.setToken(this@LoginActivity, "Bearer ${it.token}")
                startActivity(Intent(this@LoginActivity, MainActivity::class.java)).also { finish() }
            }
        }
    }

    private fun login(){
        btn_login.setOnClickListener {
            val email = et_email.text.toString().trim()
            val pasword = et_password.text.toString().trim()
            if (loginViewModel.validate(email, pasword)){
                loginViewModel.login(email,pasword)
            }
        }
    }

    private fun setEmailError(err : String?){ til_email.error = err }
    private fun setPasswordError(err : String?){ til_password.error = err }
    private fun toast(message : String) = Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()

    override fun onResume() {
        super.onResume()
        if (!Constants.getToken(this@LoginActivity).equals("UNDEFINED")){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }
}
