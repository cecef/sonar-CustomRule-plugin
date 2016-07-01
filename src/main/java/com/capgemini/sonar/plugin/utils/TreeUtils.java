/*
 * Copyright (c) $yalami
 *  Date: 2016.03.02
 */

package com.capgemini.sonar.plugin.utils;

import org.sonar.plugins.java.api.tree.*;

import javax.annotation.Nullable;
import java.util.Deque;
import java.util.LinkedList;

public class TreeUtils {

    public static String concatenate(@Nullable ExpressionTree tree) {
        if (tree == null) {
            return "";
        }
        Deque<String> pieces = new LinkedList<String>();

        ExpressionTree expr = tree;
        while (expr.is(Tree.Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mse = (MemberSelectExpressionTree) expr;
            pieces.push(mse.identifier().name());
            pieces.push(".");
            expr = mse.expression();
        }
        if (expr.is(Tree.Kind.IDENTIFIER)) {
            IdentifierTree idt = (IdentifierTree) expr;
            pieces.push(idt.name());
        }

        StringBuilder sb = new StringBuilder();
        for (String piece : pieces) {
            sb.append(piece);
        }
        return sb.toString();
    }

    public final static String getTypeName(Tree typeTree) {
        if (typeTree != null) {
            if (typeTree.is(Tree.Kind.PRIMITIVE_TYPE)){
                return (((PrimitiveTypeTree) typeTree).keyword().text());
            } else if(typeTree.is(Tree.Kind.ARRAY_TYPE)) {
                //TODO fix this quick solution to evade false positive
                return typeTree.toString();
            } else if (typeTree.is(Tree.Kind.IDENTIFIER)) {
                return ((IdentifierTree) typeTree).name();
            } else if (typeTree.is(Tree.Kind.MEMBER_SELECT)) {
                return ((MemberSelectExpressionTree) typeTree).identifier().name();
            } else if (typeTree.is(Tree.Kind.PARAMETERIZED_TYPE)) {
                return getTypeName(((ParameterizedTypeTree) typeTree).type());
            }
        }
        return "";
    }

    public static String getMethodHash(@Nullable MethodTree tree, @Nullable String methodNameSubstitution){
        if (tree == null) return "";
        StringBuilder ret = new StringBuilder();
        String METHOD_HASH_DELIMITER = ".";
        /* METHOD MODIFIER */
        if (tree.modifiers()!=null) {
            for (org.sonar.plugins.java.api.tree.Modifier modifier:tree.modifiers().modifiers()) {
                ret.append(modifier.name()).append(METHOD_HASH_DELIMITER);
            }
        }

        /* METHOD RETURN NAME */
        if (tree.returnType()!=null){
            ret.append(getTypeName(tree.returnType()));
            ret.append(METHOD_HASH_DELIMITER);
        }

        /* METHOD NAME */
        if (methodNameSubstitution==null){
            ret.append(tree.simpleName().name()).append(METHOD_HASH_DELIMITER);
        }else{
            ret.append(methodNameSubstitution).append(METHOD_HASH_DELIMITER);
        }

        /* METHOD PARAMETERS*/
        if (tree.parameters()!=null){
            for (org.sonar.plugins.java.api.tree.VariableTree variableTree:tree.parameters()){
                ret.append(getTypeName(variableTree.type())).append(METHOD_HASH_DELIMITER);
                ret.append(variableTree.simpleName().name()).append(METHOD_HASH_DELIMITER);
            }
        }

        return ret.toString();
    }



    public static String getAnnottationdHash(@Nullable AnnotationTree tree, @Nullable String annotationNameSubstitution){
        if (tree == null) return "";
        StringBuilder ret = new StringBuilder();
        String METHOD_HASH_DELIMITER = ".";

        /* METHOD RETURN NAME */
        if (tree.annotationType()!=null){
            ret.append(getTypeName(tree.annotationType()));
            ret.append(METHOD_HASH_DELIMITER);
        }

        /* METHOD NAME */
        if (annotationNameSubstitution==null){
            ret.append(tree.toString()).append(METHOD_HASH_DELIMITER);
        }else{
            ret.append(annotationNameSubstitution).append(METHOD_HASH_DELIMITER);
        }


        return ret.toString();
    }


    /**
     * Obtain unique identifier fro anonymous class
     *
     * @param newClassTree
     * @return type name of anonymous class + unique toString for given object instance
     */
    public static String getNewClassTreeId(NewClassTree newClassTree){
        StringBuilder strb = new StringBuilder();
        strb.append(TreeUtils.getTypeName(newClassTree.identifier())).append(":");
        strb.append(newClassTree.toString());//Solves problem with multiple anonymous classes of the same type
        return strb.toString();

    }
}
