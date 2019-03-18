package pareto.core.api;

import org.springframework.web.bind.annotation.*;
import pareto.core.api.dto.NewProductDto;
import pareto.core.api.dto.ProductDto;
import pareto.core.api.dto.mapper.MappingUtil;
import pareto.core.entity.Product;
import pareto.core.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ProductDto createProduct(@RequestBody NewProductDto dto) {
        Product product = productService.createProduct(dto.getName());
        return MappingUtil.map(product);
    }

    @GetMapping("/product/{id}")
    public ProductDto getProduct(@PathVariable long id) {
        Optional<Product> product = productService.getProduct(id);
        return product.map(MappingUtil::map).orElse(null);
    }

    @GetMapping("/product")
    public List<ProductDto> getProducts() {
        return MappingUtil.mapProducts(productService.getProducts());
    }
}
