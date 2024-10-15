package pe.edu.cibertec.adopta_patitas.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.adopta_patitas.client.AutenticacionClient;
import pe.edu.cibertec.adopta_patitas.dto.LoginResponseDTO;
import pe.edu.cibertec.adopta_patitas.dto.LoginResquestDTO;
import pe.edu.cibertec.adopta_patitas.viewmodel.LoginModel;


@Controller
@RequestMapping("/login")
public class LoginControlller {

    @Autowired
    RestTemplate restTemplateAutenticacion;
    @Autowired
    AutenticacionClient autenticacionClient;


    //private final RestTemplate restTemplate = new RestTemplate();
    //private final String bakcs = "http://localhost:8081/autenticacion/login";

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

        System.out.println("Consumo wuth Restemplatwe");

        //validar campos de entrada
        if (tipoDocumento == null || tipoDocumento.trim().length() == 0 ||
                numeroDocumento == null || numeroDocumento.trim().length() == 0 ||
                password == null || password.trim().length() == 0) {
            LoginModel loginModel = new LoginModel("01", "Error:debe completar los datos", "Cristopher Matias");
            model.addAttribute("loginModel", loginModel);
            return "inicio";
        }


         try {


             LoginResquestDTO loginRequestDTO = new LoginResquestDTO(tipoDocumento, numeroDocumento, password);
             LoginResponseDTO loginResponseDTO = restTemplateAutenticacion.postForObject("/login", loginRequestDTO, LoginResponseDTO.class);

             if(loginResponseDTO.codigo().equals("00")){
                 LoginModel loginModel = new LoginModel("00", "", loginResponseDTO.nombreUsuario());
                 model.addAttribute("loginModel", loginModel);
                 return "principal";

             }
             else {
                 LoginModel loginModel = new LoginModel("00", "Error:fallida", "");
                 model.addAttribute("loginModel", loginModel);
                 return "inicio";
             }

         }catch (Exception e){
             LoginModel loginModel = new LoginModel("99", "Error:  Ocurrio un problema", "");
             model.addAttribute("loginModel", loginModel);
             System.out.println(e.getMessage());
             return "inicio";
         }

    }
    @PostMapping("/autenticar-Feing")
    public String autenticarFeign(@RequestParam("tipoDocumento") String tipoDocumento,
                             @RequestParam("numeroDocumento") String numeroDocumento,
                             @RequestParam("password") String password,
                             Model model) {

        System.out.println("Consumo with Feing Client !!");

        //validar campos de entrada
        if (tipoDocumento == null || tipoDocumento.trim().length() == 0 ||
                numeroDocumento == null || numeroDocumento.trim().length() == 0 ||
                password == null || password.trim().length() == 0) {
            LoginModel loginModel = new LoginModel("01", "Error:debe completar los datos", "Cristopher Matias");
            model.addAttribute("loginModel", loginModel);
            return "inicio";
        }


        try {

         //preparar request
            LoginResquestDTO loginRequestDTO = new LoginResquestDTO(tipoDocumento, numeroDocumento, password);
             //consumir servicion feing client

            ResponseEntity<LoginResponseDTO>responseEntity =autenticacionClient.login(loginRequestDTO);

         //validar respuesta de servicio
            if(responseEntity.getStatusCode().is2xxSuccessful()){
           //recuperar response

                LoginResponseDTO loginResponseDTO = responseEntity.getBody();

                if(loginResponseDTO.codigo().equals("00")){
                    LoginModel loginModel = new LoginModel("00", "", loginResponseDTO.nombreUsuario());
                    model.addAttribute("loginModel", loginModel);
                    return "principal";

                }
                else {
                    LoginModel loginModel = new LoginModel("00", "Error:fallida", "");
                    model.addAttribute("loginModel", loginModel);
                    return "inicio";
                }
            }else {
                LoginModel loginModel = new LoginModel("99", "Error:  Ocurrio un problema http", "");
                model.addAttribute("loginModel", loginModel);

                return "inicio";

            }



        }catch (Exception e){
            LoginModel loginModel = new LoginModel("99", "Error:  Ocurrio un problema", "");
            model.addAttribute("loginModel", loginModel);
            System.out.println(e.getMessage());
            return "inicio";
        }

    }
    }

