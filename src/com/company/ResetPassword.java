package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResetPassword {

    private static final String TABLE_NAME = "login_table";

    public boolean updatePassword(String tableName, String id, String updateField){
        Connection con = MySqlConnection.getConnection();
        ArrayList<String> sqlList = new ArrayList();

        sqlList.add("UPDATE " + tableName + " SET `password`='" + updateField + "' WHERE `id`=" + id );

        Statement statement = null;

        try {
            statement = con.createStatement();

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

    public ResetPassword(String id){
        try{

            JFrame frame = new JFrame("смена пароля");
            frame.setSize(300,80);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton updateButton = new JButton("сменить пароль");
            JTextField updateField = new JTextField(7);

            JPanel panel = new JPanel();
            panel.add(updateField);
            panel.add(updateButton);



            frame.getContentPane().add(panel);

            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String password = updateField.getText();
                    updatePassword(TABLE_NAME, id, password);
                    JOptionPane.showMessageDialog(null, "Данные успешно обновлены!");
                    frame.dispose();
                }
            });


            frame.setVisible(true);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
