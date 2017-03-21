package com.simplebeanconverter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * {@link SimpleBeanConverter} test.
 * Created on 3/19/16.
 *
 * @author vknysh
 */
public class SimpleBeanConverterEnclosedListTest {

    @Test
    public void convertTest() throws Throwable {
        Source source = new Source();
        SourceEnclosed sourceEnclosed = new SourceEnclosed();
        sourceEnclosed.setValue("Some text");
        source.getEnclosedList().add(sourceEnclosed);

        Target target = SimpleBeanConverter.convert(source,
                Target.class, Rules.builder().enclosed("enclosedList", TargetEnclosed.class).build());
        assertNotNull(target);
        assertEquals(target.getEnclosedList().size(), 1);
        assertEquals(target.getEnclosedList().get(0).getValue(), "Some text");

    }

    public static class Source {

        private List<SourceEnclosed> enclosedList = new ArrayList<>();

        public List<SourceEnclosed> getEnclosedList() {
            return enclosedList;
        }

    }

    public static class SourceEnclosed {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Target {

        private List<TargetEnclosed> enclosedList;

        public List<TargetEnclosed> getEnclosedList() {
            return enclosedList;
        }

        public void setEnclosedList(List<TargetEnclosed> enclosedList) {
            this.enclosedList = enclosedList;
        }
    }

    public static class TargetEnclosed {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
