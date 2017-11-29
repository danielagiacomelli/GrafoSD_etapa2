package Project;

import java.util.ArrayList;

import Project.Metodos;
import grafos.Aresta;
import grafos.Vertice;
import org.apache.thrift.TException;


public class Dijkstra {

    static double min;
    static int next = 0;
    static Metodos handler;
    static Vertice vertice;


    public static void menorDistancia(double[][] matriz, int tamanho, int tag1, int tag2, ArrayList<Vertice> array){

        double distancia[] = new double[tamanho];
        int visitados[] = new int[tamanho];
        int pre[] = new int[tamanho];

        //preenche os vertices que nao tem arestas adjacentes com 999
        for(int i=0; i<tamanho; i++){
            for(int j=0; j<tamanho; j++){
                if(matriz[i][j]==0.0){
                    matriz[i][j]=999;
                }
            }
        }

        //seta a distancia com o peso da aresta
        for(int i=0; i<tamanho; i++){
            distancia[i] = matriz[tag1][i];
        }

        visitados[tag1] = 1;

        for(int i=0; i<tamanho; i++){
            min=999;

            for(int j=0; j<tamanho; j++){

                //a variavel min recebe o menor peso de aresta
                if(min>distancia[j] && visitados[j]!=1){
                    min = distancia[j];
                    visitados[j] = 1;

                }
            }


            for(int k=0; k<tamanho; k++){
                for(int c=0; c<tamanho; c++){
                    if(visitados[c]!=1){
                        if(min+matriz[k][c]<distancia[c]){
                            distancia[c]= min+matriz[k][c];
                            pre[c] = k;
                        }

                }
            }

            }
        }
        int j;
        if(distancia[tag2]==-1 || distancia[tag2]==999){
            System.out.println("\nCaminho: "+array.get(tag2).getId()+" Distância: "+999);
        }
        else if(distancia[tag2]>0 && distancia[tag2-1]<999){
            for(Vertice v : array){
                if(v.getId() == tag2)
                    System.out.println("\nCaminho: de " + tag1 + " até " + v.getId()+" Distância: "+distancia[tag2]);
            }
        }
        j=tag2;
        do{
            j=pre[j];
            if(j!=0){
                System.out.println(" <- "+array.get(j).getId());
            }
        }while(j!=0);

        System.out.println(" <- "+array.get(tag1).getId());

        for(int i=0; i<tamanho; i++){

            distancia[i] = 0;
            visitados[i] = 0;
            pre[i]=0;

        }


    }

}

