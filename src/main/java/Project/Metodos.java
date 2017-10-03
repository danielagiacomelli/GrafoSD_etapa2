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
            }
        } finally {
            lock.unlock();
            return true;
        }
    }


    public boolean salvarOperacoes() {
        arq.salvaArestas((ArrayList<Aresta>) listaArestas);
        arq.salvaVertices((ArrayList<Vertice>) listaVertices);
        return true;
    }

    @Override
    public boolean removeVertice(Vertice v) throws TException {
        listaVertices = arq.leArquivoVertice();

        try {
            lock.lock();
            listaVertices.remove(v);
        } finally {
            lock.unlock();
            return true;
        }

    }

    @Override
    public boolean removeAresta(Aresta a) throws TException {
        listaArestas = arq.leArquivoAresta();

        try {
            lock.lock();
            listaArestas.remove(a);
        } finally {
            lock.unlock();
            return true;
        }
    }

    @Override
    public boolean updateAresta(Aresta a) throws TException {
        listaArestas = arq.leArquivoAresta();

        try {
            lock.lock();
            if (existeAresta(a)) {
                for (int i = 0; i < listaArestas.size(); i++) {
                    if (listaArestas.get(i).getVertice1() == a.getVertice1() && listaArestas.get(i).getVertice2() == a.getVertice2()) {
                        listaArestas.get(i).setPeso(a.getPeso());
                        listaArestas.get(i).setDescricao(a.getDescricao());
                        listaArestas.get(i).setDirecionada(a.isDirecionada());
                    }
                }

            }
        } finally {
            lock.unlock();
            return true;
        }
    }

    @Override
    public boolean updateVertice(Vertice v) throws TException {
        listaVertices = arq.leArquivoVertice();

        try {
            lock.lock();
            if (existeVertice(v)) {
                for (int i = 0; i < listaVertices.size(); i++) {
                    if (listaVertices.get(i).getId() == v.getId()) {
                        listaVertices.get(i).setCor(v.getCor());
                        listaVertices.get(i).setDesc(v.getDesc());
                        listaVertices.get(i).setPeso(v.getPeso());
                    }
                }
            }
        } finally {
            lock.unlock();
            return true;
        }

    }


    @Override
    public List<Aresta> getArestasdoVertice(Vertice v) throws TException {
        listaArestas = arq.leArquivoAresta();

        try {
            lock.lock();
            for (int i = 0; i < listaArestas.size(); i++) {
                if (listaArestas.get(i).getVertice1() == v.getId() || listaArestas.get(i).getVertice2() == v.getId()) {
                    listaArestas.add(listaArestas.get(i));
                }
            }
        } finally {
            lock.unlock();
            return listaArestas;
        }
    }


    public Vertice buscaVertice(int v) {
        listaVertices = arq.leArquivoVertice();

        for (int i = 0; i < listaVertices.size(); i++) {
            if (listaVertices.get(i).getId() == v)
                return listaVertices.get(i);
        }
        return null;
    }

    @Override
    public List<Vertice> getVertices() throws TException {
        listaVertices = arq.leArquivoVertice();
        return listaVertices;

    }


    @Override
    public List<Aresta> getArestas() throws TException {
        listaArestas = arq.leArquivoAresta();
        return listaArestas;
    }


    @Override
    public List<Vertice> getVizinhos(int vetId) throws TException {
        List<Vertice> vizinhos = new ArrayList<Vertice>();
        listaArestas = arq.leArquivoAresta();

        try {
            lock.lock();
            for (int i = 0; i < listaArestas.size(); i++) {
                if (listaArestas.get(i).getVertice1() == vetId) {
                    vizinhos.add(buscaVertice(listaArestas.get(i).getVertice2()));
                } else if (listaArestas.get(i).getVertice2() == vetId) {
                    vizinhos.add(buscaVertice(listaArestas.get(i).getVertice1()));
                }
            }

        } finally {
            lock.unlock();
            return vizinhos;
        }
    }


    @Override
    public List<Vertice> getMenorCaminho(Vertice vet1, Vertice vet2) throws TException {
        return null;
    }

}