package edu.mum.coffee.api;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.mum.coffee.domain.Order;
import edu.mum.coffee.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderApiController {
	@Resource
	private OrderService orderService;
	
	// -------------------Retrieve All Order---------------------------------------------
	 
	 @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.findAll();
        if (orders.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }
	 
	// -------------------Retrieve Single Order------------------------------------------
	 
	    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
	    public ResponseEntity<?> getOrder(@PathVariable("id") int id) {
	        Order order = orderService.findById(id);
	        if (order == null) {
	            return new ResponseEntity(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<Order>(order, HttpStatus.OK);
	    }
	 
	    // -------------------Create a Order-------------------------------------------
	    @RequestMapping(value = "/order", method = RequestMethod.POST)
	    public ResponseEntity<?> createProduct(@RequestBody Order order, UriComponentsBuilder ucBuilder) {
	    	orderService.save(order);
	    	HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/api/order/{id}").buildAndExpand(order.getId()).toUri());
	        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	    } 
}