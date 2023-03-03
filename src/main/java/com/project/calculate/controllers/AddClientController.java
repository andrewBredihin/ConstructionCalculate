package com.project.calculate.controllers;

import com.project.calculate.entity.Customer;
import com.project.calculate.form.ClientForm;
import com.project.calculate.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddClientController {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = { "/addClient" }, method = RequestMethod.GET)
    public String showAddClientPage(Model model) {
        ClientForm clientForm = new ClientForm();
        model.addAttribute("clientForm", clientForm);
        return "addClient";
    }

    @RequestMapping(value = { "/addClient" }, method = RequestMethod.POST)
    public String saveClient(Model model, //
                              @ModelAttribute("clientForm") ClientForm clientForm) {
        String first_name = clientForm.getFirst_name();
        String last_name = clientForm.getLast_name();
        String second_name = clientForm.getSecond_name();
        String email = clientForm.getEmail();
        String adress = clientForm.getAdress();
        Long phone = Long.parseLong(clientForm.getPhone());

        Customer customer = new Customer();
        customer.setFirstName(first_name);
        customer.setLastName(last_name);
        customer.setSecondName(second_name);
        customer.setAdress(adress);
        customer.setEmail(email);
        customer.setPhone(phone);
        customerRepository.save(customer);

        return "redirect:/home";
    }
}
