package org.grails.plugins.dynamicConfig

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.codehaus.groovy.runtime.DefaultGroovyMethods

class ConfigProperty {

    String name
    String value
    byte[] checksum

    ConfigProperty() {}

    ConfigProperty(String name, String value) {
        this.name = name
        this.value = value
    }

    static constraints = {
        name(unique: true, blank: false, validator: {String val, obj ->
            if (!(val ==~ /([a-zA-Z_]([a-zA-Z0-9_])*)(\.([a-zA-Z_]([a-zA-Z0-9_])*))*/)) {
                return 'configProperty.not.matches.message'
            }
        })
        value(nullable: true)
    }

    String toString() {
        return name
    }

    def beforeDelete = {
        CH.config.remove(name)
    }

    def beforeInsert = {
        updateConfigMap()
    }

    def beforeUpdate = {
        updateConfigMap()
    }

    void updateConfigMap() {
        Boolean useQuotes = !(((value ==~ /\[.*]/)) || DefaultGroovyMethods.isNumber(value) || DefaultGroovyMethods.isFloat(value) || value in ['true', 'false'])
        String objectString = useQuotes ? "${name}='''${value}'''" : "${name}=${value}"
        ConfigObject configObject = new ConfigSlurper().parse(objectString)
        CH.config.merge(configObject)
    }

}
