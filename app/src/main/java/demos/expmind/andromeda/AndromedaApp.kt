package demos.expmind.andromeda

import android.app.Application
import com.facebook.stetho.Stetho
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

/**
 * Created by RAJ1GA on 02/10/2018.
 */
class AndromedaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //Init Image loader
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this))
        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build())
        }
    }
}