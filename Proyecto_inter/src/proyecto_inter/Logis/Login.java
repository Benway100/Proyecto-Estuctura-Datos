/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyecto_inter.Logis;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Timestamp;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import proyecto_inter.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import proyecto_inter.EnviarCorreo;

/**
 *
 * @author mvale
 */
public class Login extends javax.swing.JFrame {

    public static void moverAbajo(JComponent componente, int milisegundos, int saltos, int parar) {
        new Thread(() -> {
            int x = componente.getX();
            int starty = componente.getY();
            for (int i = starty; i <= parar; i += saltos) {
                try {
                    Thread.sleep(milisegundos);
                    final int y = i;
                    SwingUtilities.invokeLater(() -> componente.setLocation(x, y));
                } catch (InterruptedException e) {
                    System.out.println("Error Thread Interrumpido: " + e);
                }
            }
        }).start();
    }

    public static void moverArriba(JComponent componente, int milisegundos, int saltos, int parar) {
        new Thread(() -> {
            int x = componente.getX();
            int starty = componente.getY();
            for (int i = starty; i >= parar; i -= saltos) {
                try {
                    Thread.sleep(milisegundos);
                    final int y = i;
                    SwingUtilities.invokeLater(() -> componente.setLocation(x, y));
                } catch (InterruptedException e) {
                    System.out.println("Error Thread Interrumpido: " + e);
                }
            }
        }).start();
    }

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

    public void descargarHistorial(String idUsuario, File destino) {
        Connection con = Conexion.getConnection();

        try {
            String sql = "SELECT * FROM usuarios_dunab.transacciones WHERE id_usuario = ? ORDER BY fecha DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            PrintWriter writer = new PrintWriter(new FileWriter(destino));

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                int valor = rs.getInt("valor");
                String destinoStr = rs.getString("destino");
                Timestamp fecha = rs.getTimestamp("fecha");

                writer.println("ID: " + id
                        + ", Nombre: " + nombre
                        + ", Tipo: " + tipo
                        + ", Valor: " + valor
                        + ", Destino: " + destinoStr
                        + ", Fecha: " + fecha);
            }

            writer.close();
            JOptionPane.showMessageDialog(null, "Historial descargado exitosamente en: " + destino.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al descargar historial: " + e.getMessage());
        }
    }

    public void mostrarUltimasTransacciones(String idUsuario, JPanel panel) {
        Connection con = Conexion.getConnection();
        panel.removeAll();
        panel.setLayout(null); // Desactiva el layout automático

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        // Tamaño y posición: (x, y, ancho, alto)
        area.setBounds(25, 145, 260, 370);
        area.setBorder(null);
        // Cambia estos valores como quieras

        try {
            String sql = "SELECT * FROM usuarios_dunab.transacciones WHERE id_usuario = ? ORDER BY fecha DESC LIMIT 6";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            StringBuilder texto = new StringBuilder();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                int valor = rs.getInt("valor");
                String destino = rs.getString("destino");
                Timestamp fecha = rs.getTimestamp("fecha");

                texto.append("ID: ").append(id)
                        .append(" | Nombre: ").append(nombre)
                        .append(" | Tipo: ").append(tipo)
                        .append(" | Valor: ").append(valor)
                        .append(" | Destino: ").append(destino)
                        .append(" | Fecha: ").append(fecha)
                        .append("\n\n");
            }

            area.setText(texto.toString());
            panel.add(area);
            panel.revalidate();
            panel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error al cargar el historial: " + e.getMessage());
        }
    }

    public void inversiones(int monto) {
        Connection con = Conexion.getConnection();
        String idUsuario = TextoLogin.getText();
        try {
            // Consulta todos los ingresos
            String sql = "SELECT cantidad_total, cantidad_disponible FROM ingresos WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "ID de usuario no encontrado.");
                return;
            }
            int totalActual = rs.getInt("cantidad_total");
            int disponibleActual = rs.getInt("cantidad_disponible");

            // Recorrer todos los registros
            int porcentaje = (int) (Math.random() * 3) + 1;
            int ganancia = (int) Math.round(monto * (porcentaje / 100.0));

            // Actualizar cantidades
            int nuevoTotal = totalActual + ganancia;
            int nuevoDisponible = disponibleActual + ganancia;

            // Guardar cambios
            String updateSQL = "UPDATE ingresos SET cantidad_total = ?, cantidad_disponible = ? WHERE id = ?";
            PreparedStatement psUpdate = con.prepareStatement(updateSQL);
            psUpdate.setInt(1, nuevoTotal);
            psUpdate.setInt(2, nuevoDisponible);
            psUpdate.setString(3, idUsuario);
            psUpdate.executeUpdate();

            JOptionPane.showMessageDialog(null,
                    "Inversión registrada para el ID: " + idUsuario
                    + "\nMonto invertido: $" + monto
                    + "\nGanancia aplicada (" + porcentaje + "%): $" + ganancia
                    + "\nNuevo total: $" + nuevoTotal
                    + "\nNuevo disponible: $" + nuevoDisponible
            );
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al aplicar ganancias: " + e.getMessage());
        }
    }

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();

        // Importante: usar null layout
        movi.setBounds(132, 200, 150, 250);
        trans.setVisible(false);
        jButton2.setLayout(null);
        panelinicio.setLayout(null);
        TextoLogin.setBackground(new java.awt.Color(0, 0, 0, 0)); // Fondo 100% transparente
        TextoLogin.setBorder(null);
        trans.setBackground(new Color(255, 255, 255, 120));
        disponible_l.setBounds(25, 75, 150, 20);
        total_l.setBounds(25, 130, 150, 20);
        guardada_l.setBounds(45, 290, 150, 20);

        apodo_lb.setBounds(80, 180, 140, 40);
        nombre_lb.setBounds(80, 240, 140, 40);
        apellido_lb.setBounds(80, 292, 140, 40);
        id_lb.setBounds(80, 347, 140, 40);
        correo_lb.setBounds(78, 403, 150, 40);

        mas_eventos.setBounds(30, 250, 230, 300);
        mas_eventos.setBorder(null);
        mas_eventos.setBackground(new java.awt.Color(0, 0, 0, 0));

        mas_cajitas.setBounds(40, 130, 50, 50);
        mas_cajitas.setBorder(null);
        mas_cajitas.setBackground(new java.awt.Color(0, 0, 0, 0));

        mas_metas.setBounds(120, 130, 50, 50);
        mas_metas.setBorder(null);
        mas_metas.setBackground(new java.awt.Color(0, 0, 0, 0));

        mas_inversiones.setBounds(200, 130, 50, 50);
        mas_inversiones.setBorder(null);
        mas_inversiones.setBackground(new java.awt.Color(0, 0, 0, 0));

        mas_eventos_btn.setBounds(40, 190, 50, 50);
        mas_eventos_btn.setBorder(null);
        mas_eventos_btn.setBackground(new java.awt.Color(0, 0, 0, 0));

        mas_compra.setBounds(120, 190, 50, 50);
        mas_compra.setBorder(null);
        mas_compra.setBackground(new java.awt.Color(0, 0, 0, 0));

        mas_gana.setBounds(200, 190, 50, 50);
        mas_gana.setBorder(null);
        mas_gana.setBackground(new java.awt.Color(0, 0, 0, 0));

        volver_retirar.setBorder(null);
        volver_retirar.setBackground(new java.awt.Color(0, 0, 0, 0));

        id_retirar.setBounds(15, 100, 270, 85);
        id_retirar.setBackground(new java.awt.Color(0, 0, 0, 0));
        id_retirar.setBorder(null);
        id_retira_lb.setBounds(70, 125, 150, 35);
        id_retira_lb.setBorder(null);

        cuanto_retirar.setBounds(15, 220, 270, 85);
        cuanto_retirar.setBorder(null);
        cuanto_retirar.setBackground(new java.awt.Color(0, 0, 0, 0));
        cuanto_retira_lb.setBounds(40, 243, 240, 35);
        cuanto_retira_lb.setBorder(null);

        retirar_btn.setBounds(15, 500, 270, 90);
        retirar_btn.setBorder(null);
        retirar_btn.setBackground(new java.awt.Color(0, 0, 0, 0));

        volver_recargar.setBorder(null);
        volver_recargar.setBackground(new java.awt.Color(0, 0, 0, 0));

        id_recarga.setBounds(15, 100, 270, 85);
        id_recarga.setBorder(null);
        id_recarga.setBackground(new java.awt.Color(0, 0, 0, 0));
        id_recarga_lb.setBounds(70, 125, 150, 35);
        id_recarga_lb.setBorder(null);

        cuanto_recarga.setBounds(15, 220, 270, 85);
        cuanto_recarga.setBorder(null);
        cuanto_recarga.setBackground(new java.awt.Color(0, 0, 0, 0));
        cuanto_recarga_lb.setBounds(40, 243, 240, 35);
        cuanto_recarga_lb.setBorder(null);

        recargar_btn.setBounds(15, 500, 270, 90);
        recargar_btn.setBorder(null);
        recargar_btn.setBackground(new java.awt.Color(0, 0, 0, 0));

        noti_info_volver.setBounds(55, 400, 185, 70);
        noti_info_volver.setBorder(null);
        noti_info_volver.setBackground(new java.awt.Color(0, 0, 0, 0));

        noti_info_parrafo.setBounds(30, 60, 240, 300);
        noti_info_titulo.setBounds(20, 20, 270, 40);

        noti_info.setBounds(0, 621, 300, 550);//550
        noti_evento.setBounds(15, 150, 285, 80);

        volver_noti.setBorder(null);
        volver_noti.setBackground(new java.awt.Color(0, 0, 0, 0));

        noti_evento.setBackground(new java.awt.Color(0, 0, 0, 0));
        noti_evento.setBorder(null);

        noti_final.setBounds(15, 215, 285, 80);
        noti_final.setBackground(new java.awt.Color(0, 0, 0, 0));
        noti_final.setBorder(null);

        noti_winner.setBounds(15, 310, 285, 80);
        noti_winner.setBackground(new java.awt.Color(0, 0, 0, 0));
        noti_winner.setBorder(null);

        noti_evento2.setBounds(15, 375, 285, 80);
        noti_evento2.setBackground(new java.awt.Color(0, 0, 0, 0));
        noti_evento2.setBorder(null);

        noti_metas.setBounds(15, 440, 285, 80);
        noti_metas.setBackground(new java.awt.Color(0, 0, 0, 0));
        noti_metas.setBorder(null);

        volver_preuntas.setBorder(null);
        volver_preuntas.setBackground(new java.awt.Color(0, 0, 0, 0));

        volver_inver.setBorder(null);
        volver_usuario.setBackground(new java.awt.Color(0, 0, 0, 0));

        cerrar_sesion.setBackground(new java.awt.Color(0, 0, 0, 0));
        cerrar_sesion.setBorder(null);

        guardar_ahorro.setBackground(new java.awt.Color(0, 0, 0, 0));
        guardar_ahorro.setBorder(null);

        volver_inver.setBackground(new java.awt.Color(0, 0, 0, 0));
        volver_inver.setBorder(null);

        check_inver_1.setBounds(45, 290, 45, 45);
        check_inver_1.setBorder(null);
        check_inver_2.setBounds(45, 325, 45, 45);
        check_inver_2.setBorder(null);
        check_inver_3.setBounds(45, 360, 45, 45);
        check_inver_3.setBorder(null);

        realizar_inver_btn.setBounds(22, 530, 260, 80);
        realizar_inver_btn.setBackground(new java.awt.Color(0, 0, 0, 0));
        realizar_inver_btn.setBorder(null);

        cuanto_inver_btn.setBounds(25, 430, 260, 80);
        cuanto_inver_btn.setBackground(new java.awt.Color(0, 0, 0, 0));
        cuanto_inver_btn.setBorder(null);

        cuanto_inver_lb.setBounds(42, 447, 240, 40);
        cuanto_inver_lb.setBorder(null);

        enviar_ac_btn.setBounds(22, 520, 260, 90);
        enviar_ac_btn.setBackground(new java.awt.Color(0, 0, 0, 0));
        enviar_ac_btn.setBorder(null);

        mensaje_envia.setBounds(20, 320, 270, 200);
        mensaje_envia.setBackground(new java.awt.Color(0, 0, 0, 0));
        mensaje_envia.setBorder(null);
        Mensaje_enviaT.setBounds(50, 338, 210, 160);
        Mensaje_enviaT.setBorder(null);

        cuanto_envia_btn.setBounds(20, 220, 260, 80);
        cuanto_envia_btn.setBackground(new java.awt.Color(0, 0, 0, 0));
        cuanto_envia_btn.setBorder(null);
        cuanto_envia_lb.setBounds(50, 238, 220, 40);
        cuanto_envia_lb.setBorder(null);

        id_envia_btn.setBounds(20, 100, 260, 80);
        id_envia_btn.setBackground(new java.awt.Color(0, 0, 0, 0));
        id_envia_btn.setBorder(null);
        id_envia_lb.setBounds(70, 118, 155, 40);
        id_envia_lb.setBorder(null);

        reti_btn.setBounds(90, 20, 55, 60);
        reti_btn.setBackground(new java.awt.Color(0, 0, 0, 0));
        reti_btn.setBorder(null);
        reti_btn.setContentAreaFilled(false);
        reti_btn.setFocusPainted(false);
        reti_btn.setBorderPainted(false);
        reti_btn.setOpaque(false);
        retira.setBounds(15, 25, 70, 40);
        retira.setBorder(null);

        pide_btn.setBounds(90, 75, 55, 60);
        pide_btn.setBackground(new java.awt.Color(0, 0, 0, 0));
        pide_btn.setBorder(null);
        pide.setBounds(35, 80, 70, 40);
        pide.setBorder(null);

        envia_btn.setBounds(90, 130, 55, 60);
        envia_btn.setBackground(new java.awt.Color(0, 0, 0, 0));
        envia_btn.setBorder(null);
        envia.setBounds(22, 135, 70, 40);
        envia.setBorder(null);

        recarga_btn.setBounds(90, 185, 55, 60);
        recarga_btn.setBackground(new java.awt.Color(0, 0, 0, 0));
        recarga_btn.setBorder(null);
        recarga_btn.setContentAreaFilled(false);
        recarga_btn.setFocusPainted(false);
        recarga_btn.setBorderPainted(false);
        recarga_btn.setOpaque(false);
        recarga.setBounds(3, 193, 100, 40);
        recarga.setBorder(null);

        volver_envia.setBackground(new java.awt.Color(0, 0, 0, 0)); // Fondo 100% transparente
        volver_envia.setBorder(null);

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

        getContentPane().add(panel_movimientos);
        getContentPane().add(panel_mas);
        getContentPane().add(panel_retirar);
        getContentPane().add(panel_recarga);
        getContentPane().add(panel_notificacion);
        getContentPane().add(panel_preguntas);
        getContentPane().add(panel_usuario);
        getContentPane().add(panel_ahorros);
        getContentPane().add(panel_inversiones);
        getContentPane().add(panel_envia);
        getContentPane().add(panel_static);
        getContentPane().add(panel_movimientos);
        getContentPane().add(panel_mas);
        getContentPane().add(panelinicio);

        panel_movimientos.setBounds(300, 0, 300, 621);
        panel_mas.setBounds(600, 0, 300, 621);
        panel_retirar.setBounds(0, -621, 300, 621);
        panel_recarga.setBounds(0, 621, 300, 621);
        panel_notificacion.setBounds(-300, 0, 300, 621);
        panel_preguntas.setBounds(-300, 0, 300, 621);
        panel_usuario.setBounds(300, 0, 300, 621);
        panel_ahorros.setBounds(0, 641, 300, 300);//341
        panel_inversiones.setBounds(-300, 0, 300, 621);
        panelinicio.setBounds(0, 0, 300, 621);
        PanelLogin.setBounds(-300, 0, 300, 621);
        PanelRegis.setBounds(300, 0, 300, 621);
        Panelcuenta.setBounds(-300, 0, 300, 621);
        panel_static.setBounds(-255, 530, 210, 60);
        panel_cajitas.setBounds(300, 0, 300, 621);
        panel_envia.setBounds(-300, 0, 300, 621);
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

    public void transsacciones(String id_origen, String id_destino, int valor, String mode) {
        Connection con = Conexion.getConnection();

        int dispoOrigen = 0, tolOrigen = 0, guarOrigen = 0;
        int dispoDestino = 0, tolDestino = 0, guarDestino = 0;

        try {
            // Leer datos del origen
            String sql = "SELECT cantidad_total, cantidad_disponible, cantidad_guardada FROM ingresos WHERE id = ?";
            PreparedStatement psOrigen = con.prepareStatement(sql);
            psOrigen.setString(1, id_origen);
            ResultSet rsOrigen = psOrigen.executeQuery();

            if (!rsOrigen.next()) {
                JOptionPane.showMessageDialog(Panelcuenta, "ID de origen no encontrado.");

            }

            tolOrigen = rsOrigen.getInt("cantidad_total");
            dispoOrigen = rsOrigen.getInt("cantidad_disponible");
            guarOrigen = rsOrigen.getInt("cantidad_guardada");

            switch (mode.toLowerCase()) {
                case "guardar":
                    if (dispoOrigen < valor) {
                        JOptionPane.showMessageDialog(Panelcuenta, "No hay suficiente disponible para guardar.");

                    } else {
                        dispoOrigen -= valor;
                        guarOrigen += valor;
                        tolOrigen = dispoOrigen + guarOrigen;
                    }
                    break;
                case "recargar":
                    dispoOrigen += valor;
                    tolOrigen = dispoOrigen + guarOrigen;
                    break;
                case "retirar":
                    if (dispoOrigen < valor) {
                        JOptionPane.showMessageDialog(Panelcuenta, "No hay suficiente disponible para reitrar.");

                    } else {
                        dispoOrigen -= valor;
                        tolOrigen = dispoOrigen + guarOrigen;
                    }
                    break;

                case "enviar":
                    if (id_destino == null) {
                        JOptionPane.showMessageDialog(Panelcuenta, "ID de destino requerido para enviar.");

                    }
                    if (dispoOrigen < valor) {
                        JOptionPane.showMessageDialog(Panelcuenta, "Fondos insuficientes para enviar.");
                    } else {

                        // Leer datos del destino
                        PreparedStatement psDestino = con.prepareStatement(sql);
                        psDestino.setString(1, id_destino);
                        ResultSet rsDestino = psDestino.executeQuery();

                        if (!rsDestino.next()) {
                            JOptionPane.showMessageDialog(Panelcuenta, "ID de destino no encontrado.");

                        }

                        tolDestino = rsDestino.getInt("cantidad_total");
                        dispoDestino = rsDestino.getInt("cantidad_disponible");
                        guarDestino = rsDestino.getInt("cantidad_guardada");

                        // Modificar valores
                        dispoOrigen -= valor;
                        tolOrigen = dispoOrigen + guarOrigen;

                        dispoDestino += valor;
                        tolDestino = dispoDestino + guarDestino;

                        // Actualizar destino
                        String update = "UPDATE ingresos SET cantidad_total = ?, cantidad_disponible = ?, cantidad_guardada = ? WHERE id = ?";
                        PreparedStatement psUpdateDestino = con.prepareStatement(update);
                        psUpdateDestino.setInt(1, tolDestino);
                        psUpdateDestino.setInt(2, dispoDestino);
                        psUpdateDestino.setInt(3, guarDestino);
                        psUpdateDestino.setString(4, id_destino);
                        psUpdateDestino.executeUpdate();

                    }
                    break;

                default:
                    JOptionPane.showMessageDialog(Panelcuenta, "Modo de transacción no reconocido.");
            }

            // Actualizar origen
            String update = "UPDATE ingresos SET cantidad_total = ?, cantidad_disponible = ?, cantidad_guardada = ? WHERE id = ?";
            PreparedStatement psUpdateOrigen = con.prepareStatement(update);
            psUpdateOrigen.setInt(1, tolOrigen);
            psUpdateOrigen.setInt(2, dispoOrigen);
            psUpdateOrigen.setInt(3, guarOrigen);
            psUpdateOrigen.setString(4, id_origen);
            psUpdateOrigen.executeUpdate();
            JOptionPane.showMessageDialog(Panelcuenta, "Transacción '" + mode + "' realizada correctamente.");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
        try {
            // Obtener nombre del usuario origen
            String nombre = "";
            String nombreSql = "SELECT nombre FROM usuarios WHERE id = ?";
            PreparedStatement psNombre = con.prepareStatement(nombreSql);
            psNombre.setString(1, id_origen);
            ResultSet rsNombre = psNombre.executeQuery();
            if (rsNombre.next()) {
                nombre = rsNombre.getString("nombre");
            }
            rsNombre.close();
            psNombre.close();

            // Registrar transacción
            String sqlInsert = "INSERT INTO transacciones (id_usuario, nombre, tipo, valor, destino, fecha) VALUES (?, ?, ?, ?, ?, NOW())";
            PreparedStatement psLog = con.prepareStatement(sqlInsert);
            psLog.setString(1, id_origen);
            psLog.setString(2, nombre);
            psLog.setString(3, mode);
            psLog.setInt(4, valor);

            if (mode.equalsIgnoreCase("enviar")) {
                psLog.setString(5, id_destino);
            } else {
                psLog.setNull(5, java.sql.Types.VARCHAR);
            }

            psLog.executeUpdate();
            psLog.close();

        } catch (SQLException e) {
            System.out.println("Error al registrar la transacción: " + e.getMessage());
        }
    }

    public void plata() {
        String idBuscado = TextoLogin.getText();
        try (Connection con = Conexion.getConnection()) {

            String total = "SELECT id,cantidad_total FROM ingresos WHERE id = ?";
            String disponible = "SELECT id,cantidad_disponible FROM ingresos WHERE id = ?";
            String guarda = "SELECT id,cantidad_guardada FROM ingresos WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(total);
            ps.setString(1, idBuscado);

            PreparedStatement ps2 = con.prepareStatement(disponible);
            ps2.setString(1, idBuscado);

            PreparedStatement ps3 = con.prepareStatement(guarda);
            ps3.setString(1, idBuscado);

            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            ResultSet rs3 = ps3.executeQuery();

            if (rs.next() && rs2.next() && rs3.next()) {
                String total_num = rs.getString("cantidad_total");
                String dispo_num = rs2.getString("cantidad_disponible");
                String guarda_num = rs3.getString("cantidad_guardada");
                disponible_l.setText("Đ" + dispo_num);
                total_l.setText("Đ" + total_num);
                guardada_l.setText("Đ" + guarda_num);
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

        panel_mas = new fondomas();
        mas_cajitas = new javax.swing.JButton();
        mas_metas = new javax.swing.JButton();
        mas_inversiones = new javax.swing.JButton();
        mas_eventos_btn = new javax.swing.JButton();
        mas_compra = new javax.swing.JButton();
        mas_gana = new javax.swing.JButton();
        mas_eventos = new javax.swing.JButton();
        panel_movimientos = new fondomovimientos();
        panel_retirar = new fondoretira();
        cuanto_retira_lb = new javax.swing.JTextField();
        id_retira_lb = new javax.swing.JTextField();
        cuanto_retirar = new javax.swing.JButton();
        id_retirar = new javax.swing.JButton();
        retirar_btn = new javax.swing.JButton();
        volver_retirar = new javax.swing.JButton();
        panel_notificacion = new fondonoti();
        noti_info = new fondonotiinfo();
        noti_info_titulo = new javax.swing.JLabel();
        noti_info_parrafo = new javax.swing.JLabel();
        noti_info_volver = new javax.swing.JButton();
        volver_noti = new javax.swing.JButton();
        noti_evento = new javax.swing.JButton();
        noti_final = new javax.swing.JButton();
        noti_winner = new javax.swing.JButton();
        noti_evento2 = new javax.swing.JButton();
        noti_metas = new javax.swing.JButton();
        panel_recarga = new fondorecarga();
        cuanto_recarga_lb = new javax.swing.JTextField();
        id_recarga_lb = new javax.swing.JTextField();
        id_recarga = new javax.swing.JButton();
        cuanto_recarga = new javax.swing.JButton();
        recargar_btn = new javax.swing.JButton();
        volver_recargar = new javax.swing.JButton();
        panel_preguntas = new fondopreguntas();
        volver_preuntas = new javax.swing.JButton();
        panel_usuario = new fondousuario();
        volver_usuario = new javax.swing.JButton();
        cerrar_sesion = new javax.swing.JButton();
        apodo_lb = new javax.swing.JLabel();
        nombre_lb = new javax.swing.JLabel();
        apellido_lb = new javax.swing.JLabel();
        id_lb = new javax.swing.JLabel();
        correo_lb = new javax.swing.JLabel();
        panel_inversiones = new fondoinversiones();
        cuanto_inver_lb = new javax.swing.JTextField();
        realizar_inver_btn = new javax.swing.JButton();
        cuanto_inver_btn = new javax.swing.JButton();
        check_inver_1 = new javax.swing.JCheckBox();
        check_inver_2 = new javax.swing.JCheckBox();
        check_inver_3 = new javax.swing.JCheckBox();
        volver_inver = new javax.swing.JButton();
        panel_envia = new fondoenvia();
        cuanto_envia_lb = new javax.swing.JTextField();
        id_envia_lb = new javax.swing.JTextField();
        Mensaje_enviaT = new javax.swing.JScrollPane(mensaje_envia_at,Mensaje_enviaT.VERTICAL_SCROLLBAR_NEVER,Mensaje_enviaT.HORIZONTAL_SCROLLBAR_NEVER);
        mensaje_envia_at = new javax.swing.JTextArea();
        cuanto_envia_btn = new javax.swing.JButton();
        id_envia_btn = new javax.swing.JButton();
        mensaje_envia = new javax.swing.JButton();
        enviar_ac_btn = new javax.swing.JButton();
        volver_envia = new javax.swing.JButton();
        panel_ahorros = new fondoahorro();
        guardar_ahorro = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
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
        guardada_l = new javax.swing.JLabel();
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
        total_l = new javax.swing.JLabel();
        disponible_l = new javax.swing.JLabel();
        panel_static = new fondostatic();
        casa_btn = new javax.swing.JButton();
        trans_btn = new javax.swing.JButton();
        mas_btn = new javax.swing.JButton();
        pass = new fondopass();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DUNAB+");
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(300, 621));
        setMinimumSize(new java.awt.Dimension(300, 621));
        setResizable(false);

        panel_mas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_masMouseClicked(evt);
            }
        });
        panel_mas.setLayout(null);

        mas_cajitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mas_cajitas.png"))); // NOI18N
        panel_mas.add(mas_cajitas);
        mas_cajitas.setBounds(20, 140, 61, 50);

        mas_metas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/descarga.png"))); // NOI18N
        mas_metas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mas_metasMouseClicked(evt);
            }
        });
        panel_mas.add(mas_metas);
        mas_metas.setBounds(110, 140, 70, 50);

        mas_inversiones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mas_inversiones.png"))); // NOI18N
        panel_mas.add(mas_inversiones);
        mas_inversiones.setBounds(110, 210, 61, 50);

        mas_eventos_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mas_eventos.png"))); // NOI18N
        mas_eventos_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mas_eventos_btnMouseClicked(evt);
            }
        });
        mas_eventos_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mas_eventos_btnActionPerformed(evt);
            }
        });
        panel_mas.add(mas_eventos_btn);
        mas_eventos_btn.setBounds(190, 210, 61, 50);

        mas_compra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mas_compra.png"))); // NOI18N
        mas_compra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mas_compraMouseClicked(evt);
            }
        });
        panel_mas.add(mas_compra);
        mas_compra.setBounds(190, 140, 61, 50);

        mas_gana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mas_gana.png"))); // NOI18N
        mas_gana.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mas_ganaMouseClicked(evt);
            }
        });
        panel_mas.add(mas_gana);
        mas_gana.setBounds(20, 210, 75, 50);

        mas_eventos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/panel_mas_eventos.png")));
        mas_eventos.setToolTipText("");
        panel_mas.add(mas_eventos);
        mas_eventos.setBounds(140, 310, 72, 7);

        panel_movimientos.setLayout(null);

        panel_retirar.setLayout(null);

        cuanto_retira_lb.setBackground(new java.awt.Color(240, 240, 240));
        cuanto_retira_lb.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        cuanto_retira_lb.setForeground(new java.awt.Color(233, 164, 39));
        cuanto_retira_lb.setText("¿Cuanto?");
        cuanto_retira_lb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cuanto_retira_lbFocusGained(evt);
            }
        });
        cuanto_retira_lb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuanto_retira_lbMouseClicked(evt);
            }
        });
        cuanto_retira_lb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuanto_retira_lbActionPerformed(evt);
            }
        });
        panel_retirar.add(cuanto_retira_lb);
        cuanto_retira_lb.setBounds(120, 60, 94, 28);

        id_retira_lb.setBackground(new java.awt.Color(233, 164, 39));
        id_retira_lb.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        id_retira_lb.setForeground(new java.awt.Color(255, 255, 255));
        panel_retirar.add(id_retira_lb);
        id_retira_lb.setBounds(130, 30, 64, 28);

        cuanto_retirar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cuanto_recarga.png"))); // NOI18N
        cuanto_retirar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuanto_retirarMouseClicked(evt);
            }
        });
        panel_retirar.add(cuanto_retirar);
        cuanto_retirar.setBounds(170, 130, 269, 20);

        id_retirar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/id_recargar_btn.png"))); // NOI18N
        panel_retirar.add(id_retirar);
        id_retirar.setBounds(170, 180, 75, 23);

        retirar_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/retirar_btn.png"))); // NOI18N
        retirar_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                retirar_btnMouseClicked(evt);
            }
        });
        panel_retirar.add(retirar_btn);
        retirar_btn.setBounds(170, 220, 264, 20);

        volver_retirar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volver_retirarMouseClicked(evt);
            }
        });
        volver_retirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volver_retirarActionPerformed(evt);
            }
        });
        panel_retirar.add(volver_retirar);
        volver_retirar.setBounds(20, 20, 72, 30);

        panel_notificacion.setLayout(null);

        noti_info.setLayout(null);

        noti_info_titulo.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        noti_info.add(noti_info_titulo);
        noti_info_titulo.setBounds(20, 20, 0, 0);

        noti_info_parrafo.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        noti_info_parrafo.setText("El CSU se complace en invitar a toda\\n la comunidad a su gran evento deportivo, una jornada dedicada a la competencia, la integración y el espíritu atlético. Durante este día, se llevarán a cabo diversas actividades y torneos en los que equipos y participantes demostrarán su talento, disciplina y trabajo en equipo. Este encuentro busca promover la sana convivencia y el amor por el deporte en un ambiente lleno de energía y entusiasmo.");
        noti_info.add(noti_info_parrafo);
        noti_info_parrafo.setBounds(20, 40, 2826, 18);

        noti_info_volver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/noti_info_volver.png"))); // NOI18N
        noti_info_volver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noti_info_volverMouseClicked(evt);
            }
        });
        noti_info.add(noti_info_volver);
        noti_info_volver.setBounds(20, 40, 189, 70);

        panel_notificacion.add(noti_info);
        noti_info.setBounds(209, 39, 120, 70);

        volver_noti.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volver_notiMouseClicked(evt);
            }
        });
        panel_notificacion.add(volver_noti);
        volver_noti.setBounds(10, 60, 72, 30);

        noti_evento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/noti_evento.png"))); // NOI18N
        noti_evento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noti_eventoMouseClicked(evt);
            }
        });
        panel_notificacion.add(noti_evento);
        noti_evento.setBounds(90, 160, 286, 79);

        noti_final.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/noti_finas.png"))); // NOI18N
        noti_final.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noti_finalMouseClicked(evt);
            }
        });
        panel_notificacion.add(noti_final);
        noti_final.setBounds(90, 200, 286, 79);

        noti_winner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/noti_winner.png"))); // NOI18N
        noti_winner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noti_winnerMouseClicked(evt);
            }
        });
        panel_notificacion.add(noti_winner);
        noti_winner.setBounds(90, 260, 286, 79);

        noti_evento2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/noti_evento2.png"))); // NOI18N
        noti_evento2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noti_evento2MouseClicked(evt);
            }
        });
        panel_notificacion.add(noti_evento2);
        noti_evento2.setBounds(90, 330, 286, 79);

        noti_metas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/notis_meta.png"))); // NOI18N
        noti_metas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noti_metasMouseClicked(evt);
            }
        });
        noti_metas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noti_metasActionPerformed(evt);
            }
        });
        panel_notificacion.add(noti_metas);
        noti_metas.setBounds(90, 370, 286, 79);

        panel_recarga.setLayout(null);

        cuanto_recarga_lb.setBackground(new java.awt.Color(240, 240, 240));
        cuanto_recarga_lb.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        cuanto_recarga_lb.setForeground(new java.awt.Color(233, 164, 39));
        cuanto_recarga_lb.setText("¿Cuanto?");
        cuanto_recarga_lb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cuanto_recarga_lbFocusGained(evt);
            }
        });
        cuanto_recarga_lb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuanto_recarga_lbMouseClicked(evt);
            }
        });
        cuanto_recarga_lb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuanto_recarga_lbActionPerformed(evt);
            }
        });
        panel_recarga.add(cuanto_recarga_lb);
        cuanto_recarga_lb.setBounds(120, 60, 94, 28);

        id_recarga_lb.setBackground(new java.awt.Color(233, 164, 39));
        id_recarga_lb.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        id_recarga_lb.setForeground(new java.awt.Color(255, 255, 255));
        panel_recarga.add(id_recarga_lb);
        id_recarga_lb.setBounds(130, 30, 64, 28);

        id_recarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/id_recargar_btn.png"))); // NOI18N
        panel_recarga.add(id_recarga);
        id_recarga.setBounds(150, 230, 269, 81);

        cuanto_recarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cuanto_recarga.png"))); // NOI18N
        panel_recarga.add(cuanto_recarga);
        cuanto_recarga.setBounds(130, 140, 269, 70);

        recargar_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/recargar_btn.png"))); // NOI18N
        recargar_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recargar_btnMouseClicked(evt);
            }
        });
        panel_recarga.add(recargar_btn);
        recargar_btn.setBounds(140, 190, 264, 88);

        volver_recargar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volver_recargarMouseClicked(evt);
            }
        });
        volver_recargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volver_recargarActionPerformed(evt);
            }
        });
        panel_recarga.add(volver_recargar);
        volver_recargar.setBounds(20, 20, 75, 23);

        panel_preguntas.setLayout(null);

        volver_preuntas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volver_preuntasMouseClicked(evt);
            }
        });
        panel_preguntas.add(volver_preuntas);
        volver_preuntas.setBounds(0, 20, 72, 30);

        panel_usuario.setMaximumSize(new java.awt.Dimension(300, 621));
        panel_usuario.setMinimumSize(new java.awt.Dimension(300, 621));
        panel_usuario.setPreferredSize(new java.awt.Dimension(300, 621));
        panel_usuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_usuarioMouseClicked(evt);
            }
        });
        panel_usuario.setLayout(null);

        volver_usuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volver_usuarioMouseClicked(evt);
            }
        });
        panel_usuario.add(volver_usuario);
        volver_usuario.setBounds(10, 40, 75, 23);

        cerrar_sesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar_sesion_btn.png"))); // NOI18N
        cerrar_sesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cerrar_sesionMouseClicked(evt);
            }
        });
        cerrar_sesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrar_sesionActionPerformed(evt);
            }
        });
        panel_usuario.add(cerrar_sesion);
        cerrar_sesion.setBounds(40, 530, 220, 70);

        apodo_lb.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        apodo_lb.setForeground(new java.awt.Color(255, 255, 255));
        apodo_lb.setText("qwe");
        panel_usuario.add(apodo_lb);
        apodo_lb.setBounds(110, 270, 25, 17);

        nombre_lb.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        nombre_lb.setForeground(new java.awt.Color(255, 255, 255));
        nombre_lb.setText("qwe");
        panel_usuario.add(nombre_lb);
        nombre_lb.setBounds(110, 300, 25, 17);

        apellido_lb.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        apellido_lb.setForeground(new java.awt.Color(255, 255, 255));
        apellido_lb.setText("qwe");
        panel_usuario.add(apellido_lb);
        apellido_lb.setBounds(110, 330, 25, 17);

        id_lb.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        id_lb.setForeground(new java.awt.Color(255, 255, 255));
        id_lb.setText("qwe");
        panel_usuario.add(id_lb);
        id_lb.setBounds(100, 360, 33, 22);

        correo_lb.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        correo_lb.setForeground(new java.awt.Color(255, 255, 255));
        correo_lb.setText("qwe");
        panel_usuario.add(correo_lb);
        correo_lb.setBounds(90, 400, 25, 17);

        panel_inversiones.setMaximumSize(new java.awt.Dimension(300, 621));
        panel_inversiones.setMinimumSize(new java.awt.Dimension(300, 621));
        panel_inversiones.setPreferredSize(new java.awt.Dimension(300, 621));
        panel_inversiones.setLayout(null);

        cuanto_inver_lb.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        cuanto_inver_lb.setForeground(new java.awt.Color(233, 164, 39));
        cuanto_inver_lb.setText("¿Cuanto quieres invertir?");
        cuanto_inver_lb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cuanto_inver_lbFocusGained(evt);
            }
        });
        panel_inversiones.add(cuanto_inver_lb);
        cuanto_inver_lb.setBounds(130, 80, 234, 30);

        realizar_inver_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/realizar_inver_btn.png"))); // NOI18N
        realizar_inver_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                realizar_inver_btnMouseClicked(evt);
            }
        });
        realizar_inver_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realizar_inver_btnActionPerformed(evt);
            }
        });
        panel_inversiones.add(realizar_inver_btn);
        realizar_inver_btn.setBounds(170, 200, 264, 88);

        cuanto_inver_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cuanto_envia.png"))); // NOI18N
        panel_inversiones.add(cuanto_inver_btn);
        cuanto_inver_btn.setBounds(120, 300, 269, 70);

        check_inver_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check_inver.png"))); // NOI18N
        check_inver_1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                check_inver_1MouseClicked(evt);
            }
        });
        check_inver_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_inver_1ActionPerformed(evt);
            }
        });
        panel_inversiones.add(check_inver_1);
        check_inver_1.setBounds(80, 500, 20, 34);

        check_inver_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check_inver.png"))); // NOI18N
        check_inver_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                check_inver_2MouseClicked(evt);
            }
        });
        panel_inversiones.add(check_inver_2);
        check_inver_2.setBounds(110, 540, 34, 34);

        check_inver_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check_inver.png"))); // NOI18N
        check_inver_3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                check_inver_3MouseClicked(evt);
            }
        });
        panel_inversiones.add(check_inver_3);
        check_inver_3.setBounds(110, 570, 20, 34);

        volver_inver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volver_inverMouseClicked(evt);
            }
        });
        panel_inversiones.add(volver_inver);
        volver_inver.setBounds(20, 20, 75, 23);

        panel_envia.setPreferredSize(new java.awt.Dimension(300, 621));
        panel_envia.setLayout(null);

        cuanto_envia_lb.setBackground(new java.awt.Color(255, 255, 255));
        cuanto_envia_lb.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        cuanto_envia_lb.setForeground(new java.awt.Color(233, 164, 39));
        cuanto_envia_lb.setText("¿Cuanto?");
        cuanto_envia_lb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cuanto_envia_lbFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cuanto_envia_lbFocusLost(evt);
            }
        });
        cuanto_envia_lb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuanto_envia_lbMouseClicked(evt);
            }
        });
        cuanto_envia_lb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cuanto_envia_lbKeyPressed(evt);
            }
        });
        panel_envia.add(cuanto_envia_lb);
        cuanto_envia_lb.setBounds(100, 130, 100, 30);

        id_envia_lb.setBackground(new java.awt.Color(233, 164, 39));
        id_envia_lb.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        id_envia_lb.setForeground(new java.awt.Color(255, 255, 255));
        panel_envia.add(id_envia_lb);
        id_envia_lb.setBounds(100, 90, 64, 30);

        mensaje_envia_at.setColumns(20);
        mensaje_envia_at.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        mensaje_envia_at.setForeground(new java.awt.Color(233, 164, 39));
        mensaje_envia_at.setRows(5);
        mensaje_envia_at.setText("Mensaje");
        mensaje_envia_at.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mensaje_envia_atFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                mensaje_envia_atFocusLost(evt);
            }
        });
        mensaje_envia_at.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mensaje_envia_atKeyReleased(evt);
            }
        });
        Mensaje_enviaT.setViewportView(mensaje_envia_at);

        panel_envia.add(Mensaje_enviaT);
        Mensaje_enviaT.setBounds(30, 10, 274, 111);

        cuanto_envia_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cuanto_envia.png"))); // NOI18N
        panel_envia.add(cuanto_envia_btn);
        cuanto_envia_btn.setBounds(80, 350, 269, 70);

        id_envia_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/bnt_id_envia.png"))); // NOI18N
        panel_envia.add(id_envia_btn);
        id_envia_btn.setBounds(80, 320, 269, 81);

        mensaje_envia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mensaje_envia.png"))); // NOI18N
        panel_envia.add(mensaje_envia);
        mensaje_envia.setBounds(120, 350, 267, 196);

        enviar_ac_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/envia_ac_btn.png"))); // NOI18N
        enviar_ac_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                enviar_ac_btnMouseClicked(evt);
            }
        });
        panel_envia.add(enviar_ac_btn);
        enviar_ac_btn.setBounds(120, 200, 264, 88);

        volver_envia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volver_enviaMouseClicked(evt);
            }
        });
        volver_envia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volver_enviaActionPerformed(evt);
            }
        });
        panel_envia.add(volver_envia);
        volver_envia.setBounds(10, 20, 72, 30);

        panel_ahorros.setLayout(null);

        guardar_ahorro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar_ahorros.png"))); // NOI18N
        guardar_ahorro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guardar_ahorroMouseClicked(evt);
            }
        });
        panel_ahorros.add(guardar_ahorro);
        guardar_ahorro.setBounds(50, 180, 190, 80);
        panel_ahorros.add(jSlider1);
        jSlider1.setBounds(50, 110, 200, 20);

        jLabel1.setText("? Đ");
        panel_ahorros.add(jLabel1);
        jLabel1.setBounds(250, 130, 30, 16);

        jLabel2.setText("0 Đ");
        panel_ahorros.add(jLabel2);
        jLabel2.setBounds(30, 130, 37, 16);

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
        reti_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reti_btnMouseClicked(evt);
            }
        });
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
        envia_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                envia_btnMouseClicked(evt);
            }
        });
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
        recarga_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recarga_btnMouseClicked(evt);
            }
        });
        movi.add(recarga_btn);
        recarga_btn.setBounds(120, 220, 51, 65);

        trans.add(movi);
        movi.setBounds(90, 190, 0, 0);

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

        guardada_l.setBackground(new java.awt.Color(0, 0, 255));
        guardada_l.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        guardada_l.setForeground(new java.awt.Color(255, 255, 255));
        panel_cajitas.add(guardada_l);
        guardada_l.setBounds(90, 340, 0, 0);

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
        jCheckBox1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check.png")));
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
        metas_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                metas_btnMouseClicked(evt);
            }
        });
        Panelcuenta.add(metas_btn);
        metas_btn.setBounds(20, 310, 64, 70);

        inver_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inverciones_btn.png"))); // NOI18N
        inver_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inver_btnMouseClicked(evt);
            }
        });
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
        usuario_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usuario_btnMouseClicked(evt);
            }
        });
        Panelcuenta.add(usuario_btn);
        usuario_btn.setBounds(160, 50, 62, 63);

        sig_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sig_btn.png"))); // NOI18N
        sig_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sig_btnMouseClicked(evt);
            }
        });
        Panelcuenta.add(sig_btn);
        sig_btn.setBounds(170, 110, 60, 57);

        noti_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/noti_btn.png"))); // NOI18N
        noti_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noti_btnMouseClicked(evt);
            }
        });
        Panelcuenta.add(noti_btn);
        noti_btn.setBounds(90, 70, 62, 60);

        total_l.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        total_l.setForeground(new java.awt.Color(255, 255, 255));
        Panelcuenta.add(total_l);
        total_l.setBounds(80, 220, 0, 0);

        disponible_l.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        disponible_l.setForeground(new java.awt.Color(255, 255, 255));
        Panelcuenta.add(disponible_l);
        disponible_l.setBounds(100, 260, 0, 0);

        panel_static.setLayout(null);

        casa_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/casa_btn.png"))); // NOI18N
        casa_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                casa_btnMouseClicked(evt);
            }
        });
        panel_static.add(casa_btn);
        casa_btn.setBounds(0, 0, 39, 40);

        trans_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/trans_btn.png"))); // NOI18N
        trans_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trans_btnMouseClicked(evt);
            }
        });
        trans_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trans_btnActionPerformed(evt);
            }
        });
        panel_static.add(trans_btn);
        trans_btn.setBounds(0, 0, 36, 38);

        mas_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mas_btn.png"))); // NOI18N
        mas_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mas_btnMouseClicked(evt);
            }
        });
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
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_envia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_inversiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_ahorros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_preguntas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_notificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_recarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_retirar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_mas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_movimientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_envia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_inversiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_ahorros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_preguntas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_notificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_recarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_retirar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_mas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_movimientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        plata();
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
        int ingreso_incia = 0;
        if (name.equals("") || ide.equals("") || gmail.equals("") || pass.equals("") || apodo.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Empty entries, please check again", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                Connection conn = Conexion.getConnection();
                if (conn != null) {
                    String sql_usuarios = "INSERT INTO usuarios (id,nombre,apodo,correo,contrasena) VALUES (?, ?, ?, ?, ?)";
                    String sql_ingresos = "INSERT INTO ingresos (id,cantidad_total,cantidad_disponible,cantidad_guardada) VALUES (?, ?,?,?)";

                    PreparedStatement stmt = conn.prepareStatement(sql_usuarios);
                    PreparedStatement stmt2 = conn.prepareStatement(sql_ingresos);
                    stmt2.setString(1, ide);
                    stmt2.setInt(2, ingreso_incia);
                    stmt2.setInt(3, ingreso_incia);
                    stmt2.setInt(4, ingreso_incia);

                    stmt.setString(1, ide);
                    stmt.setString(2, name);
                    stmt.setString(3, apodo);
                    stmt.setString(4, gmail);
                    stmt.setString(5, pass);
                    stmt2.executeUpdate();
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
        if (guardar.getText().equals("¿Cuanto quieres guardar?")) {
            transsacciones(TextoLogin.getText(), null, -Integer.parseInt(retiro.getText()), "guardar");
        } else if (retiro.getText().equals("¿Cuanto quieres retirar?")) {
            transsacciones(TextoLogin.getText(), null, Integer.parseInt(guardar.getText()), "guardar");

        } else {
            transsacciones(TextoLogin.getText(), null, -Integer.parseInt(retiro.getText()), "guardar");
            transsacciones(TextoLogin.getText(), null, Integer.parseInt(retiro.getText()), "guardar");
        }

        guardar.setText("¿Cuanto quieres guardar?");
        retiro.setText("¿Cuanto quieres retirar?");
        plata();
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

    private void envia_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_envia_btnMouseClicked
        // TODO add your handling code here:
        trans.setVisible(false);
        Der(panel_envia, 10, 5, 0);
    }//GEN-LAST:event_envia_btnMouseClicked

    private void mensaje_envia_atKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mensaje_envia_atKeyReleased
        // TODO add your handling code here:
        String text = mensaje_envia_at.getText();
        StringBuilder modified = new StringBuilder();
        int count = 0;
        for (char c : text.toCharArray()) {
            if (c == '\n') {
                count = 0;
            }
            if (count >= 25 && c != '\n') {
                modified.append('\n');
                count = 0;
            }
            modified.append(c);
            count++;
        }
        if (!text.equals(modified.toString())) {
            mensaje_envia_at.setText(modified.toString());
        }
    }//GEN-LAST:event_mensaje_envia_atKeyReleased

    private void volver_enviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volver_enviaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_volver_enviaActionPerformed

    private void volver_enviaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volver_enviaMouseClicked
        // TODO add your handling code here:
        Izq(panel_envia, 10, 5, -300);
    }//GEN-LAST:event_volver_enviaMouseClicked

    private void mensaje_envia_atFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mensaje_envia_atFocusGained
        // TODO add your handling code here:
        mensaje_envia_at.setText("");
    }//GEN-LAST:event_mensaje_envia_atFocusGained

    private void cuanto_envia_lbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cuanto_envia_lbKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_cuanto_envia_lbKeyPressed

    private void cuanto_envia_lbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuanto_envia_lbMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cuanto_envia_lbMouseClicked

    private void cuanto_envia_lbFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cuanto_envia_lbFocusGained
        // TODO add your handling code here:
        cuanto_envia_lb.setText("");
    }//GEN-LAST:event_cuanto_envia_lbFocusGained

    private void cuanto_envia_lbFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cuanto_envia_lbFocusLost
        // TODO add your handling code here:
        if (cuanto_envia_lb.getText().equals("")) {
            cuanto_envia_lb.setText("¿Cuanto?");

        }
    }//GEN-LAST:event_cuanto_envia_lbFocusLost

    private void mensaje_envia_atFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mensaje_envia_atFocusLost
        // TODO add your handling code here:
        if (mensaje_envia_at.getText().equals("")) {
            mensaje_envia_at.setText("Mensaje");

        }
    }//GEN-LAST:event_mensaje_envia_atFocusLost

    private void enviar_ac_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_enviar_ac_btnMouseClicked
        // TODO add your handling code here:
        transsacciones(TextoLogin.getText(), id_envia_lb.getText(), Integer.parseInt(cuanto_envia_lb.getText()), "enviar");
        plata();
        id_envia_lb.setText("");
        mensaje_envia_at.setText("Mensaje");
        cuanto_envia_lb.setText("¿Cuanto?");

    }//GEN-LAST:event_enviar_ac_btnMouseClicked

    private void realizar_inver_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realizar_inver_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_realizar_inver_btnActionPerformed

    private void cuanto_inver_lbFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cuanto_inver_lbFocusGained
        // TODO add your handling code here:
        cuanto_inver_lb.setText("");
    }//GEN-LAST:event_cuanto_inver_lbFocusGained

    private void check_inver_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_inver_1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_check_inver_1ActionPerformed
    boolean a = true;
    int b = 0;
    private void check_inver_1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_check_inver_1MouseClicked
        // TODO add your handling code here:
        if (a) {
            check_inver_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check_inver_yes.png")));
            a = false;
            b++;
        } else {
            check_inver_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check_inver.png")));
            a = true;
            b--;
        }


    }//GEN-LAST:event_check_inver_1MouseClicked

    private void check_inver_2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_check_inver_2MouseClicked
        // TODO add your handling code here:
        if (a) {
            check_inver_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check_inver_yes.png")));
            a = false;
            b++;
        } else {
            check_inver_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check_inver.png")));
            a = true;
            b--;
        }
    }//GEN-LAST:event_check_inver_2MouseClicked

    private void check_inver_3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_check_inver_3MouseClicked
        // TODO add your handling code here:
        if (a) {
            check_inver_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check_inver_yes.png")));
            a = false;
            b++;
        } else {
            check_inver_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check_inver.png")));
            a = true;
            b--;

        }
    }//GEN-LAST:event_check_inver_3MouseClicked

    private void inver_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inver_btnMouseClicked
        // TODO add your handling code here:
        Der(Panelcuenta, 10, 5, 300);
        Der(panel_inversiones, 10, 5, 0);
        Der(panel_static, 10, 5, 345);

    }//GEN-LAST:event_inver_btnMouseClicked

    private void volver_inverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volver_inverMouseClicked
        // TODO add your handling code here:
        Izq(Panelcuenta, 10, 5, 0);
        Izq(panel_inversiones, 10, 5, -300);
        Izq(panel_static, 10, 5, 45);
        cuanto_inver_lb.setText("¿Cuanto quieres invertir?");
    }//GEN-LAST:event_volver_inverMouseClicked

    private void realizar_inver_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_realizar_inver_btnMouseClicked
        // TODO add your handling code here:
        int monto = Integer.parseInt(cuanto_inver_lb.getText());
        inversiones(monto);
        cuanto_inver_lb.setText("¿Cuanto quieres invertir?");
        plata();

    }//GEN-LAST:event_realizar_inver_btnMouseClicked

    private void metas_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_metas_btnMouseClicked
        // TODO add your handling code here:
        moverArriba(panel_ahorros, 10, 5, 341);


    }//GEN-LAST:event_metas_btnMouseClicked

    private void guardar_ahorroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardar_ahorroMouseClicked
        // TODO add your handling code here:
        moverAbajo(panel_ahorros, 10, 5, 641);
    }//GEN-LAST:event_guardar_ahorroMouseClicked

    private void cerrar_sesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrar_sesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cerrar_sesionActionPerformed

    private void sig_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sig_btnMouseClicked
        Der(panel_preguntas, 10, 5, 0);
        Der(Panelcuenta, 10, 5, 300);
        Der(panel_static, 10, 5, 345);


    }//GEN-LAST:event_sig_btnMouseClicked

    private void cerrar_sesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrar_sesionMouseClicked
        // TODO add your handling code here:
        panel_movimientos.setBounds(300, 0, 300, 621);
        panel_mas.setBounds(600, 0, 300, 621);
        panel_retirar.setBounds(0, -621, 300, 621);
        panel_recarga.setBounds(0, 621, 300, 621);
        panel_notificacion.setBounds(-300, 0, 300, 621);
        panel_preguntas.setBounds(-300, 0, 300, 621);
        panel_usuario.setBounds(300, 0, 300, 621);
        panel_ahorros.setBounds(0, 641, 300, 300);//341
        panel_inversiones.setBounds(-300, 0, 300, 621);
        panelinicio.setBounds(0, 0, 300, 621);
        PanelLogin.setBounds(-300, 0, 300, 621);
        PanelRegis.setBounds(300, 0, 300, 621);
        Panelcuenta.setBounds(-300, 0, 300, 621);
        panel_static.setBounds(-255, 530, 210, 60);
        panel_cajitas.setBounds(300, 0, 300, 621);
        panel_envia.setBounds(-300, 0, 300, 621);
        TextoLogin.setText("ID Unab");
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        
    }//GEN-LAST:event_cerrar_sesionMouseClicked

    private void usuario_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usuario_btnMouseClicked
        // TODO add your handling code here:
        try {
            String idP = TextoLogin.getText(); // El ID que ingresas
            Connection con = Conexion.getConnection();

            String sql = "SELECT nombre, correo, apodo FROM usuarios WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, idP);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String apodo = rs.getString("apodo");

                apodo_lb.setText(apodo);
                correo_lb.setText(correo);
                id_lb.setText(idP);
                nombre_lb.setText(nombre.substring(0, 13));
                apellido_lb.setText(nombre.substring(13, 25));
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
        Izq(panel_usuario, 10, 5, 0);
        Izq(Panelcuenta, 10, 5, -300);
        Izq(panel_static, 10, 5, -255);

    }//GEN-LAST:event_usuario_btnMouseClicked

    private void volver_usuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volver_usuarioMouseClicked
        // TODO add your handling code here:
        Der(panel_usuario, 10, 5, 300);
        Der(Panelcuenta, 10, 5, 0);
        Der(panel_static, 10, 5, 45);

    }//GEN-LAST:event_volver_usuarioMouseClicked

    private void volver_preuntasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volver_preuntasMouseClicked
        // TODO add your handling code here:
        Izq(panel_preguntas, 10, 5, -300);
        Izq(Panelcuenta, 10, 5, 0);
        Izq(panel_static, 10, 5, 45);


    }//GEN-LAST:event_volver_preuntasMouseClicked

    private void noti_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noti_btnMouseClicked
        // TODO add your handling code here:
        Der(Panelcuenta, 10, 5, 300);
        Der(panel_static, 10, 5, 345);
        Der(panel_notificacion, 10, 5, 0);
    }//GEN-LAST:event_noti_btnMouseClicked

    private void volver_notiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volver_notiMouseClicked
        // TODO add your handling code here:
        Izq(Panelcuenta, 10, 5, 0);
        Izq(panel_static, 10, 5, 45);
        Izq(panel_notificacion, 10, 5, -300);
    }//GEN-LAST:event_volver_notiMouseClicked

    private void noti_eventoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noti_eventoMouseClicked
        // TODO add your handling code here:
        noti_info_titulo.setText("Evento deprotivo en el CSU");
        noti_info_parrafo.setText("<html>"
                + "El CSU se complace en "
                + "invitar a toda la comunidad"
                + "a su gran evento deportivo, "
                + "una jornada dedicada a "
                + "la competencia, la integración "
                + "y el espíritu atlético. Durante "
                + "este día, se llevarán "
                + "a cabo diversas actividades "
                + "y torneos en los que "
                + "equipos y participantes demostrarán "
                + "su talento, disciplina y "
                + "trabajo en equipo. Este "
                + "encuentro busca promover la "
                + "sana convivencia y el "
                + "amor por el deporte "
                + "en un ambiente lleno "
                + "de energía y entusiasmo."
                + "</html>");
        moverArriba(noti_info, 10, 5, 150);
    }//GEN-LAST:event_noti_eventoMouseClicked

    private void noti_info_volverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noti_info_volverMouseClicked
        // TODO add your handling code here:
        moverAbajo(noti_info, 10, 5, 621);
    }//GEN-LAST:event_noti_info_volverMouseClicked

    private void noti_finalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noti_finalMouseClicked
        // TODO add your handling code here:
        noti_info_titulo.setText("Presentación de Proyecto");
        noti_info_parrafo.setText("<html>"
                + "Se recuerda a todos los estudiantes "
                + "que la presentación del proyecto final "
                + "del curso de Estructura de Datos "
                + "es de carácter obligatorio y constituye "
                + "un requisito fundamental para la evaluación "
                + "y aprobación de la asignatura. "
                + "La no entrega o presentación del proyecto "
                + "en la fecha establecida implicará una calificación "
                + "no aprobatoria, sin excepciones. "
                + "Se recomienda verificar los criterios "
                + "de evaluación y preparar adecuadamente "
                + "la exposición del trabajo."
                + "</html>");
        moverArriba(noti_info, 10, 5, 150);
    }//GEN-LAST:event_noti_finalMouseClicked

    private void noti_winnerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noti_winnerMouseClicked
        // TODO add your handling code here:
        noti_info_titulo.setText("Ganadores IngeniaTic");
        noti_info_parrafo.setText("<html>"
                + "Nos complace anunciar a los ganadores "
                + "del concurso Ingeniatic. El primer lugar "
                + "fue otorgado al equipo 'J3M CALCULATOR' por su "
                + "proyecto de automatización inteligente de cultivos, "
                + "que destacó por su innovación y funcionalidad. "
                + "El segundo lugar fue para 'DataSolve', "
                + "por su aplicación de análisis predictivo "
                + "en sistemas de transporte urbano. "
                + "El tercer lugar lo obtuvo el equipo 'GreenCode', "
                + "con su propuesta de monitoreo ambiental en tiempo real. "
                + "Felicitamos a todos los participantes por su esfuerzo."
                + "</html>");
        moverArriba(noti_info, 10, 5, 150);
    }//GEN-LAST:event_noti_winnerMouseClicked

    private void noti_evento2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noti_evento2MouseClicked
        // TODO add your handling code here:
        noti_info_titulo.setText("Presentar Parcial");
        noti_info_parrafo.setText("<html>"
                + "Se informa a todos los estudiantes "
                + "que deberán presentar el examen parcial "
                + "de la asignatura de Cálculo en la fecha "
                + "y hora previamente establecidas por la coordinación. "
                + "Es indispensable asistir con puntualidad "
                + "y portar los materiales permitidos según indicaciones. "
                + "La evaluación abarcará los temas vistos "
                + "hasta la fecha y tendrá un valor significativo "
                + "dentro de la calificación final del curso. "
                + "La inasistencia sin justificación válida "
                + "implicará una calificación de cero. "
                + "Se recomienda repasar ejercicios prácticos "
                + "y consultar dudas antes de la fecha límite."
                + "</html>");
        moverArriba(noti_info, 10, 5, 150);

    }//GEN-LAST:event_noti_evento2MouseClicked

    private void noti_metasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noti_metasActionPerformed
        // TODO add your handling code here:
        noti_info_titulo.setText("Metas Financieras");
        noti_info_parrafo.setText("<html>"
                + "Después de esfuerzo, constancia y disciplina, "
                + "estás muy cerca de alcanzar tus metas financieras. "
                + "Cada decisión tomada con responsabilidad y visión "
                + "te ha llevado paso a paso hacia la estabilidad "
                + "y el cumplimiento de tus objetivos económicos. "
                + "Este logro es reflejo de tu compromiso "
                + "con una planificación inteligente y sostenida. "
                + "Ahora es momento de reconocer tu avance, "
                + "mantener el enfoque y seguir construyendo "
                + "un futuro financiero sólido y consciente."
                + "</html>");
        moverArriba(noti_info, 10, 5, 150);
    }//GEN-LAST:event_noti_metasActionPerformed

    private void noti_metasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noti_metasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_noti_metasMouseClicked

    private void panel_usuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_usuarioMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_panel_usuarioMouseClicked

    private void cuanto_recarga_lbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuanto_recarga_lbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cuanto_recarga_lbActionPerformed

    private void cuanto_recarga_lbFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cuanto_recarga_lbFocusGained
        // TODO add your handling code here:
        cuanto_recarga_lb.setText("");
    }//GEN-LAST:event_cuanto_recarga_lbFocusGained

    private void cuanto_recarga_lbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuanto_recarga_lbMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cuanto_recarga_lbMouseClicked

    private void recargar_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recargar_btnMouseClicked
        // TODO add your handling code here:
        int a = Integer.parseInt(cuanto_recarga_lb.getText());
        String b = id_recarga_lb.getText();
        transsacciones(b, null, a, "recargar");
        plata();
        id_recarga_lb.setText("");
        cuanto_recarga_lb.setText("¿Cuanto?");

    }//GEN-LAST:event_recargar_btnMouseClicked

    private void volver_recargarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volver_recargarMouseClicked
        // TODO add your handling code here:
        moverAbajo(panel_recarga, 5, 5, 621);

    }//GEN-LAST:event_volver_recargarMouseClicked

    private void recarga_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recarga_btnMouseClicked
        // TODO add your handling code here:
        trans.setVisible(false);
        moverArriba(panel_recarga, 5, 5, 0);
    }//GEN-LAST:event_recarga_btnMouseClicked

    private void volver_recargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volver_recargarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_volver_recargarActionPerformed

    private void cuanto_retira_lbFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cuanto_retira_lbFocusGained
        // TODO add your handling code here:
        cuanto_retira_lb.setText("");
    }//GEN-LAST:event_cuanto_retira_lbFocusGained

    private void cuanto_retira_lbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuanto_retira_lbMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cuanto_retira_lbMouseClicked

    private void cuanto_retira_lbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuanto_retira_lbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cuanto_retira_lbActionPerformed

    private void cuanto_retirarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuanto_retirarMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cuanto_retirarMouseClicked

    private void retirar_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_retirar_btnMouseClicked
        // TODO add your handling code here:
        String id = id_retira_lb.getText();
        int cuanto = Integer.parseInt(cuanto_retira_lb.getText());
        transsacciones(id, null, cuanto, "retirar");
        plata();
        id_retira_lb.setText("");
        cuanto_retira_lb.setText("¿Cuanto?");
    }//GEN-LAST:event_retirar_btnMouseClicked

    private void reti_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reti_btnMouseClicked
        // TODO add your handling code here:
        trans.setVisible(false);
        moverAbajo(panel_retirar, 5, 5, 0);

    }//GEN-LAST:event_reti_btnMouseClicked

    private void volver_retirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volver_retirarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_volver_retirarActionPerformed

    private void volver_retirarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volver_retirarMouseClicked
        // TODO add your handling code here:
        moverArriba(panel_retirar, 5, 5, -621);
    }//GEN-LAST:event_volver_retirarMouseClicked

    private void mas_eventos_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mas_eventos_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mas_eventos_btnActionPerformed

    private void mas_ganaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mas_ganaMouseClicked
        // TODO add your handling code here:
        mas_eventos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo_gana.png")));

    }//GEN-LAST:event_mas_ganaMouseClicked

    private void mas_eventos_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mas_eventos_btnMouseClicked
        // TODO add your handling code here:
        mas_eventos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/panel_mas_eventos.png")));
    }//GEN-LAST:event_mas_eventos_btnMouseClicked

    private void mas_compraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mas_compraMouseClicked
        // TODO add your handling code here:
        mas_eventos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo_compra.png")));
    }//GEN-LAST:event_mas_compraMouseClicked

    private void panel_masMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_masMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_panel_masMouseClicked

    private void mas_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mas_btnMouseClicked
        // TODO add your handling code here:
        Izq(Panelcuenta, 10, 5, -600);
        Der(pass, 10, 2, 145);
        Izq(panel_mas, 10, 5, 0);
        Izq(panel_movimientos, 10, 5, -300);
    }//GEN-LAST:event_mas_btnMouseClicked

    private void casa_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_casa_btnMouseClicked
        // TODO add your handling code here:
        Izq(pass, 10, 2, 5);
        Der(panel_mas, 10, 5, 600);
        Der(Panelcuenta, 10, 5, 0);
        Der(panel_movimientos, 10, 5, 300);

    }//GEN-LAST:event_casa_btnMouseClicked

    private void trans_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trans_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_trans_btnActionPerformed
    boolean pala = true;
    private void trans_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trans_btnMouseClicked
        // TODO add your handling code here:
        String idusu = TextoLogin.getText();
        mostrarUltimasTransacciones(idusu, panel_movimientos);
        if (pala) {
            Izq(Panelcuenta, 10, 5, -300);
            Der(pass, 10, 2, 77);
            Izq(panel_movimientos, 10, 5, 0);
            Izq(panel_mas, 10, 5, 300);
            pala = false;
        } else {
            Der(Panelcuenta, 10, 5, -300);
            Der(panel_movimientos, 10, 5, 0);
            Der(panel_mas, 10, 5, 300);
            Izq(pass, 10, 2, 77);
            pala = true;

        }
        ;
    }//GEN-LAST:event_trans_btnMouseClicked

    private void mas_metasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mas_metasMouseClicked
        // TODO add your handling code here:
        String a = TextoLogin.getText();
        File archi =new File("C:\\Users\\mvale\\Desktop\\Historial.txt");
        descargarHistorial(a,archi);
    }//GEN-LAST:event_mas_metasMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
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
    private javax.swing.JScrollPane Mensaje_enviaT;
    private javax.swing.JPanel PanelLogin;
    private javax.swing.JPanel PanelRegis;
    private javax.swing.JPanel Panelcuenta;
    private javax.swing.JTextField TextoLogin;
    private javax.swing.JLabel apellido_lb;
    private javax.swing.JTextField apodoR;
    private javax.swing.JLabel apodo_lb;
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
    private javax.swing.JButton cerrar_sesion;
    private javax.swing.JCheckBox check_inver_1;
    private javax.swing.JCheckBox check_inver_2;
    private javax.swing.JCheckBox check_inver_3;
    private javax.swing.JTextField contraR;
    private javax.swing.JTextField correoR;
    private javax.swing.JLabel correo_lb;
    private javax.swing.JButton cuanto_envia_btn;
    private javax.swing.JTextField cuanto_envia_lb;
    private javax.swing.JButton cuanto_inver_btn;
    private javax.swing.JTextField cuanto_inver_lb;
    private javax.swing.JButton cuanto_recarga;
    private javax.swing.JTextField cuanto_recarga_lb;
    private javax.swing.JTextField cuanto_retira_lb;
    private javax.swing.JButton cuanto_retirar;
    private javax.swing.JLabel disponible_l;
    private javax.swing.JLabel envia;
    private javax.swing.JButton envia_btn;
    private javax.swing.JButton enviar_ac_btn;
    private javax.swing.JLabel guardada_l;
    private javax.swing.JTextField guardar;
    private javax.swing.JButton guardar_ahorro;
    private javax.swing.JButton id_envia_btn;
    private javax.swing.JTextField id_envia_lb;
    private javax.swing.JLabel id_lb;
    private javax.swing.JButton id_recarga;
    private javax.swing.JTextField id_recarga_lb;
    private javax.swing.JTextField id_retira_lb;
    private javax.swing.JButton id_retirar;
    private javax.swing.JButton inver_btn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JButton mas_btn;
    private javax.swing.JButton mas_cajitas;
    private javax.swing.JButton mas_compra;
    private javax.swing.JButton mas_eventos;
    private javax.swing.JButton mas_eventos_btn;
    private javax.swing.JButton mas_gana;
    private javax.swing.JButton mas_inversiones;
    private javax.swing.JButton mas_metas;
    private javax.swing.JButton mensaje_envia;
    private javax.swing.JTextArea mensaje_envia_at;
    private javax.swing.JButton metas_btn;
    private javax.swing.JPanel movi;
    private javax.swing.JButton n_btn;
    private javax.swing.JTextField nameR;
    private javax.swing.JLabel nombre_lb;
    private javax.swing.JButton noti_btn;
    private javax.swing.JButton noti_evento;
    private javax.swing.JButton noti_evento2;
    private javax.swing.JButton noti_final;
    private javax.swing.JPanel noti_info;
    private javax.swing.JLabel noti_info_parrafo;
    private javax.swing.JLabel noti_info_titulo;
    private javax.swing.JButton noti_info_volver;
    private javax.swing.JButton noti_metas;
    private javax.swing.JButton noti_winner;
    private javax.swing.JPanel panel_ahorros;
    private javax.swing.JPanel panel_cajitas;
    private javax.swing.JPanel panel_envia;
    private javax.swing.JPanel panel_inversiones;
    private javax.swing.JPanel panel_mas;
    private javax.swing.JPanel panel_movimientos;
    private javax.swing.JPanel panel_notificacion;
    private javax.swing.JPanel panel_preguntas;
    private javax.swing.JPanel panel_recarga;
    private javax.swing.JPanel panel_retirar;
    private javax.swing.JPanel panel_static;
    private javax.swing.JPanel panel_usuario;
    private javax.swing.JPanel panelinicio;
    private javax.swing.JPanel pass;
    private javax.swing.JLabel pide;
    private javax.swing.JButton pide_btn;
    private javax.swing.JButton realizar_inver_btn;
    private javax.swing.JButton realizar_r;
    private javax.swing.JLabel recarga;
    private javax.swing.JButton recarga_btn;
    private javax.swing.JButton recargar_btn;
    private javax.swing.JButton regis;
    private javax.swing.JButton reti_btn;
    private javax.swing.JLabel retira;
    private javax.swing.JButton retirar_btn;
    private javax.swing.JTextField retiro;
    private javax.swing.JButton sig_btn;
    private javax.swing.JTextField t1;
    private javax.swing.JTextField t2;
    private javax.swing.JTextField t3;
    private javax.swing.JTextField t4;
    private javax.swing.JLabel total_l;
    private javax.swing.JPanel trans;
    private javax.swing.JButton trans_btn;
    private javax.swing.JButton usuario_btn;
    private javax.swing.JButton volver_c;
    private javax.swing.JButton volver_envia;
    private javax.swing.JButton volver_inver;
    private javax.swing.JButton volver_noti;
    private javax.swing.JButton volver_preuntas;
    private javax.swing.JButton volver_recargar;
    private javax.swing.JButton volver_retirar;
    private javax.swing.JButton volver_usuario;
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

    class fondoenvia extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/fondo_envia.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondoinversiones extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/fondo_inversiones.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondoahorro extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/fondo_ahorros.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondousuario extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/fondo_usuario.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondopreguntas extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/fondo_preguntas.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondonoti extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/fondo_notifi.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondonotiinfo extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/noti_info.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondorecarga extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/fondo_recarga.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondoretira extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/fondo_retira.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondomas extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/fondo_mas.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondomaseventos extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/panel_mas_eventos.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    class fondomovimientos extends JPanel {

        private Image imagen4;

        public void paint(Graphics g) {
            imagen4 = new ImageIcon(getClass().getResource("/imagenes/fondo_movimientos.png")).getImage();
            g.drawImage(imagen4, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

}
