package org.grails.plugins.dynamicConfig

enum DataType {
    STRING('String'),
    BOOLEAN('Boolean'),
    INTEGER('Integer'),
    LONG('Long'),
    FLOAT('Float'),    
    DOUBLE('Double')

    private final String name

    DataType(String name){
        this.name = name
    }

    public String toString() {
        return name
    }

}
