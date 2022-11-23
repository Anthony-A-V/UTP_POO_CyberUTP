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
import Vista.VistaInicioCliente;
import Vista.VistaInicioEmpleado;
import Vista.VistaLoginCliente;
import Vista.VistaLoginEmpleado;
import Vista.VistaRegistro;

public class CyberUTP {

    public static void main(String[] args) {
        UsuarioCliente modUsuCli = new UsuarioCliente();
        DAOUsuarioClienteImpl modUsuCliDao = new DAOUsuarioClienteImpl();
        VistaLoginCliente vistaLoginC = new VistaLoginCliente();
        VistaInicioCliente vistaInicioC = new VistaInicioCliente();
        VistaRegistro vistaReg = new VistaRegistro();
        UsuarioEmpleado modUsuEmp = new UsuarioEmpleado();
        DAOUsuarioEmpleadoImpl modUsuEmpDao = new DAOUsuarioEmpleadoImpl();
        VistaLoginEmpleado vistaLoginE = new VistaLoginEmpleado();
        VistaInicioEmpleado vistaInicioE = new VistaInicioEmpleado();
        Producto modProd = new Producto();
        DAOProductoImpl modProdDao = new DAOProductoImpl();
        Pedido modPed = new Pedido();
        DAOPedidoImpl modPedDao = new DAOPedidoImpl();
        DetallePedido modDetPed = new DetallePedido();
        DAODetallePedidoImpl modDetPedDao = new DAODetallePedidoImpl();

        Controlador ctrl = new Controlador(modUsuCli, modUsuCliDao, vistaLoginC, vistaInicioC,
                vistaReg, modUsuEmp, modUsuEmpDao, vistaLoginE, vistaInicioE, modProd, modProdDao, modPed, modPedDao,
                modDetPed, modDetPedDao);
        ctrl.iniciar();
        vistaLoginC.setVisible(true);
    }
}