package io.bootify.infinity_cart.service;

import io.bootify.infinity_cart.domain.ProductCategory;
import io.bootify.infinity_cart.model.ProductCategoryDTO;
import io.bootify.infinity_cart.repos.ProductCategoryRepository;
import io.bootify.infinity_cart.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(final ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategoryDTO> findAll() {
        final List<ProductCategory> productCategories = productCategoryRepository.findAll(Sort.by("id"));
        return productCategories.stream()
                .map(productCategory -> mapToDTO(productCategory, new ProductCategoryDTO()))
                .toList();
    }

    public ProductCategoryDTO get(final Long id) {
        return productCategoryRepository.findById(id)
                .map(productCategory -> mapToDTO(productCategory, new ProductCategoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProductCategoryDTO productCategoryDTO) {
        final ProductCategory productCategory = new ProductCategory();
        mapToEntity(productCategoryDTO, productCategory);
        return productCategoryRepository.save(productCategory).getId();
    }

    public void update(final Long id, final ProductCategoryDTO productCategoryDTO) {
        final ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productCategoryDTO, productCategory);
        productCategoryRepository.save(productCategory);
    }

    public void delete(final Long id) {
        productCategoryRepository.deleteById(id);
    }

    private ProductCategoryDTO mapToDTO(final ProductCategory productCategory,
            final ProductCategoryDTO productCategoryDTO) {
        productCategoryDTO.setId(productCategory.getId());
        productCategoryDTO.setCategoryName(productCategory.getCategoryName());
        productCategoryDTO.setParentProductCategoryId(productCategory.getParentProductCategoryId() == null ? null : productCategory.getParentProductCategoryId().getId());
        return productCategoryDTO;
    }

    private ProductCategory mapToEntity(final ProductCategoryDTO productCategoryDTO,
            final ProductCategory productCategory) {
        productCategory.setCategoryName(productCategoryDTO.getCategoryName());
        final ProductCategory parentProductCategoryId = productCategoryDTO.getParentProductCategoryId() == null ? null : productCategoryRepository.findById(productCategoryDTO.getParentProductCategoryId())
                .orElseThrow(() -> new NotFoundException("parentProductCategoryId not found"));
        productCategory.setParentProductCategoryId(parentProductCategoryId);
        return productCategory;
    }

}
