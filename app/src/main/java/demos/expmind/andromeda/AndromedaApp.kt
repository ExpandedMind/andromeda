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

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        //Init Image loader
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this))
    }

    fun getApplicationComponent(): AppComponent = appComponent

}