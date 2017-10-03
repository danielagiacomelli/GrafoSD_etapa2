package Client;

import Project.Metodos;
import grafos.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import Project.Arquivo;

import java.awt.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {


        try {
            TTransport transport;

            transport = new TSocket("localhost", 9090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            GrafoBD.Client client = new GrafoBD.Client(protocol);

            //perform(client);

            int op = 1;
            Arquivo arq = new Arquivo();
            Metodos metodo = new Metodos();

            // **** Menu com as opções disponíveis ***
            while (op == 1) {
                System.out.println("\n1 - Inserir vértice" + "\n2 - Inserir aresta" + "\n3 - Remover vertice"
                        + "\n4 - Remover aresta" + "\n5 - Atualizar aresta" + "\n6 - Atualizar vertice" + "\n7 - Listar arestas"
                        + "\n8 - Listar vértices" + "\n9 - Listar vertices vizinhos" + "\n10 - Listar menor caminho");

                Scanner s = new Scanner(System.in);

                int valor_lido = s.nextInt();
                s.nextLine();

                switch (valor_lido) {
                    case 1:
                        System.out.println("Id Vértice (nome): ");
                        int id = s.nextInt();

                        System.out.println("Cor: ");
                        int cor = s.nextInt();

                        System.out.println("Descrição: ");
                        s.nextLine();
                        String descricao = s.nextLine();

                        System.out.println("Peso: ");
                        Double peso = s.nextDouble();

                        Vertice vert = new Vertice(id, cor, descricao, peso);
                        client.insereVertice(vert);
                        break;

                    case 2:
                        System.out.println("Vértice 1: ");
                        int vet1 = s.nextInt();

                        System.out.println("Vértice 2: ");
                        int vet2 = s.nextInt();

                        System.out.println("Descrição: ");
                        s.nextLine();
                        String descricao1 = s.nextLine();

                        System.out.println("Peso: ");
                        Double peso1 = s.nextDouble();

                        System.out.println("Bidirecionada? ");
                        boolean direcionada = s.hasNextBoolean();

                        Aresta a = new Aresta(vet1, vet2, peso1, direcionada, descricao1);
                        client.insereAresta(a);
                        break;
                    case 3:
                        System.out.println("Digite as informações do vertice que deseja remover: ");
                        System.out.println("Id Vértice (nome): ");
                        int id2 = s.nextInt();

                        System.out.println("Cor: ");
                        int cor2 = s.nextInt();

                        System.out.println("Descrição: ");
                        s.nextLine();
                        String descricao2 = s.nextLine();

                        System.out.println("Peso: ");
                        Double peso2 = s.nextDouble();

                        Vertice vert1 = new Vertice(id2, cor2, descricao2, peso2);
                        client.removeVertice(vert1);
                        break;
                    case 4:
                        System.out.println("Digite as informações da aresta que deseja remover: ");

                        System.out.println("Vértice 1: ");
                        int vet1a = s.nextInt();

                        System.out.println("Vértice 2: ");
                        int vet2a = s.nextInt();

                        System.out.println("Descrição: ");
                        s.nextLine();
                        String descricao3 = s.nextLine();

                        System.out.println("Peso: ");
                        Double peso3 = s.nextDouble();

                        System.out.println("Bidirecionada? ");
                        boolean direcionada3 = s.hasNextBoolean();

                        Aresta a1 = new Aresta(vet1a, vet2a, peso3, direcionada3, descricao3);
                        client.removeAresta(a1);
                        break;
                    case 5:
                        System.out.println("Vértice 1: ");
                        int vet1b = s.nextInt();

                        System.out.println("Vértice 2: ");
                        int vet2b = s.nextInt();

                        System.out.println("Descrição: ");
                        s.nextLine();
                        String descricao4 = s.nextLine();

                        System.out.println("Peso: ");
                        Double peso4 = s.nextDouble();

                        System.out.println("Bidirecionada? ");
                        boolean direcionada4 = s.hasNextBoolean();

                        Aresta a2 = new Aresta(vet1b, vet2b, peso4, direcionada4, descricao4);
                        client.updateAresta(a2);
                        break;
                    case 6:
                        System.out.println("Id Vértice (nome): ");
                        int id5 = s.nextInt();

                        System.out.println("Cor: ");
                        int cor5 = s.nextInt();

                        System.out.println("Descrição: ");
                        s.nextLine();
                        String descricao5 = s.nextLine();

                        System.out.println("Peso: ");
                        Double peso5 = s.nextDouble();

                        Vertice vert2 = new Vertice(id5, cor5, descricao5, peso5);
                        client.updateVertice(vert2);
                        break;
                    case 7:
                        metodo.salvarOperacoes();
                        System.out.println(client.getArestas());
                        break;
                    case 8:
                        metodo.salvarOperacoes();
                        System.out.println(client.getVertices());
                        break;
                    case 9:
                        System.out.println("Digite o nome(id) do vertice: ");
                        int verticeId = s.nextInt();
                        metodo.salvarOperacoes();
                        System.out.println(client.getVizinhos(verticeId));
                        break;
                    //case 10:

                    //	break;
                    default:
                        System.out.println("Operação inválida, tente novamente");
                        break;
                }
                System.out.println("Deseja realizar outra operação? Digite 1 para sim e 2 para não: ");
                Scanner s1 = new Scanner(System.in);
                op = s1.nextInt();
                if (op==2){
                    metodo.salvarOperacoes();
                }
            }
            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }
}
