package com.tbazd.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * {@link SimpleBeanConverter} test.
 * Created on 3/19/16.
 *
 * @author vknysh
 */
public class SimpleBeanConverterTest {

    @Test
    public void convertTest() throws Throwable {
        Foo source = new Foo();
        source.setStringProperty("testValue");
        source.setIntProperty(7);

        Bar result = SimpleBeanConverter.convert(source, Foo.class, Bar.class);

        assertNotNull(result);
        assertEquals("testValue", result.getStringProperty());
        assertEquals(7, result.getIntProperty());
    }

    public static class Foo {

        private String stringProperty;
        private int intProperty;

        public String getStringProperty() {
            return stringProperty;
        }

        public void setStringProperty(String stringProperty) {
            this.stringProperty = stringProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }
    }

    public static class Bar {

        private String stringProperty;
        private int intProperty;

        public String getStringProperty() {
            return stringProperty;
        }

        public void setStringProperty(String stringProperty) {
            this.stringProperty = stringProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }
    }
}
