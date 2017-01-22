package com.simplebeanconverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Rules
 *
 * @author vknysh
 */
public class Rules {

    private Map<String, String> rules = new HashMap<>();

    public Rules(Map<String, String> rules) {
        this.rules = rules;
    }

    public static RulesBuilder builder() {
        return new RulesBuilder();
    }

    public Map<String, String> getRules() {
        return rules;
    }

    public static class RulesBuilder {
        private Map<String, String> builderRules = new HashMap<>();

        public RulesBuilder mongoDbRule(String field) {
            builderRules.put(field, "MONGODB");
            return this;
        }

        public RulesBuilder localDateTimeRule(String field) {
            builderRules.put(field, "LOCAL_DATE_TIME");
            return this;
        }

        public Rules build() {
            return new Rules(builderRules);
        }
    }
}
