import br.com.techchallenge.order_microsservice.order.adapters.external.product.DTO.ProductResponse
import br.com.techchallenge.order_microsservice.order.adapters.external.product.ProductAdapter
import br.com.techchallenge.order_microsservice.order.adapters.external.product.ProductConfig
import br.com.techchallenge.order_microsservice.order.core.entities.Product
import br.com.techchallenge.order_microsservice.order.core.entities.ProductCategory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class ProductAdapterTest {

    @Mock
    private lateinit var productApi: ProductConfig

    @InjectMocks
    private lateinit var productAdapter: ProductAdapter

    @Test
    fun `should get product successfully`() {
        val productId = UUID.randomUUID()
        val productResponse = ProductResponse(productId,ProductCategory.ACOMPANHAMENTO.name, "Product Name", 100.0)
        val expectedProduct = Product(productId, 100.00 ,"Product Name",ProductCategory.ACOMPANHAMENTO)

        whenever(productApi.getProduct(productId.toString())).thenReturn(productResponse)

        val product = productAdapter.getProduct(productId.toString())

        assertEquals(expectedProduct, product)
    }

}