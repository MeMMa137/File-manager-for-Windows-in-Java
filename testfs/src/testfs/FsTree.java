
package testfs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.util.Scanner;
import javax.swing.JPanel;

class FsTree extends JPanel implements MouseListener {

    static String leggiString() {
        return (new Scanner(System.in)).nextLine();
    }

    static void SOP(String s) {
        System.out.println(s);
    }

    private Nodo root;
    private int y = 0;
    public final int S = 20;
    private Color fileColor = Color.pink;
    private Color folderColor = Color.blue;
    Nodo nTrovato;

    FsTree(String path) {
        addMouseListener(this);
        root = new Nodo(path);
        boolean parolaTrovata = inputStringa();
        coloreTrovato(nTrovato, Color.green, parolaTrovata);
        int nFile = contaFile(root);
        int nCartelle = contaCartelle(root);
        SOP("numero di file nelll'albero: " + nFile + "\n" + "numero di cartelle nelll'albero: " + nCartelle);

    }

    private void disegna(Graphics g, Nodo nodo, int x) {
        g.setColor(nodo.fileColor); // Usa il colore del nodo
        nodo.disegna(g, x, y);
        y += S;
        for (Nodo figlio : nodo.figli) {
            disegna(g, figlio, x + S);
        }
        g.setColor(fileColor);
        for (String s : nodo.files) {
            g.drawString(s, x + S, y);
            y += S;
        }
    }
