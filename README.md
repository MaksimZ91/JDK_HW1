# Урок 1. Графические интерфейсы
Собрать графический интерфейс проекта месседжера (скриншоты можно посмотреть в материалах к уроку)  
Отправлять сообщения из текстового поля сообщения в лог по нажатию кнопки или по нажатию клавиши Enter на поле ввода сообщения;  
Продублировать импровизированный лог (историю) чата в файле;  
При запуске клиента чата заполнять поле истории из файла, если он существует. Обратите внимание, что чаще всего история сообщений хранится на сервере и заполнение истории чата лучше делать при соединении с сервером, а не при открытии окна клиента.  

![HW1](https://github.com/MaksimZ91/JDK_HW1/assets/72209139/0057d252-a947-47a7-8e1f-7c894dadb0a8)


## Main Class
```java
package org.example;

public class Main {
    public static void main(String[] args){
        new Controller();
    }
}
```
## Controller Class
```java
package org.example;

import org.example.Client.ClintUI;
import org.example.Loger.Logger;
import org.example.Server.Server;
import org.example.Server.ServerUI;

public class Controller {
    Server server;

    ServerUI serverUI;
    ClintUI clintUI;
    Logger logger;

    public Controller(){
      this.server  = new Server(this);
      this.serverUI = new ServerUI(this);
      this.clintUI = new ClintUI(this);
      this.logger = new Logger(this);
    }

    public void serverStart() throws Exception {
        server.serverStart();
    }
    public void serverStop() throws Exception {
        server.serverStop();
        clintUI.getTopPanel().setVisible(true);
    }
    public boolean login(String name) throws Exception {
       return server.login(name);
    }
    public void toLog (String message) throws Exception {
        logger.toLog(message);
    }
    public void sendMessage (String message) throws Exception {
        server.get(message);
    }
    public void toUI(String message) throws Exception {
        serverUI.logger(message);
        clintUI.historyChat(message);
    }
}
```
## Server Class
```java
package org.example.Server;


import org.example.Controller;
import org.example.Repository.Repository;

import java.io.FileNotFoundException;

public class Server {

    Repository repository = new Repository();
    private boolean status = false;
    private boolean isLogin = false;
    private final Controller controller;
    private String userName;
    public Server(Controller controller) {
        this.controller = controller;
    }
    public void serverStart() throws Exception {
        String message;
        if(!status){
            message = "System: Server start!";
            try {
                controller.toLog(repository.read("system_log"));
            } catch (FileNotFoundException ignored){

            }
            controller.toLog(message);
            repository.write(message, "system_log");
            status = true;
        } else {
            message = "System: Server already start!";
            controller.toLog(message);
            repository.write(message, "system_log");
        }
    }
    public void serverStop() throws Exception {
        String message;
        if(status){
            message = "System: Server stop!";
            controller.toLog(message);
            repository.write(message, "system_log");
            status = false;
        } else {
            message = "System: Server already stop!";
            controller.toLog(message);
            repository.write(message, "system_log");
        }
    }
    public boolean login(String name) throws Exception {
        String message;
        if(!status){
            message = "System: Server don't start!";
            controller.toLog(message);
            repository.write(message, "system_log");
            isLogin = false;
            return false;
        } else {
            message = name + " " + "connect to server!";
            userName = name;
            controller.toLog(message);
            repository.write(message, "system_log");
            isLogin = true;
            return true;
        }
    }
    public void get(String message) throws Exception {
        if(status && isLogin){
            if(!(message == null)){
                controller.toLog(userName + ": " + message);
                repository.write(userName + ": " + message, "system_log");
            }
        }
    }
}
```
## Loger Class
```java
package org.example.Server;
import org.example.Controller;
import org.example.Repository.Repository;
import java.io.FileNotFoundException;

public class Server {

    Repository repository = new Repository();
    private boolean status = false;
    private boolean isLogin = false;
    private final Controller controller;
    private String userName;
    public Server(Controller controller) {
        this.controller = controller;
    }
    public void serverStart() throws Exception {
        String message;
        if(!status){
            message = "System: Server start!";
            try {
                controller.toLog(repository.read("system_log"));
            } catch (FileNotFoundException ignored){

            }
            controller.toLog(message);
            repository.write(message, "system_log");
            status = true;
        } else {
            message = "System: Server already start!";
            controller.toLog(message);
            repository.write(message, "system_log");
        }
    }
    public void serverStop() throws Exception {
        String message;
        if(status){
            message = "System: Server stop!";
            controller.toLog(message);
            repository.write(message, "system_log");
            status = false;
        } else {
            message = "System: Server already stop!";
            controller.toLog(message);
            repository.write(message, "system_log");
        }
    }
    public boolean login(String name) throws Exception {
        String message;
        if(!status){
            message = "System: Server don't start!";
            controller.toLog(message);
            repository.write(message, "system_log");
            isLogin = false;
            return false;
        } else {
            message = name + " " + "connect to server!";
            userName = name;
            controller.toLog(message);
            repository.write(message, "system_log");
            isLogin = true;
            return true;
        }
    }
    public void get(String message) throws Exception {
        if(status && isLogin){
            if(!(message == null)){
                controller.toLog(userName + ": " + message);
                repository.write(userName + ": " + message, "system_log");
            }
        }
    }
}
```
## Repository Class
```java
package org.example.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Repository {
    public void write(String data, String name) throws IOException{
        try(FileWriter fr = new FileWriter(name +".txt", true)) {
            fr.write(data);
            fr.append(System.lineSeparator());
            fr.flush();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
    public  String read(String name) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        try(FileReader reader = new FileReader(name +".txt")){
            Scanner scan = new Scanner(reader);
            while (scan.hasNextLine()){
                sb.append(scan.nextLine()).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        return sb.toString();
    }
}
```
## ServerUI Class
```java
package org.example.Server;
import org.example.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerUI extends JFrame {
    private static final int WIDTH = 450;
    private static final int HEIGHT = 480;
    JButton btnStart, btnStop;
    JTextArea log;
    JPanel bottomPanel;
    Controller controller;
    public ServerUI(Controller controller){
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Chat server");
        bottomPanel = new JPanel(new GridLayout(1,2));
        setResizable(false);
        log = new JTextArea();
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");
        bottomPanel.add(btnStart);
        bottomPanel.add(btnStop);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.serverStart();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.serverStop();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(log);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
   public void logger(String message){
        log.setText(message);
   }
}
```
## ClientUI Class
```java
package org.example.Client;

import org.example.Controller;
import org.example.Server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ClintUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 480;
    private JButton btnLogin, btnSend,  mock;
    private JTextArea chat;
    private JPanel topPanel, sendPanel, socketPanel, loginPanel;
    private JTextField ip, port, name, message;
    private JPasswordField password;
    private Controller controller;

    public ClintUI(Controller controller){
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Client chat");
        build();
        setResizable(false);
        setVisible(true);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(controller.login(name.getText()))
                        topPanel.setVisible(false);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.sendMessage(message.getText());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void historyChat(String message){
        chat.setText(message);
    }
    private void build(){
        add(top(), BorderLayout.NORTH);
        add(mid());
        add(bottom(), BorderLayout.SOUTH);
    }
    private Component mid(){
        chat = new JTextArea();
        return chat;
    }
    private Component top(){
        btnLogin = new JButton("Login");
        ip = new JTextField("127.0.0.1",16);
        port = new JTextField("8189",5 );
        mock = new JButton("Mock");
        mock.setVisible(false);
        name = new JTextField("Ivan Ivanovich");
        password = new JPasswordField("*******");
        socketPanel = new JPanel(new GridLayout(1,3));
        socketPanel.add(ip);
        socketPanel.add(port);
        socketPanel.add(mock);
        loginPanel = new JPanel(new GridLayout(1,3));
        loginPanel.add(name);
        loginPanel.add(password);
        loginPanel.add(btnLogin);
        topPanel = new JPanel(new BorderLayout());
        topPanel.add(socketPanel, BorderLayout.NORTH);
        topPanel.add(loginPanel, BorderLayout.SOUTH);
        return topPanel;
    }
    private Component bottom(){
        btnSend = new JButton("Send");
        message = new JTextField();
        sendPanel = new JPanel(new GridLayout(1,2));
        sendPanel.add(message);
        sendPanel.add(btnSend);
        return sendPanel;
    }
    public JPanel getTopPanel() {
        return topPanel;
    }
}
```
