/*
 * Copyright (c) $yalami
 *  Date: 2016.03.02
 */

package com.capgemini.sonar.plugin.rules;

import com.capgemini.sonar.plugin.CustomRulesDefinition;
import com.capgemini.sonar.plugin.utils.TreeUtils;
import org.slf4j.LoggerFactory;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.rule.Severity;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;


/**
 *  Rule to check whether important classes/methods are coverage by unit test.
 *
 *  This rule should traverse all files and compute result at the end.
 *
 */
@Rule(key = UnitTestCoverageRule.KEY,
      priority = Priority.CRITICAL,
      name = UnitTestCoverageRule.NAME,
      description = UnitTestCoverageRule.DESCRIPTION,
      tags = {UnitTestCoverageRule.TAG})
public class UnitTestCoverageRule extends BaseTreeVisitor implements JavaFileScanner {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UnitTestCoverageRule.class);

    public static final String TAG = TestRule.TAG;
    public static final String SEVERITY = Severity.CRITICAL;
    public static final String KEY = "unit-test-coverage-rule";
    private final RuleKey RULE_KEY = RuleKey.of(CustomRulesDefinition.REPOSITORY_KEY, KEY);
    public static final String NAME = "Unit test coverage rule";
    public static final String DESCRIPTION = "Check for important methods to have unit test.";

    private String currentPackage;
    private StringBuilder classStorage;

    public UnitTestCoverageRule() {
        super();
        System.out.println("[69] UnitTestCoverageRule constructed...");
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

        //TODO http://sonarqube.15.x6.nabble.com/sonar-dev-sonar-java-plugin-access-src-test-tp5030251.html

//        DefaultFileSystem fs = new DefaultFileSystem();
//        fs.setBaseDir(new File("src/test/java/com/capgemini/sonar/plugin/rules/"));


//        Project project = new Project("key","branch","name");
//        DefaultJavaResourceLocator jrl = new DefaultJavaResourceLocator(project, null);
//        JavaAstScanner.scanSingleFile(new File("src/test/java/com/capgemini/sonar/plugin/rules/RemoteSourceUniversalTest.java"), new VisitorsBridge(jrl));


//        JavaConfiguration conf = new JavaConfiguration(Charset.forName("UTF-8"));
//        JavaSquid squid = new JavaSquid(conf, jrl);
//        squid.scanDirectories(
//                Collections.singleton(new File("src/test/java/com/capgemini/sonar/plugin/rules/")),
//                Collections.singleton(new File("build/classes/test/com/capgemini/sonar/plugin")));
//
        System.out.println(context.getFileKey());
        scan(context.getTree());
    }

    @Override
    public void visitCompilationUnit(CompilationUnitTree tree) {
        currentPackage = TreeUtils.concatenate(tree.packageName());
        super.visitCompilationUnit(tree);
    }

    @Override
    public void visitClass(ClassTree tree) {
        getClassStorage().append(currentPackage);
        getClassStorage().append(tree.simpleName().name()).append("\n");
        System.out.println(getClassStorage().toString());
    }

    private StringBuilder getClassStorage(){
        if (this.classStorage==null){
            return classStorage = new StringBuilder().append("[69]");
        } else {
            return classStorage;
        }
    }

}