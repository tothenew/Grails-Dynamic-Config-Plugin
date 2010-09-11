package org.grails.plugins.dynamicConfig

enum DataType {
    STRING('STRING'),
    BOOLEAN('BOOLEAN'),
    INTEGER('INTEGER'),
    LONG('LONG'),
    FLOAT('FLOAT'),
    DOUBLE('DOUBLE')

    final String name

    DataType(String name){
        this.name = name
    }

    public String toString() {
        return name
    }

}
