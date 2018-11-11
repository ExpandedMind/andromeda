package demos.expmind.andromeda

import android.app.Application
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

/**
 * Created by RAJ1GA on 02/10/2018.
 */
open class AndromedaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //Init Image loader
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this))
    }
}