package io.bootify.infinity_cart.service;

import io.bootify.infinity_cart.domain.Payement;
import io.bootify.infinity_cart.domain.PaymentType;
import io.bootify.infinity_cart.domain.User;
import io.bootify.infinity_cart.model.PayementDTO;
import io.bootify.infinity_cart.repos.PayementRepository;
import io.bootify.infinity_cart.repos.PaymentTypeRepository;
import io.bootify.infinity_cart.repos.UserRepository;
import io.bootify.infinity_cart.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PayementService {

    private final PayementRepository payementRepository;
    private final UserRepository userRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    public PayementService(final PayementRepository payementRepository,
            final UserRepository userRepository,
            final PaymentTypeRepository paymentTypeRepository) {
        this.payementRepository = payementRepository;
        this.userRepository = userRepository;
        this.paymentTypeRepository = paymentTypeRepository;
    }

    public List<PayementDTO> findAll() {
        final List<Payement> payements = payementRepository.findAll(Sort.by("id"));
        return payements.stream()
                .map(payement -> mapToDTO(payement, new PayementDTO()))
                .toList();
    }

    public PayementDTO get(final Long id) {
        return payementRepository.findById(id)
                .map(payement -> mapToDTO(payement, new PayementDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PayementDTO payementDTO) {
        final Payement payement = new Payement();
        mapToEntity(payementDTO, payement);
        return payementRepository.save(payement).getId();
    }

    public void update(final Long id, final PayementDTO payementDTO) {
        final Payement payement = payementRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(payementDTO, payement);
        payementRepository.save(payement);
    }

    public void delete(final Long id) {
        payementRepository.deleteById(id);
    }

    private PayementDTO mapToDTO(final Payement payement, final PayementDTO payementDTO) {
        payementDTO.setId(payement.getId());
        payementDTO.setUser(payement.getUser() == null ? null : payement.getUser().getId());
        payementDTO.setPaymentType(payement.getPaymentType() == null ? null : payement.getPaymentType().getId());
        return payementDTO;
    }

    private Payement mapToEntity(final PayementDTO payementDTO, final Payement payement) {
        final User user = payementDTO.getUser() == null ? null : userRepository.findById(payementDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        payement.setUser(user);
        final PaymentType paymentType = payementDTO.getPaymentType() == null ? null : paymentTypeRepository.findById(payementDTO.getPaymentType())
                .orElseThrow(() -> new NotFoundException("paymentType not found"));
        payement.setPaymentType(paymentType);
        return payement;
    }

}
