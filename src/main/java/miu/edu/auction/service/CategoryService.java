package miu.edu.auction.service;

import miu.edu.auction.domain.Category;

import java.util.List;

public interface CategoryService {

    public Category addCategory(Category category);

    public List<Category> getCategories();
}
