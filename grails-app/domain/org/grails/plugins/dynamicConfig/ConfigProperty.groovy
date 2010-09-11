package org.grails.plugins.dynamicConfig

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class ConfigProperty {


    String name
    String value
    DataType type = DataType.STRING

    ConfigProperty(){}

    ConfigProperty(String name, String value, DataType type){
        this.name = name
        this.value = value
        this.type = type
    }

    static constraints = {
        name(unique: true, blank: false)
        value(blank: false, validator: {String val, obj ->
            switch (obj.type) {
                case DataType.INTEGER: return (val.isInteger() ? true : 'typeMismatch.java.lang.Integer'); break;
                case DataType.LONG: return (val.isLong() ? true : 'typeMismatch.java.lang.Long'); break;
                case DataType.FLOAT: return (val.isFloat() ? true : 'typeMismatch.java.lang.Float'); break;
                case DataType.DOUBLE: return (val.isDouble() ? true : 'typeMismatch.java.lang.Double'); break;
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
