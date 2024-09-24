package pe.edu.cibertec.adopta_patitas.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.adopta_patitas.dto.LoginResponseDTO;
import pe.edu.cibertec.adopta_patitas.dto.LoginResquestDTO;
import pe.edu.cibertec.adopta_patitas.viewmodel.LoginModel;

@Controller
@RequestMapping("/login")
public class LoginControlller {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String bakcs = "http://localhost:8081/autenticacion/login";

    @GetMapping("/inicio")
    public String inicio(Model model) {
        LoginModel loginModel = new LoginModel("00", "", "");
        model.addAttribute("loginModel", loginModel);
        return "inicio";
    }

    @PostMapping("/autenticar")
    public String autenticar(@RequestParam("tipoDocumento") String tipoDocumento,
                             @RequestParam("numeroDocumento") String numeroDocumento,
                             @RequestParam("password") String password,
                             Model model) {

        //validar campos de entrada
        if (tipoDocumento == null || tipoDocumento.trim().length() == 0 ||
                numeroDocumento == null || numeroDocumento.trim().length() == 0 ||
                password == null || password.trim().length() == 0) {
            LoginModel loginModel = new LoginModel("01", "Error:debe completar los datos", "Cristopher Matias");
            model.addAttribute("loginModel", loginModel);
            return "inicio";
        }
        //invokar servicio de autenticacion 20/09/24

       // LoginModel loginModel = new LoginModel("00", "", "Cristopher Matias");
         //model.addAttribute("loginModel", loginModel);
       //return "principal";

        // Crear el DTO para enviar al backend
        LoginResquestDTO loginRequest = new LoginResquestDTO(tipoDocumento, numeroDocumento, password);
        LoginResponseDTO response = restTemplate.postForObject(bakcs, loginRequest, LoginResponseDTO.class);

        String codigo = response != null ? response.codigo() : "99";
        String mensaje = response != null ? response.mensaje() : "Error de conexión";
        String nombreUsuario = response != null && "00".equals(codigo) ? response.nombreUsuario() : "";

        model.addAttribute(


                "loginModel", new LoginModel(codigo, mensaje, nombreUsuario));

        // Redirigir a "principal" si la autenticación es exitosa
        return "00".equals(codigo) ? "principal" : "inicio";
    }

    }

