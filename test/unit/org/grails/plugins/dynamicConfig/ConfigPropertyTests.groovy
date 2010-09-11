package org.grails.plugins.dynamicConfig

import grails.test.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class ConfigPropertyTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        mockDomain(ConfigProperty)
        mockConfig('')

    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCreate_Valid_STRING() {
        new ConfigProperty('x', 'y', DataType.STRING).save();
        assertEquals (1, ConfigProperty.count())
        assertEquals ('y', CH.config.x)
    }

    void testCreate_Valid_INTEGER() {
        new ConfigProperty('x', '100', DataType.INTEGER).save();
        assertEquals (1, ConfigProperty.count())
        assertEquals (100, CH.config.x)
    }

    void testCreate_Valid_LONG() {
        new ConfigProperty('x', '100', DataType.LONG).save();
        assertEquals (1, ConfigProperty.count())
        assertEquals (100l, CH.config.x)
    }

    void testCreate_Valid_BOOLEAN() {
        new ConfigProperty('x', 'false', DataType.BOOLEAN).save();
        assertEquals (1, ConfigProperty.count())
        assertEquals (false, CH.config.x)
    }

    void testCreate_Valid_FLOAT() {
        new ConfigProperty('x', '100.01', DataType.FLOAT).save();
        assertEquals (1, ConfigProperty.count())
        assertEquals (100.01f, CH.config.x)
    }

    void testCreate_Valid_DOUBLE() {
        new ConfigProperty('x', '100.01', DataType.DOUBLE).save();
        assertEquals (1, ConfigProperty.count())
        assertEquals (100.01, CH.config.x)
    }

    void testUpdate_Valid_STRING() {
        ConfigProperty property = new ConfigProperty('x', 'y', DataType.STRING).save()
        property.value = 'z'
        property.save()
        assertEquals ('z', CH.config.x)
    }

    void testUpdate_Valid_INTEGER() {
        ConfigProperty property = new ConfigProperty('x', '50', DataType.INTEGER).save();
        property.value = '100'
        property.save()
        assertEquals (100, CH.config.x)
    }

    void testUpdate_Valid_LONG() {
        ConfigProperty property = new ConfigProperty('x', '50', DataType.LONG).save();
        property.value = '100'
        property.save()
        assertEquals (100l, CH.config.x)
    }

    void testUpdate_Valid_BOOLEAN() {
        ConfigProperty property = new ConfigProperty('x', 'false', DataType.BOOLEAN).save();
        property.value = 'true'
        property.save()
        assertEquals (true, CH.config.x)
    }

    void testUpdate_Valid_FLOAT() {
        ConfigProperty property = new ConfigProperty('x', '50.01', DataType.FLOAT).save();
        property.value = '100.01'
        property.save()
        assertEquals (100.01f, CH.config.x)
    }

    void testUpdate_Valid_DOUBLE() {
        ConfigProperty property = new ConfigProperty('x', '50.01', DataType.DOUBLE).save();
        property.value = '100.01'
        property.save()
        assertEquals (100.01, CH.config.x)
    }
}
