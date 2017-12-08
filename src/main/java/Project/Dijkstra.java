package Project;

import grafos.Vertice;

public class Dijkstra {

    static double min;
    static int next = 0;

    public static void menorDistancia(double[][] matriz, int tamanho, int tag1, int tag2, Vertice[] verticesArray){

        double distancia[] = new double[tamanho];
        int visitados[] = new int[tamanho];
        int pre[] = new int[tamanho];
        int ultimo = tag2;

        // preenche os vertices que nao tem arestas adjacentes com infinito
        for(int i=0; i<tamanho; i++){
            for(int j=0; j<tamanho; j++){
                if(matriz[i][j]==0.0){
                    matriz[i][j]=Integer.MAX_VALUE;
                }
            }
        }

        // seta a distancia com o peso da aresta
        for(int i=0; i<tamanho; i++){
            distancia[i] = matriz[tag1][i];
        }

        visitados[tag1] = 1;

        for(int i=0; i<tamanho; i++) {
            min = Integer.MAX_VALUE;

            for (int j = 0; j < tamanho; j++) {
                // a variavel min recebe o menor peso de aresta
                if (min > distancia[j] && visitados[j] != 1) {
                    min = distancia[j];
                    next = j;
                }
            }

            visitados[next] = 1;
            // verifica se a soma do minimo+proximo é menor que distancia
            // se for, distancia[c] recebe a soma e o pre[c] guarda o vertice(no)
            for (int c = 0; c < tamanho; c++) {
                if (visitados[c] != 1) {
                    if (min + matriz[next][c] < distancia[c]) {
                        distancia[c] = min + matriz[next][c];
                        pre[c] = next;
                    }

                }
            }
        }

        int j;

        if(distancia[tag2]==0 || distancia[tag2]==Integer.MAX_VALUE){
            System.out.println("\nCaminho: "+verticesArray[tag2].getId()+" Distância: "+Integer.MAX_VALUE);
        }
        j=tag2;

        // imprime o caminho de forma inversa
        System.out.println(" <- "+ultimo);

        do{
            j=pre[j];
            if(j!=0){
                System.out.println(" <- "+verticesArray[j].getId());
            }
        }while(j!=0);

        System.out.println(" <- "+verticesArray[tag1].getId());



        for(int i=0; i<tamanho; i++){

            distancia[i] = 0;
            visitados[i] = 0;
            pre[i]=0;

        }
    }
}

