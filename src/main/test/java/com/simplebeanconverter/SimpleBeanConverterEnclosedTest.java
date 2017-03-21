package com.simplebeanconverter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * {@link SimpleBeanConverter} test.
 * Created on 3/19/16.
 *
 * @author vknysh
 */
public class SimpleBeanConverterEnclosedTest {

    @Test
    public void convertTest() throws Throwable {
        Source source = new Source();
        SourceEnclosed sourceEnclosed = new SourceEnclosed();
        sourceEnclosed.setValue("Some text");
        source.setEnclosed(sourceEnclosed);

        Target target = SimpleBeanConverter.convert(source,
                Target.class, Rules.builder().enclosed("enclosed", TargetEnclosed.class).build());
        assertNotNull(target);
        assertNotNull(target.getEnclosed());
        assertEquals(target.getEnclosed().getValue(), "Some text");

    }

    public static class Source {

        private SourceEnclosed enclosed;

        public SourceEnclosed getEnclosed() {
            return enclosed;
        }

        public void setEnclosed(SourceEnclosed enclosed) {
            this.enclosed = enclosed;
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

        private TargetEnclosed enclosed;

        public TargetEnclosed getEnclosed() {
            return enclosed;
        }

        public void setEnclosed(TargetEnclosed enclosed) {
            this.enclosed = enclosed;
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
