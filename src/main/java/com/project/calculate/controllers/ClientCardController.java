package com.project.calculate.controllers;

import com.project.calculate.CalculateApplication;
import com.project.calculate.entity.Calculation;
import com.project.calculate.entity.Customer;
import com.project.calculate.entity.User;
import com.project.calculate.form.CalculationForm;
import com.project.calculate.form.ClientForm;
import com.project.calculate.repository.CalculationRepository;
import com.project.calculate.repository.CustomerRepository;
import com.project.calculate.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Контроллер страницы карточки клиента /clientCard
 */
@Controller
public class ClientCardController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CalculationRepository calculationRepository;

    /**
     * GET запрос. Заполняет страницу расчетами выбранного клиента
     * @param id
     * @param model
     * @param request
     */
    @RequestMapping(value = "/clientCard", method = RequestMethod.GET)
    public String getClient(@RequestParam(name = "id", defaultValue = "") Long id, Model model, HttpServletRequest request) {
        //Отображение ФИ:должность пользователя
        String principal = request.getUserPrincipal().getName();
        User user = userRepository.findByLogin(principal);
        String user_name = user.getUserName();
        String user_role = "";
        Collection<? extends GrantedAuthority> roles = user.getAuthorities();
        for (GrantedAuthority x : roles) {
            if (x.getAuthority().equals("USER"))
                user_role = "Менеджер";
            else if (x.getAuthority().equals("ADMIN"))
                user_role = "Администратор";
        }
        String user_info = user_name + ": " + user_role;
        model.addAttribute("user_name", user_info);

        //Отображение данных о клиенте
        Customer customer = customerRepository.findById(id).get();
        model.addAttribute("customerId", id);
        String Customers_info = customer.getFullName() + "<br/>" + customer.getAdress();
        model.addAttribute("Customers_info", Customers_info);
        model.addAttribute("customer_name", customer.getFullName());
        model.addAttribute("customer_adress", customer.getAdress());
        model.addAttribute("customer_phone", customer.getPhone());
        model.addAttribute("customer_phone_str", customer.getPhoneMask());

        Set<Calculation> calculations = customer.getCalculations();
        Set<CalculationForm> calculationForms = new HashSet<>();
        for (Calculation x : calculations) {
            calculationForms.add(new CalculationForm(x.getId(), x.getCreatedDate().toString(), x.getСalculationState().getTitle(), x.getAddressObjectConstractions(), x.getNumber()));
        }
        model.addAttribute("calculations", calculationForms);

        return "/clientCard";
    }

    @RequestMapping(value = { "/clientCard" }, method = RequestMethod.POST)
    public String deleteCalculation(HttpServletRequest request, Model model, //
                                    @RequestParam(name = "deleteCalculation", defaultValue = "") Long calculationId,
                                    @RequestParam(name = "customer", defaultValue = "") Long customerId) {
        try{
            calculationRepository.deleteById(calculationId);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "redirect:/clientCard?id=" + customerId;
    }
}
