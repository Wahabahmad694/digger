package com.example.digger.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginSignUpBody() : Serializable {

    @SerializedName("user")
    var user = User()

    class User {

        var email: String = ""
        var password: String = ""
        var first_name: String = ""
        var last_name: String = ""
        var code: String = ""
    }
}




