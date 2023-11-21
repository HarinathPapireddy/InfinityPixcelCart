package io.bootify.infinity_cart.service;

import io.bootify.infinity_cart.domain.Order;
import io.bootify.infinity_cart.domain.Payement;
import io.bootify.infinity_cart.domain.Product;
import io.bootify.infinity_cart.domain.UserAddress;
import io.bootify.infinity_cart.model.OrderDTO;
import io.bootify.infinity_cart.repos.OrderRepository;
import io.bootify.infinity_cart.repos.PayementRepository;
import io.bootify.infinity_cart.repos.ProductRepository;
import io.bootify.infinity_cart.repos.UserAddressRepository;
import io.bootify.infinity_cart.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserAddressRepository userAddressRepository;
    private final PayementRepository payementRepository;

    public OrderService(final OrderRepository orderRepository,
            final ProductRepository productRepository,
            final UserAddressRepository userAddressRepository,
            final PayementRepository payementRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userAddressRepository = userAddressRepository;
        this.payementRepository = payementRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
    }

    public void update(final Long id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setOrderedDate(order.getOrderedDate());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setProduct(order.getProduct() == null ? null : order.getProduct().getId());
        orderDTO.setUserAddress(order.getUserAddress() == null ? null : order.getUserAddress().getId());
        orderDTO.setPayment(order.getPayment() == null ? null : order.getPayment().getId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setOrderedDate(orderDTO.getOrderedDate());
        order.setTotalPrice(orderDTO.getTotalPrice());
        final Product product = orderDTO.getProduct() == null ? null : productRepository.findById(orderDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        order.setProduct(product);
        final UserAddress userAddress = orderDTO.getUserAddress() == null ? null : userAddressRepository.findById(orderDTO.getUserAddress())
                .orElseThrow(() -> new NotFoundException("userAddress not found"));
        order.setUserAddress(userAddress);
        final Payement payment = orderDTO.getPayment() == null ? null : payementRepository.findById(orderDTO.getPayment())
                .orElseThrow(() -> new NotFoundException("payment not found"));
        order.setPayment(payment);
        return order;
    }

}
