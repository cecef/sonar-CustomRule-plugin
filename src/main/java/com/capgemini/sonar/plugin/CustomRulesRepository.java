/*
 * Copyright (c) $yalami
 *  Date: 2016.03.02
 */

package com.capgemini.sonar.plugin;

import com.capgemini.sonar.plugin.rules.*;
import org.slf4j.LoggerFactory;
import org.sonar.api.BatchExtension;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannersFactory;

import java.util.Arrays;

/**
 * Custom rules definition 2(RUN TIME - Sonar analyzer appearance).
 *
 * Create a class implementing BatchExtension and JavaFileScannersFactory
 * -> its purpose is to make all your custom java rules available during batch analysis
 * by returning instances of your rules. This extension is loaded during analysis.
 *
 * User: ${yalami}
 * Date: 2016.03.02
 */
public class CustomRulesRepository implements JavaFileScannersFactory, BatchExtension {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomQualityProfile.class);

    public CustomRulesRepository() {
        super();
        logger.debug("constructor...");
    }

    @Override
    public Iterable<JavaFileScanner> createJavaFileScanners() {
        return Arrays.<JavaFileScanner>asList(new TestRule(), new PublicAccessImportRule(), new GetterIsRule(), new UnitTestCoverageRule(), new InterceptorsAnnotation());
    }

}
