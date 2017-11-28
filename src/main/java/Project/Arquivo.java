package Project;

import grafos.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {
    private static int porta=0;
    public static String caminhoVertice = "";
    public static String caminhoAresta = "";


    public static List<Vertice> leArquivoVertice(){
        BufferedReader buff = null;

        List<Vertice> lista = new ArrayList<Vertice>();
        try{

            buff = new BufferedReader(new FileReader(caminhoVertice));

            String linha = buff.readLine();
            System.out.println(linha);

            while(linha != null && !linha.isEmpty()){
                Vertice novoVertice = new Vertice();
                novoVertice.setId(Integer.parseInt(linha.split(";")[0]));
                novoVertice.setCor(Integer.parseInt(linha.split(";")[1]));
                novoVertice.setDesc(linha.split(";")[2]);
                novoVertice.setPeso(Float.parseFloat(linha.split(";")[3]));
                System.out.println(linha);
                lista.add(novoVertice);

                linha = buff.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void salvaVertices(ArrayList<Vertice> listaVertices){
        try {
            String pathSaida = caminhoVertice;
            FileWriter saida;

            saida = new FileWriter(pathSaida, false);

            for(int i=0; i<listaVertices.size(); i++){
                saida.write(listaVertices.get(i).getId() + ";");
                saida.write(listaVertices.get(i).getCor() + ";");
                saida.write(listaVertices.get(i).getDesc() + ";");
                saida.write(listaVertices.get(i).getPeso() + "");
                saida.write("\n");
            }

            saida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static List<Aresta> leArquivoAresta(){
        BufferedReader buff = null;

        List<Aresta> lista = new ArrayList<Aresta>();
        try{

            buff = new BufferedReader(new FileReader(caminhoAresta));

            String linha = buff.readLine();
            System.out.println(linha);


            while(linha != null && !linha.isEmpty()){
                Aresta novaAresta = new Aresta();
                novaAresta.setVertice1(Integer.parseInt(linha.split(";")[0]));
                novaAresta.setVertice2(Integer.parseInt(linha.split(";")[1]));
                novaAresta.setPeso(Float.parseFloat(linha.split(";")[2]));
                novaAresta.setFlag(Integer.parseInt(linha.split(";")[3]));
                novaAresta.setDescricao(linha.split(";")[4]);

                System.out.println(linha);
                lista.add(novaAresta);

                linha = buff.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void salvaArestas(ArrayList<Aresta> listaArestas){
        try {
            String pathSaida = caminhoAresta;
            FileWriter saida;

            saida = new FileWriter(pathSaida, false);

            for(int i=0; i<listaArestas.size(); i++){
                saida.write(listaArestas.get(i).getVertice1() + ";");
                saida.write(listaArestas.get(i).getVertice2() + ";");
                saida.write(listaArestas.get(i).getPeso() + ";");
                saida.write(listaArestas.get(i).getFlag() + ";");
                saida.write(listaArestas.get(i).getDescricao()+"");
                saida.write("\n");
            }

            saida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
