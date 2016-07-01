/*
 * Copyright (c) $yalami
 *  Date: 2016.03.02
 */

package com.capgemini.module_alfa.api;


import com.capgemini.module_beta.impl.BModuleImpl;
import com.sun.org.apache.xpath.internal.operations.String;
import com.sun.org.glassfish.gmbal.DescriptorKey;
import com.sun.org.glassfish.gmbal.ManagedObject;

import javax.jws.Oneway;


/**
 * User: ${yalami}
 * Date: 2016.03.02
 */
@Override
@Override
@Stateful

@ManagedObject
@ManagedObject
@Override
@Stateful

public class AModuleApi {

    public static String MODULE_A_API = "Api of module A";

    @Override
    public void testMethod(String o) {

        System.out.print(MODULE_A_API);
    }

    @DescriptorKey(AModuleApi)
    public void testMethod(String o) {

        System.out.print(MODULE_A_API);
    }

}