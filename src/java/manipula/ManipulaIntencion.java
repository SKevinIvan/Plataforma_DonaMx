package manipula;

import config.conexion.ConexionFactory;
import config.conexion.IConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Intencion;
import utils.GenericResponse;
import utils.Logg;
import utils.Misc;

/**
 *
 * @author Kevin Ivan Sanchez Valdin
 */
public class ManipulaIntencion implements Manipula<Intencion> {

    @Override
    public GenericResponse<Intencion> registrar(Intencion obj) {
        GenericResponse<Intencion> response = new GenericResponse<>();
        IConexion conexionDB = ConexionFactory.getConexion("MYSQL");
        if (conexionDB.conectar() == 1) {
            try {
                String sql = "INSERT INTO intencion ("
                        + "idUsuario, "
                        + "tituloIntencion, "
                        + "descripcion, "
                        + "fechaIntencion, "
                        + "estadoIntencion, "
                        + "respuestaIntencion, "
                        + "fechaRespuesta "
                        + ") VALUES (?,?,?,?,?,?,?)";
                PreparedStatement registro = conexionDB.getConexion().prepareStatement(sql);
                registro.setInt(1, obj.getIdUsuario());
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
                conexionDB.desconectar();
            }
        } else {
            response.setStatus(utils.Constantes.STATUS_CONEXION_FALLIDA_BD);
            response.setResponseObject(null);
            response.setMensaje("Error de conexión a la base de datos");
        }
        return response;
    }

    @Override
    public GenericResponse<Intencion> actualizar(int id) {
        GenericResponse<Intencion> response = new GenericResponse<>();
        response.setMensaje("Accion no implementada");
        response.setStatus(utils.Constantes.LOGIC_WARNING);
        response.setResponseObject(null);
        return response;
    }

    public GenericResponse<Intencion> actualizar(int id, String respuestaIntencion, java.util.Date fechaRespuesta,String estado) {
        GenericResponse<Intencion> response = new GenericResponse<>();
        IConexion conexionDB = ConexionFactory.getConexion("MYSQL");
        if (conexionDB.conectar() == 1) {
            Intencion inten = encontrarId(id);
            if (inten != null) {
                try {
                    String sql = "UPDATE intencion SET "
                            + "respuestaIntencion=?, "
                            + "fechaRespuesta=?, "
                            + "estadoIntencion=? "
                            + "WHERE idIntencion=?";
                    PreparedStatement registro = conexionDB.getConexion().prepareStatement(sql);
                    registro.setString(1, respuestaIntencion);
                    registro.setDate(2, Misc.transformDateTimeJavaSql(fechaRespuesta));
                    registro.setString(3, estado);
                    registro.setInt(4, id);
                    int r = registro.executeUpdate();
                    if (r > 0) {
                        inten.setRespuestaIntencion(respuestaIntencion);
                        inten.setFechaRespuesta(fechaRespuesta);
                        response.setStatus(utils.Constantes.STATUS_ACTUALIZACION_EXITOSA_BD);
                        response.setResponseObject(inten);
                        response.setMensaje("Edición exitosa en la base de datos");
                    } else {
                        response.setStatus(utils.Constantes.STATUS_ACTUALIZACION_FALLIDA_BD);
                        response.setResponseObject(null);
                        response.setMensaje("Edición fallido en la base de datos");
                    }
                } catch (SQLException ex) {
                    response.setStatus(utils.Constantes.STATUS_CONEXION_FALLIDA_BD);
                    response.setResponseObject(null);
                    response.setMensaje("Error de comunicación con la base de datos " + ex.getSQLState());
                } finally {
                    conexionDB.desconectar();
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
    public GenericResponse<Intencion> editar(int id, Intencion nvoObj) {
        GenericResponse<Intencion> response = new GenericResponse<>();
        IConexion conexionDB = ConexionFactory.getConexion("MYSQL");
        if (conexionDB.conectar() == 1) {
            if (encontrarId(id) != null) {
                try {
                    String sql = "UPDATE intencion SET "
                            + "idUsuario=?, "
                            + "tituloIntencion=?, "
                            + "descripcion=?, "
                            + "fechaIntencion=?, "
                            + "estadoIntencion=?, "
                            + "respuestaIntencion=?, "
                            + "fechaRespuesta=? "
                            + "WHERE idIntencion=?";
                    PreparedStatement registro = conexionDB.getConexion().prepareStatement(sql);
                    registro.setInt(1, nvoObj.getIdUsuario());
                    registro.setString(2, nvoObj.getTituloIntencion());
                    registro.setString(3, nvoObj.getDescripcion());
                    registro.setDate(4, Misc.transformDateTimeJavaSql(nvoObj.getFechaIntencion()));
                    registro.setString(5, nvoObj.getEstadoIntencion());
                    registro.setString(6, nvoObj.getRespuestaIntencion());
                    registro.setDate(7, Misc.transformDateTimeJavaSql(nvoObj.getFechaRespuesta()));
                    registro.setInt(8, id);
                    int r = registro.executeUpdate();
                    if (r > 0) {
                        nvoObj.setIdIntencion(id);
                        response.setStatus(utils.Constantes.STATUS_ACTUALIZACION_EXITOSA_BD);
                        response.setResponseObject(nvoObj);
                        response.setMensaje("Edición exitosa en la base de datos");
                    } else {
                        response.setStatus(utils.Constantes.STATUS_ACTUALIZACION_FALLIDA_BD);
                        response.setResponseObject(nvoObj);
                        response.setMensaje("Edición fallido en la base de datos");
                    }
                } catch (SQLException ex) {
                    response.setStatus(utils.Constantes.STATUS_CONEXION_FALLIDA_BD);
                    response.setResponseObject(null);
                    response.setMensaje("Error de comunicación con la base de datos " + ex.getSQLState());
                } finally {
                    conexionDB.desconectar();
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
    public GenericResponse<Intencion> eliminar(int id) {
        GenericResponse<Intencion> response = new GenericResponse<>();
        IConexion conexionDB = ConexionFactory.getConexion("MYSQL");
        if (conexionDB.conectar() == 1) {
            Intencion obj = encontrarId(id);
            if (obj != null) {
                try {
                    String sql = "DELETE FROM intencion "
                            + "WHERE idIntencion=?";
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
                    conexionDB.desconectar();
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
    public List<Intencion> getData() {
        List<Intencion> response = new ArrayList<>();
        IConexion conexionDB = ConexionFactory.getConexion("MYSQL");
        if (conexionDB.conectar() == 1) {
            try {
                String sql = "SELECT "
                        + "idIntencion, "
                        + "idUsuario, "
                        + "tituloIntencion, "
                        + "descripcion, "
                        + "fechaIntencion, "
                        + "estadoIntencion, "
                        + "respuestaIntencion, "
                        + "fechaRespuesta "
                        + "FROM intencion";
                PreparedStatement ps = conexionDB.getConexion().prepareStatement(sql);
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    Intencion sol = new Intencion();
                    sol.setIdIntencion(rs.getInt(1));
                    sol.setIdUsuario(rs.getInt(2));
                    sol.setTituloIntencion(rs.getString(3));
                    sol.setDescripcion(rs.getString(4));
                    sol.setFechaIntencion(Misc.transformDateTimeSqlJava(rs.getDate(5)));
                    sol.setEstadoIntencion(rs.getString(6));
                    sol.setRespuestaIntencion(rs.getString(6));
                    sol.setFechaRespuesta(Misc.transformDateTimeSqlJava(rs.getDate(6)));
                    response.add(sol);
                }
            } catch (SQLException ex) {
                Logg.error("Comunicación fallida con la base de datos");
            } finally {
                conexionDB.desconectar();
            }
        } else {
            Logg.error("Conexión fallida con la base de datos");
        }
        return response;
    }

    @Override
    public List<Intencion> consultar(String... filtros) {
        List<Intencion> response = new ArrayList<>();
        IConexion conexionDB = ConexionFactory.getConexion("MYSQL");
        if (conexionDB.conectar() == 1) {
            try {
                String sql = "SELECT "
                        + "idIntencion, "
                        + "idUsuario, "
                        + "tituloIntencion, "
                        + "descripcion, "
                        + "fechaIntencion, "
                        + "estadoIntencion, "
                        + "respuestaIntencion, "
                        + "fechaRespuesta "
                        + "FROM intencion";
                PreparedStatement ps = conexionDB.getConexion().prepareStatement(sql);
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    Intencion sol = new Intencion();
                    sol.setIdIntencion(rs.getInt(1));
                    sol.setIdUsuario(rs.getInt(2));
                    sol.setTituloIntencion(rs.getString(3));
                    sol.setDescripcion(rs.getString(4));
                    sol.setFechaIntencion(Misc.transformDateTimeSqlJava(rs.getDate(5)));
                    sol.setEstadoIntencion(rs.getString(6));
                    sol.setRespuestaIntencion(rs.getString(6));
                    sol.setFechaRespuesta(Misc.transformDateTimeSqlJava(rs.getDate(6)));
                    response.add(sol);
                }
            } catch (SQLException ex) {
                Logg.error("Comunicación fallida con la base de datos");
            } finally {
                conexionDB.desconectar();
            }
        } else {
            Logg.error("Conexión fallida con la base de datos");
        }
        return response;
    }

    @Override
    public Intencion encontrarId(int id) {
        Intencion response = null;
        IConexion conexionDB = ConexionFactory.getConexion("MYSQL");
        if (conexionDB.conectar() == 1) {
            try {
                String sql = "SELECT "
                        + "idIntencion, "
                        + "idUsuario, "
                        + "tituloIntencion, "
                        + "descripcion, "
                        + "fechaIntencion, "
                        + "estadoIntencion, "
                        + "respuestaIntencion, "
                        + "fechaRespuesta "
                        + "FROM intencion "
                        + "WHERE idIntencion=?";
                PreparedStatement ps = conexionDB.getConexion().prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs;
                rs = ps.executeQuery();
                if (rs.next()) {
                    response = new Intencion();
                    response.setIdIntencion(rs.getInt(1));
                    response.setIdUsuario(rs.getInt(2));
                    response.setTituloIntencion(rs.getString(3));
                    response.setDescripcion(rs.getString(4));
                    response.setFechaIntencion(Misc.transformDateTimeSqlJava(rs.getDate(5)));
                    response.setEstadoIntencion(rs.getString(6));
                    response.setRespuestaIntencion(rs.getString(6));
                    response.setFechaRespuesta(Misc.transformDateTimeSqlJava(rs.getDate(6)));
                } else {
                    Logg.error("No se encontro ningun registro");
                }
            } catch (SQLException ex) {
                Logg.error("Comunicación fallida con la base de datos");
            } finally {
                conexionDB.desconectar();
            }
        } else {
            Logg.error("Conexión fallida con la base de datos");
        }
        return response;
    }
}