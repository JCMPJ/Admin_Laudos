package org.jcmpj.controller;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.jcmpj.model.DBManager;
import org.jcmpj.resources.Global;

/**
 *
 * @author jcmpj
 */
public class Docx {

    public static String id;
    static ResultSet rs;
    static String[] pathToDoc;
    static Document document;

    public static void gerarDocumento() throws SQLException {
        Map<String, String> dl = PreencherForm.getLaudoIdNumProcesso();
        id = dl.get("id");

        rs = DBManager.listById(id);
        /**
         * ##########
         */

        String pathToModel = System.getProperty("user.dir") + File.separator + "modelo_laudo.docx";

        document = new Document(pathToModel);

        String np = rs.getString("numProcesso").replaceAll("-", " - ");
        document.replace("#processo", np, false, true);
        document.replace("#reclamante", rs.getString("nomeReclamante"), false, true);
        document.replace("#reclamada", rs.getString("nomeReclamada"), false, true);
        document.replace("#dataEmissao", dataEmissao(), false, true);

        if (rs.getString("periodoReclamado") != null) {
            document.replace("#periodoReclamado", rs.getString("periodoReclamado"), false, true);
        } else {
            document.replace("#periodoReclamado", "", false, true);
        }

        if (rs.getString("funcaoExercida") != null) {
            document.replace("<funcaoExercida/>", rs.getString("funcaoExercida"), false, true);
        } else {
            document.replace("<funcaoExercida/>", "", false, true);
        }
        if (rs.getString("dataVistoria") != null) {
            document.replace("#dataVistoria", builFormDate(rs.getString("dataVistoria")), false, true);
        } else {
            document.replace("#dataVistoria", "", false, true);
        }
        if (rs.getString("horaVistoria") != null) {
            document.replace("#horaVistoria", rs.getString("horaVistoria"), false, true);
        } else {
            document.replace("#horaVistoria", "", false, true);
        }
        if (rs.getString("localVistoria") != null) {
            document.replace("#localVistoria", rs.getString("localVistoria"), false, true);
        } else {
            document.replace("#localVistoria", "", false, true);
        }
        if (rs.getString("enderecoVistoria") != null) {
            document.replace("#enderecoVistoria", rs.getString("enderecoVistoria"), false, true);
        } else {
            document.replace("#enderecoVistoria", "", false, true);
        }
        if (rs.getString("acompanhantesReclamante") != null) {
            document.replace("<acompanhantesReclamante/>", rs.getString("acompanhantesReclamante"), false, true);
        } else {
            document.replace("<acompanhantesReclamante/>", "", false, true);
        }
        if (rs.getString("acompanhantesReclamada") != null) {
            document.replace("<acompanhantesReclamada/>", rs.getString("acompanhantesReclamada"), false, true);
        } else {
            document.replace("<acompanhantesReclamada/>", "", false, true);
        }
        if (rs.getString("atividadesReclamante") != null) {
            document.replace("<atividadesReclamante/>", rs.getString("atividadesReclamante"), false, true);
        } else {
            document.replace("<atividadesReclamante/>", "", false, true);
        }
        if (rs.getString("atividadesReclamada") != null) {
            document.replace("<atividadesReclamada/>", rs.getString("atividadesReclamada"), false, true);
        } else {
            document.replace("<atividadesReclamada/>", "", false, true);
        }
        if (rs.getString("descricaoLocalTrabalho") != null) {
            document.replace("<descricaoLocalTrabalho/>", rs.getString("descricaoLocalTrabalho"), false, true);
        } else {
            document.replace("<descricaoLocalTrabalho/>", "", false, true);
        }
        if (rs.getString("quesitosReclamante") != null) {
            document.replace("<quesitosReclamante/>", rs.getString("quesitosReclamante"), false, true);
        } else {
            document.replace("<quesitosReclamante/>", "", false, true);
        }
        if (rs.getString("quesitosReclamada") != null) {
            document.replace("<quesitosReclamada/>", rs.getString("quesitosReclamada"), false, true);
        } else {
            document.replace("<quesitosReclamada/>", "", false, true);
        }
        /**
         * ##########
         */
        String arquivoLaudo = pathToNewDoc();

        File dataFile = new File(arquivoLaudo); // C:\\User\\user\\Documents\\Laudos\\2022\\05\\31\\

        if (!Files.exists(Paths.get(arquivoLaudo))) {
            dataFile.mkdirs();
        }

        String[] aux1 = rs.getString("numProcesso").split(" ");
        /**
         * É prcisso retirar os espaçoes em branco pois causam erro na linha:
         * Process exec = Runtime.getRuntime().exec(comando);
         */
        // String strOut = arquivoLaudo + rs.getString("numProcesso") + ".docx";
        String strOut = arquivoLaudo + aux1[0] + aux1[1] + aux1[2] + ".docx";
        document.saveToFile(strOut, FileFormat.Docx_2013);

        try {
            String comando;
            if (File.separator.equals("/")) {
                comando = "libreoffice --writer " + strOut;
            } else {
                //comando = "\"c:\\Program Files (x86)\\microsoft Office\\office14\\winword.exe\" /t " + strOut;
                comando = locateWinword() + " /t " + strOut;
            }
            Process exec = Runtime.getRuntime().exec(comando);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        rs.close();
    }

    private static String dataEmissao() {
        String[] mes = {
            "",
            "janeiro",
            "fevereiro",
            "março",
            "abril",
            "maio",
            "junho",
            "julho",
            "agosto",
            "setembro",
            "outubro",
            "novembro",
            "dezembro"
        };
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        String[] dma = data.split("/");
        String dataEmissao = "Campinas, " + dma[0] + " de " + mes[Integer.parseInt(dma[1])] + " de " + dma[2];
        // System.out.println(dataEmissao);
        return dataEmissao;
    }

    /**
     * Transforma o formato da data de yyy-mm-dd para dd/mm/aa
     *
     * @param data
     * @return
     */
    private static String builFormDate(String data) {
        String dia, mes, ano;
        String[] amd = data.split("-");
        dia = amd[2];
        mes = amd[1];
        ano = amd[0];
        String dma = dia + "/" + mes + "/" + ano + " ";

        return dma;
    }

    private static String[] buildPathToDoc(String data) {
        if (data == null || data.isEmpty()) {
            data = "1970-01-01";
        }
        pathToDoc = new String[3];
        String[] amd = data.split("-");
        pathToDoc[0] = amd[0];
        pathToDoc[1] = amd[1];
        pathToDoc[2] = amd[2];

        return pathToDoc;
    }

    private static String pathToNewDoc() throws SQLException {
        // if (pathToDoc == null) {
            pathToDoc = buildPathToDoc(rs.getString("dataVistoria"));
        // }
        StringBuilder path = new StringBuilder();
        path.append(Global.getPathDoc()); // C:\\User\\user\\Documents ou /home/user/Documentos
        path.append(File.separator);
        path.append("Laudos");          // C:\\User\\user\\Documents\\Laudos
        path.append(File.separator);
        path.append(pathToDoc[0]);      // C:\\User\\user\\Documents\\Laudos\\2022
        path.append(File.separator);
        path.append(pathToDoc[1]);      // C:\\User\\user\\Documents\\Laudos\\2022\\05
        path.append(File.separator);
        path.append(pathToDoc[2]);      // C:\\User\\user\\Documents\\Laudos\\2022\\05\\31        
        path.append(File.separator);
        // para verificar se o diretório pathDoc existe
        String pathToLaudo = new String(path); // C:\\User\\user\\Documents\\Laudos\\2022\\05\\31\\
        /**
         * String arquivoLaudo; if (File.separator.equalsIgnoreCase("/")){
         * arquivoLaudo = pathDoc + numProcesso + ".odt"; } else { arquivoLaudo
         * = pathDoc + numProcesso + ".docx"; }
         */
        return pathToLaudo;
    }

    private static String locateWinword() {
        String command = null;
        String[] path = {
            "C:\\Program Files (x86)\\Microsoft Office\\Office12\\winword.exe",
            "C:\\Program Files\\Microsoft Office\\Office12\\winword.exe",
            "C:\\Program Files (x86)\\Microsoft Office\\Office14\\winword.exe",
            "C:\\Program Files\\Microsoft Office\\Office14\\winword.exe",
            "C:\\Program Files (x86)\\Microsoft Office\\Office15\\winword.exe",
            "C:\\Program Files\\Microsoft Office\\Office15\\winword.exe",
            "C:\\Program Files (x86)\\Microsoft Office\\Office16\\winword.exe",
            "C:\\Program Files\\Microsoft Office\\Office16\\winword.exe",
            "C:\\Program Files (x86)\\Microsoft Office\\root\\Office16\\winword.exe",
            "C:\\Program Files\\Microsoft Office\\root\\Office16\\winword.exe"
        };

        for (int i = 0; i < path.length; i++) {
            File winwordexc = new File(path[i]);
            if (winwordexc.exists()) {
                command = path[i];
                break;
            }
            // System.out.println(path[i]);
            // System.out.println(command);
        }
        return command;
    }
}
