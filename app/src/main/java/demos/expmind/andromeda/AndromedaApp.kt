package demos.expmind.andromeda

import android.app.Application
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import demos.expmind.andromeda.di.AppComponent
import demos.expmind.andromeda.di.DaggerAppComponent

/**
 * Created by RAJ1GA on 02/10/2018.
 */
open class AndromedaApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appComponent = DaggerAppComponent.builder().bindsApplication(this).build()
        //Init Image loader
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this))
    }


    companion object {
        private var INSTANCE: AndromedaApp? = null

        @JvmStatic
        fun getAppGraph(): AppComponent = INSTANCE!!.appComponent
    }

}