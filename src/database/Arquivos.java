package database;

import java.io.*;

public class Arquivos {
    String nome;
    // Objetos para entrada de dados
    InputStream fluxoEntrada = null;
    InputStreamReader geradorFluxoEntrada = null;
    BufferedReader bufferEntrada = null;

    // Objetos para saída de dados
    OutputStream fluxoSaida = null;
    OutputStreamWriter geradorFluxoSaida = null;
    BufferedWriter bufferSaida = null;

    public Arquivos(String nome){
        this.nome = nome;
    }

    public void escrever(int x, int y){
        try{
            //Inicializando os objetos
            fluxoSaida = new FileOutputStream(nome,true);
            geradorFluxoSaida = new OutputStreamWriter(fluxoSaida);
            bufferSaida = new BufferedWriter(geradorFluxoSaida);
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }finally {
            try{
                //Escrita no arquivo
                bufferSaida.write(x + "!" + y);
                bufferSaida.newLine();
            } catch (IOException ex) {
                System.err.println(ex);
            }finally {
                try{
                    assert bufferSaida != null;
                    bufferSaida.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }
    }

    public void ler(){
        try{
            //Inicializando os objetos
            fluxoEntrada = new FileInputStream(nome);
            geradorFluxoEntrada = new InputStreamReader(fluxoEntrada);
            bufferEntrada = new BufferedReader(geradorFluxoEntrada);
        } catch (FileNotFoundException ex) {
            System.err.println(String.valueOf(ex));
        }finally {
            try{
                String leitura = bufferEntrada.readLine();
                while(leitura != null) {
                    //Leitura no arquivo
                    System.out.println("Posição X e Y: " + leitura);
                    leitura = bufferEntrada.readLine();
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }finally {
                try{
                    assert bufferEntrada != null;
                    bufferEntrada.close();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }
    }
}
