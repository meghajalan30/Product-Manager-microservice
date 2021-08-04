package net.codejava;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {
	public ProductRepository getRepo() {
		return repo;
	}

	public void setRepo(ProductRepository repo) {
		this.repo = repo;
	}

	@Autowired
	private ProductRepository repo;
	
	public List<Product> listAll() {
		return repo.findAll();
	}
	
	public Product save(Product product) {
		return repo.save(product);
	}
	
	public Product get(long id) {
		return repo.findById(id).get();
	}
	
	public void delete(long id) {
		repo.deleteById(id);
	}
}
