
package grafopalavras;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Grafo {
    
    //ATRIBUTOS
    private final List<Vertice> listaVertices = new ArrayList<>();
    
    private final Stack<Vertice> pilha = new Stack(); //Usado na Busca em Largura
    private int tempo; //Variavel global, Usado na Busca em Profundidade
    private int numCC; //Variavel global, Numero de componentes conexos
    
    //METODOS
    public List<Vertice> getListaVertices() {
        return listaVertices;
    }  
    
    public void inserirVertice(Vertice vertice){
        listaVertices.add(vertice);
    }
    
    public Vertice getVerticePorPalavra(String palavra){
        for(Vertice vertice : listaVertices){
            if(vertice.getPalavra().equals(palavra)){
                return vertice;
            }
        }        
        return null; //Nenhum vertice encontrado
    }
    
    //DISTANCIA ENTRE DOIS VERTICES
    public int distanciaEntreVertices(Vertice vertice1, Vertice vertice2){
        buscaLargura(vertice2);        
        return vertice1.getDistancia();
    }
    
    //CAMINHO ENTRE DOIS VERTICES
    public void imprimeCaminho(Vertice vertice1, Vertice vertice2){        
        buscaLargura(vertice2);
        if(vertice1.getDistancia()==-1){
            System.out.println("Não existe caminho entre os vertices: ( " 
                    + vertice1 + " e " + vertice2 + " )");
            return;
        }
        Vertice u = vertice1;
        System.out.print("Caminho: ");
        System.out.print(u);
        while( (u.getPred()!=null) && (!u.equals(vertice2)) ){
            System.out.print(" --> " + u.getPred());
            u = u.getPred();
        }
        System.out.println();
    }
    
    public void imprimeGrafo(){
        System.out.println("\n---------------GRAFO DE PALAVRAS---------------");
        System.out.println("(Vertice) --> Lista de Adj");
        System.out.println("-----------------------------------------------");
        for (Vertice vertice : listaVertices) {
            System.out.print("(" + vertice + ")");
            for(Vertice adj: vertice.getListaAdj()){
                System.out.print(" --> " + adj.getPalavra());
            }
            System.out.println();
        }
    }
    
    public void formarGrafoPalavras(){
        for (Vertice vertice1 : listaVertices) {
            for (Vertice vertice2 : listaVertices){
                if(vertice1.satisfazRequisito(vertice2)){
                    vertice1.adicionarAresta(vertice2);
                }
            }
        }
    }
    
    
    //--------------------BUSCA EM LARGURA--------------------
    
    private void inicializaBuscaLargura(Vertice s){
        for(Vertice u : listaVertices){
            u.setCor(Cor.BRANCO);
            u.setDistancia(-1); //representando infinito
            u.setPred(null);
        }
        s.setCor(Cor.CINZA);
        s.setDistancia(0);
        s.setPred(null);
    }
    
    public void buscaLargura(Vertice s){
        inicializaBuscaLargura(s);
        pilha.push(s);        
        while(!pilha.empty()){
            Vertice u = pilha.pop();
            for(Vertice v : u.getListaAdj()){
                if(v.getCor().equals(Cor.BRANCO)){
                    v.setCor(Cor.CINZA);
                    v.setDistancia(u.getDistancia() + 1 );
                    v.setPred(u);
                    pilha.push(v);
                }
            }
            u.setCor(Cor.PRETO);
        }
        
    }
    
    
    //--------------------BUSCA EM PROFUNDIDADE--------------------
    
    private void dfsVisit(Vertice u){
        tempo++;
        u.setCor(Cor.CINZA);
        u.setDescoberta(tempo);
        for(Vertice v : u.getListaAdj()){
            if(v.getCor().equals(Cor.BRANCO)){
                v.setPred(u);
                dfsVisit(v);
            }
        }
        u.setCor(Cor.PRETO);
        tempo++;
        u.setTermino(tempo);
    }
    
    public void buscaProfundidade(){
        for(Vertice u : listaVertices){
            u.setCor(Cor.BRANCO);
            u.setPred(null);
        }
        tempo = 0;
        for(Vertice u : listaVertices){
            if(u.getCor().equals(Cor.BRANCO)){
                dfsVisit(u);
            }
        }
    }
    
    
    //--------------------PONTOS DE ARTICULAÇÃO--------------------
    
    private boolean temDoisFilhos(Vertice vertice){
        int filhos=0;
        for(Vertice v : listaVertices){         
            if(( v.getPred()!=null) && (v.getPred().equals(vertice)) ){
                filhos++;
                if(filhos==2){
                    return true;
                }
            }            
        }
        return false;
    }
    
    private void pontoArticulacao(Vertice u){ //Modificação de dfsVisit
        tempo++;
        u.setCor(Cor.CINZA);
        u.setLow(tempo);
        u.setDescoberta(tempo);
        for(Vertice v : u.getListaAdj()){
            if(v.getCor().equals(Cor.BRANCO)){
                v.setPred(u);
                pontoArticulacao(v);
                if(u.getPred()==null){ //Se u é raiz
                    if( (temDoisFilhos(u)) && (!u.isImpresso()) ){
                        System.out.println("Vertice (" + u + ")" + " é um ponto de articulação.");
                        u.setImpresso(true);
                    }
                }else{
                    u.setLow(Integer.min(u.getLow(), v.getLow()));
                    if( (v.getLow()>=u.getDescoberta()) && (!u.isImpresso()) ){
                        System.out.println("Vertice (" + u + ")" + " é um ponto de articulação.");
                        u.setImpresso(true);
                    }
                }
            }else{
                if( (!v.equals(u.getPred())) && (v.getDescoberta()<u.getDescoberta()) ){
                    u.setLow(Integer.min(u.getLow(), v.getDescoberta()));
                }
            }
        }
        u.setCor(Cor.PRETO);
        tempo++;
        u.setTermino(tempo);
    }
    
    public void dfsPontosArticulacao(){
        System.out.println("\n-------------PONTOS DE ARTICULAÇÃO-------------");
        for(Vertice u : listaVertices){
            u.setCor(Cor.BRANCO);
            u.setPred(null);
            u.setImpresso(false);
        }
        tempo = 0;
        for(Vertice u : listaVertices){
            if(u.getCor().equals(Cor.BRANCO)){
                pontoArticulacao(u);
            }
        }
    }
    
    
    //--------------------PONTES--------------------
    
    private void pontes(Vertice u){ //Modificação de dfsVisit
        tempo++;
        u.setCor(Cor.CINZA);
        u.setLow(tempo);
        u.setDescoberta(tempo);
        for(Vertice v : u.getListaAdj()){
            if(v.getCor().equals(Cor.BRANCO)){
                v.setPred(u);
                pontes(v);
                u.setLow(Integer.min(u.getLow(), v.getLow()));
                if(v.getLow()>u.getDescoberta()){
                    System.out.println("Aresta (" + u + " --> " + v + ")" + " é uma ponte.");;
                }
            }else{
                if( (!v.equals(u.getPred())) && (v.getDescoberta()<u.getDescoberta()) ){
                    u.setLow(Integer.min(u.getLow(), v.getDescoberta()));
                }
            }
        }
        u.setCor(Cor.PRETO);
        tempo++;
        u.setTermino(tempo);
    }
    
    public void dfsPontes(){
        System.out.println("\n--------------------PONTES---------------------");
        for(Vertice u : listaVertices){
            u.setCor(Cor.BRANCO);
            u.setPred(null);
        }
        tempo = 0;
        for(Vertice u : listaVertices){
            if(u.getCor().equals(Cor.BRANCO)){
                pontes(u);
            }
        }
    }
    
    
    //--------------------COMPONENTES CONEXOS--------------------
    private void dfsCC(Vertice u){ //Modificação de dfsVisit
        u.setCor(Cor.CINZA);
        u.setCc(numCC);
        for(Vertice v : u.getListaAdj()){
            if(v.getCor().equals(Cor.BRANCO)){
                dfsCC(v);
            }
        }
        u.setCor(Cor.PRETO);
    }
    
    public int numComponentesConexos(){
        for(Vertice v : listaVertices){
            v.setCor(Cor.BRANCO);
        }
        numCC=0;
        for(Vertice v : listaVertices){
            if(v.getCor().equals(Cor.BRANCO)){
                numCC++;
                dfsCC(v);
            }
        }
        return numCC;
    }
    
}
