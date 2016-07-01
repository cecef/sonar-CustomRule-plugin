package com.capgemini.sonar.plugin.rules;

/**
 * Created by yalami on 09/03/2016.
 */
import com.capgemini.sonar.plugin.CustomRulesDefinition;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.rule.Severity;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

import java.util.*;


@Rule(key = InterceptorsAnnotation.KEY,
        priority = Priority.CRITICAL,
        name = InterceptorsAnnotation.NAME,
        description = InterceptorsAnnotation.DESCRIPTION,
        tags = {InterceptorsAnnotation.TAG})

public class InterceptorsAnnotation extends BaseTreeVisitor implements JavaFileScanner {



    public static final Priority PRIORITY = Priority.INFO;
    public static final String SEVERITY = Severity.CRITICAL;
    public static final String TAG = TestRule.TAG;
    public static final String KEY = "InterceptorsAnnotation";
    private final RuleKey RULE_KEY = RuleKey.of(CustomRulesDefinition.REPOSITORY_KEY, KEY);
    public static final String NAME = "Usage Of Stateless and Stateful Annotation";
    public static final String DESCRIPTION = "If there is a @Stateless or @Stateful annotation on a class, there should be a @Interceptors annotation";
    public static final String VIOLATION_MESSAGE = "Stateless or Stateful used without @Interceptors annotation raised!";

    private final Map<String,List<String>> annotationRootNameMap;

    List<AnnotationTree> annotationTreeList = new ArrayList<>();


    private JavaFileScannerContext context;


    public InterceptorsAnnotation() {
        this.annotationRootNameMap = new HashMap<String,List<String>>();
    }

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;

        scan(context.getTree());

    }

    @Override
    public void visitClass(ClassTree tree) {
        List<AnnotationTree> annotations = tree.modifiers().annotations();
        for (AnnotationTree annotationTree : annotations) {

            if (annotationTree.annotationType().is(Tree.Kind.IDENTIFIER)) {
                IdentifierTree idf = (IdentifierTree) annotationTree.annotationType();
                System.out.println(idf.name());
                ListIterator<AnnotationTree> iterID2;
                if (idf.name().equals("Stateless") || idf.name().equals("Stateful"))  {
                    for (ListIterator<AnnotationTree> iterID = annotations.listIterator(); iterID.hasNext();) {
                        iterID2 = iterID;
                        if (annotations.get(iterID.nextIndex()).annotationType().is(Tree.Kind.IDENTIFIER)) {
                            IdentifierTree idf2 = (IdentifierTree) annotations.get(iterID.nextIndex()).annotationType();

                            if (iterID2.hasNext()) {
                                iterID2.next();
                            }
                                if (idf2.name().equals("Interceptors") || !idf2.name().equals("Interceptors") && (iterID.hasNext())) {
                                    if (idf2.name().equals("Interceptors")) {
                                        break;
                                    }

                                } else {
                                    context.addIssue(idf, this.RULE_KEY, VIOLATION_MESSAGE);
                                }

                        }
                }
                }
            }
        }

    }


}