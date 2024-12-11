package src;

import org.zeromq.*;
import java.io.*;
import java.util.*;

public class Process4 {
    public static void main(String[] args) {
        long lamportClock=0;
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.SUB);
            socket.connect("tcp://localhost:5558");
            socket.subscribe("file-p4");

            Message receivedMessage = null;
            boolean allChunksReceived = false;
            ArrayList<Message> receivedMessages = new ArrayList<>();

            while (!Thread.currentThread().isInterrupted()) {
                String topic = socket.recvStr(0); 
                byte[] messageBytes = socket.recv(0);
                receivedMessage = deserializeMessage(messageBytes);
                if ("END".equals(receivedMessage.getMessageType())) {
                    System.out.println("Received end-of-chunks message.");
                    allChunksReceived = true;
                    break; 
                }
                
                long receivedLamportClock = receivedMessage.getNewLamportClock();
                receivedMessage.setOldLamportClock(receivedLamportClock);
                lamportClock = Math.max(lamportClock, receivedLamportClock) + 1;  
                
                receivedMessage.setNewLamportClock(lamportClock);
                
                receivedMessages.add(receivedMessage);
                
                
                System.out.println("Old Lamport clock: " + receivedMessage.getOldLamportClock());
                System.out.println("New Lamport clock: " + receivedMessage.getNewLamportClock());
                System.out.println("Process 4 received chunk: " + new String(receivedMessage.getFileContent()));
            }
            
            if (allChunksReceived) {
                try (ZContext contextFromM = new ZContext()){
                    ZMQ.Socket ackFromM = contextFromM.createSocket(SocketType.SUB);
                    ackFromM.bind("tcp://*:8004");
                    ackFromM.subscribe("ackToP");
                    lamportClock++;
                    
                    
                        Thread.sleep(200);
                        byte[] reply = ackFromM.recv(0);
                        System.out.println("process 4: Received acknowledgement "+ new String(reply, ZMQ.CHARSET));
                        if (new String(reply, ZMQ.CHARSET).equals("ackToP")) {
                            System.out.println("Waiting 15 seconds...");
                            Thread.sleep(15000);
                        }
                    } catch (Exception e) {
                        System.err.println("Error in getting ack from main: "+e.getMessage());
                    }
                    System.out.println("Sending Chunks to Main...");
                    try (ZContext contextToMain = new ZContext()) {
                        ZMQ.Socket sendSocketToMain = contextToMain.createSocket(SocketType.PUB);
                        sendSocketToMain.connect("tcp://localhost:7004");
                        if (receivedMessages.isEmpty()) {
                            System.out.println("Process did not receive any messages.");
                            return;
                        } else {
                            int counter = 0;
                            for (Message message : receivedMessages) {
                                if (message != null) {
                                    Thread.sleep(100);
                                    lamportClock++;
                                    message.setNewLamportClock(lamportClock);
                                    byte[] serializedMessage = serializeMessage(message);
                                    
                                    sendSocketToMain.send(serializedMessage, 1);
                                    System.out.println("Sent chunk " + counter + " to Main: (" + message + ")");
                                    counter++;
                                }
                            }
                        }
                        

                    
                        Message allChunksMessage = new Message("END");
                        byte[] allChunksMessageBytes = serializeMessage(allChunksMessage);
                        sendSocketToMain.send(allChunksMessageBytes, 1);
                        System.out.println("Sent allChunksMessage: " + allChunksMessage);
                    }                    
                }

        } catch (Exception ex) {
            System.err.println("Process 4 error: " + ex.getMessage());
        }
    }

    public static Message deserializeMessage(byte[] messageBytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(messageBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Message) objectInputStream.readObject();
    }

    public static byte[] serializeMessage(Message message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }
}
