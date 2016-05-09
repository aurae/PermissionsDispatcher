package permissions.dispatcher.processor.aa

import org.androidannotations.AndroidAnnotationsEnvironment
import org.androidannotations.ElementValidation
import org.androidannotations.handler.BaseAnnotationHandler
import org.androidannotations.holder.EComponentHolder
import permissions.dispatcher.RuntimePermissions
import javax.lang.model.element.Element

class PDHandler(environment: AndroidAnnotationsEnvironment?) : BaseAnnotationHandler<EComponentHolder>(RuntimePermissions::class.java, environment) {
    override fun validate(element: Element?, validation: ElementValidation?) {
        validatorHelper.elementHasAnnotation(RuntimePermissions::class.java, element)
    }

    override fun process(element: Element?, holder: EComponentHolder?) {
        holder?.generatedClass?.annotate(RuntimePermissions::class.java)
    }
}
