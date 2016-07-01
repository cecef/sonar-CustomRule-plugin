/*
 * Copyright (c) $yalami
 *  Date: 2016.03.02
 */

package com.capgemini.sonar.plugin.rules;

import com.capgemini.sonar.plugin.CustomQualityProfile;
import com.capgemini.sonar.plugin.CustomRulesDefinition;
import org.slf4j.LoggerFactory;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;

/**
 * This class is an example of how to implement your own rules.
 * The (stupid) rule raises a minor issue each time a method is encountered.
 * The @Rule annotation allows to specify the rule key, name, description and default severity.
 */
@Rule(key = TestRule.KEY,
      priority = Priority.MINOR,
      name = TestRule.NAME,
      description = TestRule.DESCRIPTION,
      tags = {TestRule.TAG})

@BelongsToProfile(title = CustomQualityProfile.CUSTOM_QA_PROFILE, priority = Priority.BLOCKER)
/**
 * The class extends BaseTreeVisitor: the visitor for the Java AST.
 * The logic of the rule sonais implemented by overriding its methods.
 * It also implements the JavaFileScanner interface to be injected with the JavaFileScannerContext to attach issues to this context.
 *
 */

public class TestRule extends BaseTreeVisitor implements JavaFileScanner {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TestRule.class);

    public static final String KEY = "custom-test-rule";
    private final RuleKey RULE_KEY = RuleKey.of(CustomRulesDefinition.REPOSITORY_KEY, KEY);
    public static final String NAME = "Custom test rule";
    public static final String DESCRIPTION = "Custom test rule to check if custom plugin works.";

    /**
     * Tags(e.g. tags = {"dcs"}) must follow specific rule given by org.sonar.api.server.rule.RuleTagFormat(sonar-plugin-api-4.2.jar)
     * Rule tags accept only the following characters: a-z, 0-9, '+', '-', '#', '.'
     */
    public static final String TAG = "capgemini-custom";

    public TestRule() {
        super();
        logger.debug("constructed...");
    }

    /**
     * Private field to store the context: this is the object used to create issues.
     */
    private JavaFileScannerContext context;

    /**
     * Implementation of the method of the JavaFileScanner interface.
     * @param context Object used to attach issues to source file.
     */
    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;

        logger.debug("visited...");

        // The call to the scan method on the root of the tree triggers the visit of the AST by this visitor
        scan(context.getTree());

        // For debugging purpose, you can print out the entire AST of the analyzed file
        //System.out.println(PrinterVisitor.print(context.getTree()));

    }


    /**
     * Overriding the visitor method to implement the logic of the rule.
     * @param tree AST of the visited method.
     */
    @Override
    public void visitMethod(MethodTree tree) {

        logger.debug("visited...");


        // All the code located before the call to the overridden method is executed before visiting the node

        // Adds an issue by attaching it with the tree and the rule
        context.addIssue(tree, RULE_KEY, "TestRule raised on method.");

        // The call to the super implementation allows to continue the visit of the AST.
        // Be careful to always call this method to visit every node of the tree.
        super.visitMethod(tree);

        // All the code located after the call to the overridden method is executed when leaving the node
    }

}