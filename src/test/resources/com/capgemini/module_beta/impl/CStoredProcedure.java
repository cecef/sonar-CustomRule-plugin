/*
 * Copyright (c) $yalami
 *  Date: 2016.03.02
 */

package com.capgemini.module_beta.impl;

import java.sql.SQLException;


public class CStoredProcedure implements ISQLCall{


    public void registerOutParameter()throws SQLException {}

    public void setString() throws SQLException {}

    public void setInt() throws SQLException {}

    @Override
    public void execute(){}

    public void executeQuery(){}

    public void executeStringQuery(){}

    public void executeLongQuery(){}

    public void executeIntQuery(){}

    public void executeDateQuery(){}

    public void executeDatabaseQuery(){}

    public void executeStringQueryNoLogs(){}

    public void executeQueryWithoutLogs(){}

    public void xExecute(){}

    public void xExecuteQuery(){}

    @Override
    public void closeEverything(){}

    public void closeEverythingLogCursorCount(){}

    public void closeRegisteredCursors(){}

    @Override
    public void getConnection() throws SQLException {}

    @Override
    public void getResultSet() throws SQLException {}
}
