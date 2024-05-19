package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ActionListener {
    private Server server;
    private JTextArea statusArea;
    private JButton startButton;
    private JButton stopButton;

    public ServerGUI() {
        setTitle("Server Control Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        statusArea = new JTextArea();
        statusArea.setEditable(false);
        panel.add(new JScrollPane(statusArea));

        startButton = new JButton("Start Server");
        startButton.addActionListener(this);
        panel.add(startButton);

        stopButton = new JButton("Stop Server");
        stopButton.addActionListener(this);
        panel.add(stopButton);

        add(panel);
        setVisible(true);
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void appendStatus(String text) {
        statusArea.append(text + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            if (server != null) {
                new Thread(() -> server.start()).start();
                appendStatus("Server started.");
            }
        } else if (e.getSource() == stopButton) {
            if (server != null) {
                server.stop();
                appendStatus("Server stopped.");
                System.exit(0); // Exit the system
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        ServerGUI serverGUI = new ServerGUI();
        serverGUI.setServer(server);
    }
}








//package Server;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class ServerGUI extends JFrame implements ActionListener {
//    private Server server;
//    private JTextArea statusArea;
//    private JButton startButton;
//    private JButton stopButton;
//
//    public ServerGUI() {
//        setTitle("Server Control Panel");
//        setSize(400, 300);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(3, 1));
//
//        statusArea = new JTextArea();
//        statusArea.setEditable(false);
//        panel.add(new JScrollPane(statusArea));
//
//        startButton = new JButton("Start Server");
//        startButton.addActionListener(this);
//        panel.add(startButton);
//
//        stopButton = new JButton("Stop Server");
//        stopButton.addActionListener(this);
//        panel.add(stopButton);
//
//        add(panel);
//        setVisible(true);
//    }
//
//    public void setServer(Server server) {
//        this.server = server;
//    }
//
//    public void appendStatus(String text) {
//        statusArea.append(text + "\n");
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == startButton) {
//            if (server != null) {
//                new Thread(() -> server.start()).start();
//                appendStatus("Server started.");
//            }
//        } else if (e.getSource() == stopButton) {
//            if (server != null) {
//                server.stop();
//                appendStatus("Server stopped.");
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        Server server = new Server();
//        ServerGUI serverGUI = new ServerGUI();
//        serverGUI.setServer(server);
//    }
//}
//
//
//
//
//////package Server;
//////
//////import javax.swing.*;
//////import java.awt.*;
//////import java.awt.event.ActionEvent;
//////import java.awt.event.ActionListener;
//////import java.io.IOException;
//////
//////public class ServerGUI extends JFrame implements ActionListener {
//////    private Server server;
//////    private JTextArea statusArea;
//////    private JButton startButton;
//////    private JButton stopButton;
//////
//////    public ServerGUI() {
//////        setTitle("Server Control Panel");
//////        setSize(400, 300);
//////        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//////
//////        JPanel panel = new JPanel();
//////        panel.setLayout(new GridLayout(3, 1));
//////
//////        statusArea = new JTextArea();
//////        statusArea.setEditable(false);
//////        panel.add(new JScrollPane(statusArea));
//////
//////        startButton = new JButton("Start Server");
//////        startButton.addActionListener(this);
//////        panel.add(startButton);
//////
//////        stopButton = new JButton("Stop Server");
//////        stopButton.addActionListener(this);
//////        panel.add(stopButton);
//////
//////        add(panel);
//////        setVisible(true);
//////    }
//////
//////    public void setServer(Server server) {
//////        this.server = server;
//////    }
//////
//////    public void appendStatus(String text) {
//////        statusArea.append(text + "\n");
//////    }
//////
//////    @Override
//////    public void actionPerformed(ActionEvent e) {
//////        if (e.getSource() == startButton) {
//////            if (server != null) {
//////                server.start();
//////                appendStatus("Server started.");
//////            }
//////        } else if (e.getSource() == stopButton) {
//////            if (server != null) {
//////                server.stop();
//////                appendStatus("Server stopped.");
//////            }
//////        }
//////    }
//////
//////    public static void main(String[] args) {
//////        Server server = new Server();
//////        ServerGUI serverGUI = new ServerGUI();
//////        serverGUI.setServer(server);
//////    }
//////}
////package Server;
////
////import javax.swing.*;
////import java.awt.*;
////import java.awt.event.ActionEvent;
////import java.awt.event.ActionListener;
////
////public class ServerGUI extends JFrame implements ActionListener {
////    private Server server;
////    private JTextArea statusArea;
////    private JButton startButton;
////    private JButton stopButton;
////
////    public ServerGUI() {
////        setTitle("Server Control Panel");
////        setSize(400, 300);
////        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////
////        JPanel panel = new JPanel();
////        panel.setLayout(new GridLayout(3, 1));
////
////        statusArea = new JTextArea();
////        statusArea.setEditable(false);
////        panel.add(new JScrollPane(statusArea));
////
////        startButton = new JButton("Start Server");
////        startButton.addActionListener(this);
////        panel.add(startButton);
////
////        stopButton = new JButton("Stop Server");
////        stopButton.addActionListener(this);
////        panel.add(stopButton);
////
////        add(panel);
////        setVisible(true);
////    }
////
////    public void setServer(Server server) {
////        this.server = server;
////    }
////
////    public void appendStatus(String text) {
////        statusArea.append(text + "\n");
////    }
////
////    @Override
////    public void actionPerformed(ActionEvent e) {
////        if (e.getSource() == startButton) {
////            if (server != null) {
////                new Thread(() -> server.start()).start();
////                appendStatus("Server started.");
////            }
////        } else if (e.getSource() == stopButton) {
////            if (server != null) {
////                server.stop();
////                appendStatus("Server stopped.");
////            }
////        }
////    }
////
////    public static void main(String[] args) {
////        Server server = new Server();
////        ServerGUI serverGUI = new ServerGUI();
////        serverGUI.setServer(server);
////    }
////}
