package pe.edu.cibertec.adopta_patitas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.edu.cibertec.adopta_patitas.dto.LoginResponseDTO;
import pe.edu.cibertec.adopta_patitas.dto.LoginResquestDTO;

@FeignClient(name="autenticaion", url="http://localhost:8081/autenticacion")
public interface AutenticacionClient {
    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(@RequestBody LoginResquestDTO loginResquestDTO);

}
