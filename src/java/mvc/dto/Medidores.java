package mvc.dto;

import java.io.Serializable;

public class Medidores implements Serializable{
    public String nome;
    public String tabela;
    public boolean editing = false;
    public boolean selected = false;

    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setTabela(String tabela) {
        this.tabela = tabela;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public String getTabela(){
        return this.tabela;
    }


}
