/*
 * Copyright (c) $yalami
 *  Date: 2016.03.02
 */

package com.capgemini.module_beta.impl;

import java.sql.SQLException;


public interface ISQLCall {

    public void getConnection() throws SQLException;

    public void closeEverything();

    public void getResultSet() throws SQLException;

    public void execute();
}
