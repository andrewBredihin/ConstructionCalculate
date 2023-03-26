package com.project.calculate.controllers;

import com.project.calculate.CalculateApplication;
import com.project.calculate.entity.Customer;
import com.project.calculate.form.ClientForm;
import com.project.calculate.repository.CustomerRepository;
import com.project.calculate.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер страниц добавления клиента /addClient и редактирование клиента /editClient
 */
@Controller
public class ClientController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    //Создание формы клиента
    @RequestMapping(value = { "/addClient" }, method = RequestMethod.GET)
    public String showAddClientPage(Model model) {
        ClientForm clientForm = new ClientForm();
        model.addAttribute("clientForm", clientForm);
        return "addClient";
    }

    /**
     * POST запрос. Создает нового клиента с заданными параметрами.
     * Добавляет запись в таблице customers
     * @param request
     * @param model
     * @param clientForm
     */
    @RequestMapping(value = { "/addClient" }, method = RequestMethod.POST)
    public String saveClient(HttpServletRequest request, Model model, //
                             @ModelAttribute("clientForm") ClientForm clientForm) {
        //Получаем значения из формы
        String first_name = null;
        String last_name = null;
        String second_name = null;
        String email = null;
        String adress = null;
        Long phone = 0L;
        try {
            first_name = clientForm.getFirst_name();
            last_name = clientForm.getLast_name();
            second_name = clientForm.getSecond_name();
            email = clientForm.getEmail();
            adress = clientForm.getAdress();
            phone = Long.parseLong(clientForm.getPhone());
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
        //Создаем нового клиента
        try {
            Customer customer = new Customer();
            customer.setFirstName(first_name);
            customer.setLastName(last_name);
            customer.setSecondName(second_name);
            customer.setAdress(adress);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setManager(userRepository.findByLogin(request.getUserPrincipal().getName()));
            //Сохраняем в БД
            customerRepository.save(customer);
            String id = Long.toString(customerRepository.getMaxId());
            return "redirect:/clientCard?id=" + id;
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @RequestMapping(value = { "/editClient" }, method = RequestMethod.GET)
    public String showEditClientPage(Model model, @RequestParam(name = "customerId", defaultValue = "") Long customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        ClientForm clientForm = new ClientForm(customer);

        LoggerFactory.getLogger(CalculateApplication.class).error("CUSTOMER GET: " + customer);

        model.addAttribute("clientForm", clientForm);
        return "editClient";
    }


    @RequestMapping(value = { "/editClient" }, method = RequestMethod.POST)
    public String editClient(HttpServletRequest request, Model model, //
                             @ModelAttribute("clientForm") ClientForm clientForm) {

        LoggerFactory.getLogger(CalculateApplication.class).error("CUSTOMER POST: " + clientForm);

        try {
            Customer customer = new Customer();
            customer.setId(clientForm.getId());
            customer.setFirstName(clientForm.getFirst_name());
            customer.setLastName(clientForm.getLast_name());
            customer.setSecondName(clientForm.getSecond_name());
            customer.setAdress(clientForm.getAdress());
            customer.setEmail(clientForm.getEmail());
            customer.setPhone(Long.parseLong(clientForm.getPhone()));
            customer.setManager(clientForm.getManager());



            customerRepository.save(customer);
            return "redirect:/clientCard?id=" + clientForm.getId();
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
