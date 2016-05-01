package com.simplebeanconverter;

import org.bson.types.ObjectId;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
        source.setBigDecimalProperty(new BigDecimal("122"));
        source.setId(new ObjectId("56dd410ed4c6407deacd7e78"));

        Map<String, String> properties = new HashMap<>();
        properties.put("id", "MONGODB");

        Bar result = SimpleBeanConverter.convert(source, Foo.class, Bar.class, properties);

        assertNotNull(result);
        assertEquals("testValue", result.getStringProperty());
        assertEquals(7, result.getIntProperty());
        assertEquals("56dd410ed4c6407deacd7e78", result.getId());
    }

    public static class Foo {

        private String stringProperty;
        private int intProperty;
        private BigDecimal bigDecimalProperty;
        private ObjectId id;

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

        public BigDecimal getBigDecimalProperty() {
            return bigDecimalProperty;
        }

        public void setBigDecimalProperty(BigDecimal bigDecimalProperty) {
            this.bigDecimalProperty = bigDecimalProperty;
        }

        public ObjectId getId() {
            return id;
        }

        public void setId(ObjectId id) {
            this.id = id;
        }
    }

    public static class Bar {

        private String stringProperty;
        private int intProperty;
        private String id;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
