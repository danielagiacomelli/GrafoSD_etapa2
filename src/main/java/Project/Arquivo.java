package Project;

import grafos.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {
    public static List<Vertice> leArquivoVertice(){
        BufferedReader buff = null;

        List<Vertice> lista = new ArrayList<Vertice>();
        try{

            buff = new BufferedReader(new FileReader("C:/Users/Usuario/Desktop/GrafoSD/src/main/Vertice.txt"));

            String linha = buff.readLine();
            System.out.println(linha);

            while(linha != null){
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
            String pathSaida = "C:/Users/Usuario/Desktop/GrafoSD/src/main/Vertice.txt";
            FileWriter saida;

            saida = new FileWriter(pathSaida, true);

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

            buff = new BufferedReader(new FileReader("C:/Users/Usuario/Desktop/GrafoSD/src/main/Aresta.txt"));

            String linha = buff.readLine();
            System.out.println(linha);


            while(linha != null){
                Aresta novaAresta = new Aresta();
                novaAresta.setVertice1(Integer.parseInt(linha.split(";")[0]));
                novaAresta.setVertice2(Integer.parseInt(linha.split(";")[1]));
                novaAresta.setPeso(Float.parseFloat(linha.split(";")[2]));
                if (Integer.parseInt(linha.split(";")[3]) == 1){
                    novaAresta.setDirecionada(true);
                }else{
                    novaAresta.setDirecionada(false);
                }
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
            String pathSaida = "C:/Users/Usuario/Desktop/GrafoSD/src/main/Aresta.txt";
            FileWriter saida;

            saida = new FileWriter(pathSaida, true);

            for(int i=0; i<listaArestas.size(); i++){
                saida.write(listaArestas.get(i).getVertice1() + ";");
                saida.write(listaArestas.get(i).getVertice2() + ";");
                saida.write(listaArestas.get(i).getPeso() + ";");
                if (listaArestas.get(i).isDirecionada()){
                    saida.write("1");
                }else{
                    saida.write("0");
                }
                saida.write(listaArestas.get(i).getDescricao());


                saida.write("\n");
            }

            saida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
