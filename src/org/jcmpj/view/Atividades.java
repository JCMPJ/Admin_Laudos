package org.jcmpj.view;

import java.awt.event.KeyEvent;
import java.util.Map;
import javax.swing.JOptionPane;
import org.jcmpj.controller.PreencherForm;
import org.jcmpj.model.DBManager;
import org.jcmpj.resources.WindowManager;

/**
 *
 * @author jcmpj
 */
public class Atividades extends javax.swing.JInternalFrame {
    private WindowManager windowManager;
    private String id;
    private static Atividades atividades;

    public static Atividades getInstance() {
        if (atividades == null) {
            atividades = new Atividades();
        } else {
            atividades.loadInitialData();
        }
        return atividades;
    }

    /**
     * Creates new form Atividades
     */
    public Atividades() {
        initComponents();
        loadInitialData();
    }

    private void loadInitialData() {
        Map<String, String> dl = PreencherForm.getLaudoIdNumProcesso();

        id = dl.get("id");
        String numProcessos = dl.get("numProcesso");
        String nomeReclamante = dl.get("nomeReclamante");
        String nomeReclamada = dl.get("nomeReclamada");

        // txtReclamada.setText("Reclamada: " + nomeReclamada);
        // txtReclamante.setText("Reclamante: " + nomeReclamante);
        
        String titReclamante;
        titReclamante = "Atividades reclamante: " + nomeReclamante;
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(
                new java.awt.Color(0, 153, 153)), titReclamante,
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Liberation Sans", 3, 15),
                new java.awt.Color(0, 153, 153)));
        
        String titReclamada;
        titReclamada = "Atividades reclamada: " + nomeReclamada;
        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(
			new java.awt.Color(0, 153, 153)), titReclamada,
			javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
			javax.swing.border.TitledBorder.DEFAULT_POSITION,
			new java.awt.Font("Liberation Sans", 3, 15),
			new java.awt.Color(0, 153, 153)));
        
        Map<String, String> txt = DBManager.getActivities(id);
        txtAreaReclamante.setText(txt.get("atividadesReclamante"));
        txtAreaReclamada.setText(txt.get("atividadesReclamada"));
    }
    
    private void addLineBreak(int c, String anterior, String txtArea) {
        String txt = anterior.trim() + "\n";
        //txt = txt.trim();
        if (c == KeyEvent.VK_ENTER) {
            if (txtArea.equalsIgnoreCase("txtAreaReclamante")) {
                txtAreaReclamante.setText(txt);
            } else if (txtArea.equalsIgnoreCase("txtAreaReclamada")) {
                txtAreaReclamada.setText(txt);
            }
        }
    }
    
    private void saveText() {
        boolean up;
        up = DBManager.updateActivities(txtAreaReclamante.getText(), 
                txtAreaReclamada.getText(),
                txtAreaDescLocalTrabalho.getText(),
                Integer.parseInt(id));
        if (up) {
            JOptionPane.showMessageDialog(null, "Textos Atualizados\nno banco de dados", "Update", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Erro\nbanco de dados", "Error", JOptionPane.ERROR_MESSAGE);
        }        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaReclamante = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaReclamada = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        btnSalvarAtividades = new javax.swing.JButton();
        btnProximoAcompanhantes = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaDescLocalTrabalho = new javax.swing.JTextArea();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Descrição das atividades e do local de trabalho");
        setPreferredSize(new java.awt.Dimension(1024, 603));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)), "Atividades da Reclamante", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Liberation Sans", 3, 18), new java.awt.Color(0, 153, 153))); // NOI18N

        txtAreaReclamante.setLineWrap(true);
        txtAreaReclamante.setWrapStyleWord(true);
        txtAreaReclamante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAreaReclamanteKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(txtAreaReclamante);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)), "Atividades da Reclamada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Liberation Sans", 3, 18), new java.awt.Color(0, 153, 153))); // NOI18N
        jScrollPane2.setForeground(new java.awt.Color(0, 153, 153));

        txtAreaReclamada.setLineWrap(true);
        txtAreaReclamada.setWrapStyleWord(true);
        txtAreaReclamada.setMargin(new java.awt.Insets(0, 6, 0, 6));
        txtAreaReclamada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAreaReclamadaKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(txtAreaReclamada);

        jPanel1.setPreferredSize(new java.awt.Dimension(242, 32));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalvarAtividades.setText("Salvar Alterações");
        btnSalvarAtividades.setMargin(new java.awt.Insets(2, 50, 2, 50));
        btnSalvarAtividades.setPreferredSize(new java.awt.Dimension(50, 22));
        btnSalvarAtividades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarAtividadesActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalvarAtividades, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 2, 240, 40));

        btnProximoAcompanhantes.setText("Próximo");
        btnProximoAcompanhantes.setPreferredSize(new java.awt.Dimension(30, 22));
        btnProximoAcompanhantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProximoAcompanhantesActionPerformed(evt);
            }
        });
        jPanel1.add(btnProximoAcompanhantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(895, 2, 90, 40));

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)), "Descrição do local de trabalho", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Liberation Sans", 3, 18), new java.awt.Color(0, 153, 153))); // NOI18N

        txtAreaDescLocalTrabalho.setColumns(20);
        txtAreaDescLocalTrabalho.setRows(5);
        jScrollPane3.setViewportView(txtAreaDescLocalTrabalho);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarAtividadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarAtividadesActionPerformed
        saveText();
    }//GEN-LAST:event_btnSalvarAtividadesActionPerformed

    private void txtAreaReclamanteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaReclamanteKeyReleased
        addLineBreak(evt.getKeyCode(), txtAreaReclamante.getText(), "txtAreaReclamante");
    }//GEN-LAST:event_txtAreaReclamanteKeyReleased

    private void txtAreaReclamadaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaReclamadaKeyReleased
        addLineBreak(evt.getKeyCode(), txtAreaReclamada.getText(), "txtAreaReclamada");
    }//GEN-LAST:event_txtAreaReclamadaKeyReleased

    private void btnProximoAcompanhantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoAcompanhantesActionPerformed
        if (windowManager == null) {
            windowManager = new WindowManager(getDesktopPane());
        }
        windowManager.openWindow(Acompanhantes.getInstance());
    }//GEN-LAST:event_btnProximoAcompanhantesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProximoAcompanhantes;
    private javax.swing.JButton btnSalvarAtividades;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea txtAreaDescLocalTrabalho;
    public javax.swing.JTextArea txtAreaReclamada;
    public javax.swing.JTextArea txtAreaReclamante;
    // End of variables declaration//GEN-END:variables
}
