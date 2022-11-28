package org.jcmpj.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jcmpj.criptografia.EncriptaDecriptaAES;
import org.jcmpj.model.DBManager;

/**
 *
 * @author jcmpj
 */
public class Global {
    
    private boolean flagSalvar;
    private static String pathAux;
    private static String pathDocumemtos;
    private static String pathDataFile;
    private static String pathLaudoDbFile;
    private static String pathUserDataFile;
    // private static final String pathToUserdb = System.getProperty("user.dir") + File.separator + "userdata.db";
    // private static final String url = "jdbc:sqlite:" + pathToUserdb;
    // private static Connection conn;

    public boolean isFlagSalvar() {
        return flagSalvar;
    }

    public void setFlagSalvar(boolean flagSalvar) {
        this.flagSalvar = flagSalvar;
    }
    
    public static void Startmain() {

        StringBuilder sb = new StringBuilder();

        sb.append(System.getProperty("user.home"));
        sb.append(System.getProperty("file.separator"));
        if (File.separator.equalsIgnoreCase("/")) {
            sb.append("Documentos");
        } else {
            sb.append("Documents");
        }
        sb.append(System.getProperty("file.separator"));
        pathDocumemtos = new String(sb); // pathDocumemtos + "Laudos" + File.separator + nomeDoArquivoLaudo
        sb.append("Laudos-TRT");
        sb.append(File.separator);
        pathAux = new String(sb);
        File strPath = new File(pathAux);
        String pathToDataFile = pathAux + "dataFile.txt";
        pathDataFile = pathToDataFile;
        String pathToLaudo = pathAux + "laudo.db";
        pathLaudoDbFile = pathToLaudo;
        /**
         * Gerengiamento da senha que será armazenada em um banco de dados *
         */
        String pathToUserData = pathAux + "userdata.db";
        pathUserDataFile = pathToUserData;
        File userdata = new File(pathToUserData);
        if (!userdata.exists()) {
            createNewUserDatabase();
        }
        /**
         * Gerengiamento dos arquivos e diretórios onde serão armazenados os
         * dados gerados pelo sistema*
         */
        File dataFile = new File(pathToDataFile);
        File laudoFile = new File(pathToLaudo);

        if (!Files.exists(Paths.get(pathAux))) {
            strPath.mkdirs();
        }

        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Global.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (!laudoFile.exists()) {
            //laudoFile.mkdirs();
            if (DBManager.createNewDatabase()) {
                DBManager.createNewTable();
            }
        }
    }

    public static String getPathAux() {
        Startmain();
        return pathAux;
    }

    public static String getPathDoc() {
        Startmain();
        return pathDocumemtos;
    }

    public static String getPathDataFile() {
        Startmain();
        return pathDataFile;
    }

    public static String getPathLaudoDbFile() {
        Startmain();
        // System.out.println(pathLaudoDbFile);
        return pathLaudoDbFile;
    }

    public static String getUserName() {
        LoadResources loadResources = new LoadResources();
        Properties prop = loadResources.loadPropertiesFile("per.properties");
        //prop.forEach((k, v) -> System.out.println(k + ":" + v));
        //System.out.println("per.username:: " + prop.getProperty("per.properties"));
        return prop.getProperty("per.username");
    }

    /**
     * Gerengiamento da senha que será armazenada em um banco de dados *
     * @return 
     */
    //Cria conexões com o banco
    public static Connection getConnection() {
        String url = "jdbc:sqlite:" + pathUserDataFile;
        try {

            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean createNewUserDatabase() {
        String url = "jdbc:sqlite:" + pathUserDataFile;
        try ( Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                createNewUserTable();
                // System.out.println("The driver name is " + meta.getDriverName());
                // System.out.println("A new database has been created.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public static void createNewUserTable() {
        // String url = "jdbc:sqlite:" + pathUserDataFile;
        // SQL statement for creating a new table
        String sql = "CREATE TABLE \"usuarios\" (\n";
        sql += "\"id\"	INTEGER PRIMARY KEY AUTOINCREMENT,\n";
        sql += "\"password\"	BLOB NOT NULL\n";
        sql += "  );";
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "INSERT INTO usuarios (password) VALUES(?)";
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBytes(1, EncriptaDecriptaAES.encrypt("Borba1905@"));
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(Global.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getPassword() {
        // String url = "jdbc:sqlite:" + pathUserDataFile;
        String sql = "SELECT password FROM usuarios WHERE id = 1";
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String passwd = EncriptaDecriptaAES.decrypt(rs.getBytes("password"));
            conn.close();
            return passwd;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(Global.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean setPassword(String senha) {
        // String url = "jdbc:sqlite:" + pathUserDataFile;
        String sql = "UPDATE usuarios SET password = ? WHERE id = 1";
        try {

            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            try {
                pstmt.setBytes(1, EncriptaDecriptaAES.encrypt(senha));
            } catch (Exception ex) {
                Logger.getLogger(Global.class.getName()).log(Level.SEVERE, null, ex);
            }
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String getName() {
        LoadResources loadResources = new LoadResources();
        Properties prop = loadResources.loadPropertiesFile("per.properties");
        //prop.forEach((k, v) -> System.out.println(k + ":" + v));
        //System.out.println("per.password:: " + prop.getProperty("per.password"));
        prop.setProperty("per.serial", "951/2022");
        return prop.getProperty("per.name");
    }

    public static String getSerial() {
        LoadResources loadResources = new LoadResources();
        Properties prop = loadResources.loadPropertiesFile("per.properties");
        //prop.forEach((k, v) -> System.out.println(k + ":" + v));
        //System.out.println("per.password:: " + prop.getProperty("per.password"));
        return prop.getProperty("per.serial");
    }

    public static long getExpires() {
        LocalDate agora = LocalDate.now();
        LocalDate fi = LocalDate.of(2022, 12, 31);

        long dia = DAYS.between(agora, fi);

        return dia;
    }
}
