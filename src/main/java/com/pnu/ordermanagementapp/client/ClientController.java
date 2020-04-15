package com.pnu.ordermanagementapp.client;

import com.pnu.ordermanagementapp.adapter.DbAdapter;
import com.pnu.ordermanagementapp.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clients")
public class ClientController {
    private DbAdapter<Client> adapter;

    @Autowired
    public ClientController(DbAdapter<Client> adapter) {
        this.adapter = adapter;
    }

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("clients", adapter.findAll());
        return "show_clients";
    }

    @GetMapping("/new")
    public String create() {
        return "client_form";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("client", adapter.findById(id));
        return "client_form";
    }

    @PostMapping("/new")
    public String createClient(@ModelAttribute Client client ) {
        adapter.create(client);
        return "redirect:/clients";
    }

    @PostMapping("/update")
    public String updateClient(@ModelAttribute Client client) {
        adapter.update(client);
        return "redirect:/clients";
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        adapter.delete(id);
        return "redirect:/clients";
    }
}
