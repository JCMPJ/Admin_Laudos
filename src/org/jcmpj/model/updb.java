package org.jcmpj.model;

/**
 *
 * @author josec
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class updb {
    static List<Map<String, ?>> procs;
    public static void main(String[] args) {
        // String sql = "SELECT * FROM dataLaudo";
        // ResultSet rs = DBManager.listAll();
        Connection conn = DBManager.getConnection();
        try {
            procs = DBManager.rsToList();
            for (Map proc : procs) {
                String nome = proc.get("numProcesso").toString();
                String id = proc.get("id").toString();
                // nome = nome.replaceAll("\\s+E\\s+OUTRAS", "");
                nome = nome.replaceAll(" - ", "-");
                System.out.println(nome);
                
                String sql = "UPDATE dataLaudo SET numProcesso = ? WHERE id = " + id;
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, nome);
                pstmt.executeUpdate();               
            }
        } catch (SQLException e) {
            System.out.println("Quem é null? " + e.getMessage());
        }
    }
}
