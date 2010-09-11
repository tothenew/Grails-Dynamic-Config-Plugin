package org.grails.plugins.dynamicConfig

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ConfigProperty {

    static config = ConfigurationHolder.config

    String name
    String value

    static constraints = {
        name(unique: true, blank: false)
        value(blank: false)
    }

}
