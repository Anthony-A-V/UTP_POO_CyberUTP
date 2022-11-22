package cyberutp;

import Controlador.Controlador;
import Modelo.DAODetallePedidoImpl;
import Modelo.DAOPedidoImpl;
import Modelo.DAOProductoImpl;
import Modelo.DAOUsuarioClienteImpl;
import Modelo.DAOUsuarioEmpleadoImpl;
import Modelo.DetallePedido;
import Modelo.Pedido;
import Modelo.Producto;
import Modelo.UsuarioCliente;
import Modelo.UsuarioEmpleado;
import Vista.frmInicioCliente;
import Vista.frmInicioEmpleado;
import Vista.frmLoginCliente;
import Vista.frmLoginEmpleado;
import Vista.frmRegistro;

public class CyberUTP {

    public static void main(String[] args) {
        UsuarioCliente modUsuCli = new UsuarioCliente();
        DAOUsuarioClienteImpl modUsuCliDao = new DAOUsuarioClienteImpl();
        frmLoginCliente frmLoginC = new frmLoginCliente();
        frmInicioCliente frmInicioC = new frmInicioCliente();
        frmRegistro frmReg = new frmRegistro();
        UsuarioEmpleado modUsuEmp = new UsuarioEmpleado();
        DAOUsuarioEmpleadoImpl modUsuEmpDao = new DAOUsuarioEmpleadoImpl();
        frmLoginEmpleado frmLoginE = new frmLoginEmpleado();
        frmInicioEmpleado frmInicioE = new frmInicioEmpleado();
        Producto modProd = new Producto();
        DAOProductoImpl modProdDao = new DAOProductoImpl();
        Pedido modPed = new Pedido();
        DAOPedidoImpl modPedDao = new DAOPedidoImpl();
        DetallePedido modDetPed = new DetallePedido();
        DAODetallePedidoImpl modDetPedDao = new DAODetallePedidoImpl();

        Controlador ctrl = new Controlador(modUsuCli, modUsuCliDao, frmLoginC, frmInicioC,
                frmReg, modUsuEmp, modUsuEmpDao, frmLoginE, frmInicioE, modProd, modProdDao, modPed, modPedDao,
                modDetPed, modDetPedDao);
        ctrl.iniciar();
        frmLoginC.setVisible(true);
    }
}