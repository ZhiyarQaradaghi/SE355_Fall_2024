package src;

import org.zeromq.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a file path:");
        String filePath = sc.nextLine();

        try (ZContext context = new ZContext(); FileInputStream fileInput = new FileInputStream(new File(filePath))) {
            ZMQ.Socket sendSocket = context.createSocket(SocketType.PUSH);
            sendSocket.bind("tcp://*:5555");
            ZMQ.Socket receiveSocket = context.createSocket(SocketType.PULL);
            receiveSocket.bind("tcp://*:5556");
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

    public static void mergeSort(List<Message> messages) {
        if (messages.size() <= 1) return; 
    
        int mid = messages.size() / 2;
        List<Message> left = new ArrayList<>(messages.subList(0, mid));
        List<Message> right = new ArrayList<>(messages.subList(mid, messages.size()));
    
        mergeSort(left);
        mergeSort(right);
    
        merge(messages, left, right);
    }
    
    private static void merge(List<Message> messages, List<Message> left, List<Message> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getOldLamportClock() <= right.get(j).getOldLamportClock()) {
                messages.set(k++, left.get(i++));
            } else {
                messages.set(k++, right.get(j++));
            }
        }
    
        while (i < left.size()) messages.set(k++, left.get(i++));
        while (j < right.size()) messages.set(k++, right.get(j++));
    }
}
