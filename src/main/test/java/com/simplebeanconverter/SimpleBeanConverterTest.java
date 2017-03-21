package com.simplebeanconverter;

import org.bson.types.ObjectId;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * {@link SimpleBeanConverter} test.
 * Created on 3/19/16.
 *
 * @author vknysh
 */
// TODO: Create more readable tests
// TODO: Think on vise versa ObjectId convertation (optional dependency in maven!)
// TODO: Create JMH test
// TODO: Split method `convert`. It is too large.
public class SimpleBeanConverterTest {

    @Test
    public void convertTest() throws Throwable {
        Source source = new Source();
        source.setStringProperty("testValue");
        source.setIntProperty(7);
        source.setBigDecimalProperty(new BigDecimal("122"));
        source.setId(new ObjectId("56dd410ed4c6407deacd7e78"));
        source.setToLocalDateTime(LocalDateTime.now());
        source.setCreatedAt(LocalDateTime.now());
        source.setUpdatedAt(new Date());
        source.getStringList().addAll(Collections.singletonList("some_string"));

        Map<String, String> properties = new HashMap<>();
        properties.put("id", "MONGODB");
        properties.put("createdAt", "LOCAL_DATE_TIME");
        properties.put("updatedAt", "LOCAL_DATE_TIME");

        Bar result = SimpleBeanConverter.convert(source, Bar.class, properties, new HashMap<>());

        assertNotNull(result);
        assertEquals("testValue", result.getStringProperty());
        assertEquals(7, result.getIntProperty());
        assertEquals("56dd410ed4c6407deacd7e78", result.getId());
        assertEquals(LocalDateTime.class, result.getToLocalDateTime().getClass());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertNotNull(result.getStringList());
        assertEquals(1, result.getStringList().size());
        assertEquals("some_string", result.getStringList().get(0));

    }

    public static class Source {

        private String stringProperty;
        private int intProperty;
        private BigDecimal bigDecimalProperty;
        private ObjectId id;
        private LocalDateTime toLocalDateTime;
        private LocalDateTime createdAt;
        private Date updatedAt;
        private List<String> stringList = new ArrayList<>();

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

        public LocalDateTime getToLocalDateTime() {
            return toLocalDateTime;
        }

        public void setToLocalDateTime(LocalDateTime toLocalDateTime) {
            this.toLocalDateTime = toLocalDateTime;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<String> getStringList() {
            return stringList;
        }

        public void setStringList(List<String> stringList) {
            this.stringList = stringList;
        }
    }

    public static class Bar {

        private String stringProperty;
        private int intProperty;
        private String id;
        private LocalDateTime toLocalDateTime;
        private Date createdAt;
        private LocalDateTime updatedAt;
        private List<String> stringList = new ArrayList<>();

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

        public LocalDateTime getToLocalDateTime() {
            return toLocalDateTime;
        }

        public void setToLocalDateTime(LocalDateTime toLocalDateTime) {
            this.toLocalDateTime = toLocalDateTime;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<String> getStringList() {
            return stringList;
        }

        public void setStringList(List<String> stringList) {
            this.stringList = stringList;
        }
    }
}
