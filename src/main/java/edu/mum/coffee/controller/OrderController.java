package edu.mum.coffee.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.mum.coffee.domain.Order;
import edu.mum.coffee.domain.Orderline;
import edu.mum.coffee.domain.Person;
import edu.mum.coffee.domain.Product;
import edu.mum.coffee.service.OrderService;
import edu.mum.coffee.service.PersonService;
import edu.mum.coffee.service.ProductService;

@Controller
public class OrderController {
	@Resource
	private OrderService orderService;
	
	@Resource
	private PersonService personService;
	
	@Resource
	private ProductService productService;

	@RequestMapping("/orders")
	public String getOrders(Model model) {
		model.addAttribute("orders", orderService.findAll());
		return "orderList";
	}

	@RequestMapping(value = { "/addOrder" }, method = RequestMethod.GET)
	public String addOrder(@ModelAttribute("order") Order order, Model model) {
		return "addOrder";
	}

	@RequestMapping(value = { "/addOrder" }, method = RequestMethod.POST)
	public String addOrder(@ModelAttribute("product") @Valid Order order, BindingResult result, Model model) {
		if(!result.hasErrors()) {
			orderService.save(order);
			return "redirect:/orders";
		}
		return "addOrder";
	} 
	
	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
	public String placeOrder(@ModelAttribute("orderline") Orderline orderline, HttpSession session) {
		orderline.getProduct().setId(1);
		Product product = productService.getProduct(orderline.getProduct().getId());
		orderline.setProduct(product);
		List<Orderline> orderLineList;
		if(session.getAttribute("orderLineList") == null) {
			orderLineList = new ArrayList<Orderline>();
		} else {
			orderLineList = (ArrayList<Orderline>) session.getAttribute("orderLineList");
		}
		
		int index = 0;
		boolean isNew = false;
		for(Orderline ol : orderLineList) {
			if(ol.getProduct().equals(orderline.getProduct())) {
				orderLineList.set(index, orderline);
				isNew = true;
			}
			
			index++;
		}
		
		if(!isNew) orderLineList.add(orderline);
		session.setAttribute("orderLineList", orderLineList);	
		return "redirect:/index";
	}
	
	@RequestMapping(value = { "/checkout" }, method = RequestMethod.GET)
	public String checkout(Model model, HttpSession session) {
		model.addAttribute("orderLineList", (ArrayList<Orderline>) session.getAttribute("orderLineList"));
		return "checkout";
	}
	
	@RequestMapping(value = { "/makeOrder" }, method = RequestMethod.GET)
	public String makeOrder(Model model, HttpSession session) {
		List<Orderline> orderLineList =  (ArrayList<Orderline>) session.getAttribute("orderLineList");
		Person person = (Person) session.getAttribute("user");
		
		Order order = new Order();
		order.setOrderDate(new Date());
		order.setPerson(person);
		for(Orderline orderline : orderLineList) {
			order.addOrderLine(orderline);
		}
		
		orderService.save(order);
		return "success";
	}
}
