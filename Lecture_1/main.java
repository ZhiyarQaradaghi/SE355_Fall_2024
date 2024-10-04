package Lecture_1;

import java.net.Socket;

import javax.swing.*;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        GoogleSocket gs = new GoogleSocket();
        SwingUtilities.invokeLater(() -> {
            Frame obj2 = new Frame(gs);
            obj2.setTitle("Sockets");
        });
    }
}

class GoogleSocket {
    String host;
    Boolean connection;
    public void sendDOSGoogle(){
        try {
            for (int i = 0; i < 6500000 || 1 == 1; i++) {
                Socket obj = new Socket("google.com", 80);
                System.out.println("Host: " + obj.getInetAddress().getHostName());
                host = obj.getInetAddress().getHostName();
                System.out.println("Connected? :" + obj.isConnected());
                connection = obj.isConnected();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

class Frame extends JFrame {
    public Frame(GoogleSocket gs) {
        init(gs);
    }

    public void init(GoogleSocket gs) {
        this.setSize(500, 500);

        JTextArea jt1 = new JTextArea(){
            
        };
        jt1.setBounds(50, 50, 250, 250);
        this.add(jt1);
        jt1.setText(gs.host);

        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

}