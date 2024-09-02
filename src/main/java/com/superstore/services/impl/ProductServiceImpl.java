package com.superstore.services.impl;

import com.superstore.dto.ProductDto;
import com.superstore.entity.Category;
import com.superstore.entity.Product;
import com.superstore.exceptions.CategoryNotFoundException;
import com.superstore.exceptions.InvalidFilterException;
import com.superstore.exceptions.ProductNotFoundException;
import com.superstore.mapper.ProductMapper;
import com.superstore.repository.CategoryRepository;
import com.superstore.repository.ProductRepository;
import com.superstore.services.ProductService;
import com.superstore.specifications.ProductSpecifications;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductMapper productMapper;

    private final ProductRepository dao;

    private final CategoryRepository categoryDao;


    private Product createAndSaveProduct(Product product) {
        return dao.save(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
//        TODO: что то придумать
        Category category = categoryDao.findById(Long.parseLong(productDto.category()))
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + productDto.category() + " not found"));

        Product product = productMapper.productDtoToProduct(productDto);
        product.setCategory(category);
        Product savedProduct = createAndSaveProduct(product);

        return productMapper.productToProductDto(savedProduct);
    }

    @Override
    public ProductDto editProduct(Long productId, ProductDto newProduct) {
        Product existingProduct = dao.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));
        Product updatedProduct = productMapper.productDtoToProduct(newProduct);

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setDiscountPrice(updatedProduct.getDiscountPrice());
        existingProduct.setImageURL(updatedProduct.getImageURL());

        return productMapper.productToProductDto(dao.save(existingProduct));
    }

    @Override
    public ProductDto getProductById(Long productId) {
        return dao.findById(productId).map(productMapper::productToProductDto)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));
    }

    @Override
    public void deleteById(Long productId) {
        if (!dao.existsById(productId)) {
            logger.error("Product with id {} not found", productId);
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        }

        dao.deleteById(productId);
    }

    public List<ProductDto> getProducts(BigDecimal minPrice, BigDecimal maxPrice, Boolean hasDiscount, Long categoryId, String sortBy, String order) {

        if (minPrice != null && minPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidFilterException("Minimum price must be greater than or equal to zero.");
        }
        if (maxPrice != null && maxPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidFilterException("Maximum price must be greater than or equal to zero.");
        }
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new InvalidFilterException("Minimum price cannot be greater than maximum price.");
        }

        // Построение спецификации для фильтрации
        Specification<Product> spec = Specification.where(null);

        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.priceGreaterThanOrEqualTo(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.priceLessThanOrEqualTo(maxPrice));
        }
        if (hasDiscount != null) {
            spec = spec.and(ProductSpecifications.hasDiscount(hasDiscount));
        }
        if (categoryId != null) {
            spec = spec.and(ProductSpecifications.categoryIdEquals(categoryId));
        }

        // Построение сортировки
        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);

        // Выполнение запроса в репозитории
        List<Product> products = dao.findAll(spec, sort);

        // Маппинг в DTO
        return products.stream().map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }
}
