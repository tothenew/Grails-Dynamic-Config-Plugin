package org.grails.plugins.dynamicConfig

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class ConfigProperty {


    String name
    String value
    DataType type

    ConfigProperty() {}

    ConfigProperty(String name, String value, DataType type) {
        this.name = name
        this.value = value
        this.type = type
    }

    static constraints = {
        name(unique: true, blank: false, validator: {String val, obj ->
            if (!(val ==~ /([a-zA-Z_]([a-zA-Z0-9_])*)(\.([a-zA-Z_]([a-zA-Z0-9_])*))*/)) {
                return 'configProperty.not.matches.message'
            }
        })
        value(blank: false, validator: {String val, obj ->
            switch (obj.type) {
                case DataType.INTEGER: return (val.isInteger() ? true : 'configProperty.typeMismatch.java.lang.Integer'); break;
                case DataType.LONG: return (val.isLong() ? true : 'configProperty.typeMismatch.java.lang.Long'); break;
                case DataType.FLOAT: return (val.isFloat() ? true : 'configProperty.typeMismatch.java.lang.Float'); break;
                case DataType.DOUBLE: return (val.isDouble() ? true : 'configProperty.typeMismatch.java.lang.Double'); break;
            }
        })
    }

    String toString() {
        return name
    }

    def beforeInsert = {
        updateConfigMap()
    }

    def beforeUpdate = {
        updateConfigMap()
    }

    void updateConfigMap() {
        List propertyList = name.tokenize('.')
        String lastProperty = propertyList.last()
        propertyList.remove(lastProperty)
        def currentMap = CH.config
        propertyList?.each {
            if (!currentMap[it]) { currentMap[it] = [:] }
            currentMap = currentMap[it]
        }
        def configValue
        if (value) {
            switch (type) {
                case DataType.BOOLEAN: configValue = value.toBoolean(); break;
                case DataType.INTEGER: configValue = value.toInteger(); break;
                case DataType.LONG: configValue = value.toLong(); break;
                case DataType.FLOAT: configValue = value.toFloat(); break;
                case DataType.DOUBLE: configValue = value.toDouble(); break;
                default: configValue = value; break;
            }
            currentMap["${lastProperty}"] = configValue
        }
    }

}
