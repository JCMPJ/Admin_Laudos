package org.jcmpj.controller;

/**
 *
 * @author jcmpj
 */
/**
 * import org.apache.commons.io.FileUtils; import
 * org.apache.commons.io.FilenameUtils; import com.microsoft.playwright.*;
 *
 */
import com.microsoft.playwright.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jcmpj.resources.Global;
import org.jcmpj.view.Inicio;

public class AcessarWeb implements Runnable {

    private final ArrayList<String> listaDados;
    private boolean ultimaPagina;
    private final String nome;
    private final Inicio ini;

    /**
     *
     * @param nome
     * @param ini
     */
    public AcessarWeb(String nome, Inicio ini) {
        listaDados = new ArrayList<>();
        ultimaPagina = true;
        this.nome = nome;
        this.ini = ini;
    }

    @Override
    public void run() {
        try ( Playwright playwright = Playwright.create()) {
            BrowserType browserType = playwright.firefox();
            // Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext newContext = browser.newContext(new Browser.NewContextOptions().setAcceptDownloads(true));
            // Page page = browser.newPage();
            Page page = newContext.newPage();
            page.navigate("https://pje.trt15.jus.br/primeirograu/login.seam");
            // System.out.println(page.title());

            String username = Global.getUserName();
            String password = Global.getPassword();
            // String username = "45938024900";
            // String password = "Borba1905@";

            page.fill("xpath=//*[@id=\"username\"]", username);
            page.fill("xpath=//*[@id=\"password\"]", password);
            page.locator("xpath=//*[@id=\"btnEntrar\"]").click();
            //page.locator("/html/body/pje-root/mat-sidenav-container/mat-sidenav-content/div/pje-cabecalho/div/mat-toolbar/section[2]/pje-cabecalho-perfil/div/button[1]").click();
            tempo(1);
            /* Botão trocar orgão julgador ou papel */
            /**
             * Alteração em virtude da atualização do sistema PJe para a versão
             * 2.8.4 em 25 de outubro de 2022 *
             * page.locator("xpath=/html/body/pje-root/mat-sidenav-container/mat-sidenav-content/div/pje-cabecalho/div/mat-toolbar/section[2]/pje-cabecalho-perfil/div/button[1]").click();
             */
            page.locator("xpath=/html/body/pje-root/mat-sidenav-container/mat-sidenav-content/pje-cabecalho/div/mat-toolbar/section[2]/pje-cabecalho-perfil/div/button[1]/span[1]/div").click();
            tempo(1);
            /* Botão promover para Perito */
            /**
             * Alteração em virtude da atualização do sistema PJe para a versão
             * 2.8.4 em 25 de outubro de 2022 *
             * page.locator("xpath=//button[contains(text(),
             * 'Perito')]").click();
             */
            page.locator("xpath=//*[@id=\"mat-menu-panel-0\"]/div/div[2]/button").click();
            tempo(1);
            /* Botão Aguardando Laudo */
            /**
             * Alteração em virtude da atualização do sistema PJe para a versão
             * 2.8.4 em 25 de outubro de 2022 *
             * page.locator("xpath=/html/body/pje-root/mat-sidenav-container/mat-sidenav-content/div/div/div/pje-home-perito/div/div/ng-component/div[2]/pje-painel-item[3]/div/mat-card").click();
             */
            page.locator("xpath=/html/body/pje-root/mat-sidenav-container/mat-sidenav-content/div/div/pje-home-perito/div/div/ng-component/div[2]/pje-painel-item[3]/div/mat-card").click();
            tempo(1);
            /**
             * Captura da lista de processos em uma certa página
             */
            int pag = 0;
            while (ultimaPagina) {
                try {
                    /**
                     * Seleciona as céluas que contém os dados de interesse do
                     * projeto
                     */
                    pag++;
                    ini.setStatus("Lendo página: " + pag);
                    if (!page.isVisible("xpath=/html/body/pje-root/mat-sidenav-container/mat-sidenav-content/div/div/pje-home-perito/div/div/ng-component/mat-card/mat-card-content/pje-data-table/div[2]/pje-paginador/div/span[4]/button[3][not (@disabled)]")) {
                        ultimaPagina = false;
                    }
                    /**
                     * Alteração em virtude da atualização do sistema PJe para a
                     * versão 2.8.4 em 25 de outubro de 2022 * Locator rows =
                     * (Locator)
                     * page.locator("//pje-celula-dados-basicos-processo");
                     * //*[@id="cdk-drop-list-0"]/tr/td[2]/pje-celula-dados-basicos-processo/div/div/div[1]
                     * -- reclamante x reclamada sem o número do processo
                     * //*[@id="cdk-drop-list-0"]/tr/td[2]/pje-celula-dados-basicos-processo/div
                     * -- com o número do processo
                     * //*[@id="cdk-drop-list-0"]/tr/td[2]/pje-celula-dados-basicos-processo/div/div/a
                     * -- apenas o número do processdo
                     *
                     */
                    Locator rows = (Locator) page.locator("//*[@id=\"cdk-drop-list-0\"]/tr/td[2]/pje-celula-dados-basicos-processo/div");
                    /**
                     * Constroi um ArrayList(listaDados) que será usado para
                     * gerar um arquivo .txt e posterior atualização do banco de
                     * dados
                     */
                    int count = rows.count();
                    for (int i = 0; i < count; i++) {
                        listaDados.add(rows.nth(i).textContent());
                        // System.out.println("Linha: " + i + " :: " + rows.nth(i).textContent() + "\n");
                    }
                } catch (ExceptionInInitializerError ex) {
                    System.err.println(ex.getMessage());
                }
                tempo(1);
                /* Botão próxima página */
                /**
                 * Alteração em virtude da atualização do sistema PJe para a
                 * versão 2.8.4 em 25 de outubro de 2022 *
                 * page.locator("//button[@aria-label=\"Próximo\"][not
                 * (@disabled)]").click();
                 */
                if (ultimaPagina) {
                    page.locator("xpath=/html/body/pje-root/mat-sidenav-container/mat-sidenav-content/div/div/pje-home-perito/div/div/ng-component/mat-card/mat-card-content/pje-data-table/div[2]/pje-paginador/div/span[4]/button[3][not (@disabled)]").click();
                }
                tempo(1);
            }
            write(listaDados);
            //tempo(5);
        }
    }

    /**
     * Tempo t em segundos
     *
     * @param t
     */
    private static void tempo(int t) {
        int temp = t * 1000;
        try {
            Thread.sleep(temp);
        } catch (InterruptedException ex) {
            Logger.getLogger(Playwright.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gerar o arquivo texto que será usado posteriormente pela classe
     * AtualizarBancoDados
     *
     * @param linha
     */
    private static void write(ArrayList<String> linha) {

        File dataFile = new File(Global.getPathDataFile());

        try {
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }

            //construtor que recebe também como argumento se o conteúdo será acrescentado
            //ao invés de ser substituído (append)
            FileWriter fw = new FileWriter(dataFile, false);
            //construtor recebe como argumento o objeto do tipo FileWriter
            BufferedWriter bw = new BufferedWriter(fw);
            for (String l : linha) {
                //escreve o conteúdo no arquivo
                bw.write(l);
                //quebra de linha
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException ex) {
            String message = ex.getMessage();
            System.err.println(message);
        }
    }
}
