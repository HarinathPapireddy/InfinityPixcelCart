package io.bootify.infinity_cart.service;

import io.bootify.infinity_cart.domain.Promotions;
import io.bootify.infinity_cart.model.PromotionsDTO;
import io.bootify.infinity_cart.repos.PromotionsRepository;
import io.bootify.infinity_cart.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PromotionsService {

    private final PromotionsRepository promotionsRepository;

    public PromotionsService(final PromotionsRepository promotionsRepository) {
        this.promotionsRepository = promotionsRepository;
    }

    public List<PromotionsDTO> findAll() {
        final List<Promotions> promotionses = promotionsRepository.findAll(Sort.by("id"));
        return promotionses.stream()
                .map(promotions -> mapToDTO(promotions, new PromotionsDTO()))
                .toList();
    }

    public PromotionsDTO get(final Long id) {
        return promotionsRepository.findById(id)
                .map(promotions -> mapToDTO(promotions, new PromotionsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PromotionsDTO promotionsDTO) {
        final Promotions promotions = new Promotions();
        mapToEntity(promotionsDTO, promotions);
        return promotionsRepository.save(promotions).getId();
    }

    public void update(final Long id, final PromotionsDTO promotionsDTO) {
        final Promotions promotions = promotionsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(promotionsDTO, promotions);
        promotionsRepository.save(promotions);
    }

    public void delete(final Long id) {
        promotionsRepository.deleteById(id);
    }

    private PromotionsDTO mapToDTO(final Promotions promotions, final PromotionsDTO promotionsDTO) {
        promotionsDTO.setId(promotions.getId());
        promotionsDTO.setImage(promotions.getImage());
        promotionsDTO.setName(promotions.getName());
        promotionsDTO.setDescription(promotions.getDescription());
        promotionsDTO.setDicount(promotions.getDicount());
        promotionsDTO.setStartDate(promotions.getStartDate());
        promotionsDTO.setEndDate(promotions.getEndDate());
        return promotionsDTO;
    }

    private Promotions mapToEntity(final PromotionsDTO promotionsDTO, final Promotions promotions) {
        promotions.setImage(promotionsDTO.getImage());
        promotions.setName(promotionsDTO.getName());
        promotions.setDescription(promotionsDTO.getDescription());
        promotions.setDicount(promotionsDTO.getDicount());
        promotions.setStartDate(promotionsDTO.getStartDate());
        promotions.setEndDate(promotionsDTO.getEndDate());
        return promotions;
    }

}
