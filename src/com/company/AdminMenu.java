package com.company;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdminMenu {

    public AdminMenu(String id){
        try{

            JFrame frame = new JFrame("Админ меню");
            frame.setSize(550,120);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton TableDriverButton = new JButton("Таблица машин");
            JButton TableAccountButton = new JButton("Таблица аккаунтов");
            JButton TableOrderButton = new JButton("Таблица заказов");
            JButton resetPasswordButton = new JButton("сменить пароль");

            JPanel panel = new JPanel();
            panel.add(TableDriverButton);
            panel.add(TableAccountButton);
            panel.add(TableOrderButton);
            panel.add(resetPasswordButton);


            frame.getContentPane().add(panel);

            TableDriverButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
new JTableEdit();

                }
            });
            TableOrderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   new JTableOrderAdmin();
                }
            });

            TableAccountButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
new JTableAccount(id);
                }
            });

            resetPasswordButton.addActionListener(new ActionListener() {
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

