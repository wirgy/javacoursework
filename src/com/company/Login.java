package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Login extends JDialog
{
    //private static final long serialVersionUID = 1L;

    public JTextField tfLogin, tfPassword;
    public JButton    btnOk, btnSignUp;

    public Login(JFrame frame)
    {
        super(frame, "Экскурсии на ольхоне");

        centeringFrame(200, 200, frame);
        // При выходе из диалогового окна работа заканчивается
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
                System.exit(0);
            }
        });
        // добавляем расположение в центр окна
        getContentPane().add(createGUI());
        // задаем предпочтительный размер
        pack();
        // выводим окно на экран
        setVisible(true);
    }
    // этот метод будет возвращать панель с созданным расположением
    private JPanel createGUI()
    {
        // Создание панели для размещение компонентов
        JPanel panel = BoxLayoutUtils.createVerticalPanel();
        // Определение отступов от границ ранели. Для этого используем пустую рамку
        panel.setBorder (BorderFactory.createEmptyBorder(12,12,12,12));
        // Создание панели для размещения метки и текстового поля логина
        JPanel name = BoxLayoutUtils.createHorizontalPanel();
        JLabel nameLabel = new JLabel("Логин:");
        name.add(nameLabel);
        name.add(Box.createHorizontalStrut(12));
        tfLogin = new JTextField(12);
        name.add(tfLogin);
        // Создание панели для размещения метки и текстового поля пароля
        JPanel password = BoxLayoutUtils.createHorizontalPanel();
        JLabel passwrdLabel = new JLabel("Пароль:");
        password.add(passwrdLabel);
        password.add(Box.createHorizontalStrut(12));
        JPasswordField tfPassword = new JPasswordField(12);
        tfPassword.setEchoChar('*');
        password.add(tfPassword);
        // Создание панели для размещения кнопок управления
        JPanel flow = new JPanel( new FlowLayout( FlowLayout.RIGHT, 0, 0) );
        JPanel grid = new JPanel( new GridLayout( 1,2,5,0) );
        btnOk = new JButton("Вход");
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String uname = tfLogin.getText();
String pword = tfPassword.getText();

if(uname.equals("")||pword.equals("")){
    JOptionPane.showMessageDialog(rootPane, "Некоторые поля пусты", "Ошибка", 1);
}else {
try{
    Connection con = MySqlConnection.getConnection();
    PreparedStatement pst = con.prepareStatement("select * from login_table where username=? and password=?");
    pst.setString(1, uname);
    pst.setString(2, pword);
    ResultSet rs = pst.executeQuery();
    if(rs.next()){
        String s1 = rs.getString("options");
        String id = rs.getString("id");
        String username = rs.getString("username");
        if(s1.equalsIgnoreCase("admin")){
            new AdminMenu(id);
            setVisible(false);
        }
        if(s1.equalsIgnoreCase("worker")){
            new DriverMenu(id, username);
            setVisible(false);
        }
    }else{
        JOptionPane.showMessageDialog(rootPane, "Неверный логин или пароль", "Ошибка", 1);
    }
    
}catch (Exception ex){
    System.out.println("" +ex);
}

}
            }
        });

        btnSignUp = new JButton("Регистрация");
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               new SignUp();
            }
        });

        grid.add(btnOk);
        grid.add(btnSignUp);
        flow.add(grid);
        // Выравнивание вложенных панелей по горизонтали
        BoxLayoutUtils.setGroupAlignmentX(new JComponent[] { name, password, panel, flow },
                Component.LEFT_ALIGNMENT);
        // Выравнивание вложенных панелей по вертикали
        BoxLayoutUtils.setGroupAlignmentY(new JComponent[] { tfLogin, tfPassword, nameLabel, passwrdLabel},
                Component.CENTER_ALIGNMENT);
        // Определение размеров надписей к текстовым полям
        GUITools.makeSameSize(new JComponent[] { nameLabel, passwrdLabel } );
        // Определение стандартного вида для кнопок
        GUITools.createRecommendedMargin(new JButton[] { btnOk, btnSignUp } );
        GUITools.fixTextFieldSize(tfLogin);
        GUITools.fixTextFieldSize(tfPassword);

        // Сборка интерфейса
        panel.add(name);
        panel.add(Box.createVerticalStrut(12));
        panel.add(password);
        panel.add(Box.createVerticalStrut(17));
        panel.add(flow);
        return panel;
    }
    private static void centeringFrame (int sizeWidth, int sizeHeight, JFrame frame) {
        Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
        int X = (s.width - sizeWidth) / 2;
        int Y = (s.height - sizeHeight) / 2;
        frame.setBounds(X, Y, sizeWidth, sizeHeight);
    }
}

