package org.example;

import com.google.protobuf.Empty;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import phonebook.Phonebook.Entry;
import phonebook.PhoneBookServiceGrpc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PhoneBookServer {
    private final int port;
    private final Server server;

    private final Map<String, Entry> phoneBook = new HashMap<>();

    public PhoneBookServer(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
                .addService(new PhoneBookService())
                .build();
    }

    public void start() throws IOException {
        server.start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            PhoneBookServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        PhoneBookServer server = new PhoneBookServer(50051);
        server.start();
        server.server.awaitTermination();
    }

    static class PhoneBookService extends PhoneBookServiceGrpc.PhoneBookServiceImplBase {
        private final Map<String, Entry> phoneBook;

        public PhoneBookService() {
            // Initializing the phone book here
            this.phoneBook = new HashMap<>();
        }

        @Override
        public void addEntry(Entry request, StreamObserver<Empty> responseObserver) {
            phoneBook.put(request.getPhoneNumber(), request);
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        }

        @Override
        public void deleteEntry(Entry request, StreamObserver<Empty> responseObserver) {
            phoneBook.remove(request.getPhoneNumber());
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        }

        @Override
        public void searchEntry(Entry request, StreamObserver<Entry> responseObserver) {
            String phoneNumber = request.getPhoneNumber();
            Entry entry = phoneBook.get(phoneNumber);
            if (entry != null) {
                responseObserver.onNext(entry);
            }
            responseObserver.onCompleted();
        }

        @Override
        public void viewEntry(Entry request, StreamObserver<Entry> responseObserver) {
            String phoneNumber = request.getPhoneNumber();
            Entry entry = phoneBook.get(phoneNumber);
            if (entry != null) {
                responseObserver.onNext(entry);
            }
            responseObserver.onCompleted();
        }
    }
}
