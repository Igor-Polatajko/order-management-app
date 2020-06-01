package com.pnu.ordermanagementapp.controller;

import com.pnu.ordermanagementapp.dto.client.ClientFormSubmitDto;
import com.pnu.ordermanagementapp.model.Client;
import com.pnu.ordermanagementapp.model.User;
import com.pnu.ordermanagementapp.service.ClientService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    private Validator validator;


    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @GetMapping
    public String getAll(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                         @RequestParam(name = "active", required = false, defaultValue = "true") boolean isActive,
                         Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("active", isActive);
        model.addAttribute("clients", clientService.findAllByActivity(page, isActive, user.getId()));
        return "client/show_clients";
    }

    @GetMapping("/find")
    public String getAllByName(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                               @RequestParam(name = "active", required = false, defaultValue = "true") boolean isActive,
                               @RequestParam("q") String nameQuery, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("active", isActive);
        model.addAttribute("nameQuery", nameQuery);
        model.addAttribute("clients", clientService.findAllByNameAndActivity(page, nameQuery, isActive, user.getId()));
        return "client/show_clients";
    }

    @GetMapping("/new")
    public String create() {
        return "client/create_form";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("client", clientService.findById(id, user.getId()));
        return "client/update_form";
    }

    @PostMapping("/new")
    public String createClient(@ModelAttribute("client") ClientFormSubmitDto clientFormDto, Model model,
                               @AuthenticationPrincipal User user) {
        if (validateClientFormDto(clientFormDto, model)) return "client/create_form";

        Client client = Client.builder()
                .firstName(clientFormDto.getFirstName())
                .lastName(clientFormDto.getLastName())
                .email(clientFormDto.getEmail())
                .build();

        clientService.create(client, user.getId());
        return "redirect:/clients";
    }

    @PostMapping("/update")
    public String updateClient(@ModelAttribute("client") ClientFormSubmitDto clientFormDto, Model model,
                               @AuthenticationPrincipal User user) {

        if (validateClientFormDto(clientFormDto, model)) return "client/update_form";

        Client client = Client.builder()
                .id(clientFormDto.getId())
                .firstName(clientFormDto.getFirstName())
                .lastName(clientFormDto.getLastName())
                .email(clientFormDto.getEmail())
                .build();

        clientService.update(client, user.getId());
        return "redirect:/clients";
    }

    private boolean validateClientFormDto(@ModelAttribute("client") ClientFormSubmitDto clientFormDto, Model model) {
        Set<ConstraintViolation<ClientFormSubmitDto>> constraintViolations = validator.validate(clientFormDto);

        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            String errorMessage = constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(StringUtils.LF));
            model.addAttribute("error", errorMessage);
            model.addAttribute("client", clientFormDto);
            return true;
        }
        return false;
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id, @AuthenticationPrincipal User user) {

        clientService.delete(id, user.getId());
        return "redirect:/clients";
    }

    @PostMapping("/activate/{id}")
    public String activateClient(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {

        clientService.activate(id, user.getId());
        return "redirect:/clients";
    }

    @GetMapping("/export")
    public String downloadClientsExcel(Model model, ModelAndView mav, @AuthenticationPrincipal User user) {
        model.addAttribute("clients", clientService.findAll(user.getId()));
        return "clientsExcelView";
    }

}
