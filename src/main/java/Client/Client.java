package Client;


import Project.Dijkstra;
import grafos.*;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import Project.Arquivo;
import org.apache.thrift.transport.TTransportException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    static TTransport transport;
    static GrafoBD.Client client;

    public static void conecta_servidor(int port) throws TTransportException {
        transport = new TSocket("localhost", port);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        client = new GrafoBD.Client(protocol);
    }

    public static void desconecta_servidor() {
        transport.close();
    }

    public static void main(String[] args) {
        Arquivo arq = new Arquivo();
        int op = 1;

        // **** Menu com as opções disponíveis ***
        try {
            while (op == 1) {
                System.out.println("\n1 - Inserir vértice" + "\n2 - Inserir aresta" + "\n3 - Remover vertice"
                        + "\n4 - Remover aresta" + "\n5 - Atualizar aresta" + "\n6 - Atualizar vertice" + "\n7 - Listar vértices"
                        + "\n8 - Listar arestas" + "\n9 - Menor caminho (Dijkstra)");

                Scanner s = new Scanner(System.in);

                int valor_lido = s.nextInt();
                s.nextLine();

                switch (valor_lido) {
                    case 1:
                        System.out.println("Id Vértice (nome): ");
                        int id = s.nextInt();
                        conecta_servidor((id % 5) + 9090); //Hash para calcular em qual servidor o vertice deve ser adicionado

                        System.out.println("Cor: ");
                        int cor = s.nextInt();

                        System.out.println("Descrição: ");
                        s.nextLine();
                        String descricao = s.nextLine();

                        System.out.println("Peso: ");
                        Double peso = s.nextDouble();

                        Vertice vert = new Vertice(id, cor, descricao, peso);
                        client.insereVertice(vert);
                        desconecta_servidor();
                        break;
                    case 2:
                        System.out.println("Vértice 1: ");
                        int vet1 = s.nextInt();

                        conecta_servidor((vet1 % 5) + 9090);

                        System.out.println("Vértice 2: ");
                        int vet2 = s.nextInt();

                        System.out.println("Descrição: ");
                        s.nextLine();
                        String descricao1 = s.nextLine();

                        System.out.println("Peso: ");
                        Double peso1 = s.nextDouble();

                        System.out.println("Bidirecionada? Digite 0 para não e 1 para sim: ");
                        int direcionada = s.nextInt();

                        Aresta a = new Aresta(vet1, vet2, peso1, direcionada, descricao1);
                        client.insereAresta(a);
                        desconecta_servidor();
                        break;
                    case 3:
                        System.out.println("Digite o nome (id) do vértice que deseja remover: ");
                        int id2 = s.nextInt();

                        conecta_servidor((id2 % 5) + 9090);
                        client.removeVertice(id2);
                        desconecta_servidor();


                        for (int serv1 = 0; serv1 < 5; serv1++) {
                            conecta_servidor(9090 + serv1);
                            client.excluiAresta(id2);
                            desconecta_servidor();
                        }
                        break;

                    case 4:
                        System.out.println("Digite as informações da aresta que deseja remover: ");

                        System.out.println("Vértice 1: ");
                        int vet1a = s.nextInt();
                        conecta_servidor((vet1a % 5) + 9090);

                        System.out.println("Vértice 2: ");
                        int vet2a = s.nextInt();

                        client.removeAresta(vet1a, vet2a);
                        desconecta_servidor();
                        break;
                    case 5:
                        System.out.println("Digite os vertices da aresta que deseja atualizar: ");
                        System.out.println("Vértice 1: ");
                        int vet1b = s.nextInt();
                        conecta_servidor((vet1b % 5) + 9090);

                        System.out.println("Vértice 2: ");
                        int vet2b = s.nextInt();

                        System.out.println("Agora digite as informações atualizadas para esta aresta:");
                        System.out.println("Descrição: ");
                        s.nextLine();
                        String descricao4 = s.nextLine();

                        System.out.println("Peso: ");
                        Double peso4 = s.nextDouble();

                        System.out.println("Bidirecionada? ");
                        int direcionada4 = s.nextInt();

                        Aresta a2 = new Aresta(vet1b, vet2b, peso4, direcionada4, descricao4);
                        client.updateAresta(vet1b, vet2b, a2);
                        desconecta_servidor();
                        break;
                    case 6:
                        System.out.println("Digite o nome(id) do vertice que deseja atualizar:");
                        int id5 = s.nextInt();
                        conecta_servidor((id5 % 5) + 9090);

                        System.out.println("Agora digite as novas informações para este vertice");
                        System.out.println("Cor: ");
                        int cor5 = s.nextInt();

                        System.out.println("Descrição: ");
                        s.nextLine();
                        String descricao5 = s.nextLine();

                        System.out.println("Peso: ");
                        Double peso5 = s.nextDouble();

                        Vertice vert2 = new Vertice(id5, cor5, descricao5, peso5);
                        client.updateVertice(id5, vert2);
                        desconecta_servidor();
                        break;
                    case 7:
                        for (int num = 0; num < 5; num++) {
                            conecta_servidor(9090 + num);
                            System.out.println("Servidor " + 909 + num + ":");
                            System.out.println(client.getVertices());
                            desconecta_servidor();
                        }

                        break;
                    case 8:
                        for (int serv = 0; serv < 5; serv++) {
                            conecta_servidor(9090 + serv);
                            System.out.println("Servidor " + 909 + serv + ":");
                            System.out.println(client.getArestas());
                            desconecta_servidor();
                        }

                        break;
                    case 9:
                        Dijkstra dijkstra = new Dijkstra();

                        List<Aresta> listaArestas = new ArrayList<Aresta>();
                        List<Vertice> listaVertices = new ArrayList<Vertice>();

                        for (int j = 0; j < 5; j++) {
                            conecta_servidor(9090 + j);
                            List<Aresta> listaAux = client.getArestas();
                            for(int w=0; w< listaAux.size(); w++)
                                listaArestas.add(listaAux.get(w));
                            desconecta_servidor();
                        }

                        for (int j = 0; j < 5; j++) {
                            conecta_servidor(9090 + j);
                            List<Vertice> listaVerticeAux = client.getVertices();
                            for (int k = 0; k < listaVerticeAux.size(); k++) {
                                listaVertices.add(listaVerticeAux.get(k));
                            }
                            desconecta_servidor();
                        }

                        int maior = 0;

                        for(int z = 0; z<listaVertices.size(); z++){

                            if(listaVertices.get(z).getId()>maior){
                                maior = listaVertices.get(z).getId();
                            }
                        }
                        double[][] matriz = new double[maior+1][maior+1];

                        for (int i = 0; i < listaArestas.size(); i++) {
                            matriz[listaArestas.get(i).getVertice1()][listaArestas.get(i).getVertice2()] = listaArestas.get(i).getPeso();
                        }

                        System.out.println("Digite o vertice de origem: ");
                        int origem = s.nextInt();

                        System.out.println("Digite o vertice de destino: ");
                        int destino = s.nextInt();

                        dijkstra.menorDistancia(matriz, (maior+1), origem, destino, (ArrayList<Vertice>) listaVertices);


                        break;
                    default:
                        System.out.println("Operação inválida, tente novamente");
                        break;
                }
                System.out.println("Deseja realizar outra operação? Digite 1 para sim e 2 para não: ");
                Scanner s1 = new Scanner(System.in);
                op = s1.nextInt();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }

    }

}


