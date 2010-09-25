import org.grails.plugins.dynamicConfig.ConfigProperty
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class DynamicConfigBootStrap {

    def init = {servletContext ->
        if (!ConfigProperty.count()) {
            ConfigurationHolder.config.each {key, value ->
                loadValues("", key, value)
            }
        }
    }

    void loadValues(String parentKey, String key, def value) {
        String newParentKey = "${parentKey.size() > 0 ? parentKey + '.' : ''}${key}"
        try {
            if (value && !(value instanceof Closure)) {
                if (value instanceof Map) {
                    value.each {k, v ->
                        loadValues newParentKey, k, v
                    }
                } else {
                    ConfigProperty configProperty = new ConfigProperty(name: newParentKey, value: value.toString()).save()
                }
            }
        } catch (Exception e) {
            println "Exception ${e.message} for " + value.getClass() + " key : ${newParentKey}"
        }

    }

    def destroy = {
    }
}
