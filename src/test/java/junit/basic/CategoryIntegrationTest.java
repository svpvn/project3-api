package junit.basic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.shop.ShopApplication;
import com.example.shop.dto.CategoryDTO;
import com.example.shop.service.CategoryService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShopApplication.class)
public class CategoryIntegrationTest {

	@Autowired
	CategoryService categoryService;
	
	@Test
	public void createCategory() {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setName("Test");
		
		categoryService.create(categoryDTO);
		
		CategoryDTO saveCategory = categoryService.getById(categoryDTO.getId());
		
		assertThat(categoryDTO.getName()).isEqualTo(saveCategory.getName());
	}
}
