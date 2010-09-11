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
        new ConfigProperty('x', 'y', DataType.STRING).save(failOnError:true);
        assertEquals (1, ConfigProperty.count())
        assertEquals ('y', CH.config.x)
    }

    void testCreate_Valid_INTEGER() {
        new ConfigProperty('x', '100', DataType.INTEGER).save(failOnError:true);
        assertEquals (1, ConfigProperty.count())
        assertEquals (100, CH.config.x)
    }

    void testCreate_Valid_LONG() {
        new ConfigProperty('x', '100', DataType.LONG).save(failOnError:true);
        assertEquals (1, ConfigProperty.count())
        assertEquals (100, CH.config.x)
    }

    void testCreate_Valid_BOOLEAN() {
        new ConfigProperty('x', 'false', DataType.BOOLEAN).save(failOnError:true);
        assertEquals (1, ConfigProperty.count())
        assertEquals (false, CH.config.x)
    }

    void testCreate_Valid_FLOAT() {
        new ConfigProperty('x', '100.01', DataType.FLOAT).save(failOnError:true);
        assertEquals (1, ConfigProperty.count())
        assertEquals (100.01f, CH.config.x)
    }

    void testCreate_Valid_DOUBLE() {
        new ConfigProperty('x', '100.01', DataType.DOUBLE).save(failOnError:true);
        assertEquals (1, ConfigProperty.count())
        assertEquals (100.01, CH.config.x)
    }
}
