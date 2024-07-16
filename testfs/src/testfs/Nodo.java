package testfs;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.LinkedList;

public class Nodo {

    String nome;
    Color fileColor;
    LinkedList<Nodo> figli;
    LinkedList<String> files;
    int x, y;

    public Nodo(String path) {
        fileColor = Color.blue;
        x = 0;
        y = 0;
        String[] p = path.split("\\\\");
        nome = p[p.length - 1];
        figli = new LinkedList<>();
        files = new LinkedList<>();
        try {
            File f = new File(path);
            for(File sub : f.listFiles()) {
                String name = sub.getName();
                if(sub.isDirectory()) {
                    Nodo nn = new Nodo(path+"\\"+name);
                    figli.add(nn);
                }else
                    files.add(name);
            }
        }catch(Exception e) {}
    }
    
    public void disegna(Graphics g, int x, int y) {
        this.x=x;
        this.y=y;
        g.drawString(nome, x, y);
    }
}
