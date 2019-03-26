
package grafopalavras;

import java.util.ArrayList;
import java.util.List;


public class Vertice {
    
    //ATRIBUTOS    
    private final String palavra;
    private final List<Vertice> listaAdj = new ArrayList<>();
    
    //Atributos utilizados nas buscas do grafo
    private Cor cor;
    private int descoberta;
    private int termino;
    private Vertice pred;    
    private int distancia;
    private int low;
    private int cc;
    private boolean impresso; //Controle para evitar prints repetidos
    
    //CONSTRUTOR
    public Vertice(String palavra) {
        this.palavra = palavra;
    }
    
    //METODOS    
    public String getPalavra() {
        return palavra;
    }

    public List<Vertice> getListaAdj() {
        return listaAdj;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }    
    
    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public int getDescoberta() {
        return descoberta;
    }

    public void setDescoberta(int descoberta) {
        this.descoberta = descoberta;
    }

    public int getTermino() {
        return termino;
    }

    public void setTermino(int termino) {
        this.termino = termino;
    }

    public Vertice getPred() {
        return pred;
    }

    public void setPred(Vertice pred) {
        this.pred = pred;
    }

    public boolean isImpresso() {
        return impresso;
    }

    public void setImpresso(boolean impresso) {
        this.impresso = impresso;
    }
           
    
    public void imprimirListaAdj(){
        for(Vertice vertice : listaAdj){
            System.out.println(vertice.getPalavra() + " ");
        }
    }
    
    public void adicionarAresta(Vertice vertice){
        listaAdj.add(vertice);
    }    
    
    public boolean satisfazRequisito(Vertice verticeAnalisado){
        //Verifica se duas palavras possuem exatamento 1 letra de diferença
        int diferentes=0;
        for(int i=0; i<verticeAnalisado.getPalavra().length(); i++){
            if(verticeAnalisado.getPalavra().charAt(i)!=this.palavra.charAt(i)){
                diferentes++;
            }
        }        
        return (diferentes==1);
    }
    
    @Override
    public String toString(){
        return this.palavra;
    }
    
    
}
