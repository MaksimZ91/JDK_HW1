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

