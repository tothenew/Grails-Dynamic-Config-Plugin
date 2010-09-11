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

    void testValid_STRING() {
        new ConfigProperty('x', 'y', DataType.STRING).save(failOnError:true);
        assertEquals (1, ConfigProperty.count())
        assertEquals ('y', CH.config.x)
    }

    void testValid_INTEGER() {
        new ConfigProperty('x', '100', DataType.INTEGER).save(failOnError:true);
        assertEquals (1, ConfigProperty.count())
        assertEquals (100, CH.config.x)
    }
}
