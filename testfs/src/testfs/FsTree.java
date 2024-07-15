package testfs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

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

    boolean inputStringa() {
        System.out.println("Inserisci parola da cercare: ");
        String parola = leggiString();
        boolean parTrovata = stringTrova(root, parola);
        System.out.println("Parola trovata: " + parTrovata);
        return parTrovata;
    }

    boolean stringTrova(Nodo n, String target) {
        if (n == null) {
            return false;
        }

        if (n.nome.equals(target)) {
            nTrovato = n;
            return true;
        }

        for (Nodo figlio : n.figli) {
            if (stringTrova(figlio, target)) {
                return true;
            }
        }

        for (String s : n.files) {
            if (s.equals(target)) {
                nTrovato = n;
                return true;
            }
        }

        return false;
    }

    void coloreTrovato(Nodo n, Color colore, boolean trovato) {
        if (trovato) {
            coloraPercorso(root, n, colore);

        } else {
            SOP("non colorato");
            setBackground(new Color(255, 80, 220)); //prof rosa su rosa non si vede nullaaaa, quindi ho cambiato tonalità
            showMessageDialog("File non trovato");
        }
    }

    void coloraPercorso(Nodo current, Nodo target, Color colore) {
        if (current != null) {
            if (current == target) {
                current.fileColor = colore;
            } else {
                current.fileColor = colore;
                for (Nodo figlio : current.figli) {
                    coloraPercorso(figlio, target, colore);
                }
            }
        }
    }

    int contaFile(Nodo nodo) {
        int count = nodo.files.size();

        for (Nodo figlio : nodo.figli) {
            count += contaFile(figlio);
        }

        return count;
    }

    int contaCartelle(Nodo nodo) {
        int count = 1; // Conta la cartella corrente

        for (Nodo figlio : nodo.figli) {
            count += contaCartelle(figlio);
        }

        return count;
    }

    private Nodo cartellaClick(Nodo nodo, int mouseX, int mouseY) {
        if (mouseX >= nodo.x && mouseX <= nodo.x + S && mouseY >= nodo.y - S && mouseY <= nodo.y) {
            SOP(" TROVATO x: " + mouseX + "  y: " + mouseY);
            return nodo;
        }

        for (Nodo figlio : nodo.figli) {
            Nodo risultato = cartellaClick(figlio, mouseX, mouseY);
            if (risultato != null) {
                return risultato;
            }
        }
        return null;
    }
}
