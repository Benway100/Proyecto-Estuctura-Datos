/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyecto_inter.Logis;

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
        jButton2.setLayout(null);
        panelinicio.setLayout(null);
        TextoLogin.setBackground(new java.awt.Color(0, 0, 0, 0)); // Fondo 100% transparente
        TextoLogin.setBorder(null);

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

        getContentPane().add(panelinicio);
        panelinicio.setBounds(0, 0, 300, 621);
        PanelLogin.setBounds(-300, 0, 300, 621);
        PanelRegis.setBounds(300, 0, 300, 621);
        getContentPane().add(PanelLogin);
        getContentPane().add(PanelRegis);

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
                    // Aquí puedes abrir el siguiente formulario o ventana del sistema
                    // new Menu().setVisible(true); // por ejemplo
                    // this.dispose(); // para cerrar el login
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DUNAB+");
        setBackground(new java.awt.Color(153, 204, 0));
        setResizable(false);

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
        if (t1.getText().equals("")|| t2.getText().equals("")|| t3.getText().equals("")||t4.getText().equals("")) {     
            
        }else{
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
        if (t1.getText().equals("")|| t2.getText().equals("")|| t3.getText().equals("")||t4.getText().equals("")) {     
            
        }else{
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
        if (t1.getText().equals("")|| t2.getText().equals("")|| t3.getText().equals("")||t4.getText().equals("")) {     
            
        }else{
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
        if (t1.getText().equals("")|| t2.getText().equals("")|| t3.getText().equals("")||t4.getText().equals("")) {     
            
        }else{
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
        if (t1.getText().equals("")|| t2.getText().equals("")|| t3.getText().equals("")||t4.getText().equals("")) {     
            
        }else{
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
        if (t1.getText().equals("")|| t2.getText().equals("")|| t3.getText().equals("")||t4.getText().equals("")) {     
            
        }else{
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
        if (t1.getText().equals("")|| t2.getText().equals("")|| t3.getText().equals("")||t4.getText().equals("")) {     
            
        }else{
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
        if (t1.getText().equals("")|| t2.getText().equals("")|| t3.getText().equals("")||t4.getText().equals("")) {     
            
        }else{
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
        if (t1.getText().equals("")|| t2.getText().equals("")|| t3.getText().equals("")||t4.getText().equals("")) {     
            
        }else{
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
        if (t1.getText().equals("")|| t2.getText().equals("")|| t3.getText().equals("")||t4.getText().equals("")) {     
            
        }else{
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
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_botonRMouseClicked

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
    private javax.swing.JTextField contraR;
    private javax.swing.JTextField correoR;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JTextField nameR;
    private javax.swing.JPanel panelinicio;
    private javax.swing.JButton regis;
    private javax.swing.JTextField t1;
    private javax.swing.JTextField t2;
    private javax.swing.JTextField t3;
    private javax.swing.JTextField t4;
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

}
