package miu.edu.auction.service;

import miu.edu.auction.domain.Category;
import miu.edu.auction.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CategoryServiceImpl implements CategoryService { // Assuming you have a CategoryService interface


    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
