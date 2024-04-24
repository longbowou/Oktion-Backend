package miu.edu.oktion.service;

import miu.edu.oktion.domain.Category;

import java.util.List;

public interface CategoryService {

    public Category addCategory(Category category);

    public List<Category> getCategories();
}
