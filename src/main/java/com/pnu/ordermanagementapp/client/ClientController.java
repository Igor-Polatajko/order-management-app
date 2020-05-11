package com.pnu.ordermanagementapp.client;

import com.pnu.ordermanagementapp.adapter.DbAdapter;
import com.pnu.ordermanagementapp.model.Client;
import com.pnu.ordermanagementapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientController {
    private DbAdapter<Client> adapter;

    @Autowired
    public ClientController(DbAdapter<Client> adapter) {
        this.adapter = adapter;
    }

    @GetMapping
    public String getAll(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("clients", adapter.findAll());
        return "client/show_clients";
    }

    @GetMapping("/new")
    public String create() {
        return "client/create_form";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("client", adapter.findById(id));
        return "client/update_form";
    }

    @PostMapping("/new")
    public String createClient(@ModelAttribute Client client, @AuthenticationPrincipal User user) {
        adapter.create(client);
        return "redirect:/clients";
    }

    @PostMapping("/update")
    public String updateClient(@ModelAttribute Client client, @AuthenticationPrincipal User user) {
        adapter.update(client);
        return "redirect:/clients";
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id, @AuthenticationPrincipal User user) {
        adapter.delete(id);
        return "redirect:/clients";
    }

}
