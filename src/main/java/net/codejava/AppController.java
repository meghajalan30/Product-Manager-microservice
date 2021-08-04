package net.codejava;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/product")
public class AppController {

	@Autowired
	private ProductService service;

	public ProductService getService() {
		return service;
	}

	public void setService(ProductService service) {
		this.service = service;
	}

	@RequestMapping("/")
	public Products viewHomePage(Model model) {
		List<Product> listProducts = service.listAll();
		Products productList=new Products(listProducts);
		return productList;
	}


	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveProduct( @RequestBody Product product) {
		Product product1=service.save(product);
	}

	@RequestMapping("/edit/{id}")
	public Product showEditProductPage(@PathVariable(name = "id") int id) {
		Product product = service.get(id);
		return product;
	}

	@RequestMapping("/delete/{id}")
	public void deleteProduct(@PathVariable(name = "id") int id) {
		service.delete(id);
	}
}
