package manipula;

import config.conexion.ConexionFactory;
import config.conexion.IConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Direccion;
import utils.GenericResponse;
import utils.Logg;

/**
 *
 * @author Kevin Ivan Sanchez Valdin
 */
public class ManipulaDireccion implements Manipula<Direccion> {

    @Override
    public GenericResponse<Direccion> registrar(IConexion conexionDB,Direccion obj) {
        GenericResponse<Direccion> response = new GenericResponse<>();
        
        if (conexionDB.getConexion()!=null) {
            try {
                String sql = "INSERT INTO Direccion ("
                        + "idEstado, "
                        + "idMunicipio, "
                        + "idLocalidad, "
                        + "referencias, "
                        + "calleInteriorYNumero, "
                        + "calleExteriorYNumero, "
                        + "calleFrontal, "
                        + "calleTrasera, "
                        + "calleDerecha, "
                        + "calleIzquierda, "
                        + "tipoAsentamiento, "
                        + "nombreAsentamiento, "
                        + "idUsuario, "
                        + "codigo_postal "
                        + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement registro = conexionDB.getConexion().prepareStatement(sql);
                registro.setInt(1, obj.getIdEstado());
                registro.setInt(2, obj.getIdMunicipio());
                registro.setInt(3, obj.getIdLocalidad());
                registro.setString(4, obj.getReferencias());
                registro.setString(5, obj.getCalleInteriorYNumero());
                registro.setString(6, obj.getCalleExteriorYNumero());
                registro.setString(7, obj.getCalleFrontal());
                registro.setString(8, obj.getCalleTrasera());
                registro.setString(9, obj.getCalleDerecha());
                registro.setString(10, obj.getCalleIzquierda());
                registro.setString(11, obj.getTipoAsentamiento());
                registro.setString(12, obj.getNombreAsentamiento());
                registro.setInt(13, obj.getIdUsuario());
                registro.setString(14, obj.getCodigoPostal());
                int r = registro.executeUpdate();
                if (r > 0) {
                    response.setStatus(utils.Constantes.STATUS_REGISTRO_EXITOSO_BD);
                    response.setResponseObject(obj);
                    response.setMensaje("Registro exitoso en la base de datos");
                } else {
                    response.setStatus(utils.Constantes.STATUS_REGISTRO_FALLIDO_BD);
                    response.setResponseObject(obj);
                    response.setMensaje("Registro fallido en la base de datos");
                }
            } catch (SQLException ex) {
                response.setStatus(utils.Constantes.STATUS_CONEXION_FALLIDA_BD);
                response.setResponseObject(null);
                response.setMensaje("Error de comunicación con la base de datos " + ex.getMessage());
            } finally {
                 
            }
        } else {
            response.setStatus(utils.Constantes.STATUS_CONEXION_FALLIDA_BD);
            response.setResponseObject(null);
            response.setMensaje("Error de conexión a la base de datos");
        }
        return response;
    }

    @Override
    public GenericResponse<Direccion> actualizar(IConexion conexionDB,int id) {
        GenericResponse<Direccion> response = new GenericResponse<>();
        response.setMensaje("Accion no implementada");
        response.setStatus(utils.Constantes.LOGIC_WARNING);
        response.setResponseObject(null);
        return response;
    }

    @Override
    public GenericResponse<Direccion> editar(IConexion conexionDB,int id, Direccion nvoObj) {
        GenericResponse<Direccion> response = new GenericResponse<>();
        
        if (conexionDB.getConexion()!=null) {
            Direccion obj = encontrarId(conexionDB,id);
            if (obj != null) {
                try {
                    String sql = "UPDATE Direccion SET "
                            + "idEstado, "
                            + "idMunicipio, "
                            + "idLocalidad, "
                            + "referencias, "
                            + "calleInteriorYNumero, "
                            + "calleExteriorYNumero, "
                            + "calleFrontal, "
                            + "calleTrasera, "
                            + "calleDerecha, "
                            + "calleIzquierda, "
                            + "tipoAsentamiento, "
                            + "nombreAsentamiento, "
                            + "idUsuario, "
                            + "codigo_postal "
                            + "WHERE idDireccion=?";
                    PreparedStatement registro = conexionDB.getConexion().prepareStatement(sql);
                    registro.setInt(1, obj.getIdEstado());
                    registro.setInt(2, obj.getIdMunicipio());
                    registro.setInt(3, obj.getIdLocalidad());
                    registro.setString(4, obj.getReferencias());
                    registro.setString(5, obj.getCalleInteriorYNumero());
                    registro.setString(6, obj.getCalleExteriorYNumero());
                    registro.setString(7, obj.getCalleFrontal());
                    registro.setString(8, obj.getCalleTrasera());
                    registro.setString(9, obj.getCalleDerecha());
                    registro.setString(10, obj.getCalleIzquierda());
                    registro.setString(11, obj.getTipoAsentamiento());
                    registro.setString(12, obj.getNombreAsentamiento());
                    registro.setInt(13, obj.getIdUsuario());
                    registro.setString(14, obj.getCodigoPostal());
                    registro.setInt(15, id);
                    int r = registro.executeUpdate();
                    if (r > 0) {
                        nvoObj.setIdDireccion(id);
                        response.setStatus(utils.Constantes.STATUS_ACTUALIZACION_EXITOSA_BD);
                        response.setResponseObject(nvoObj);
                        response.setMensaje("Edición exitosa en la base de datos");
                    } else {
                        response.setStatus(utils.Constantes.STATUS_ACTUALIZACION_FALLIDA_BD);
                        response.setResponseObject(obj);
                        response.setMensaje("Edición fallido en la base de datos");
                    }
                } catch (SQLException ex) {
                    response.setStatus(utils.Constantes.STATUS_CONEXION_FALLIDA_BD);
                    response.setResponseObject(null);
                    response.setMensaje("Error de comunicación con la base de datos " + ex.getSQLState());
                } finally {
                     
                }
            } else {
                response.setStatus(utils.Constantes.STATUS_NO_DATA);
                response.setResponseObject(null);
                response.setMensaje("El registro no existe");
                 
            }
        } else {
            response.setStatus(utils.Constantes.STATUS_CONEXION_FALLIDA_BD);
            response.setResponseObject(null);
            response.setMensaje("Error de conexión a la base de datos");
        }
        return response;
    }

    @Override
    public GenericResponse<Direccion> eliminar(IConexion conexionDB,int id) {
        GenericResponse<Direccion> response = new GenericResponse<>();
        
        if (conexionDB.getConexion()!=null) {
            Direccion obj = encontrarId(conexionDB,id);
            if (obj != null) {
                try {
                    String sql = "DELETE FROM Direccion "
                            + "WHERE idDireccion=?";
                    PreparedStatement registro = conexionDB.getConexion().prepareStatement(sql);
                    registro.setInt(1, id);
                    int r = registro.executeUpdate();
                    if (r > 0) {
                        response.setStatus(utils.Constantes.STATUS_ACTUALIZACION_EXITOSA_BD);
                        response.setResponseObject(obj);
                        response.setMensaje("Eliminacion exitosa en la base de datos");
                    } else {
                        response.setStatus(utils.Constantes.STATUS_ACTUALIZACION_FALLIDA_BD);
                        response.setResponseObject(null);
                        response.setMensaje("Eliminacion fallido en la base de datos");
                    }
                } catch (SQLException ex) {
                    response.setStatus(utils.Constantes.STATUS_CONEXION_FALLIDA_BD);
                    response.setResponseObject(null);
                    response.setMensaje("Error de comunicación con la base de datos " + ex.getSQLState());
                } finally {
                     
                }
            } else {
                response.setStatus(utils.Constantes.STATUS_NO_DATA);
                response.setResponseObject(null);
                response.setMensaje("El registro no existe");
                 
            }
        } else {
            response.setStatus(utils.Constantes.STATUS_CONEXION_FALLIDA_BD);
            response.setResponseObject(null);
            response.setMensaje("Error de conexión a la base de datos");
        }
        return response;
    }

    @Override
    public List<Direccion> getData(IConexion conexionDB) {
        List<Direccion> response = new ArrayList<>();
        
        if (conexionDB.getConexion()!=null) {
            try {
                String sql = "SELECT "
                        + "idDireccion, "
                        + "idEstado, "
                        + "idMunicipio, "
                        + "idLocalidad, "
                        + "referencias, "
                        + "calleInteriorYNumero, "
                        + "calleExteriorYNumero, "
                        + "calleFrontal, "
                        + "calleTrasera, "
                        + "calleDerecha, "
                        + "calleIzquierda, "
                        + "tipoAsentamiento, "
                        + "nombreAsentamiento, "
                        + "idUsuario, "
                        + "codigo_postal "
                        + "FROM Direccion";
                PreparedStatement ps = conexionDB.getConexion().prepareStatement(sql);
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    Direccion dir = new Direccion();
                    dir.setIdDireccion(rs.getInt(1));
                    dir.setIdEstado(rs.getInt(2));
                    dir.setIdMunicipio(rs.getInt(3));
                    dir.setIdLocalidad(rs.getInt(4));
                    dir.setReferencias(rs.getString(5));
                    dir.setCalleInteriorYNumero(rs.getString(6));
                    dir.setCalleExteriorYNumero(rs.getString(7));
                    dir.setCalleFrontal(rs.getString(8));
                    dir.setCalleTrasera(rs.getString(9));
                    dir.setCalleDerecha(rs.getString(10));
                    dir.setCalleIzquierda(rs.getString(11));
                    dir.setTipoAsentamiento(rs.getString(12));
                    dir.setNombreAsentamiento(rs.getString(13));
                    dir.setIdUsuario(rs.getInt(14));
                    dir.setCodigoPostal(rs.getString(15));
                    response.add(dir);
                }
            } catch (SQLException ex) {
                Logg.error("Comunicación fallida con la base de datos");
            } finally {
                 
            }
        } else {
            Logg.error("Conexión fallida con la base de datos");
        }
        return response;
    }

    @Override
    public List<Direccion> consultar(IConexion conexionDB,String... filtros) {
        List<Direccion> response = new ArrayList<>();
        
        if (conexionDB.getConexion()!=null) {
            try {
                String sql = "SELECT "
                        + "idDireccion, "
                        + "idEstado, "
                        + "idMunicipio, "
                        + "idLocalidad, "
                        + "referencias, "
                        + "calleInteriorYNumero, "
                        + "calleExteriorYNumero, "
                        + "calleFrontal, "
                        + "calleTrasera, "
                        + "calleDerecha, "
                        + "calleIzquierda, "
                        + "tipoAsentamiento, "
                        + "nombreAsentamiento, "
                        + "idUsuario, "
                        + "codigo_postal "
                        + "FROM Direccion";
                PreparedStatement ps = conexionDB.getConexion().prepareStatement(sql);
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    Direccion dir = new Direccion();
                    dir.setIdDireccion(rs.getInt(1));
                    dir.setIdEstado(rs.getInt(2));
                    dir.setIdMunicipio(rs.getInt(3));
                    dir.setIdLocalidad(rs.getInt(4));
                    dir.setReferencias(rs.getString(5));
                    dir.setCalleInteriorYNumero(rs.getString(6));
                    dir.setCalleExteriorYNumero(rs.getString(7));
                    dir.setCalleFrontal(rs.getString(8));
                    dir.setCalleTrasera(rs.getString(9));
                    dir.setCalleDerecha(rs.getString(10));
                    dir.setCalleIzquierda(rs.getString(11));
                    dir.setTipoAsentamiento(rs.getString(12));
                    dir.setNombreAsentamiento(rs.getString(13));
                    dir.setIdUsuario(rs.getInt(14));
                    dir.setCodigoPostal(rs.getString(15));
                    response.add(dir);
                }
            } catch (SQLException ex) {
                Logg.error("Comunicación fallida con la base de datos");
            } finally {
                 
            }
        } else {
            Logg.error("Conexión fallida con la base de datos");
        }
        return response;
    }

    @Override
    public Direccion encontrarId(IConexion conexionDB,int id) {
        Direccion response = new Direccion();
        
        if (conexionDB.getConexion()!=null) {
            try {
                String sql = "SELECT "
                        + "idDireccion, "
                        + "idEstado, "
                        + "idMunicipio, "
                        + "idLocalidad, "
                        + "referencias, "
                        + "calleInteriorYNumero, "
                        + "calleExteriorYNumero, "
                        + "calleFrontal, "
                        + "calleTrasera, "
                        + "calleDerecha, "
                        + "calleIzquierda, "
                        + "tipoAsentamiento, "
                        + "nombreAsentamiento, "
                        + "idUsuario, "
                        + "codigo_postal "
                        + "FROM Direccion "
                        + "WHERE idDireccion=?";
                PreparedStatement ps = conexionDB.getConexion().prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs;
                rs = ps.executeQuery();
                if (rs.next()) {
                    response = new Direccion();
                    response.setIdDireccion(rs.getInt(1));
                    response.setIdEstado(rs.getInt(2));
                    response.setIdMunicipio(rs.getInt(3));
                    response.setIdLocalidad(rs.getInt(4));
                    response.setReferencias(rs.getString(5));
                    response.setCalleInteriorYNumero(rs.getString(6));
                    response.setCalleExteriorYNumero(rs.getString(7));
                    response.setCalleFrontal(rs.getString(8));
                    response.setCalleTrasera(rs.getString(9));
                    response.setCalleDerecha(rs.getString(10));
                    response.setCalleIzquierda(rs.getString(11));
                    response.setTipoAsentamiento(rs.getString(12));
                    response.setNombreAsentamiento(rs.getString(13));
                    response.setIdUsuario(rs.getInt(14));
                    response.setCodigoPostal(rs.getString(15));
                } else {
                    Logg.error("No se encontro ningun registro");
                }
            } catch (SQLException ex) {
                Logg.error("Comunicación fallida con la base de datos");
            } finally {
                 
            }
        } else {
            Logg.error("Conexión fallida con la base de datos");
        }
        return response;
    }

    public boolean changeDomicilio(IConexion conexionDB, int infDomIdUser, String infDomIdEstado, String infDomIdMunicipio, String infDomIdocalidad, String infDomReferencias, String infDomCalleInterna, String infDomCalleExterna, String infDomCalleFrontal, String infDomCalleTrasera, String infDomCalleIzquierda, String infDomCalleDerecha, String infDomCodigoPostal, String infDomNombreAsentamiento, String infDomTipoAsentamiento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
