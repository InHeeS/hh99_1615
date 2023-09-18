package com.example.ali.controller;

import com.example.ali.entity.Orders;
import com.example.ali.entity.Product;
import com.example.ali.repository.OrderRepository;
import com.example.ali.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class AsideController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository ordersRepository;

    @GetMapping("/product")
    public String showProductList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        List<Product> products = productRepository.findAll();

        model.addAttribute("products", products);
        model.addAttribute("page", page);

        return "productList"; // 상품 목록 페이지로 이동
    }

    @GetMapping("/orders")
    public String showOrderList(Model model) {
        List<Orders> orders = ordersRepository.findAll();
        model.addAttribute("orders", orders);

        return "orderList"; // 주문 목록 페이지로 이동
    }

    @GetMapping("/store")
    public String showStoreManagement(Model model) {
        // 여기에 상품 관리 로직을 추가하세요.
        return "storeManagement"; // 상품 관리 페이지로 이동
    }
}
