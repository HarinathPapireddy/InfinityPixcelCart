package io.bootify.infinity_cart.service;

import io.bootify.infinity_cart.domain.Product;
import io.bootify.infinity_cart.domain.ShoppingCart;
import io.bootify.infinity_cart.domain.User;
import io.bootify.infinity_cart.model.CartDTO;
import io.bootify.infinity_cart.model.ShoppingCartDTO;
import io.bootify.infinity_cart.repos.ProductRepository;
import io.bootify.infinity_cart.repos.ShoppingCartRepository;
import io.bootify.infinity_cart.repos.UserRepository;
import io.bootify.infinity_cart.util.NotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ShoppingCartService {

    @Autowired
    private ProductService productService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public ShoppingCartService(final ShoppingCartRepository shoppingCartRepository,
            final UserRepository userRepository,
    final ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productRepository=productRepository;
    }

    public List<ShoppingCartDTO> findAll() {
        final List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll(Sort.by("id"));
        return shoppingCarts.stream()
                .map(shoppingCart -> mapToDTO(shoppingCart, new ShoppingCartDTO()))
                .toList();
    }

    public ShoppingCartDTO get(final Long id) {
        return shoppingCartRepository.findById(id)
                .map(shoppingCart -> mapToDTO(shoppingCart, new ShoppingCartDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ShoppingCartDTO shoppingCartDTO) {
        final ShoppingCart shoppingCart = new ShoppingCart();
        mapToEntity(shoppingCartDTO, shoppingCart);
        return shoppingCartRepository.save(shoppingCart).getId();
    }

    public void update(final Long id, final ShoppingCartDTO shoppingCartDTO) {
        final ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(shoppingCartDTO, shoppingCart);
        shoppingCartRepository.save(shoppingCart);
    }

    public void delete(final Long id) {
        shoppingCartRepository.deleteById(id);
    }

    private ShoppingCartDTO mapToDTO(final ShoppingCart shoppingCart, final ShoppingCartDTO shoppingCartDTO) {
        shoppingCartDTO.setId(shoppingCart.getId());
        shoppingCartDTO.setProduct(productService.get(shoppingCart.getProduct().getId()));
        shoppingCartDTO.setQuantity(shoppingCart.getQuantity());
        return shoppingCartDTO;
    }

    private ShoppingCart mapToEntity(final ShoppingCartDTO shoppingCartDTO, final ShoppingCart shoppingCart) {

        return shoppingCart;
    }

    public boolean addToShoppingCart(final String username,final Long productId){
        if(userRepository.existsByUsernameIgnoreCase(username) && productRepository.existsById(productId)){
            User user = userRepository.findByUsername(username);
            Product product = productRepository.findById(productId).get();
            if(!shoppingCartRepository.existsByUserAndProduct(user,product)){
                final ShoppingCart shoppingCart=new ShoppingCart();
                shoppingCart.setUser(user);
                shoppingCart.setProduct(product);
                shoppingCart.setQuantity(1L);
                shoppingCartRepository.save(shoppingCart);
                return true;
            }
        }
        return false;
    }

    public List<ShoppingCartDTO> getProductsInShoppingCart(final String username){
        final User user = userRepository.findByUsername(username);
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.getByUser(user);
        return shoppingCartList.stream().map(shoppingCart -> mapToDTO(shoppingCart,new ShoppingCartDTO())).toList();
    }

    public boolean existProductInShoppingCart(final String username,final Long id){
        if(userRepository.existsByUsernameIgnoreCase(username) && productRepository.existsById(id)){
            User user = userRepository.findByUsername(username);
            Product product = productRepository.getReferenceById(id);
            if(shoppingCartRepository.existsByUserAndProduct(user,product)){
                return true;
            }
        }
        return false;
    }

    public boolean deleteProductInCart(final String username,final Long id){
        if(userRepository.existsByUsernameIgnoreCase(username) && productRepository.existsById(id)){
            User user = userRepository.findByUsername(username);
            Product product = productRepository.getReferenceById(id);
            if(shoppingCartRepository.existsByUserAndProduct(user,product)){
                shoppingCartRepository.delete(shoppingCartRepository.getByUserAndProduct(user,product));
                return true;
            }
        }
        return false;
    }

    public boolean addQuantityOfProductInCart(final Long cartId){
        if(shoppingCartRepository.existsById(cartId)){
            ShoppingCart shoppingCart=shoppingCartRepository.findById(cartId).get();
            if(shoppingCart.getProduct().getPrice()<=10000 && shoppingCart.getQuantity()<10) {
                shoppingCart.setQuantity(shoppingCart.getQuantity() + 1);
                shoppingCartRepository.save(shoppingCart);
            } else if (shoppingCart.getProduct().getPrice()>=10000 && shoppingCart.getQuantity()<2) {
                shoppingCart.setQuantity(shoppingCart.getQuantity() + 1);
                shoppingCartRepository.save(shoppingCart);
            }
            return true;
        }
        return false;
    }

    public boolean decreaseQuantityOfProductInCart(final Long cartId){
        if(shoppingCartRepository.existsById(cartId)){
            ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId).get();
            if(shoppingCart.getQuantity()>1){
                shoppingCart.setQuantity(shoppingCart.getQuantity()-1);
                shoppingCartRepository.save(shoppingCart);
                return true;
            }
            else if(shoppingCart.getQuantity()==1){
                shoppingCartRepository.delete(shoppingCart);
                return true;
            }
        }
        return false;
    }
}
