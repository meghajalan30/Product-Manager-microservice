package net.codejava;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration

class AppControllerTest  {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testViewHomePage() throws Exception {
		Product product=new Product();
		product.setId((long) 1);
		product.setBrand("Apple");
		product.setName("IPhone");
		product.setMadein("China");
		product.setPrice((float) 80000.0);

		Product product1=new Product();
		product1.setId((long) 2);
		product1.setBrand("Apple");
		product1.setName("IPad");
		product1.setMadein("China");
		product1.setPrice((float) 90000.0);

		List<Product> productList=new ArrayList<Product>();
		productList.add(product);
		productList.add(product1);

		ProductRepository productRepository = mock(ProductRepository.class);
		when(productRepository.findAll()).thenReturn(productList);
		ProductService productServiceMock=mock(ProductService.class);
		productServiceMock.setRepo(productRepository);
		when(productServiceMock.listAll()).thenReturn(productList);

		AppController appController=new AppController();
		appController.setService(productServiceMock);
		mockMvc = MockMvcBuilders.standaloneSetup(appController).build();
		mockMvc.perform(MockMvcRequestBuilders.
				get("/product/")).
				andExpect(status().isOk()).
				//andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				andExpect(MockMvcResultMatchers.jsonPath("$.products[0].name", is("IPhone")));
				//andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(productList.size())));
	}

	@Test
	void testSaveProduct() throws Exception{
		Product product=new Product();
		product.setId((long) 1);
		product.setBrand("Apple");
		product.setName("IPhone");
		product.setMadein("China");
		product.setPrice((float) 80000.0);

		ObjectMapper objectMapper=new ObjectMapper();
		ProductRepository productRepository1 = mock(ProductRepository.class);
		when(productRepository1.save(product)).thenReturn(product);
		ProductService productServiceMock=mock(ProductService.class);
		productServiceMock.setRepo(productRepository1);
		when(productServiceMock.save(product)).thenReturn(product);

		AppController appController=new AppController();
		appController.setService(productServiceMock);
		mockMvc = MockMvcBuilders.standaloneSetup(appController).build();
		mockMvc.perform(MockMvcRequestBuilders.
				post("/product/save").
				contentType(MediaType.APPLICATION_JSON).
				content(objectMapper.writeValueAsString(new Product((long) 3,"IPod","Apple","China", 70000.0f,"https://assets.croma.com/medias/sys_master/images/images/hdb/h8b/8797902405662/215522_pjpeg.jpg","description")))).
				andExpect(status().isOk());
	}

	@Test
	void testShowEditProductPage() throws Exception
	{
		Product product=new Product();
		product.setId((long) 5);
		product.setBrand("Apple");
		product.setName("IPhone");
		product.setMadein("China");
		product.setPrice((float) 85000.0);

		ProductRepository productRepository1 = mock(ProductRepository.class);
		when(productRepository1.findById(anyLong())).thenReturn(java.util.Optional.of(product));
		ProductService productServiceMock=mock(ProductService.class);
		productServiceMock.setRepo(productRepository1);
		when(productServiceMock.get(anyLong())).thenReturn(product);

		AppController appController=new AppController();
		appController.setService(productServiceMock);
		mockMvc = MockMvcBuilders.standaloneSetup(appController).build();
		mockMvc.perform(MockMvcRequestBuilders.
				get("/product/edit/5")).
				andExpect(status().isOk()).
				//andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
						andExpect(MockMvcResultMatchers.jsonPath("$.name", is("IPhone")));
		//andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(productList.size())));
	}


}
