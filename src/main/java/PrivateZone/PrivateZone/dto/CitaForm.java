package PrivateZone.PrivateZone.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

// estado de la reserva de cita entre los 3 pasos; guarda solo ids, nunca entidades
@Data
public class CitaForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long servicioId;

    // null significa "sin preferencia"
    private Long especialistaId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hora;

    private String nombre;

    private String telefono;
}
