package demos.expmind.andromeda

import android.app.Application
import demos.expmind.network_client.ServiceGenerator

/**
 * Created by RAJ1GA on 02/10/2018.
 */
class AndromedaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceGenerator.setupBaseUrl(Configurations.MUSIXMATCH_URL)
    }
}