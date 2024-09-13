
package za.ac.tut.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import za.ac.tut.encryption.MessageEncryptor;
import za.ac.tut.message.Message;

public class SecureMessageFrame implements ActionListener{
     private JFrame gui;
     
     private JMenuBar menuBar;
     private JMenu menu;
     
     private JMenuItem miOpenFile;
     private JMenuItem miSaveFile;
     private JMenuItem encryptedMsg;
     private JMenuItem mClearFile;
     private JMenuItem miexitFile;
    
    private JPanel mainPnl;
    private JPanel pnlPlain , pnlEncrypt ,pnlLabel ,pnlCombine;
    
    private JLabel lblMessage;
    
    private JTextArea txtPlain;
    private JTextArea txtEncrypted;
    
    private JScrollPane scrollPlain;
    private JScrollPane scrollEncrypted;

    public SecureMessageFrame() {
        gui= new JFrame("Secure Messages");
        gui.setSize(50, 100);
        gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gui.setLocationRelativeTo(null);
        
        menuBar = new JMenuBar();
        
        menu= new JMenu("File");
        
        miOpenFile = new JMenuItem("Open File...");
        miOpenFile.addActionListener(this);
        
        encryptedMsg = new JMenuItem("Encrypted message...");
        encryptedMsg.addActionListener(this);
        
        miSaveFile = new JMenuItem("Save encrypted message...");
        miSaveFile.addActionListener(this);
        
        mClearFile= new JMenuItem("Clear");
        mClearFile.addActionListener(this);
        
        miexitFile = new JMenuItem("Exit");
        miexitFile.addActionListener(this);
        
        menu.add(miOpenFile);
        menu.add(encryptedMsg);
        menu.add(miSaveFile);
        menu.addSeparator();
        menu.add(mClearFile);
        menu.add(miexitFile);
        
        menuBar.add(menu);
        
        pnlLabel= new JPanel(new FlowLayout(FlowLayout.CENTER));
        
          lblMessage=new JLabel("Messsage Encryptor");
        lblMessage.setForeground(Color.BLUE);
        lblMessage.setFont(new Font("SERIF" ,Font.ITALIC+Font.BOLD,30));
        lblMessage.setBorder(new BevelBorder(BevelBorder.RAISED));
       
        pnlPlain= new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlPlain.setBorder(new TitledBorder(new LineBorder(Color.BLACK,1), "Plain message"));
        
        pnlEncrypt=new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlEncrypt.setBorder(new TitledBorder(new LineBorder(Color.BLACK,1), "Encrypted message"));
        
         mainPnl=new JPanel(new BorderLayout());
         
         txtPlain=new JTextArea(10, 30);
         txtPlain.setEditable(false);
         
         txtEncrypted =new JTextArea(10, 30);
         txtEncrypted.setEditable(false);
         
         scrollPlain = new JScrollPane(txtPlain,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS , JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
         scrollEncrypted = new JScrollPane(txtEncrypted,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
         
         pnlLabel.add(lblMessage);
         pnlPlain.add(scrollPlain);
         pnlEncrypt.add(scrollEncrypted);
         
         mainPnl.add(pnlLabel ,BorderLayout.NORTH);
         mainPnl.add(pnlPlain ,BorderLayout.WEST);
         mainPnl.add(pnlEncrypt ,BorderLayout.CENTER);
        
        gui.setJMenuBar(menuBar);
        gui.add(mainPnl);
        gui.pack();
        gui.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == miOpenFile) {
            String data = "", record;
            int val;
            File file;
            JFileChooser fc = new JFileChooser();
            BufferedReader br;
            
            val = fc.showOpenDialog(gui);
            
            if(val == JFileChooser.APPROVE_OPTION){
                try {
                     file=fc.getSelectedFile();
                br = new BufferedReader(new FileReader(file));
               while((record = br.readLine()) != null){
                   data = data + record + "\n";
               }
                     br.close();
                     txtPlain.setText(data);
                } catch (IOException io) {
                    Logger.getLogger(SecureMessageFrame.class.getName()).log(Level.SEVERE, null, io);

                }
               
            }
           
        } else if (e.getSource() == encryptedMsg) {
            String plainMsg;
            Message message;
            MessageEncryptor msgEncrypt;
            
            plainMsg = txtPlain.getText();
            message = new Message(plainMsg);
            
            msgEncrypt= new MessageEncryptor();
            
            txtEncrypted.setText(msgEncrypt.toString());


        } else if (e.getSource() == miSaveFile) {
            String encryptedMsg;
            int value;
            File file;
            JFileChooser fc =new JFileChooser();
            BufferedWriter bw;
            
            value= fc.showSaveDialog(gui);
            
            if(value == JFileChooser.APPROVE_OPTION){
                try{
                    file = fc.getSelectedFile();
                    
                    bw = new BufferedWriter(new FileWriter(file));
                    
                    encryptedMsg = txtEncrypted.getText();
                    bw.write(encryptedMsg);
                    
                    bw.newLine();
                    bw.close();
                }catch(IOException io){
                   Logger.getLogger(SecureMessageFrame.class.getName()).log(Level.SEVERE, null, io);
                }

            }
        

        } else if (e.getSource() == mClearFile) {
            txtEncrypted.setText("");
            txtPlain.setText("");

        } else if (e.getSource() == miexitFile) {
            System.exit(0);

        }
    }
    public static void main(String[] args){
        new SecureMessageFrame();
    }
    
    
}


