import org.grails.plugins.dynamicConfig.ConfigProperty
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class DynamicConfigBootStrap {

    def init = {servletContext ->
        ConfigurationHolder.flatConfig.each {key, value ->
            loadValues(key, value)
        }
        ConfigProperty.list()*.updateConfigMap()
    }

    void loadValues(String key, def value) {
        try {
            if (value?.toString() && !((value instanceof List) || (value instanceof Closure))) {
                byte[] checksum = value.toString().encodeAsMD5Bytes()
                ConfigProperty configProperty = ConfigProperty.findByName(key)
                if (configProperty && (configProperty.checksum != checksum)) {
                    configProperty.value = value
                    configProperty.checksum = checksum
                    configProperty.save()
                } else {
                    new ConfigProperty(name: key, value: value.toString(), checksum: checksum).save()
                }
            }
        } catch (Exception e) {
            println "Exception ${e.message} for " + value + " key : ${key}"
        }
    }

    def destroy = {
    }
}
