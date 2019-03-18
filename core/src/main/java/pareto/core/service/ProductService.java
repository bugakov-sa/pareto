package pareto.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import pareto.core.entity.Product;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    private final String schemaName;
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductService(
            @Value("${spring.flyway.schemas}") String schemaName,
            DataSource dataSource,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.schemaName = schemaName;
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Product createProduct(String name) {
        SimpleJdbcInsert productInsert = new SimpleJdbcInsert(dataSource)
                .withSchemaName(schemaName)
                .withTableName("product")
                .usingColumns("name")
                .usingGeneratedKeyColumns("id");
        long productId = productInsert.executeAndReturnKey(Map.of("name", name)).longValue();
        return getProduct(productId).get();
    }

    public List<Product> getProducts() {
        return namedParameterJdbcTemplate.query("select * from product", (rs, rowNum) -> mapProduct(rs));
    }

    private Product mapProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        return product;
    }

    public Optional<Product> getProduct(long id) {
        List<Product> products = namedParameterJdbcTemplate.query(
                "select * from product where id = :id",
                Map.of("id", id),
                (rs, rowNum) -> mapProduct(rs)
        );
        return products.isEmpty() ? Optional.empty() : Optional.of(products.get(0));
    }
}
