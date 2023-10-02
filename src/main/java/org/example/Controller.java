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
