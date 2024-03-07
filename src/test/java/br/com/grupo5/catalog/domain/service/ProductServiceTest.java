package br.com.grupo5.catalog.domain.service;

import br.com.grupo5.catalog.domain.exception.EntityNotFoundException;
import br.com.grupo5.catalog.domain.model.Category;
import br.com.grupo5.catalog.domain.model.Product;
import br.com.grupo5.catalog.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    private Product product;
    private Category category;
    private UUID productId;
    private UUID categoryId;

    @BeforeEach
    public void setUp() {
        productId = UUID.randomUUID();

        categoryId = UUID.randomUUID();

        product = new Product("Product",
                "Description",
                true,
                BigDecimal.valueOf(999.99));
        category = new Category("Category");
    }

    @Test
    void findById_ShouldReturnProduct() {
        when(productRepository.findById(productId))
                .thenReturn(Optional.ofNullable(product));

        Product productReturned = productService.findById(productId);

        assertThat(productReturned).isEqualTo(product);
        verify(productRepository).findById(productId);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void findById_ShouldThrowEntityNotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Throwable e = assertThrows(EntityNotFoundException.class,
                () -> productService.findById(productId));

        assertThat(String.format("Product not found for id: %s", productId)).isEqualTo(e.getMessage());
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void associateProductToCategory_ShouldAssociateWithSuccess() {
        when(productRepository.findById(productId))
                .thenReturn(Optional.ofNullable(product));
        when(categoryService.findById(categoryId))
                .thenReturn(category);

        productService.associateProductToCategory(productId, categoryId);

        assertThat(product.getCategories()).containsExactly(category);
        verify(productRepository).findById(productId);
        verify(categoryService).findById(categoryId);
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void disassociateProductToCategory_ShouldDisassociateWithSuccess() {
        product.associateCategory(category);

        when(productRepository.findById(productId))
                .thenReturn(Optional.ofNullable(product));
        when(categoryService.findById(categoryId))
                .thenReturn(category);

        productService.disassociateProductToCategory(productId, categoryId);

        assertThat(product.getCategories()).doesNotContain(category);
        verify(productRepository).findById(productId);
        verify(categoryService).findById(categoryId);
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(categoryService);
    }
}
