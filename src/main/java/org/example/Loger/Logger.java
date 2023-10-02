package org.example.Loger;

import org.example.Controller;

public class Logger {

    Controller controller;
    private StringBuilder messages = new StringBuilder();

    public Logger(Controller controller) {
        this.controller = controller;
    }
    public void toLog(String message) throws Exception {
        messages.append(message).append(System.lineSeparator());
        controller.toUI(messages.toString());
    }

}
