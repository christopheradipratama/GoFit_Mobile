package com.example.gofit_mobile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gofit_mobile.JadwalHarian.JadwalHarianActivity
import com.example.gofit_mobile.api.UserApi
import com.example.gofit_mobile.databinding.ActivityMainBinding
import com.example.gofit_mobile.model.UserModel
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.nio.charset.StandardCharsets

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout

    var etEmail: String = ""
    var etPassword: String = ""

    lateinit var mBundle: Bundle
    var tempEmail: String = "admin"
    var tempPass: String = "admin"
    private var queue: RequestQueue? = null

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        queue = Volley.newRequestQueue(this)

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        supportActionBar?.hide()

        inputEmail = findViewById(R.id.inputLayoutEmail)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        mainLayout = findViewById(R.id.mainLayout)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnForgetpassword: Button = findViewById(R.id.btnForgetPassword)

        binding.btnLogin.setOnClickListener(View.OnClickListener {
            etEmail = binding.inputLayoutEmail.editText?.text.toString()
            etPassword = binding.inputLayoutPassword.editText?.text.toString()

            binding.inputLayoutEmail.setError(null)
            binding.inputLayoutPassword.setError(null)

            val auth = UserModel(etEmail, etPassword)

            val stringRequest: StringRequest =
                object : StringRequest(
                    Request.Method.POST,
                    UserApi.LOGIN_URL,
                    Response.Listener { response ->
                        val gson = Gson()
                        var jsonObj = JSONObject(response.toString())
                        var userObj = jsonObj.getJSONObject("user")

                        if (userObj.has("ID_MEMBER")) {
                            val token = jsonObj.getString("access_token")
                            val move = Intent(this@LoginActivity, HomeActivity::class.java)
                            sharedPreferences.edit()
                                .putString("id", userObj.getString("ID_MEMBER"))
                                .putString("role", "Member")
                                .putString("token", token)
                                .apply()
                            startActivity(move)
                        } else if (userObj.has("ID_PEGAWAI")) {
                            val token = jsonObj.getString("access_token")
                            FancyToast.makeText(this,sharedPreferences.getString("role",null),
                                FancyToast.LENGTH_LONG,
                                FancyToast.SUCCESS,false).show()
                            val move = Intent(this@LoginActivity, HomeActivity::class.java)
                            sharedPreferences.edit()
                                .putString("id", userObj.getString("ID_PEGAWAI"))
                                .putString("role", "Manajer Operasional")
                                .putString("token", token)
                                .apply()
                            startActivity(move)
                        } else if (userObj.has("ID_INSTRUKTUR")) {
                            val token = jsonObj.getString("access_token")
                            val move = Intent(this@LoginActivity, HomeActivity::class.java)
                            sharedPreferences.edit()
                                .putString("id", userObj.getString("ID_INSTRUKTUR"))
                                .putString("role", "Instruktur")
                                .putString("token", token)
                                .apply()
                            startActivity(move)
                        }
                    }, Response.ErrorListener { error ->
                        try {
                            val responseBody =
                                String(error.networkResponse.data, StandardCharsets.UTF_8)

                            if (error.networkResponse.statusCode == 400) {
                                binding.inputLayoutEmail.setError("Email salah lur!")
                                binding.inputLayoutPassword.setError("Password salah lur!")
                            } else if (error.networkResponse.statusCode == 400) {
                                val jsonObject = JSONObject(responseBody)
                                val jsonObject1 = jsonObject.getJSONObject("message")
                                for (i in jsonObject1.keys()) {
                                    if (i == "Email") {
                                        binding.inputLayoutEmail.error =
                                            jsonObject1.getJSONArray(i).getString(0)
                                    }
                                    if (i == "password") {
                                        binding.inputLayoutPassword.error =
                                            jsonObject1.getJSONArray(i).getString(0)
                                    }
                                }
                            } else {
                                val errors = JSONObject(responseBody)
                                MotionToast.darkColorToast(this,
                                    "Login Gagal!",
                                    "Cek",
                                    MotionToastStyle.ERROR,
                                    MotionToast.GRAVITY_BOTTOM,
                                    MotionToast.LONG_DURATION,
                                    ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
                            }
                        } catch (e: Exception){
                            MotionToast.darkColorToast(this,
                                "Login Gagal!",
                                "Cek",
                                MotionToastStyle.ERROR,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
                        }
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Accept"] = "application/json"
                        return headers
                    }

                    @Throws(AuthFailureError::class)
                    override fun getBody(): ByteArray {
                        val gson = Gson()
                        val requestBody = gson.toJson(auth)
                        return requestBody.toByteArray(StandardCharsets.UTF_8)
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }

                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["Email"] = binding.inputLayoutEmail.editText?.text.toString()
                        params["Password"] = binding.inputLayoutPassword.editText?.text.toString()
                        return params
                    }
                }
            queue!!.add(stringRequest)
        })

        binding.btnForgetPassword.setOnClickListener(View.OnClickListener{
            val move = Intent(this@LoginActivity, ForgetPasswordActivity::class.java)
            startActivity((move))
        })

        binding.btnJadwalHarian.setOnClickListener(View.OnClickListener{
            val move = Intent(this@LoginActivity, JadwalHarianActivity::class.java)
            startActivity((move))
        })
    }
}