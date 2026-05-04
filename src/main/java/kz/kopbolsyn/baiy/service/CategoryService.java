package kz.kopbolsyn.baiy.service;

import kz.kopbolsyn.baiy.model.Category;
import kz.kopbolsyn.baiy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }
}
