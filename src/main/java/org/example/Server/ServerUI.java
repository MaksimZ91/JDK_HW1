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
