package pe.edu.cibertec.adopta_patitas.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.cibertec.adopta_patitas.dto.LoginResquestDTO;

import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.adopta_patitas.viewmodel.LoginModel;

@Controller
@RequestMapping("/login")
public class LoginControlller {

    @GetMapping("/inicio")
    public String inicio(Model model){
        LoginModel loginModel = new LoginModel("00","","");
         model.addAttribute("loginModel",loginModel);
        return "inicio";
    }
    @PostMapping("/autenticar")
    public String autenticar(@RequestParam("tipoDocumento")String tipoDocumento,
                             @RequestParam("numeroDocumento")String numeroDocumento,
                             @RequestParam("password") String password,
                             Model model){

        //validar campos de entrada
        if(tipoDocumento == null || tipoDocumento.trim().length()==0 ||
                                  numeroDocumento ==null || numeroDocumento.trim().length()==0 ||
                                 password == null || password.trim().length() ==0){
            LoginModel loginModel = new LoginModel("01","Error:debe completar los datos","Cristopher Matias");
            model.addAttribute("loginModel",loginModel);
            return "inicio";
        }
        //invokar servicio de autenticacion 20/09/24

        LoginModel loginModel = new LoginModel("00","","Cristopher Matias");
        model.addAttribute("loginModel",loginModel);
        return "principal";

    }
}
