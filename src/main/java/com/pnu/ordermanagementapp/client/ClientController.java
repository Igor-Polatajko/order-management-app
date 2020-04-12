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
import java.util.List;

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
        return "index_orders";
    }

    @GetMapping("/new")
    public String create() {
        return "new_client";
    }

    @GetMapping("/update")
    public String update() {
        return "update_client";
    }

    @PostMapping("/new")
    public void createClient(@ModelAttribute Client client ) {
        adapter.create(client);
    }

    @PostMapping("/update")
    public void updateClient(@ModelAttribute Client client) {
        adapter.create(client);
    }

    @PostMapping("/delete/{id}")
    public void deleteClient(@ModelAttribute Client client ) {
        adapter.delete(client.getId());
    }
}
