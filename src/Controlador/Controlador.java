package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import Modelo.Categoria;
import Modelo.DAOCategoriaImpl;
import Modelo.DAODetallePedidoImpl;
import Modelo.DAOPedidoImpl;
import Modelo.DAOProductoImpl;
import Modelo.DAOUsuarioClienteImpl;
import Modelo.DAOUsuarioEmpleadoImpl;
import Modelo.DetallePedido;
import Modelo.ICargarCombo;
import Modelo.IPedidoExistente;
import Modelo.Pedido;
import Modelo.Producto;
import Modelo.UsuarioCliente;
import Modelo.UsuarioEmpleado;
import Vista.VistaInicioCliente;
import Vista.VistaInicioEmpleado;
import Vista.VistaLoginCliente;
import Vista.VistaLoginEmpleado;
import Vista.VistaRegistro;

public class Controlador implements ActionListener, ChangeListener {

    private UsuarioCliente usuCli;
    private DAOUsuarioClienteImpl usuCliDao;
    private VistaLoginCliente vistaLoginC;
    private VistaInicioCliente vistaInicioC;
    private VistaRegistro vistaReg;
    private UsuarioEmpleado usuEmp;
    private DAOUsuarioEmpleadoImpl usuEmpDao;
    private VistaLoginEmpleado vistaLoginE;
    private VistaInicioEmpleado vistaInicioE;
    private Producto prod;
    private DAOProductoImpl prodDao;
    private Pedido ped;
    private DAOPedidoImpl pedDao;
    private DetallePedido detPed;
    private DAODetallePedidoImpl detPedDao;
    private Categoria cat;
    private DAOCategoriaImpl catDao;
    private ArrayList<DetallePedido> listPedidos = new ArrayList<>();
    private int idEmpleado;

    public Controlador(UsuarioCliente usuCli, DAOUsuarioClienteImpl usuCliDao, VistaLoginCliente vistaLoginC,
            VistaInicioCliente vistaInicioC, VistaRegistro vistaReg, UsuarioEmpleado usuEmp,
            DAOUsuarioEmpleadoImpl usuEmpDao, VistaLoginEmpleado vistaLoginE, VistaInicioEmpleado vistaInicioE,
            Producto prod, DAOProductoImpl prodDao, Pedido ped, DAOPedidoImpl pedDao, DetallePedido detPed,
            DAODetallePedidoImpl detPedDao, Categoria cat, DAOCategoriaImpl catDao) {
        this.usuCli = usuCli;
        this.usuCliDao = usuCliDao;
        this.vistaLoginC = vistaLoginC;
        this.vistaInicioC = vistaInicioC;
        this.vistaReg = vistaReg;
        this.usuEmp = usuEmp;
        this.usuEmpDao = usuEmpDao;
        this.vistaLoginE = vistaLoginE;
        this.vistaInicioE = vistaInicioE;
        this.prod = prod;
        this.prodDao = prodDao;
        this.ped = ped;
        this.pedDao = pedDao;
        this.detPed = detPed;
        this.detPedDao = detPedDao;
        this.cat = cat;
        this.catDao = catDao;
        this.vistaLoginC.btnIniciarSesion.addActionListener(this);
        this.vistaLoginC.btnLoginEmpleado.addActionListener(this);
        this.vistaLoginC.btnRegistrarse.addActionListener(this);
        this.vistaInicioC.btnCerrar.addActionListener(this);
        this.vistaReg.btnRegistrarse.addActionListener(this);
        this.vistaReg.btnRegresar.addActionListener(this);
        this.vistaLoginE.btnIniciarSesion.addActionListener(this);
        this.vistaLoginE.btnRegresar.addActionListener(this);
        this.vistaInicioE.btnCerrarSesion.addActionListener(this);
        this.vistaInicioE.btnAgregarEmpleado.addActionListener(this);
        this.vistaInicioE.btnEliminarEmpleado.addActionListener(this);
        this.vistaInicioE.btnActualizarEmpleado.addActionListener(this);
        this.vistaInicioE.btnBuscarEmpleado.addActionListener(this);
        this.vistaInicioE.btnAgregarProducto.addActionListener(this);
        this.vistaInicioE.btnActualizarProducto.addActionListener(this);
        this.vistaInicioE.btnEliminarProducto.addActionListener(this);
        this.vistaInicioE.btnBuscarProducto.addActionListener(this);
        this.vistaInicioC.cboProductos.addActionListener(this);
        this.vistaInicioC.spnCantidad.addChangeListener(this);
        this.vistaInicioC.btnAgregar.addActionListener(this);
        this.vistaInicioC.btnQuitar.addActionListener(this);
        this.vistaInicioC.btnComprar.addActionListener(this);
        this.vistaInicioC.btnActualizar.addActionListener(this);
    }

    public void iniciar() {
        vistaLoginC.setTitle("Login Cliente");
        vistaLoginC.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaLoginC.btnIniciarSesion) {

            usuCli.setUsuario(vistaLoginC.txtUsuario.getText());
            usuCli.setClave(String.valueOf(vistaLoginC.txtClave.getPassword()));
            prod.setCategoria(cat);

            if (usuCliDao.iniciarSesion(usuCli)) {

                vistaLoginC.setVisible(false);
                usuCliDao.buscar(usuCli);
                vistaInicioC.spnCantidad.setValue(1);

                // Función Lambda 1
                ICargarCombo lambda1 = () -> {
                    int i = 0;
                    int numProd = prodDao.listar().size();
                    String[] productos = new String[numProd];

                    for (Producto p : prodDao.listar()) {
                        productos[i] = p.getNombreProducto();
                        i++;
                    }
                    DefaultComboBoxModel<String> comboModelo = new DefaultComboBoxModel<>(productos);
                    vistaInicioC.cboProductos.setModel(comboModelo);
                };
                lambda1.cargarCombo();

                // Función Lambda 2
                prodDao.listar().forEach((p) -> {
                    if (p.getNombreProducto().equals(vistaInicioC.cboProductos.getSelectedItem())) {

                        detPed.setProducto(p);
                        detPed.setCantidad(Integer.parseInt(vistaInicioC.spnCantidad.getValue().toString()));
                        vistaInicioC.lblPrecio.setText("S/. " + " " + p.getPrecio());
                        detPed.setImporte(p.getPrecio() * (double) detPed.getCantidad());
                        vistaInicioC.lblImporte.setText("S/. " + " " + detPed.getImporte());
                    }
                });
                vistaInicioC.setVisible(true);
                vistaInicioC.lblCliente.setText("Usuario - " + usuCli.getNombres());

            } else {

                vistaLoginC.lblMensajeInicio.setText("Usuario y/o Clave incorrectos");
            }
        }

        if (e.getSource() == vistaLoginC.btnRegistrarse) {

            vistaLoginC.setVisible(false);
            limpiarRegistroCliente();
            vistaReg.setVisible(true);
        }

        if (e.getSource() == vistaLoginC.btnLoginEmpleado) {

            vistaLoginC.setVisible(false);
            limpiarLoginEmpleado();
            vistaLoginE.setVisible(true);
        }

        if (e.getSource() == vistaInicioC.btnCerrar) {

            vistaInicioC.setVisible(false);
            limpiarLoginCliente();
            vistaLoginC.setVisible(true);
        }

        if (e.getSource() == vistaReg.btnRegistrarse) {

            if (validarCamposRegistro()) {

                usuCli.setUsuario(vistaReg.txtUsuario.getText());
                usuCli.setNombres(String.valueOf(vistaReg.txtNombres.getText()));
                usuCli.setApellidos(String.valueOf(vistaReg.txtApellidos.getText()));
                usuCli.setClave(String.valueOf(vistaReg.txtClave.getPassword()));
                usuCli.setDireccion(vistaReg.txtDireccion.getText());
                usuCli.setDni(Integer.parseInt(vistaReg.txtDni.getText()));
                usuCli.setTelefono(Integer.parseInt(vistaReg.txtTelefono.getText()));

                if (String.valueOf(vistaReg.txtClave.getPassword())
                        .equals(String.valueOf(vistaReg.txtConfirmarClave.getPassword()))) {

                    if (usuCliDao.crear(usuCli)) {

                        JOptionPane.showMessageDialog(null, "Registrado correctamente", "Registrado",
                                JOptionPane.INFORMATION_MESSAGE);
                        vistaReg.setVisible(false);
                        limpiarLoginCliente();
                        vistaLoginC.setVisible(true);

                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Claves ingresadas diferentes",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getSource() == vistaReg.btnRegresar) {

            vistaReg.setVisible(false);
            limpiarLoginCliente();
            vistaLoginC.setVisible(true);
        }

        if (e.getSource() == vistaLoginE.btnIniciarSesion) {

            usuEmp.setUsuario(vistaLoginE.txtCodEmpleado.getText());
            usuEmp.setClave(String.valueOf(vistaLoginE.txtClave.getPassword()));

            if (usuEmpDao.iniciarSesion(usuEmp)) {

                vistaLoginE.setVisible(false);
                usuEmpDao.buscar(usuEmp);
                idEmpleado = usuEmp.getIdEmpleado();
                vistaInicioE.lblEmpleado.setText("Empleado - " + usuEmp.getNombres());
                mostrarEmpTabla();
                mostrarProTabla();

                // Función Lambda 3
                ICargarCombo lambda3 = () -> {
                    int i = 0;
                    int numCat = catDao.listar().size();
                    String[] categorias = new String[numCat];

                    for (Categoria c : catDao.listar()) {
                        categorias[i] = c.getNombre();
                        i++;
                    }
                    DefaultComboBoxModel<String> comboModelo = new DefaultComboBoxModel<>(categorias);
                    vistaInicioE.cboCategoria.setModel(comboModelo);
                };
                lambda3.cargarCombo();

                cat.setNombre(String.valueOf(vistaInicioE.cboCategoria.getSelectedItem()));
                catDao.buscarPorNombre(cat);
                prod.setCategoria(cat);
                vistaInicioE.setVisible(true);

            } else {

                vistaLoginE.lblMensajeInicio.setText("Código Empleado y/o Clave incorrectos");
            }
        }

        if (e.getSource() == vistaLoginE.btnRegresar) {

            vistaLoginE.setVisible(false);
            limpiarLoginCliente();
            vistaLoginC.setVisible(true);
        }

        if (e.getSource() == vistaInicioE.btnCerrarSesion) {

            vistaInicioE.setVisible(false);
            limpiarLoginEmpleado();
            vistaLoginE.setVisible(true);
        }

        if (e.getSource() == vistaInicioE.btnAgregarEmpleado) {

            if (validarCamposEmpleado()) {

                usuEmp.setUsuario(vistaInicioE.txtUsuario.getText());
                usuEmp.setClave(vistaInicioE.txtClave.getText());
                usuEmp.setNombres(vistaInicioE.txtNombres.getText());
                usuEmp.setApellidos(vistaInicioE.txtApellidos.getText());

                if (usuEmpDao.crear(usuEmp)) {

                    mostrarEmpTabla();
                    limpiarCajasEmpleado();
                }
            }
        }

        if (e.getSource() == vistaInicioE.btnEliminarEmpleado) {

            if (vistaInicioE.tblEmpleados.getSelectedRow() != -1) {

                int filaSeleccionada = vistaInicioE.tblEmpleados.getSelectedRow();
                String id = String.valueOf(vistaInicioE.tblEmpleados.getValueAt(filaSeleccionada, 0));
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

        if (e.getSource() == vistaInicioE.btnActualizarEmpleado) {

            if (validarCamposEmpleado()) {

                usuEmp.setUsuario(vistaInicioE.txtUsuario.getText());
                usuEmp.setClave(vistaInicioE.txtClave.getText());
                usuEmp.setNombres(vistaInicioE.txtNombres.getText());
                usuEmp.setApellidos(vistaInicioE.txtApellidos.getText());

                if (vistaInicioE.tblEmpleados.getSelectedRow() != -1) {
                    int filaSeleccionada = vistaInicioE.tblEmpleados.getSelectedRow();
                    String id = String.valueOf(vistaInicioE.tblEmpleados.getValueAt(filaSeleccionada, 0));
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

        if (e.getSource() == vistaInicioE.btnBuscarEmpleado) {

            if (vistaInicioE.txtIdEmpleado.getText().equals("") || vistaInicioE.txtIdEmpleado.getText() == null) {
                mostrarEmpTabla();

            } else {

                usuEmp.setIdEmpleado(Integer.parseInt(vistaInicioE.txtIdEmpleado.getText()));
                buscarEmpTabla(usuEmp);
            }
        }

        if (e.getSource() == vistaInicioE.btnAgregarProducto) {

            if (validarCamposProducto()) {
                prod.setNombreProducto(vistaInicioE.txtProducto.getText());
                cat.setNombre(String.valueOf(vistaInicioE.cboCategoria.getSelectedItem()));
                catDao.buscarPorNombre(cat);
                prod.setCategoria(cat);
                System.out.println("Id Empleado " + idEmpleado);
                usuEmp.setIdEmpleado(idEmpleado);
                prod.setUsuarioEmpleado(usuEmp);
                prod.setStock(Integer.parseInt(vistaInicioE.txtStock.getText()));
                prod.setPrecio(Double.parseDouble(vistaInicioE.txtPrecio.getText()));
                System.out.println("Producto " + prod.toString());
                if (prodDao.crear(prod)) {

                    mostrarProTabla();
                    limpiarCajasProducto();
                }
            }
        }

        if (e.getSource() == vistaInicioE.btnEliminarProducto) {

            if (vistaInicioE.tblProductos.getSelectedRow() != -1) {

                int filaSeleccionada = vistaInicioE.tblProductos.getSelectedRow();
                String id = String.valueOf(vistaInicioE.tblProductos.getValueAt(filaSeleccionada, 0));
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

        if (e.getSource() == vistaInicioE.btnActualizarProducto) {

            if (validarCamposProducto()) {

                prod.setNombreProducto(vistaInicioE.txtProducto.getText());
                cat.setNombre(String.valueOf(vistaInicioE.cboCategoria.getSelectedItem()));
                catDao.buscarPorNombre(cat);
                prod.setCategoria(cat);
                prod.setStock(Integer.parseInt(vistaInicioE.txtStock.getText()));
                prod.setPrecio(Double.parseDouble(vistaInicioE.txtPrecio.getText()));

                if (vistaInicioE.tblProductos.getSelectedRow() != -1) {

                    int filaSeleccionada = vistaInicioE.tblProductos.getSelectedRow();
                    String id = String.valueOf(vistaInicioE.tblProductos.getValueAt(filaSeleccionada, 0));
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

        if (e.getSource() == vistaInicioE.btnBuscarProducto) {

            if (vistaInicioE.txtIdProducto.getText().equals("") || vistaInicioE.txtIdProducto.getText() == null) {
                mostrarProTabla();

            } else {

                prod.setIdProducto(Integer.parseInt(vistaInicioE.txtIdProducto.getText()));
                buscarProTabla(prod);
            }
        }

        if (e.getSource() == vistaInicioC.cboProductos) {

            prodDao.listar().forEach((p) -> {
                if (p.getNombreProducto().equals(vistaInicioC.cboProductos.getSelectedItem())) {

                    detPed.setProducto(p);
                    detPed.setCantidad(Integer.parseInt(vistaInicioC.spnCantidad.getValue().toString()));
                    vistaInicioC.lblPrecio.setText("S/. " + " " + p.getPrecio());
                    detPed.setImporte(p.getPrecio() * (double) detPed.getCantidad());
                    vistaInicioC.lblImporte.setText("S/. " + " " + detPed.getImporte());
                }
            });
        }

        if (e.getSource() == vistaInicioC.btnAgregar) {

            DetallePedido dp = new DetallePedido();
            dp.setProducto(detPed.getProducto());
            dp.setCantidad(detPed.getCantidad());
            dp.setImporte(detPed.getImporte());

            if (dp.getCantidad() != 0) {

                // Función Lambda 4
                IPedidoExistente lambda4 = (detPed) -> {
                    for (DetallePedido p : listPedidos) {

                        if (p.getProducto().getNombreProducto().equals(detPed.getProducto().getNombreProducto())) {

                            p.setCantidad(detPed.getCantidad());
                            p.setImporte(detPed.getImporte());
                            return true;
                        }
                    }
                    return false;
                };
                if (!lambda4.pedidoExistente(dp)) {

                    listPedidos.add(dp);
                }
                vistaInicioC.spnCantidad.setValue(1);
                mostrarProPedidoTabla();
            }
        }

        if (e.getSource() == vistaInicioC.btnQuitar) {

            if (vistaInicioC.tblProdTienda.getSelectedRow() != -1) {
                ArrayList<DetallePedido> listEliminar = new ArrayList<>();
                int filaSeleccionada = vistaInicioC.tblProdTienda.getSelectedRow();
                String nombre = String.valueOf(vistaInicioC.tblProdTienda.getValueAt(filaSeleccionada, 0));

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

        if (e.getSource() == vistaInicioC.btnComprar) {

            ped.setUsuarioCliente(usuCli);
            pedDao.crear(ped);
            pedDao.obtenerIdActual(ped);
            for (DetallePedido dp : listPedidos) {
                dp.setPedido(ped);
                detPedDao.crear(dp);
            }
            JOptionPane.showMessageDialog(null, "Compra realizada");
        }

        if (e.getSource() == vistaInicioC.btnActualizar) {
            if (validarCamposActualizacion()) {

                usuCli.setNombres(String.valueOf(vistaInicioC.txtNombres.getText()));
                usuCli.setApellidos(String.valueOf(vistaInicioC.txtApellidos.getText()));
                usuCli.setClave(String.valueOf(vistaInicioC.txtClave.getPassword()));
                usuCli.setDireccion(vistaInicioC.txtDireccion.getText());
                usuCli.setDni(Integer.parseInt(vistaInicioC.txtDni.getText()));
                usuCli.setTelefono(Integer.parseInt(vistaInicioC.txtTelefono.getText()));

                if (String.valueOf(vistaInicioC.txtClave.getPassword())
                        .equals(String.valueOf(vistaInicioC.txtConfirmarClave.getPassword()))) {

                    if (usuCliDao.actualizar(usuCli)) {

                        JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Actualizado",
                                JOptionPane.INFORMATION_MESSAGE);
                        vistaInicioC.lblCliente.setText("Usuario - " + usuCli.getNombres());
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Claves ingresadas diferentes",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    @Override
    public void stateChanged(ChangeEvent e) {

        if (e.getSource() == vistaInicioC.spnCantidad) {

            prodDao.listar().forEach((p) -> {
                if (p.getNombreProducto().equals(vistaInicioC.cboProductos.getSelectedItem())) {

                    detPed.setProducto(p);
                    detPed.setCantidad(Integer.parseInt(vistaInicioC.spnCantidad.getValue().toString()));
                    vistaInicioC.lblPrecio.setText("S/. " + " " + p.getPrecio());
                    detPed.setImporte(p.getPrecio() * (double) detPed.getCantidad());
                    vistaInicioC.lblImporte.setText("S/. " + " " + detPed.getImporte());
                }
            });
        }
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

        double igv = Math.round(subtotal * 0.18 * 100.0) / 100.0;
        double total = subtotal + igv;
        ped.setSubtotal(subtotal);
        ped.setIgv(igv);
        ped.setTotal(total);
        vistaInicioC.lblSubtotal.setText("S/. " + String.valueOf(subtotal));
        vistaInicioC.lblIgv.setText("S/. " + String.valueOf(igv));
        vistaInicioC.lblTotal.setText("S/. " + String.valueOf(total));
        vistaInicioC.tblProdTienda.setModel(modelo);
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

        vistaInicioE.tblEmpleados.setModel(modelo);
    }

    private void buscarEmpTabla(UsuarioEmpleado u) {

        String[] cabecera = { "IdEmpleado", "Usuario", "Clave", "Nombres", "Apellidos" };
        String[] registros = new String[10];
        DefaultTableModel modelo = new DefaultTableModel(null, cabecera);

        if (usuEmpDao.buscar(u)) {

            registros[0] = String.valueOf(u.getIdEmpleado());
            registros[1] = u.getUsuario();
            registros[2] = u.getClave();
            registros[3] = u.getNombres();
            registros[4] = u.getApellidos();
            modelo.addRow(registros);
        }

        vistaInicioE.tblEmpleados.setModel(modelo);
    }

    private void mostrarProTabla() {

        String[] cabecera = { "IdProducto", "Producto", "Categoria", "Stock", "Precio" };
        String[] registros = new String[10];
        DefaultTableModel modelo = new DefaultTableModel(null, cabecera);

        for (Producto p : prodDao.listar()) {

            registros[0] = String.valueOf(p.getIdProducto());
            registros[1] = p.getNombreProducto();
            catDao.buscar(p.getCategoria());
            registros[2] = p.getCategoria().getNombre();
            registros[3] = String.valueOf(p.getStock());
            registros[4] = String.valueOf(p.getPrecio());
            modelo.addRow(registros);
        }

        vistaInicioE.tblProductos.setModel(modelo);
    }

    private void buscarProTabla(Producto p) {

        String[] cabecera = { "IdProducto", "Producto", "Categoria", "Stock", "Precio" };
        String[] registros = new String[10];
        DefaultTableModel modelo = new DefaultTableModel(null, cabecera);

        catDao.buscar(p.getCategoria());

        if (prodDao.buscar(p)) {

            registros[0] = String.valueOf(p.getIdProducto());
            registros[1] = p.getNombreProducto();
            registros[2] = p.getCategoria().getNombre();
            registros[3] = String.valueOf(p.getStock());
            registros[4] = String.valueOf(p.getPrecio());
            modelo.addRow(registros);
        }

        vistaInicioE.tblProductos.setModel(modelo);
    }

    private boolean validarCamposRegistro() {

        if (vistaReg.txtUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Usuario", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (vistaReg.txtNombres.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Nombres", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (vistaReg.txtApellidos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Apellidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(vistaReg.txtClave.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Clave", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(vistaReg.txtConfirmarClave.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Confirmar Clave", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(vistaReg.txtDni.getText()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese DNI", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(vistaReg.txtTelefono.getText()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Teléfono", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (vistaReg.txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Dirección", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!esNumerico(vistaReg.txtDni.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un DNI con valor numérico", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!esNumerico(vistaReg.txtTelefono.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un Teléfono con valor numérico", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            return true;
        }
        return false;
    }

    private boolean validarCamposActualizacion() {

        if (vistaInicioC.txtNombres.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Nombres", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (vistaInicioC.txtApellidos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Apellidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(vistaInicioC.txtClave.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Clave", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(vistaInicioC.txtConfirmarClave.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Confirmar Clave", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(vistaInicioC.txtDni.getText()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese DNI", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (String.valueOf(vistaInicioC.txtTelefono.getText()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Teléfono", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (vistaInicioC.txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Dirección", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!esNumerico(vistaInicioC.txtDni.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un DNI con valor numérico", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!esNumerico(vistaInicioC.txtTelefono.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un Teléfono con valor numérico", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            return true;
        }
        return false;
    }

    public boolean validarCamposEmpleado() {
        if (vistaInicioE.txtUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Usuario", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (vistaInicioE.txtClave.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Clave", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (vistaInicioE.txtNombres.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Nombres", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (vistaInicioE.txtApellidos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Apellidos", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            return true;
        }
        return false;
    }

    public boolean validarCamposProducto() {
        if (vistaInicioE.txtProducto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Producto", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (vistaInicioE.txtStock.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Stock", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (vistaInicioE.txtPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Precio", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!esNumerico(vistaInicioE.txtStock.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un Stock válido", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!esNumerico(vistaInicioE.txtPrecio.getText())) {
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

        vistaReg.txtUsuario.setText("");
        vistaReg.txtClave.setText("");
        vistaReg.txtConfirmarClave.setText("");
        vistaReg.txtDni.setText("");
        vistaReg.txtTelefono.setText("");
        vistaReg.txtDireccion.setText("");
    }

    private void limpiarLoginCliente() {

        vistaLoginC.txtUsuario.setText("");
        vistaLoginC.txtClave.setText("");
        vistaLoginC.lblMensajeInicio.setText("");
    }

    private void limpiarLoginEmpleado() {

        vistaLoginE.txtCodEmpleado.setText("");
        vistaLoginE.txtClave.setText("");
        vistaLoginE.lblMensajeInicio.setText("");
    }

    public void limpiarCajasEmpleado() {

        vistaInicioE.txtUsuario.setText("");
        vistaInicioE.txtClave.setText("");
        vistaInicioE.txtNombres.setText("");
        vistaInicioE.txtApellidos.setText("");
    }

    public void limpiarCajasProducto() {

        vistaInicioE.txtProducto.setText("");
        vistaInicioE.txtStock.setText("");
        vistaInicioE.txtPrecio.setText("");
    }
}