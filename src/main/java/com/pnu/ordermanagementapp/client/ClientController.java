package com.pnu.ordermanagementapp.client;

import com.pnu.ordermanagementapp.adapter.DbAdapter;
import com.pnu.ordermanagementapp.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Client> list = adapter.findAll();
        model.addAllAttributes(Collections.singletonMap("clients", list));
        return "show_clients";
    }

    @GetMapping("/new")
    public String create() {
        return "new_client";
    }

    @GetMapping("/update")
    public String update(Model model) {
        List<Client> clients = adapter.findAll();

        Map<String, Object> params = new HashMap<>();
        params.put("clients", clients);
        model.addAllAttributes(params);
        return "update_client";
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
    public String deleteClient(@ModelAttribute Client client ) {
        adapter.delete(client.getId());
        return "redirect:/clients";
    }
}
