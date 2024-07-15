package testfs;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class TestFS {

    public static void main(String[] args) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
        fc.showSaveDialog(null);
        FsTree fst = new FsTree(fc.getSelectedFile().getPath());
        JFrame f = new JFrame();
        f.setBounds(0,0,500,500);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.add(fst);
        f.setVisible(true);
        
        //fst.inputStringa(); //es a
        
    }
    
}
