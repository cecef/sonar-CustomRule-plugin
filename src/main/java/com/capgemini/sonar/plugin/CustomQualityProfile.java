/*
 * Copyright (c) $yalami
 *  Date: 2016.03.02
 */

package com.capgemini.sonar.plugin;

import com.capgemini.sonar.plugin.rules.*;
import org.slf4j.LoggerFactory;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleAnnotationUtils;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.plugins.java.Java;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Quality profile definition.
 * This profile groups all rules bundled with this plugin.
 */
public class CustomQualityProfile extends ProfileDefinition {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomQualityProfile.class);

    public static final String CUSTOM_QA_PROFILE = "Capgemini Custom Java profile";

    private final RuleFinder ruleFinder;

    public CustomQualityProfile(RuleFinder ruleFinder) {
        this.ruleFinder = ruleFinder;
        logger.debug("constructor...");
    }


    @Override
    public RulesProfile createProfile(ValidationMessages validationMessages) {
        System.out.println("#capgemini | CustomQualityProfile.createProfile");
        Collection<Class> annotatedCollection = new ArrayList<Class>();
        annotatedCollection.add(TestRule.class);
        annotatedCollection.add(PublicAccessImportRule.class);
        annotatedCollection.add(GetterIsRule.class);
        annotatedCollection.add(UnitTestCoverageRule.class);
        annotatedCollection.add(InterceptorsAnnotation.class);

        RulesProfile profile = RulesProfile.create(CUSTOM_QA_PROFILE, Java.KEY);
        for (Class ruleClass : annotatedCollection) {
            String ruleKey = RuleAnnotationUtils.getRuleKey(ruleClass);
            logger.debug("-ruleKey: {} for class: {}",ruleKey,ruleClass);
            Rule rule = ruleFinder.findByKey(CustomRulesDefinition.REPOSITORY_KEY, ruleKey);
            logger.debug("-rule: {}",rule);
            profile.activateRule(rule, null);
        }
        return profile;
    }



    /**
     * sources:
     *  https://github.com/SonarSource/sonar-java/blob/master/sonar-java-plugin/src/main/java/org/sonar/plugins/java/JavaSonarWayProfile.java
     *  http://stackoverflow.com/questions/24204641/how-to-create-rule-repository-and-load-it-with-some-checklist-rule-in-sonarqube
     */


}