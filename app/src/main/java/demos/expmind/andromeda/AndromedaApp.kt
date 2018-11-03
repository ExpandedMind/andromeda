package demos.expmind.andromeda

import android.app.Application
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import demos.expmind.network_client.ServiceGenerator

/**
 * Created by RAJ1GA on 02/10/2018.
 */
class AndromedaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //Init Image loader
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this))
    }
}