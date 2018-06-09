package edu.mum.coffee.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.mum.coffee.domain.Order;
import edu.mum.coffee.domain.Orderline;
import edu.mum.coffee.domain.Person;
import edu.mum.coffee.service.OrderLineService;
import edu.mum.coffee.service.OrderService;
import edu.mum.coffee.service.PersonService;
import edu.mum.coffee.service.ProductService;

@Controller
public class OrderController {
	@Resource
	private OrderService orderService;
	
	@Resource
	private PersonService personService;

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
	
	@RequestMapping(value = "/placeOrder/{id}", method = RequestMethod.GET)
	public String placeOrder(@PathVariable("id") int id, Model model) {
		Order order = new Order();
		//PersonService personService = new PersonService();
		order.setOrderDate(new Date());
		
		Person p1 = personService.findById(79);
		System.out.println("Order --->" + p1);
	//	order.setPerson(personService.findById(76));
		
		System.out.println("Order --->" + order);
		orderService.save(order);
		
		ProductService productService = new ProductService();
		Orderline orderLine = new Orderline();
		orderLine.setQuantity(2);
		orderLine.setOrder(order);
		orderLine.setProduct(productService.getProduct(id));
		OrderLineService orderLineService = new OrderLineService();
		System.out.println("OrderLine--->" + orderLine );
		orderLineService.save(orderLine);		
		return "index";
	}
}
