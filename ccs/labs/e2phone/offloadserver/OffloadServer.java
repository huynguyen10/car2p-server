/* @file     offloadserver.java
 * @author   Quang-Huy Nguyen
 * @desc     Java UDP socket server, handles multiple clients using threads.
 *
 * Copyright (c) 2018, Distributed Embedded Systems (CCS Labs)
 * All rights reserved.
 */

package ccs.labs.e2phone.offloadserver;

import ccs.labs.e2phone.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;

public class OffloadServer {

    public static void main(String[] args) throws Exception {

        DatagramSocket udpSocket = new DatagramSocket(28120);

        try {
            System.out.println("Waiting for incoming connections...");
            while (true) {
                byte[] buf = new byte[8000];
                DatagramPacket rcvPaket = new DatagramPacket(buf, buf.length);
                udpSocket.receive(rcvPaket);
                new ConnectionHandler(udpSocket, rcvPaket).start();
            }
        } finally {
            if (udpSocket != null) {
                udpSocket.close();
            }
        }
    }

    private static class ConnectionHandler extends Thread {
        private DatagramSocket udpSocket;
        private DatagramPacket rcvPacket;

        public ConnectionHandler(DatagramSocket udpSocket, DatagramPacket rcvPacket) {
            this.udpSocket = udpSocket;
            this.rcvPacket = rcvPacket;
        }

        public void run() {
            try {
                System.out.printf("Thread No.: %d\n", Thread.currentThread().getId());

                System.out.printf("RcvMessage length: %d\n", rcvPacket.getLength());

                // Send reply message with fixed length
                byte[] buf = new byte[32];
                new Random().nextBytes(buf);
                DatagramPacket sendPacket = new DatagramPacket(buf, buf.length,
                        rcvPacket.getAddress(), rcvPacket.getPort());
                udpSocket.send(sendPacket);

            } catch (IOException e) {
                System.out.printf("IOException: %s\n", e);
            }
        }
    }
}
