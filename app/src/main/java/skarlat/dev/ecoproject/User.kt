package skarlat.dev.ecoproject

import android.graphics.drawable.BitmapDrawable

class User {
    @JvmField
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var progress = 0
    var avatar: BitmapDrawable? = null

    constructor() {}
    constructor(name: String?) {
        this.name = name
        currentUser = this
    }

    companion object {
        @JvmField
        var currentUser: User? = null
    }
}