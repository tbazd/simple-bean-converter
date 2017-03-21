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
 * {@link SimpleBeanConverter} abstract test.
 *
 * @author vknysh
 */
public class SimpleBeanConverterAbstractTest {

    @Test
    public void convertTest() throws Throwable {
        Source source = new Source();

        Map<String, String> properties = new HashMap<>();
        properties.put("id", "MONGODB");
        properties.put("createdAt", "LOCAL_DATE_TIME");
        properties.put("updatedAt", "LOCAL_DATE_TIME");

        Bar result = SimpleBeanConverter.convert(source, Bar.class, properties, new HashMap<>());

        assertNotNull(result);


    }

    public static class Source {

        private String stringProperty;
        private int intProperty;
        private BigDecimal bigDecimalProperty;

    }

    public static class Bar {

        private String stringProperty;
        private int intProperty;
        private String id;

    }
}
