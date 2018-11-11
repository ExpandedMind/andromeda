package demos.expmind.andromeda

import com.facebook.stetho.Stetho

/**
 * Specific version of Application component for debug build variant
 */
class DebugAndromedaApp : AndromedaApp() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build())
    }
}