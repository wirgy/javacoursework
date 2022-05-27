package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DriverMenu {



    public DriverMenu(String id, String username){
        try{

            JFrame frame = new JFrame("Меню водителя");
            frame.setSize(200,250);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton TableDriverButton = new JButton("Мои машины");
            JButton TableAccountButton = new JButton("Сменить пароль");
            JButton OrderButton = new JButton("Заказы");

JLabel hellolabel = new JLabel("Здравствуйте, " + username);

            JPanel panel = new JPanel();
            panel.add(hellolabel);
            panel.add(TableDriverButton);
            panel.add(TableAccountButton);
            panel.add(OrderButton);


            frame.getContentPane().add(panel);

            TableDriverButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new JTableCarDrivers(id);
                }
            });
            OrderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
  new JTableOrderDriver();
                }
            });

            TableAccountButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ResetPassword(id);
                }
            });

            frame.setVisible(true);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}

