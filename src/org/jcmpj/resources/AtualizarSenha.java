/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.jcmpj.resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.jcmpj.view.AlterarSenha;

/**
 *
 * @author josec
 */
public class AtualizarSenha {

    // private static String senhaAntiga;
    private static String novaSenha;
    private static String confirma;
    private static AlterarSenha as;

    public AtualizarSenha() {
    }
    
    public void verificarSenhaAtual(String senhaAntiga) {
        // senhaAntiga = as.getSenhaAntiga();
        String senhaAtual = Global.getPassword();
        if (!senhaAtual.equals(senhaAntiga)) {
            JOptionPane.showMessageDialog(null, "As senhas não conferem!", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void salvarNovaSenha(String novaSenha, String confirma) {
        
        // novaSenha = as.getNovaSenha();
        // confirma = as.getConfirma();
        if (novaSenha.equals(confirma)) {
            savePassword(novaSenha);
        } else {
            JOptionPane.showMessageDialog(null, "As senhas não conferem!", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void savePassword(String novaSenha) {
        List<String> li = new ArrayList<>();
        try {
            InputStream is = getClass().getResourceAsStream("per.properties");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                if (line.indexOf("password") > 0) {
                    line = "per.password=" + novaSenha;
                }
                li.add(line);
            }

            br.close();
            isr.close();
            is.close();

        } catch (IOException e) {
            System.out.println(e);
        }
        writeFile(li);
    }

    public void writeFile(List<String> li) {
        try {
            URI url;
            url = getClass().getResource("per.properties").toURI();
            // System.out.println("URL: " + url);
            File dataFile = new File(url);

            //construtor que recebe também como argumento se o conteúdo será acrescentado
            //ao invés de ser substituído (append)
            FileWriter fw = new FileWriter(dataFile, false);
            //construtor recebe como argumento o objeto do tipo FileWriter
            BufferedWriter bw = new BufferedWriter(fw);

            for (String linha : li) {
                //escreve o conteúdo no arquivo
                bw.write(linha);
                //quebra de linha
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException ex) {
            String message = ex.getMessage();
            System.err.println(message);
        } catch (URISyntaxException ex) {
            System.out.println(ex);
            System.out.println(ex.getMessage());
        }
    }
}
