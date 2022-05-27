package com.company;

import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableAccountModel extends AbstractTableModel {

    private static Connection conn;

    public Object[][] contents;
    private String[] columnNames;
    private Class[] columnClasses;
    private ArrayList rowList;
    public TableAccountModel(Connection conn, String tableName) throws SQLException{
        super();
        TableAccountModel.conn = conn;
        getTableContents(tableName);
    }


    private void getTableContents(String tableName) throws SQLException {

        DatabaseMetaData meta = conn.getMetaData();

        ResultSet rs = meta.getColumns(null, null, tableName,null);

        ArrayList colNamesList = new ArrayList();//список имен столбцов
        ArrayList colTypesList = new ArrayList();// список типов столбцов

        while (rs.next()) {

            colNamesList.add(rs.getString("COLUMN_NAME"));

            int dbType = rs.getInt("DATA_TYPE");

            switch (dbType) {
                case Types.INTEGER:
                    colTypesList.add(Integer.class);
                    break;
                case Types.FLOAT:
                    colTypesList.add(Float.class);
                    break;
                case Types.DOUBLE:
                case Types.REAL:
                    colTypesList.add(Double.class);
                    break;
                case Types.DATE:
                case Types.TIME:
                case Types.TIMESTAMP:
                    colTypesList.add(java.sql.Date.class);
                    break;
                default:
                    colTypesList.add(String.class);
                    break;
            };

        }

//имена столбцов
        columnNames = new String[colNamesList.size()];
        colNamesList.toArray(columnNames);
//типы столбцов
        columnClasses = new Class[colTypesList.size()];
        colTypesList.toArray(columnClasses);



        Statement statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM " + tableName );

        rowList = new ArrayList(); //хранит записи из таблицы

//цикл по всем записям таблицы
        while (rs.next()) {
            ArrayList cellList = new ArrayList(); //хранит данные по каждому столбцу

            for (int i = 0; i < columnClasses.length; i++) {
                Object cellValue = null;

                if(columnClasses[i] == String.class) cellValue = rs.getString(columnNames[i]);
                else if(columnClasses[i] == Integer.class) cellValue = rs.getInt(columnNames[i]);
                else if(columnClasses[i] == Float.class) cellValue = rs.getInt(columnNames[i]);
                else if(columnClasses[i] == Double.class) cellValue = rs.getDouble(columnNames[i]);
                else if(columnClasses[i] == java.sql.Date.class) cellValue = rs.getDate(columnNames[i]);
                else System.out.println("Не могу определить тип поля " + columnNames[i]);

                cellList.add(cellValue);
            }

            Object[] cells = cellList.toArray();
            rowList.add(cells);

        }

        contents = new Object[rowList.size()][];
        for (int i = 0; i < contents.length; i++){
            contents[i] = (Object[]) rowList.get(i);
        }

        if (rs!=null) rs.close();
        if (statement!=null) statement.close();

    }

    public boolean updateDB(String tableName){

        ArrayList<String> sqlList = new ArrayList();

        for (int i = 0; i < contents.length; i++){
            Object[] objects = contents[i];

            sqlList.add("UPDATE `" + tableName + "` SET `фио`='" + objects[1] + "',`firstname`='"+ objects[2] +"',`lastname`='" + objects[3] + "',`username`='" + objects[4] + "',`username`='" + objects[5] + "' WHERE `id`=" + objects[0]);
        }

        Statement statement = null;

        try {
            statement = conn.createStatement();

            for(String sql : sqlList){
                statement.executeUpdate(sql);
            }

        }catch (SQLException ex){
            Logger.getLogger(TableDriverModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally {
            try {
                if (statement!=null) statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(TableDriverModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return true;

    }


    public boolean deleteDB(String tableName, int row){

        ArrayList<String> sqlList = new ArrayList();
        Object[] objects = contents[row];
        sqlList.add("DELETE FROM " + tableName + "` WHERE `id`=" + objects[0]);

        Statement statement = null;

        try {
            statement = conn.createStatement();

            for(String sql : sqlList){
                statement.executeUpdate(sql);
            }

        }catch (SQLException ex){
            Logger.getLogger(TableDriverModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally {
            try {
                if (statement!=null) statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(TableDriverModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return true;

    }

    public boolean addDB(String tableName, String firstname, String lastname, String username, String password){

        ArrayList<String> sqlList = new ArrayList();
        sqlList.add("INSERT INTO `" + tableName + "`(`id`, `firstname`, `lastname`, `username`, `password`, `options`) VALUES (NULL,'" + firstname + "','" + lastname + "','" + username + "','" + password + "','Worker')");

        Statement statement = null;

        try {
            statement = conn.createStatement();

            for(String sql : sqlList){
                statement.executeUpdate(sql);
            }

        }catch (SQLException ex){
            Logger.getLogger(TableDriverModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally {
            try {
                if (statement!=null) statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(TableDriverModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return true;

    }

    @Override
    public int getRowCount() {
        return contents.length;
    }

    @Override
    public int getColumnCount() {
        if (contents.length == 0) return 0;
        else return contents[0].length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return contents[row][column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        contents[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public Class getColumnClass(int col){
        return columnClasses[col];
    }

    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        if(columnIndex == 0){
            return false;
        }
        return true;
    }

    public void updateJTable(String tableName) throws SQLException {
        getTableContents(tableName);
    }

}
