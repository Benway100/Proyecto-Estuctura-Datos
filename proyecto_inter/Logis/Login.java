/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyecto_inter.Logis;

import java.awt.Color;
import java.sql.ResultSet;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JOptionPane;
import proyecto_inter.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import proyecto_inter.EnviarCorreo;

/**
 *
 * @author mvale
 */
public class Login extends javax.swing.JFrame {

    public static void Izq(JComponent componente, int milisegundos, int saltos, int parar) {
        new Thread(() -> {
            int startX = componente.getX();
            int y = componente.getY(); // Guardamos el Y para no cambiarlo
            for (int i = startX; i >= parar; i -= saltos) {
                try {
                    Thread.sleep(milisegundos);
                    final int x = i;
                    SwingUtilities.invokeLater(() -> componente.setLocation(x, y));
                } catch (InterruptedException e) {
                    System.out.println("Error Thread Interrumpidoa: " + e);

                }
            }
        }).start();
    }

    public static void Der(JComponent componente, int milisegundos, int saltos, int parar) {
        new Thread(() -> {
            int startX = componente.getX();
            int y = componente.getY();
            for (int i = startX; i <= parar; i += saltos) {
                try {
                    Thread.sleep(milisegundos);
                    final int x = i;
                    SwingUtilities.invokeLater(() -> componente.setLocation(x, y));
                } catch (InterruptedException e) {
                    System.out.println("Error Thread Interrumpido: " + e);
                }
            }
        }).start();
    }

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();

        // Importante: usar null layout
        movi.setBounds(132,200,150,250);
        trans.setVisible(false);
        jButton2.setLayout(null);
        panelinicio.setLayout(null);
        TextoLogin.setBackground(new java.awt.Color(0, 0, 0, 0)); // Fondo 100% transparente
        TextoLogin.setBorder(null);
        trans.setBackground(new Color(255, 255, 255, 120));
        
        reti_btn.setBounds(90,20,55,60);
        reti_btn.setBackground(new java.awt.Color(0, 0, 0, 0));
        reti_btn.setBorder(null);
        reti_btn.setContentAreaFilled(false);
        reti_btn.setFocusPainted(false);
        reti_btn.setBorderPainted(false);
        reti_btn.setOpaque(false);
        retira.setBounds(15,25,70,40);
        retira.setBorder(null);
        
        pide_btn.setBounds(90,75,55,60);
        pide_btn.setBackground(new java.awt.Color(0, 0, 0, 0));
        pide_btn.setBorder(null);
        pide.setBounds(35,80,70,40);
        pide.setBorder(null);
        
        envia_btn.setBounds(90,130,55,60);
        envia_btn.setBackground(new java.awt.Color(0, 0, 0, 0));
        envia_btn.setBorder(null);
        envia.setBounds(22,135,70,40);
        envia.setBorder(null);
        
        recarga_btn.setBounds(90,185,55,60);
        recarga_btn.setBackground(new java.awt.Color(0, 0, 0, 0));
        recarga_btn.setBorder(null);
        recarga_btn.setContentAreaFilled(false);
        recarga_btn.setFocusPainted(false);
        recarga_btn.setBorderPainted(false);
        recarga_btn.setOpaque(false);
        recarga.setBounds(3,193,100,40);
        recarga.setBorder(null);
        
        
        volver_c.setBounds(10, 20, 80, 20);
        volver_c.setBackground(new java.awt.Color(0, 0, 0, 0));
        volver_c.setBorder(null);

        retiro.setBounds(45, 370, 200, 30);
        retiro.setBackground(new java.awt.Color(0, 0, 0, 0)); // Fondo 100% transparente
        retiro.setBorder(null);

        guardar.setBounds(45, 440, 200, 30);
        guardar.setBackground(new java.awt.Color(0, 0, 0, 0)); // Fondo 100% transparente
        guardar.setBorder(null);

        btn_r.setBounds(17, 260, 260, 250);
        btn_r.setOpaque(false);
        btn_r.setContentAreaFilled(false);
        btn_r.setBorderPainted(false);

        btn_g.setBounds(17, 330, 260, 250);
        btn_g.setOpaque(false);
        btn_g.setContentAreaFilled(false);
        btn_g.setBorderPainted(false);

        realizar_r.setBounds(17, 450, 260, 250);
        realizar_r.setOpaque(false);
        realizar_r.setContentAreaFilled(false);
        realizar_r.setBorderPainted(false);

        casa_btn.setBounds(20, 12, 30, 30);
        casa_btn.setOpaque(false);
        casa_btn.setContentAreaFilled(false);
        casa_btn.setBorderPainted(false);

        trans_btn.setBounds(90, 12, 30, 30);
        trans_btn.setOpaque(false);
        trans_btn.setContentAreaFilled(false);
        trans_btn.setBorderPainted(false);

        mas_btn.setBounds(160, 12, 30, 30);
        mas_btn.setOpaque(false);
        mas_btn.setContentAreaFilled(false);
        mas_btn.setBorderPainted(false);

        x_btn.setBounds(232, 450, 50, 50);
        x_btn.setOpaque(false);
        x_btn.setContentAreaFilled(false);
        x_btn.setBorderPainted(false);

        n_btn.setBounds(232, 450, 50, 50);
        n_btn.setOpaque(false);
        n_btn.setContentAreaFilled(false);
        n_btn.setBorderPainted(false);
        n_btn.setRolloverEnabled(false);

        usuario_btn.setBounds(232, 0, 70, 70);
        usuario_btn.setOpaque(false);
        usuario_btn.setContentAreaFilled(false);
        usuario_btn.setBorderPainted(false);

        sig_btn.setBounds(204, 57, 70, 70);
        sig_btn.setOpaque(false);
        sig_btn.setContentAreaFilled(false);
        sig_btn.setBorderPainted(false);

        noti_btn.setBounds(180, 0, 60, 60);
        noti_btn.setOpaque(false);
        noti_btn.setContentAreaFilled(false);
        noti_btn.setBorderPainted(false);

        metas_btn.setBounds(12, 310, 70, 70);
        metas_btn.setOpaque(false);
        metas_btn.setContentAreaFilled(false);
        metas_btn.setBorderPainted(false);
        metas_btn.setRolloverEnabled(false);

        inver_btn.setBounds(110, 310, 70, 70);
        inver_btn.setOpaque(false);
        inver_btn.setContentAreaFilled(false);
        inver_btn.setBorderPainted(false);

        caja_btn.setBounds(208, 310, 70, 70);
        caja_btn.setOpaque(false);
        caja_btn.setContentAreaFilled(false);
        caja_btn.setBorderPainted(false);
        

        jCheckBox1.setBounds(40, 495, 45, 35);
        apodoR.setBounds(20, 173, 250, 30);
        nameR.setBounds(20, 239, 250, 30);
        correoR.setBounds(20, 312, 250, 30);
        IdR.setBounds(20, 382, 250, 30);
        contraR.setBounds(20, 448, 250, 30);

        apodoR.setBorder(null);
        nameR.setBorder(null);
        correoR.setBorder(null);
        IdR.setBorder(null);
        contraR.setBorder(null);

        apodoR.setBackground(new java.awt.Color(0, 0, 0, 0));
        nameR.setBackground(new java.awt.Color(0, 0, 0, 0));
        correoR.setBackground(new java.awt.Color(0, 0, 0, 0));
        IdR.setBackground(new java.awt.Color(0, 0, 0, 0));
        contraR.setBackground(new java.awt.Color(0, 0, 0, 0));

        botonR.setBounds(30, 482, 240, 200);
        botonR.setOpaque(false);
        botonR.setContentAreaFilled(false);
        botonR.setBorderPainted(false);

        regis.setBounds(40, 590, 135, 30);
        regis.setOpaque(false);
        regis.setContentAreaFilled(false);
        regis.setBorderPainted(false);

        t1.setBorder(null);// Opcional: sin bordes
        t2.setBorder(null);
        t3.setBorder(null);
        t4.setBorder(null);

        getContentPane().add(panel_static);
        getContentPane().add(panelinicio);
        panelinicio.setBounds(0, 0, 300, 621);
        PanelLogin.setBounds(-300, 0, 300, 621);
        PanelRegis.setBounds(300, 0, 300, 621);
        Panelcuenta.setBounds(-300, 0, 300, 621);
        panel_static.setBounds(-255, 530, 210, 60);
        panel_cajitas.setBounds(300, 0, 300, 621);
        pass.setBounds(5, 5, 60, 50);
        getContentPane().add(PanelLogin);
        getContentPane().add(PanelRegis);
        getContentPane().add(Panelcuenta);
        getContentPane().add(panel_cajitas);

        jButton1.setBounds(40, 410, 211, 60);
        jButton2.setBounds(40, 480, 211, 60);

        jButton1.setOpaque(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setBorderPainted(false);

        jButton2.setOpaque(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setBorderPainted(false);

        b1.setOpaque(false);
        b1.setContentAreaFilled(false);
        b1.setBorderPainted(false);

        b2.setOpaque(false);
        b2.setContentAreaFilled(false);
        b2.setBorderPainted(false);

        b3.setOpaque(false);
        b3.setContentAreaFilled(false);
        b3.setBorderPainted(false);

        b4.setOpaque(false);
        b4.setContentAreaFilled(false);
        b4.setBorderPainted(false);

        b5.setOpaque(false);
        b5.setContentAreaFilled(false);
        b5.setBorderPainted(false);

        b6.setOpaque(false);
        b6.setContentAreaFilled(false);
        b6.setBorderPainted(false);

        b7.setOpaque(false);
        b7.setContentAreaFilled(false);
        b7.setBorderPainted(false);

        b8.setOpaque(false);
        b8.setContentAreaFilled(false);
        b8.setBorderPainted(false);

        b9.setOpaque(false);
        b9.setContentAreaFilled(false);
        b9.setBorderPainted(false);

        b0.setOpaque(false);
        b0.setContentAreaFilled(false);
        b0.setBorderPainted(false);

        borar.setOpaque(false);
        borar.setContentAreaFilled(false);
        borar.setBorderPainted(false);

        atras.setOpaque(false);
        atras.setContentAreaFilled(false);
        atras.setBorderPainted(false);
    }

    public void logicLogin() {
        String ide = TextoLogin.getText(); // ID ingresado en el login
        String pass = t1.getText() + t2.getText() + t3.getText() + t4.getText(); // Contrase?a ingresada

        try {
            Connection conn = Conexion.getConnection();
            if (conn != null) {
                String sql = "SELECT * FROM usuarios WHERE id = ? AND contrasena = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, ide);
                stmt.setString(2, pass);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Inicio de sesi?n exitoso");
                    // Aqu� puedes abrir el siguiente formulario o ventana del sistema
                    // new Menu().setVisible(true); // por ejemplo
                    // this.dispose(); // para cerrar el login
                    Izq(PanelLogin, 10, 5, -300);
                    Der(Panelcuenta, 10, 5, 0);
                    Der(panel_static, 10, 5, 45);
                } else {
                    JOptionPane.showMessageDialog(this, "ID o contrase?a incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
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

        trans = new javax.swing.JPanel();
        movi = new fondomovi();
        reti_btn = new javax.swing.JButton();
        retira = new javax.swing.JLabel();
        pide_btn = new javax.swing.JButton();
        pide = new javax.swing.JLabel();
        envia_btn = new javax.swing.JButton();
        envia = new javax.swing.JLabel();
        recarga = new javax.swing.JLabel();
        recarga_btn = new javax.swing.JButton();
        x_btn = new javax.swing.JButton();
        PanelLogin = new fondoLogin();
        borar = new javax.swing.JButton();
        b1 = new javax.swing.JButton();
        b2 = new javax.swing.JButton();
        b3 = new javax.swing.JButton();
        b6 = new javax.swing.JButton();
        b5 = new javax.swing.JButton();
        b4 = new javax.swing.JButton();
        b7 = new javax.swing.JButton();
        b8 = new javax.swing.JButton();
        b9 = new javax.swing.JButton();
        b0 = new javax.swing.JButton();
        t4 = new javax.swing.JTextField();
        t1 = new javax.swing.JTextField();
        t2 = new javax.swing.JTextField();
        t3 = new javax.swing.JTextField();
        atras = new javax.swing.JButton();
        panel_cajitas = new fondocajitas();
        retiro = new javax.swing.JTextField();
        guardar = new javax.swing.JTextField();
        btn_r = new javax.swing.JButton();
        btn_g = new javax.swing.JButton();
        realizar_r = new javax.swing.JButton();
        volver_c = new javax.swing.JButton();
        panelinicio = new fondopanel();
        TextoLogin = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        regis = new javax.swing.JButton();
        PanelRegis = new fondoregis();
        apodoR = new javax.swing.JTextField();
        nameR = new javax.swing.JTextField();
        correoR = new javax.swing.JTextField();
        IdR = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        botonR = new javax.swing.JButton();
        contraR = new javax.swing.JTextField();
        Panelcuenta = new fondocuenta();
        n_btn = new javax.swing.JButton();
        metas_btn = new javax.swing.JButton();
        inver_btn = new javax.swing.JButton();
        caja_btn = new javax.swing.JButton();
        usuario_btn = new javax.swing.JButton();
        sig_btn = new javax.swing.JButton();
        noti_btn = new javax.swing.JButton();
        panel_static = new fondostatic();
        casa_btn = new javax.swing.JButton();
        trans_btn = new javax.swing.JButton();
        mas_btn = new javax.swing.JButton();
        pass = new fondopass();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DUNAB+");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        trans.setForeground(new java.awt.Color(0, 0, 0));
        trans.setMaximumSize(new java.awt.Dimension(300, 621));
        trans.setPreferredSize(new java.awt.Dimension(300, 621));
        trans.setLayout(null);

        movi.setLayout(null);

        reti_btn.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        reti_btn.setForeground(new java.awt.Color(255, 255, 255));
        reti_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/retira_btn.png"))); // NOI18N
        reti_btn.setToolTipText("");
        reti_btn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        movi.add(reti_btn);
        reti_btn.setBounds(160, 300, 51, 63);

        retira.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Retira.png"))); // NOI18N
        movi.add(retira);
        retira.setBounds(240, 420, 73, 26);

        pide_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pide_btn.png"))); // NOI18N
        movi.add(pide_btn);
        pide_btn.setBounds(190, 260, 51, 65);

        pide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Pide.png"))); // NOI18N
        pide.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        movi.add(pide);
        pide.setBounds(260, 380, 55, 26);

        envia_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/envia_btn.png"))); // NOI18N
        movi.add(envia_btn);
        envia_btn.setBounds(160, 340, 52, 65);

        envia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Envía.png"))); // NOI18N
        envia.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        movi.add(envia);
        envia.setBounds(250, 360, 68, 25);

        recarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Recarga.png"))); // NOI18N
        movi.add(recarga);
        recarga.setBounds(220, 340, 110, 30);

        recarga_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/recar_btn.png"))); // NOI18N
        movi.add(recarga_btn);
        recarga_btn.setBounds(120, 220, 51, 65);

        trans.add(movi);
        movi.setBounds(90, 190, 100, 100);

        x_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/x_btn.png"))); // NOI18N
        x_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                x_btnMouseClicked(evt);
            }
        });
        trans.add(x_btn);
        x_btn.setBounds(120, 220, 51, 53);

        PanelLogin.setPreferredSize(new java.awt.Dimension(300, 621));
        PanelLogin.setLayout(null);

        borar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borarMouseClicked(evt);
            }
        });
        PanelLogin.add(borar);
        borar.setBounds(190, 490, 70, 50);

        b1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b1MouseClicked(evt);
            }
        });
        PanelLogin.add(b1);
        b1.setBounds(40, 320, 60, 40);

        b2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b2MouseClicked(evt);
            }
        });
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });
        PanelLogin.add(b2);
        b2.setBounds(120, 320, 60, 40);

        b3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b3MouseClicked(evt);
            }
        });
        PanelLogin.add(b3);
        b3.setBounds(200, 320, 60, 40);

        b6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b6MouseClicked(evt);
            }
        });
        b6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b6ActionPerformed(evt);
            }
        });
        PanelLogin.add(b6);
        b6.setBounds(200, 380, 60, 40);

        b5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b5MouseClicked(evt);
            }
        });
        PanelLogin.add(b5);
        b5.setBounds(120, 380, 60, 40);

        b4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b4MouseClicked(evt);
            }
        });
        PanelLogin.add(b4);
        b4.setBounds(40, 380, 60, 40);

        b7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b7MouseClicked(evt);
            }
        });
        PanelLogin.add(b7);
        b7.setBounds(40, 440, 60, 40);

        b8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b8MouseClicked(evt);
            }
        });
        PanelLogin.add(b8);
        b8.setBounds(120, 440, 60, 40);

        b9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b9MouseClicked(evt);
            }
        });
        b9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b9ActionPerformed(evt);
            }
        });
        PanelLogin.add(b9);
        b9.setBounds(200, 440, 60, 40);

        b0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b0MouseClicked(evt);
            }
        });
        PanelLogin.add(b0);
        b0.setBounds(120, 500, 60, 40);

        t4.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        t4.setForeground(new java.awt.Color(208, 139, 13));
        t4.setSelectionColor(new java.awt.Color(255, 255, 255));
        PanelLogin.add(t4);
        t4.setBounds(230, 120, 40, 40);

        t1.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        t1.setForeground(new java.awt.Color(208, 139, 13));
        t1.setSelectionColor(new java.awt.Color(255, 255, 255));
        t1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t1ActionPerformed(evt);
            }
        });
        PanelLogin.add(t1);
        t1.setBounds(50, 120, 40, 40);

        t2.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        t2.setForeground(t1.getForeground());
        t2.setSelectionColor(new java.awt.Color(255, 255, 255));
        PanelLogin.add(t2);
        t2.setBounds(110, 120, 40, 40);

        t3.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        t3.setForeground(t1.getForeground());
        t3.setSelectionColor(new java.awt.Color(255, 255, 255));
        PanelLogin.add(t3);
        t3.setBounds(170, 120, 40, 40);

        atras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                atrasMouseClicked(evt);
            }
        });
        PanelLogin.add(atras);
        atras.setBounds(20, 20, 75, 30);

        panel_cajitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_cajitasMouseClicked(evt);
            }
        });
        panel_cajitas.setLayout(null);

        retiro.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        retiro.setForeground(new java.awt.Color(233, 164, 39));
        retiro.setText("¿Cuanto quieres retirar?");
        retiro.setToolTipText("");
        retiro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                retiroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                retiroFocusLost(evt);
            }
        });
        panel_cajitas.add(retiro);
        retiro.setBounds(110, 50, 210, 28);

        guardar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        guardar.setForeground(new java.awt.Color(233, 164, 39));
        guardar.setText("¿Cuanto quieres guardar?");
        guardar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                guardarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                guardarFocusLost(evt);
            }
        });
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });
        panel_cajitas.add(guardar);
        guardar.setBounds(90, 130, 225, 28);

        btn_r.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cuanto_g.png"))); // NOI18N
        panel_cajitas.add(btn_r);
        btn_r.setBounds(6, 26, 261, 70);

        btn_g.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cuanto_g.png"))); // NOI18N
        panel_cajitas.add(btn_g);
        btn_g.setBounds(6, 191, 261, 70);

        realizar_r.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/realizar_m.png"))); // NOI18N
        realizar_r.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                realizar_rMouseClicked(evt);
            }
        });
        panel_cajitas.add(realizar_r);
        realizar_r.setBounds(11, 97, 256, 82);

        volver_c.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volver_cMouseClicked(evt);
            }
        });
        panel_cajitas.add(volver_c);
        volver_c.setBounds(10, 0, 72, 7);

        getContentPane().setLayout(null);
        panelinicio.setMaximumSize(new java.awt.Dimension(300, 621));
        panelinicio.setMinimumSize(new java.awt.Dimension(300, 621));
        panelinicio.setLayout(null);

        TextoLogin.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        TextoLogin.setForeground(new java.awt.Color(255, 255, 255));
        TextoLogin.setText("ID Unab");
        TextoLogin.setToolTipText("Ingrese su ID");
        TextoLogin.setBorder(null);
        TextoLogin.setCaretColor(new java.awt.Color(255, 255, 255));
        TextoLogin.setDisabledTextColor(new java.awt.Color(102, 102, 0));
        TextoLogin.setOpaque(false);
        TextoLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TextoLoginFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TextoLoginFocusLost(evt);
            }
        });
        TextoLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextoLoginActionPerformed(evt);
            }
        });
        panelinicio.add(TextoLogin);
        TextoLogin.setBounds(115, 426, 110, 30);

        jButton1.setBackground(new java.awt.Color(255, 153, 0));
        jButton1.setForeground(new java.awt.Color(232, 164, 38));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Crea.png"))); // NOI18N
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName(""); // NOI18N
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelinicio.add(jButton1);
        jButton1.setBounds(27, 487, 211, 58);

        jButton2.setForeground(new java.awt.Color(232, 164, 38));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ingresar.png"))); // NOI18N
        jButton2.setMaximumSize(new java.awt.Dimension(211, 58));
        jButton2.setMinimumSize(new java.awt.Dimension(211, 58));
        jButton2.setName(""); // NOI18N
        jButton2.setPreferredSize(new java.awt.Dimension(211, 58));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panelinicio.add(jButton2);
        jButton2.setBounds(27, 551, 211, 58);

        regis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                regisMouseClicked(evt);
            }
        });
        panelinicio.add(regis);
        regis.setBounds(160, 380, 72, 7);

        PanelRegis.setMaximumSize(new java.awt.Dimension(300, 621));
        PanelRegis.setMinimumSize(new java.awt.Dimension(300, 621));
        PanelRegis.setLayout(null);

        apodoR.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        apodoR.setForeground(new java.awt.Color(255, 255, 255));
        PanelRegis.add(apodoR);
        apodoR.setBounds(20, 200, 64, 28);

        nameR.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        nameR.setForeground(new java.awt.Color(255, 255, 255));
        PanelRegis.add(nameR);
        nameR.setBounds(20, 260, 64, 28);

        correoR.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        correoR.setForeground(new java.awt.Color(255, 255, 255));
        PanelRegis.add(correoR);
        correoR.setBounds(20, 330, 64, 28);

        IdR.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        IdR.setForeground(new java.awt.Color(255, 255, 255));
        PanelRegis.add(IdR);
        IdR.setBounds(20, 390, 64, 20);

        jCheckBox1.setToolTipText("");
        jCheckBox1.setBorder(null);
        jCheckBox1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check.png"))); // NOI18N
        jCheckBox1.setMaximumSize(new java.awt.Dimension(60, 60));
        jCheckBox1.setMinimumSize(new java.awt.Dimension(60, 60));
        jCheckBox1.setPreferredSize(new java.awt.Dimension(60, 60));
        jCheckBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBox1MouseClicked(evt);
            }
        });
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        PanelRegis.add(jCheckBox1);
        jCheckBox1.setBounds(20, 460, 60, 60);

        botonR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botn_regis.png"))); // NOI18N
        botonR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonRMouseClicked(evt);
            }
        });
        PanelRegis.add(botonR);
        botonR.setBounds(20, 530, 260, 83);

        contraR.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        contraR.setForeground(new java.awt.Color(255, 255, 255));
        PanelRegis.add(contraR);
        contraR.setBounds(20, 430, 64, 28);

        Panelcuenta.setMaximumSize(new java.awt.Dimension(300, 621));
        Panelcuenta.setMinimumSize(new java.awt.Dimension(300, 621));
        Panelcuenta.setLayout(null);

        n_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/n_btn.png"))); // NOI18N
        n_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                n_btnMouseClicked(evt);
            }
        });
        Panelcuenta.add(n_btn);
        n_btn.setBounds(0, 0, 51, 53);

        metas_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/metas_btn.png"))); // NOI18N
        Panelcuenta.add(metas_btn);
        metas_btn.setBounds(20, 310, 64, 70);

        inver_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inverciones_btn.png"))); // NOI18N
        Panelcuenta.add(inver_btn);
        inver_btn.setBounds(110, 310, 72, 73);

        caja_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/caja_btn.png"))); // NOI18N
        caja_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                caja_btnMouseClicked(evt);
            }
        });
        caja_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caja_btnActionPerformed(evt);
            }
        });
        Panelcuenta.add(caja_btn);
        caja_btn.setBounds(210, 310, 63, 64);

        usuario_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuario_btn.png"))); // NOI18N
        Panelcuenta.add(usuario_btn);
        usuario_btn.setBounds(160, 50, 62, 63);

        sig_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sig_btn.png"))); // NOI18N
        Panelcuenta.add(sig_btn);
        sig_btn.setBounds(170, 110, 60, 57);

        noti_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/noti_btn.png"))); // NOI18N
        Panelcuenta.add(noti_btn);
        noti_btn.setBounds(90, 70, 62, 60);

        panel_static.setLayout(null);

        casa_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/casa_btn.png"))); // NOI18N
        panel_static.add(casa_btn);
        casa_btn.setBounds(0, 0, 39, 40);

        trans_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/trans_btn.png"))); // NOI18N
        panel_static.add(trans_btn);
        trans_btn.setBounds(0, 0, 36, 38);

        mas_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mas_btn.png"))); // NOI18N
        panel_static.add(mas_btn);
        mas_btn.setBounds(0, 0, 37, 36);

        javax.swing.GroupLayout passLayout = new javax.swing.GroupLayout(pass);
        pass.setLayout(passLayout);
        passLayout.setHorizontalGroup(
            passLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        passLayout.setVerticalGroup(
            passLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        panel_static.add(pass);
        pass.setBounds(0, 0, 100, 100);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelinicio, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(PanelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(PanelRegis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Panelcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_static, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_cajitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(trans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(PanelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(PanelRegis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Panelcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_static, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_cajitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(trans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        Der(panelinicio, 10, 5, 300);
        Der(PanelLogin, 10, 5, 0);


    }//GEN-LAST:event_jButton2MouseClicked

    private void TextoLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TextoLoginFocusGained
        // TODO add your handling code here:
        TextoLogin.setText("");
    }//GEN-LAST:event_TextoLoginFocusGained

    private void TextoLoginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TextoLoginFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_TextoLoginFocusLost

    private void t1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t1ActionPerformed

    private void b9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b9ActionPerformed

    private void b2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b2ActionPerformed

    private void atrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_atrasMouseClicked
        // TODO add your handling code here:
        Izq(panelinicio, 10, 5, 0);
        Izq(PanelLogin, 10, 5, -300);
    }//GEN-LAST:event_atrasMouseClicked

    private void b1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b1MouseClicked
        // TODO add your handling code here:
        if (t1.getText().equals("")) {
            t1.setText("1");
        } else if (t2.getText().equals("")) {
            t2.setText("1");
        } else if (t3.getText().equals("")) {
            t3.setText("1");
        } else if (t4.getText().equals("")) {
            t4.setText("1");
        }
        if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("")) {

        } else {
            logicLogin();
        }
    }//GEN-LAST:event_b1MouseClicked

    private void b2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b2MouseClicked
        // TODO add your handling code here:
        if (t1.getText().equals("")) {
            t1.setText("2");
        } else if (t2.getText().equals("")) {
            t2.setText("2");
        } else if (t3.getText().equals("")) {
            t3.setText("2");
        } else if (t4.getText().equals("")) {
            t4.setText("2");
        }
        if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("")) {

        } else {
            logicLogin();
        }
    }//GEN-LAST:event_b2MouseClicked

    private void b3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b3MouseClicked
        // TODO add your handling code here:
        if (t1.getText().equals("")) {
            t1.setText("3");
        } else if (t2.getText().equals("")) {
            t2.setText("3");
        } else if (t3.getText().equals("")) {
            t3.setText("3");
        } else if (t4.getText().equals("")) {
            t4.setText("3");
        }
        if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("")) {

        } else {
            logicLogin();
        }
    }//GEN-LAST:event_b3MouseClicked

    private void b4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b4MouseClicked
        // TODO add your handling code here:
        if (t1.getText().equals("")) {
            t1.setText("4");
        } else if (t2.getText().equals("")) {
            t2.setText("4");
        } else if (t3.getText().equals("")) {
            t3.setText("4");
        } else if (t4.getText().equals("")) {
            t4.setText("4");
        }
        if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("")) {

        } else {
            logicLogin();
        }
    }//GEN-LAST:event_b4MouseClicked

    private void b5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b5MouseClicked
        // TODO add your handling code here:
        if (t1.getText().equals("")) {
            t1.setText("5");
        } else if (t2.getText().equals("")) {
            t2.setText("5");
        } else if (t3.getText().equals("")) {
            t3.setText("5");
        } else if (t4.getText().equals("")) {
            t4.setText("5");
        }
        if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("")) {

        } else {
            logicLogin();
        }
    }//GEN-LAST:event_b5MouseClicked

    private void b6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b6ActionPerformed

    private void b6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b6MouseClicked
        // TODO add your handling code here:
        if (t1.getText().equals("")) {
            t1.setText("6");
        } else if (t2.getText().equals("")) {
            t2.setText("6");
        } else if (t3.getText().equals("")) {
            t3.setText("6");
        } else if (t4.getText().equals("")) {
            t4.setText("6");
        }
        if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("")) {

        } else {
            logicLogin();
        }
    }//GEN-LAST:event_b6MouseClicked

    private void b7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b7MouseClicked
        // TODO add your handling code here:
        if (t1.getText().equals("")) {
            t1.setText("7");
        } else if (t2.getText().equals("")) {
            t2.setText("7");
        } else if (t3.getText().equals("")) {
            t3.setText("7");
        } else if (t4.getText().equals("")) {
            t4.setText("7");
        }
        if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("")) {

        } else {
            logicLogin();
        }
    }//GEN-LAST:event_b7MouseClicked

    private void b8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b8MouseClicked
        // TODO add your handling code here:
        if (t1.getText().equals("")) {
            t1.setText("8");
        } else if (t2.getText().equals("")) {
            t2.setText("8");
        } else if (t3.getText().equals("")) {
            t3.setText("8");
        } else if (t4.getText().equals("")) {
            t4.setText("8");
        }
        if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("")) {

        } else {
            logicLogin();
        }
    }//GEN-LAST:event_b8MouseClicked

    private void b9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b9MouseClicked
        // TODO add your handling code here:
        if (t1.getText().equals("")) {
            t1.setText("9");
        } else if (t2.getText().equals("")) {
            t2.setText("9");
        } else if (t3.getText().equals("")) {
            t3.setText("9");
        } else if (t4.getText().equals("")) {
            t4.setText("9");
        }
        if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("")) {

        } else {
            logicLogin();
        }
    }//GEN-LAST:event_b9MouseClicked

    private void b0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b0MouseClicked
        // TODO add your handling code here:
        if (t1.getText().equals("")) {
            t1.setText("0");
        } else if (t2.getText().equals("")) {
            t2.setText("0");
        } else if (t3.getText().equals("")) {
            t3.setText("0");
        } else if (t4.getText().equals("")) {
            t4.setText("0");
        }
        if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("")) {

        } else {
            logicLogin();
        }
    }//GEN-LAST:event_b0MouseClicked

    private void borarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borarMouseClicked
        // TODO add your handling code here:

        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
    }//GEN-LAST:event_borarMouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed
    boolean h = true;
    private void jCheckBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBox1MouseClicked
        // TODO add your handling code here:
        if (h) {
            jCheckBox1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check_yes.png")));
            h = false;
        } else {
            jCheckBox1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check.png")));
            h = true;
        }

    }//GEN-LAST:event_jCheckBox1MouseClicked

    private void TextoLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextoLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextoLoginActionPerformed

    private void regisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_regisMouseClicked
        // TODO add your handling code here:
        Izq(panelinicio, 10, 5, -300);
        Izq(PanelRegis, 10, 5, 0);
    }//GEN-LAST:event_regisMouseClicked

    private void botonRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonRMouseClicked
        // TODO add your handling code here:
        String name = nameR.getText();
        String ide = IdR.getText();
        String apodo = (apodoR.getText());
        String gmail = correoR.getText();
        String pass = contraR.getText();
        if (name.equals("") || ide.equals("") || gmail.equals("") || pass.equals("") || apodo.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Empty entries, please check again", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                Connection conn = Conexion.getConnection();
                if (conn != null) {
                    String sql = "INSERT INTO usuarios (id,nombre,apodo,correo,contrasena) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, ide);
                    stmt.setString(2, name);
                    stmt.setString(3, apodo);
                    stmt.setString(4, gmail);
                    stmt.setString(5, pass);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Registro exitosoa");
                    Der(PanelRegis, 10, 5, 600);
                    Der(panelinicio, 10, 5, 0);
                    EnviarCorreo Ecorreo = new EnviarCorreo();
                    Ecorreo.enviar(gmail, name, ide, pass, apodo);
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_botonRMouseClicked

    private void caja_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_caja_btnMouseClicked
        // TODO add your handling code here:
        Izq(Panelcuenta, 10, 5, -300);
        Izq(panel_static, 10, 5, -255);
        Izq(panel_cajitas, 10, 5, 0);
    }//GEN-LAST:event_caja_btnMouseClicked

    private void retiroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_retiroFocusGained
        // TODO add your handling code here:
        retiro.setText("");
    }//GEN-LAST:event_retiroFocusGained

    private void guardarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_guardarFocusGained
        // TODO add your handling code here:
        guardar.setText("");
    }//GEN-LAST:event_guardarFocusGained

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guardarActionPerformed

    private void guardarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_guardarFocusLost
        // TODO add your handling code here:\

    }//GEN-LAST:event_guardarFocusLost

    private void retiroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_retiroFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_retiroFocusLost

    private void panel_cajitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_cajitasMouseClicked
        // TODO add your handling code here:
        guardar.setText("¿Cuanto quieres guardar?");
        retiro.setText("¿Cuanto quieres retirar?");
    }//GEN-LAST:event_panel_cajitasMouseClicked

    private void realizar_rMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_realizar_rMouseClicked
        // TODO add your handling code here:
        guardar.setText("¿Cuanto quieres guardar?");
        retiro.setText("¿Cuanto quieres retirar?");
    }//GEN-LAST:event_realizar_rMouseClicked

    private void volver_cMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volver_cMouseClicked
        // TODO add your handling code here:
        Der(Panelcuenta, 10, 5, 0);
        Der(panel_static, 10, 5, 45);
        Der(panel_cajitas, 10, 5, 300);

    }//GEN-LAST:event_volver_cMouseClicked

    private void caja_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caja_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_caja_btnActionPerformed

    private void n_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_n_btnMouseClicked
        // TODO add your handling code here:

        trans.setVisible(true);
        Panelcuenta.repaint();


    }//GEN-LAST:event_n_btnMouseClicked

    private void x_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_x_btnMouseClicked
        // TODO add your handling code here:

        trans.setVisible(false);
        Panelcuenta.repaint();
    }//GEN-LAST:event_x_btnMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IdR;
    private javax.swing.JPanel PanelLogin;
    private javax.swing.JPanel PanelRegis;
    private javax.swing.JPanel Panelcuenta;
    private javax.swing.JTextField TextoLogin;
    private javax.swing.JTextField apodoR;
    private javax.swing.JButton atras;
    private javax.swing.JButton b0;
    private javax.swing.JButton b1;
    private javax.swing.JButton b2;
    private javax.swing.JButton b3;
    private javax.swing.JButton b4;
    private javax.swing.JButton b5;
    private javax.swing.JButton b6;
    private javax.swing.JButton b7;
    private javax.swing.JButton b8;
    private javax.swing.JButton b9;
    private javax.swing.JButton borar;
    private javax.swing.JButton botonR;
    private javax.swing.JButton btn_g;
    private javax.swing.JButton btn_r;
    private javax.swing.JButton caja_btn;
    private javax.swing.JButton casa_btn;
    private javax.swing.JTextField contraR;
    private javax.swing.JTextField correoR;
    private javax.swing.JLabel envia;
    private javax.swing.JButton envia_btn;
    private javax.swing.JTextField guardar;
    private javax.swing.JButton inver_btn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JButton mas_btn;
    private javax.swing.JButton metas_btn;
    private javax.swing.JPanel movi;
    private javax.swing.JButton n_btn;
    private javax.swing.JTextField nameR;
    private javax.swing.JButton noti_btn;
    private javax.swing.JPanel panel_cajitas;
    private javax.swing.JPanel panel_static;
    private javax.swing.JPanel panelinicio;
    private javax.swing.JPanel pass;
    private javax.swing.JLabel pide;
    private javax.swing.JButton pide_btn;
    private javax.swing.JButton realizar_r;
    private javax.swing.JLabel recarga;
    private javax.swing.JButton recarga_btn;
    private javax.swing.JButton regis;
    private javax.swing.JButton reti_btn;
    private javax.swing.JLabel retira;
    private javax.swing.JTextField retiro;
    private javax.swing.JButton sig_btn;
    private javax.swing.JTextField t1;
    private javax.swing.JTextField t2;
    private javax.swing.JTextField t3;
    private javax.swing.JTextField t4;
    private javax.swing.JPanel trans;
    private javax.swing.JButton trans_btn;
    private javax.swing.JButton usuario_btn;
    private javax.swing.JButton volver_c;
    private javax.swing.JButton x_btn;
    // End of variables declaration//GEN-END:variables

    class fondopanel extends JPanel {

        private Image imagen;

        public void paint(Graphics g) {
            imagen = new ImageIcon(getClass().getResource("/imagenes/fondo.png")).getImage();
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondoLogin extends JPanel {

        private Image imagen1;

        public void paint(Graphics g) {
            imagen1 = new ImageIcon(getClass().getResource("/imagenes/fondo_login.png")).getImage();
            g.drawImage(imagen1, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondoregis extends JPanel {

        private Image imagen2;

        public void paint(Graphics g) {
            imagen2 = new ImageIcon(getClass().getResource("/imagenes/fondo_regis.png")).getImage();
            g.drawImage(imagen2, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondocuenta extends JPanel {

        private Image imagen3;

        public void paint(Graphics g) {
            imagen3 = new ImageIcon(getClass().getResource("/imagenes/fondo_inicio.png")).getImage();
            g.drawImage(imagen3, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondostatic extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/static.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondopass extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/pass.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondocajitas extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/cajitas.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondomovi extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/movi.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

}
