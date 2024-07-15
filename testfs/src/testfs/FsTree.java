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
    
    public static void cancellaFile(Nodo fileDaCancellare) {
        try {
            SOP("entrato in funz canc");
            File file = new File(fileDaCancellare.nome);
            if (file.exists()) {
                SOP("vuoi cancellare file: " + file.getAbsolutePath() + "?");
                if (file.delete()) {
                    SOP("File cancellato con successo: " + file.getAbsolutePath());
                } else {
                    SOP("Impossibile cancellare il file: " + file.getAbsolutePath());
                }
            } else {
                SOP("Il file non esiste: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Errore durante la cancellazione del file.");
            e.printStackTrace();
        }
    }

    public static void creaFileDiTesto(Nodo cartella) {
        try {
            SOP("Inserisci nome file.txt da creare: ");
            String nomeFile = leggiString();
            File file = new File(cartella.nome + File.separator + nomeFile);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Contenuto del file di testo.");  // Aggiungi il tuo contenuto qui
            bufferedWriter.close();

            SOP("File di testo creato con successo: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Errore durante la creazione del file di testo.");
            e.printStackTrace();
        }
    }

    private static void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
