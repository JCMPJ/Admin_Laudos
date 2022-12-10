/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.jcmpj.resources;

import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import org.jcmpj.view.Acompanhantes;
import org.jcmpj.view.Atividades;
import org.jcmpj.view.Listagem;
import org.jcmpj.view.Quesitos;

/**
 *
 * @author jcmpj
 */
public class WindowManager {

    private static JDesktopPane jDesktopPane;
    private boolean flagGlabalSaveAll = false;
    private ArrayList<JInternalFrame> saveList;

    public WindowManager(JDesktopPane jDesktopPane) {

        WindowManager.jDesktopPane = jDesktopPane;
    }

    public void openWindow(JInternalFrame jInternalFrame) {
        if (jInternalFrame.isVisible()) {
            jInternalFrame.toFront();
            jInternalFrame.requestFocusInWindow();
        } else {
            jDesktopPane.add(jInternalFrame);
            jInternalFrame.setVisible(true);
        }
    }

    public boolean isFlagGlabalSaveAll() {
        return flagGlabalSaveAll;
    }

    public void setFlagGlabalSaveAll(boolean flagGlabalSaveAll) {
        this.flagGlabalSaveAll = flagGlabalSaveAll;
    }

}
