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
 * Created by yalami on 09/03/2016.
 */
public class InterceptorsAnnotationTest {
    @Rule
    public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();


    @Test
    public void InterceptorsAnnotationRule(){

        File src = new File("src/test/resources/com/capgemini/module_alfa/api/AModuleApi.java");

        Assert.assertNotNull(src);
        Assert.assertTrue(src.exists());
        VisitorsBridge vb = new VisitorsBridge(new InterceptorsAnnotation());
        SourceFile file = JavaAstScanner.scanSingleFile(src, vb);

        checkMessagesVerifier.verify(file.getCheckMessages())
                .noMore();

    }
}
