package permissions.dispatcher.processor.aa

import org.androidannotations.AndroidAnnotationsEnvironment
import org.androidannotations.handler.AnnotationHandler
import org.androidannotations.plugin.AndroidAnnotationsPlugin

class AAPlugin : AndroidAnnotationsPlugin() {
    override fun getHandlers(androidAnnotationEnv: AndroidAnnotationsEnvironment?): MutableList<AnnotationHandler<*>>? {
        return arrayListOf(PDHandler(androidAnnotationEnv))
    }

    override fun getName(): String? {
        return "permissionsdispatcher"
    }
}
