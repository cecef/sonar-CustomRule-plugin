/*
 * Copyright (c) $yalami
 *  Date: 2016.03.02
 */

package com.capgemini.module_beta.impl;


public class Level2 extends Level1 {

    public void getterIsRuleTest(){
        Label lb = new Label(){
            @Override
            public boolean isVisible() {
                return super.isVisible();
            }
        };
    }

    @Override
    public boolean isVisible() {
        return super.isVisible();
    }

    public void add() {

        new Label("automaticPayLabelLabel", true ? new String("bu2190.yes") : new String("bu2513.no")) {
            @Override
            public boolean isVisible() {
                return true;
            }
        };


        new Label("composit1", new Object(){
            @Override
            public String toString() {
                return super.toString();
            }
        }){
            @Override
            public Object getHeader() {
                return super.getHeader();
            }

            @Override
            public String getCssClass() {
                return super.getCssClass();
            }
        };


        new Label("composit2", new Object(){
            @Override
            public String toString() {
                return super.toString();
            }
        }){
            @Override
            public Object getHeader() {
                return super.getHeader();
            }

            @Override
            public String getCssClass() {
                return super.getCssClass();
            }
        };

    }
}
