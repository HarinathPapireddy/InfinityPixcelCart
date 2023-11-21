package io.bootify.infinity_cart.service;

import io.bootify.infinity_cart.domain.Product;
import io.bootify.infinity_cart.domain.User;
import io.bootify.infinity_cart.domain.WishlistCart;
import io.bootify.infinity_cart.model.ProductDTO;
import io.bootify.infinity_cart.model.WishlistCartDTO;
import io.bootify.infinity_cart.repos.ProductRepository;
import io.bootify.infinity_cart.repos.UserRepository;
import io.bootify.infinity_cart.repos.WishlistCartRepository;
import io.bootify.infinity_cart.util.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class WishlistCartService {

    private final WishlistCartRepository wishlistCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final ProductService productService;

    public WishlistCartService(final WishlistCartRepository wishlistCartRepository,
            final UserRepository userRepository , final ProductRepository productRepository,final ProductService productService) {
        this.wishlistCartRepository = wishlistCartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productService=productService;
    }

    public List<WishlistCartDTO> findAll() {
        final List<WishlistCart> wishlistCarts = wishlistCartRepository.findAll(Sort.by("id"));
        return wishlistCarts.stream()
                .map(wishlistCart -> mapToDTO(wishlistCart, new WishlistCartDTO()))
                .toList();
    }

    public WishlistCartDTO get(final Long id) {
        return wishlistCartRepository.findById(id)
                .map(wishlistCart -> mapToDTO(wishlistCart, new WishlistCartDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public boolean create(final String username,final Long productId) {
        if(userRepository.existsByUsernameIgnoreCase(username) && productRepository.existsById(productId) && !wishlistCartRepository.existsByUserAndProduct(userRepository.findByUsername(username),productRepository.findById(productId).get())){
            final WishlistCart wishlistCart = new WishlistCart();
            final User user = userRepository.findByUsername(username);
            final Product product = productRepository.findById(productId).get();
            mapToEntity(user,product,wishlistCart);
            wishlistCartRepository.save(wishlistCart);
            return true;
        }
        return false;
    }

    public void delete(final Long id) {
        wishlistCartRepository.deleteById(id);
    }

    private WishlistCartDTO mapToDTO(final WishlistCart wishlistCart,
            final WishlistCartDTO wishlistCartDTO) {
        wishlistCartDTO.setId(wishlistCart.getId());
        wishlistCartDTO.setUser(wishlistCart.getUser() == null ? null : wishlistCart.getUser().getId());
        return wishlistCartDTO;
    }

    private WishlistCart mapToEntity(final User user, final Product product,final WishlistCart wishlistCart) {
        wishlistCart.setUser(user);
        wishlistCart.setProduct(product);
        return wishlistCart;
    }
    private List<ProductDTO> mapToProductDTO(List<WishlistCart> wishlistCartItems){
        List<ProductDTO> productList= new ArrayList<>();
        for(WishlistCart wishlistCart:wishlistCartItems){
            try {
                productList.add(productService.mapToDTO(wishlistCart.getProduct(), new ProductDTO()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return productList;
    }


    public List<ProductDTO> getAllWishListCartItems(final String username){

        List<WishlistCart> productList=wishlistCartRepository.getAllProductsByUser(userRepository.findByUsername(username));
        return mapToProductDTO(productList);
    }

    public boolean existProductInWishList(final String username , final Long productId){
        if(userRepository.existsByUsernameIgnoreCase(username)&&productRepository.existsById(productId)){
            return wishlistCartRepository.existsByUserAndProduct(userRepository.findByUsername(username),productRepository.findById(productId).get());
        }
        return false;
    }

    public void deleteWishListedProduct(String username,Long productId){
        if (userRepository.existsByUsernameIgnoreCase(username)&&productRepository.existsById(productId)){
            WishlistCart wishlistCart = wishlistCartRepository.getByUserAndProduct(userRepository.findByUsername(username),productRepository.findById(productId).get() );
            delete(wishlistCart.getId());
        }
    }

}
