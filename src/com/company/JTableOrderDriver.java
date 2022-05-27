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

public class JTableOrderDriver {

    private static final String TABLE_NAME = "orders";

    public JTableOrderDriver(){
        try{

            Connection con = MySqlConnection.getConnection();

            final TableOrderModelAdmin mod = new TableOrderModelAdmin(con, TABLE_NAME);

            JTable jtable = new JTable(mod);

            TableDriverRender cellRender = new TableDriverRender();
            jtable.setDefaultRenderer(Object.class, cellRender);

            JScrollPane scroller = new JScrollPane(jtable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            JFrame frame = new JFrame("Заказы");
            frame.setSize(500,200);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            JButton updateButton = new JButton("обновить");


            JPanel panel = new JPanel();
            panel.add(updateButton);
            panel.add(scroller);

            frame.getContentPane().add(panel);

            frame.addWindowStateListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(con!=null) try {
                        con.close();
                    }catch (SQLException ex) {
                        Logger.getLogger(JTableEdit.class.getName()).log(Level.SEVERE, null, ex);
                    }
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


            frame.setVisible(true);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
