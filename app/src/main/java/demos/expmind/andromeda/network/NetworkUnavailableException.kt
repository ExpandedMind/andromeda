package demos.expmind.andromeda.network

import java.io.IOException

class NetworkUnavailableException : IOException() {

    override val message: String
        get() = "There is not internet connection.\n Please try later"
}