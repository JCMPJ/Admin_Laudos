package org.jcmpj.controller;

import javax.swing.JOptionPane;
import org.jcmpj.resources.Global;

/**
 *
 * @author josec
 */
public class AtualizarSenha {

    public AtualizarSenha() {
    }

    public boolean verificarSenhaAtual(String senhaAntiga) {

        String senhaAtual = Global.getPassword();
        // System.out.println("Senha atual: " + senhaAtual);
        if (senhaAtual.equals(senhaAntiga)) {
            return true;
            //JOptionPane.showMessageDialog(null, "As senhas não conferem!", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean salvarNovaSenha(String novaSenha, String confirma) {

        if (!novaSenha.isEmpty() && novaSenha.equals(confirma)) {
            return Global.setPassword(novaSenha);
        } else {
            return false;
            //JOptionPane.showMessageDialog(null, "As novas senhas não conferem\nou\nestão vazias!", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }
}
