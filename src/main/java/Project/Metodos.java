package Project;


import org.apache.thrift.TException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import grafos.*;

public class Metodos implements GrafoBD.Iface {

    private Lock lock = new ReentrantLock();
    Arquivo arq = new Arquivo();

    List<Vertice> listaVertices = new ArrayList<Vertice>();
    List<Aresta> listaArestas = new ArrayList<Aresta>();

    HashMap<Integer, Integer> mapaVertices = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> mapaArestas = new HashMap<Integer, Integer>();


    public void setCaminhoVerticeEAresta(int port) {
        arq.caminhoVertice = "C:/Users/Usuario/Desktop/GrafoSD/src/main/Vertice" + Integer.toString(port) + ".txt";
        arq.caminhoAresta = "C:/Users/Usuario/Desktop/GrafoSD/src/main/Aresta" + Integer.toString(port) + ".txt";
    }

    private boolean existeVertice(Vertice v) {
        listaVertices = arq.leArquivoVertice();

        for (int i = 0; i < listaVertices.size(); i++) {
            if (listaVertices.get(i).getId() == v.getId())
                return true;
        }
        return false;
    }

    private boolean existeAresta(Aresta a) {
        listaArestas = arq.leArquivoAresta();

        for (int i = 0; i < listaArestas.size(); i++) {
            if (listaArestas.get(i).getVertice1() == a.getVertice1() && listaArestas.get(i).getVertice2() == a.getVertice2())
                return true;
        }
        return false;
    }

    @Override
    public boolean ping() throws TException {
        return false;
    }

    @Override
    public boolean insereVertice(Vertice v) throws TException {
        listaVertices = arq.leArquivoVertice();

        try {
            lock.lock();
            if (!existeVertice(v)) {
                listaVertices.add(v);
                mapaVertices.put(v.id, (v.id % 5) + 9090);

                arq.salvaVertices((ArrayList<Vertice>) listaVertices);
            }
        } finally {
            lock.unlock();
            return true;
        }
    }


    @Override
    public boolean insereAresta(Aresta a) throws TException {
        listaArestas = arq.leArquivoAresta();

        try {
            lock.lock();
            if (!existeAresta(a)) {
                listaArestas.add(a);
                mapaArestas.put(a.vertice1, (a.vertice1 % 5) + 9090);

                arq.salvaArestas((ArrayList<Aresta>) listaArestas);
            }
        } finally {
            lock.unlock();
            return true;
        }
    }

    @Override
    public boolean removeVertice(int vertice) throws TException {
        listaVertices = arq.leArquivoVertice();
        listaArestas = arq.leArquivoAresta();
        try {
            lock.lock();
            for (int i = 0; i < listaVertices.size(); i++) {
                if (listaVertices.get(i).getId() == vertice) {
                    listaVertices.remove(listaVertices.get(i));
                    arq.salvaVertices((ArrayList<Vertice>) listaVertices);
                }
            }
        } finally {
            lock.unlock();
            return true;
        }
    }

    @Override
    public boolean removeAresta(int vertice1, int vertice2) throws TException {
        listaArestas = arq.leArquivoAresta();

        try {
            lock.lock();
            for (int i = 0; i < listaArestas.size(); i++) {
                if (listaArestas.get(i).getVertice1() == vertice1 && listaArestas.get(i).getVertice2() == vertice2 ||
                        listaArestas.get(i).getVertice2() == vertice1 && listaArestas.get(i).getVertice1() == vertice2)
                    listaArestas.remove(listaArestas.get(i));
                arq.salvaArestas((ArrayList<Aresta>) listaArestas);
            }
        } finally {
            lock.unlock();
            return true;
        }
    }

    @Override
    public boolean updateAresta(int vertice1, int vertice2, Aresta aresta) throws TException {
        listaArestas = arq.leArquivoAresta();

        try {
            lock.lock();
            for (int i = 0; i < listaArestas.size(); i++) {
                if (listaArestas.get(i).getVertice1() == vertice1 && listaArestas.get(i).getVertice2() == vertice2 ||
                        listaArestas.get(i).getVertice2() == vertice1 && listaArestas.get(i).getVertice1() == vertice2) {
                    listaArestas.get(i).setPeso(aresta.getPeso());
                    listaArestas.get(i).setDescricao(aresta.getDescricao());
                    listaArestas.get(i).setFlag(aresta.getFlag());
                    arq.salvaArestas((ArrayList<Aresta>) listaArestas);
                }
            }
        } finally {
            lock.unlock();
            return true;
        }
    }

    @Override
    public boolean updateVertice(int vertice, Vertice v) throws TException {
        listaVertices = arq.leArquivoVertice();

        try {
            lock.lock();
            for (int i = 0; i < listaVertices.size(); i++) {
                if (listaVertices.get(i).getId() == vertice) {
                    listaVertices.get(i).setDesc(v.getDesc());
                    listaVertices.get(i).setPeso(v.getPeso());
                    listaVertices.get(i).setCor(v.getCor());
                    arq.salvaVertices((ArrayList<Vertice>) listaVertices);
                }
            }
        } finally {
            lock.unlock();
        }
        return true;
    }

    // Mudar esse aqui
    @Override
    public List<Aresta> getArestasdoVertice(Vertice v) throws TException {
        listaArestas = arq.leArquivoAresta();
        for (int i = 0; i < listaArestas.size(); i++) {
            if (listaArestas.get(i).getVertice1() == v.getId() || listaArestas.get(i).getVertice2() == v.getId()) {
                listaArestas.add(listaArestas.get(i));
            }
        }

        return listaArestas;
    }


    // Mudar esse aqui
    public Vertice buscaVertice(int v) {
        listaVertices = arq.leArquivoVertice();

        for (int i = 0; i < listaVertices.size(); i++) {
            if (listaVertices.get(i).getId() == v)
                return listaVertices.get(i);
        }
        return null;
    }

    //
    @Override
    public List<Vertice> getVertices() throws TException {
        return arq.leArquivoVertice();

    }


    @Override
    public List<Aresta> getArestas() throws TException {
        return arq.leArquivoAresta();
    }


    @Override
    public List<Vertice> getVizinhos(int vetId) throws TException {
        List<Vertice> vizinhos = new ArrayList<Vertice>();
        listaArestas = arq.leArquivoAresta();
        for (int i = 0; i < listaArestas.size(); i++) {
            if (listaArestas.get(i).getVertice1() == vetId) {
                vizinhos.add(buscaVertice(listaArestas.get(i).getVertice2()));
            } else if (listaArestas.get(i).getVertice2() == vetId) {
                vizinhos.add(buscaVertice(listaArestas.get(i).getVertice1()));
            }
        }
        return vizinhos;
    }

    @Override
    public List<Vertice> getMenorCaminho(Vertice vet1, Vertice vet2) throws TException {
        return null;
    }

    @Override
    public int PortaServidor(int vertice) throws TException {
        return vertice % 5;
    }

    public boolean excluiAresta(int verticeA) {
        listaArestas = arq.leArquivoAresta();

        try {
            lock.lock();
            for (int i = 0; i < listaArestas.size(); i++) {
                if (listaArestas.get(i).getVertice1() == verticeA || listaArestas.get(i).getVertice2() == verticeA)
                    listaArestas.remove(listaArestas.get(i));
                arq.salvaArestas((ArrayList<Aresta>) listaArestas);
            }
        } finally {
            lock.unlock();
            return true;
        }
    }
}