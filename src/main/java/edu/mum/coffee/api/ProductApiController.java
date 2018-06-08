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

import edu.mum.coffee.domain.Product;
import edu.mum.coffee.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductApiController {
	@Resource
	private ProductService productService;
	
	// -------------------Retrieve All Products---------------------------------------------
	 
	 @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProduct();
        if (products.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
	 
	// -------------------Retrieve Single Product------------------------------------------
	 
	    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	    public ResponseEntity<?> getProduct(@PathVariable("id") int id) {
	        Product product = productService.getProduct(id);
	        if (product == null) {
	            return new ResponseEntity(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<Product>(product, HttpStatus.OK);
	    }
	 
	    // -------------------Create a Product-------------------------------------------
	 
	    @RequestMapping(value = "/product", method = RequestMethod.POST)
	    public ResponseEntity<?> createProduct(@RequestBody Product product, UriComponentsBuilder ucBuilder) {
	    	productService.save(product);
	    	HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/api/product/{id}").buildAndExpand(product.getId()).toUri());
	        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	    }
	 
	    // ------------------- Update a Product ------------------------------------------------
	 
	    @RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
	    public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
	 
	        Product currentProduct = productService.getProduct(id);
	 
	        if (currentProduct == null) {
	            return new ResponseEntity("Unable to upate. User with id " + id + " not found.",
	                    HttpStatus.NOT_FOUND);
	        }
	 
	        currentProduct.setProductName(product.getProductName());
	        currentProduct.setDescription(product.getDescription());
	        currentProduct.setPrice(product.getPrice());
	        currentProduct.setProductType(product.getProductType());
	 
	        productService.save(currentProduct);
	        return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
	    }
	 
	    // ------------------- Delete a Product-----------------------------------------
	 
	    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
	        Product product = productService.getProduct(id);
	        if (product == null) {
	            return new ResponseEntity("Unable to delete. User with id " + id + " not found.",
	                    HttpStatus.NOT_FOUND);
	        }
	        productService.delete(product);;
	        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	    }
	 
}
