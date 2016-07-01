/*
 * Copyright (c) $yalami
 *  Date: 2016.03.02
 */

package com.capgemini.sonar.plugin.rules;

import junit.framework.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.sonar.java.JavaAstScanner;
import org.sonar.java.model.VisitorsBridge;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

import java.io.File;


/**
 * In current build dependency is unit test working only when SONAR is UP&running.
 */
public class PublicAccessImportRuleTest {


    @Rule
    public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();


    @Test
    public void testForbiddenAccess(){

        File src = new File("src/test/resources/com/capgemini/module_alfa/api/AModuleApi.java");
        Assert.assertNotNull(src);
        Assert.assertTrue(src.exists());
        VisitorsBridge vb = new VisitorsBridge(new PublicAccessImportRule());
        SourceFile file = JavaAstScanner.scanSingleFile(src,vb);

        checkMessagesVerifier.verify(file.getCheckMessages())
                .next().atLine(4).withMessage(PublicAccessImportRule.VIOLATION_MESSAGE)
                .noMore();

    }
}
