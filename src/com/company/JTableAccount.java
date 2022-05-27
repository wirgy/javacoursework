package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JTableAccount {

    private static final String TABLE_NAME = "login_table";

    public JTableAccount(String id){
        try{

            Connection con = MySqlConnection.getConnection();

            final TableAccountModel mod = new TableAccountModel(con, TABLE_NAME);

            JTable jtable = new JTable(mod);

            TableDriverRender cellRender = new TableDriverRender();
            jtable.setDefaultRenderer(Object.class, cellRender);

            JScrollPane scroller = new JScrollPane(jtable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            JFrame frame = new JFrame("Таблица аккаунтов");
            frame.setSize(800,400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton refreshButton = new JButton("Загрузить в базу данных");
            JButton deleteButton = new JButton("Удалить строку");
            JButton updateButton = new JButton("обновить");
            JButton addButton = new JButton("добавить в бд");


            JPanel panel = new JPanel();
            panel.add(addButton);
            panel.add(refreshButton);
            panel.add(updateButton);
            panel.add(deleteButton);
            panel.add(scroller);

            frame.getContentPane().add(panel);

            frame.addWindowStateListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(con!=null) try {
                        con.close();
                    }catch (SQLException ex) {
                        Logger.getLogger(JTableAccount.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            refreshButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (mod.updateDB(TABLE_NAME)) JOptionPane.showMessageDialog(null, "Данные успешно обновлены!");
                    else JOptionPane.showMessageDialog(null, "Ошибка при обновлении данных!");

                }
            });
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        mod.updateJTable(TABLE_NAME);
                        mod.fireTableDataChanged();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = jtable.getSelectedRow();
                    if (mod.deleteDB(TABLE_NAME, row)) JOptionPane.showMessageDialog(null, "Данные успешно обновлены!");
                    else JOptionPane.showMessageDialog(null, "Ошибка при обновлении данных!");
                    try {
                        mod.updateJTable(TABLE_NAME);
                        mod.fireTableDataChanged();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame addToTable_frame = new JFrame("Добавление в таблицу");
                    addToTable_frame.setSize(new Dimension(500, 200));
                    addToTable_frame.setLocationRelativeTo(null);
                    addToTable_frame.setLayout(new GridBagLayout());

                    String id = Integer.toString(mod.contents.length);

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

                    JButton addButton = new JButton("Добавить");
                    addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String firstname = nameField.getText();
                            String lastname = lastnameField.getText();
                            String username = loginField.getText();
                            String password = passwordField.getText();
                            if (mod.addDB(TABLE_NAME, firstname, lastname, username, password)) JOptionPane.showMessageDialog(null, "Данные успешно обновлены!");
                            else JOptionPane.showMessageDialog(null, "Ошибка при обновлении данных!");
                            try {
                                mod.updateJTable(TABLE_NAME);
                                mod.fireTableDataChanged();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
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
                }
            });

            frame.setVisible(true);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
