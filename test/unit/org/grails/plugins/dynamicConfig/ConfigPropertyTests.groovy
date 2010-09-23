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

    void testCreate_Valid_STRING_1() {
        new ConfigProperty('x', 'y').save();
        assertEquals(1, ConfigProperty.count())
        assertEquals('y', CH.config.x)
    }

    void testCreate_Valid_STRING_2() {
        new ConfigProperty('x', '"y"').save();
        assertEquals(1, ConfigProperty.count())
        assertEquals('"y"', CH.config.x)
    }

    void testCreate_Valid_STRING_3() {
        new ConfigProperty('x', "'y's").save();
        assertEquals(1, ConfigProperty.count())
        assertEquals("'y's", CH.config.x)
    }

    void testCreate_Valid_LIST() {
        new ConfigProperty('x', '[1,2,3]').save();
        assertEquals(1, ConfigProperty.count())
        assertEquals([1,2,3], CH.config.x)
    }

    void testCreate_Valid_MAP() {
        new ConfigProperty('x', '[y : 1, z: "abc"]').save();
        assertEquals(1, ConfigProperty.count())
        assertEquals(1, CH.config.x.y)
        assertEquals('abc', CH.config.x.z)
    }

    void testCreate_Valid_INTEGER() {
        new ConfigProperty('x', '100').save();
        assertEquals(1, ConfigProperty.count())
        assertEquals(100, CH.config.x)
    }

    void testCreate_Valid_BOOLEAN() {
        new ConfigProperty('x', 'false').save();
        assertEquals(1, ConfigProperty.count())
        assertEquals(false, CH.config.x)
    }

    void testCreate_Valid_DOUBLE() {
        new ConfigProperty('x', '100.01').save();
        assertEquals(1, ConfigProperty.count())
        assertEquals(100.01, CH.config.x)
    }

    void testUpdate_Valid_STRING() {
        ConfigProperty property = new ConfigProperty('x', 'y').save()
        property.value = 'z'
        property.save()
        assertEquals('z', CH.config.x)
    }

    void testUpdate_Valid_INTEGER() {
        ConfigProperty property = new ConfigProperty('x', '50').save();
        property.value = '100'
        property.save()
        assertEquals(100, CH.config.x)
    }

    void testUpdate_Valid_LONG() {
        ConfigProperty property = new ConfigProperty('x', '50').save();
        property.value = '100'
        property.save()
        assertEquals(100, CH.config.x)
    }

    void testUpdate_Valid_BOOLEAN() {
        ConfigProperty property = new ConfigProperty('x', 'false').save();
        property.value = 'true'
        property.save()
        assertEquals(true, CH.config.x)
    }

    void testUpdate_Valid_DOUBLE() {
        ConfigProperty property = new ConfigProperty('x', '50.01').save();
        property.value = '100.01f'
        property.save()
        assertEquals(100.01f, CH.config.x)
    }

    void testCreate_Blank_Value() {
        ConfigProperty property = new ConfigProperty('x', '')
        property.save()
        assertEquals(1, ConfigProperty.count())
        assertEquals('', CH.config.x)
    }

    void testCreate_Two_Properties_Same_Name() {
        ConfigProperty property1 = new ConfigProperty('x', 'y')
        property1.save()
        assertEquals(1, ConfigProperty.count())
        ConfigProperty property2 = new ConfigProperty('x', 'z')
        property2.save()
        assertEquals(1, ConfigProperty.count())
        assertEquals("unique", property2.errors["name"])
    }

    void testCreate_Valid_Name2() {
        ConfigProperty property = new ConfigProperty('firstName.lastName', 'y').save();
        assertEquals(1, ConfigProperty.count())
    }

    void testCreate_Valid_Name3() {
        new ConfigProperty('x_y', 'value1').save()
        assertEquals(1, ConfigProperty.count())
        assertEquals('value1', CH.config.x_y)
    }

    void testCreate_Valid_Name4() {
        new ConfigProperty('x.y', 'value1').save()
        assertEquals(1, ConfigProperty.count())
        assertEquals('value1', CH.config.x.y)
    }

    void testCreate_Invalid_Name1() {
        ConfigProperty property = new ConfigProperty('x y', 'value1')
        property.save()
        assertEquals(0, ConfigProperty.count())
        assertEquals("configProperty.not.matches.message", property.errors["name"])
    }

    void testCreate_Invalid_Name2() {
        ConfigProperty property = new ConfigProperty('x/y', 'value1')
        property.save()
        assertEquals(0, ConfigProperty.count())
        assertEquals("configProperty.not.matches.message", property.errors["name"])
    }

    void testCreate_Invalid_Name3() {
        ConfigProperty property = new ConfigProperty('.x.y', 'value1')
        property.save()
        assertEquals(0, ConfigProperty.count())
        assertEquals("configProperty.not.matches.message", property.errors["name"])
    }

    void testCreate_Invalid_Name4() {
        ConfigProperty property = new ConfigProperty('x.y.', 'value1')
        property.save()
        assertEquals(0, ConfigProperty.count())
        assertEquals("configProperty.not.matches.message", property.errors["name"])
    }

    void testCreate_Invalid_Name5() {
        ConfigProperty property = new ConfigProperty('1x', 'value1')
        property.save()
        assertEquals(0, ConfigProperty.count())
        assertEquals("configProperty.not.matches.message", property.errors["name"])
    }

}

