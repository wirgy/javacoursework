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

public class JTableCarDrivers {

    private static final String TABLE_NAME = "driver";

    public JTableCarDrivers(String id){
        try{

            Connection con = MySqlConnection.getConnection();

            final TableCarDrivers mod = new TableCarDrivers(con, TABLE_NAME, id);

            JTable jtable = new JTable(mod);

            TableDriverRender cellRender = new TableDriverRender();
            jtable.setDefaultRenderer(Object.class, cellRender);

            JScrollPane scroller = new JScrollPane(jtable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            JFrame frame = new JFrame("Таблица водителей");
            frame.setSize(500,150);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton deleteButton = new JButton("Удалить строку");
            JButton updateButton = new JButton("обновить");
            JButton addButton = new JButton("добавить в бд");


            JPanel panel = new JPanel();
            panel.add(addButton);
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
                        Logger.getLogger(JTableCarDrivers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        mod.updateJTable(TABLE_NAME, id);
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
                        mod.updateJTable(TABLE_NAME, id);
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

                    JLabel idLabel = new JLabel(" id: " + id);


                    JLabel carLabel = new JLabel(" Название ТС: ");
                    JTextField carField = new JTextField(10);

                    JLabel carNumberLabel = new JLabel(" Гос. номер ТС: ");
                    JTextField carNumberField = new JTextField(7);

                    JButton addButton = new JButton("Добавить");
                    addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String carName = carField.getText();
                            String carNumber = carNumberField.getText();
                            if  (mod.addDB(TABLE_NAME, id, carName, carNumber)) JOptionPane.showMessageDialog(null, "Данные успешно обновлены!");
                            else JOptionPane.showMessageDialog(null, "Ошибка при обновлении данных!");
                            try {
                                mod.updateJTable(TABLE_NAME, id);
                                mod.fireTableDataChanged();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            addToTable_frame.dispose();
                        }
                    });

                    addToTable_frame.add(idLabel);
                    addToTable_frame.add(carLabel);
                    addToTable_frame.add(carField);
                    addToTable_frame.add(carNumberLabel);
                    addToTable_frame.add(carNumberField);
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

