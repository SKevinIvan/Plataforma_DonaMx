/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.inicio;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manipula.ManipulaAutenticacion;
import manipula.ManipulaRol;
import model.Rol;
import model.Usuario;
import utils.GenericResponse;

/**
 *
 * @author Kevin Ivan Sanchez Valdin
 */
public class Srv_inicio_sesion extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/iniciar_session.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //  if (session != null) {
//            if (request.isRequestedSessionIdValid()) {
//                if (request.isRequestedSessionIdFromCookie()) {
//                    //Se obtubo de una cokkie, y se mantiene la session en la cokkie
//                }
//                //cualquier otra tarea que requiera de una sesion valida
//            } else {
//                //registra un error de session
//            }
        //}
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password_cifrado = utils.Hash.sha1(password);
        GenericResponse<Usuario> respuesta = ManipulaAutenticacion.iniciarSesionUsuario(username, password_cifrado);
        if (respuesta.getResponseObject() != null) {

            HttpSession session = request.getSession();
            session.setAttribute("username", respuesta.getResponseObject().getNombreUsuario());
            session.setAttribute("user", respuesta.getResponseObject());
            session.setAttribute("idUser", respuesta.getResponseObject().getIdUsuario());
            ManipulaRol mRol = new ManipulaRol();
            Rol rol = mRol.encontrarId(respuesta.getResponseObject().getIdRol());
            session.setAttribute("rol", rol.getRol());

            switch (rol.getRol()) {
                case "DONADOR": {
                    //redirectServlet(request, response, "donador");
                    response.sendRedirect("donador");
                }
                break;
                case "DONATARIO": {
                  //  redirectServlet(request, response, "estudiante");
                    response.sendRedirect("estudiante");
                }
                break;
                case "VOLUNTARIO": {
                    //redirectServlet(request, response, "voluntario");
                     response.sendRedirect("voluntario");
                }
                break;
                case "ADMINISTRADOR": {
                    //redirectServlet(request, response, "administrador");
                    response.sendRedirect("administrador");
                }
                break;
                case "PENDIENTE": {
                    redirectServlet(request, response, "Srv_registro?ser=PENDIENTE");
                    
                }
                break;
            }

        } else {
            //no se pudo conectar
            redirectView(request, response, "/iniciarSesion.jsp");
        }

    }

    private void redirectView(HttpServletRequest req, HttpServletResponse resp, String pathView)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(pathView);
        dispatcher.forward(req, resp);
    }

    private void redirectServlet(HttpServletRequest req, HttpServletResponse resp, String servlet)
            throws ServletException, IOException {

        req.getRequestDispatcher(servlet)
                .forward(req, resp);
    }

}
