
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.Locale;

public class contrato {
    private int id;
    private String contratante;
    private String contratado;
    private String numeroContrato;
    private String descricao;
    private double valor;
    private LocalDate celebracao;
    private LocalDate inicio;
    private LocalDate termino;
    private String cpf;
    
    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final Locale localBrasil = new Locale("pt", "BR");
    
    public contrato(int id, String contratante, String contratado, String numeroContrato, 
                   String descricao, double valor, LocalDate celebracao, 
                   LocalDate inicio, LocalDate termino, String cpf) {
        this.id = id;
        this.contratante = contratante;
        this.contratado = contratado;
        this.numeroContrato = numeroContrato;
        this.descricao = descricao;
        this.valor = valor;
        this.celebracao = celebracao;
        this.inicio = inicio;
        this.termino = termino;
        this.cpf = cpf;
    }
    
    // Valida se as datas estão consistentes
    public boolean validarDatas() {
        if (celebracao.isAfter(termino)) {
            return false;
        }
        if (inicio.isAfter(termino)) {
            return false;
        }
        if (termino.isBefore(celebracao) || termino.isBefore(inicio)) {
            return false;
        }
        return true;
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }
    
    public String getContratante() {
        return contratante;
    }
    
    public void setContratante(String contratante) {
        this.contratante = contratante;
    }
    
    public String getContratado() {
        return contratado;
    }
    
    public void setContratado(String contratado) {
        this.contratado = contratado;
    }
    
    public String getNumeroContrato() {
        return numeroContrato;
    }
    
    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public double getValor() {
        return valor;
    }
    
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public String getValorFormatado() {
        return NumberFormat.getCurrencyInstance(localBrasil).format(valor);
    }
    
    public LocalDate getCelebracao() {
        return celebracao;
    }
    
    public void setCelebracao(LocalDate celebracao) {
        this.celebracao = celebracao;
    }
    
    public String getCelebracaoFormatada() {
        return celebracao.format(formatoData);
    }
    
    public LocalDate getInicio() {
        return inicio;
    }
    
    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }
    
    public String getInicioFormatado() {
        return inicio.format(formatoData);
    }
    
    public LocalDate getTermino() {
        return termino;
    }
    
    public void setTermino(LocalDate termino) {
        this.termino = termino;
    }
    
    public String getTerminoFormatado() {
        return termino.format(formatoData);
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    @Override
    public String toString() {
        return id + "|" + contratante + "|" + contratado + "|" + cpf + "|" + numeroContrato + "|" + 
               getValorFormatado() + "|" + getCelebracaoFormatada() + "|" + 
               getInicioFormatado() + "|" + getTerminoFormatado() + "|" + descricao;
    }
}