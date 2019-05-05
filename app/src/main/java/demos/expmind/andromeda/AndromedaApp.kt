package demos.expmind.andromeda

import android.app.Activity
import android.app.Application
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import demos.expmind.andromeda.di.AppComponent
import demos.expmind.andromeda.di.DaggerAppComponent
import javax.inject.Inject

/**
 * Created by RAJ1GA on 02/10/2018.
 */
open class AndromedaApp : Application(), HasActivityInjector {

    private lateinit var appComponent: AppComponent

    @Inject
    lateinit var defaultAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().bindsApplication(this).build()
        appComponent.inject(this)
        //Init Image loader
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this))
    }

    fun getApplicationComponent(): AppComponent = appComponent

    override fun activityInjector(): AndroidInjector<Activity> = defaultAndroidInjector

}