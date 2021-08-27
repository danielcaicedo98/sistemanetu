/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author danie
 */
public class VentanaServidor extends javax.swing.JFrame {

    private ArrayList<itemCombo> dependencias;
    public static final int VCbxDepListado = 1;
    public static final int VCbxDependencias = 2;
    public static final int VCbxSubDep = 3;
    public static final int VCxSubDepListados = 4;

    public static void main(String[] args) {

        VentanaServidor ven = new VentanaServidor();

    }

    /**
     * Creates new form Servidor
     */
    public VentanaServidor() {
        tableModel = new TMEmpleado();
        initComponents();
        setTitle("Servidor");
        setResizable(false);
        setLocationRelativeTo(null);

        setVisible(true);
        activarControles(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        dependencias = new ArrayList();
    }

    //Metodo para mostrar mensajes cuando se presente una exepción
    public void gestionMensajes(String mensaje, String titulo, int icono) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, icono);
    }

    public void cargarEmpleados(List<Empleado> empleados) {
        tableModel.setEmpleados(empleados);
        tablaEmpleados.updateUI();
    }

    //GETTERS
    //Nombre Completo
    public String getNombre() {
        return txtNomServidor.getText().trim();
    }

    //Correo Electronico
    public String getCorreo() {
        return txtCorreoServidor.getText().trim();
    }

    //Sexo
    public String getSexo() {
        return cbxSexo.getSelectedItem().toString().trim();
    }

    //Codigo 
    public String getCodigo() {
        return txtCodigo.getText();
    }

    //Codigo int
    public int getCodigoInt() {
        int codigo = -1;
        
        try {
            
            codigo = Integer.parseInt(txtCodigo.getText());
            
            
            
        } catch (NumberFormatException e) {
            
            
        }
        
        if(codigo == 0){
            codigo = -1;
        }
        
        return codigo;
    }

    //Nombre en busqueda
    public String getNombreBusq() {
        return txtNombreBusq.getText();
    }

    //Combo de dependencias en busqueda
    public JComboBox<itemCombo> getCbxDepListado() {
        return cbxDepListado;
    }

    //Combo de dependencias en registro
    public JComboBox<itemCombo> getCbxDependencias() {
        return cbxDependencias;
    }

    //Combo de subdependencias en registro
    public JComboBox<itemCombo> getCbxSubdependencias() {
        return cbxSubDep;
    }

    //Combo de subdependencias en busqueda
    public JComboBox<itemCombo> getCbxSubdependenciasBusq() {
        return cbxSubDepListados;
    }

    //Metodo para llenas todos los combos de la vista con las dependencias y subdependencias de la BD
    public void llenarCDependencias() {

        cbxDepListado.removeAllItems();
        cbxDependencias.removeAllItems();

        cbxDepListado.addItem(new itemCombo(0, "TODAS"));
        cbxDependencias.addItem(new itemCombo(0, "SELECCIONAR"));

        for (itemCombo item : dependencias) {
            cbxDepListado.addItem(item);
            cbxDependencias.addItem(item);
        }

    }

    public void llenarCSubdpendencias(int combo, ResultSet consulta) {

        JComboBox<itemCombo> combobox = null;

        switch (combo) {

            case VCxSubDepListados: {
                combobox = cbxSubDepListados;
                combobox.removeAllItems();
                combobox.addItem(new itemCombo(0, "TODAS"));
            }
            break;

            case VCbxSubDep: {
                combobox = cbxSubDep;
                combobox.removeAllItems();
                combobox.addItem(new itemCombo(0, "SELECCIONAR"));
            }
            break;

        }

        try {

            if (consulta != null) {
                while (consulta.next()) {

                    combobox.addItem(new itemCombo(Integer.parseInt(consulta.getString("id")),
                            consulta.getString("nombre")));

                }

            }
        } catch (SQLException ex) {
            System.out.println("Error al extraer la consulta en VentanaServidor");;
        }

    }

    //Se agregan escuchas a los botones
    public void addListenerBtnModEmpleados(ActionListener listenPrograma) {
        btnModEmpleados.addActionListener(listenPrograma);
    }

    public void addListenerBtnElmEmpleados(ActionListener listenPrograma) {
        btnElmEmpleados.addActionListener(listenPrograma);
    }

    public void addListenerBtnRegistrarEmpleados(ActionListener listenPrograma) {
        btnRegistrarEmpleados.addActionListener(listenPrograma);
    }

    public void addListenerBtnRegistrarAdmin(ActionListener listenPrograma) {
        btnRegistrarAdmin.addActionListener(listenPrograma);
    }

    public void addListenerBtnBusListado(ActionListener listenPrograma) {
        btnBusListado.addActionListener(listenPrograma);
    }

    public void addListenerDependencias(ItemListener al) {
        cbxDependencias.addItemListener(al);
        cbxDepListado.addItemListener(al);
    }

    //Metodo para activar y desactivar componentes
    public void activarControles(boolean estado) {
        txtNomServidor.setEnabled(estado);
        txtCorreoServidor.setEnabled(estado);
        txtCodigo.setEnabled(estado);
        cbxDependencias.setEnabled(estado);
        cbxSexo.setEnabled(estado);
        cbxSubDep.setEnabled(estado);
        tablaEmpleados.setEnabled(!estado);
    }

    //Metodo para limpiar componentes
    public void limpiar() {
        txtNomServidor.setText("");
        txtCorreoServidor.setText("");
        txtCodigo.setText("");
        txtNombreBusq.setText("");
        cbxSexo.setSelectedItem("Seleccionar");
        seleccionarItem(VCbxDependencias, 0);
        seleccionarItem(VCbxSubDep, 0);
        //cbxDependencias.setSelectedItem("TODAS");
        //cbxSubDep.setSelectedItem("TODAS");
        //cbxDepListado.setSelectedItem("TODAS");
        //cbxSubDepListados.setSelectedItem("TODAS");
    }

    //Metodo para modificar componentes cuando se desee registrar un empleado o administrador
    public void accionRegistrar(int referencia) {

        if (btnRegistrarEmpleados.isEnabled() || btnRegistrarAdmin.isEnabled()) {
            limpiar();
            activarControles(true);
            btnRegistrarEmpleados.setEnabled(false);
            btnRegistrarAdmin.setEnabled(false);
            btnElmEmpleados.setText("Cancelar");
            btnElmEmpleados.setActionCommand("Cancelar");
            txtNomServidor.requestFocusInWindow();
            
            btnModEmpleados.setText("Registrar");
            
            if(referencia == 1){
                btnModEmpleados.setActionCommand("Registrar emple");
            }else{
                btnModEmpleados.setActionCommand("Registrar admin");
            }
            
            
            

        } else {
            limpiar();
            activarControles(false);
            btnRegistrarEmpleados.setEnabled(true);
            btnRegistrarAdmin.setEnabled(true);
            btnModEmpleados.setText("Modificar");
            btnModEmpleados.setActionCommand("Modificar");
            btnElmEmpleados.setText("Eliminar");
            btnElmEmpleados.setActionCommand("Eliminar");
            txtNomServidor.requestFocusInWindow();
            seleccionarItem(VCbxDependencias, 0);
            //seleccionarItem(VCbxSubDep, 0);
        }
    }

    //Metodo para modificar componentes cuando se desee modificar un empleado
    public int accionModificar() {

        int modificar = 0;

        if (btnModEmpleados.getText().equals("Modificar")) {

            if (tablaEmpleados.getSelectedRow() == -1) {

                if (tablaEmpleados.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "NO HAY REGISTROS");
                } else {
                    JOptionPane.showMessageDialog(this, "SELECCIONE UN EMPLEADO EN LA TABLA");
                }
            } else {
                activarControles(true);
                txtCodigo.setEnabled(false);
                btnRegistrarEmpleados.setEnabled(false);
                btnRegistrarAdmin.setEnabled(false);
                tablaEmpleados.setEnabled(false);

                btnModEmpleados.setText("Actualizar");
                btnModEmpleados.setActionCommand("Actualizar");
                btnElmEmpleados.setText("Cancelar");
                btnElmEmpleados.setActionCommand("Cancelar");
                txtNomServidor.requestFocusInWindow();
                modificar = 1;
            }
        } else {
            //activarControles(false);
            limpiar();
            btnRegistrarEmpleados.setEnabled(true);
            btnRegistrarAdmin.setEnabled(true);
            tablaEmpleados.setEnabled(true);

            btnModEmpleados.setText("Modificar");
            btnModEmpleados.setActionCommand("Modificar");
            btnElmEmpleados.setText("Eliminar");
            btnElmEmpleados.setActionCommand("Eliminar");
            txtNomServidor.requestFocusInWindow();

        }

        return modificar;
    }

    public int accionEliminar() {
        
        int eliminar = 0;

        if (tablaEmpleados.getSelectedRow() == -1) {

            if (tablaEmpleados.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "NO HAY REGISTROS");
            } else {
                JOptionPane.showMessageDialog(this, "SELECCIONE UN EMPLEADO EN LA TABLA");
            }
        }else{
            eliminar = 1;
        }
        
        return eliminar;
    }

    public void cargarEmpleadoAModificar() {

        Empleado empleado = getEmpleadoFromTable();

        txtNomServidor.setText(empleado.getNombre());
        txtCorreoServidor.setText(empleado.getCorreo());

        seleccionarItem(VCbxDependencias, empleado.getDependencia());
        seleccionarItem(VCbxSubDep, empleado.getSubDependencia());
        
        cbxSexo.setSelectedItem(empleado.getSexo());
        
        txtCodigo.setText(""+empleado.getCodigo());

    }

    public void seleccionarItem(int combo, int codigoitem) {

        JComboBox<itemCombo> combobox = null;

        switch (combo) {

            case VCbxDepListado: {
                combobox = cbxDepListado;
            }
            break;

            case VCbxDependencias: {
                combobox = cbxDependencias;
            }
            break;

            case VCbxSubDep: {
                combobox = cbxSubDep;
            }
            break;

            case VCxSubDepListados: {
                combobox = cbxSubDepListados;
            }
            break;
        }

        for (int i = 0; i < combobox.getItemCount(); i++) {

            itemCombo item = combobox.getItemAt(i);

            if (item.getId() == codigoitem) {
                combobox.setSelectedItem(item);
                break;
            }
        }

    }

    public void setDependencias(ResultSet consulta) {
        try {
            dependencias.clear();

            while (consulta.next()) {

                dependencias.add(new itemCombo(Integer.parseInt(consulta.getString("id")), consulta.getString("nombre")));

            }

            this.dependencias = dependencias;
        } catch (SQLException ex) {
            Logger.getLogger(VentanaServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Empleado getEmpleadoFromTable(){
        
        Empleado empleado = tableModel.getEmpleadoAt(tablaEmpleados.getSelectedRow());

        return empleado;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        lblNombre = new javax.swing.JLabel();
        pnlInfEmpleado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNomServidor = new javax.swing.JTextField();
        txtCorreoServidor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        cbxDependencias = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbxSubDep = new javax.swing.JComboBox<>();
        cbxSexo = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        btnRegistrarEmpleados = new javax.swing.JButton();
        btnModEmpleados = new javax.swing.JButton();
        btnElmEmpleados = new javax.swing.JButton();
        btnRegistrarAdmin = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cbxDepListado = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbxSubDepListados = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtNombreBusq = new javax.swing.JTextField();
        btnBusListado = new javax.swing.JButton();
        srcListadoEmpleadosS = new javax.swing.JScrollPane();
        tablaEmpleados = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNombre.setText("REGISTRO EMPLEADOS");

        pnlInfEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        pnlInfEmpleado.setBorder(javax.swing.BorderFactory.createTitledBorder("Informacion de Empleados"));

        jLabel1.setText("Nombre Completo");

        jLabel2.setText("Correo Electronico");

        txtNomServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomServidorActionPerformed(evt);
            }
        });

        txtCorreoServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoServidorActionPerformed(evt);
            }
        });

        jLabel3.setText("Código");

        jLabel4.setText("Dependencia");

        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        cbxDependencias.setModel(new javax.swing.DefaultComboBoxModel<>(new itemCombo[] {null }));

        jLabel5.setText("Sexo");

        jLabel6.setText("Subdependencia");

        cbxSubDep.setModel(new javax.swing.DefaultComboBoxModel<>(new itemCombo[] {null}));

        cbxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Femenino", "Masculino" }));

        javax.swing.GroupLayout pnlInfEmpleadoLayout = new javax.swing.GroupLayout(pnlInfEmpleado);
        pnlInfEmpleado.setLayout(pnlInfEmpleadoLayout);
        pnlInfEmpleadoLayout.setHorizontalGroup(
            pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfEmpleadoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfEmpleadoLayout.createSequentialGroup()
                        .addGroup(pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxDependencias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxSubDep, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfEmpleadoLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfEmpleadoLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(8, 8, 8)))
                        .addGroup(pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigo)
                            .addComponent(cbxSexo, 0, 122, Short.MAX_VALUE))
                        .addGap(26, 26, 26))
                    .addGroup(pnlInfEmpleadoLayout.createSequentialGroup()
                        .addGroup(pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(30, 30, 30)
                        .addGroup(pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNomServidor, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                            .addComponent(txtCorreoServidor))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlInfEmpleadoLayout.setVerticalGroup(
            pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfEmpleadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNomServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCorreoServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfEmpleadoLayout.createSequentialGroup()
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxSubDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(37, 37, 37))
                    .addGroup(pnlInfEmpleadoLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(pnlInfEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxSexo)
                            .addComponent(cbxDependencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(79, 79, 79))))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnRegistrarEmpleados.setText("Registrar Empleado");
        btnRegistrarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarEmpleadosActionPerformed(evt);
            }
        });

        btnModEmpleados.setText("Modificar");

        btnElmEmpleados.setText("Eliminar");

        btnRegistrarAdmin.setText("Registrar Administrador");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnRegistrarAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRegistrarEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnModEmpleados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnElmEmpleados)))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModEmpleados)
                    .addComponent(btnElmEmpleados))
                .addGap(32, 32, 32)
                .addComponent(btnRegistrarEmpleados)
                .addGap(35, 35, 35)
                .addComponent(btnRegistrarAdmin)
                .addGap(30, 30, 30))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Empleados"));

        jLabel7.setText("Dependencia");

        cbxDepListado.setModel(new javax.swing.DefaultComboBoxModel<>(new itemCombo[] {new itemCombo(0, "TODAS") }));
        cbxDepListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxDepListadoActionPerformed(evt);
            }
        });

        jLabel8.setText("Subdependencia");

        cbxSubDepListados.setModel(new javax.swing.DefaultComboBoxModel<>(new itemCombo[] {new itemCombo(0, "TODAS") }));
        cbxSubDepListados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSubDepListadosActionPerformed(evt);
            }
        });

        jLabel9.setText("Nombre de Empleado");

        txtNombreBusq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreBusqActionPerformed(evt);
            }
        });

        btnBusListado.setText("Buscar");

        srcListadoEmpleadosS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                srcListadoEmpleadosSMouseClicked(evt);
            }
        });

        tablaEmpleados.setModel(tableModel);
        tablaEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEmpleadosMouseClicked(evt);
            }
        });
        srcListadoEmpleadosS.setViewportView(tablaEmpleados);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(srcListadoEmpleadosS, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxDepListado, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxSubDepListados, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombreBusq, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBusListado)))
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbxDepListado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(cbxSubDepListados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtNombreBusq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBusListado))
                .addGap(31, 31, 31)
                .addComponent(srcListadoEmpleadosS, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(pnlInfEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35))
            .addGroup(layout.createSequentialGroup()
                .addGap(301, 301, 301)
                .addComponent(lblNombre)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblNombre)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlInfEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomServidorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomServidorActionPerformed

    private void txtCorreoServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoServidorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoServidorActionPerformed

    private void btnRegistrarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarEmpleadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarEmpleadosActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtNombreBusqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreBusqActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreBusqActionPerformed

    private void tablaEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaEmpleadosMouseClicked

    }//GEN-LAST:event_tablaEmpleadosMouseClicked

    private void cbxDepListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxDepListadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxDepListadoActionPerformed

    private void cbxSubDepListadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSubDepListadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxSubDepListadosActionPerformed

    private void srcListadoEmpleadosSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_srcListadoEmpleadosSMouseClicked

    }//GEN-LAST:event_srcListadoEmpleadosSMouseClicked

    /**
     * @param args the command line arguments
     */
    private TMEmpleado tableModel;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBusListado;
    private javax.swing.JButton btnElmEmpleados;
    private javax.swing.JButton btnModEmpleados;
    private javax.swing.JButton btnRegistrarAdmin;
    private javax.swing.JButton btnRegistrarEmpleados;
    private javax.swing.JComboBox<itemCombo> cbxDepListado;
    private javax.swing.JComboBox<itemCombo> cbxDependencias;
    private javax.swing.JComboBox<String> cbxSexo;
    private javax.swing.JComboBox<itemCombo> cbxSubDep;
    private javax.swing.JComboBox<itemCombo> cbxSubDepListados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JPanel pnlInfEmpleado;
    private javax.swing.JScrollPane srcListadoEmpleadosS;
    private javax.swing.JTable tablaEmpleados;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCorreoServidor;
    private javax.swing.JTextField txtNomServidor;
    private javax.swing.JTextField txtNombreBusq;
    // End of variables declaration//GEN-END:variables
}
