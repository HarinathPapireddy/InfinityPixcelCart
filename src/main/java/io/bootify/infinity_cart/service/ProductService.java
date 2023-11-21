package io.bootify.infinity_cart.service;

import io.bootify.infinity_cart.domain.Image;
import io.bootify.infinity_cart.domain.Product;
import io.bootify.infinity_cart.domain.ProductCategory;
import io.bootify.infinity_cart.model.ProductDTO;
import io.bootify.infinity_cart.repos.ImageRepository;
import io.bootify.infinity_cart.repos.ProductCategoryRepository;
import io.bootify.infinity_cart.repos.ProductRepository;
import io.bootify.infinity_cart.repos.UserRepository;
import io.bootify.infinity_cart.util.NotFoundException;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    private final ImageRepository imageRepository;

    private final UserRepository userRepository;

    public ProductService(final ProductRepository productRepository,
                          final ProductCategoryRepository productCategoryRepository,
                          final ImageRepository imageRepository,
                          final UserRepository userRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.imageRepository=imageRepository;
        this.userRepository = userRepository;
    }

    public List<ProductDTO> findAll() {
        final List<Product> products = productRepository.findAll(Sort.by("id"));
        return products.stream()
                .map(product -> {
                    try {
                        return mapToDTO(product, new ProductDTO());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    public ProductDTO get(final Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    try {
                        return mapToDTO(product, new ProductDTO());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProductDTO productDTO) {
        final Product product = new Product();
        mapToEntity(productDTO, product);
        return productRepository.save(product).getId();
    }

    public String uploadImage(MultipartFile file){
        String response = "";
        try {
            String uploadPath = "D:\\productImage\\" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath));
            response = uploadPath;
        } catch (Exception ex){
            response="Exception "+ex.getMessage();
        }
        return response;
    }


    private String convertImageToBase64(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

    public void delete(final Long id) {
        productRepository.deleteById(id);
    }

    public ProductDTO mapToDTO(final Product product, final ProductDTO productDTO) {
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setRatings(product.getRatings());
        productDTO.setImage(convertImageToBase64(imageRepository.getImageByProductAndIsClickBait(product,true).getImage()));
        productDTO.setProductCategory(product.getProductCategory() == null ? null : product.getProductCategory().getId());
        return productDTO;
    }

    private Product mapToEntity(final ProductDTO productDTO, final Product product) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setRatings(productDTO.getRatings());
        final ProductCategory productCategory = productDTO.getProductCategory() == null ? null : productCategoryRepository.findById(productDTO.getProductCategory())
                .orElseThrow(() -> new NotFoundException("productCategory not found"));
        product.setProductCategory(productCategory);
        return product;
    }

    public List<ProductDTO> getProductsByName(String productName) {
        List<Product> productList = productRepository.getProductsByName(productName);
        //addRelatedProducts(productList);
        return productList.stream()
                .map(product -> {
                        return mapToDTO(product, new ProductDTO());
                })
                .toList();

    }
    private void addRelatedProducts(List<Product> productList){
        if(productList==null) return;
        else {
            ProductCategory category = productList.get(0).getProductCategory();
            productList.addAll(productRepository.getProductByProductCategory(category));
        }
    }

    public List<ProductDTO> getProductsByCategory(String productName){
        ProductCategory productCategory = productCategoryRepository.getByCategoryName(productName);
        List<Product> products;
        if(productCategory!=null) {
            products=productRepository.getProductByProductCategory(productCategory);
        }
        else{
            products = new ArrayList<>();
        }
        return products.stream()
                .map(product -> {
                    return mapToDTO(product, new ProductDTO());
                })
                .toList();
    }
    public void addImagesToProduct(Long productId,List<MultipartFile> files){
        if(productRepository.existsById(productId)){
            Product product = productRepository.findById(productId).get();
            for(MultipartFile file : files){
                Image image= new Image();
                try {
                    image.setImage(convertImagesTobyte(file));
                    image.setProduct(productRepository.findById(productId).get());
                    image.setClickBait(false);
                    image.setDateCreated(OffsetDateTime.now());
                    image.setLastUpdated(OffsetDateTime.now());
                    imageRepository.save(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void uploadClickBaitImage(Long productId,MultipartFile file){
        if(productRepository.existsById(productId) && !imageRepository.existsImageByProductAndIsClickBait(productRepository.findById(productId).get(),true)){
            Image image = new Image();
            try {
                image.setImage(convertImagesTobyte(file));
                image.setProduct(productRepository.findById(productId).get());
                image.setClickBait(true);
                image.setDateCreated(OffsetDateTime.now());
                image.setLastUpdated(OffsetDateTime.now());
                imageRepository.save(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getImagesOfProduct(final Long productId){
        List<String> images= new ArrayList<>();
        if(productRepository.existsById(productId)){
            List<Image> productimages = imageRepository.getImagesByProduct(productRepository.findById(productId).get());
            for(Image image:productimages){
                images.add(convertImageToBase64(image.getImage()));
            }
        }
        return images;
    }




    private byte[] convertImagesTobyte(MultipartFile file) throws IOException {
        return file.getBytes();
    }



}
