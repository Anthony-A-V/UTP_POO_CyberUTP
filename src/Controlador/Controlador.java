package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import Modelo.DAODetallePedidoImpl;
import Modelo.DAOPedidoImpl;
import Modelo.DAOProductoImpl;
import Modelo.DAOUsuarioClienteImpl;
import Modelo.DAOUsuarioEmpleadoImpl;
import Modelo.DetallePedido;
import Modelo.IPedidoExistente;
import Modelo.Pedido;
import Modelo.Producto;
import Modelo.UsuarioCliente;
import Modelo.UsuarioEmpleado;
import Vista.frmInicioCliente;
import Vista.frmInicioEmpleado;
import Vista.frmLoginCliente;
import Vista.frmLoginEmpleado;
import Vista.frmRegistro;

public class Controlador implements ActionListener, ChangeListener {

    private UsuarioCliente usuCli;
    private DAOUsuarioClienteImpl usuCliDao;
    private frmLoginCliente frmLoginC;
    private frmInicioCliente frmInicioC;
    private frmRegistro frmReg;
    private UsuarioEmpleado usuEmp;
    private DAOUsuarioEmpleadoImpl usuEmpDao;
    private frmLoginEmpleado frmLoginE;
    private frmInicioEmpleado frmInicioE;
    private Producto prod;
    private DAOProductoImpl prodDao;
    private Pedido ped;
    private DAOPedidoImpl pedDao;
    private DetallePedido detPed;
    private DAODetallePedidoImpl detPedDao;
    private ArrayList<DetallePedido> listPedidos = new ArrayList<>();

    public Controlador(UsuarioCliente usuCli, DAOUsuarioClienteImpl usuCliDao, frmLoginCliente frmLoginC,
            frmInicioCliente frmInicioC, frmRegistro frmReg, UsuarioEmpleado usuEmp, DAOUsuarioEmpleadoImpl usuEmpDao,
            frmLoginEmpleado frmLoginE, frmInicioEmpleado frmInicioE, Producto prod, DAOProductoImpl prodDao,
            Pedido ped, DAOPedidoImpl pedDao,
            DetallePedido detPed, DAODetallePedidoImpl detPedDao) {
        this.usuCli = usuCli;
        this.usuCliDao = usuCliDao;
        this.frmLoginC = frmLoginC;
        this.frmInicioC = frmInicioC;
        this.frmReg = frmReg;
        this.usuEmp = usuEmp;
        this.usuEmpDao = usuEmpDao;
        this.frmLoginE = frmLoginE;
        this.frmInicioE = frmInicioE;
        this.prod = prod;
        this.prodDao = prodDao;
        this.ped = ped;
        this.pedDao = pedDao;
        this.detPed = detPed;
        this.detPedDao = detPedDao;
        this.frmLoginC.btnIniciarSesion.addActionListener(this);
        this.frmLoginC.btnLoginEmpleado.addActionListener(this);
        this.frmLoginC.btnRegistrarse.addActionListener(this);
        this.frmInicioC.btnCerrar.addActionListener(this);
        this.frmReg.btnRegistrarse.addActionListener(this);
        this.frmReg.btnRegresar.addActionListener(this);
        this.frmLoginE.btnIniciarSesion.addActionListener(this);
        this.frmLoginE.btnRegresar.addActionListener(this);
        this.frmInicioE.btnCerrarSesion.addActionListener(this);
        this.frmInicioE.btnAgregarEmpleado.addActionListener(this);
        this.frmInicioE.btnEliminarEmpleado.addActionListener(this);
        this.frmInicioE.btnActualizarEmpleado.addActionListener(this);
        this.frmInicioE.btnBuscarEmpleado.addActionListener(this);
        this.frmInicioE.btnAgregarProducto.addActionListener(this);
        this.frmInicioE.btnActualizarProducto.addActionListener(this);
        this.frmInicioE.btnEliminarProducto.addActionListener(this);
        this.frmInicioE.btnBuscarProducto.addActionListener(this);
        this.frmInicioC.cboProductos.addActionListener(this);
        this.frmInicioC.spnCantidad.addChangeListener(this);
        this.frmInicioC.btnAgregar.addActionListener(this);
        this.frmInicioC.btnQuitar.addActionListener(this);
        this.frmInicioC.btnComprar.addActionListener(this);
    }

    public void iniciar() {
        frmLoginC.setTitle("Login Cliente");
        frmLoginC.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmLoginC.btnIniciarSesion) {

            usuCli.setUsuario(frmLoginC.txtUsuario.getText());
            usuCli.setClave(String.valueOf(frmLoginC.txtClave.getPassword()));

            if (usuCliDao.iniciarSesion(usuCli)) {

                frmLoginC.setVisible(false);
                usuCliDao.buscar(usuCli);
                frmInicioC.spnCantidad.setValue(1);
                listarComboProd();
                
                // Función Lambda 2
                prodDao.listar().forEach((p) -> {
                    if (p.getNombreProducto().equals(frmInicioC.cboProductos.getSelectedItem())) {

                        detPed.setProducto(p);
                        detPed.setCantidad(Integer.parseInt(frmInicioC.spnCantidad.getValue().toString()));
                        frmInicioC.lblPrecio.setText("S/. " + " " + p.getPrecio());
                        detPed.setImporte(p.getPrecio() * (double) detPed.getCantidad());
                        frmInicioC.lblImporte.setText("S/. " + " " + detPed.getImporte());
                    }
                });
                frmInicioC.setVisible(true);
                frmInicioC.lblCliente.setText("Usuario - " + usuCli.getNombres());

            } else {

                frmLoginC.lblMensajeInicio.setText("Usuario y/o Clave incorrectos");
            }
        }

        if (e.getSource() == frmLoginC.btnRegistrarse) {

            frmLoginC.setVisible(false);
            limpiarRegistroCliente();
            frmReg.setVisible(true);
        }

        if (e.getSource() == frmLoginC.btnLoginEmpleado) {

            frmLoginC.setVisible(false);
            limpiarLoginEmpleado();
            frmLoginE.setVisible(true);
        }

        if (e.getSource() == frmInicioC.btnCerrar) {

            frmInicioC.setVisible(false);
            limpiarLoginCliente();
            frmLoginC.setVisible(true);
        }

        if (e.getSource() == frmReg.btnRegistrarse) {

            if (validarCamposRegistro()) {

                usuCli.setUsuario(frmReg.txtUsuario.getText());
                usuCli.setNombres(String.valueOf(frmReg.txtNombres.getText()));
                usuCli.setApellidos(String.valueOf(frmReg.txtApellidos.getText()));
                usuCli.setClave(String.valueOf(frmReg.txtClave.getPassword()));
                usuCli.setDireccion(frmReg.txtDireccion.getText());
                usuCli.setDni(Integer.parseInt(frmReg.txtDni.getText()));
                usuCli.setTelefono(Integer.parseInt(frmReg.txtTelefono.getText()));

                if (String.valueOf(frmReg.txtClave.getPassword())
                        .equals(String.valueOf(frmReg.txtConfirmarClave.getPassword()))) {

                    if (usuCliDao.crear(usuCli)) {

                        JOptionPane.showMessageDialog(null, "Registrado correctamente", "Registrado",
                                JOptionPane.INFORMATION_MESSAGE);
                        frmReg.setVisible(false);
                        limpiarLoginCliente();
                        frmLoginC.setVisible(true);

                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Claves ingresadas diferentes",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getSource() == frmReg.btnRegresar) {

            frmReg.setVisible(false);
            limpiarLoginCliente();
            frmLoginC.setVisible(true);
        }

        if (e.getSource() == frmLoginE.btnIniciarSesion) {

            usuEmp.setUsuario(frmLoginE.txtCodEmpleado.getText());
            usuEmp.setClave(String.valueOf(frmLoginE.txtClave.getPassword()));

            if (usuEmpDao.iniciarSesion(usuEmp)) {

                frmLoginE.setVisible(false);
                usuEmpDao.buscar(usuEmp);
                frmInicioE.lblEmpleado.setText("Empleado - " + usuEmp.getNombres());
                mostrarEmpTabla();
                mostrarProTabla();
                frmInicioE.setVisible(true);

            } else {

                frmLoginE.lblMensajeInicio.setText("Código Empleado y/o Clave incorrectos");
            }
        }

        if (e.getSource() == frmLoginE.btnRegresar) {

            frmLoginE.setVisible(false);
            limpiarLoginCliente();
            frmLoginC.setVisible(true);
        }

        if (e.getSource() == frmInicioE.btnCerrarSesion) {

            frmInicioE.setVisible(false);
            limpiarLoginEmpleado();
            frmLoginE.setVisible(true);
        }

        if (e.getSource() == frmInicioE.btnAgregarEmpleado) {

            if (validarCamposEmpleado()) {

                usuEmp.setUsuario(frmInicioE.txtUsuario.getText());
                usuEmp.setClave(frmInicioE.txtClave.getText());
                usuEmp.setNombres(frmInicioE.txtNombres.getText());
                usuEmp.setApellidos(frmInicioE.txtApellidos.getText());

                if (usuEmpDao.crear(usuEmp)) {

                    mostrarEmpTabla();
                    limpiarCajasEmpleado();
                }
            }
        }

        if (e.getSource() == frmInicioE.btnEliminarEmpleado) {

            if (frmInicioE.tblEmpleados.getSelectedRow() != -1) {

                int filaSeleccionada = frmInicioE.tblEmpleados.getSelectedRow();
                String id = String.valueOf(frmInicioE.tblEmpleados.getValueAt(filaSeleccionada, 0));
                usuEmp.setIdEmpleado(Integer.parseInt(id));

                if (usuEmpDao.eliminar(usuEmp)) {
                    mostrarEmpTabla();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una fila",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == frmInicioE.btnActualizarEmpleado) {

            if (validarCamposEmpleado()) {

                usuEmp.setUsuario(frmInicioE.txtUsuario.getText());
                usuEmp.setClave(frmInicioE.txtClave.getText());
                usuEmp.setNombres(frmInicioE.txtNombres.getText());
                usuEmp.setApellidos(frmInicioE.txtApellidos.getText());

                if (frmInicioE.tblEmpleados.getSelectedRow() != -1) {
                    int filaSeleccionada = frmInicioE.tblEmpleados.getSelectedRow();
                    String id = String.valueOf(frmInicioE.tblEmpleados.getValueAt(filaSeleccionada, 0));
                    usuEmp.setIdEmpleado(Integer.parseInt(id));

                    if (usuEmpDao.actualizar(usuEmp)) {
                        mostrarEmpTabla();
                        limpiarCajasEmpleado();
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Seleccione una fila",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getSource() == frmInicioE.btnBuscarEmpleado) {

            if (frmInicioE.txtIdEmpleado.getText().equals("") || frmInicioE.txtIdEmpleado.getText() == null) {
                mostrarEmpTabla();

            } else {

                usuEmp.setIdEmpleado(Integer.parseInt(frmInicioE.txtIdEmpleado.getText()));
                buscarEmpTabla(usuEmp);
            }
        }

        if (e.getSource() == frmInicioE.btnAgregarProducto) {

            if (validarCamposProducto()) {

                prod.setNombreProducto(frmInicioE.txtProducto.getText());
                prod.setCategoria(frmInicioE.txtCategoria.getText());
                prod.setStock(Integer.parseInt(frmInicioE.txtStock.getText()));
                prod.setPrecio(Double.parseDouble(frmInicioE.txtPrecio.getText()));

                if (prodDao.crear(prod)) {

                    mostrarProTabla();
                    limpiarCajasProducto();
                }
            }
        }

        if (e.getSource() == frmInicioE.btnEliminarProducto) {

            if (frmInicioE.tblProductos.getSelectedRow() != -1) {

                int filaSeleccionada = frmInicioE.tblProductos.getSelectedRow();
                String id = String.valueOf(frmInicioE.tblProductos.getValueAt(filaSeleccionada, 0));
                prod.setIdProducto(Integer.parseInt(id));

                if (prodDao.eliminar(prod)) {
                    mostrarProTabla();
                }

            } else {

                JOptionPane.showMessageDialog(null, "Seleccione una fila",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == frmInicioE.btnActualizarProducto) {

            if (validarCamposProducto()) {

                prod.setNombreProducto(frmInicioE.txtProducto.getText());
                prod.setCategoria(frmInicioE.txtCategoria.getText());
                prod.setStock(Integer.parseInt(frmInicioE.txtStock.getText()));
                prod.setPrecio(Double.parseDouble(frmInicioE.txtPrecio.getText()));

                if (frmInicioE.tblProductos.getSelectedRow() != -1) {

                    int filaSeleccionada = frmInicioE.tblProductos.getSelectedRow();
                    String id = String.valueOf(frmInicioE.tblProductos.getValueAt(filaSeleccionada, 0));
                    prod.setIdProducto(Integer.parseInt(id));

                    if (prodDao.actualizar(prod)) {

                        mostrarProTabla();
                        limpiarCajasProducto();
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Seleccione una fila",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getSource() == frmInicioE.btnBuscarProducto) {

            if (frmInicioE.txtIdProducto.getText().equals("") || frmInicioE.txtIdProducto.getText() == null) {
                mostrarProTabla();

            } else {

                prod.setIdProducto(Integer.parseInt(frmInicioE.txtIdProducto.getText()));
                buscarProTabla(prod);
            }
        }

        if (e.getSource() == frmInicioC.cboProductos) {

            prodDao.listar().forEach((p) -> {
                if (p.getNombreProducto().equals(frmInicioC.cboProductos.getSelectedItem())) {

                    detPed.setProducto(p);
                    detPed.setCantidad(Integer.parseInt(frmInicioC.spnCantidad.getValue().toString()));
                    frmInicioC.lblPrecio.setText("S/. " + " " + p.getPrecio());
                    detPed.setImporte(p.getPrecio() * (double) detPed.getCantidad());
                    frmInicioC.lblImporte.setText("S/. " + " " + detPed.getImporte());
                }
            });
        }

        if (e.getSource() == frmInicioC.btnAgregar) {

            DetallePedido dp = new DetallePedido();
            dp.setProducto(detPed.getProducto());
            dp.setCantidad(detPed.getCantidad());
            dp.setImporte(detPed.getImporte());

            if (dp.getCantidad() != 0) {

                // Función Lambda 1
                IPedidoExistente lambda1 = (detPed) -> {
                    for (DetallePedido p : listPedidos) {

                        if (p.getProducto().getNombreProducto().equals(detPed.getProducto().getNombreProducto())) {

                            p.setCantidad(detPed.getCantidad());
                            p.setImporte(detPed.getImporte());
                            return true;
                        }
                    }
                    return false;
                };
                if (!lambda1.pedidoExistente(dp)) {

                    listPedidos.add(dp);
                }
                frmInicioC.spnCantidad.setValue(1);
                mostrarProPedidoTabla();
            }
        }

        if (e.getSource() == frmInicioC.btnQuitar) {

            if (frmInicioC.tblProdTienda.getSelectedRow() != -1) {
                ArrayList<DetallePedido> listEliminar = new ArrayList<>();
                int filaSeleccionada = frmInicioC.tblProdTienda.getSelectedRow();
                String nombre = String.valueOf(frmInicioC.tblProdTienda.getValueAt(filaSeleccionada, 0));

                for (DetallePedido p : listPedidos) {

                    if (p.getProducto().getNombreProducto().equals(nombre)) {
                        listEliminar.add(p);
                    }
                }
                listPedidos.removeAll(listEliminar);
                mostrarProPedidoTabla();
            } else {

                JOptionPane.showMessageDialog(null, "Seleccione una fila",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == frmInicioC.btnComprar) {

            ped.setUsuarioCliente(usuCli);
            pedDao.crear(ped);
            pedDao.obtenerIdActual(ped);
            for (DetallePedido dp : listPedidos) {
                dp.setPedido(ped);
                detPedDao.crear(dp);
            }
            JOptionPane.showMessageDialog(null, "Compra realizada");
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        if (e.getSource() == frmInicioC.spnCantidad) {

            prodDao.listar().forEach((p) -> {
                if (p.getNombreProducto().equals(frmInicioC.cboProductos.getSelectedItem())) {

                    detPed.setProducto(p);
                    detPed.setCantidad(Integer.parseInt(frmInicioC.spnCantidad.getValue().toString()));
                    frmInicioC.lblPrecio.setText("S/. " + " " + p.getPrecio());
                    detPed.setImporte(p.getPrecio() * (double) detPed.getCantidad());
                    frmInicioC.lblImporte.setText("S/. " + " " + detPed.getImporte());
                }
            });
        }
    }

    private void listarComboProd() {
        int i = 0;
        int numProd = prodDao.listar().size();
        String[] productos = new String[numProd];

        for (Producto p : prodDao.listar()) {
            productos[i] = p.getNombreProducto();
            i++;
        }
        DefaultComboBoxModel<String> comboModelo = new DefaultComboBoxModel<>(productos);
        frmInicioC.cboProductos.setModel(comboModelo);
    }

    private void mostrarProPedidoTabla() {

        double subtotal = 0;
        String[] cabecera = { "Producto", "Precio", "Cantidad", "Importe" };
        String[] registros = new String[10];
        DefaultTableModel modelo = new DefaultTableModel(null, cabecera);

        for (DetallePedido p : listPedidos) {
            registros[0] = p.getProducto().getNombreProducto();
            registros[1] = String.valueOf(p.getProducto().getPrecio());
            registros[2] = String.valueOf(p.getCantidad());
            registros[3] = String.valueOf(p.getImporte());
            subtotal += p.getImporte();
            modelo.addRow(registros);
        }

        double igv = subtotal * 0.18;
        double total = subtotal + igv;
        ped.setSubtotal(subtotal);
        ped.setIgv(igv);
        ped.setTotal(total);
        frmInicioC.lblSubtotal.setText("S/. " + String.valueOf(subtotal));
        frmInicioC.lblIgv.setText("S/. " + String.valueOf(igv));
        frmInicioC.lblTotal.setText("S/. " + String.valueOf(total));
        frmInicioC.tblProdTienda.setModel(modelo);
    }

    private void mostrarEmpTabla() {

        String[] cabecera = { "IdEmpleado", "Usuario", "Clave", "Nombres", "Apellidos" };
        String[] registros = new String[10];
        DefaultTableModel modelo = new DefaultTableModel(null, cabecera);

        for (UsuarioEmpleado u : usuEmpDao.listar()) {

            registros[0] = String.valueOf(u.getIdEmpleado());
            registros[1] = u.getUsuario();
            registros[2] = u.getClave();
            registros[3] = u.getNombres();
            registros[4] = u.getApellidos();
            modelo.addRow(registros);
        }

        frmInicioE.tblEmpleados.setModel(modelo);
    }

    private void buscarEmpTabla(UsuarioEmpleado u) {

        String[] cabecera = { "IdEmpleado", "Usuario", "Clave", "Nombres", "Apellidos" };
        String[] registros = new String[10];
        DefaultTableModel modelo = new DefaultTableModel(null, cabecera);

        usuEmpDao.buscar(u);

        registros[0] = String.valueOf(u.getIdEmpleado());
        registros[1] = u.getUsuario();
        registros[2] = u.getClave();
        registros[3] = u.getNombres();
        registros[4] = u.getApellidos();
        modelo.addRow(registros);

        frmInicioE.tblEmpleados.setModel(modelo);
    }

    private void mostrarProTabla() {

        String[] cabecera = { "IdProducto", "Producto", "Categoria", "Stock", "Precio" };
        String[] registros = new String[10];
        DefaultTableModel modelo = new DefaultTableModel(null, cabecera);

        for (Producto p : prodDao.listar()) {
            registros[0] = String.valueOf(p.getIdProducto());
            registros[1] = p.getNombreProducto();
            registros[2] = p.getCategoria();
            registros[3] = String.valueOf(p.getStock());
            registros[4] = String.valueOf(p.getPrecio());
            modelo.addRow(registros);
        }

        frmInicioE.tblProductos.setModel(modelo);
    }

    private void buscarProTabla(Producto p) {

        String[] cabecera = { "IdProducto", "Producto", "Categoria", "Stock", "Precio" };
        String[] registros = new String[10];
        DefaultTableModel modelo = new DefaultTableModel(null, cabecera);

        prodDao.buscar(p);

        registros[0] = String.valueOf(p.getIdProducto());
        registros[1] = p.getNombreProducto();
        registros[2] = p.getCategoria();
        registros[3] = String.valueOf(p.getStock());
        registros[4] = String.valueOf(p.getPrecio());
        modelo.addRow(registros);

        frmInicioE.tblProductos.setModel(modelo);
    }

    private boolean validarCamposRegistro() {

        if (frmReg.txtUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Usuario", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (frmReg.txtNombres.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Nombres", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (frmReg.txtApellidos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Apellidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(frmReg.txtClave.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Clave", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(frmReg.txtConfirmarClave.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Confirmar Clave", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(frmReg.txtClave.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Clave", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(frmReg.txtTelefono.getText()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Teléfono", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (frmReg.txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Dirección", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!esNumerico(frmReg.txtDni.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un DNI con valor numérico", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!esNumerico(frmReg.txtTelefono.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un Teléfono con valor numérico", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            return true;
        }
        return false;
    }

    public boolean validarCamposEmpleado() {

        if (frmInicioE.txtClave.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Clave", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (frmInicioE.txtNombres.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Nombres", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (frmInicioE.txtApellidos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Apellidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            return true;
        }
        return false;
    }

    public boolean validarCamposProducto() {
        if (frmInicioE.txtProducto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Producto", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (frmInicioE.txtCategoria.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Categoría", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (frmInicioE.txtStock.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Stock", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (frmInicioE.txtPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Precio", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!esNumerico(frmInicioE.txtStock.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un Stock válido", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!esNumerico(frmInicioE.txtPrecio.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un Precio válido", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            return true;
        }
        return false;
    }

    private boolean esNumerico(String str) {

        try {
            int entero = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void limpiarRegistroCliente() {

        frmReg.txtUsuario.setText("");
        frmReg.txtClave.setText("");
        frmReg.txtConfirmarClave.setText("");
        frmReg.txtDni.setText("");
        frmReg.txtTelefono.setText("");
        frmReg.txtDireccion.setText("");
    }

    private void limpiarLoginCliente() {

        frmLoginC.txtUsuario.setText("");
        frmLoginC.txtClave.setText("");
        frmLoginC.lblMensajeInicio.setText("");
    }

    private void limpiarLoginEmpleado() {

        frmLoginE.txtCodEmpleado.setText("");
        frmLoginE.txtClave.setText("");
        frmLoginE.lblMensajeInicio.setText("");
    }

    public void limpiarCajasEmpleado() {

        frmInicioE.txtIdEmpleado.setText("");
        frmInicioE.txtNombres.setText("");
        frmInicioE.txtApellidos.setText("");
        frmInicioE.txtClave.setText("");
    }

    public void limpiarCajasProducto() {

        frmInicioE.txtProducto.setText("");
        frmInicioE.txtCategoria.setText("");
        frmInicioE.txtStock.setText("");
        frmInicioE.txtPrecio.setText("");
    }
}
