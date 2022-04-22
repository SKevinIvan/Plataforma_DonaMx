package model;

import java.util.Date;

/**
 *
 * @author Kevin Ivan Sanchez Valdin
 */
public class Mochila {

    private int idItem;
    private int idRecurso;
    private int idEstudiante;
    private Date fecha;

    public Mochila() {
    }

    public Mochila(int idRecurso, int cantidad, Date fecha) {
        this.idRecurso = idRecurso;
        this.idEstudiante = cantidad;
        this.fecha = fecha;
    }

    public Mochila(int idItem, int idRecurso, int cantidad, Date fecha) {
        this.idItem = idItem;
        this.idRecurso = idRecurso;
        this.idEstudiante = cantidad;
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        this.idRecurso = idRecurso;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    @Override
    public String toString() {
        return "Mochila{" + "idItem=" + idItem + ", idRecurso=" + idRecurso + ", cantidad=" + idEstudiante + ", fecha=" + fecha + '}';
    }
}
