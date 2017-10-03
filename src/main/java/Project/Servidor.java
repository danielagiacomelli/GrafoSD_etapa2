package Project;

        import grafos.*;
        import org.apache.thrift.server.TServer;
        import org.apache.thrift.server.TSimpleServer;
        import org.apache.thrift.transport.TServerSocket;
        import org.apache.thrift.transport.TServerTransport;

public class Servidor {

    public static Metodos novoMetodo;
    public static GrafoBD.Processor processor;

    public static void main(String [] args) {
        try {
            novoMetodo = new Metodos();
            processor = new GrafoBD.Processor(novoMetodo);

            Runnable simple = new Runnable() {
                public void run() {
                    simple(processor);
                }
            };


            new Thread(simple).start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void simple(GrafoBD.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
