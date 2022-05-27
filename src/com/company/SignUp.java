package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUp {

    private static final String TABLE_NAME = "login_table";

    public boolean addDB(String tableName, String firstname, String lastname, String username, String password) {
        Connection con = MySqlConnection.getConnection();
        ArrayList<String> sqlList = new ArrayList();
        sqlList.add("INSERT INTO `" + tableName + "`(`id`, `firstname`, `lastname`, `username`, `password`, `options`) VALUES (NULL,'" + firstname + "','" + lastname + "','" + username + "','" + password + "','Worker')");

        Statement statement = null;

        try {
            statement = con.createStatement();

            for (String sql : sqlList) {
                statement.executeUpdate(sql);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TableDriverModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(TableDriverModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return true;

    }

    public SignUp() {
        try {

            JFrame addToTable_frame = new JFrame("Регистрация");
            addToTable_frame.setSize(new Dimension(800, 200));
            addToTable_frame.setLocationRelativeTo(null);
            addToTable_frame.setLayout(new GridBagLayout());

            JLabel nameLabel = new JLabel(" имя: ");
            JTextField nameField = new JTextField(15);
            nameField.setToolTipText("имя");

            JLabel lastnameLabel = new JLabel(" фамилия: ");
            JTextField lastnameField = new JTextField(15);
            lastnameField.setToolTipText("фамилия");

            JLabel loginLabel = new JLabel(" логин: ");
            JTextField loginField = new JTextField(10);
            loginField.setToolTipText("логин");

            JLabel passwordLabel = new JLabel(" пароль: ");
            JTextField passwordField = new JTextField(7);
            passwordField.setToolTipText("пароль");

            JButton addButton = new JButton("Зарегистрироваться");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String firstname = nameField.getText();
                    String lastname = lastnameField.getText();
                    String username = loginField.getText();
                    String password = passwordField.getText();
                    if (addDB(TABLE_NAME, firstname, lastname, username, password))
                        JOptionPane.showMessageDialog(null, "Данные успешно обновлены!");
                    else JOptionPane.showMessageDialog(null, "Ошибка при обновлении данных!");

                    addToTable_frame.dispose();
                }
            });

            addToTable_frame.add(nameLabel);
            addToTable_frame.add(nameField);
            addToTable_frame.add(lastnameLabel);
            addToTable_frame.add(lastnameField);
            addToTable_frame.add(loginLabel);
            addToTable_frame.add(loginField);
            addToTable_frame.add(passwordLabel);
            addToTable_frame.add(passwordField);
            addToTable_frame.add(addButton);
            addToTable_frame.setVisible(true);
            addToTable_frame.pack();

        } catch (HeadlessException e) {
            e.printStackTrace();
        }

    }
}
