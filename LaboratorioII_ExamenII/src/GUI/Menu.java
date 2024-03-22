package GUI;

import Logic.PSNUser;
import Logic.Trophy;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author josue
 */
public class Menu extends JFrame{
    public static TrophyPanel TP;
    public static UserInfo UsIn;
    public static MainPanel MP;
    
    public Menu(PSNUser PSN){
        setLayout(null);
        setSize(350,340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        MP = new MainPanel(PSN);
        add(MP);
        TP = new TrophyPanel(PSN);
        TP.Reveal(false);
        add(TP);
        UsIn = new UserInfo(PSN);
        UsIn.Reveal(false);
        add(UsIn);
    }
    
    public static ImageIcon getPSLogo(int Width, int Height){
        ImageIcon neoIcon = new ImageIcon(new File("Icons\\PSLogo.png").getAbsolutePath());
        Image scaledCard = neoIcon.getImage().getScaledInstance(Width, Height, Image.SCALE_SMOOTH);
        neoIcon = new ImageIcon(scaledCard);
        return neoIcon;
    }
    
    public static void setLabels(JPanel Panel){
        JLabel PSIcon = new JLabel(getPSLogo(40, 40));
        
        PSIcon.setBounds(25, 5, 40, 40);
        Panel.add(PSIcon);
        
        JLabel PSHeader = new JLabel("Play station trophy");
        
        PSHeader.setBounds(PSIcon.getX() + PSIcon.getWidth(), 5, 245, 40);
        PSHeader.setFont(new Font("Zrnic", 1, 20));
        PSHeader.setHorizontalAlignment(JLabel.CENTER);
        PSHeader.setVerticalAlignment(JLabel.CENTER);
        PSHeader.setForeground(Color.white);
        Panel.add(PSHeader);
    }
    
}

class MainPanel extends JPanel{
    private final PSNUser PSN;
    
    public MainPanel(PSNUser PSN){
       this.PSN = PSN;
       
       setLayout(null);
       setBackground(new Color(26, 64, 159));
       setBounds(0,0,350,305);
       
       setBTNs();
       Menu.setLabels(this);
    }
    
    private void setBTNs(){
        JButton AddUser = new JButton();
        
        AddUser.addActionListener((ActionEvent Ev) ->{
            try {
                String NeoUser = JOptionPane.showInputDialog(this, "Ingrese el nombre de usuario: ");
                if (!NeoUser.isBlank()){
                    if (PSN.AddUser(NeoUser)){
                        JOptionPane.showMessageDialog(this, "¡Se ha agregado el usuario exitosamente!", "Agregar usuario", JOptionPane.INFORMATION_MESSAGE);
                    } else JOptionPane.showMessageDialog(this, "¡Ha ocurrido un problema intentando agregar el usuario!", "Agregar usuario", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception Ex){}
        });
        AddUser.setBounds(25, 50, 300,50);
        AddUser.setText("Agregar usuario");
        AddUser.setFocusable(false);
        
        add(AddUser);
        
        JButton RemoveUser = new JButton();
        
        RemoveUser.addActionListener((ActionEvent Ev) ->{
            try {
                String NeoUser = JOptionPane.showInputDialog(this, "Ingrese el nombre de usuario: ");
                if (!NeoUser.isBlank()){
                    if (PSN.RemoveUser(NeoUser)){
                        JOptionPane.showMessageDialog(this, "¡Se ha eliminado el usuario exitosamente!", "Agregar usuario", JOptionPane.INFORMATION_MESSAGE);
                    } else JOptionPane.showMessageDialog(this, "¡Ha ocurrido un problema intentando eliminar el usuario!", "Agregar usuario", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception Ex){}
        });
        RemoveUser.setBounds(25, AddUser.getY() + AddUser.getHeight() + 10, 300,50);
        RemoveUser.setText("Desactivar usuario");
        RemoveUser.setFocusable(false);
        
        add(RemoveUser);
        
        JButton AddTrophyBTN = new JButton();
        
        AddTrophyBTN.addActionListener((ActionEvent Ev) ->{
            Menu.TP.Reveal(true);
            Menu.UsIn.Reveal(false);
            Menu.MP.setVisible(false);
        });
        AddTrophyBTN.setBounds(25, RemoveUser.getY() + RemoveUser.getHeight() + 10, 300,50);
        AddTrophyBTN.setText("Agregar trofeo a usuario");
        AddTrophyBTN.setFocusable(false);
        
        add(AddTrophyBTN);
        
        JButton UserInfo = new JButton();
        
        UserInfo.addActionListener((ActionEvent Ev) ->{
            Menu.TP.Reveal(false);
            Menu.UsIn.Reveal(true);
            Menu.MP.setVisible(false);
        });
        UserInfo.setBounds(25, AddTrophyBTN.getY() + AddTrophyBTN.getHeight() + 10, 300,50);
        UserInfo.setText("Revisar informacion de usuario");
        UserInfo.setFocusable(false);
        
        add(UserInfo);
    }
    
}
class TrophyPanel extends JPanel{
    private final PSNUser PSN;
    
    public TrophyPanel(PSNUser PSN){
        this.PSN = PSN;
        
        setLayout(null);
        setBounds(0,0,350,305);
        setBackground(new Color(26, 64, 159));
        
        Game = new JTextField();
        TrophyType = new JComboBox();
        TrophyName = new JTextField();
        
        Menu.setLabels(this);
        
        setBTNs();
        setTXTArea();
        setTropyBox();
    }
    
    private void setBTNs(){
        JButton Regresar = new JButton();
        
        Regresar.addActionListener((ActionEvent Ev)->{
            Menu.TP.Reveal(false);
            Menu.MP.setVisible(true);
            Menu.UsIn.Reveal(false);
        });
        Regresar.setBounds(10, getHeight() - 55, getWidth()/3, 40);
        Regresar.setFocusable(false);
        Regresar.setText("Regresar");
        
        add(Regresar);
        
        JButton Registrar = new JButton();
        
        Registrar.addActionListener((ActionEvent Ev)->{
            String GameTrophy = this.Game.getText();
            String TrophyNameSave = this.TrophyName.getText();
            if (!GameTrophy.isBlank() && !TrophyNameSave.isBlank()){
                try {
                    String Username = JOptionPane.showInputDialog(this, "Ingrese el usuario al que desea agreagar este usuario: ");
                    if (!Username.isBlank()){
                        if (PSN.AddTrophyTo(Username, GameTrophy, TrophyNameSave, 
                            switch (TrophyType.getSelectedIndex()){
                                case 0 -> Trophy.Platino;
                                case 1 -> Trophy.Oro;
                                case 2 -> Trophy.Plata;
                                default -> Trophy.Bronce;
                            })){
                            JOptionPane.showMessageDialog(this, "Se ha agregado el trofeo exitosamente!");
                            this.Game.setText("");
                            this.TrophyName.setText("");
                            this.TrophyType.setSelectedIndex(0);
                        } else JOptionPane.showMessageDialog(this, "No se ha podido agregar el trofeo");
                    }
                } catch (Exception Ex){}
            }
        });
        Registrar.setBounds(getWidth() - getWidth()/3 - 30, Regresar.getY(), getWidth()/3, 40);
        Registrar.setFocusable(false);
        Registrar.setText("Agregar");
        
        add(Registrar);
    }
    
    private void setTXTArea(){
        Menu.setLabels(this);

        Game.setBounds(10, 65, getWidth() - 40, 40);
        JLabel GameTXT = new JLabel("Nombre del juego: ");
        GameTXT.setForeground(Color.white);
        
        GameTXT.setBounds(10, Game.getY() - 30, Game.getWidth(), Game.getHeight());
        add(GameTXT);
        add(Game);
        
        TrophyName.setBounds(Game.getX(), Game.getY() + Game.getHeight() + 40, Game.getWidth(), Game.getHeight());
        JLabel TrophyTXT = new JLabel("Nombre del trofeo: ");
        TrophyTXT.setForeground(Color.white);
        
        TrophyTXT.setBounds(TrophyName.getX(), TrophyName.getY() - 30, TrophyName.getWidth(), TrophyName.getHeight());
        add(TrophyTXT);
        add(TrophyName);
    }
    
    private void setTropyBox(){
        TrophyType.setBounds(getWidth()/2, TrophyName.getY() + TrophyName.getHeight() + 20, getWidth()/2 - 20, TrophyName.getHeight());
        TrophyType.setModel(new DefaultComboBoxModel(new String[] {"Platino", "Oro", "Plata", "Bronce"}));
        JLabel TypeTXT = new JLabel("Tipo del trofeo: ");
        TypeTXT.setForeground(Color.white);
        
        TypeTXT.setBounds(10, TrophyType.getY(), TrophyType.getWidth(), TrophyType.getHeight());
        TypeTXT.setHorizontalAlignment(JLabel.CENTER);
        add(TypeTXT);
        add(TrophyType);
    }
    
    public void Reveal(boolean Reveal){
        setVisible(Reveal);
        if (Reveal){
            this.Game.setText("");
            this.TrophyName.setText("");
            this.TrophyType.setSelectedIndex(0);            
        }
    }
    
    //-- SWING ELEMENT --
    private final JTextField Game;
    private final JComboBox TrophyType;
    private final JTextField TrophyName;
    //-- SWING ELEMENT --
}
class UserInfo extends JPanel{
    private final PSNUser PSN;
    
    public UserInfo(PSNUser PSN){
        this.PSN = PSN;
        Desc = new JTextArea();
        
        setLayout(null);
        setBounds(0,0,350,305);
        setBackground(new Color(26, 64, 159));
        Menu.setLabels(this);
        
        setBTNS();
        setDescBox();
    }
    
    private void setBTNS(){
        JButton Regresar = new JButton();
        
        Regresar.addActionListener((ActionEvent Ev)->{
            Menu.TP.Reveal(false);
            Menu.UsIn.Reveal(false);
            Menu.MP.setVisible(true);
        });
        Regresar.setBounds(10, getHeight() - 45, getWidth()-35, 35);
        Regresar.setFocusable(false);
        Regresar.setText("Regresar");
        
        add(Regresar);
    }
    
    private void setDescBox(){
        Desc = new JTextArea();
        
        Desc.setEditable(false);
        Desc.setBounds(10, 50, getWidth() - 35, getHeight() - 105);
        JScrollPane Skroll = new JScrollPane(Desc);
        Skroll.setBounds(Desc.getBounds());
        add(Skroll);
        
        UserInfo = new JLabel();

        UserInfo.setBounds(Desc.getX(), Desc.getY() + Desc.getHeight(), Desc.getWidth(), 40);
        add(UserInfo);
    }
    
    public void Reveal(boolean Reveal){
        setVisible(Reveal);
        if (Reveal){
            try{
                String Username = JOptionPane.showInputDialog(this, "Ingrese el nombre del usuario que desea buscar: ");
                if (!Username.isBlank()){
                    String Info = PSN.PlayerInfo(Username);
                    Desc.setText(Info);
                    if (!Info.isEmpty()) {
                        UserInfo.setText(Username + " cuenta con " + PSN.getTrophyCant() + " trofeo(s) - Pts: " + PSN.getUserPoints());
                    } else JOptionPane.showMessageDialog(this, "El usuario que busca no cuenta con trofeos!");
                }
            } catch (Exception Ex){}
        }
    }
    
    //-- SWING ELEMENTS --
    private JTextArea Desc;
    private JLabel UserInfo;
    //-- SWING ELEMENTS --
}