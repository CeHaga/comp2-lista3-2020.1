package covid.data;

import java.time.LocalDate;

/**
 * Classe para representar um caso datado de uma cidade
 * @author Carlos Bravo - cehaga@dcc.ufrj.br
 */
public class Caso {
    private LocalDate data;
    private String estado;
    private String cidade;
    private boolean isEstado;
    private int confirmado;
    private int mortes;
    private int posicao;
    private boolean ultimo;
    private Integer populacao2019;
    private Integer populacao2020;
    private double confirmado100k;
    private double taxaMorte;
    private double taxaCrescimento;

    /**
     * Construtor de um caso
     * @param caso Uma linha do arquivo disponibilizado pela data.brasil.io
     */
    public Caso(String caso){
        String[] dados = caso.split(",");
        data = LocalDate.parse(dados[0]);
        estado = dados[1];
        cidade = dados[2];
        isEstado = dados[3].equals("state");
        confirmado = Integer.parseInt(dados[4]);
        mortes = Integer.parseInt(dados[5]);
        posicao = Integer.parseInt(dados[6]);
        ultimo = dados[7].equals("True");
        populacao2019 = dados[8].isEmpty() ? -1 : Integer.parseInt(dados[8]);
        populacao2020 = dados[9].isEmpty() ? -1 : Integer.parseInt(dados[9]);
        if(!dados[11].isEmpty()){
            confirmado100k = Double.parseDouble(dados[11]);
        }else{
            double pop = populacao2020 <= 0 ? populacao2019 <= 0 ? -confirmado : populacao2019 : populacao2020;
            confirmado100k = (confirmado / pop) * 10e5;
        }
        taxaMorte = Double.parseDouble(dados[12]);
    }

    public boolean isEstado(){
        return this.isEstado;
    }

    public String getEstado() {
        return estado;
    }

    public String getCidade() {
        return cidade;
    }

    public LocalDate getData() {
        return data;
    }

    public int getConfirmado() {
        return confirmado;
    }

    public int getMortes() {
        return mortes;
    }

    public int getPosicao() {
        return posicao;
    }

    public boolean isUltimo() {
        return ultimo;
    }

    public Integer getPopulacao2019() {
        return populacao2019;
    }

    public Integer getPopulacao2020() {
        return populacao2020;
    }

    public double getConfirmado100k() {
        return confirmado100k;
    }

    public double getTaxaMorte() {
        return taxaMorte;
    }

    public double getTaxaCrescimento() {
        return taxaCrescimento;
    }

    public void setTaxaCrescimento(double taxaCrescimento) {
        this.taxaCrescimento = taxaCrescimento;
    }

    public boolean isEquals(Object o){
        if(o == null) return false;
        if(o == this) return true;
        if(!(o instanceof Caso)) return false;
        Caso c = (Caso)o;
        if(c.estado.equals(estado) && c.cidade.equals(cidade) && c.posicao == posicao) return true;
        return false;
    }

    /**
     * Compara se os casos se referem à mesma localização
     * @param o Objeto a ser comparado
     * @return true se os casos são do mesmo lugar, false caso contrário
     */
    public boolean isPlaceEquals(Object o){
        if(o == null) return false;
        if(o == this) return true;
        if(!(o instanceof Caso)) return false;
        Caso c = (Caso)o;
        if(c.estado.equals(estado) && c.cidade.equals(cidade)) return true;
        return false;
    }

    public String toString(){
        String nome = isEstado ? estado : cidade;
        return "Caso num "+posicao+" referente a "+nome+(isEstado ? "" : ("-" + estado));
    }
}
