package io.bootify.infinity_cart.service;

import io.bootify.infinity_cart.domain.ProductCategory;
import io.bootify.infinity_cart.domain.Promotions;
import io.bootify.infinity_cart.domain.PromotonCategory;
import io.bootify.infinity_cart.model.PromotonCategoryDTO;
import io.bootify.infinity_cart.repos.ProductCategoryRepository;
import io.bootify.infinity_cart.repos.PromotionsRepository;
import io.bootify.infinity_cart.repos.PromotonCategoryRepository;
import io.bootify.infinity_cart.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PromotonCategoryService {

    private final PromotonCategoryRepository promotonCategoryRepository;
    private final PromotionsRepository promotionsRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public PromotonCategoryService(final PromotonCategoryRepository promotonCategoryRepository,
            final PromotionsRepository promotionsRepository,
            final ProductCategoryRepository productCategoryRepository) {
        this.promotonCategoryRepository = promotonCategoryRepository;
        this.promotionsRepository = promotionsRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<PromotonCategoryDTO> findAll() {
        final List<PromotonCategory> promotonCategories = promotonCategoryRepository.findAll(Sort.by("id"));
        return promotonCategories.stream()
                .map(promotonCategory -> mapToDTO(promotonCategory, new PromotonCategoryDTO()))
                .toList();
    }

    public PromotonCategoryDTO get(final Long id) {
        return promotonCategoryRepository.findById(id)
                .map(promotonCategory -> mapToDTO(promotonCategory, new PromotonCategoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PromotonCategoryDTO promotonCategoryDTO) {
        final PromotonCategory promotonCategory = new PromotonCategory();
        mapToEntity(promotonCategoryDTO, promotonCategory);
        return promotonCategoryRepository.save(promotonCategory).getId();
    }

    public void update(final Long id, final PromotonCategoryDTO promotonCategoryDTO) {
        final PromotonCategory promotonCategory = promotonCategoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(promotonCategoryDTO, promotonCategory);
        promotonCategoryRepository.save(promotonCategory);
    }

    public void delete(final Long id) {
        promotonCategoryRepository.deleteById(id);
    }

    private PromotonCategoryDTO mapToDTO(final PromotonCategory promotonCategory,
            final PromotonCategoryDTO promotonCategoryDTO) {
        promotonCategoryDTO.setId(promotonCategory.getId());
        promotonCategoryDTO.setPromotions(promotonCategory.getPromotions() == null ? null : promotonCategory.getPromotions().getId());
        promotonCategoryDTO.setProductCategory(promotonCategory.getProductCategory() == null ? null : promotonCategory.getProductCategory().getId());
        return promotonCategoryDTO;
    }

    private PromotonCategory mapToEntity(final PromotonCategoryDTO promotonCategoryDTO,
            final PromotonCategory promotonCategory) {
        final Promotions promotions = promotonCategoryDTO.getPromotions() == null ? null : promotionsRepository.findById(promotonCategoryDTO.getPromotions())
                .orElseThrow(() -> new NotFoundException("promotions not found"));
        promotonCategory.setPromotions(promotions);
        final ProductCategory productCategory = promotonCategoryDTO.getProductCategory() == null ? null : productCategoryRepository.findById(promotonCategoryDTO.getProductCategory())
                .orElseThrow(() -> new NotFoundException("productCategory not found"));
        promotonCategory.setProductCategory(productCategory);
        return promotonCategory;
    }

}
