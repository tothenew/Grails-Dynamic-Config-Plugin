import org.grails.plugins.dynamicConfig.ConfigProperty
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class DynamicConfigBootStrap {

    def init = {servletContext ->
        if (!ConfigProperty.count()) {
            ConfigurationHolder.flatConfig.each {key, value ->
                loadValues(key, value)
            }
        }
    }

    void loadValues(String key, def value) {
        try {
            if (value && !((value instanceof List) || (value instanceof Closure))) {
               if (!new ConfigProperty(name: key, value: value.toString()).save()) {
                   ConfigProperty.findByName(key)?.value = value
               }
            }
        } catch (Exception e) {
            println "Exception ${e.message} for " + value + " key : ${key}"
        }
    }

    def destroy = {
    }
}
