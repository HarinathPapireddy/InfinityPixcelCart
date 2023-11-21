package io.bootify.infinity_cart.AppController;

import io.bootify.infinity_cart.domain.User;
import io.bootify.infinity_cart.domain.WishlistCart;
import io.bootify.infinity_cart.model.*;
import io.bootify.infinity_cart.service.AddressService;
import io.bootify.infinity_cart.service.ShoppingCartService;
import io.bootify.infinity_cart.service.UserService;
import io.bootify.infinity_cart.service.WishlistCartService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserAppController {
    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;


    @Autowired
    private WishlistCartService wishlistCartService;

    @Autowired
    private ShoppingCartService shoppingCartService;




    @PostMapping("/addAddress")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createAddress(@RequestBody  final AddressDTO addressDTO) {
        final Long createdId = addressService.create(addressDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/getAllAddresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(@RequestParam("username") final String username){
        return ResponseEntity.ok(addressService.getAllAddresses(username));
    }

    @GetMapping("/getAccount")
    public ResponseEntity<UserDTO> getAccount(@RequestParam("username") final String username){
        return ResponseEntity.ok(userService.getAccount(username));
    }

    @DeleteMapping("/removeAddress")
    public ResponseEntity<HttpStatus> removeAddress(@RequestParam("username") String username ,@RequestParam("addressId") Long id){
        if(addressService.delete(username,id)) {
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addToWishList")
    public ResponseEntity<HttpStatus> addToWishList(@RequestParam("username") String username ,@RequestParam("productId") Long id){
        if(wishlistCartService.create(username,id)){
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getAllWishListItems")
    public ResponseEntity<List<ProductDTO>> getAllWishListItems(@RequestParam("username") String username){
        return ResponseEntity.ok(wishlistCartService.getAllWishListCartItems(username));
    }

    @GetMapping("/productWishListedOrNot")
    public ResponseEntity<HttpStatus> productWishListedOrNot(@RequestParam("username") String username ,@RequestParam("productId") Long id){
        if(wishlistCartService.existProductInWishList(username,id)){
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/removeFromWishList")
    public ResponseEntity<Void> removeProductFromWishList(@RequestParam("username") String username ,@RequestParam("productId") Long id){
        wishlistCartService.deleteWishListedProduct(username,id);
        return  ResponseEntity.noContent().build();
    }

    @PostMapping("/addToShoppingCart")
    public ResponseEntity<HttpStatus> addToShoppingCart(@RequestParam("username") String username ,@RequestParam("productId") Long id){
        if(shoppingCartService.addToShoppingCart(username,id)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("getProductsInShoppingCart")
    public ResponseEntity<List<ShoppingCartDTO>> getProductsInShoppingCart(@RequestParam("username") String username){
        if(userService.userExists(username)){
            return ResponseEntity.ok(shoppingCartService.getProductsInShoppingCart(username));
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/productExistsInCart")
    public ResponseEntity<Void> productExistsInCart(@RequestParam("username") String username ,@RequestParam("productId") Long id){
        if(shoppingCartService.existProductInShoppingCart(username,id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteProductInCart")
    public ResponseEntity<Void> deleteProductInCart(@RequestParam("username") String username ,@RequestParam("productId") Long id){
        if(shoppingCartService.deleteProductInCart(username,id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/addQuantity")
    public ResponseEntity<Void> addQuantityInCart(@RequestParam("cartId") Long cartId){
        if(shoppingCartService.addQuantityOfProductInCart(cartId)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    @PutMapping("/decreaseQuantity")
    public ResponseEntity<Void> decreaseQuantityInCart(@RequestParam("cartId") final Long cartId){
        if(shoppingCartService.decreaseQuantityOfProductInCart(cartId))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }



}
