package com.pnu.ordermanagementapp.order;

import com.pnu.ordermanagementapp.adapter.DbAdapter;
import com.pnu.ordermanagementapp.model.Client;
import com.pnu.ordermanagementapp.model.Order;
import com.pnu.ordermanagementapp.model.Product;
import com.pnu.ordermanagementapp.order.dto.OrderFormSubmitDto;
import com.pnu.ordermanagementapp.order.dto.OrdersFtlPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderDbAdapter orderDbAdapter;

    private DbAdapter<Client> clientDbAdapter;

    private DbAdapter<Product> productDbAdapter;

    private OrdersPageToOrdersFtlPageDtoMapper ordersPageToOrdersFtlPageDtoMapper;

    @Autowired
    public OrderController(OrderDbAdapter orderDbAdapter,
                           DbAdapter<Client> clientDbAdapter,
                           DbAdapter<Product> productDbAdapter,
                           OrdersPageToOrdersFtlPageDtoMapper ordersPageToOrdersFtlPageDtoMapper) {
        this.orderDbAdapter = orderDbAdapter;
        this.clientDbAdapter = clientDbAdapter;
        this.productDbAdapter = productDbAdapter;
        this.ordersPageToOrdersFtlPageDtoMapper = ordersPageToOrdersFtlPageDtoMapper;
    }

    @GetMapping
    public String getAll(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                         Model model) {

        Page<Order> ordersPage = orderDbAdapter.findAll(pageNumber);
        OrdersFtlPageDto orders = ordersPageToOrdersFtlPageDtoMapper.map(ordersPage);

        model.addAttribute("orders", orders);
        model.addAttribute("headline", "The most recent orders");

        return "orders/show_orders";
    }

    @GetMapping("/client/{clientId}")
    public String getAllForClient(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                                  @PathVariable("clientId") Long clientId, Model model) {

        Page<Order> ordersPage = orderDbAdapter.findByClientId(clientId, pageNumber);
        OrdersFtlPageDto orders = ordersPageToOrdersFtlPageDtoMapper
                .map(ordersPage);

        Client client = clientDbAdapter.findById(clientId);

        model.addAttribute("orders", orders);
        model.addAttribute("headline", String.format("Orders made by %s %s",
                client.getFirstName(), client.getLastName()
        ));

        return "orders/show_orders";
    }

    @GetMapping("/product/{productId}")
    public String getAllForProduct(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
                                   @PathVariable("productId") Long productId, Model model) {

        Page<Order> ordersPage = orderDbAdapter.findByProductId(productId, pageNumber);
        OrdersFtlPageDto orders = ordersPageToOrdersFtlPageDtoMapper
                .map(ordersPage);

        Product product = productDbAdapter.findById(productId);

        model.addAttribute("orders", orders);
        model.addAttribute("headline", String.format("Orders of %s (id: %s)",
                product.getName(), product.getId()
        ));

        return "orders/show_orders";
    }

    @GetMapping("/new")
    public String createNew(Model model) {

        List<Client> clients = clientDbAdapter.findAll();
        List<Product> products = productDbAdapter.findAll();

        model.addAttribute("clients", clients);
        model.addAttribute("products", products);

        return "orders/new_order";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute OrderFormSubmitDto orderDto) {

        Client client = clientDbAdapter.findById(orderDto.getClientId());
        Product product = productDbAdapter.findById(orderDto.getProductId());

        Order order = Order.builder()
                .product(product)
                .client(client)
                .amount(orderDto.getAmount())
                .createdDate(LocalDateTime.now())
                .build();

        orderDbAdapter.create(order);
        return "redirect:/orders";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        orderDbAdapter.delete(id);
        return "redirect:/orders";
    }

}
