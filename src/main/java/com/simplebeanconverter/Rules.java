package com.simplebeanconverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Rules.
 *
 * @author vknysh
 */
public class Rules {

    private Map<String, String> rules = new HashMap<>();
    private Map<String, Class<?>> enclosed = new HashMap<>();

    public Rules(Map<String, String> rules, Map<String, Class<?>> enclosed) {
        this.rules = rules;
        this.enclosed = enclosed;
    }

    public static RulesBuilder builder() {
        return new RulesBuilder();
    }

    public Map<String, String> getRules() {
        return rules;
    }

    public Map<String, Class<?>> getEnclosed() {
        return enclosed;
    }

    public static class RulesBuilder {
        private Map<String, String> builderRules = new HashMap<>();
        private Map<String, Class<?>> builderEnclosed = new HashMap<>();

        public RulesBuilder mongoDbRule(String field) {
            builderRules.put(field, "MONGODB");
            return this;
        }

        public RulesBuilder localDateTimeRule(String field) {
            builderRules.put(field, "LOCAL_DATE_TIME");
            return this;
        }

        /**
         * Add enclosed convert rule.
         *
         * @param field field name
         * @param to target class
         * @return {@code this}
         */
        public RulesBuilder enclosed(String field, Class<?> to) {
            builderEnclosed.put(field, to);
            return this;
        }

        public Rules build() {
            return new Rules(builderRules, builderEnclosed);
        }
    }
}
