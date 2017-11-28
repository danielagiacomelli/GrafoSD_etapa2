package Project;

import grafos.*;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Servidor {
    public static Metodos novoMetodo;
    public static GrafoBD.Processor processor;


    private static int port;

    public static void main(String [] args) {
        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("Porta: ");
            port = scan.nextInt();

            novoMetodo = new Metodos();
            processor = new GrafoBD.Processor(novoMetodo);

            novoMetodo.setCaminhoVerticeEAresta(port);

            Runnable simple = new Runnable() {
                public void run() {
                    simple(processor);
                }
            };

            new Thread(simple).start();
        } catch(InputMismatchException st){
            System.out.println("Digite uma porta valida.");
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void simple(GrafoBD.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(port);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the simple server: port " + port);
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}