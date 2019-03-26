
package grafopalavras;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Principal {    
    
    public static void main(String[] args) {
        
        Scanner leitor = new Scanner(System.in);
        int opcao=1;
        String palavra1, palavra2;
        Vertice vertice1, vertice2;
        
        System.out.println("----------ESPECIFICAÇÕES DO ARQUIVO------------");
        System.out.print("\nDigite o nome do arquivo: ");
        String nomeArquivo = leitor.next(); //Nome ou caminho do arquivo
        
        Grafo grafo = new Grafo();
        
        try{
            BufferedReader conteudo = new BufferedReader(new FileReader(nomeArquivo));
            String palavra = conteudo.readLine();
            while(palavra!=null){
                grafo.inserirVertice(new Vertice(palavra));
                palavra = conteudo.readLine();
            }
        }catch(IOException e) {
        System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
            
        }      
        
        grafo.formarGrafoPalavras();         
        
        while(opcao!=0){
            System.out.println("\n--------------------OPÇÕES---------------------");
            System.out.println("(1) Exibir o grafo formado"
            + "\n(2) Calcular a Distancia entre duas palavras"
            + "\n(3) Exibir o Caminho entre duas palavras"
            + "\n(4) Calcular o numero de Componentes Conexos"
            + "\n(5) Listar Pontes do grafo"
            + "\n(6) Listar Pontos de Articulação do grafo"
            + "\n(0) Sair do programa");
            System.out.println("-----------------------------------------------");            
            System.out.print("Digite a opção escolhida: ");
            opcao = leitor.nextInt();
            
            switch(opcao){
                case 0:
                    System.out.println("\nFim do programa.");
                    break;
                case 1:
                    grafo.imprimeGrafo();
                    break;
                case 2:
                    System.out.print("Digite a palavra referente ao primeiro vertice: ");
                    palavra1 = leitor.next();
                    System.out.print("Digite a palavra referente ao segundo vertice: ");
                    palavra2 = leitor.next();
                    vertice1 = grafo.getVerticePorPalavra(palavra1);
                    vertice2 = grafo.getVerticePorPalavra(palavra2);
                    
                    if( (vertice1==null) || (vertice2==null) ){
                        if(vertice1==null){
                            System.out.println("ERRO: Nenhum vertice encontrado para: " + palavra1);
                        }
                        if(vertice2==null){
                            System.out.println("ERRO: Nenhum vertice encontrado para: " + palavra2);
                        }                        
                        break;
                    }else{
                        int distancia = grafo.distanciaEntreVertices(grafo.getVerticePorPalavra(palavra1),
                                    grafo.getVerticePorPalavra(palavra2));
                        if(distancia==-1){
                            System.out.println("Não existe caminho entre os vertices, distancia infinita.");
                        }else{
                          System.out.println("Distancia entre (" + palavra1 + ") e (" + palavra2 
                            + ") = " +grafo.distanciaEntreVertices(grafo.getVerticePorPalavra(palavra1),
                                    grafo.getVerticePorPalavra(palavra2)));  
                        }                        
                    }
                    break;
                case 3:
                    System.out.print("Digite a palavra referente ao primeiro vertice: ");
                    palavra1 = leitor.next();
                    System.out.print("Digite a palavra referente ao segundo vertice: ");
                    palavra2 = leitor.next();
                    vertice1 = grafo.getVerticePorPalavra(palavra1);
                    vertice2 = grafo.getVerticePorPalavra(palavra2);
                    
                    if( (vertice1==null) || (vertice2==null) ){
                        if(vertice1==null){
                            System.out.println("ERRO: Nenhum vertice encontrado para: " + palavra1);
                        }
                        if(vertice2==null){
                            System.out.println("ERRO: Nenhum vertice encontrado para: " + palavra2);
                        }                        
                        break;
                    }else{
                        grafo.imprimeCaminho(grafo.getVerticePorPalavra(palavra1),
                                grafo.getVerticePorPalavra(palavra2));                        
                    }
                    break;
                case 4:
                    System.out.println("\nNumero de Componentes conexos: " + grafo.numComponentesConexos());
                    break;
                case 5:
                    grafo.dfsPontes();
                    break;
                case 6:
                    grafo.dfsPontosArticulacao();
                    break;
                default:
                    System.out.println("ERRO: Opção inválida.");
                    break;
            }
        }
    }
    
}
