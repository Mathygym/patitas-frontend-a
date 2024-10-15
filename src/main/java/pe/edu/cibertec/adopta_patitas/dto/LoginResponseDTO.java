package pe.edu.cibertec.adopta_patitas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//JsonIgnoreProperties(ignoreUnknown = true)
public record LoginResponseDTO(String codigo, String mensaje,String nombreUsuario,String correoUsuario) {
}
