package src;

import org.zeromq.*;
import java.io.*;
import java.util.*;

public class Process1 {
    public static void main(String[] args) {
        long lamportClock = 0;
        List<Message> savedMessages = new ArrayList<>();

        try (ZContext context = new ZContext()) {
            ZMQ.Socket receiveSocket = context.createSocket(SocketType.PULL);
            receiveSocket.connect("tcp://localhost:5555");

            ZMQ.Socket sendSocket = context.createSocket(SocketType.PUSH);
            sendSocket.connect("tcp://localhost:5557");

            Message receivedMessage = null;
            boolean allChunksReceived = false;
            ArrayList<Message> receivedMessages = new ArrayList<>();
            ArrayList<byte[]> messageBytesList = new ArrayList<>();


            while (!Thread.currentThread().isInterrupted()&&!allChunksReceived) {
                Thread.sleep(1);
                byte[] messageBytes = receiveSocket.recv(0);

                receivedMessage = deserializeMessage(messageBytes);
                String content = new String(receivedMessage.getFileContent());

                long receivedLamportClock = receivedMessage.getNewLamportClock();
                receivedMessage.setOldLamportClock(receivedLamportClock);
                lamportClock = Math.max(lamportClock, receivedLamportClock) + 1;  
                
                receivedMessage.setNewLamportClock(lamportClock);

                messageBytesList.add(messageBytes);

                if ("END".equals(receivedMessage.getMessageType())) {
                    System.out.println("Received end-of-chunks receivedMessage.");
                    allChunksReceived = true;
                } else {
                    if ((receivedMessage.getProcess()).equals("file-p1")) {
                        savedMessages.add(receivedMessage);
                        System.out.println("Process1 saved receivedMessage: " + content);
                    }
                }
            }


            for (byte[] messageBytes : messageBytesList) {
                sendSocket.send(messageBytes, 0);
                String content = new String(deserializeMessage(messageBytes).getFileContent());
                System.out.println("Process1 forwarded receivedMessage: " + content);
            }
            

            for (Message savedMessage : savedMessages) {
                byte[] serializedMessage = serializeMessage(savedMessage);
                sendSocket.send(serializedMessage, 0);
                System.out.println("Process1 sent saved receivedMessage to Process2: " + new String(savedMessage.getFileContent()));
            }
            Message endMessage = new Message("END");
            sendSocket.send(serializeMessage(endMessage), 0);
            System.out.println("Process1 sent 'END' to Process2.");
        } catch (Exception e) {
            System.err.println("Error in Process1: " + e.getMessage());
        }
    }

    public static byte[] serializeMessage(Message message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public static Message deserializeMessage(byte[] messageBytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(messageBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Message) objectInputStream.readObject();
    }
}
